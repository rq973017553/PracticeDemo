package com.rq.practice.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

/**
 * HttpURLConnection的封装
 * @author rock you
 * @version 1.0.0
 */
public class HttpUtils {

    private static final int READ_TIME_OUT = 3*1000;

    private static final int CONNECT_TIME_OUT = 5*1000;

    private static final int CHUNKED_STREAMING_MODE = 8*1024;

    private static final String BOUNDARY = UUID.randomUUID().toString();
    private static final String PREFIX = "--";
    private static final String NEWLINE = "\r\n";

    /**
     * GET请求
     * @param url 请求访问的地址
     * @return {@link Response}
     */
    public static Response get(String url){
        HttpURLConnection connection = null;
        try {
            connection = createHttpURLConnection(url, false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();
            return disposeInputStream(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * POST请求
     * @param url 请求访问的地址
     * @param map key和value的map
     * @param <T> value的数据类型
     * @return {@link Response}
     */
    public static <T> Response post(String url, Map<String, T> map){
        HttpURLConnection connection = null;
        try {
            connection = createHttpURLConnection(url, false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            disposePost(outputStream, map);
            return disposeInputStream(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * upload上传文件
     * @param url url链接
     * @param map key和path(路径)存放的集合
     * @return {@link Response}
     */
    public static Response upload(String url, Map<String, String> map){

        HttpURLConnection connection = null;
        try {
            connection = createHttpURLConnection(url, false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(CHUNKED_STREAMING_MODE);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            disposeUpload(outputStream, map);
            return disposeInputStream(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件，可以上传多个文件
     * @param outputStream OutputStream
     * @param map key和path的组合
     * @throws IOException IO异常
     */
    private static void disposeUpload(OutputStream outputStream, Map<String, String> map) throws IOException {
        byte buf[] = new byte[1024];
        int len;
        DataOutputStream dos = new DataOutputStream(outputStream);
        FileInputStream fis = null;
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        try {
            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                if (isEmpty(key)){
                    continue;
                }
                String value = entry.getValue();
                File file = new File(value);
                if (!file.exists()){
                    continue;
                }
                String fileName = getFileName(value);
                dos.writeBytes(PREFIX+BOUNDARY+NEWLINE);
                dos.writeBytes("Content-Disposition:form-data; "+
                                    "name=\""+key+"\""+
                                    ";filename=\""+fileName+"\""+NEWLINE);
                System.out.println("Content-Disposition:form-data; "+
                        "name=\""+key+"\""+
                        ";filename=\""+fileName+"\""+NEWLINE);
                dos.writeBytes(NEWLINE);
                fis = new FileInputStream(file);
                while ((len = fis.read(buf)) != -1){
                    dos.write(buf, 0, len);
                }
                dos.writeBytes(NEWLINE);
            }
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE);
        } finally {
            close(dos);
        }
    }

    /**
     * 处理OutputStream方法(POST)
     * @param outputStream OutputStream
     * @param map key和value的map集合
     * @param <T> value的类型
     * @throws IOException IO异常
     */
    private static <T> void disposePost(OutputStream outputStream, Map<String, T> map) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while (iterator.hasNext()){
                String line;
                Map.Entry<String, T> entry = iterator.next();
                String key = entry.getKey();
                if (isEmpty(key)){
                    continue;
                }
                String value;
                T object = entry.getValue();
                if (object != null){
                    value = String.valueOf(object);
                }else {
                    value = "";
                }
                line = key+"="+URLEncoder.encode(value, "UTF-8");
                stringBuilder.append(line);
                if (iterator.hasNext()){
                    stringBuilder.append("&");
                }
            }
            dos.writeBytes(stringBuilder.toString());
        } finally {
            close(dos);
        }
    }

    /**
     * 处理InputStream
     * @param connection HttpURLConnection对象
     * @return {@link Response}
     */
    private static Response disposeInputStream(HttpURLConnection connection) throws IOException{
        Response response = new Response();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = connection.getInputStream();
        int responseCode;
        try {
            responseCode = connection.getResponseCode();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            response.setResponseCode(responseCode);
            response.setResult(stringBuilder.toString());
            return response;
        }finally {
            close(bufferedReader);
        }
    }

    private static String getFileName(String path){
        String fileName;
        fileName = path.substring(path.lastIndexOf("/")+1);
        return fileName;
    }

    /**
     * 判断字符串是否为null或者是否为空字符串
     * @param str 传入的字符串
     * @return 返回boolean判断值
     */
    private static boolean isEmpty(CharSequence str) {
         return TextUtils.isEmpty(str); // Android版本
//        return str == null || str.length() == 0; // Java版本
    }

    /**
     * 创建HttpURLConnection或者HttpsURLConnection
     * @param actionURL HTTP URL
     * @param isHttps 判断是https还是http协议
     * @return HttpURLConnection或者HttpsURLConnection对象
     * @throws IOException IO异常
     */
    private static HttpURLConnection createHttpURLConnection(String actionURL, boolean isHttps) throws IOException{
        URL url;
        HttpURLConnection connection;
        if (isEmpty(actionURL)){
            throw new IllegalArgumentException("actionURL is null or empty!");
        }
        url = new URL(actionURL);
        if (isHttps){
            connection = (HttpsURLConnection) url.openConnection();
        }else {
            connection =  (HttpURLConnection) url.openConnection();
        }
        connection.setReadTimeout(READ_TIME_OUT);
        connection.setConnectTimeout(CONNECT_TIME_OUT);
        return connection;
    }

    /**
     * 通用close
     * @param closeable Closeable接口所有IO流的接口
     */
    private static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * HTTP请求结果的封装
     */
    public static class Response{
        private int mResponseCode = -1;

        private String mResult = null;

        /**
         * 设置请求后返回的状态码
         * @param responseCode 状态码
         */
        public void setResponseCode(int responseCode) {
            this.mResponseCode = responseCode;
        }

        /**
         * 获取请求返回的状态码
         * @return 状态码
         */
        public int getResponseCode() {
            return mResponseCode;
        }

        /**
         * 设置请求的结果
         * @param result 请求结果
         */
        public void setResult(String result) {
            this.mResult = result;
        }

        /**
         * 获取请求后的结果
         * @return 请求结果
         */
        public String getResult() {
            return mResult;
        }
    }
}
