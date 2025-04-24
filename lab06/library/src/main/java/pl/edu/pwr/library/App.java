package pl.edu.pwr.library;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pwr.library.application.service.*;
import pl.edu.pwr.library.database.models.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

import static pl.edu.pwr.library.database.models.Type.BASIC;

@SpringBootApplication
public class App {

    private ClientService clientService;
    private AbonamentService abonamentService;
    private SubaccountService subaccountService;
    private PriceListService priceListService;
    private ReceivableService receivableService;
    private PaymentService paymentService;
    private Clock clock;
    private Logger logger = Logger.getLogger(App.class.getName());

    public App(ClientService clientService, AbonamentService abonamentService, SubaccountService subaccountService, PriceListService priceListService, ReceivableService receivableService, PaymentService paymentService, Clock clock) {
        this.clientService = clientService;
        this.abonamentService = abonamentService;
        this.subaccountService = subaccountService;
        this.priceListService = priceListService;
        this.receivableService = receivableService;
        this.paymentService = paymentService;
        this.clock = clock;
    }

    private void clientSection(){
        List<Client> clientList;
        List<Abonament> abonaments;
        while(true){
        System.out.println("---------------------");
        System.out.println("CLIENT MENU");
        System.out.println("---------------------");
        System.out.println("Choose what u want to do: \n" +
                "1. Show all clients\n" +
                "2. Show client abonaments\n" +
                "3. Edit client with id\n" +
                "4. Add new client\n" +
                "5. Abonament section\n" +
                "6. Return\n" +
                "default. Exit");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        switch(selection) {
            case "1": {
                clientList = clientService.findAll();
                System.out.println();
                System.out.println("Clients:");
                for (Client client : clientList) {
                    System.out.println("ID: " + client.getId() + ", Name: " + client.getName() + ", Surname = " + client.getSurname());
                }
                System.out.println();
                break;
            }
            case "2": {
                System.out.println("Enter id of client whose abonaments u want to be listed:");
                String clientId = scanner.next();
                abonaments = abonamentService.getAllAbonamentByUserID(Integer.valueOf(clientId));
                System.out.println();
                System.out.println("Abonaments:");
                for (Abonament abonament : abonaments) {
                    System.out.println("ID: " + abonament.getId() + ", type: " + abonament.getAbonamentType());
                }
                System.out.println();
                break;
            }
            case "3": {
                System.out.println("Enter id of client u want to edit:");
                String id = scanner.next();
                Client client = clientService.findById(Integer.valueOf(id));
                if(client == null){
                    logger.info("Client is null.");
                    break;
                }
                System.out.println("Chosen client:\nID: " + client.getId() + ", Name: " + client.getName() + ", Surname = " + client.getSurname());
                System.out.println("Enter new name:");
                String name = scanner.next();
                System.out.println("Enter new surname:");
                String surname = scanner.next();
                clientService.modifyClient(Integer.valueOf(id),name,surname);
                client = clientService.findById(Integer.valueOf(id));
                System.out.println("Client after  modification:\nID: " + client.getId() + ", Name: " + client.getName() + ", Surname = " + client.getSurname());
                System.out.println();
                break;
            }
            case "4": {
                System.out.println("Enter name for a new client:");
                String newName = scanner.next();
                System.out.println("Enter surname for a new client:");
                String newSurname = scanner.next();
                clientService.addClient(new Client(newName,newSurname));
                System.out.println();
                break;
            }
            case "5":{
                abonamentSection();
            }
            case "6":{
                menu();
            }
            default: {
                System.exit(0);
            }
        }
        }
    }

