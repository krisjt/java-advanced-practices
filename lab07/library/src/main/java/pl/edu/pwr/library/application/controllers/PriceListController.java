package pl.edu.pwr.library.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.library.application.models.dto.PriceListDTO;
import pl.edu.pwr.library.application.models.payload.PriceListPayload;
import pl.edu.pwr.library.application.service.PriceListService;

import java.util.List;

@RestController
@RequestMapping("/price-list")
public class PriceListController {
    private final PriceListService priceListService;

    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<PriceListDTO>> findAll(){
        return priceListService.getAll();
    }

    @GetMapping("/find-all-by-active")
    public ResponseEntity<List<PriceListDTO>> findAllByActive(@RequestParam boolean active){
        return priceListService.getByActive(active);
    }

    @PutMapping("/set-price")
    public ResponseEntity<String> setPrice(@RequestBody PriceListPayload priceListPayload){
        return priceListService.setPrice(priceListPayload.abonamentType(),priceListPayload.price());
    }
}
