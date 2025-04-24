package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.application.models.payload.ClientPayload;
import pl.edu.pwr.library.application.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ClientDTO>> findAll(){
        return clientService.findAll();
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<ClientDTO> findByID(@RequestParam int id){
        return clientService.findDTOById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClient(@RequestBody ClientDTO clientDTO){
        return clientService.addClient(clientDTO);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyClient(@RequestBody ClientPayload clientPayload){
        return clientService.modifyClient(clientPayload);
    }
}