    private void abonamentSection() {
        List<Abonament> abonaments;
        List<Client> clientList;
        List<Subaccount> subaccounts;
        while(true){
            System.out.println("---------------------");
            System.out.println("ABONAMENT MENU");
            System.out.println("---------------------");
            System.out.println("Choose what u want to do: \n" +
                    "1. Show all abonaments\n" +
                    "2. Show subaccounts related to abonament\n" +
                    "3. Add new abonament\n" +
                    "4. Subaccount section\n" +
                    "5. List all clients\n" +
                    "6. Return\n" +
                    "default. Exit");

            Scanner scanner = new Scanner(System.in);
            String selection = scanner.next();
            switch(selection) {
                case "1": {
                    abonaments = abonamentService.findAll();
                    System.out.println();
                    System.out.println("Abonaments:");
                    for (Abonament abonament : abonaments) {
                        System.out.println("ID: " + abonament.getId() + ", type: " + abonament.getAbonamentType() + ", client = " + abonament.getClient());
                    }
                    System.out.println();
                    break;
                }
                case "2": {
                    System.out.println("Enter id of abonament which subacounts u want to be listed:");
                    String subaccountid = scanner.next();
                    subaccounts = subaccountService.findAllByAbonament(Integer.valueOf(subaccountid));
                    System.out.println();
                    System.out.println("Subaccounts:");
                    for (Subaccount subaccount : subaccounts) {
                        System.out.println("ID: " + subaccount.getId() + ", login: " + subaccount.getLogin() +", active: " + subaccount.isActive());
                    }
                    System.out.println();
                    break;
                }
                case "3": {
                    System.out.println("Enter id of a client, u want to add abonaemnt to:");
                    String cleintId = scanner.next();
                    System.out.println("Choose abonament type: [1: BASIC, 2: PREMIUM]");
                    String abonaemntType = scanner.next();
                    if(Objects.equals(abonaemntType, "1"))abonamentService.addAbonament(Integer.valueOf(cleintId), BASIC);
                    else if(Objects.equals(abonaemntType, "2"))abonamentService.addAbonament(Integer.valueOf(cleintId),Type.PREMIUM);
                    else{logger.warning("You have chosen wrong type.");}
                    System.out.println();
                    break;
                }
                case "4":{
                    subaccountSection();
                }
                case "5": {
                    clientList = clientService.findAll();
                    System.out.println();
                    System.out.println("Clients:");
                    for (Client client : clientList) {
                        System.out.println("ID: " + client.getId() + ", Name: " + client.getName() + ", Surname = " + client.getSurname());
                    }
                    System.out.println();
                    break;
                }
                case "6":{
                    clientSection();
                }
                default: {
                    System.exit(0);
                }
            }
        }
    }

    private void subaccountSection() {
        List<Abonament> abonaments;
        List<Subaccount> subaccounts;
        while(true){
            System.out.println("---------------------");
            System.out.println("SUBACCOUNT MENU");
            System.out.println("---------------------");
            System.out.println("Choose what u want to do: \n" +
                    "1. Show all subaccounts\n" +
                    "2. Show subaccounts related to abonament\n" +
                    "3. Add new subaccount\n" +
                    "4. Edit subaccount\n" +
                    "5. List all abonaments\n" +
                    "6. Return\n" +
                    "default. Exit");

            Scanner scanner = new Scanner(System.in);
            String selection = scanner.next();
            switch(selection) {
                case "1": {
                    subaccounts = subaccountService.findAll();
                    System.out.println();
                    System.out.println("Subaccounts:");
                    for (Subaccount subaccount : subaccounts) {
                        System.out.println("ID: " + subaccount.getId() + ", login: " + subaccount.getLogin() +", active: " + subaccount.isActive());
                    }
                    System.out.println();
                    break;
                }
                case "2": {
                    System.out.println("Enter id of abonament which subacounts u want to be listed:");
                    String subaccountid = scanner.next();
                    subaccounts = subaccountService.findAllByAbonament(Integer.valueOf(subaccountid));
                    System.out.println();
                    System.out.println("Subaccounts:");
                    for (Subaccount subaccount : subaccounts) {
                        System.out.println("ID: " + subaccount.getId() + ", login: " + subaccount.getLogin() +", active: " + subaccount.isActive());
                    }
                    System.out.println();
                    break;
                }
                case "3": {
                    System.out.println("Enter id of a abonament, u want to add subaccount to:");
                    String abonamentId = scanner.next();
                    System.out.println("Enter login:");
                    String login = scanner.next();
                    System.out.println("Enter password:");
                    String password = scanner.next();
                    subaccountService.addSubaccount(Integer.valueOf(abonamentId),login,password);
                    System.out.println();
                    break;
                }
                case "4": {
                    System.out.println("Enter id of a subaccount, u want to edit:");
                    String subacountId = scanner.next();
                    System.out.println("Enter login:");
                    String subLogin = scanner.next();
                    System.out.println("Enter password:");
                    String subPassword = scanner.next();
                    subaccountService.modifySubaccount(Integer.valueOf(subacountId),subLogin,subPassword);
                    System.out.println();
                    break;
                }
                case "5": {
                    abonaments = abonamentService.findAll();
                    System.out.println();
                    System.out.println("Abonaments:");
                    for (Abonament abonament : abonaments) {
                        System.out.println("ID: " + abonament.getId() + ", type: " + abonament.getAbonamentType() + ", client = " + abonament.getClient());
                    }
                    System.out.println();
                    break;
                }
                case "6":{
                    abonamentSection();
                }
                default: {
                    System.exit(0);
                }
            }
        }
    }

