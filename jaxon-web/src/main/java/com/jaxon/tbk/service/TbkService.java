package com.jaxon.tbk.service;

import com.jaxon.exception.BusinessException;
import com.jaxon.tbk.entity.TbkItemConvertResponse;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;

public interface TbkService {


    /**
     * 商品查询
     * @param
     * @return
     * @throws BusinessException
     */
    public TbkItemGetResponse getItem(String keyWord, boolean isMall, Long pageNum, Long pageSize) throws BusinessException;

    /**
     * Tbk链接转化
     * 没有权限
     * @return
     * @throws BusinessException
     */
    public TbkItemConvertResponse getTbkUrl(String numIids) throws BusinessException;

    /**
     * 获取优惠券商品
     * @param keyWord
     * @param cat
     * @param pageNum
     * @param pageSize
     * @return
     */
    public TbkDgItemCouponGetResponse getCouponItem(String keyWord,String cat ,Long pageNum, Long pageSize) throws BusinessException;

    /**
     * 获取优惠券详情
     * @param me
     * @return
     * @throws BusinessException
     */
    public TbkCouponGetResponse getCouponDetail(String me)throws BusinessException;

    /**
     * 创建口令
     * @param userId
     * @param text
     * @param url
     * @param logoUrl
     * @param ext
     * @return
     * @throws BusinessException
     */
    public TbkTpwdCreateResponse createTbkPassword(String userId,String text,String url,String logoUrl,String ext) throws BusinessException;
}
