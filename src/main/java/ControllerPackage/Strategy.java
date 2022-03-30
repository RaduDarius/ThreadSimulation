package ControllerPackage;

import ModelPackage.Server;
import ModelPackage.Task;

import java.util.LinkedList;

public interface Strategy {

    void addTask(LinkedList<Server> servers, Task task);

}
