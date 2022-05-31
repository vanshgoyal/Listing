package com.example.Listing.service;

import com.example.Listing.dto.PropertyDTO;

public interface RestClient {
    void get(String uri) throws Exception;
    PropertyDTO getPropertyDetails(String propertyId);
}
