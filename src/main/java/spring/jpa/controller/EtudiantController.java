package spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import spring.jpa.model.Etudiant;
import spring.jpa.service.EtudiantService;
import spring.jpa.service.CourService;
import spring.jpa.service.GroupeService;

import java.util.List;

@Controller
@RequestMapping("/etudiant")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private CourService courService;

    @Autowired
    private GroupeService groupeService;

    // 🔹 Afficher tous les étudiants
    @GetMapping("/index")
    public String index(Model model) {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        model.addAttribute("etudiants", etudiants);
        return "etudiants"; // Vue Thymeleaf : etudiants.html
    }

    // 🔹 Formulaire de création
    @GetMapping("/form")
    public String formEtudiant(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        model.addAttribute("cours", courService.getAllCours());
        model.addAttribute("groupes", groupeService.getAllGroupes());
        return "formEtudiant"; // Vue Thymeleaf : formEtudiant.html
    }

    // 🔹 Sauvegarde
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("etudiant") Etudiant etudiant, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cours", courService.getAllCours());
            model.addAttribute("groupes", groupeService.getAllGroupes());
            return "formEtudiant";
        }
        etudiantService.createEtudiant(etudiant);
        return "redirect:/etudiant/index";
    }

    // 🔹 Edition
    @GetMapping("/edit")
    public String edit(@RequestParam("matricule") String matricule, Model model) {
        Etudiant etudiant = etudiantService.getEtudiantByMatricule(matricule)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable avec le matricule : " + matricule));
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("cours", courService.getAllCours());
        model.addAttribute("groupes", groupeService.getAllGroupes());
        return "formEtudiant";
    }

    // 🔹 Suppression
    @GetMapping("/delete")
    public String delete(@RequestParam("matricule") String matricule) {
        etudiantService.deleteEtudiant(matricule);
        return "redirect:/etudiant/index";
    }
}
