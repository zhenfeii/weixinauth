package sevlet;


import domain.Result;
import utils.CookieUtils;
import utils.RandomUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * 微信公众号(测试号) 微信网页授权
 * Created by  Administrator on  2018/11/8
 */
@WebServlet("/front/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //用户临时凭证
        String uuid = RandomUtils.getRandomChars(40);
        //使用全局变量
        Map<String,String> info = new HashMap<>();
        info.put("isAuth","0");
        info.put("token","");
        req.getServletContext().setAttribute(uuid,info);


        req.setAttribute("uuid",uuid);
        req.setAttribute("reUrl",req.getParameter("reUrl"));

        CookieUtils.add("uuid",uuid,resp);

        req.getRequestDispatcher("/WEB-INF/weixinlogin.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

        Map<String,Object> map = new HashMap<String,Object>();

        String uuid = request.getParameter("uuid");
        //判断微信是否已经授权
        Map<String,String> info = (Map<String, String>) request.getServletContext().getAttribute(uuid);

        if("1".equals(info.get("isAuth"))){
            map.put("isAuth",true);
            CookieUtils.add("token",info.get("token"),response);
            Result.genSuccessResult(map,response);
        }else {
            map.put("isAuth",false);
            Result.genSuccessResult(map,response);
        }
    }
}
