//package pl.lodz.p.edu.csr.textclassification.classifications;
//
//import com.jcabi.aspects.Loggable;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
//import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
//import pl.lodz.p.edu.csr.textclassification.service.ClassificationService;
//import pl.lodz.p.edu.csr.textclassification.service.metrics.MetricType;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
////import org.springframework.security.access.prepost.PreAuthorize;
//
//@Controller
//@Api(value = "Classification - Different K")
//@RequestMapping("/diffk")
////@PreAuthorize("hasRole('ADMIN')")
//public class DifferentK {
//
//    @Autowired
//    ClassificationService classificationService;
//
//    @DeleteMapping(value = "/delete/{name}")
//    @ResponseBody
//    @ApiOperation(value = "Delete classification data by process name.")
//    @ResponseStatus(HttpStatus.OK)
//    @Loggable(Loggable.TRACE)
//    public String deleteClassificationDataByName(@PathVariable String name) {
//        return classificationService.deleteClassificationDataByName(name);
//    }
//
//    @GetMapping(value = "/class")
//    @ResponseBody
//    public void classificationForDifferentK(Double k, String processName) throws Exception {
//        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
//        DataBreakdown dataBreakdown = DataBreakdown.L90T10;
//        MetricType metricType = MetricType.EUCLIDEAN;
//        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metricType, processName);
//        StringBuilder sb = new StringBuilder();
//        sb.append("=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
//        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
//        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
//        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
//        sb.append("METRIC TYPE = ").append(metricType.toString()).append("\n");
//        sb.append("K = ").append(k.toString()).append("\n");
//        sb.append("PROCESS NAME = ").append(processName).append("\n");
//        sb.append(result).append("\n\n");
//        System.out.println(sb.toString());
//    }
//
////    @GetMapping(value = "/classificationForDifferentDataBreakDown")
////    @ResponseBody
////    public void classificationForDifferentDataBreakDown(DataBreakdown dataBreakdown, String processName) throws Exception {
////        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
////        Double k = 0.02;
////        MetricType metricType = MetricType.EUCLIDEAN;
////        String result = classificationService.classifyAllReuters(k, dataBreakdown, usedFeatures, metricType, processName);
////        StringBuilder sb = new StringBuilder();
////        sb.append("=====================[").append(LocalDateTime.now().toString()).append("]=====================\n");
////        sb.append("CLASSIFICATION WITH PARAMETERS:\n");
////        sb.append("USED FEATURES = ").append(usedFeatures.toString()).append("\n");
////        sb.append("DATA BREAKDOWN = ").append(dataBreakdown.toString()).append("\n");
////        sb.append("METRIC TYPE = ").append(metricType.toString()).append("\n");
////        sb.append("K = ").append(k.toString()).append("\n");
////        sb.append("PROCESS NAME = ").append(processName).append("\n");
////        sb.append(result).append("\n\n");
////        System.out.println(sb.toString());
////    }
////
////    @GetMapping(value = "/thebestK")
////    @ResponseBody
////    public String theBestK(){
////        return classificationService.theBestK();
////    }
//
//}
