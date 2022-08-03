package uz.jl.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.jl.configs.security.UserDetails;

@Controller
public class HomeController {


    @RequestMapping
    @PreAuthorize("permitAll()")
    public String homePage() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/open-1")
    public String open1Page() {
        return "OPEN URL";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String admin() {
        return "ADMIN PAGE";
    }

    @ResponseBody
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping("/mana")
    public String manager() {
        return "MANAGER PAGE";
    }

    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @RequestMapping("/admin-manager")
    public String adminManager() {
        return "ADMIN AND MANAGER PAGE";
    }

    @ResponseBody
    @PreAuthorize("isAuthenticated() || hasAuthority('create')")
    @RequestMapping("/user")
    public String user(@AuthenticationPrincipal UserDetails userDetails) {
        return "" + userDetails;
    }


    @ResponseBody
    @RequestMapping("/create")
    public String hasAuthorityCreate() {
        return "hasAuthorityCreate";
    }


}
