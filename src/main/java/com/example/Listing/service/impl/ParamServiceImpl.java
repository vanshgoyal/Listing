package com.example.Listing.service.impl;

import com.example.Listing.loggingFolder.LoggingController;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
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
    Logger logger = LoggerFactory.getLogger(LoggingController.class);
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
    public ParamModel findByCityId(String propertyId) {

        ParamModel paramModel= paramRepository.findBycityId(propertyId);
        if (paramModel != null) {
            return paramModel;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            ParamModel placeHolder = new ParamModel();
            return placeHolder;
        }

    }
}
