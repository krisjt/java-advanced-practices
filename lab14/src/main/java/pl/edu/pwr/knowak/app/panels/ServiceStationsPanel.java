package pl.edu.pwr.knowak.app.panels;

import pl.edu.pwr.knowak.lib.Category;
import pl.edu.pwr.knowak.lib.CustomerServiceManager;
import pl.edu.pwr.knowak.lib.ServiceStation;
import pl.edu.pwr.knowak.lib.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ServiceStationsPanel extends JPanel {
    private JPanel stationsPanel;
    private JPanel detailsPanel;
    private JTextArea statusLabel;
    private JLabel resultLabel;
    private CustomerServiceManager customerServiceManager;
    private Map<Integer, ServiceStation> stations;
    private ServiceStation chosenStation;

    public ServiceStationsPanel(CustomerServiceManager customerServiceManager) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.customerServiceManager = customerServiceManager;
        this.stations = customerServiceManager.getServiceStations();

        stationsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        detailsPanel = new JPanel(new BorderLayout(10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Station Details"));
        detailsPanel.setVisible(false);

        statusLabel = new JTextArea("Select a station to view details");
        statusLabel.setEditable(false);
        resultLabel = new JLabel(" ", JLabel.CENTER);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        detailsPanel.add(statusLabel, BorderLayout.NORTH);
        detailsPanel.add(buttonPanel, BorderLayout.CENTER);
        detailsPanel.add(resultLabel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
                    startServing();
                    loadStations();
        });

        stopButton.addActionListener(e -> {
            stopServing();
            loadStations();
        });

        loadStations();

        add(new JScrollPane(stationsPanel), BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);
    }

    public void loadStations() {
        stationsPanel.removeAll();
        this.stations = customerServiceManager.getServiceStations();

        for (int i = 0; i < stations.size(); i++) {
            ServiceStation station = stations.get(i);
            JButton stationButton = new JButton("Station: " + (station.getId()+1));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Queue: ").append(getTicketsText(station)).append("\n").append("Categories: ")
                    .append(getCategoriesText(station)).append("\n").append("Currently served ticket: ").append(station.getCurrentlyServedTicket());
            stationButton.addActionListener(e -> {
                chosenStation = station;
                statusLabel.setText(stringBuilder.toString());
                resultLabel.setText(" ");
                detailsPanel.setVisible(true);
            });
            stationsPanel.add(stationButton);
        }

        stationsPanel.revalidate();
        stationsPanel.repaint();
    }

    private String getTicketsText(ServiceStation serviceStation){
        StringBuilder stringBuilder = new StringBuilder();
        for(Ticket ticket: serviceStation.getTicketQueue()){
            stringBuilder.append(ticket.getId()).append(", ");
        }
        return stringBuilder.toString();
    }

    private String getCategoriesText(ServiceStation serviceStation){
        StringBuilder stringBuilder = new StringBuilder();
        for(Category category: serviceStation.getCategories()){
            stringBuilder.append(category.getName()).append(", ");
        }
        return stringBuilder.toString();
    }

    private void startServing(){
        if(chosenStation.showCurrentlyServedTicket().isPresent()){
            resultLabel.setText("There is a ticket that is being served now: " + chosenStation.showCurrentlyServedTicket().orElse(null));
        }
        else{
            if(chosenStation.startServing()) {
                resultLabel.setText("Starting to serve: " + chosenStation.showCurrentlyServedTicket());
                updateStationDetails();
            }
            else
                resultLabel.setText("Couldn't start. Maybe the queue is empty?");
        }
    }


    private void stopServing(){
        if(chosenStation.showCurrentlyServedTicket().isPresent()){
            resultLabel.setText("The ticket that is being served now is being stopped: " + chosenStation.showCurrentlyServedTicket().orElse(null));
            chosenStation.stopServing();
            updateStationDetails();
        }
        else{
            resultLabel.setText("Nothing is being served now.");
        }
    }

    private void updateStationDetails() {
        if (chosenStation != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Queue: ").append(getTicketsText(chosenStation)).append("\n")
                    .append("Categories: ").append(getCategoriesText(chosenStation)).append("\n")
                    .append("Currently served ticket: ").append(chosenStation.getCurrentlyServedTicket());
            statusLabel.setText(stringBuilder.toString());
        }
    }

}
