package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.library.application.models.dto.AbonamentDTO;
import pl.edu.pwr.library.application.models.payload.AbonamentPayload;
import pl.edu.pwr.library.application.service.AbonamentService;

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

    @GetMapping("/find-by-id")
    public ResponseEntity<AbonamentDTO> getById(@RequestParam int id){
        return ResponseEntity.ok(abonamentService.findDTOById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAbonament(@RequestBody AbonamentPayload abonamentPayload){
        return abonamentService.addAbonament(abonamentPayload);
    }
}
