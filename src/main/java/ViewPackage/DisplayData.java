package ViewPackage;

import ControllerPackage.Scheduler;
import ModelPackage.Server;
import ModelPackage.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DisplayData extends JFrame {
    private JPanel mainPanel;
    private JTable tableData;
    private JTextField txtCurrTime;
    private JTextField txtRemainingClients;
    private JTextField txtAverageServiceTime;
    private JTextField txtAverageWaitingTime;

    public DisplayData() {

        this.setSize(620, 420);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setContentPane(mainPanel);

        this.setVisible(true);
    }

    public void setNumColumns(int numberOfServers) {

        TableModel tableModel = new DefaultTableModel(numberOfServers, 2);

        for (int i = 0; i < numberOfServers; i++) {
            tableModel.setValueAt("Q" + (i+1), i, 0);
        }

        tableData.setModel(tableModel);
        tableData.getColumnModel().getColumn(0).setMinWidth(50);
        tableData.getColumnModel().getColumn(0).setMaxWidth(50);
        //tableData.setRowHeight(50);
    }

    public void printData(Scheduler scheduler, int currentTime, int remainingClients, double averageServiceTime) {

        txtCurrTime.setText(String.valueOf(currentTime));
        txtRemainingClients.setText(String.valueOf(remainingClients));
        txtAverageServiceTime.setText(String.valueOf(averageServiceTime));

        int index = 0;
        for (Server s: scheduler.getServers()) {

            StringBuilder txt = new StringBuilder();
            for (Task t: s.getTasks()) {
                txt.append(t.getId()).append(" ");
            }
            tableData.getModel().setValueAt(txt, index, 1);
            index++;
        }
    }

    public void printData(double averageWaitingTime) {
        txtAverageWaitingTime.setText(String.valueOf(averageWaitingTime));
    }
}
