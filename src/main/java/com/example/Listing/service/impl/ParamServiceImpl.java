package com.example.Listing.service.impl;

import com.example.Listing.model.ParamModel;
import com.example.Listing.model.Property;
import com.example.Listing.model.mass_model;
import com.example.Listing.repository.RepositoryParam.ParamRepository;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
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
    @Override
    public ParamModel saveOrUpdateParam(ParamModel paramModel) {
        if(paramRepository.findBycityId(paramModel.getCityId())!= null)
        {
//            Query query = new Query();
//            Update update = new Update().inc("paramType", paramModel.getParamType());
            Query query = new Query(
                    Criteria.where("cityId").is(paramModel.getCityId()));
            System.out.println(query);
            Update update = new Update().set("paramType", paramModel.getParamType());

            return mt.findAndModify(query, update, ParamModel.class);
        }
        else {
            return paramRepository.save(paramModel);
        }

    }

    @Override
    public void deleteParamModelById(String id) {
        paramRepository.deleteParamModelById(id);
    }

    @Override
    public ParamModel findBycityId(String propertyId) {

        return paramRepository.findBycityId(propertyId);
    }
}
