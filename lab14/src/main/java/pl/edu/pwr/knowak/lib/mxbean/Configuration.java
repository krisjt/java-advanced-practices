package pl.edu.pwr.knowak.lib.mxbean;

import pl.edu.pwr.knowak.lib.Category;
import pl.edu.pwr.knowak.lib.CustomerServiceManager;
import pl.edu.pwr.knowak.lib.ServiceStation;
import pl.edu.pwr.knowak.lib.Ticket;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Configuration extends NotificationBroadcasterSupport implements ConfigurationMXBean{

    private CustomerServiceManager customerServiceManager;
    private Random random = new Random();

    public Configuration(CustomerServiceManager customerServiceManager) {
        this.customerServiceManager = customerServiceManager;
    }

    public Configuration() {
        this.customerServiceManager = new CustomerServiceManager();
    }

    private final AtomicLong sequenceNumber = new AtomicLong(0);

    public static final String PRIORITIES_CHANGED = "configuration.priorities.changed";
    public static final String STATION_CATEGORIES_CHANGED = "configuration.station.categories.changed";

    @Override
    public void configurePriorities() {
        Map<Integer,String> categoriesPrioritiesList = customerServiceManager.getCategories();
        List<String> categories = new ArrayList<>(categoriesPrioritiesList.values());

        List<String> reordered = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            if ((random.nextInt(5) + 1) % 5 != 0) {
                if (i == categories.size() - 1) {
                    reordered.add(0, categories.get(i));
                } else {
                    reordered.add(categories.get(i));
                }
            } else {
                reordered.add(categories.get(i));
            }
        }

        Map<Integer, String> newCategoriesPrioritiesList = new LinkedHashMap<>();
        for (int i = 0; i < reordered.size(); i++) {
            newCategoriesPrioritiesList.put(i, reordered.get(i));
        }

        customerServiceManager.reassignPriorities(newCategoriesPrioritiesList);
        customerServiceManager.getTicketMachine().setCategories(customerServiceManager.getCategoryList());

        System.out.println("NEW CATEGORIES LIST");
        System.out.println(customerServiceManager.getCategoryList());
        Map<Integer,ServiceStation> stationMap = customerServiceManager.getServiceStations();

        for(int i = 0; i < stationMap.size(); i++){
            List<Ticket> ticketQueue = stationMap.get(i).getTicketQueue();
            List<Ticket> ticketsToReassign = new ArrayList<>(ticketQueue);

            for (Ticket ticket : ticketsToReassign) {
                if(ticketsToReassign.contains(ticket))ticketQueue.remove(ticket);
                customerServiceManager.findStation(ticket);
            }
        }

        sendNotification(new Notification(
                PRIORITIES_CHANGED,
                this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                String.format("Priorities configuration changed. %d categories reordered.", reordered.size())
        ));
    }
    @Override
    public void configureStationCategories(int id) {
        Map<Integer,ServiceStation> stationMap = customerServiceManager.getServiceStations();
        List<Category> categories = customerServiceManager.getTicketMachine().getCategories();
        if(stationMap.isEmpty()||categories.isEmpty())return;

        List<Category> newCategories = new ArrayList<>();
        int rand = random.nextInt(stationMap.size());

            for (int i = 0; i < categories.size(); i++) {
                if ((random.nextInt(3) + 1) % 3 == 0) {
                    newCategories.add(categories.get(i));
                }
            }

        ServiceStation selectedStation = findServiceStationById(id);
        selectedStation.setCategories(newCategories);

        for(int i = 0; i < stationMap.size(); i++){
            List<Ticket> ticketQueue = stationMap.get(i).getTicketQueue();
            List<Ticket> ticketsToReassign = new ArrayList<>(ticketQueue);

            for (Ticket ticket : ticketsToReassign) {
                if(ticketsToReassign.contains(ticket))ticketQueue.remove(ticket);
                customerServiceManager.findStation(ticket);
            }
        }

        sendNotification(new Notification(
                STATION_CATEGORIES_CHANGED,
                this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                String.format("Station %d categories changed. New categories count: %d",
                        rand, newCategories.size())
        ));
    }

    @Override
    public String getConfigurationStatus() {
        return String.format("Categories: %d, Service Stations: %d, Last notification: %d",
                customerServiceManager.getCategoryList().size(),
                customerServiceManager.getServiceStations().size(),
                sequenceNumber.get());
    }

    @Override
    public int getCategoriesCount() {
        return customerServiceManager.getCategoryList().size();
    }

    @Override
    public int getStationsCount() {
        return customerServiceManager.getServiceStations().size();
    }

    public void sendCustomNotification(String type, String message) {
        sendNotification(new Notification(
                type,
                this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                message
        ));
    }

    @Override
    public void configureNumberOfCategories(int numberOfCategories) {
        customerServiceManager.reinitialize(numberOfCategories);

        sendNotification(new Notification(
                "configuration.categories.count.changed",
                this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                String.format("Number of categories changed to: %d", numberOfCategories)
        ));
    }

    private ServiceStation findServiceStationById(int id){
        return customerServiceManager.getServiceStations().get(id);
    }
    @Override
    public int getNumberOfCategories() {
        return customerServiceManager.getCategoryList().size();
    }
}