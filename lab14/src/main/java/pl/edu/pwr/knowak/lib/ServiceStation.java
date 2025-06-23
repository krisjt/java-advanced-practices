package pl.edu.pwr.knowak.lib;

import java.util.*;

public class ServiceStation {
    private static int idIterator = 0;
    private List<Category> categories;
    private List<Ticket> ticketQueue;
    private Ticket currentlyServedTicket;
    private int id;

    public ServiceStation(List<Category> categories) {
        this.categories = categories;
        ticketQueue = new Stack<>();
        id = idIterator;
        idIterator++;
    }

    public boolean addToQueue(Ticket ticket){
        if(categories.contains(ticket.getCategory()) && !ticketQueue.contains(ticket)){
            ticketQueue.add(ticket);
            Collections.sort(ticketQueue);
            return true;
        }
        return false;
    }

    public Optional<Ticket> serveCustomer(){
        Ticket ticket = ticketQueue.remove(0);
        Random random = new Random();
        int delay = 2000 + random.nextInt(3000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    public Optional<Ticket> showCurrentlyServedTicket(){
        return currentlyServedTicket==null?Optional.empty():Optional.of(currentlyServedTicket);
    }

    public boolean startServing(){
        if(!ticketQueue.isEmpty() && currentlyServedTicket == null) {
            currentlyServedTicket = ticketQueue.remove(0);
            return true;
        }
        return false;
    }

    public boolean stopServing(){
        if(currentlyServedTicket==null)return false;
        currentlyServedTicket = null;
        return true;
    }

    public int getId() {
        return id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Ticket> getTicketQueue() {
        return ticketQueue;
    }

    public static int getIdIterator() {
        return idIterator;
    }

    public Ticket getCurrentlyServedTicket() {
        return currentlyServedTicket;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static void setIdIterator(int idIterator) {
        ServiceStation.idIterator = idIterator;
    }

    @Override
    public String toString() {
        return "\nServiceStation{" +
                "categories=" + categories +
                ", ticketQueue=" + ticketQueue +
                ", currentlyServedTicket=" + currentlyServedTicket +
                ", id=" + id +
                '}' + '\n';
    }
}
