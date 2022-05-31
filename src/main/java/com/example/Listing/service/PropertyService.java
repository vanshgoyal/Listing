package com.example.Listing.service;

import com.example.Listing.model.MassModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

public interface PropertyService {

    MassModel saveOrUpdateProperty(String cityId,String propertyId) throws JsonProcessingException, ParseException;

    MassModel findBypropertyId(String propertyId);
}
