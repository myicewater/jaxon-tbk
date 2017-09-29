package com.jaxon.base;


import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:ApplicationContext.xml" })
public class SpringJUnit4 extends AbstractJUnit4SpringContextTests
{

    @Autowired
    public ServletContext context;

    public MockMvc mockMvc;


    /**
     * &#x5355;&#x5143;&#x6d4b;&#x8bd5;&#x5171;&#x540c;&#x65b9;&#x6cd5;
     * @param path &#x6d4b;&#x8bd5;url
     * @param param &#x53c2;&#x6570;
     * @param instance controller&#x5b9e;&#x4f8b;
     * @return
     * @throws Exception
     */
    public String commonTest(String path, Map<String, String> param, Object instance) throws Exception
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(instance).build();
        MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(path).accept(MediaType.APPLICATION_JSON);
        if (param != null)
        {
            Set<String> keySet = param.keySet();
            for (String key : keySet)
            {
                accept.param(key, param.get(key));
            }
        }

        ResultActions resultActions = this.mockMvc.perform(accept);
        MvcResult mvcResult = resultActions.andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        String result = mvcResult.getResponse().getContentAsString();
        return result;
    }

    /**
     *
     * @param path
     * @param param
     * @param instance
     * @param cookieMap
     * @return
     * @throws Exception
     */
    public String commonTest(String path, Map<String, String> param, Object instance, Map<String, String> cookieMap) throws Exception
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(instance).build();
        MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(path).accept(MediaType.APPLICATION_JSON);
        if (param != null)
        {
            Set<String> keySet = param.keySet();
            for (String key : keySet)
            {
                accept.param(key, param.get(key));
            }
        }

        if (cookieMap != null)
        {
            Set<String> keySet2 = cookieMap.keySet();
            Cookie[] cookies = new Cookie[keySet2.size()];
            int i = 0;
            for (String key : keySet2)
            {
                Cookie cookie = new Cookie(key, cookieMap.get(key));
                cookies[i++] = cookie;
            }
            accept.cookie(cookies);
        }

        ResultActions resultActions = this.mockMvc.perform(accept);
        MvcResult mvcResult = resultActions.andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        String result = mvcResult.getResponse().getContentAsString();
        return result;
    }
    
    public <T> T getBean(Class<T> type)
    {
        return this.applicationContext.getBean(type);
    }

    public Object getBean(String beanName)
    {
        return this.applicationContext.getBean(beanName);
    }

    protected ApplicationContext getContext()
    {
        return this.applicationContext;
    }
}