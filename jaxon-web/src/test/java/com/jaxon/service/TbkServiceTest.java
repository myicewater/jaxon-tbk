package com.jaxon.service;

import com.alibaba.fastjson.JSONObject;
import com.jaxon.base.SpringJUnit4;
import com.jaxon.exception.BusinessException;
import com.jaxon.tbk.entity.TbkItemConvertResponse;
import com.jaxon.tbk.service.TbkService;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TbkServiceTest extends SpringJUnit4 {

    @Autowired
    private TbkService tbkService;

    @Test
    public void getItemTest() throws BusinessException {
        TbkItemGetResponse response = tbkService.getItem("女装", false, 1L, 5L);
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void getTbkUrlTest()throws  BusinessException{
        TbkItemConvertResponse tbkUrl = tbkService.getTbkUrl("557450294840");
        System.out.println(JSONObject.toJSONString(tbkUrl));
    }

    @Test
    public void getCouponItemTest() throws  BusinessException{
        TbkDgItemCouponGetResponse 女装 = tbkService.getCouponItem("女装", "", 1L, 20L);
        System.out.println(JSONObject.toJSONString(女装));
    }

    @Test
    public void getCouponDetailTest() throws  BusinessException{
        TbkCouponGetResponse couponDetail = tbkService.getCouponDetail("eK9tZZMzoXUGQASttHIRqUJhb0evyhjVwzMnJ7Yh25jeQisExutkD%2B7%2Fvs6PcMc4tkiapbyvo%2FOWcbHphBpPCZJTbpqQTi6FDfqEFBOhTcwCwnsioheyR9spo5v3YuwYsr1MS13h%2B6fciqrKK46fd2Pfrr0N2WBeUEHnA%2FqswL%2Fk92%2BM7h46c6J7%2BkHL3AEW");
        System.out.println(JSONObject.toJSONString(couponDetail));
    }
}
