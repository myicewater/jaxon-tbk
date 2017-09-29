package com.jaxon.util.htmlto;


import org.apache.log4j.Logger;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class HtmlToPdfUtil {

    private static final Logger log = Logger.getLogger(HtmlToPdfUtil.class);

    //wkhtmltopdf在系统中的路径
    private static final String winPdfTool = "/home/gooagoo/bin/wkhtmltopdf/bin/wkhtmltopdf";
    private static final String linuxPdfTool = "/home/gooagoo/bin/wkhtmltox/bin/wkhtmltopdf";


    /**
     * html转pdf
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param filePath pdf文件名(无需后缀)
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String filePath,int goodsCount) {
        long begin = System.currentTimeMillis();
        File file = new File(filePath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }

        StringBuilder cmd = new StringBuilder();
        cmd.append(getBinName());
        cmd.append(" ");
//        cmd.append("--margin-bottom 0mm ");//页面格式设置要放在src之前
//        cmd.append("--margin-top 0mm ");
        cmd.append("--allow ");
        String allowPath = new File(srcPath).getParent();
        cmd.append(allowPath);
        cmd.append(" ");
        double height = 115 +5.4*goodsCount;
        cmd.append("-L 1mm -R 1mm -T 1mm -B 1mm --page-width 80mm --page-height "+height+"mm ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(filePath);
        cmd.append(" ");

        log.info("executing cmd: " + cmd.toString());
        System.out.println("executing cmd: " + cmd.toString());
        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            proc.getInputStream().close();
            proc.getErrorStream().close();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("pdfUtil takes " + (end - begin) + " ms.");
        log.info("pdfUtil takes " + (end - begin) + " ms.");
        return result;
    }

    private static String getBinName() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return winPdfTool;
        }
        return linuxPdfTool;
    }


}
