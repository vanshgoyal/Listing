package com.example.Listing.service;

import com.example.Listing.dto.PropertyDTO;

public interface RestClientService {
    PropertyDTO getPropertyDTO(String propertyId);
    // accep parameters required to formulate the reuests
}
