package sevlet;

import utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录
 * Created by  Administrator on  2018/11/9
 */
@WebServlet("/front/logout")
public class LogoutServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //删除cookie即可
        CookieUtils.delete("token",resp);

        resp.sendRedirect(req.getContextPath() + "/front/login");
    }
}
