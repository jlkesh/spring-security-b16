package uz.jl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


    @RequestMapping
    public String homePage() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/open-1")
    public String open1Page() {
        return "OPEN URL";
    }

    @ResponseBody
    @RequestMapping("/admin")
    public String admin() {
        return "ADMIN PAGE";
    }

    @ResponseBody
    @RequestMapping("/mana")
    public String manager() {
        return "MANAGER PAGE";
    }

    @ResponseBody
    @RequestMapping("/admin-manager")
    public String adminManager() {
        return "ADMIN AND MANAGER PAGE";
    }

    @ResponseBody
    @RequestMapping("/user")
    public String user() {
        return "USER PAGE";
    }


    @ResponseBody
    @RequestMapping("/create")
    public String hasAuthorityCreate() {
        return "hasAuthorityCreate";
    }


}
