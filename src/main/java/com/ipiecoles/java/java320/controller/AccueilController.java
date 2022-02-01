package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccueilController {

    @Autowired
    private EmployeService employeService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/"
    )
    public String index(final ModelMap model){
        model.put("number", employeService.countAllEmploye());
        return "accueil";
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/accueil"
    )
    public ModelAndView accueil(){
        ModelAndView model = new ModelAndView("accueil");
        model.addObject("number", employeService.countAllEmploye());
        return model;
    }
}
