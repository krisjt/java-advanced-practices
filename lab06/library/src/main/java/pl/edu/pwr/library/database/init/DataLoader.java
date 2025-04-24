//package pl.edu.pwr.library.init;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;
//import pl.edu.pwr.library.models.*;
//import pl.edu.pwr.library.repository.*;
//
//import java.time.LocalDate;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private AbonamentRepository abonamentRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private PriceListRepository priceListRepository;
//
//    @Autowired
//    private ReceivablesRepository receivablesRepository;
//
//    @Autowired
//    private SubaccountRepository subaccountRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Client client = new Client();
//        client.setName("Jan");
//        client.setSurname("Kowalski");
//        clientRepository.save(client);
//
//        Client client2 = new Client();
//        client2.setName("Maria");
//        client2.setSurname("Kowalska");
//        clientRepository.save(client2);
//
//        Abonament abonament = new Abonament();
//        abonament.setAbonamentType(Type.PREMIUM);
//        abonament.setClient(client);
//        abonamentRepository.save(abonament);
//
//        Abonament abonament2 = new Abonament();
//        abonament2.setAbonamentType(Type.BASIC);
//        abonament2.setClient(client2);
//        abonamentRepository.save(abonament2);
//
//        PriceList priceList = new PriceList();
//        priceList.setPrice(100.0f);  // Cena
//        priceList.setActive(true);
//        priceList.setAbonamentType(Type.PREMIUM);
//        priceListRepository.save(priceList);
//
//        PriceList priceList2 = new PriceList();
//        priceList2.setPrice(55.0f);  // Cena
//        priceList2.setActive(true);
//        priceList2.setAbonamentType(Type.BASIC);
//        priceListRepository.save(priceList2);
//
//        Receivable receivable = new Receivable();
//        receivable.setPrice(100.0f);  // Cena należności
//        receivable.setPayday(LocalDate.now().minusDays(3));
//        receivable.setAbonament(abonamentRepository.findAll().get(0));
//        receivable.setPayed(false);
//        receivable.setAlreadyPayed(0);
//        receivablesRepository.save(receivable);
//
//        Receivable receivable2 = new Receivable();
//        receivable2.setPrice(250.0f);  // Cena należności
//        receivable2.setPayday(LocalDate.now().minusDays(5));  // Termin należności
//        receivable2.setAbonament(abonamentRepository.findAll().get(1));
//        receivable2.setPayed(false);
//        receivable2.setAlreadyPayed(0);
//        receivablesRepository.save(receivable2);
//
//        Subaccount subaccount = new Subaccount("janek", "haslo123", true, abonament);
//        subaccountRepository.save(subaccount);
//
//        Subaccount subaccount2 = new Subaccount("jan2345", "haslo2345", true, abonament);
//        subaccountRepository.save(subaccount2);
//    }
//}
