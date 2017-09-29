package com.jaxon.exception;

/**
 * 业务异常，用于标识哪些业务环节有问题
 *
 * 系统异常也会通过封装，上升成为业务异常
 */
public class BusinessException extends Exception
{

    /**
     * 序列化ID
     */
     private static final long serialVersionUID = 1L;
     
     public BusinessException()
     {
         super();
     }

     public BusinessException(String msg)
     {
         super(msg);
     }
}
