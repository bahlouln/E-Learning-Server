package spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import spring.jpa.model.Groupe;
import spring.jpa.service.GroupeService;
import spring.jpa.service.CourService;
import spring.jpa.service.EtudiantService;

import java.util.List;

@Controller
@RequestMapping("/groupe")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private CourService courService;

    @Autowired
    private EtudiantService etudiantService;

    // 🔹 Afficher tous les groupes
    @GetMapping("/index")
    public String index(Model model) {
        List<Groupe> groupes = groupeService.getAllGroupes();
        model.addAttribute("groupes", groupes);
        return "groupe"; // Vue Thymeleaf : groupes.html
    }

    // 🔹 Formulaire de création
    @GetMapping("/form")
    public String formGroupe(Model model) {
        model.addAttribute("groupe", new Groupe());
        model.addAttribute("cours", courService.getAllCours());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        return "formGroupe"; // Vue Thymeleaf : formGroupe.html
    }

    // 🔹 Sauvegarde
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("groupe") Groupe groupe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cours", courService.getAllCours());
            model.addAttribute("etudiants", etudiantService.getAllEtudiants());
            return "formGroupe";
        }
        groupeService.createGroupe(groupe);
        return "redirect:/groupe/index";
    }

    // 🔹 Edition
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Groupe groupe = groupeService.getGroupeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + id));
        model.addAttribute("groupe", groupe);
        model.addAttribute("cours", courService.getAllCours());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        return "formGroupe";
    }

    // 🔹 Suppression
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        groupeService.deleteGroupe(id);
        return "redirect:/groupe/index";
    }
    @GetMapping("/etudiants") 
    public String getEtudiantsByGroupe(@RequestParam("id") Long id, Model model) { 
    	Groupe groupe = groupeService.getGroupeById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + id));
    	model.addAttribute("groupe", groupe);
    	model.addAttribute("etudiants", groupeService.getEtudiantsByGroupe(id));
    	return "groupeEtudiants"; 
    }
 // 🔹 Formulaire pour ajouter un étudiant
    @GetMapping("/addEtudiantForm")
    public String addEtudiantForm(@RequestParam("id") Long id, Model model) {
        Groupe groupe = groupeService.getGroupeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable avec l'id : " + id));
        model.addAttribute("groupe", groupe);
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        return "addEtudiantToGroupe"; // Vue Thymeleaf
    }

    // 🔹 Sauvegarde de l’ajout
    @PostMapping("/addEtudiant")
    public String addEtudiant(@RequestParam("id") Long id,
                              @RequestParam("matricule") String matricule,
                              Model model) {
        try {
            groupeService.addEtudiantToGroupe(id, matricule);
            return "redirect:/groupe/etudiants?id=" + id;
        } catch (IllegalStateException e) {
            // On renvoie le message d’erreur à la vue
            Groupe groupe = groupeService.getGroupeById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable"));
            model.addAttribute("groupe", groupe);
            model.addAttribute("etudiants", groupeService.getEtudiantsByGroupe(id));
            model.addAttribute("errorMessage", e.getMessage());
            return "groupeEtudiants"; // on revient sur la liste avec le message
        }
    }
    @GetMapping("/removeEtudiant")
    public String removeEtudiant(@RequestParam("id") Long id,
                                 @RequestParam("matricule") String matricule) {
        groupeService.removeEtudiantFromGroupe(id, matricule);
        return "redirect:/groupe/etudiants?id=" + id;
    }



}
