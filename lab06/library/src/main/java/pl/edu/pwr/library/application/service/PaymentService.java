package pl.edu.pwr.library.application.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.library.database.models.Payment;
import pl.edu.pwr.library.database.models.Receivable;
import pl.edu.pwr.library.database.repository.PaymentsRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PaymentService {
    private PaymentsRepository paymentsRepository;
    private ReceivableService receivableService;
    private Clock clock;
    private Logger logger = Logger.getLogger(PaymentService.class.getName());

    public PaymentService(PaymentsRepository paymentsRepository, ReceivableService receivableService, Clock clock) {
        this.paymentsRepository = paymentsRepository;
        this.receivableService = receivableService;
        this.clock = clock;
    }

    public Payment findById(int id){
        Optional<Payment> payment = paymentsRepository.findById(id);
        if(payment.isEmpty()){
            logger.warning("Payment does not exist.");
            return null;
        }
        return payment.get();
    }
    public List<Payment> findAllByReceivable_Id(int id){
        return paymentsRepository.findAllByReceivable_Id(id);
    }
    public List<Payment> findAll(){
        return paymentsRepository.findAll();
    }


    public void modifyPayment(int paymentId,float price) {
        Optional<Payment> payment = paymentsRepository.findById(paymentId);
        if (payment.isEmpty()) {
            logger.warning("The payment does not exist or has been fully payed.");
            return;
        }
        Receivable receivable = payment.get().getReceivables();
        float expectedPrice = receivable.getPrice()-receivable.getAlreadyPayed()-payment.get().getPrice();

        if(expectedPrice <= 0){
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
            return;
        }

        Payment payment2 = new Payment(price, LocalDate.now(clock),receivable.getAbonament(),receivable);
        paymentsRepository.save(payment2);

        if(expectedPrice > price){
            receivable.setPayed(false);
            receivable.setAlreadyPayed(expectedPrice-price);
            receivableService.save(receivable);
        }
        else{
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
        }
    }
    public void addPayment(int receivableId,float price){
        Receivable receivable = receivableService.getReceivableById(receivableId);
        if(receivable ==null){
            logger.warning("The receivable does not exist or has been fully payed.");
            return;
        }
        if(receivable.isPayed()){
            logger.info("The receivable has been fully payed.");
            return;
        }
        float expectedPrice = receivable.getPrice()-receivable.getAlreadyPayed();

        if(expectedPrice <= 0){
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
            return;
        }

        Payment payment = new Payment(price, LocalDate.now(clock),receivable.getAbonament(),receivable);
        paymentsRepository.save(payment);

        if(expectedPrice > price){
            receivable.setPayed(false);
            receivable.setAlreadyPayed(expectedPrice-price);
            receivableService.save(receivable);
        }
        else{
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
        }
    }

    public void deleteAll(){
        paymentsRepository.deleteAll();
    }
}
