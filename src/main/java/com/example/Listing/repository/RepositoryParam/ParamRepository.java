package com.example.Listing.repository.RepositoryParam;

import com.example.Listing.model.CoefficientModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParamRepository extends MongoRepository<CoefficientModel, String> {


    CoefficientModel findByCityId(String cityId);

    void deleteParamModelById(String id);
}
