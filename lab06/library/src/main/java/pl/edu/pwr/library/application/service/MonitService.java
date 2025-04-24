package pl.edu.pwr.library.application.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.database.models.Abonament;
import pl.edu.pwr.library.database.models.Receivable;
import pl.edu.pwr.library.database.models.Subaccount;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MonitService {

    private ReceivableService receivableService;
    private AbonamentService abonamentService;
    private PriceListService priceListService;
    private SubaccountService subaccountService;
    private Clock clock;
    private static final org.slf4j.Logger monitLogger = org.slf4j.LoggerFactory.getLogger("monitLogger");
    private static final org.slf4j.Logger escalatedLogger = org.slf4j.LoggerFactory.getLogger("escalatedMonitLogger");

    public MonitService(ReceivableService receivableService, AbonamentService abonamentService, PriceListService priceListService, SubaccountService subaccountService, Clock clock) {
        this.receivableService = receivableService;
        this.abonamentService = abonamentService;
        this.priceListService = priceListService;
        this.subaccountService = subaccountService;
        this.clock = clock;
    }

    @Scheduled(fixedRate = 60_000)
    public void sendMonit(){
        LocalDate today = LocalDate.now(clock);
        List<Receivable> receivableList = receivableService.findAllByPaydayBetweenAndPayed(today,today.plusDays(1),false);
        for(Receivable r: receivableList){
            monitLogger.info("You have less than a day (due day = "+r.getPayday()+") to pay for the receivable: " + r +".");
        }
    }

    @Scheduled(fixedRate = 240_000)
    public void addReceivables(){
        List<Abonament> abonaments = abonamentService.findAll();
        for(Abonament abonament: abonaments) {
            Receivable receivable = new Receivable(priceListService.getPrice(abonament.getAbonamentType(), true).getPrice(), 0.0f, LocalDate.now(clock).plusDays(1), abonament,false);
            receivableService.save(receivable);
        }
    }
    @Scheduled(fixedRate = 60_000)
    public void sendEscalatedMonit(){
        List<Receivable> receivableList = receivableService.findAllByPayDayBeforeAndPayed(LocalDate.now(clock),false);
        for(Receivable r: receivableList){
            if(ChronoUnit.DAYS.between(LocalDate.now(clock),r.getPayday())<-6){
                List<Subaccount> subaccounts = subaccountService.findAllByAbonament(r.getAbonament().getId());
                    for(Subaccount s: subaccounts){
                        s.setActive(false);
                        subaccountService.save(s);
                        escalatedLogger.warn("Your account has been suspended, because u haven't payed for receivable: " + r + ", pay for it and we will activate your account in short time. ");
                    }
            }
            else {
                escalatedLogger.warn("This receivable is past expected payday (days of delay: " + (ChronoUnit.DAYS.between(LocalDate.now(clock), r.getPayday())) + "), you need to pay for abonament. Receivable: " + r + ". Or else your subaccont will be deactivated.");
            }
        }
    }

}
