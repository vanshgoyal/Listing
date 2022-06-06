package com.example.Listing.service;

import com.example.Listing.model.MassModel;

public interface PropertyService {
// rename to ScoringService
    MassModel calculateQualityScore(String cityId, String propertyId);

    MassModel calculateRelevanceScore(String propertyId);

    MassModel findScoreBypropertyId(String propertyId);
}
