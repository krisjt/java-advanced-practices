package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.library.application.models.dto.PaymentDTO;
import pl.edu.pwr.library.application.models.payload.PaymentPayload;
import pl.edu.pwr.library.application.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<PaymentDTO>> findAll(){
        return paymentService.findAll();
    }

    @GetMapping("/find-all-by-receivable")
    public ResponseEntity<List<PaymentDTO>> findAll(@RequestParam int id){
        return paymentService.findAllByReceivable_Id(id);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<PaymentDTO> findById(@RequestParam int id){
        return paymentService.findById(id);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyPayment(@RequestBody PaymentPayload paymentPayload){
        return paymentService.modifyPayment(paymentPayload.id(), paymentPayload.price());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@RequestBody PaymentPayload paymentPayload){
        return paymentService.addPayment(paymentPayload.id(),paymentPayload.price());
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        paymentService.deleteAll();
    }
}
//TODO modifying