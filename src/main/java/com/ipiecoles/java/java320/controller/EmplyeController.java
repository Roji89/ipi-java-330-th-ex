package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Commercial;
import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class EmplyeController {

    @Autowired
    private EmployeService employeService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes/{id}"
    )
    public ModelAndView detailEmploye(@PathVariable Long id){
        ModelAndView model = new ModelAndView("detail");
        model.addObject("employe", employeService.findById(id));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes",
            params = "matricule"
    )
    public ModelAndView searchByMatricule(@RequestParam String matricule){
        ModelAndView model = new ModelAndView("detail");
        model.addObject("employe", employeService.findMyMatricule(matricule));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes"
    )
    public ModelAndView getAllEmployes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "Id") String sortProperty,
            @RequestParam(defaultValue = "ASC") String  sortDirection
    ){
        Page<Employe> employes = employeService.findAllEmployes(page, size, sortProperty, sortDirection);
        ModelAndView model = new ModelAndView("list");
        model.addObject("start", page * size + 1);
        model.addObject("end", page * size + employes.getNumberOfElements());
        model.addObject("employes", employes);
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/employes/new/{typeEmploye}"
    )
    public ModelAndView newEmploye(@PathVariable String typeEmploye){
        ModelAndView modelAndView = new ModelAndView("detail");
        switch (typeEmploye.toLowerCase()){
            case "commercial":
                modelAndView.addObject("employe", new Commercial());
                break;
            case "technicien":
                modelAndView.addObject("employe", new Technicien());
                break;
            case "manager":
                modelAndView.addObject("employe", new Manager());
                break;
        }
        return modelAndView;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/employes/technicien",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView createTechnicien(Technicien technicien){
        if(technicien.getId() == null){
            //Création
            technicien = employeService.creerEmploye(technicien);
        }
        else {
            //Modification
            technicien = employeService.updateEmploye(technicien.getId(), technicien);
        }
        //Redirection vers /employes/id
        return new RedirectView("/employes/" + technicien.getId());
    }

}
