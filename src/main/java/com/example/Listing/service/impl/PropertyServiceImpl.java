package com.example.Listing.service.impl;

import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.loggingFolder.LoggingController;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.utils.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    private RestClientService restClientService;
    @Autowired
    private ParamService paramService;
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @Override
    public MassModel calculatePropertyScore(String cityId, String propertyId) {
        final String uri = "https://www.nobroker.in/api/v1/property/"+propertyId;
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = restClientService.getPropertyDTO(uri);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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
        ParamModel paramModel = new ParamModel();
        paramModel = paramService.findByCityId(cityId);

        MassModel propertyMass = new MassModel();
        propertyMass.setId(propertyParams.getId());
        propertyMass.setPropertyId(propertyParams.getPropertyId());
        propertyMass.setMassVal(QualityScore(propertyParams, paramModel));

        return propertyMass;

    }

    @Override
    public MassModel findScoreBypropertyId(String propertyId) {
        MassModel massModel = propertyRepository.findBypropertyId(propertyId);
        if (massModel != null) {
            return massModel;
        } else {
            logger.error("No property by this Id available. Please add the the property");
            MassModel placeHolder = new MassModel();
            return placeHolder;
        }

    }

}
