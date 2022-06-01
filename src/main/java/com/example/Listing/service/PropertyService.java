package com.example.Listing.service;

import com.example.Listing.model.MassModel;

public interface PropertyService {

    MassModel calculatePropertyScore(String cityId, String propertyId) throws Exception;

    MassModel findScoreBypropertyId(String propertyId);
}
