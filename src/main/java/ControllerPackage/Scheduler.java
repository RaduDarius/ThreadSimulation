package ControllerPackage;

import ModelPackage.SelectionPolicy;
import ModelPackage.Server;
import ModelPackage.Task;

import java.util.LinkedList;

public class Scheduler {

    private final LinkedList<Server> servers;
    private final int numServers;
    private Strategy strategy;

    public Scheduler(int numServers) {
        servers = new LinkedList<>();
        this.numServers = numServers;
    }

    public void changeStrategy(SelectionPolicy policy) {

        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }

    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    //SETTERS AND GETTERS

    public LinkedList<Server> getServers() {
        return servers;
    }
}
