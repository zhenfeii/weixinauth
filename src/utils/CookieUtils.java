package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on  2018/9/26
 */
public class CookieUtils {

    private static final String DEFAULT_PATH = "/";
    private static final int DEFAULT_AGE = -1;

    private static String path = DEFAULT_PATH;
    private static int age = DEFAULT_AGE;

    /**
     * 添加cookie
     * @param name
     * @param value
     * @param response
     */
    public static void add(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name,value);
        /**
         * 单位：秒
         * expiry - an integer specifying the maximum age of the cookie in seconds;
         * if negative, means the cookie is not stored; if zero, deletes the cookie
         */
        cookie.setMaxAge(-1);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param name
     * @param response
     */
    public static void delete(String name,HttpServletResponse response){
        Cookie cookie = new Cookie(name,"");
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 改变cookie的值
     * @param cookie
     * @param value
     */
    public static void edit(Cookie cookie, String value){
        if(cookie != null){
            cookie.setValue(value);
        }
    }

    /**
     * 增加cookie名称查找，返回其值
     */
    public static String fine(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie:cookies){
                if(name.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static Cookie get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName()))
                    return cookie;
            }
        }
        return null;
    }
}