package com.example.Listing.controller;

import com.example.Listing.dto.ParamDTO;
import com.example.Listing.dto.PropertyDTO;
import com.example.Listing.loggingFolder.LoggingController;
import com.example.Listing.model.MassModel;
import com.example.Listing.model.ParamModel;
import com.example.Listing.model.PropertyModel;
import com.example.Listing.service.ParamService;
import com.example.Listing.service.PropertyService;
import com.example.Listing.service.RestClient;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Listing.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;


@RestController
@RequestMapping("/property")
public class PropertRestController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RestClient abc;
    @Autowired
    private ParamService paramService;
    private Class<? extends org.json.simple.JSONObject> JSONObject;

    @GetMapping(value = "/")
    public void Home() throws Exception {
        abc.get("knscksnck");
    }
    @PostMapping (value = "/save/{id}/{propId}")
    public ResponseEntity<?> saveOrUpdateProperty(@PathVariable("id") String cityId, @PathVariable("propId") String properyId) throws Exception {
        propertyService.saveOrUpdateProperty(cityId, properyId);
        return new ResponseEntity("PropertyModel added successfully", HttpStatus.OK);
    }

    @PostMapping (value = "/saveParam")
    public ResponseEntity<?> saveOrUpdateParam(@RequestBody ParamDTO paramDTO) {
        paramService.saveOrUpdateParam(ObjectMapperUtils.map(paramDTO, ParamModel.class));
        return new ResponseEntity("Param added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/byPropId/{propId}")
    public MassModel getPropertyById(@PathVariable("propId") String propId) {
        return ObjectMapperUtils.map(propertyService.findBypropertyId(propId), MassModel.class);
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
}
