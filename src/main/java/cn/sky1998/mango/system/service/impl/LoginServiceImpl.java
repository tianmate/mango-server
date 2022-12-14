package cn.sky1998.mango.system.service.impl;

import cn.sky1998.mango.common.constant.Constants;
import cn.sky1998.mango.common.exception.user.CaptchaException;
import cn.sky1998.mango.common.exception.user.CaptchaExpireException;
import cn.sky1998.mango.common.utils.StringUtils;
import cn.sky1998.mango.framework.config.MangoConfig;
import cn.sky1998.mango.framework.utils.RedisUtils;
import cn.sky1998.mango.system.domain.Account;
import cn.sky1998.mango.system.domain.form.LoginBody;
import cn.sky1998.mango.system.security.service.SecurityLoginService;
import cn.sky1998.mango.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tcy@1753163342@qq.com
 * @Date 20-01-2022
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SecurityLoginService service;

    @Autowired
    private MangoConfig mangoConfig;

    @Override
    public Map<Object, Object> commonLogin(LoginBody loginBody) {

        //验证码开关
        Boolean captchaEnable = mangoConfig.getCaptchaEnable();

        if (captchaEnable) {
            //校验验证码
            if (StringUtils.isEmpty(loginBody.getCode()) && StringUtils.isEmpty(loginBody.getUuid())) {
                throw new CaptchaExpireException();
            }
            String verifyKey = Constants.CAPTCHA_CODE_KEY + loginBody.getUuid();
            String value = (String) RedisUtils.getValue(verifyKey);
            if (value == null) {
                throw new CaptchaExpireException();
            }
            if (!value.equals(loginBody.getCode())) {
                // AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }

        }
        //校验用户是否存在或者是否启用
        return service.login(loginBody.getUsername(),loginBody.getPassword());
    }

    @Override
    public Map<String, Object> gzhLogin(Account sysAccount) {
        /**
         * 前端传递的code和后端生成的code进行比对
         */

        Map<String,Object> map=new HashMap<>(2);

        map.put("token","token");

        return map;
    }

    @Override
    public Map<Object, Object> uniappLogin() {
        return null;
    }
}