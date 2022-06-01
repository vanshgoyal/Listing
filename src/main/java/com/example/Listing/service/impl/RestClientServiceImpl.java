package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.service.RestClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class RestClientServiceImpl implements RestClientService
{

    public PropertyDTO getPropertyDTO(String uri) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpGet httpget = new HttpGet(uri);

        //Printing the method used
        System.out.println("Request Type: " + httpget.getMethod());

        //Executing the Get request
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpget);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = null;
        try {
            sc = new Scanner(httpresponse.getEntity().getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Printing the status line
        //System.out.println(httpresponse.getStatusLine());
        //System.out.println(sc.nextLine());
        JsonNode productNode = null;
        try {
            productNode = new ObjectMapper().readTree(sc.nextLine());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPropertyId(productNode.get("data").get("id").textValue());
        propertyDTO.setType(productNode.get("data").get("type").textValue());
        propertyDTO.setDeposit(productNode.get("data").get("deposit").intValue());
        propertyDTO.setLatitude(productNode.get("data").get("latitude").floatValue());
        propertyDTO.setLongitude(productNode.get("data").get("longitude").floatValue());
        propertyDTO.setLeaseType(productNode.get("data").get("leaseType").textValue());
        propertyDTO.setParking(productNode.get("data").get("parking").textValue());
        propertyDTO.setFurnishing(productNode.get("data").get("furnishing").textValue());
        propertyDTO.setRent(productNode.get("data").get("rent").intValue());
        propertyDTO.setBuildingType(productNode.get("data").get("buildingType").textValue());
        return (propertyDTO);

    }

//    @Override
//    public PropertyDTO getPropertyDetails(String propertyId) {
//        return null;
//    }
}
