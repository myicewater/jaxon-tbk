package com.jaxon.util.htmlto;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 当java调用wkhtmltopdf时，用于获取wkhtmltopdf返回的内容
 *  必须开启此子线程消耗输入流中的内容，否则主线程会阻塞
 */
public class HtmlToPdfInterceptor extends Thread {

    private static final Logger log = Logger.getLogger(HtmlToPdfInterceptor.class);

    private InputStream is;

    public HtmlToPdfInterceptor(InputStream is) {
        this.is = is;
    }

    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                log.info(line.toString());
                System.out.println(line.toString()); //输出内容

            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HTML转pdf异常！",e);
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("HTML转pdf异常！",e);
                }
            }
        }
    }

}