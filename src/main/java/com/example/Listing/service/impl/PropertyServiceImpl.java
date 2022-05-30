package com.example.Listing.service.impl;

import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ragcrix
 */
@Service
public class  PropertyServiceImpl implements PropertyService {

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public mass_model saveOrUpdateProperty(Property property) {
        mass_model property_mass = new mass_model();
        property_mass.setId(property.getId());
        property_mass.setPropertyId(property.getPropertyId());
        property_mass.setMassVal(property.getDeposit()* property.getLeaseType()* property.getPhotoCount());
        return propertyRepository.save(property_mass);
    }

    @Override
    public mass_model findBypropertyId(String propertyId) {

        return propertyRepository.findBypropertyId(propertyId);
    }

}
