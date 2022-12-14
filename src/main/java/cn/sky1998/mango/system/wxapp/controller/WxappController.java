package cn.sky1998.mango.system.wxapp.controller;

import cn.sky1998.mango.framework.web.core.AjaxResult;
import cn.sky1998.mango.system.wxapp.service.WxappService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WxappController {

    @Autowired
    private WxappService wxappService;

    /**
     * 公众号-获取二维码
     * @return
     */
    @GetMapping("/getQrCode/public")
    public AjaxResult getQrCode(){
        return AjaxResult.success(wxappService.getQrCode());
    }

    /**
     * 验证token
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws IOException
     */
    @RequestMapping("/valid/public")
    public String validateWxappToken(HttpServletResponse response,String  signature, String timestamp, String nonce, String echostr) throws IOException {
        String s = wxappService.validateWxappToken(signature, timestamp, nonce, echostr);
        response.setCharacterEncoding("UTF-8");
       // response.getWriter().print(echostr);
        response.getOutputStream().print(echostr);
        return s;
    }

    /**
     * 接收用户的消息和事件
     * @param request
     * @param openid
     * @return
     */
    @PostMapping("/valid/public")
    public String getMessage(HttpServletRequest request,String  openid) {

        return wxappService.getMessage(request,openid);
    }

    /**
     * 获取小程序openid
     * @param code
     * @param appid
     * @param req
     * @return
     */
    @PostMapping(value = "/getOpenid/public")
    public AjaxResult getOpenid(String code, String appid,HttpServletRequest req){

        return AjaxResult.success(wxappService.getOpenid(code,appid));
    }


}