    private void priceListSection(){
        List<PriceList> priceLists;
        List<Type> abonamentTypes = Arrays.asList(Type.values());
        while(true) {
            System.out.println("---------------------");
            System.out.println("PRICE LIST MENU");
            System.out.println("---------------------");
            System.out.println("Choose what u want to do: \n" +
                    "1. Show price list\n" +
                    "2. Show abonament types\n" +
                    "3. Add new price\n" +
                    "4. Show active prices\n" +
                    "5. Show inactive prices\n" +
                    "6. Return\n"+
                    "default. Exit");

            Scanner scanner = new Scanner(System.in);
            String selection = scanner.next();
            switch (selection) {
                case "1": {
                    priceLists = priceListService.getAll();
                    System.out.println();
                    System.out.println("Price List:");
                    for (PriceList priceList : priceLists) {
                        System.out.println("ID: " + priceList.getId() + ", aktywny:" + priceList.isActive() + ", type: " + priceList.getAbonamentType() + ", price: " + priceList.getPrice());
                    }
                    System.out.println();
                    break;
                }
                case "2": {
                    System.out.println();
                    System.out.println("Abonament types:");
                    for (Type abonamentType : abonamentTypes) {
                        System.out.println("Type:" + abonamentType);
                    }
                    System.out.println();
                    break;
                }
                case "3": {
                    System.out.println();
                    System.out.println("Choose abonament to which u want to set new price: [1: BASIC, 2: PREMIUM]");
                    int abonament = scanner.nextInt();
                    System.out.println("Set new price:");
                    float price = scanner.nextFloat();
                    if (abonament == 1) priceListService.setPrice(BASIC, price);
                    if (abonament == 2) priceListService.setPrice(Type.PREMIUM, price);
                    System.out.println();
                    break;
                }
                case "4": {
                    priceLists = priceListService.getByActive(true);
                    System.out.println();
                    System.out.println("Active Price List:");
                    for (PriceList priceList : priceLists) {
                        System.out.println("ID: " + priceList.getId() + ", aktywny:" + priceList.isActive() + ", type: " + priceList.getAbonamentType() + ", price: " + priceList.getPrice());
                    }
                    System.out.println();
                    break;
                }
                case "5": {
                    priceLists = priceListService.getByActive(false);
                    System.out.println();
                    System.out.println("Inactive Price List:");
                    for (PriceList priceList : priceLists) {
                        System.out.println("ID: " + priceList.getId() + ", aktywny:" + priceList.isActive() + ", type: " + priceList.getAbonamentType() + ", price: " + priceList.getPrice());
                    }
                    System.out.println();
                    break;
                }
                case "6": {
                    menu();
                }
                default: {
                    System.exit(0);
                }
            }
        }
    }

