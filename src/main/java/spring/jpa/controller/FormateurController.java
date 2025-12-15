package spring.jpa.controller;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import spring.jpa.model.Formateur;
import spring.jpa.repository.FormateursRepository;

import java.util.List;

@Controller
@RequestMapping("/formateur")
public class FormateurController {

    @Autowired
    private FormateursRepository formateurRepos;

    // 🔹 Afficher tous les formateurs
    @GetMapping("/index")
    public String index(Model model) {
        List<Formateur> formateurs = formateurRepos.findAll();
        model.addAttribute("formateurs", formateurs);
        return "formateurs"; // vue Thymeleaf : formateurs.html
    }

    // 🔹 Formulaire de création
    @GetMapping("/form")
    public String formFormateur(Model model) {
        model.addAttribute("formateur", new Formateur());
        return "formFormateur"; // vue Thymeleaf : formFormateur.html
    }

    // 🔹 Sauvegarde d’un nouveau formateur
    @PostMapping("/save")
    public String save(@Valid Formateur formateur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formFormateur";
        }
        formateurRepos.save(formateur);
        return "confirmation"; // vue confirmation.html
    }

    // 🔹 Edition d’un formateur existant
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Formateur f = formateurRepos.findById(id).orElse(null);
        model.addAttribute("formateur", f);
        return "editFormateur"; // vue editFormateur.html
    }

    // 🔹 Mise à jour d’un formateur
    @PostMapping("/update")
    public String update(@Valid Formateur formateur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editFormateur";
        }
        formateurRepos.save(formateur);
        return "confirmation";
    }

    // 🔹 Suppression d’un formateur
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        formateurRepos.deleteById(id);
        return "redirect:/formateur/index";
    }
}
