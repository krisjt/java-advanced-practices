package pl.edu.pwr.knowak.app.panels;

import pl.edu.pwr.knowak.lib.Category;
import pl.edu.pwr.knowak.lib.CustomerServiceManager;
import pl.edu.pwr.knowak.lib.ServiceStation;
import pl.edu.pwr.knowak.lib.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueuesPanel extends JPanel {
    private JPanel queuesPanel;
    private CustomerServiceManager customerServiceManager;

    public QueuesPanel(CustomerServiceManager customerServiceManager) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.customerServiceManager = customerServiceManager;

        queuesPanel = new JPanel();
        queuesPanel.setLayout(new BoxLayout(queuesPanel, BoxLayout.Y_AXIS));

        add(new JScrollPane(queuesPanel), BorderLayout.CENTER);
    }

    public void loadQueues() {
        queuesPanel.removeAll();
        Map<Integer, ServiceStation> serviceStations = customerServiceManager.getServiceStations();
        List<Category> categories = customerServiceManager.getTicketMachine().getCategories();

        for (int i = 0; i < serviceStations.size(); i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

            JLabel queueLabel = new JLabel("Station " + (serviceStations.get(i).getId()+1) + ":");
            queueLabel.setPreferredSize(new Dimension(80, 25));
            queueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            StringBuilder stringBuilder = new StringBuilder();
            for(Ticket ticket: serviceStations.get(i).getTicketQueue()){
                stringBuilder.append(ticket.getId()).append(", ");
            }

            StringBuilder secondBuilder = new StringBuilder();
            for (Category category : serviceStations.get(i).getCategories()) {
                secondBuilder.append(category.getName()).append(", ");
            }

            JTextField queueField1 = new JTextField(stringBuilder.toString());
            queueField1.setEditable(false);
            queueField1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

            JTextField queueField2 = new JTextField(secondBuilder.toString());
            queueField2.setEditable(true);
            queueField2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

            JButton convertButton = new JButton("Update list");
            int finalI = i;
            convertButton.addActionListener(e -> {
                String text = queueField2.getText();
                text = text.replaceAll(",\\s*$", "");
                List<String> arrayList = Arrays.stream(text.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

                serviceStations.get(finalI).getCategories().clear();
                List<Category> newCategories = new ArrayList<>();
                for(String element: arrayList){
                    if(!categories.stream()
                            .anyMatch(p -> element.equals(p.getName()))){
                        continue;
                    }
                    Category category = categories.stream().filter(c -> element.equals(c.getName())).findFirst().orElse(null);
                    if(category!=null)newCategories.add(category);
                }
                customerServiceManager.changeStationCategories(serviceStations.get(finalI),newCategories);
                loadQueues();
            });

            rowPanel.add(queueLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(queueField1);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(queueField2);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(convertButton);

            queuesPanel.add(rowPanel);
            queuesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        queuesPanel.revalidate();
        queuesPanel.repaint();
    }
}