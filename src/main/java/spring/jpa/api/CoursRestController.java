package spring.jpa.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import spring.jpa.model.Cours;
import spring.jpa.service.CourService;

@RestController
@RequestMapping("/api/cours")
public class CoursRestController {

    private final CourService coursService;

    public CoursRestController(CourService coursService) {
        this.coursService = coursService;
    }

    @GetMapping
    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }
}
