package pl.edu.pwr.library.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.AbonamentDTO;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.application.models.dto.PaymentDTO;
import pl.edu.pwr.library.application.models.dto.ReceivableDTO;
import pl.edu.pwr.library.database.models.Payment;
import pl.edu.pwr.library.database.models.Receivable;
import pl.edu.pwr.library.database.repository.PaymentsRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public ResponseEntity<PaymentDTO> findById(int id){
        Optional<Payment> payment = paymentsRepository.findById(id);
        if(payment.isEmpty()){
            logger.warning("Payment does not exist.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        PaymentDTO paymentDTO = new PaymentDTO(
                payment.get().getPrice(),
                payment.get().getPayday(),
                new ReceivableDTO(
                        payment.get().getReceivables().isPayed(),
                        payment.get().getReceivables().getAlreadyPayed(),
                        payment.get().getReceivables().getPrice(),
                        payment.get().getReceivables().getPayday(),
                        new AbonamentDTO(
                                payment.get().getReceivables().getAbonament().getAbonamentType(),
                                new ClientDTO(
                                        payment.get().getReceivables().getAbonament().getClient().getName(),
                                        payment.get().getReceivables().getAbonament().getClient().getSurname())
                        ))
        );
        return ResponseEntity.ok(paymentDTO);
    }
    public ResponseEntity<List<PaymentDTO>> findAllByReceivable_Id(int id){
        List<Payment> payments = paymentsRepository.findAllByReceivable_Id(id);
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        payments.forEach(payment -> paymentDTOS.add(
                new PaymentDTO(
                        payment.getPrice(),
                        payment.getPayday(),
                        new ReceivableDTO(
                                payment.getReceivables().isPayed(),
                                payment.getReceivables().getAlreadyPayed(),
                                payment.getReceivables().getPrice(),
                                payment.getReceivables().getPayday(),
                                new AbonamentDTO(
                                        payment.getReceivables().getAbonament().getAbonamentType(),
                                        new ClientDTO(
                                                payment.getReceivables().getAbonament().getClient().getName(),
                                                payment.getReceivables().getAbonament().getClient().getSurname())
                                ))
                )));
        return ResponseEntity.ok(paymentDTOS);
    }

    public ResponseEntity<List<PaymentDTO>> findAll(){
        List<Payment> payments = paymentsRepository.findAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        payments.forEach(payment -> paymentDTOS.add(
                new PaymentDTO(
                        payment.getPrice(),
                        payment.getPayday(),
                        new ReceivableDTO(
                                payment.getReceivables().isPayed(),
                                payment.getReceivables().getAlreadyPayed(),
                                payment.getReceivables().getPrice(),
                                payment.getReceivables().getPayday(),
                                new AbonamentDTO(
                                        payment.getReceivables().getAbonament().getAbonamentType(),
                                new ClientDTO(
                                        payment.getReceivables().getAbonament().getClient().getName(),
                                        payment.getReceivables().getAbonament().getClient().getSurname())
                                ))
                )));
        return ResponseEntity.ok(paymentDTOS);
    }

    public ResponseEntity<String> modifyPayment(int paymentId,float price) {
        Optional<Payment> payment = paymentsRepository.findById(paymentId);
        if (payment.isEmpty()) {
            logger.warning("The payment does not exist or has been fully payed.");
            return new ResponseEntity<>("The payment does not exist or has been fully payed.",HttpStatus.CONFLICT);
        }

        Receivable receivable = payment.get().getReceivables();
        float expectedPrice = receivable.getPrice()-receivable.getAlreadyPayed()+payment.get().getPrice();

        if(expectedPrice <= 0){
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
        }

        if(expectedPrice > price){
            receivable.setPayed(false);
            receivable.setAlreadyPayed(expectedPrice-price);
        }
        else{
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
        }
        receivableService.save(receivable);
        payment.get().setPrice(price);
        payment.get().setPayday(LocalDate.now(clock));
        payment.get().setReceivables(receivable);
        paymentsRepository.save(payment.get());

        return ResponseEntity.ok("Payment modified");
    }

    public ResponseEntity<String> addPayment(int receivableId,float price){
        Receivable receivable = receivableService.getReceivableById(receivableId);
        if(receivable ==null){
            logger.warning("The receivable does not exist or has been fully payed.");
            return new ResponseEntity<>("The payment does not exist or has been fully payed.",HttpStatus.CONFLICT);
        }
        if(receivable.isPayed()){
            logger.info("The receivable has been fully payed.");
            return ResponseEntity.ok("The receivable is already payed");
        }
        float expectedPrice = receivable.getPrice()-receivable.getAlreadyPayed();

        if(expectedPrice <= 0){
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
            receivableService.save(receivable);
            return ResponseEntity.ok("Payment added");
        }

        if(expectedPrice > price){
            receivable.setPayed(false);
            receivable.setAlreadyPayed(receivable.getAlreadyPayed()+price);
        }
        else{
            receivable.setPayed(true);
            receivable.setAlreadyPayed(receivable.getPrice());
        }
        receivableService.save(receivable);

        Payment payment = new Payment(price, LocalDate.now(clock),receivable);
        paymentsRepository.save(payment);

        return ResponseEntity.ok("Payment added");
    }

    public void deleteAll(){
        paymentsRepository.deleteAll();
    }
}
