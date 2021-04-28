//public class Main {
//    public static void main(String[] args)
//    {
//        // Setup everything
//
//
//        // GUI CODE
//        // Useful Functions: BusNetwork::getShortestPath(), BusNetwork::lookup(), TernarySearchTrie::Search(), TripDatabase::SearchTime()
//    }
//}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import com.sun.javafx.application.LauncherImpl;
import javafx.stage.Stage;

public class UIApp extends Application {

    public static BusNetwork network;

    @Override
    public void init() throws Exception {
        setupNetwork();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane(new Label("Loading complete!"));
        Scene scene = new Scene(root);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(UIApp.class, PreloadData.class, args);
    }

    public static void setupNetwork()
    {
        network = new BusNetwork("src/main/resources/stops.txt", "src/main/resources/transfers.txt");   // Generate bus network from stops and transfers
        TripDatabase tripDatabase = new TripDatabase();

        // Add all the trip connections to the network
        for(int trip = 1; trip < tripDatabase.database.size(); trip++)
        {
            TripDatabase.TripSection firstSection = tripDatabase.database.get(trip - 1);
            TripDatabase.TripSection secondSection = tripDatabase.database.get(trip);
            if(firstSection.tripID == secondSection.tripID)
            {
                network.addConnection(firstSection.stopID, secondSection.stopID, 1);    // Add this part of the trip to the network
            }
        }
        System.out.println("It works Cian!");
    }
}