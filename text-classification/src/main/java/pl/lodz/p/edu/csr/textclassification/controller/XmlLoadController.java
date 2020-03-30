package pl.lodz.p.edu.csr.textclassification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.csr.textclassification.service.utils.XmlParser;

@Controller
public class XmlLoadController {

    @Autowired
    XmlParser xmlParser;

    @PatchMapping(value = "/h2/load-xml")
    @ResponseBody
    public String loadXmlReutersToDB(@RequestParam String filePath) {
        xmlParser.migrateReutersToDatabase(filePath);
        return "DONE";
    }

    @PatchMapping(value = "/h2/load-all-xml")
    @ResponseBody
    public String loadDirXmlReutersToDB() {
        String basePath="/reuters-machine-learning/reuters21578/reut2-";
        String extension = ".sgm";
        // temp solution
        for(int i=0;i<=21;i++){
            String number = String.format("%3s", i).replace(' ', '0');
            System.out.println("Starting parse: "+basePath+number+extension);
            xmlParser.migrateReutersToDatabase(basePath+number+extension);
            System.out.println("=> DONE");
        }
        return "DONE";
    }

    @DeleteMapping(value = "/h2/delete-all-xml")
    @ResponseBody
    public String deleteAllReutersFromDB(){
        xmlParser.deleteAllReutersFromDB();
        return "DONE";
    }

}
