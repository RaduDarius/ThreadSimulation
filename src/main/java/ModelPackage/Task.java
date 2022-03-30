package ModelPackage;

public class Task {

    // This class represents the client who needs to be served

    private final int id; // the id, for client identification
    private final int arrivalTime;
    private int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public void decrementServiceTime() {
        this.serviceTime--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && arrivalTime == task.arrivalTime && serviceTime == task.serviceTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }

    // SETTERS AND GETTERS

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

}
