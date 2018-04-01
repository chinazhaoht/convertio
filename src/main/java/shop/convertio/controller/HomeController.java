package shop.convertio.controller;


import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "/index.html";
    }
}
