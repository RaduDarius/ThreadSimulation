package ControllerPackage;

import ModelPackage.SelectionPolicy;
import ModelPackage.Server;
import ModelPackage.Task;
import ViewPackage.DisplayData;
import ViewPackage.HomePage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class SimulationManager implements Runnable{

    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    public int waitingGlobalPeriod = 0;

    public double averageServiceTime = 0;
    public double averageWaitingTime = 0;

    //Entity responsible with queue management and client distribution
    private final Scheduler scheduler;

    //Frame for displaying simulation
    private HomePage homePage;
    private DisplayData displayData;

    public void controllerHomePage(HomePage homePage) {
        this.homePage = homePage;

        homePage.addbtnSimulationListener(e -> {
            boolean valid = readData();

            if (valid) {
                scheduler.changeStrategy(selectionPolicy);
                generateTasks();

                homePage.dispose();
                displayData = new DisplayData();
                displayData.setNumColumns(numberOfServers);

                for (int i = 0; i < numberOfServers; i++) {
                    Server server = new Server();
                    scheduler.getServers().add(server);
                    Thread thread = new Thread(server);
                    thread.start();
                }

                Thread t = new Thread(this);
                t.start();
            }
        });
    }

    private boolean readData() {

        try {
            timeLimit = Integer.parseInt(homePage.getTxtTimeLimit().getText());
            maxProcessingTime = Integer.parseInt(homePage.getTxtMaxProcessingTime().getText());
            minProcessingTime = Integer.parseInt(homePage.getTxtMinProcessingTime().getText());
            maxArrivalTime = Integer.parseInt(homePage.getTxtMaxArrivalTime().getText());
            minArrivalTime = Integer.parseInt(homePage.getTxtMinArrivalTime().getText());
            numberOfServers = Integer.parseInt(homePage.getTxtServers().getText());
            numberOfClients = Integer.parseInt(homePage.getTxtClients().getText());
            selectionPolicy = (SelectionPolicy) homePage.getComboBoxStrategy().getSelectedItem();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Input invalid !");
            return false;
        }
    }


    //Tasks
    private final ArrayList<Task> generatedTasks;

    public SimulationManager(HomePage homePage) {
        generatedTasks = new ArrayList<>();
        scheduler = new Scheduler(numberOfServers);

        controllerHomePage(homePage);
    }

    public void generateTasks() {
        for (int i = 0; i < numberOfClients; i++) {
            Random random = new Random();
            int randomArrivalTime = Math.abs(random.nextInt()) % maxArrivalTime;
            int randomServiceTime = Math.abs(random.nextInt()) % maxProcessingTime; // between minProcessingTime and maxProcessingTime

            if (randomServiceTime < minProcessingTime) {
                randomServiceTime += minProcessingTime;
            }

            if (randomArrivalTime < minArrivalTime) {
                randomArrivalTime += minArrivalTime;
            }

            waitingGlobalPeriod += randomServiceTime;

            averageServiceTime += randomServiceTime;
            generatedTasks.add(new Task(i, randomArrivalTime, randomServiceTime));
        }

        averageServiceTime /= numberOfClients;
        generatedTasks.sort(((o1, o2) -> Integer.signum(o1.getArrivalTime() - o2.getArrivalTime())));

        for (Task t: generatedTasks) {
            System.out.println(t.toString());
        }
    }


    @Override
    public void run() {
        int currentTime = 0;
        int remainingClients = generatedTasks.size();
        FileOutputStream file = null;
        try {
            file = new FileOutputStream("logT.txt");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (currentTime < timeLimit && waitingGlobalPeriod >= 0) {

            if (waitingGlobalPeriod == 0) {
                waitingGlobalPeriod--;
            }
            Iterator<Task> itr = generatedTasks.iterator();
            while (itr.hasNext()) {
                Task t = itr.next();
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    remainingClients--;
                    itr.remove();
                }
            }

            printQueues(currentTime);
            printQueuesF(currentTime, file);
            displayData.printData(scheduler, currentTime, remainingClients, averageServiceTime);
            currentTime++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            decrementQueues();
            removeTasks();
        }
        displayData.printData(averageWaitingTime / numberOfClients);
        assert file != null;
        PrintStream fout = new PrintStream(file);
        fout.println("Average service time: " + averageServiceTime);
        fout.println("Average waiting time: " + (averageWaitingTime / numberOfClients));
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeTasks() {

        for (Server s: scheduler.getServers()) {
            s.getTasks().removeIf(t -> t.getServiceTime() == 0);
        }

    }

    private void decrementQueues() {

        for (Server s: scheduler.getServers()) {
            for (Task t: s.getTasks()) {
                t.decrementServiceTime();
                waitingGlobalPeriod--;
                s.getWaitingPeriod().decrementAndGet();
            }
        }

    }

    private void printQueuesF(int currentTime, FileOutputStream file) {

        PrintStream fout = new PrintStream(file);

        fout.println("Current time: " + currentTime);
        fout.println("Clientii ce asteapta: ");
        for (Task t: generatedTasks) {
            fout.println(t.toString());
        }

        int index = 1;
        for (Server s : scheduler.getServers()) {
            fout.println("Queue: " + index);
            if (s.getTasks().size() > 1) {
                averageWaitingTime += s.getTasks().size() - 1;
            }
            for (Task t : s.getTasks()) {
                fout.println(t.toString() + " ");
            }
            fout.println();
            index++;
        }

    }

    private void printQueues(int currentTime) {

        System.out.println("Current time: " + currentTime);
        System.out.println("Clientii ce asteapta: ");
        for (Task t: generatedTasks) {
            System.out.println(t.toString());
        }

        int index = 1;
        for (Server s: scheduler.getServers()) {
            System.out.println("Queue: " + index);
            if (s.getTasks().size() > 1) {
                averageWaitingTime += s.getTasks().size() - 1;
            }
            for (Task t : s.getTasks()) {
                System.out.println(t.toString() + " ");
            }
            System.out.println();
            index++;
        }

    }
}
