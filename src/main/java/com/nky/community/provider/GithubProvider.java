package com.nky.community.provider;

import com.alibaba.fastjson.JSON;
import com.nky.community.dto.AccessTokenDTO;
import com.nky.community.dto.GithubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther:nky
 * @Date:2019/10/9
 * @Description:com.nky.community.provider
 * @version:1.0
 *
 * 使用okHttp发起请求向GitHub的oauth/access/access_token接口，拿到token字符串
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        // 请求的类型和字符集
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        // 实例化一个client对象
        OkHttpClient client = new OkHttpClient();
        // 请求体
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        // 执行请求，拿到并返回token
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据拿到的token
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;

    }

}
