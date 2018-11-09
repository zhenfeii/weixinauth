package service;

import dao.SysAdminDao;
import dao.imp.SysAdminDaoImp;
import domain.User;
import utils.RandomUtils;
import utils.TokenUtil;
import utils.WeixinAuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理 业务层
 * Created by  Administrator on  2018/11/8
 */
public class SysAdminService {

    private SysAdminDao sysAdminDao = new SysAdminDaoImp();

    /**
     * 业务逻辑在这里处理
     * @param code
     * @param request
     */
    public boolean weixinAuth(String code, HttpServletRequest request) {

        //获取access_token
        Map<String, String> accesstoken_map = WeixinAuthUtils.get_access_token_by_code(code);

        //拉取用户信息
        Map<String, String> userinfo_map = WeixinAuthUtils.get_userinfo_by_token(accesstoken_map.get("access_token"), accesstoken_map.get("openid"));

        //有可能access token以被使用
        if (userinfo_map.get("errcode") != null) {
            System.out.println("拉取用户信息 失败啦,快来围观:" + userinfo_map.get("errmsg"));
            //刷新access_token
            Map<String, String> access_token_by_refresh_tokenMap = WeixinAuthUtils.get_access_token_by_refresh_token(accesstoken_map.get("refresh_token"));
            // 再次拉取用户信息
            userinfo_map = WeixinAuthUtils.get_userinfo_by_token(access_token_by_refresh_tokenMap.get("access_token"), access_token_by_refresh_tokenMap.get("openid"));
        }

        String openid = userinfo_map.get("openid");
        if (openid == null) {
            return false;
        } else {

            //生成10位hash
            String hash = RandomUtils.getRandomChars(10);
            Map<String, Object> claims = new HashMap<>();
            claims.put("hash", hash);
            claims.put("openid", openid);
            claims.put("timestamp", new Date().getTime());

            userinfo_map.put("hash", hash);

            //判断用户是否存在
            User user = sysAdminDao.getInfoByOpenid(openid);
            if (user.getOpenid() == null) {
                //新增
                sysAdminDao.insert(userinfo_map);
            } else {
                //更新hash
                sysAdminDao.updateHash(openid, hash);
            }

            //生成token
            String token = TokenUtil.generateToken(claims);

            Map<String,String> info = new HashMap<>();
            info.put("isAuth","1");
            info.put("token",token);

            String uuid = request.getParameter("uuid");
            request.getServletContext().setAttribute(uuid,info);
            return true;
        }
    }

}
