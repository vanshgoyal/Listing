package com.example.Listing.repository.RepositoryProperty;

import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<mass_model, String> {

    mass_model findBypropertyId( String propertyId);
}
