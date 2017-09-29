package com.jaxon.tbk.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jaxon.entity.web.ApiResult;
import com.jaxon.log.LogUtil;
import com.jaxon.tbk.service.TbkService;
import com.jaxon.util.TFUtil;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("tbk")
public class TbkController {

    @Autowired
    private TbkService tbkService;


    /**
     * 查询优惠券商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getCouponDetail")
    @ResponseBody
    public String getCouponDetail(HttpServletRequest request, HttpServletResponse response){
        ApiResult result = null;
        String json = null;
        Map<String,Object> data = new HashMap<String,Object>();
        try
        {

            String couponClickUrl = request.getParameter("couponClickUrl");
            String[] strs  = couponClickUrl.split("\\?");
            String me = null;
            if(strs.length > 1){
                String[] params = strs[1].split("&");
                for(String s : params){
                    if(s.startsWith("e=")){
                        me = s.substring(2);
                    }
                }
            }

            if(TFUtil.isNotEmpty(me)){
                TbkCouponGetResponse couponDetail = tbkService.getCouponDetail(me);
                TbkCouponGetResponse.MapData d = couponDetail.getData();
                data.put("couponAmount",d.getCouponAmount());
                data.put("couponEndTime",d.getCouponEndTime());
                data.put("couponRemainCount",d.getCouponRemainCount());
                data.put("couponStartFee",d.getCouponStartFee());
                data.put("couponStartTime",d.getCouponStartTime());
                data.put("couponTotalCount",d.getCouponTotalCount());
            }

        }

        catch (Exception e)
        {
            result = new ApiResult("F", "服务器繁忙！", null);
            LogUtil.error("", e);
        }finally{
            if(result == null){
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }


    /**
     * 查询优惠券商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getCouponItem")
    @ResponseBody
    public String getCouponItem(HttpServletRequest request, HttpServletResponse response){
        ApiResult result = null;
        String json = null;
        Map<String,Object> data = new HashMap<String,Object>();
        try
        {

            String keyWord = request.getParameter("keyWord");
            String cat = request.getParameter("cat");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");


            TbkDgItemCouponGetResponse item = tbkService.getCouponItem(keyWord,cat , Long.parseLong(pageNo), Long.parseLong(pageSize));
            data.put("items",item.getResults());
            data.put("totalCount",item.getTotalResults());

        }

        catch (Exception e)
        {
            result = new ApiResult("F", "服务器繁忙！", null);
            LogUtil.error("", e);
        }finally{
            if(result == null){
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }


    /**
     * 商品查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getItemByKeyWord")
    @ResponseBody
    public String getItemByKeyWord(HttpServletRequest request, HttpServletResponse response){
        ApiResult result = null;
        String json = null;
        Map<String,Object> data = new HashMap<String,Object>();
        try
        {



            String keyWord = request.getParameter("keyWord");
            String isMall = request.getParameter("isMall");
            String pageNo = request.getParameter("pageNo");
            String pageSize = request.getParameter("pageSize");


            TbkItemGetResponse item = tbkService.getItem(keyWord, "1".equals(isMall) ? true : false, Long.parseLong(pageNo), Long.parseLong(pageSize));
            data.put("items",item.getResults());
            data.put("totalCount",item.getTotalResults());

        }

        catch (Exception e)
        {
            result = new ApiResult("F", "服务器繁忙！", null);
            LogUtil.error("", e);
        }finally{
            if(result == null){
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }


}
