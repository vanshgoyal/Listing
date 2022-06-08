package com.example.Listing.service.impl;

import com.example.Listing.exception.CustomException;
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

    Logger logger = LoggerFactory.getLogger(CoefficientServiceImpl.class);
    @Autowired
    private MongoTemplate mt;
    @Autowired
    private ParamRepository paramRepository;

    @Override
    public void saveOrUpdateCoefficient(CoefficientModel coefficientModel) {
        if (coefficientModel.getCityId().isEmpty() || coefficientModel.getCityId().length() == 0) {
            throw new CustomException("601", "Please send a proper city id");
        }
        if (paramRepository.findByCityId(coefficientModel.getCityId()) != null) {
            Query query = new Query(
                    Criteria.where("cityId").is(coefficientModel.getCityId()));
            logger.error("query");
            Document doc = new Document(); // org.bson.Document
            mt.getConverter().write(coefficientModel, doc);
            Update update = Update.fromDocument(new Document("$set", doc));
            try {
                mt.findAndModify(query, update, CoefficientModel.class);
            } catch (IllegalArgumentException e) {
                throw new CustomException("602", "given Coefficient is null" + e.getMessage());
            } catch (Exception e) {
                throw new CustomException("603", "Something went wrong in Service layer while saving the coefficient" + e.getMessage());
            }

        } else {
            try {
                paramRepository.save(coefficientModel);
            } catch (IllegalArgumentException e) {
                throw new CustomException("602", "given coefficient is null" + e.getMessage());
            } catch (Exception e) {
                throw new CustomException("603", "Something went wrong in Service layer while saving the coefficient" + e.getMessage());
            }
            paramRepository.save(coefficientModel);
        }

    }

    @Override
    public void deleteParamModelById(String id) {


        try {
            paramRepository.deleteParamModelById(id);
        } catch (IllegalArgumentException e) {
            throw new CustomException("608", "given City id is null, please send some id to be deleted" + e.getMessage());
        } catch (java.util.NoSuchElementException e) {
            throw new CustomException("607", "given CityId does not exist in DB" + e.getMessage());
        }
    }

    @Override
    public CoefficientModel findByCityId(String cityId) {

        if (cityId.isEmpty() || cityId.length() == 0) {
            throw new CustomException("601", "Please send a proper city id");
        }
        CoefficientModel coefficientModel = paramRepository.findByCityId(cityId);
        if (coefficientModel != null) {
            return coefficientModel;
        } else {
            logger.error("No coefficients by this Id available. Please add the the coefficients");
            throw new CustomException("607", "This cityId is not correct. ");
        }

    }
}
