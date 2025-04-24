package pl.edu.pwr.library.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.AbonamentDTO;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.application.models.dto.SubaccountDTO;
import pl.edu.pwr.library.database.models.Abonament;
import pl.edu.pwr.library.database.models.Subaccount;
import pl.edu.pwr.library.database.repository.SubaccountRepository;

import java.util.ArrayList;
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

    public ResponseEntity<List<SubaccountDTO>> findAll(){
        List<Subaccount> subaccounts = subaccountRepository.findAll();
        List<SubaccountDTO> subaccountDTOS = new ArrayList<>();
        subaccounts.forEach(subaccount -> subaccountDTOS.add(new SubaccountDTO(
                subaccount.isActive(),
                subaccount.getLogin(),
                new AbonamentDTO(
                        subaccount.getAbonament().getAbonamentType(),
                        new ClientDTO(
                                subaccount.getAbonament().getClient().getName(),
                                subaccount.getAbonament().getClient().getSurname()
                        )
                )
        )));
        return ResponseEntity.ok(subaccountDTOS);
    }

    public List<Subaccount> findAllByAbonament(int abonamentId){
        return subaccountRepository.findAllByAbonament_Id(abonamentId);
    }

    public ResponseEntity<String> modifySubaccount(int accountId, String login, String password){
        Optional<Subaccount> subaccount = subaccountRepository.findById(accountId);
        if(subaccount.isEmpty()){
            logger.warning("Subaccount does not exist.");
            return new ResponseEntity<>("Subaccount doesn't exist", HttpStatus.NOT_FOUND);
        }
        subaccount.get().setLogin(login);
        subaccount.get().setPassword(password);
        subaccountRepository.save(subaccount.get());
        logger.info("Subaccount has been modified.");
        return ResponseEntity.ok("Subaccount modified.");
    }

    public ResponseEntity<String> addSubaccount(int abonamentId, String login, String password){
        Abonament abonament = abonamentService.findById(abonamentId);
        if(abonament==null){
            logger.warning("Abonament does not exist.");
            return new ResponseEntity<>("Abonament doesn't exist",HttpStatus.NOT_FOUND);
        }
        subaccountRepository.save(new Subaccount(login,password,true,abonament));
        logger.info("Subaccount has been added.");
        return ResponseEntity.ok("Subaccount added.");
    }

    public void save(Subaccount subaccount){
        subaccountRepository.save(subaccount);
    }

}
