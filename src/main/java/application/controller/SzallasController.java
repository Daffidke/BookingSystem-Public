package application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import application.dao.*;
import application.model.*;

@Controller
public class SzallasController {

    @Autowired
    private SzallasDAO szallasDAO;

    @Autowired
    private FoglalasDAO foglalasDAO;

    @Autowired
    private UserDAO userDAO;

    @GetMapping(value = "/szallasok")
    public String ListSzallas(Model model) {
        List<Szallas> szallasList = szallasDAO.listSzallasok();
        model.addAttribute("szallasok", szallasList);

        List<Foglalas> foglalasok = foglalasDAO.listFoglalasok();
        model.addAttribute("foglalasok", foglalasok);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals("anonymousUser")) {
            model.addAttribute("current_user", new User());
        } else {
            model.addAttribute("current_user", userDAO.getUserByEmail(authentication.getName()));
        }

        return "szallasok";
    }

    @PostMapping(value = "/add")
    public String addSzallas(@RequestParam("nev") String nev, @RequestParam("ar") Double ar,
            @RequestParam("leiras") String leiras,
            @RequestParam("utca") String utca, @RequestParam("isz") int isz, @RequestParam("varos") String varos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.getUserByEmail(currentPrincipalName);
        Szallas szallas = new Szallas(nev, ar, leiras, utca, isz, varos, (double) user.getId());
        szallasDAO.insertSzallas(szallas, user.getId());

        return "newplace";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteSzallas(@PathVariable("id") int id) {
        szallasDAO.deleteSzallas(id);

        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editSzallas(@PathVariable("id") int id, Model model) {
        Szallas szallas = szallasDAO.getSzallasById(id);
        model.addAttribute("szallas", szallas);

        return "update-szallas";
    }

    @PostMapping(value = "/update/{id}")
    public String updateSzallas(@PathVariable("id") int id, @RequestParam("nev") String nev,
            @RequestParam("ar") double ar,
            @RequestParam("leiras") String leiras, @RequestParam("utca") String utca, @RequestParam("isz") int isz,
            @RequestParam("varos") String varos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.getUserByEmail(currentPrincipalName);

        if (user != null && user.getRole().equals("admin")) {
            szallasDAO.updateSzallas(id, ar, nev, leiras, utca, isz, varos);
            return "redirect:/";
        } else {
            return "error";
        }
    }
}