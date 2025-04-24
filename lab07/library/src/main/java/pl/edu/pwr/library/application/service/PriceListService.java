package pl.edu.pwr.library.application.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.PriceListDTO;
import pl.edu.pwr.library.database.models.PriceList;
import pl.edu.pwr.library.database.models.Type;
import pl.edu.pwr.library.database.repository.PriceListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PriceListService {
    private PriceListRepository priceListRepository;
    private Logger logger = Logger.getLogger(PriceListService.class.getName());

    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    public PriceList getPrice(Type abonamentType, boolean active){
        return priceListRepository.findByAbonamentTypeAndActive(abonamentType,active);
    }

    public ResponseEntity<List<PriceListDTO>> getAll(){
        List<PriceList> priceLists = priceListRepository.findAll();
        List<PriceListDTO> priceListDTOS = new ArrayList<>();
        priceLists.forEach(priceList -> priceListDTOS.add(
                new PriceListDTO(priceList.isActive(),priceList.getPrice(),priceList.getAbonamentType())
        ));
        return ResponseEntity.ok(priceListDTOS);
    }

    public ResponseEntity<List<PriceListDTO>> getByActive(boolean active){
        List<PriceList> priceLists = priceListRepository.findByActive(active);
        List<PriceListDTO> priceListDTOS = new ArrayList<>();
        priceLists.forEach(priceList -> priceListDTOS.add(
                new PriceListDTO(priceList.isActive(),priceList.getPrice(),priceList.getAbonamentType())
        ));
        return ResponseEntity.ok(priceListDTOS);
    }
    public ResponseEntity<String> setPrice(Type abonamentTypeTy, float price){
        List<PriceList> list = priceListRepository.findAllByAbonamentTypeAndActive(abonamentTypeTy,true);
        if(!list.isEmpty()){
            logger.info("Deactivating previous price...");
            for(PriceList pl: list){
                pl.setActive(false);
                priceListRepository.save(pl);
            }
        }
        priceListRepository.save(new PriceList(price,true,abonamentTypeTy));
        return ResponseEntity.ok("New price has been set.");
    }

}
