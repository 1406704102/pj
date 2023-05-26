package com.pangjie.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager pool;

    public void init() {
        //创建http连接池，可以同时指定连接超时时间
        pool = new PoolingHttpClientConnectionManager(60000, TimeUnit.MILLISECONDS);
        //最多同时连接800个请求
        pool.setMaxTotal(800);
        //每个路由最大连接数，路由指IP+PORT或者域名
        pool.setDefaultMaxPerRoute(400);

    }

    public static CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager pool) {
        //设置请求参数配置，创建连接时间、从连接池获取连接时间、数据传输时间、是否测试连接可用、构建配置对象
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(10 * 1000)
                .setStaleConnectionCheckEnabled(true)
                .build();

        //创建httpClient时从连接池中获取，并设置连接失败时自动重试（也可以自定义重试策略：setRetryHandler()）
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(pool)
                .disableAutomaticRetries()
                .build();
        return httpClient;
    }

    public static String postData(String url, HashMap<String, Object> entityMap, HashMap<String, Object> heardMap) {

        HttpPost post = new HttpPost(url);
        String result = "";
        try (CloseableHttpClient httpClient = getHttpClient(pool)) {
            // HttpEntity entity = new StringEntity(jsonStrData);
            // 修复 POST json 导致中文乱码
            HttpEntity entity = new StringEntity(entityMap.toString(), "UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            post.setHeader("AppID", heardMap.get("appId").toString());
            post.setHeader("PublicKey", heardMap.get("publicKey").toString());
            post.setHeader("TimeStamp", heardMap.get("timeStamp").toString());
            post.setHeader("Sign", heardMap.get("sign").toString());
            CloseableHttpResponse resp = httpClient.execute(post);
            InputStream respIs = resp.getEntity().getContent();
            byte[] respBytes = IOUtils.toByteArray(respIs);
            result = new String(respBytes, Charset.forName("UTF-8"));
            return result;
        } catch (IOException e) {
            //关闭不为200的连接
            post.abort();
            e.printStackTrace();
        }
        return result;
    }



    public static String getData(String url) {
        /**
         * @Author PangJie___
         * @Description
         * @Date 10:23 2023/5/26
         * @param * @param url
         * @return * @return {@link String }
         */
        CloseableHttpClient httpClient = getHttpClient(pool);
        String result = "";
        // 创建httpGet远程连接实例
        HttpGet httpGet = new HttpGet(url);
        try {
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = execute.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
            //关闭不为200的连接
            httpGet.abort();
        }
        return result;
    }
}
