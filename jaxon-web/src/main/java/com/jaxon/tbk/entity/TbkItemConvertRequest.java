package com.jaxon.tbk.entity;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.response.TbkItemGetResponse;

import java.util.Map;

public class TbkItemConvertRequest extends BaseTaobaoRequest<TbkItemConvertResponse> {

    private static final long serialVersionUID = 3285463913662772539L;

    /**
     * 需返回的字段列表
     */
    private String fields;

    private String numIids;

    private Long adzoneId;

    /**
     * 链接形式：1：PC，2：无线，默认：１
     */
    private Long platform;

    private String unid;

    private String dx;


    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getNumIids() {
        return numIids;
    }

    public void setNumIids(String numIids) {
        this.numIids = numIids;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public Long getPlatform() {
        return platform;
    }

    public void setPlatform(Long platform) {
        this.platform = platform;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public String getApiMethodName() {
        return "taobao.tbk.item.convert";
    }

    public Map<String, String> getTextParams() {
        TaobaoHashMap txtParams = new TaobaoHashMap();
        txtParams.put("fields", this.fields);
        txtParams.put("num_iids",this.numIids);
        txtParams.put("adzone_id",this.adzoneId);
        txtParams.put("platform",this.platform);
        txtParams.put("unid",this.unid);
        txtParams.put("dx",this.dx);
        if(this.udfParams != null) {
            txtParams.putAll(this.udfParams);
        }
        return txtParams;
    }

    public Class<TbkItemConvertResponse> getResponseClass() {
        return TbkItemConvertResponse.class;
    }

    public void check() throws ApiRuleException {
        RequestCheckUtils.checkNotEmpty(fields, "fields");
    }
}
