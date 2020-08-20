package com.octanner.pdfsso.service;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.octanner.pdfsso.config.ApplicationConfig;
import com.octanner.pdfsso.service.object.AuthToken;
import com.octanner.pdfsso.service.object.Identity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
@AllArgsConstructor
public class IdentityInfoService {

    private final ApplicationConfig applicationConfig;

    public Identity callGraphQL(String identityUuid){
        HttpPost httpPost = new HttpPost(applicationConfig.getGraphQlUrl());
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> uniResponse = null;
        try {
            uniResponse = Unirest.post("https://core-qa.octanner.io/sso/oauth/token")
                    .header("Content-Type", "application/json")
                    .body("{\n  \"grant_type\": \"client_credentials\",\n  \"client_id\": \"" +
                            applicationConfig.getClientId() + "\",\n  \"client_secret\": \"" +
                            applicationConfig.getClientSecret() + "\"\n}")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        AuthToken authToken = gson.fromJson(uniResponse.getBody(), AuthToken.class);
        httpPost.addHeader("Authorization", "Bearer " + authToken.getAccess_token());
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("query",
                "{identity(id: \"" + "" + identityUuid + "" + "\"){\n" +
                        "  firstName\n" +
                        "  lastName\n" +
                        "  loginId\n" +
                        "  uniqueId\n" +
                        "  customerId\n" +
                        "  id\n" +
                        "}}");

        try {
            StringEntity entity = new StringEntity(jsonObj.toString());
            httpPost.setEntity(entity);
            response = client.execute(httpPost);

        }
        catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        try {
            assert response != null;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
                String line = null;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {

                    builder.append(line);

                }

                JSONObject jsonResponse = new JSONObject(builder.toString());
                return gson.fromJson(jsonResponse.getJSONObject("data").getJSONObject("identity").toString(), Identity.class);
            }
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
