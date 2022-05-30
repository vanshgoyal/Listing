package com.example.Listing.service;

import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;

import java.util.List;

public interface PropertyService {

    mass_model saveOrUpdateProperty(Property property);

    mass_model findBypropertyId( String propertyId);
}
