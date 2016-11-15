package eu.ldob.wecare.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.ldob.wecare.entity.rest.AuthenticationToken;

public class PostRequest<Trequest, Tresponse> {

    private ObjectMapper mapper = new ObjectMapper();

    private AuthenticationToken token;
    private Class<? extends Tresponse> typeParameterClassResponse;

    protected PostRequest(AuthenticationToken token, Class<? extends Tresponse> typeParameterClassResponse) {

        this.token = token;
        this.typeParameterClassResponse = typeParameterClassResponse;
    }

    protected void post(String urlString, RestListener<Tresponse> listener, Trequest requestObject) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if(token != null) {
                httpConnection.setRequestProperty("Authorization", token.toString());
            }
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);

            DataOutputStream outStream = new DataOutputStream(httpConnection.getOutputStream());
            mapper.writeValue(outStream, requestObject);
            outStream.flush();
            outStream.close();

            if (httpConnection.getResponseCode() >= 200 && httpConnection.getResponseCode() < 300 ) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                Tresponse response = mapper.readValue(reader, typeParameterClassResponse);

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
