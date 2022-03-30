import ControllerPackage.SimulationManager;
import ViewPackage.HomePage;

public class Main {

    public static void main(String[] args) {

        HomePage homePage = new HomePage();
        new SimulationManager(homePage);

        //new DisplayData();
    }

}
