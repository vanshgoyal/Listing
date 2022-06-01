package com.example.Listing.service.impl;

import com.example.Listing.model.MassModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.SavePropertyScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SavePropertyScoreServiceImpl implements SavePropertyScoreService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private MongoTemplate mt;

    public void savePropertyMass(String propertyId, MassModel propertyMass){
        if(propertyRepository.findBypropertyId(propertyId)!= null)
        {
            System.out.println("found");
            Query query = new Query(
                    Criteria.where("propertyId").is(propertyId));
            Update update = new Update().set("massVal", propertyMass.getMassVal());
            mt.findAndModify(query, update, MassModel.class);
        }
        else {
            propertyRepository.save(propertyMass);
        }
    }
}
