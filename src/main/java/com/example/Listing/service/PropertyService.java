package com.example.Listing.service;

import com.example.Listing.model.MassModel;

public interface PropertyService {
// rename to ScoringService
    MassModel calculatePropertyScore(String cityId, String propertyId);

    MassModel findScoreBypropertyId(String propertyId);
}
