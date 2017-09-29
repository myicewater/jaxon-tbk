package com.jaxon.tbk.service;

import com.alibaba.fastjson.JSONObject;
import com.jaxon.exception.BusinessException;
import com.jaxon.log.LogUtil;
import com.jaxon.tbk.entity.TbkItemConvertRequest;
import com.jaxon.tbk.entity.TbkItemConvertResponse;
import com.jaxon.util.TFUtil;
import com.jaxon.util.property.PropertyUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkCouponGetRequest;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import org.springframework.stereotype.Service;

@Service("tbkService")
public class TbkServiceImpl implements TbkService {

    private static final String url = "https://eco.taobao.com/router/rest";
    private static final String appkey = "24380579";
    private static final String secret = "e0ba3ba30ca72f0049ac889dfa2e8426";
    private static final String adzone_id = "112236291";//mm_122491899_29350141_112236291 账户ID：122491899  网站id：29350141



    public TbkItemGetResponse getItem(String keyWord, boolean isMall, Long pageNo, Long pageSize) throws BusinessException{

        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);

        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ(keyWord);//关键字
        req.setCat("");//商品类目
        req.setItemloc("");
        req.setSort("tk_rate_des");
        req.setIsTmall(isMall);
        req.setIsOverseas(false);
        req.setStartPrice(Long.parseLong(PropertyUtil.getProperty("startPrice")));//折扣范围下限
        req.setEndPrice(Long.parseLong(PropertyUtil.getProperty("endPrice")));//折扣范围上限
        //req.setStartTkRate(123L);//佣金比例上限
        //req.setEndTkRate(123L);//佣金比例下限
        //req.setPlatform(1L);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);

        TbkItemGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LogUtil.error("调用查询商品接口异常！",e);
            throw new BusinessException("调用查询商品接口异常！");
        }

        return rsp;
    }


    public TbkItemConvertResponse getTbkUrl(String numIids) throws BusinessException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkItemConvertRequest req = new TbkItemConvertRequest();
        req.setFields("num_iid,click_url");
        req.setNumIids(numIids);
        req.setAdzoneId(Long.parseLong(adzone_id));
        //req.setPlatform(123L);
        req.setUnid("huilegogo");
        req.setDx("1");
        TbkItemConvertResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LogUtil.error("转换链接接口异常！",e);
            throw new BusinessException("转换链接接口异常！");
        }
        return rsp;
    }

    public TbkDgItemCouponGetResponse getCouponItem(String keyWord, String cat, Long pageNum, Long pageSize) throws BusinessException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
        req.setAdzoneId(Long.parseLong(adzone_id));
        //req.setPlatform(1L);
        if(TFUtil.isNotEmpty(cat)){

            req.setCat(cat);
        }
        req.setPageSize(pageSize);
        req.setQ("女装");
        req.setPageNo(pageNum);
        TbkDgItemCouponGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LogUtil.error("调用查询优惠券商品接口异常！",e);
            throw new BusinessException("调用查询优惠券商品接口异常！");
        }

        return rsp;
    }

    public TbkCouponGetResponse getCouponDetail(String me) throws BusinessException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkCouponGetRequest req = new TbkCouponGetRequest();
        req.setMe(me);
        TbkCouponGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LogUtil.error("调用查询优惠券详情接口异常！",e);
            throw new BusinessException("调用查询优惠券详情接口异常！");
        }
        return rsp;
    }

    public TbkTpwdCreateResponse createTbkPassword(String userId, String text, String url, String logoUrl, String ext) throws BusinessException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        if(TFUtil.isNotEmpty(userId)){

            req.setUserId(userId);
        }
        req.setText(text);//口令弹框内容，长度大于5个字符
        req.setUrl(url);//口令跳转地址
        req.setLogo(logoUrl);//口令弹框logoURL
        req.setExt(ext);//扩展字段json格式
        TbkTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LogUtil.error("调用创建口令接口异常！",e);
            throw new BusinessException("调用创建口令接口异常！");
        }

        return rsp;
    }
}
