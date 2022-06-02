package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.service.RestClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RestClientServiceImpl implements RestClientService
{
    static Logger logger = LoggerFactory.getLogger(RestClientServiceImpl.class);
    public PropertyDTO getPropertyDTO(String propertyId) {

        String uri = "https://www.nobroker.in/api/v1/property/"+propertyId;
        Scanner sc = apiCall(uri);

        Gson gson=new Gson();
        String json = sc.nextLine();
        JsonObject body = gson.fromJson(json, JsonObject.class);
        String newstr = (body.get("data").toString());
        JsonObject body2 = gson.fromJson(newstr, JsonObject.class);
        int photos = body2.getAsJsonArray("photos").size();
        PropertyDTO propertyDTO = new PropertyDTO();
        try {
            propertyDTO = new ObjectMapper().readValue(newstr,PropertyDTO.class);
            propertyDTO.setNumberOfPhotos(photos);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        propertyDTO.setPropertyId(propertyId);

        return (propertyDTO);

    }
    private static Scanner apiCall(String uri)
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
        try {
            sc = new Scanner(httpresponse.getEntity().getContent());
        } catch (IOException e) {
            logger.error("Unable to get response from property ");
            throw new RuntimeException(e);
        }
        return sc;
    }
}
