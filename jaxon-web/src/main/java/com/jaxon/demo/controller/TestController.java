package com.jaxon.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.jaxon.demo.service.TestService;
import com.jaxon.entity.web.ApiResult;
import com.jaxon.log.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;


    @RequestMapping("test")
    @ResponseBody
    public String getUserChannelInfo(HttpServletRequest request, HttpServletResponse response){
        ApiResult result = null;
        String json = null;
        Map<String,Object> data = new HashMap<String,Object>();
        try
        {

        }

        catch (Exception e)
        {
            result = new ApiResult("F", "服务器繁忙！", null);
        }finally{
            if(result == null){
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }


}
