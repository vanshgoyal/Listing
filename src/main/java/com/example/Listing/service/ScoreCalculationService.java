package com.example.Listing.service;

import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.PropertyModel;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

        return (qualityScore/500);
    }

    public static float relevanceScore(PropertyModel propertyParams, float qualityScore) {
        return (float) (qualityScore + 1000 * Math.random());
    }

    public static float relevanceScoreForSponsored(PropertyModel propertyParams, float qualityScore) {
        propertyParams.setNumberOFContacts((int) (100 * Math.random()));
        int numberOfContacts = propertyParams.getNumberOFContacts();
        System.out.println("number of contacts: " + numberOfContacts);
        Calendar date = Calendar.getInstance();

        long dateAfterInMs = date.getTimeInMillis();
        long timeDiff = Math.abs(dateAfterInMs - propertyParams.getActivationDate());
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        System.out.println("Days difference: "+daysDiff);
        qualityScore = (qualityScore+1)/(1+(propertyParams.getNumberOFContacts()/(1+qualityScore)));
        //System.out.println("quality Score: "+ qualityScore);
        float p = (float) (numberOfContacts);
        float transientDecay = (float) (1-(1/(1+Math.exp(-0.000000005*(propertyParams.getNumberOFContacts()-10)))));
        //System.out.println("transient score: "+ transientDecay);
        float timeDecay = (float)(1-(1/(1+Math.exp(-0.000000005*(daysDiff)))));
        //System.out.println("time decay : "+ timeDecay);
        double z = 1- 0.05/2;
        //System.out.println("z = "+z);
        float wilsonScore = (float) ((float) (1/(1+Math.pow(z,2)/numberOfContacts))*(p+(Math.pow(z,2)/2*numberOfContacts)-z*(Math.sqrt((Math.pow(z,2)/(4*Math.pow(numberOfContacts,2)))))))/40;
        System.out.println("wilson Score: "+wilsonScore);

        float relevanceScore = (qualityScore+(wilsonScore+1)*transientDecay)*timeDecay;
        System.out.println("relevance Score: "+ relevanceScore);
        System.out.println("--------------------------------------------");

        return relevanceScore;
    }

    public static float relevanceScoreForNormal(PropertyModel propertyParams, float qualityScore) {
        propertyParams.setNumberOFContacts((int) (100 * Math.random()));
        int numberOfContacts = propertyParams.getNumberOFContacts();
        Calendar date = Calendar.getInstance();

        long dateAfterInMs = date.getTimeInMillis();
        long timeDiff = Math.abs(dateAfterInMs - propertyParams.getActivationDate());
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        qualityScore = (qualityScore+1)/(1+(propertyParams.getNumberOFContacts()/(1+qualityScore)));

        float p = (float) (numberOfContacts);
        float transientDecay = (float) (1-(1/(1+Math.exp(-0.5*(propertyParams.getNumberOFContacts()-25)))));
        float timeDecay = (float)(1-(1/(1+Math.exp(-0.5*daysDiff))));
        float z = (float) (1-(0.05/2));
        float wilsonScore = (float) ((float) (1/(1+Math.pow(z,2)/numberOfContacts))*(p+(Math.pow(z,2)/2*numberOfContacts)-z*(Math.sqrt((Math.pow(z,2)/4*Math.pow(numberOfContacts,2))))));

        float relevanceScore = (qualityScore+(wilsonScore+1)*transientDecay)*timeDecay;

        return relevanceScore;
    }

}
