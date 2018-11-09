package domain;

import com.google.gson.Gson;

import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 统一封装返回信息
 * Created by  Administrator on  2018/11/1
 */
public class Result {

    private Integer errcode;

    private String errmsg;

    private Object data;

    public static void genSuccessResult(Object data, ServletResponse response) {
        Result result = new Result();
        result.setErrcode(0);
        result.setErrmsg("");
        result.setData(data);

        try {
            response.getOutputStream().write(new Gson().toJson(result).getBytes("utf-8"));
        } catch (IOException e) {
            System.out.println("ServletResponse 输出异常");
        }
    }

    public static void genErrResult(String errmsg, ServletResponse response) {
        Result result = new Result();
        result.setErrcode(1);
        result.setErrmsg(errmsg);
        result.setData(new Object());

        try {
            response.getOutputStream().write(new Gson().toJson(result).getBytes("utf-8"));
        } catch (IOException e) {
            System.out.println("ServletResponse 输出异常");
        }
    }


    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
