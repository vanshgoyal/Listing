package com.example.Listing.repository.RepositoryParam;

import com.example.Listing.model.mass_model;
import com.example.Listing.model.ParamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParamRepository extends MongoRepository<ParamModel, String> {


    ParamModel findBycityId( String cityId);
}
