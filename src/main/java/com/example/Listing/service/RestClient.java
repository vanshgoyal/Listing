package com.example.Listing.service;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.PropertyModel;

public interface RestClient {
     PropertyDTO get(String uri) throws Exception;
    PropertyDTO getPropertyDetails(String propertyId);
}
