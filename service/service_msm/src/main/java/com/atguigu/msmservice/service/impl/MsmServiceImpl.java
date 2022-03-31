package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {

            if(StringUtils.isEmpty(phone)) return false;

            DefaultProfile profile =
                    DefaultProfile.getProfile("default", "LTAI5t5jdU4efBdSq6BaDVf7", "NIQHTyDq38qInii8czrOqHnA1Kh6hx");
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            //request.setProtocol(ProtocolType.HTTPS);固定参数
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");

            request.putQueryParameter("PhoneNumbers", phone);//设置手机号
            request.putQueryParameter("SignName", "好学在线教育网站");//签名名
            request.putQueryParameter("TemplateCode", "SMS_190267840");//模板编号
            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

            try {
                //最终发送
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                return response.getHttpResponse().isSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

}
