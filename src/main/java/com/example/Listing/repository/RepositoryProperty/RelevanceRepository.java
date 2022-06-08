package com.example.Listing.repository.RepositoryProperty;

import com.example.Listing.model.RelevanceScore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelevanceRepository extends MongoRepository<RelevanceScore, String> {

    RelevanceScore findBypropertyId(String propertyId);
}

