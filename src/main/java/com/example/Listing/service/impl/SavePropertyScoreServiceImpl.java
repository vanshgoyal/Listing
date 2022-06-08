package com.example.Listing.service.impl;

import com.example.Listing.model.QualityScore;
import com.example.Listing.model.RelevanceScore;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.repository.RepositoryProperty.RelevanceRepository;
import com.example.Listing.service.SavePropertyScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SavePropertyScoreServiceImpl implements SavePropertyScoreService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RelevanceRepository relevanceRepository;
    @Autowired
    private MongoTemplate mt;

    public void savePropertyQualityScore(String propertyId, QualityScore qualityScore) {
        if (propertyRepository.findBypropertyId(propertyId) != null) {
            System.out.println("Property with current Id already present. Updating Score.");
            Query query = new Query(
                    Criteria.where("propertyId").is(propertyId));
            Update update = new Update().set("qualityScore", qualityScore.getQualityScore());
            mt.findAndModify(query, update, QualityScore.class);
        } else {
            propertyRepository.save(qualityScore);
        }
    }

    public ResponseEntity<?> savePropertyRelevanceScore(String propertyId, RelevanceScore relevanceScore) {
        if (relevanceRepository.findBypropertyId(propertyId) != null) {
            System.out.println("Property with current Id already present. Updating Score.");
            Query query = new Query(
                    Criteria.where("propertyId").is(propertyId));
            Update update = new Update().set("relevanceScore", relevanceScore.getRelevanceScore());
            mt.findAndModify(query, update, RelevanceScore.class);
        } else {
            relevanceRepository.save(relevanceScore);
        }
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }
}
