package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @jiguiyang
 * 接口的GET请求
 */
public class HttpGet {
    /**
     * @param generalUrl 请求url
     * @param params 请求参数
     * @return
     */
    public static String getURL(String generalUrl, String params) {
        String authHost = generalUrl+params;
        try {
            URL realUrl = new URL(authHost);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            System.err.println("result:" + result);
            return result;
        } catch (Exception e) {
            System.err.printf("获取失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
