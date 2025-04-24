package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.library.application.models.dto.ReceivableDTO;
import pl.edu.pwr.library.application.service.ReceivableService;

import java.util.List;

@RestController
@RequestMapping("/receivable")
public class ReceivableController {

    private final ReceivableService receivableService;

    public ReceivableController(ReceivableService receivableService) {
        this.receivableService = receivableService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ReceivableDTO>> findAll(){
        return receivableService.findAll();
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<ReceivableDTO> findById(@RequestParam int id){
        return receivableService.getReceivableDTOById(id);
    }
}
