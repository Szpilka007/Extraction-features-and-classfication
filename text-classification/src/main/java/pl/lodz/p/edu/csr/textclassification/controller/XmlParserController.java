package pl.lodz.p.edu.csr.textclassification.controller;

import com.jcabi.aspects.Loggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.csr.textclassification.service.XmlParserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "XML Parser Module")
@RequestMapping("/h2-xml")
public class XmlParserController {

    @Autowired
    XmlParserService xmlParserService;

    @PostMapping(value = "/load-single")
    @ResponseBody
    @ApiOperation(value = "Load single reuters from resources by related file path.")
    @ResponseStatus(HttpStatus.CREATED)
    @Loggable(Loggable.TRACE)
    public String loadXmlReutersToDB(@RequestParam String filePath) {
        try {
            xmlParserService.migrateReutersToDatabase(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return "Loaded to database {" + filePath + "} => FAILED!";
        }
        return "Loaded to database {" + filePath + "} => SUCCESSFUL!";
    }

    @PostMapping(value = "/load-all")
    @ResponseBody
    @ApiOperation(value = "Load all reuters from resources.")
    @ResponseStatus(HttpStatus.CREATED)
    @Loggable(Loggable.TRACE)
    public List<String> loadDirXmlReutersToDB() {
        String basePath = "/reuters-machine-learning/reuters21578/";
        String prefix = "reut2-";
        String extension = ".sgm";
        List<String> responseSuccessLoad = new ArrayList<>();
        // temp solution
        for (int i = 0; i <= 21; i++) {
            String number = String.format("%3s", i).replace(' ', '0');
            try {
                xmlParserService.migrateReutersToDatabase(basePath + prefix + number + extension);
            } catch (Exception e) {
                responseSuccessLoad.add("Loaded to database {" + prefix + number + extension + "} => FAILED!");
                e.printStackTrace();
            }
            responseSuccessLoad.add("Loaded to database {" + prefix + number + extension + "} => SUCCESSFUL!");
        }
        return responseSuccessLoad;
    }

    @DeleteMapping(value = "/remove-all")
    @ResponseBody
    @ApiOperation(value = "Remove all reuters from database (be careful).")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Loggable(Loggable.TRACE)
    public String deleteAllReutersFromDB() {
        try {
            xmlParserService.deleteAllReutersFromDB();
        } catch (Exception e) {
            e.printStackTrace();
            return "The operation of clearing the database from reuters failed!";
        }
        return "The operation of cleaning the database from reuters was successful!";
    }

}
