package com.example.Listing.service;

import com.example.Listing.dto.PropertyDTO;

public interface RestClientService {
     PropertyDTO getPropertyDTO(String uri) throws Exception;
}
