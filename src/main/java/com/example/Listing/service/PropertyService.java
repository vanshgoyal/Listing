package com.example.Listing.service;

import com.example.Listing.model.MassModel;

public interface PropertyService {

    MassModel saveOrUpdatePropertyScore(String cityId, String propertyId) throws Exception;

    MassModel findBypropertyId(String propertyId);
}
