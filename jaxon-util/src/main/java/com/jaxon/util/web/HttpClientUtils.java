package com.jaxon.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class HttpClientUtils
{
    private static Logger log = Logger.getLogger("gaglog");

    /**
     * 通过get方式访问
     * @param url 访问地址
     * @return
     * @throws Exception
     */
    public static String httpGet(String url) throws Exception
    {
        String line = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext httpContext = new BasicHttpContext();

            HttpGet get = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(get, httpContext);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null)
            {
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                line = reader.readLine();
                in.close();
            }
            httpClient.getConnectionManager().shutdown();//关闭httpclient连接
            httpClient.getConnectionManager().shutdown();
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问编码格式异常");
        }
        catch (ClientProtocolException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问客服端协议异常");
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问IO异常");
        }

        return line;
    }

    public static String HttpPost(String url, List<NameValuePair> paramlist) throws Exception
    {
        String line = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(new UrlEncodedFormEntity(paramlist, "UTF-8"));

            System.out.println("url:" + url + "--" + paramlist);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null)
            {
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                line = reader.readLine();
                in.close();
            }
            httpClient.getConnectionManager().shutdown();//关闭httpclient连接
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问编码格式异常");
        }
        catch (ClientProtocolException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问客服端协议异常");
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问IO异常");
        }

        return line;
    }

    public static String HttpPost(String url, final HttpEntity reqEntity) throws Exception
    {
        String line = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(reqEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null)
            {
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                line = reader.readLine();
                in.close();
            }
            httpClient.getConnectionManager().shutdown();//关闭httpclient连接
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问编码格式异常");
        }
        catch (ClientProtocolException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问客服端协议异常");
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            throw new Exception("httpClient访问IO异常");
        }

        return line;
    }

}
