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
import pl.lodz.p.edu.csr.textclassification.service.classifier.KnnStatistics;
import pl.lodz.p.edu.csr.textclassification.service.metrics.MetricType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@Api(value = "Classification Controller")
@RequestMapping("/classification")
//@PreAuthorize("permitAll()")
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    @Autowired
    KnnStatistics knnStatistics;

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
        try {
            return classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, processName);
        } catch (Exception e) {
            e.printStackTrace();
            return "CLASSIFIED PROCESS FAILED!\n\n" + e.getMessage();
        }
    }

    @GetMapping(value = "/example")
    @ResponseBody
    @ApiOperation(value = "Classify all reuters and save result in database for example data.")
    @ResponseStatus(HttpStatus.OK)
    @Loggable(Loggable.TRACE)
    public String classifyAllExample() {
        System.out.println("TEST");
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.SCKDAW, FeatureType.SUKDAW);
        System.out.println(8.0 + " " + DataBreakdown.L50T50.toString() + " " + usedFeatures.toString() + " " + MetricType.EUCLIDEAN.toString() + " test1");
        try {
            return classificationService.classifyAllReuters(8.0, DataBreakdown.L20T80, usedFeatures, MetricType.EUCLIDEAN, "test1");
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

    @GetMapping(value = "/classificationForSpecificK/{k}")
    @ApiOperation(value = "Classifications for specific k.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForSpecificK(@PathVariable Double k) throws Exception {
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
        MetricType metric = MetricType.EUCLIDEAN;
        DataBreakdown dataBreakdown = DataBreakdown.L60T40;
        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, "ForK_" + k);
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
        sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
        sb.append("K = ").append(k).append("\n");
        sb.append("=========================================================================").append("\n");
        sb.append(result).append("\n\n");
        System.out.println(sb.toString());
    }

    @GetMapping(value = "/classificationForDifferentK")
    @ApiOperation(value = "Classifications for different k.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForDifferentK() throws Exception {
        List<Double> listOfK = Arrays.asList(0.001, 0.01, 0.02, 0.05, 0.1, 0.2, 0.4, 0.6, 0.8, 1.0);
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());

        MetricType metric = MetricType.EUCLIDEAN;
        DataBreakdown dataBreakdown = DataBreakdown.L60T40;

        for (Double k : listOfK) {
            String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, "");
            StringBuilder sb = new StringBuilder();
            sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
            sb.append("CLASSIFICATION WITH PARAMETERS:\n");
            sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
            sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
            sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
            sb.append("K = ").append(k).append("\n");
            sb.append("=========================================================================").append("\n");
            sb.append(result).append("\n\n");
            System.out.println(sb.toString());
        }
    }

    @GetMapping(value = "/classificationForDifferentDataBreakdown")
    @ApiOperation(value = "Classifications for different DataBreakdown.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForDifferentDataBreakdown() throws Exception {
        List<Double> listOfK = Arrays.asList(0.001, 0.01, 0.02, 0.05, 0.1, 0.2, 0.4, 0.6, 0.8, 1.0);
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());

//        List<FeatureType> withoutAVG = Arrays.asList(FeatureType.values());
//        System.out.println(withoutAVG.toString());
//        withoutAVG.removeAll(Arrays.asList(FeatureType.AOSCKDS,FeatureType.AOSCKDP,FeatureType.AOSUKDP, FeatureType.AOSUKDS));
//
//        List<FeatureType> withoutSpecial = Arrays.asList(FeatureType.values());
//        withoutSpecial.removeAll(Arrays.asList(FeatureType.AKOP, FeatureType.AWBUK, FeatureType.AWWTNOSDAK, FeatureType.AL));
//
//        List<FeatureType> withoutProportion = Arrays.asList(FeatureType.values());
//        withoutProportion.removeAll(Arrays.asList(FeatureType.PUKIPOA, FeatureType.PUACK));
//
//        List<FeatureType> withoutSum = Arrays.asList(FeatureType.values());
//        withoutSum.removeAll(Arrays.asList(FeatureType.SCKDAW, FeatureType.SUKDAW));
//
//        List<List<FeatureType>> diffFeatures = Arrays.asList(withoutAVG, withoutProportion, withoutSpecial, withoutSum);

        MetricType metric = MetricType.EUCLIDEAN;
        DataBreakdown dataBreakdown = DataBreakdown.L60T40;

        for (Double k : listOfK) {
            String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, "");
            StringBuilder sb = new StringBuilder();
            sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
            sb.append("CLASSIFICATION WITH PARAMETERS:\n");
            sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
            sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
            sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
            sb.append("K = ").append(k).append("\n");
            sb.append("=========================================================================").append("\n");
            sb.append(result).append("\n\n");
            System.out.println(sb.toString());
        }
    }

    @GetMapping(value = "/classificationForSpecificDataBreakdown/{dataBreakdown}")
    @ApiOperation(value = "Classifications for specific DataBreakdown.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForSpecificDataBreakdown(@PathVariable DataBreakdown dataBreakdown) throws Exception {
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
        MetricType metric = MetricType.EUCLIDEAN;
        Double k = 0.5;
        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, "ForDB_" + dataBreakdown.toString());
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
        sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
        sb.append("K = ").append(k).append("\n");
        sb.append("=========================================================================").append("\n");
        sb.append(result).append("\n\n");
        System.out.println(sb.toString());
    }

    @GetMapping(value = "/classificationForSpecificMetric/{metric}/{processName}")
    @ApiOperation(value = "Classifications for specific Metric.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForSpecificMetric(@PathVariable MetricType metric, @PathVariable String processName) throws Exception {
        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
//        MetricType metric = MetricType.EUCLIDEAN;
        DataBreakdown dataBreakdown = DataBreakdown.L60T40;
        Double k = 1.0;
        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, processName);
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
        sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
        sb.append("K = ").append(k).append("\n");
        sb.append("=========================================================================").append("\n");
        sb.append(result).append("\n\n");
        System.out.println(sb.toString());
    }

    @GetMapping(value = "/classificationForSpecificFeatures")
    @ApiOperation(value = "Classifications for specific Features.")
    @ResponseStatus(HttpStatus.OK)
    public void classificationForSpecificFeatures() throws Exception {
        List<FeatureType> usedFeatures = Arrays.asList(
                FeatureType.AKOP,
                FeatureType.AWBUK,
                FeatureType.AWWTNOSDAK,
                FeatureType.AL,
                FeatureType.AOSCKDP,
                FeatureType.AOSCKDS,
                FeatureType.AOSUKDP,
                FeatureType.AOSUKDS,
                FeatureType.PUACK,
                FeatureType.PUKIPOA,
                FeatureType.SCKDAW,
                FeatureType.SUKDAW
        );
        MetricType metric = MetricType.EUCLIDEAN;
        DataBreakdown dataBreakdown = DataBreakdown.L60T40;
        Double k = 0.5;
        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metric, "ForFeatures_" + FeatureType.packFeatures(usedFeatures));
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
        sb.append("METRIC TYPE = ").append(metric.toString()).append("\n");
        sb.append("K = ").append(k).append("\n");
        sb.append("=========================================================================").append("\n");
        sb.append(result).append("\n\n");
        System.out.println(sb.toString());
    }



    @GetMapping(value = "/report")
    @ApiOperation(value = "Report for all classifications.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String reportForAllClassification() {
        return classificationService.generateReportForAllClassifications();
    }

    @GetMapping(value = "/matrix/{processName}")
    @ResponseBody
    public String matrixClassification(@PathVariable String processName) {
        return knnStatistics.printMatrixConfusion(knnStatistics.generateMatrixConfusion(processName));
    }


}
