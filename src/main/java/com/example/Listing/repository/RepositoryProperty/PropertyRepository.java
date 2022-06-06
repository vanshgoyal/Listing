package com.example.Listing.repository.RepositoryProperty;

import com.example.Listing.model.MassModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<MassModel, String> {

    MassModel findBypropertyId(String propertyId);
}

