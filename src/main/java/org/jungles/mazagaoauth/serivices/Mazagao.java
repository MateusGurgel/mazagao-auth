package org.jungles.mazagaoauth.serivices;

import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jungles.mazagaoauth.config.Config;

public class Mazagao {
    public static boolean login(String username, String password){

        try{

            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);

            HttpPost httpPost = new HttpPost(Config.API_ENDPOINT + "/auth/playerSignIn");
            httpPost.setEntity(entity);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity == null) {
                return false;
            }

            return statusCode == 200 && EntityUtils.toString(responseEntity).contains("accessToken");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return false;
    }
}
