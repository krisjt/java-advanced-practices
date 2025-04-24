package pl.edu.pwr.library.application.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.library.database.models.Abonament;
import pl.edu.pwr.library.database.models.Subaccount;
import pl.edu.pwr.library.database.repository.SubaccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SubaccountService {
    private SubaccountRepository subaccountRepository;
    private AbonamentService abonamentService;
    private Logger logger = Logger.getLogger(SubaccountService.class.getName());

    public SubaccountService(SubaccountRepository subaccountRepository, AbonamentService abonamentService) {
        this.subaccountRepository = subaccountRepository;
        this.abonamentService = abonamentService;
    }

    public List<Subaccount> findAll(){
        return subaccountRepository.findAll();
    }

    public List<Subaccount> findAllByAbonament(int abonamentId){
        return subaccountRepository.findAllByAbonament_Id(abonamentId);
    }

    public void modifySubaccount(int accountId, String login, String password){
        Optional<Subaccount> subaccount = subaccountRepository.findById(accountId);
        if(subaccount.isEmpty()){
            logger.warning("Subaccount does not exist.");
            return;
        }
        subaccount.get().setLogin(login);
        subaccount.get().setPassword(password);
        subaccountRepository.save(subaccount.get());
        logger.info("Subaccount has been modified.");
    }

    public void addSubaccount(int abonamentId, String login, String password){
        Abonament abonament = abonamentService.findById(abonamentId);
        if(abonament==null){
            logger.warning("Abonament does not exist.");
            return;
        }
        subaccountRepository.save(new Subaccount(login,password,true,abonament));
        logger.info("Subaccount has been added.");
    }

    public void save(Subaccount subaccount){
        subaccountRepository.save(subaccount);
    }

}
