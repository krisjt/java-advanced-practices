package pl.edu.pwr.knowak.lib;

import java.util.*;

public class CustomerServiceManager {
    private TicketMachine ticketMachine = TicketMachine.getTicketMachine();
    private Map<Integer, ServiceStation> serviceStations = new HashMap<>();
    private List<Category> categoryList = new ArrayList<>();
    private Map<Integer,String> categories = new HashMap<>();

    public void getTicket(Category category){
        Optional<Ticket> ticket = ticketMachine.createTicket(category);
        if(ticket.isEmpty()) System.out.println("Incorrect category.");
        else {
            findStation(ticket.get());
        }
    }

    private void createStations(List<List<Category>> stationsCategories){
        for(List<Category> stationCategory: stationsCategories){
            if(!stationCategory.isEmpty())
                serviceStations.put(ServiceStation.getIdIterator(),new ServiceStation(stationCategory));
        }
    }

    public void createCategories(Map<Integer, String> categories){
        if(!categories.isEmpty()) {
            categoryList.clear();
            for (int i = 0; i < categories.size(); i++) {
                categoryList.add(new Category(categories.get(i), i));
            }
        }
    }

    public void reassignPriorities(Map<Integer,String> newPriorities){
        for(Map.Entry<Integer, String> entry : newPriorities.entrySet()) {
            int newPriority = entry.getKey();
            String categoryName = entry.getValue();

            for(Category category : categoryList) {
                if(category.getName().equals(categoryName)) {
                    category.setPriority(newPriority);
                    break;
                }
            }
        }

        categoryList.sort(Comparator.comparingInt(Category::getPriority));

        categories.clear();
        for(Category category : categoryList) {
            categories.put(category.getPriority(), category.getName());
        }
    }

    public boolean findStation(Ticket ticket){
        int queueLenggth = Integer.MAX_VALUE;
        int chosenStationId = -1;
        for(int i = 0; i < serviceStations.size(); i++){
            ServiceStation serviceStation = serviceStations.get(i);
            if(serviceStation.getCategories().contains(ticket.getCategory()) && serviceStation.getTicketQueue().size() < queueLenggth){
                queueLenggth = serviceStation.getTicketQueue().size();
                chosenStationId = i;
            }
        }
        if(chosenStationId < 0)return false;
        serviceStations.get(chosenStationId).addToQueue(ticket);
        return true;
    }

    public void init(int numberOfCategories){
        //tworzenie kategorii
        List<String> categoryNames = new ArrayList<>();
        List<Integer> priorities = new ArrayList<>();
        for(int i = 0; i < numberOfCategories; i++){
            categoryNames.add(Character.toString((char) 65+i));
            priorities.add(i);
        }
        Collections.shuffle(priorities);
        for(int i = 0; i < numberOfCategories; i++){
            categories.put(priorities.get(i), categoryNames.get(i));
        }
        createCategories(categories);
        ticketMachine.setCategories(categoryList);

        //tworzenie stanowisk
        Random random = new Random();
        int num;
        List<Category> stationCategories = new ArrayList<>();
        List<List<Category>> stations = new ArrayList<>();
        for(int i = 0; i < numberOfCategories; i++){
            num = random.nextInt(numberOfCategories);
            stationCategories.add(categoryList.get(priorities.get(i)));
            if(num != i)
                stationCategories.add(categoryList.get(priorities.get(num)));
            stations.add(new ArrayList<>(stationCategories));
            stationCategories.clear();
        }
        createStations(stations);

        //tworzenie ticketow
        int numOfTickets = 2*numberOfCategories;
        for(int i = 0; i < numOfTickets; i++){
            int rand = random.nextInt(categoryList.size());
            Category chosenCategory = categoryList.get(rand);
            getTicket(chosenCategory);
        }
    }

    public void changeStationCategories(ServiceStation serviceStation, List<Category> categories){
        serviceStation.setCategories(categories);

        for(int i = 0; i < serviceStations.size(); i++){
            List<Ticket> ticketQueue = serviceStations.get(i).getTicketQueue();
            List<Ticket> ticketsToReassign = new ArrayList<>(ticketQueue);

            for (Ticket ticket : ticketsToReassign) {
                if(ticketsToReassign.contains(ticket))ticketQueue.remove(ticket);
                findStation(ticket);
            }
        }
    }

    public void reinitialize(int numberOfCategories) {
        categoryList = new ArrayList<>();
        serviceStations = new HashMap<>();
        ticketMachine.clearTickets();
        ticketMachine.clearCategories();
        ServiceStation.setIdIterator(0);
        categories = new HashMap<>();
        this.init(numberOfCategories);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public TicketMachine getTicketMachine() {
        return ticketMachine;
    }

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public Map<Integer, ServiceStation> getServiceStations() {
        return serviceStations;
    }

}
