package filter;


import dao.SysAdminDao;
import dao.imp.SysAdminDaoImp;
import domain.User;
import utils.CookieUtils;
import utils.TokenUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 基于token验证
 * Created by  Administrator on  2018/11/6
 */
@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //记录从哪个页面进来的
        String reUrl = request.getRequestURI();

        String token = CookieUtils.fine(request, "token");
        //token不存在,则要求登录
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/front/login?reUrl=" + reUrl);
            return;
        }
        try {
            //解析token
            Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);

            String openid = (String) claims.get("openid");
            String hash = (String) claims.get("hash");
            String timestamp = String.valueOf(claims.get("timestamp"));

            //三者必须存在,少一样说明token被篡改
            if (openid == null || hash == null || timestamp == null) {
                response.sendRedirect(request.getContextPath() + "/front/login?reUrl=" + reUrl);
                return;
            }
            //三者合法才通过
            if(!(checkOpenidAndHash(openid,hash) && checkTimeStamp(timestamp))){
                response.sendRedirect(request.getContextPath() + "/front/login?reUrl=" + reUrl);
                return;
            }

            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            //token非法
            response.sendRedirect(request.getContextPath() + "/front/login?reUrl=" + reUrl);
            return;
        }
    }

    /**
     * 检查token是否过期
     * 开发时:指定1分钟,可以更好的看到效果
     * @param timestamp
     * @return
     */
    private boolean checkTimeStamp(String timestamp) {

        // 有效期: 30分钟,单位: ms
        long expires_in = 30 * 1000 * 60;

        long timestamp_long = Long.parseLong(timestamp);

        //两者相差的时间,单位(ms)
        long time = new Date().getTime() - timestamp_long;

        if(time > expires_in){
            //过期
            return false;
        }else {
            return true;
        }

    }


    @Override
    public void destroy() {

    }

    /**
     * 判断opendid,hash是否合法
     * true合法
     * false不合法
     * @param openid
     * @return
     */
    private Boolean checkOpenidAndHash(String openid,String hash){
        SysAdminDao sysAdminDao = new SysAdminDaoImp();
        User user = sysAdminDao.getInfoByOpenid(openid);

        if(user.getOpenid() != null){
            //对比
            if(openid.equals(user.getOpenid()) && hash.equals(user.getHash())){
                return true;
            }
        }
        return false;
    }

}
