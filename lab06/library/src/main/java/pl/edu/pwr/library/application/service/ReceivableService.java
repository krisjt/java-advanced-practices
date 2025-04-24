package pl.edu.pwr.library.application.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.library.database.models.Receivable;
import pl.edu.pwr.library.database.repository.ReceivablesRepository;

import java.time.LocalDate;
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

    public List<Receivable> findAll(){
        return receivablesRepository.findAll();
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

    public void save(Receivable receivable){
        receivablesRepository.save(receivable);
    }
    public void deleteReceivable(Receivable id){
        receivablesRepository.delete(id);
    }
}
