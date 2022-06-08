package com.example.Listing.repository.RepositoryProperty;

import com.example.Listing.model.QualityScore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<QualityScore, String> {

    QualityScore findBypropertyId(String propertyId);
}

