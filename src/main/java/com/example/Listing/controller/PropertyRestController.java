package com.example.Listing.controller;

import com.example.Listing.dto.ParamDTO;
import com.example.Listing.loggingFolder.LoggingController;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.repository.RepositoryProperty.PropertyRepository;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
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
import java.util.Optional;


@RestController
@RequestMapping("/property")
public class PropertyRestController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private SavePropertyScoreService savePropertyScoreService;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    private ParamService paramService;
    private Class<? extends org.json.simple.JSONObject> JSONObject;

    @GetMapping(value = "/")
    public String  Home() throws Exception {
        return "Hello World";
    }
    @PostMapping (value = "/save/{id}/{propId}")
    public ResponseEntity<?> saveOrUpdatePropertyScore(@PathVariable("id") String cityId, @PathVariable("propId") String propertyId){
        MassModel massModel = propertyService.calculatePropertyScore(cityId, propertyId);
        savePropertyScoreService.savePropertyMass(propertyId, massModel);
        return new ResponseEntity("PropertyModel added successfully", HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody ParamDTO paramDTO) {
        paramService.saveOrUpdateParam(ObjectMapperUtils.map(paramDTO, ParamModel.class));
        return new ResponseEntity("Param added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/byPropId/{propId}")
    public MassModel getPropertyMassById(@PathVariable("propId") String propId) {
        return ObjectMapperUtils.map(propertyService.findScoreBypropertyId(propId), MassModel.class);
    }
    @GetMapping(value = "/byParamId/{paramId}")
    public ParamModel getParamById(@PathVariable("paramId") String paramId) {
        return ObjectMapperUtils.map(paramService.findByCityId(paramId), ParamModel.class);
    }

    @DeleteMapping(value = "/deleteParam/{id}")
    public ResponseEntity<?> deleteParamByCityId(@PathVariable("id") String cityId) {
        paramService.deleteParamModelById(paramService.findByCityId(cityId).getId());
        return new ResponseEntity("param deleted successfully", HttpStatus.OK);
    }
    @PutMapping(value = "/bulkUpdate")
    public void bulkUpdate (@RequestParam("batch") int batch)
    {
        List<MassModel> PropArr = propertyRepository.findAll();
        ArrayList<String> idArr = new ArrayList<String>();
        for(MassModel it: PropArr)
            idArr.add(it.getPropertyId());

        ArrayList<Float> massArr = new ArrayList<Float>();
        for(String it: idArr)
        {

            try {
                MassModel massModel = propertyService.calculatePropertyScore("1", it);
                savePropertyScoreService.savePropertyMass(it, massModel);
            } catch (Exception e)
            {
                logger.error(e.getMessage());
            }
        }

    }
}
