package com.example.Listing.service.impl;

import com.example.Listing.model.CoefficientModel;
import com.example.Listing.repository.RepositoryParam.ParamRepository;
import com.example.Listing.service.ParamService;
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
public class ParamServiceImpl  implements ParamService {

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private ParamRepository paramRepository;
    Logger logger = LoggerFactory.getLogger(ParamServiceImpl.class);
    @Override
    public CoefficientModel saveOrUpdateParam(CoefficientModel coefficientModel) {
        if(paramRepository.findByCityId(coefficientModel.getCityId())!= null)
        {
            Query query = new Query(
                    Criteria.where("cityId").is(coefficientModel.getCityId()));
            logger.error("query");
            Update update = new Update().set("paramType", coefficientModel.getType());

            return mt.findAndModify(query, update, CoefficientModel.class);
        }
        else {
            return paramRepository.save(coefficientModel);
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
