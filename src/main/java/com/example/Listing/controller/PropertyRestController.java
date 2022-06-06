package com.example.Listing.controller;

import com.example.Listing.dto.CoefficientsDTO;
import com.example.Listing.model.CoefficientModel;
import com.example.Listing.model.MassModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.CoefficientService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClientService;
import com.example.Listing.service.SavePropertyScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/property")
public class PropertyRestController {

    Logger logger = LoggerFactory.getLogger(PropertyRestController.class);
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RestClientService restClientService;
    @Autowired
    private SavePropertyScoreService savePropertyScoreService;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    private CoefficientService coefficientService;
    private Class<? extends org.json.simple.JSONObject> JSONObject;

    @GetMapping(value = "/")
    public String  Home() throws Exception {
        return "Hello World";
    }

    // propertyType for extensibility
    @PostMapping (value = "/saveQualityScore/{id}/{propId}")
    public ResponseEntity<?> saveOrUpdateQualityScore(@PathVariable("id") String cityId, @PathVariable("propId") String propertyId){
        MassModel massModel = propertyService.calculateQualityScore(cityId, propertyId);
       if(massModel == null)
       {
           return new ResponseEntity("Property Id is incorrect", HttpStatus.NOT_FOUND);
       }
        savePropertyScoreService.savePropertyMass(propertyId, massModel);
        return new ResponseEntity(massModel, HttpStatus.OK);
    }

    @PostMapping (value = "/saveRelevanceScore/{propId}")
    public ResponseEntity<?> saveOrUpdateRelevanceScore(@PathVariable("propId") String propertyId){
        MassModel massModel = propertyService.calculateRelevanceScore(propertyId);
        savePropertyScoreService.savePropertyMass(propertyId, massModel);
        return new ResponseEntity("PropertyModel added successfully", HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody CoefficientsDTO coefficientsDTO) {
        coefficientService.saveOrUpdateCoefficient(ObjectMapperUtils.map(coefficientsDTO, CoefficientModel.class));
        return new ResponseEntity("Param added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/byPropId/{propId}")
    public MassModel getPropertyMassById(@PathVariable("propId") String propId) {
        return ObjectMapperUtils.map(propertyService.findScoreBypropertyId(propId), MassModel.class);
    }

    @GetMapping(value = "/byParamId/{paramId}")
    public CoefficientModel getCoefficientByCityId(@PathVariable("paramId") String paramId) {
        return ObjectMapperUtils.map(coefficientService.findByCityId(paramId), CoefficientModel.class);
    }

    @DeleteMapping(value = "/deleteParam/{id}")
    public ResponseEntity<?> deleteParamByCityId(@PathVariable("id") String cityId) {
        coefficientService.deleteParamModelById(coefficientService.findByCityId(cityId).getId());
        return new ResponseEntity("param deleted successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/bulkUpdate")
    // output statistics with failure and success count
    public void bulkUpdate (@RequestParam("batch") int batch)
    { // List<String> prpertyId ; cityId
        List<MassModel> PropArr = propertyRepository.findAll();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0, l=PropArr.size();i<l;i+=batch)
        {

            try {
                MassModel massModel = propertyService.calculateQualityScore("1", PropArr.get(i).getPropertyId()); // todo : relevance score
                savePropertyScoreService.savePropertyMass(PropArr.get(i).getPropertyId(), massModel);
            } catch (Exception e)
            {
                logger.error(e.getMessage());
            }
            int finalI = i;
            executorService.execute(()->{
                propertyService.executeBulkUpdate((ArrayList<MassModel>)PropArr, finalI, Math.min(finalI+batch-1,l-1));
            });
        }

    }

}
