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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static BusNetwork network;

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        setupNetwork();
        launch();
    }

    public static void setupNetwork()
    {
        BusNetwork network = new BusNetwork("src/main/resources/stops.txt", "src/main/resources/transfers.txt");   // Generate bus network from stops and transfers
        // Testing search trie
        ArrayList<BusStop> stops = network.searchTrie("MA");
        for(BusStop stop : stops)
        {
            System.out.println(stop.stopName);
        }
        System.out.println("Found " + stops.size() + " stops!");
    }
}