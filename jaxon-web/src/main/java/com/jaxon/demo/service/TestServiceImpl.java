package com.jaxon.demo.service;

import com.jaxon.log.LogUtil;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

    public void test() {
        LogUtil.info("test service.");
    }
}
