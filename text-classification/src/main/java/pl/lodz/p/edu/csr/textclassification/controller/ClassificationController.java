package pl.lodz.p.edu.csr.textclassification.controller;

import com.jcabi.aspects.Loggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.service.ClassificationService;
import pl.lodz.p.edu.csr.textclassification.service.metrics.MetricType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@Api(value = "Classification Controller")
@RequestMapping("/classification")
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    @GetMapping(value = "/classifyAll")
    @ResponseBody
    @ApiOperation(value = "Classify all reuters and save result in database.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String classifyAllAndSaveInDB(@RequestParam double k,
                                         @RequestParam DataBreakdown dataBreakdown,
                                         @RequestParam List<FeatureType> usedFeatures,
                                         @RequestParam MetricType metric,
                                         @RequestParam String processName) {
        System.out.println("TEST");
        try {
            return classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, processName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CLASSIFIED PROCESS FAILED!\n\n";
    }

    @GetMapping(value = "/example")
    @ResponseBody
    @ApiOperation(value = "Classify all reuters and save result in database for example data.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String classifyAllExample() {
        System.out.println("TEST");
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.SCKDAW, FeatureType.SUKDAW);
        System.out.println(0.5 +" "+DataBreakdown.L90T10.toString()+" "+usedFeatures.toString()+" "+MetricType.EUCLIDEAN.toString()+" test1");
        try {
            return classificationService.classifyAllReuters(0.8, DataBreakdown.L20T80, usedFeatures, MetricType.EUCLIDEAN, "test1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CLASSIFIED PROCESS FAILED!\n\n";
    }

    @GetMapping(value = "/classifyOne")
    @ResponseBody
    @ApiOperation(value = "Classify one reuters by uuid.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String classifyOneAndSaveInDB(@RequestParam String uuid, @RequestParam double k, @RequestParam DataBreakdown dataBreakdown,
                                         @RequestParam List<FeatureType> usedFeatures, @RequestParam MetricType metric) {
        try {
            return classificationService.classifyReutersByReutersUUID(UUID.fromString(uuid),
                    k, dataBreakdown, usedFeatures, metric);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CLASSIFIED REUTERS WITH UUID [" + uuid + "] FAILED!";
    }

    @DeleteMapping(value = "/deleteAll")
    @ResponseBody
    @ApiOperation(value = "Delete all classification data.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String deleteAllClassificationData() {
        return classificationService.deleteAll();
    }

    @DeleteMapping(value = "/delete/{name}")
    @ResponseBody
    @ApiOperation(value = "Delete classification data by process name.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String deleteClassificationDataByName(@PathVariable String name) {
        return classificationService.deleteClassificationDataByName(name);
    }

}
