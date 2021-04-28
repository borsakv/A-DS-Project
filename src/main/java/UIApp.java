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
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import com.sun.javafx.application.LauncherImpl;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.text.LabelView;

public class UIApp extends Application {

    public static BusNetwork network;
    public static TripDatabase tripDatabase;
    public static final int X_SIZE = 800;
    public static final int Y_SIZE = 600;
    public static Scene HomePageScene, ShortestPathScene, FindBusStopScene, FindBusByTimeScene;

    @Override
    public void init() throws Exception {
        setupNetwork();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome to the Vancouver Bus Network App");
        pane.add(titleLabel, 0, 0);
        //GridPane.setValignment(titleLabel, VPos.TOP);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        Label descriptionLabel = new Label("//TODO (Description of software goes here)");
        pane.add(descriptionLabel, 0, 1);
        //GridPane.setValignment(descriptionLabel, VPos.TOP);
        GridPane.setHalignment(descriptionLabel, HPos.CENTER);

        Button functionalityOneButton = new Button("Shortest Path");
        pane.add(functionalityOneButton, 0, 2);
        //GridPane.setValignment(functionalityOneButton, VPos.CENTER);
        GridPane.setHalignment(functionalityOneButton, HPos.CENTER);

        Button functionalityTwoButton = new Button("Find Bus Stop");
        pane.add(functionalityTwoButton, 0, 3);
        //GridPane.setValignment(functionalityTwoButton, VPos.TOP);
        GridPane.setHalignment(functionalityTwoButton, HPos.CENTER);

        Button functionalityThreeButton = new Button("Find Buses by Time");
        pane.add(functionalityThreeButton, 0, 4);
        //GridPane.setValignment(functionalityThreeButton, VPos.TOP);
        GridPane.setHalignment(functionalityThreeButton, HPos.CENTER);

        Label teamLabel = new Label("Team: Cian Jinks, Ajchan Mamedov, James Cowan, Vitali Borsak");
        pane.add(teamLabel, 0, 5);
        //GridPane.setValignment(teamLabel, VPos.BOTTOM);
        GridPane.setHalignment(teamLabel, HPos.CENTER);

        functionalityOneButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(ShortestPathScene);
        });

        functionalityTwoButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(FindBusStopScene);
        });

        functionalityThreeButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(FindBusByTimeScene);
        });

        initShortestPathScene(stage);
        initFindBusStopScene(stage);
        initFindBusByTimeScene(stage);

        HomePageScene = new Scene(pane, X_SIZE, Y_SIZE);
        stage.setTitle("Vancouver Bus Network");
        stage.setScene(HomePageScene);
        stage.show();
    }

    public void initShortestPathScene(Stage stage){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Label label = new Label("Here goes the UI for the Shortest Path functionality.");
        pane.add(label, 0, 0);
        Button returnButton = new Button("Home");
        pane.add(returnButton, 0, 1);

        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        ShortestPathScene = new Scene(pane, X_SIZE, Y_SIZE);
    }

    //Vitali is working on it
    public void initFindBusStopScene(Stage stage){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Label label = new Label("Here goes the UI for the Search Bus Stops by Name functionality.");
        pane.add(label, 0, 0);
        Button returnButton = new Button("Home");
        pane.add(returnButton, 0, 1);

        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        FindBusStopScene = new Scene(pane, X_SIZE, Y_SIZE);
    }

    //Ajchan is working on this
    public void initFindBusByTimeScene(Stage stage){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Label label = new Label("Here goes the UI for the Search Buses by Time functionality.");
        pane.add(label, 0, 0);
        Button returnButton = new Button("Home");
        pane.add(returnButton, 0, 1);

        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        FindBusByTimeScene = new Scene(pane, X_SIZE, Y_SIZE);
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(UIApp.class, PreloadData.class, args);
    }

    public static void setupNetwork()
    {
        System.out.println("Reading data files....");
        network = new BusNetwork("stops.txt", "transfers.txt");   // Generate bus network from stops and transfers
        tripDatabase = new TripDatabase("stop_times.txt");

        // Add all the trip connections to the network
        for(TripDatabase.Trip t : tripDatabase.database)
        {
            for(int section = 1; section < t.trip.size(); section++)
            {
                TripDatabase.TripSection firstSection = t.trip.get(section - 1);
                TripDatabase.TripSection secondSection = t.trip.get(section);
                network.addConnection(firstSection.stopID, secondSection.stopID, 1);    // Add this part of the trip to the network
            }
        }
        System.out.println("Done!");
    }
}