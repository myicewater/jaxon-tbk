package com.jaxon.util.asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.jaxon.exception.BusinessException;
import org.apache.log4j.Logger;

/**
 *
 * 字符串，对象等判断工具类
 *
 * 当判断跟预期不相符时，抛出业务异常
 */
public class AssertUtil
{
    private final static Logger log = Logger.getLogger(AssertUtil.class);

    public static void isTrue(boolean expression, String msg) throws BusinessException
    {
        if (!expression)
        {
            log.error("【表达式为false异常】：" + msg);
            throw new BusinessException(msg);
        }
    }

    public static void isNotNull(Object object, String logInfo) throws BusinessException
    {
        if (object == null)
        {
            log.error("【对象为空异常】：" + logInfo);
            throw new BusinessException(logInfo);
        }
    }

    public static void isNotBlank(String str, String logInfo) throws BusinessException
    {
        if (str == null || str.trim().length() == 0)
        {
            log.error("【字符串为空异常】：" + logInfo);
            throw new BusinessException(logInfo);
        }
    }

    public static void isNotEmpty(Collection coll, String logInfo) throws BusinessException
    {
        if (coll == null || coll.size() < 1)
        {
            log.error("【集合为空异常】：" + logInfo);
            throw new BusinessException(logInfo);
        }
    }
    
   

    public static void main(String[] args) throws BusinessException
    {
        AssertUtil.isNotBlank("   ", "空空");
        AssertUtil.isTrue(true, "真这家");
        AssertUtil.isNotNull("", "nulllll");
        List l = new ArrayList();
        AssertUtil.isNotEmpty(l, "没有东西");
    }

}
