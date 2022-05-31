package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClient;
import com.example.Listing.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.Listing.service.QualityScoreCalculation.QualityScore;
import static java.lang.Integer.parseInt;

/**
 * @author ragcrix
 */
@Service
public class  PropertyServiceImpl implements PropertyService {

    @Autowired
    private MongoTemplate mt;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private RestClient restClient;
    @Autowired
    private ParamService paramService;

    @Override
    public MassModel saveOrUpdateProperty(String cityId, String propertyId) throws Exception {
        final String uri = "https://www.nobroker.in/api/v1/property/"+propertyId;
        PropertyDTO propertyDTO = restClient.get("uri");
//        RestTemplate restTemplate = new RestTemplate();
//        JSONObject result = restTemplate.getForObject(uri, JSONObject.class);

//        System.out.println(result.toJSONString());
//        System.out.println("-------------------------------------------------------------");
//        JSONObject res = (JSONObject) new JSONParser().parse((String) result.get("data"));
//        System.out.println(res);
//        System.out.println("-------------------------------------------------------------");
//        String result1 = (result.get("data")).toString();
//        System.out.println(result1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        PropertyDTO propertyDTO = objectMapper.readValue(result1, PropertyDTO.class);
//        System.out.println("-------------------------------------------------------------");
//        System.out.println(propertyDTO);

        PropertyModel propertyParams = ObjectMapperUtils.map(propertyDTO, PropertyModel.class);


        if(propertyRepository.findBypropertyId(propertyParams.getPropertyId())!= null)
        {
            Query query = new Query(
                    Criteria.where("propertyId").is(propertyParams.getPropertyId()));
            MassModel propertyMass = new MassModel();
            propertyMass.setId(propertyParams.getId());
            propertyMass.setPropertyId(propertyParams.getPropertyId());
            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findByCityId(cityId);

            propertyMass.setMassVal(QualityScore(propertyParams, paramModel));
            Update update = new Update().set("massVal", propertyMass.getMassVal());

            return mt.findAndModify(query, update, MassModel.class);
        }
        else {
            MassModel propertyMass = new MassModel();
            propertyMass.setId(propertyParams.getId());
            propertyMass.setPropertyId(propertyParams.getPropertyId());
            ParamModel paramModel = new ParamModel();
            paramModel = paramService.findByCityId(cityId);
            propertyMass.setMassVal(QualityScore(propertyParams, paramModel));
            return propertyRepository.save(propertyMass);
        }

    }

    @Override
    public MassModel findBypropertyId(String propertyId) {

        return propertyRepository.findBypropertyId(propertyId);
    }

}
