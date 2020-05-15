package pl.lodz.p.edu.csr.textclassification.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.csr.textclassification.service.XmlParserService;

@Controller
public class MainController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password) {
        return "/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "/logout";
    }

    @RequestMapping(value = {"/index"})
    public String root() {
        return "/index";
    }

    @GetMapping("/fragments/layout")
    public String getLayout() {
        return "/fragments/layout";
    }

    @GetMapping("/fragments/header")
    public String getHeader() {
        return "/fragments/header";
    }

    @GetMapping("/fragments/footer")
    public String getFooter() {
        return "/fragments/footer";
    }

    @RequestMapping("/404")
    public String error404() {
        return "/404";
    }

    @RequestMapping("401")
    public String error401() {
        return "/401";
    }

    @RequestMapping("/reuters-operations")
    public String reutersOperations() {
        return "/reuters/reuters-operations";
    }

    @RequestMapping("/reuters-view")
    public String reutersView() {
        return "/reuters/reuters-view";
    }

}
