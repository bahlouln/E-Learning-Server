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
    public String save(@Valid @ModelAttribute("cours") Cours cours, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formCour";
        }
        
        // Si l'id existe, c'est une mise à jour, sinon c'est une création
        if (cours.getId() != null) {
            coursService.updateCours(cours.getId(), cours);
        } else {
            coursService.createCours(cours);
        }
        
        return "redirect:/cours/index";
    }

    // Edition cours existant
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Cours cours = coursService.getCoursByID(id)
                .orElseThrow(() -> new IllegalArgumentException("Cours introuvable avec l'id : " + id));
        model.addAttribute("cours", cours);
        return "formCour";
    }



    // Suppression
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        coursService.deleteCours(id);
        return "redirect:/cours/index";
    }
}
