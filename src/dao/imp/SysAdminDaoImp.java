package dao.imp;

import dao.SysAdminDao;
import domain.User;
import utils.C3poUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by  Administrator on  2018/11/8
 */
public class SysAdminDaoImp implements SysAdminDao {


    /**
     * 根据openid获取用户
     * @param openid
     * @return
     */
    @Override
    public User getInfoByOpenid(String openid) {
        //声明
        User user = new User();

        String sql = "select * from sys_user where openid=?";

        ResultSet rs = C3poUtils.executeQuery(sql,openid);

        try {
            while (rs.next()){
                user.setId(rs.getInt("id"));
                user.setOpenid(rs.getString("openid"));
                user.setNickname(rs.getString("nickname"));
                user.setSex(rs.getString("sex"));
                user.setProvince(rs.getString("province"));
                user.setCity(rs.getString("city"));
                user.setCountry(rs.getString("country"));
                user.setHeadimgurl(rs.getString("headimgurl"));
                user.setUnionid(rs.getString("unionid"));
                user.setHash(rs.getString("hash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 更新hash
     * @param openid
     * @param hash
     */
    @Override
    public void updateHash(String openid, String hash) {
        String sql = "update sys_user set hash=? where openid=?";
        C3poUtils.execute(sql,hash,openid);
    }

    /**
     * 新增用户
     * @param resMap
     */
    @Override
    public void insert(Map<String, String> resMap) {
        String sql = "insert into sys_user(openid,nickname,sex,province,city,country,headimgurl,unionid,hash) " +
                "values(?,?,?,?,?,?,?,?,?)";

        C3poUtils.execute(sql,new Object[]{resMap.get("openid"),resMap.get("nickname"),resMap.get("sex"),resMap.get("province"),
                resMap.get("city"),resMap.get("country"),resMap.get("headimgurl"),resMap.get("unionid"),resMap.get("hash")});
    }

    public static void main(String[] args) {
        User user = new SysAdminDaoImp().getInfoByOpenid("odhaT1K5s4JEakNalUlkZLhkB2T8");
        System.out.println();
    }
}
