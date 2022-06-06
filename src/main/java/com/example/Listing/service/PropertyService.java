package com.example.Listing.service;

import com.example.Listing.model.MassModel;

import java.util.ArrayList;

public interface PropertyService {
// rename to ScoringService
    MassModel calculateQualityScore(String cityId, String propertyId);

    MassModel calculateRelevanceScore(String propertyId);

    MassModel findScoreBypropertyId(String propertyId);

    void executeBulkUpdate(ArrayList<MassModel> massModelArr, int i, int l);
}
