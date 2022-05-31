package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.service.RestClient;
import com.example.Listing.utils.ObjectMapperUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class RestClientServiceImpl implements RestClient
{

    public PropertyDTO get(String uri) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpGet httpget = new HttpGet("https://www.nobroker.in/api/v1/property/ff8081815470b1de015476e59b036948");

        //Printing the method used
        System.out.println("Request Type: " + httpget.getMethod());

        //Executing the Get request
        HttpResponse httpresponse = httpclient.execute(httpget);

        Scanner sc = new Scanner(httpresponse.getEntity().getContent());

        //Printing the status line
        //System.out.println(httpresponse.getStatusLine());
        //System.out.println(sc.nextLine());
        JsonNode productNode = new ObjectMapper().readTree(sc.nextLine());
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

    @Override
    public PropertyDTO getPropertyDetails(String propertyId) {
        return null;
    }

//    @Override
//    public PropertyDTO getPropertyDetails(String propertyId) {
//        return null;
//    }
}
