package ViewPackage;

import ModelPackage.SelectionPolicy;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HomePage extends JFrame{
    private JPanel mainPanel;
    private JButton btnSimulation;
    private JTextField txtTimeLimit;
    private JTextField txtMaxProcessingTime;
    private JTextField txtMinProcessingTime;
    private JTextField txtClients;
    private JTextField txtServers;
    private JComboBox<SelectionPolicy> comboBoxStrategy;
    private JTextField txtMaxArrivalTime;
    private JTextField txtMinArrivalTime;

    public HomePage() {

        this.setSize(350, 320);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        setComboBox();

        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    private void setComboBox() {

        comboBoxStrategy.addItem(SelectionPolicy.SHORTEST_TIME);
        comboBoxStrategy.addItem(SelectionPolicy.SHORTEST_QUEUE);

    }

    public void addbtnSimulationListener(ActionListener a) {
        btnSimulation.addActionListener(a);
    }

    public JTextField getTxtTimeLimit() {
        return txtTimeLimit;
    }

    public JTextField getTxtMaxProcessingTime() {
        return txtMaxProcessingTime;
    }

    public JTextField getTxtMinProcessingTime() {
        return txtMinProcessingTime;
    }

    public JTextField getTxtClients() {
        return txtClients;
    }

    public JTextField getTxtServers() {
        return txtServers;
    }

    public JComboBox<SelectionPolicy> getComboBoxStrategy() {
        return comboBoxStrategy;
    }

    public JTextField getTxtMaxArrivalTime() {
        return txtMaxArrivalTime;
    }

    public JTextField getTxtMinArrivalTime() {
        return txtMinArrivalTime;
    }
}
