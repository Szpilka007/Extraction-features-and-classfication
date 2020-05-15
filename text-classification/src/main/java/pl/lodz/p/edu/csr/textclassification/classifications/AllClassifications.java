package pl.lodz.p.edu.csr.textclassification.classifications;

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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@Api(value = "Classification All in One")
@RequestMapping("/class")
public class AllClassifications {

    @Autowired
    ClassificationService classificationService;

//    @GetMapping(value = "/all")
//    @ResponseBody
//    public void classificationAll(String processName) throws Exception {
//        List<DataBreakdown> dataBreakdownList = Arrays.asList(DataBreakdown.values());
//
//
//
//        List<FeatureType> usedFeatures = Arrays.asList(FeatureType.values());
//        Double k = 0.02;
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

}
