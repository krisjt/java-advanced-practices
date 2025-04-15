package pl.edu.pwr.library.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.library.models.PriceList;
import pl.edu.pwr.library.models.Type;
import pl.edu.pwr.library.repository.PriceListRepository;

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

    public List<PriceList> getAll(){
        return priceListRepository.findAll();
    }

    public List<PriceList> getByActive(boolean active){
        return priceListRepository.findByActive(active);
    }
    public void setPrice(Type abonamentTypeTy, float price){
//        AbonamentType abonamentType = abonamentTypeService.getAbonamentType(abonamentTypeTy);
//        if(abonamentType==null){
//            logger.warning("Abonament type couldn't be found.");
//            return;
//        }
        List<PriceList> list = priceListRepository.findAllByAbonamentTypeAndActive(abonamentTypeTy,true);
        if(!list.isEmpty()){
            logger.info("Deactivating previous price...");
            for(PriceList pl: list){
                pl.setActive(false);
                priceListRepository.save(pl);
            }
        }
        priceListRepository.save(new PriceList(price,true,abonamentTypeTy));
    }

}
