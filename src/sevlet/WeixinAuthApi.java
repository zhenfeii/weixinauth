package sevlet;

import domain.Result;
import service.SysAdminService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信回调
 * 注意: 微信X5浏览器自带一个超时重发的功能（超过10秒重新发送请求），所以如果你处理code的时间过长，就会出现多次code的情况
 * Created by  Administrator on  2018/11/8
 */
@WebServlet("/front/weixin/auth")
public class WeixinAuthApi extends HttpServlet {

    SysAdminService sysAdminService = new SysAdminService();

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String code = request.getParameter("code");
        String uuid = request.getParameter("uuid");

        response.setHeader("Content-Type", "application/json;charset=utf-8");

        if (uuid == null) {
            Result.genErrResult("二维码来源不正确", response);
            return;
        }

        Boolean aBoolean = sysAdminService.weixinAuth(code,request);

        if(aBoolean == false){
            Result.genErrResult("授权失败", response);
        }else {
            response.getOutputStream().write("授权成功".getBytes("utf-8"));
        }

    }
}
