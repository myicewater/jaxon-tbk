package com.jaxon.tbk.entity;

import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import java.util.List;

public class TbkItemConvertResponse extends TaobaoResponse {

    private static final long serialVersionUID = 3285463913662772539L;


    /**
     * 淘宝客商品
     */
    @ApiListField("results")
    @ApiField("n_tbk_item")
    private List<NTbkItem> results;

    public List<NTbkItem> getResults() {
        return results;
    }

    public void setResults(List<NTbkItem> results) {
        this.results = results;
    }
}
