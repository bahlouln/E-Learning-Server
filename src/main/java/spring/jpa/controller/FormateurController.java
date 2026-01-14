package spring.jpa.controller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import spring.jpa.model.Formateur;
import spring.jpa.repository.FormateursRepository;
import spring.jpa.service.CourService;
import spring.jpa.service.FormateurService;

import java.util.List;

@Controller
@RequestMapping("/formateur")
public class FormateurController {

    @Autowired
    private FormateursRepository formateurRepos;
    @Autowired
    private CourService courService; 
    @Autowired
    private FormateurService formateurService;
    
    // 🔹 Afficher tous les formateurs
    @GetMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(Model model) {
        List<Formateur> formateurs = formateurRepos.findAll();
        model.addAttribute("formateurs", formateurs);
        return "formateurs"; // vue Thymeleaf : formateurs.html
    }

    // 🔹 Formulaire de création
    @GetMapping("/form")
    public String formFormateur(Model model) {
        model.addAttribute("formateur", new Formateur());
        model.addAttribute("cours", courService.getAllCours());
        return "formFormateur"; // vue Thymeleaf : formFormateur.html
    }
    @PostMapping("/save")
    public String save(@Valid Formateur formateur, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cours", courService.getAllCours());
            return "formFormateur";
        }
        formateurService.createFormateur(formateur); // <-- utilise le service
        return "redirect:/formateur/index";
    }


    // 🔹 Edition d’un formateur existant
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Formateur f = formateurRepos.findById(id).orElse(null);
        model.addAttribute("formateur", f);
        model.addAttribute("cours", courService.getAllCours());
        return "formFormateur"; // 
    }


    // 🔹 Suppression d’un formateur
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        formateurRepos.deleteById(id);
        return "redirect:/formateur/index";
    }
}