    private void receivablesAndPaymentsSection(){
        List<Receivable> receivables;
        List<Payment> payments;
        List<Client> clientList;
        while(true) {
            System.out.println("---------------------");
            System.out.println("RECEIVABLES MENU");
            System.out.println("Today is: " + LocalDate.now(clock));
            System.out.println("---------------------");
            System.out.println("Choose what u want to do: \n" +
                    "1. Show all receivables\n" +
                    "2. Show all payments\n" +
                    "3. Show deleyed receivables\n" +
                    "4. Add new payment\n" +
                    "5. Edit payment\n" +
                    "6. Show all clients\n" +
                    "7. Return\n" +
                    "default. Exit");

            Scanner scanner = new Scanner(System.in);
            String selection = scanner.next();
            switch (selection) {
                case "1": {
                    receivables = receivableService.findAll();
                    System.out.println();
                    System.out.println("Receivables:");
                    for (Receivable receivable : receivables) {
                        System.out.println("ID: " + receivable.getId() + ", client id:" + receivable.getAbonament().getClient().getId() + ", type: " + receivable.getAbonament().getAbonamentType() + ", cost: " + receivable.getPrice());
                        System.out.println("    > Payday: " + receivable.getPayday());
                        System.out.println("    > Already payed: " + receivable.getAlreadyPayed());
                        System.out.println("    > Payed: " + receivable.isPayed());
                        long days = ChronoUnit.DAYS.between(LocalDate.now(clock),receivable.getPayday());
                        if(days>=0)System.out.println("    > Days left: " + days);
                        else System.out.println("    > Days of delay: " + days);
                        System.out.println();
                    }
                    System.out.println();
                    break;
                }
                case "2": {
                    payments = paymentService.findAll();
                    System.out.println();
                    System.out.println("Payments:");
                    for (Payment payment : payments) {
                        System.out.println("ID: " + payment.getId() + ", client id:" + payment.getAbonament().getClient().getId() + ", type: " + payment.getAbonament().getAbonamentType() + ", cost: " + payment.getPrice());
                        System.out.println("    > Day of payment: " + payment.getPayday());
                        System.out.println("    > Receivable id: " + payment.getReceivables().getId());
                        if(payment.getReceivables().isPayed()) System.out.println("     > The receivable has been fully payed.");
                        else{
                            System.out.println("    > "+ (payment.getReceivables().getPrice()-payment.getPrice())+" is left to pay. ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                    break;
                }
                case "3": {
                    receivables = receivableService.findAllByPayDayBeforeAndPayed(LocalDate.now(clock),false);
                    System.out.println();
                    System.out.println("Receivables:");
                    for (Receivable receivable : receivables) {
                        System.out.println("ID: " + receivable.getId() + ", client id:" + receivable.getAbonament().getClient().getId() + ", type: " + receivable.getAbonament().getAbonamentType() + ", cost: " + receivable.getPrice());
                        System.out.println("    > Payday: " + receivable.getPayday());
                        System.out.println("    > Already payed: " + receivable.getAlreadyPayed());
                        long days = ChronoUnit.DAYS.between(LocalDate.now(clock),receivable.getPayday());
                        if(days>=0)System.out.println("    > Days left: " + days);
                        else System.out.println("    > Days of delay: " + days);
                        System.out.println();
                    }
                    System.out.println();
                    break;
                }
                case "4": {
                    System.out.println();
                    System.out.println("Enter id of receivable u want to pay:");
                    int id = scanner.nextInt();
                    Receivable receivable = receivableService.getReceivableById(id);
                    if(receivable==null){
                        logger.info("Receivable does not exist.");
                        break;
                    }
                    System.out.println();
                    System.out.println("ID: " + receivable.getId() + ", client id:" + receivable.getAbonament().getClient().getId() + ", type: " + receivable.getAbonament().getAbonamentType() + ", cost: " + receivable.getPrice());
                    System.out.println("    > Payday: " + receivable.getPayday());
                    System.out.println("    > Already payed: " + receivable.getAlreadyPayed());
                    System.out.println("    > Payed: " + receivable.isPayed());
                    long days = ChronoUnit.DAYS.between(LocalDate.now(clock),receivable.getPayday());
                    if(days>=0)System.out.println("    > Days left: " + days);
                    else System.out.println("    > Days of delay: " + days);
                    System.out.println();


                    System.out.println("Enter how much u want to pay: ");
                    float cost = scanner.nextFloat();
                    if(cost<0){
                        logger.info("Enter valid number.");
                        break;
                    }
                    paymentService.addPayment(id,cost);
                    System.out.println();
                    break;
                }
                case "5": {
                    System.out.println();
                    System.out.println("Enter id of payment u want to edit:");
                    int id = scanner.nextInt();
                    Payment payment = paymentService.findById(id);
                    if(payment==null){
                        break;
                    }
                    Receivable receivable = payment.getReceivables();

                    System.out.println();
                    System.out.println("Receiable:");
                    System.out.println("ID: " + receivable.getId() + ", client id:" + receivable.getAbonament().getClient().getId() + ", type: " + receivable.getAbonament().getAbonamentType() + ", cost: " + receivable.getPrice());
                    System.out.println("    > Payday: " + receivable.getPayday());
                    System.out.println("    > Already payed: " + receivable.getAlreadyPayed());
                    System.out.println("    > Payed: " + receivable.isPayed());
                    long days = ChronoUnit.DAYS.between(LocalDate.now(clock),receivable.getPayday());
                    if(days>=0)System.out.println("    > Days left: " + days);
                    else System.out.println("    > Days of delay: " + days);
                    System.out.println();

                    System.out.println("Payment");
                    System.out.println("ID: " + payment.getId() + ", client id:" + payment.getAbonament().getClient().getId() + ", type: " + payment.getAbonament().getAbonamentType() + ", cost: " + payment.getPrice());
                    System.out.println("    > Day of payment: " + payment.getPayday());
                    System.out.println("    > Receivable id: " + payment.getReceivables().getId());
                    if(payment.getReceivables().isPayed()) System.out.println("     > The receivable has been fully payed.");
                    else{
                        List<Payment> paymentsPayed = paymentService.findAllByReceivable_Id(receivable.getId());
                        if(paymentsPayed.size()>0){
                        System.out.println("The receivable haven't been fully payed, other payments:");
                        for(Payment payment1: paymentsPayed) {
                            if (payment1.getId() != payment.getId()) {
                                System.out.println("ID: " + payment.getId() + ", client id:" + payment.getAbonament().getClient().getId() + ", type: " + payment.getAbonament().getAbonamentType() + ", cost: " + payment.getPrice());
                                System.out.println("    > Day of payment: " + payment.getPayday());
                                System.out.println("    > Receivable id: " + payment.getReceivables().getId());
                            }
                        }
                        }
                        System.out.println("    > "+ (payment.getReceivables().getPrice()-payment.getPrice())+" is left to pay. ");
                    }
                    System.out.println();

                    System.out.println("Enter how much u want to pay: ");
                    float cost = scanner.nextFloat();
                    if(cost<0){
                        logger.info("Enter valid number.");
                        break;
                    }
                    paymentService.modifyPayment(id,cost);
                    System.out.println();
                    break;
                }
                case "6": {
                    clientList = clientService.findAll();
                    System.out.println();
                    System.out.println("Clients:");
                    for (Client client : clientList) {
                        System.out.println("ID: " + client.getId() + ", Name: " + client.getName() + ", Surname = " + client.getSurname());
                    }
                    System.out.println();
                    break;
                }
                case "7":{
                    menu();
                }
                default: {
                    System.exit(0);
                }
            }
        }
    }
    public void menu(){
        System.out.println("---------------------");
        System.out.println("Choose the section u want to enter: \n" +
                "1. Client\n" +
                "2. Price list\n" +
                "3. Receivables and Payments\n" +
                "4. Exit");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        switch(selection){
            case "1":{
                clientSection();
            }
            case "2":{
                priceListSection();
            }
            case "3":{
                receivablesAndPaymentsSection();
            }
            default:{
                System.exit(0);
            }
        }
    }
}

