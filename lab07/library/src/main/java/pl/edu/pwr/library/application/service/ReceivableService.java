package pl.edu.pwr.library.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.AbonamentDTO;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.application.models.dto.ReceivableDTO;
import pl.edu.pwr.library.database.models.Receivable;
import pl.edu.pwr.library.database.repository.ReceivablesRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReceivableService {

    private ReceivablesRepository receivablesRepository;
    private Logger logger = Logger.getLogger(ReceivableService.class.getName());

    public ReceivableService(ReceivablesRepository receivablesRepository) {
        this.receivablesRepository = receivablesRepository;
    }

    public ResponseEntity<List<ReceivableDTO>> findAll(){
        List<Receivable> receivables = receivablesRepository.findAll();
        List<ReceivableDTO> receivableDTOS = new ArrayList<>();
        receivables.forEach(receivable -> receivableDTOS.add(
                new ReceivableDTO(
                        receivable.isPayed(),
                        receivable.getAlreadyPayed(),
                        receivable.getPrice(),
                        receivable.getPayday(),
                        new AbonamentDTO(
                                receivable.getAbonament().getAbonamentType(),
                                new ClientDTO(
                                        receivable.getAbonament().getClient().getName(),
                                        receivable.getAbonament().getClient().getSurname()
                                )
                        )
                )
        ));
        return ResponseEntity.ok(receivableDTOS);
    }
    public List<Receivable> findAllByPaydayBetweenAndPayed(LocalDate from, LocalDate to, boolean payed){
        return receivablesRepository.findAllByPaydayIsBetweenAndPayed(from,to,payed);
    }

    public List<Receivable> findAllByPayDayBeforeAndPayed(LocalDate date,boolean payed){
        return receivablesRepository.findByPaydayBeforeAndPayed(date,payed);
    }

    public Receivable getReceivableById(int id){
        Optional<Receivable> receivables = receivablesRepository.findById(id);
        if(receivables.isEmpty()){
            logger.warning("Receivable does not exist.");
            return null;
        }

        return receivables.get();
    }

    public ResponseEntity<ReceivableDTO> getReceivableDTOById(int id) {
        Optional<Receivable> receivables = receivablesRepository.findById(id);
        if (receivables.isEmpty()) {
            logger.warning("Receivable does not exist.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ReceivableDTO(
                receivables.get().isPayed(),
                receivables.get().getAlreadyPayed(),
                receivables.get().getPrice(),
                receivables.get().getPayday(),
                new AbonamentDTO(
                        receivables.get().getAbonament().getAbonamentType(),
                        new ClientDTO(
                                receivables.get().getAbonament().getClient().getName(),
                                receivables.get().getAbonament().getClient().getSurname()
                        ))));
    }

    public void save(Receivable receivable){
        receivablesRepository.save(receivable);
    }
    public void deleteReceivable(Receivable id){
        receivablesRepository.delete(id);
    }
}
