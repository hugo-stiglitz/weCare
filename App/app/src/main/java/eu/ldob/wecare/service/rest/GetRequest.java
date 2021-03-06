package eu.ldob.wecare.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.ldob.wecare.entity.rest.AuthenticationToken;

public class GetRequest<Tresponse> {

    private ObjectMapper mapper = new ObjectMapper();

    private AuthenticationToken token;
    private Class<? extends Tresponse> typeParameterClass;

    protected GetRequest(AuthenticationToken token, Class<? extends Tresponse> typeParameterClass) {

        this.token = token;
        this.typeParameterClass = typeParameterClass;
    }

    protected void get(String urlString, RestListener<Tresponse> listener) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if(token != null) {
                httpConnection.setRequestProperty("Authorization", token.toString());
            }
            httpConnection.setRequestMethod("GET");

            if (httpConnection.getResponseCode() >= 200 && httpConnection.getResponseCode() < 300 ) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                Tresponse response = mapper.readValue(reader, typeParameterClass);

                listener.onRequestComplete(response);
            }
            else {
                listener.onError(new Error(httpConnection.getResponseCode() + " - " + httpConnection.getResponseMessage()));
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {
            listener.onError(new Error("OOOPS... Something went wrong, please try again"));
        } catch (IOException e) {
            listener.onError(new Error("OOOPS... Something went wrong, please try again"));
        }
    }
}
