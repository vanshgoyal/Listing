package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.exception.CustomException;
import com.example.Listing.service.RestClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Service
public class RestClientServiceImpl implements RestClientService
{
    static Logger logger = LoggerFactory.getLogger(RestClientServiceImpl.class);
    public PropertyDTO getPropertyDTO(String propertyId) {

        String uri = "https://www.nobroker.in/api/v1/property/"+propertyId;
        Scanner sc;
        try {
             sc = apiCallGet(uri);
        }
        catch (Exception e){
            logger.error("Rest Client Connection error");
            throw new RuntimeException("Rest Client Connection error " + e.getMessage());
        }
        Gson gson=new Gson();
        String json = sc.nextLine();
        logger.error(json);
        if(json.length() <400 ){
            logger.error("Given Property id is not available, please send correct id to be searched ");
            throw new java.util.NoSuchElementException("Given Property id is not available, please send correct id to be searched ");
        }
        JsonObject body = gson.fromJson(json, JsonObject.class);
        String newstr = (body.get("data").toString());
        JsonObject body2 = gson.fromJson(newstr, JsonObject.class);
        int photos = body2.getAsJsonArray("photos").size();
        PropertyDTO propertyDTO = new PropertyDTO();
        try {
            propertyDTO = new ObjectMapper().readValue(newstr,PropertyDTO.class);
            propertyDTO.setNumberOfPhotos(photos);

        } catch (JsonProcessingException e) {
            logger.error("Problem in parsing json");
            throw new RuntimeException(e);
        }
        propertyDTO.setPropertyId(propertyId);

        return (propertyDTO);

    }
    private static Scanner apiCallGet(String uri)
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(uri);
        HttpResponse httpresponse = null;

        try {
            httpresponse = httpclient.execute(httpget);
        } catch (IOException e) {
            logger.error("Unable to get property params");
            throw new RuntimeException(e);
        }
        Scanner sc = null;
        Scanner k =null;
        try {
            sc = new Scanner(httpresponse.getEntity().getContent());
        } catch (IOException e) {
            logger.error("Unable to get response from server ");
            throw new RuntimeException(e);
        }
        return sc;
    }
    private static void apiCallPost(String uri, String propertyId, String Score)throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("propertyID", propertyId));
        params.add(new BasicNameValuePair("Score", Score));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }
//    private static Scanner apiCallGet(String uri)
//    {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpget = new HttpGet(uri);
//        HttpResponse httpresponse = null;
//
//        try {
//            httpresponse = httpclient.execute(httpget);
//        } catch (IOException e) {
//            logger.error("Unable to get property params");
//            throw new RuntimeException(e);
//        }
//        Scanner sc = null;
//        try {
//            sc = new Scanner(httpresponse.getEntity().getContent());
//        } catch (IOException e) {
//            logger.error("Unable to get response from server ");
//            throw new RuntimeException(e);
//        }
//        return sc;
//    }
}
