package com.lznby.baidumapdemo.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 登录请求使用
 */
public class HttpUtilsHttpURLConnection {

    public static String BASE_URL= "http://192.168.1.2:8080";
    /**
     * urlStr:网址
     * parms：提交数据
     * return:网页源码
     */
    public static  String getContextByHttp(String urlStr){
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            /*connection.setRequestMethod("POST");*/
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);

/*            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            writer.write(getStringFromOutput(parms));

            writer.flush();
            writer.close();
            outputStream.close();*/

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp;
                while((temp = reader.readLine()) != null){
                    sb.append(temp);
                }
                reader.close();
            }else{
                return "connection error:" + connection.getResponseCode();
            }

            connection.disconnect();
        }catch (Exception e){
            return e.toString();
        }
        return sb.toString();
    }

    /**
     * 将map转换成key1=value1&key2=value2的形式
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getStringFromOutput(Map<String,String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        for(Map.Entry<String,String> entry:map.entrySet()){
            if(isFirst)
                isFirst = false;
            else
                sb.append("&");

            sb.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return sb.toString();
    }
}