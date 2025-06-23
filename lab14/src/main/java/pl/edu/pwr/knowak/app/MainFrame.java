package pl.edu.pwr.knowak.app;

import pl.edu.pwr.knowak.app.panels.QueuesPanel;
import pl.edu.pwr.knowak.app.panels.ServiceStationsPanel;
import pl.edu.pwr.knowak.app.panels.TicketMachinePanel;
import pl.edu.pwr.knowak.lib.CustomerServiceManager;
import pl.edu.pwr.knowak.lib.mxbean.Configuration;

import javax.management.*;
import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame implements NotificationListener {
    private JPanel contentPanel;
    private TicketMachinePanel ticketMachinePanel;
    private ServiceStationsPanel serviceStationsPanel;
    private QueuesPanel queuesPanel;
    public CustomerServiceManager customerServiceManager;
    private Configuration configurationMXBean;
    private ObjectName mxBeanObjectName;

    public MainFrame(int numberOfCategories) {
        setTitle("Service System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        customerServiceManager = new CustomerServiceManager();
        customerServiceManager.init(numberOfCategories);
        initializeMXBean();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton ticketMachineBtn = new JButton("Ticket Machine");
        JButton serviceStationsBtn = new JButton("Service Stations");
        JButton queuesBtn = new JButton("Queues");

        buttonPanel.add(ticketMachineBtn);
        buttonPanel.add(serviceStationsBtn);
        buttonPanel.add(queuesBtn);

        add(buttonPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new CardLayout());

        ticketMachinePanel = new TicketMachinePanel(customerServiceManager);
        serviceStationsPanel = new ServiceStationsPanel(customerServiceManager);
        queuesPanel = new QueuesPanel(customerServiceManager);

        contentPanel.add(ticketMachinePanel, "TicketMachine");
        contentPanel.add(serviceStationsPanel, "ServiceStations");
        contentPanel.add(queuesPanel, "Queues");

        add(contentPanel, BorderLayout.CENTER);

        ticketMachineBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout)(contentPanel.getLayout());
            cl.show(contentPanel, "TicketMachine");
        });

        serviceStationsBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout)(contentPanel.getLayout());
            serviceStationsPanel.loadStations();
            cl.show(contentPanel, "ServiceStations");
        });

        queuesBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout)(contentPanel.getLayout());
            queuesPanel.loadQueues();
            cl.show(contentPanel, "Queues");
        });
    }

    private void initializeMXBean() {
        try {
            configurationMXBean = new Configuration(customerServiceManager);
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            mxBeanObjectName = new ObjectName("pl.edu.pwr.knowak:type=Configuration");

            server.registerMBean(configurationMXBean, mxBeanObjectName);

            server.addNotificationListener(mxBeanObjectName, this, null, null);

        } catch (Exception e) {
            System.err.println("Failed to register Configuration MXBean: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String notificationType = notification.getType();
            String message = notification.getMessage();

            refreshAllPanels();

            showNotificationDialog(notificationType, message, timestamp);
        });
    }

    private void refreshAllPanels() {
        try {
            if (serviceStationsPanel != null) {
                serviceStationsPanel.loadStations();
            }

            if (queuesPanel != null) {
                queuesPanel.loadQueues();
            }

            if (ticketMachinePanel != null) {
                ticketMachinePanel.loadPanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotificationDialog(String type, String message, String timestamp) {
        String title = "Configuration Change Notification";
        String dialogMessage = String.format(
                "Configuration changed at %s\n\nType: %s\n\nDetails: %s\n\nAll panels have been refreshed.",
                timestamp, type, message
        );

        JOptionPane.showMessageDialog(
                this,
                dialogMessage,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void dispose() {
        unregisterMXBean();
        super.dispose();
    }

    private void unregisterMXBean() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();

            if (server.isRegistered(mxBeanObjectName)) {
                server.removeNotificationListener(mxBeanObjectName, this);
                server.unregisterMBean(mxBeanObjectName);
            }
        } catch (Exception e) {
            System.err.println("Failed to unregister bean: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialFrame initialFrame = new InitialFrame();
            initialFrame.setVisible(true);
        });
    }
}