package com.example.Listing.controller;

import com.example.Listing.model.QualityScore;
import com.example.Listing.service.PropertyService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;

public class CallableBulkUpdate {
    @Autowired
    PropertyService propertyService;
    private List<QualityScore> qualityScores;

    public CallableBulkUpdate(List<QualityScore> qualityScores) {
        this.qualityScores = qualityScores;
    }

    public class CallableImpl implements Callable {
        @Override
        public Pair<Integer, Integer> call() {
            Pair<Integer, Integer> successFail = propertyService.executeBulkUpdate(qualityScores);
            return successFail;
        }
    }
}
