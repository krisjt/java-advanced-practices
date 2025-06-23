package pl.edu.pwr.knowak.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketMachine {
    private static final TicketMachine ticketMachine = new TicketMachine();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    private TicketMachine() {
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
    }
    public Optional<Ticket> createTicket(Category category){
        if(categories.contains(category)){
            Ticket ticket = new Ticket(category,category.getCurrentMaxNumber());
            category.setCurrentMaxNumber(category.getCurrentMaxNumber()+1);
            tickets.add(ticket);
            return Optional.of(ticket);
        }
        return Optional.empty();
    }

    public List<Ticket> getTickets(){
        return tickets;
    }
    public static TicketMachine getTicketMachine() {
        return ticketMachine;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void clearTickets(){
        tickets.clear();
    }

    public void clearCategories(){
        categories.clear();
    }
}
