package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.library.application.models.AbonamentDTO;
import pl.edu.pwr.library.application.service.AbonamentService;
import pl.edu.pwr.library.database.models.Abonament;

import java.util.List;

@RestController
@RequestMapping("/abonament")
public class AbonamentController {

    private final AbonamentService abonamentService;

    public AbonamentController(AbonamentService abonamentService) {
        this.abonamentService = abonamentService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<AbonamentDTO>> getAll(){
        return ResponseEntity.ok(abonamentService.findAllDTO());
    }
}
