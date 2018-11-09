package dao;

import domain.User;

import java.util.Map;


/**
 * Created by Administrator on  2018/6/14
 */
public interface SysAdminDao {

    User getInfoByOpenid(String openid);

    void updateHash(String openid, String hash);

    void insert(Map<String,String> resMap);


}
