package com.example.Listing.service;

import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;

public class ScoreCalculationService {

    public static float qualityScore(PropertyModel propertyParams, CoefficientModel coefficientModel) {
        float qualityScore = 0;
        if (propertyParams.getType() != null) {
            qualityScore += (coefficientModel.getType().get(propertyParams.getType()));
        }
        if (propertyParams.getBuildingType() != null) {
            qualityScore += (coefficientModel.getBuildingType().get(propertyParams.getBuildingType()));
        }

        if (propertyParams.getFurnishing() != null) {
            qualityScore += (coefficientModel.getFurnishing().get(propertyParams.getFurnishing()));
        }
        if (propertyParams.getLeaseType() != null) {
            qualityScore += (coefficientModel.getLeaseType().get(propertyParams.getLeaseType()));
        }
        try {
            if (propertyParams.getParking() != null) {
                qualityScore += (coefficientModel.getParking().get(propertyParams.getParking()));
            }
        } catch (Exception e) {
            qualityScore += 0;
        }
        qualityScore += propertyParams.getDeposit() * coefficientModel.getDeposit();
        qualityScore += propertyParams.getRent() * coefficientModel.getRent();
        qualityScore += propertyParams.getLatitude() * coefficientModel.getLatitude();
        qualityScore += propertyParams.getLongitude() * coefficientModel.getLongitude();
        qualityScore += propertyParams.getNumberOfPhotos() * coefficientModel.getNumberOfPhotos();

        return qualityScore;
    }

    public static float relevanceScore(PropertyModel propertyParams, float qualityScore) {
        return (float) (qualityScore + 1000 * Math.random());
    }
}
