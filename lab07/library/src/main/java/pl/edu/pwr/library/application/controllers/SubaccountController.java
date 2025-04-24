package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.library.application.models.dto.SubaccountDTO;
import pl.edu.pwr.library.application.models.payload.SubaccountPayload;
import pl.edu.pwr.library.application.service.SubaccountService;

import java.util.List;

@RestController
@RequestMapping("/subaccount")
public class SubaccountController {
    private final SubaccountService subaccountService;

    public SubaccountController(SubaccountService subaccountService) {
        this.subaccountService = subaccountService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<SubaccountDTO>> findAll(){
        return subaccountService.findAll();
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifySubaccount(@RequestBody SubaccountPayload subaccountPayload, @RequestParam int id){
        return subaccountService.modifySubaccount(id,subaccountPayload.login(),subaccountPayload.password());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSubaccount(@RequestBody SubaccountPayload subaccountPayload, @RequestParam int id){
        return subaccountService.addSubaccount(id,subaccountPayload.login(),subaccountPayload.password());
    }
}
