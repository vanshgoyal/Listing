package com.example.Listing.service;

import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;

public class ScoreCalculationService {

        public static float QualityScore(PropertyModel propertyParams, CoefficientModel coefficientModel) {
            int qualityScore=0;
            if(propertyParams.getType() != null) {
                System.out.println(coefficientModel.getType().get(propertyParams.getType()));
                qualityScore+=(coefficientModel.getType().get(propertyParams.getType()));
            }
            if(propertyParams.getBuildingType() != null) {
                qualityScore+=(coefficientModel.getBuildingType().get(propertyParams.getBuildingType()));
            }

            if(propertyParams.getFurnishing() != null) {
                qualityScore+=(coefficientModel.getFurnishing().get(propertyParams.getFurnishing()));
            }
            //logger.error("55555555555555555555555555555555555555555555555555");
            try{
                if(propertyParams.getParking() != null) {
                    qualityScore+=(coefficientModel.getParking().get(propertyParams.getParking()));
                }
            } catch (Exception e) {
                qualityScore+=0;
            }
            qualityScore+= propertyParams.getDeposit()* coefficientModel.getDeposit();
            qualityScore+=propertyParams.getRent()*coefficientModel.getRent();
            qualityScore+=propertyParams.getLatitude()*coefficientModel.getLatitude();
            qualityScore+=propertyParams.getLongitude()*coefficientModel.getLongitude();

            return qualityScore;
        }
        public static float RelevanceScore(PropertyModel propertyParams, float qualityScore)
        {
            return (float) (qualityScore+1000*Math.random());
        }
}
