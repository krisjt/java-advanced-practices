package pl.edu.pwr.library.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.AbonamentDTO;
import pl.edu.pwr.library.application.models.payload.AbonamentPayload;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.database.models.Abonament;
import pl.edu.pwr.library.database.models.Client;
import pl.edu.pwr.library.database.repository.AbonamentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AbonamentService {
    private AbonamentRepository abonamentRepository;
    private ClientService clientService;
    private Logger logger = Logger.getLogger(AbonamentService.class.getName());

    public AbonamentService(AbonamentRepository abonamentRepository, ClientService clientService) {
        this.abonamentRepository = abonamentRepository;
        this.clientService = clientService;
    }

    public List<Abonament> getAllAbonamentByUserID(int userID){
        return abonamentRepository.findAllByClient_Id(userID);
    }

    public List<Abonament> findAll(){
        return abonamentRepository.findAll();
    }

    public List<AbonamentDTO> findAllDTO(){
        List<Abonament> abonaments = abonamentRepository.findAll();
        System.out.println(abonaments);
        List<AbonamentDTO> dtos =  new ArrayList<>();
        abonaments.forEach(abonament -> dtos.add(new AbonamentDTO(
                abonament.getAbonamentType(),
                new ClientDTO(
                        abonament.getClient().getName(),
                        abonament.getClient().getSurname())
        )));
        System.out.println(dtos);
        return dtos;
    }
    public AbonamentDTO findDTOById(int id) {
        try {
            Optional<Abonament> abonament = abonamentRepository.findById(id);
            if (abonament.isPresent()) {
                logger.info("Abonament found");
                return new AbonamentDTO(
                        abonament.get().getAbonamentType(),
                        new ClientDTO(
                                abonament.get().getClient().getName(),
                                abonament.get().getClient().getSurname())
                );
            } else {
                logger.warning("Abonament could be found.");
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while searching for abonamnt: ", e.getMessage());
            return null;
        }
    }
    public Abonament findById(int id) {
        try {
            Optional<Abonament> abonament = abonamentRepository.findById(id);
            if (abonament.isPresent()) {
                logger.info("Abonament found");
                return abonament.get();
            } else {
                logger.warning("Abonament could be found.");
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while searching for abonamnt: ", e.getMessage());
            return null;
        }
    }

    public ResponseEntity<String> addAbonament(AbonamentPayload abonamentPayload){
        Client client = clientService.findById(abonamentPayload.clientID());
        if(client==null){
            logger.warning("Client could be found.");
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
        Abonament abonament = new Abonament(abonamentPayload.type(),client);
        if(abonament==null){
            logger.warning("Abonament couldn't be found");
            return new ResponseEntity<>("Abonament not found", HttpStatus.NOT_FOUND);
        }
        abonamentRepository.save(abonament);
        logger.warning("Saved new Abonaemnt to db.");
        return new ResponseEntity<>("Abonament saved", HttpStatus.NOT_FOUND);
    }

}

