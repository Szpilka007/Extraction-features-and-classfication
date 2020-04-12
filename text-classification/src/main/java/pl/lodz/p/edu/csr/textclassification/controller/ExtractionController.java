package pl.lodz.p.edu.csr.textclassification.controller;

import com.jcabi.aspects.Loggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.service.ExtractionService;

import java.util.List;
import java.util.UUID;

@Controller
@Api(value = "Extraction Controller")
@RequestMapping("/extractor")
public class ExtractionController {

    @Autowired
    ExtractionService extractionService;

    @GetMapping(value = "/stats")
    @ResponseBody
    @ApiOperation(value = "Clear database from useless reuters.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String stats() {
        return extractionService.stats();
    }

    @GetMapping(value = "/{uuid}")
    @ResponseBody
    @ApiOperation(value = "Extract and save features from reuters by uuid.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String getAndSaveOneReutersFeatures(@PathVariable String uuid) {
        StringBuilder result = new StringBuilder();
        result.append(extractionService.extractFeature(UUID.fromString(uuid))+"\n");
        List<FeatureEntity> features = extractionService.getReutersRepository()
                .findReutersEntityByUuid(UUID.fromString(uuid))
                .getFeatures();
        features.forEach(fe -> result
                .append(fe.getFeatureType().toString())
                .append(" = ")
                .append(fe.getValue()).append("\n"));
        result.append("Added calculated features to database SUCCESSFUL!");
        return result.toString();
    }

    @GetMapping(value = "/extractAll")
    @ResponseBody
    @ApiOperation(value = "Extract and save all features for all reuters.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String calculateAndSaveAllFeatures() {
        return extractionService.extractAllFeatures();
    }

    @GetMapping(value = "/deleteAllFeatures")
    @ResponseBody
    @ApiOperation(value = "Delete all features.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public void deleteAllFeatures() {
        extractionService.dropFeatures();
    }

    @GetMapping(value = "/statsFeatures")
    @ResponseBody
    @ApiOperation(value = "Check stats for calculated features.")
    @ResponseStatus(HttpStatus.OK)
    public String checkStatusForFeatures(){
        return extractionService.checkStatusForFeatures();
    }


}
