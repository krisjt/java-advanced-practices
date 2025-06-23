package pl.edu.pwr.knowak.app.panels;

import pl.edu.pwr.knowak.lib.Category;
import pl.edu.pwr.knowak.lib.CustomerServiceManager;
import pl.edu.pwr.knowak.lib.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class TicketMachinePanel extends JPanel {
    private JComboBox<Category> ticketTypeComboBox;
    private JButton submitButton;
    private JLabel infoLabel;
    private CustomerServiceManager customerServiceManager;


    public TicketMachinePanel(CustomerServiceManager customerServiceManager) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.customerServiceManager = customerServiceManager;

        List<Category> categories = customerServiceManager.getCategoryList();
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>(categories.toArray(new Category[0]));
        ticketTypeComboBox = new JComboBox<>(model);
        loadPanel();

        submitButton = new JButton("Get Ticket");
        submitButton.setEnabled(false);

        infoLabel = new JLabel("Please select a ticket type from the dropdown");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(ticketTypeComboBox);
        topPanel.add(submitButton);

        add(topPanel, BorderLayout.NORTH);
        add(infoLabel, BorderLayout.CENTER);

        ticketTypeComboBox.addActionListener(e -> {
            submitButton.setEnabled(true);
        });

        submitButton.addActionListener(e -> {
            Category selectedCategory = (Category) ticketTypeComboBox.getSelectedItem();
            getTicket(selectedCategory);
        });
    }

    public void loadPanel(){
        List<Category> categories = customerServiceManager.getCategoryList();
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>(categories.toArray(new Category[0]));
        ticketTypeComboBox.setModel(model);
    }
    private void getTicket(Category category){
        Optional<Ticket> ticket = customerServiceManager.getTicketMachine().createTicket(category);
        if(ticket.isEmpty()) infoLabel.setText("Incorrect category.");
        else {
            customerServiceManager.findStation(ticket.get());
            infoLabel.setText("Created ticket: " + ticket.get().getId());
        }
    }
}