package com.example.Listing.service.impl;

import com.example.Listing.model.CoefficientModel;
import com.example.Listing.repository.RepositoryParam.ParamRepository;
import com.example.Listing.service.CoefficientService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author ragcrix
 */
@Service
public class CoefficientServiceImpl implements CoefficientService {

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private ParamRepository paramRepository;
    Logger logger = LoggerFactory.getLogger(CoefficientServiceImpl.class);
    @Override
    public void saveOrUpdateCoefficient(CoefficientModel coefficientModel) {
        if(paramRepository.findByCityId(coefficientModel.getCityId())!= null)
        {
            Query query = new Query(
                    Criteria.where("cityId").is(coefficientModel.getCityId()));
            logger.error("query");
            Document doc = new Document(); // org.bson.Document
            mt.getConverter().write(coefficientModel, doc);
            Update update = Update.fromDocument(new Document("$set",doc));
            mt.findAndModify(query, update, CoefficientModel.class);
        }
        else {
            paramRepository.save(coefficientModel);
        }

    }

    @Override
    public void deleteParamModelById(String id) {
        paramRepository.deleteParamModelById(id);
    }

    @Override
    public CoefficientModel findByCityId(String propertyId) {

        CoefficientModel coefficientModel = paramRepository.findByCityId(propertyId);
        if (coefficientModel != null) {
            return coefficientModel;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            return new CoefficientModel();
        }

    }
}
