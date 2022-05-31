package com.example.Listing.service;

import com.example.Listing.model.PropertyModel;
import com.example.Listing.model.MassModel;

public interface PropertyService {

    MassModel saveOrUpdateProperty(PropertyModel property, String city_id);

    MassModel findBypropertyId(String propertyId);
}
