package spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import spring.jpa.model.Cours;
import spring.jpa.service.CourService;

import java.util.List;

@Controller
@RequestMapping("/cours")
public class CourController {

    @Autowired
    private CourService coursService;

    // Afficher tous les cours
    @GetMapping("/index")
    public String index(Model model) {
        List<Cours> cours = coursService.getAllCours();
        model.addAttribute("cours", cours);
        return "cours"; // cours.html
    }

    // Formulaire ajout
    @GetMapping("/form")
    public String formCours(Model model) {
        model.addAttribute("cours", new Cours());
        return "formCour";
    }

    // Sauvegarde
    @PostMapping("/save")
    public String save(@Valid Cours cours, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Cours";
        }
        coursService.createCours(cours);
        return "formCour";
    }

    // Edition cours existant
    @GetMapping("/edit")
    public String edit(@RequestParam("code") String code, Model model) {
        Cours cours = coursService.getCoursByCode(code).orElse(null);
        model.addAttribute("cours", cours);
        return "editCours";
    }

    // Mise à jour
    @PostMapping("/update")
    public String update(@Valid Cours cours, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editCours";
        }
        coursService.createCours(cours); // save() fait create/update
        return "cours";
    }

    // Suppression
    @GetMapping("/delete")
    public String delete(@RequestParam("code") String code) {
        coursService.deleteCours(code);
        return "redirect:/cours/index";
    }
}
