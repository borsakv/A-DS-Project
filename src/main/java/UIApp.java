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

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UIApp extends Application {

    public static BusNetwork network;
    public static TripDatabase tripDatabase;
    public static final int X_SIZE = 1280;
    public static final int Y_SIZE = 720;
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
        ArrayList<Integer> stops = new ArrayList<>();
        double[] distance = new double[1];

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        TextField startField = new TextField();
        startField.setPromptText("From");
        startField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                startField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        TextField endField = new TextField();
        endField.setPromptText("To");
        endField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                endField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                stops.clear();
                distance[0] = -1.0;
                int start = Integer.parseInt(startField.getText());
                int end = Integer.parseInt(endField.getText());
                ArrayList<Integer> route =  network.getShortestPath(start, end, distance);
                stops.addAll(route);
            }
        });
        Button returnButton = new Button("Home");

        // Stop display functionality


        pane.add(returnButton, 0, 0);
        pane.add(startField, 1,1);
        pane.add(endField, 2,1);
        pane.add(searchButton, 3,1);

        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        ShortestPathScene = new Scene(pane, X_SIZE, Y_SIZE);
    }

    public void initFindBusStopScene(Stage stage){
        // Vertical box of the screen
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label tableLabel = new Label("Search for Bus Stops by Name");
        tableLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Creates a table class
        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.prefHeightProperty().bind(stage.heightProperty());
        tableView.prefWidthProperty().bind(stage.widthProperty());
        // Allow the editing of the table
        tableView.setEditable(true);

        // Creates a column in the table with the stopIdProperty which is connected to the BusStop class
        TableColumn stopIdColumn = new TableColumn("ID");
        stopIdColumn.setMinWidth(50);
        stopIdColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, Integer>("stopIdProperty"));

        // Creates a column in the table with the stopNameProperty which is connected to the BusStop class
        TableColumn stopNameColumn = new TableColumn("Name");
        stopNameColumn.setMinWidth(250);
        stopNameColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, String>("stopNameProperty"));

        // Creates a column in the table with the stopCodeProperty which is connected to the BusStop class
        TableColumn stopCodeColumn = new TableColumn("Stop Code");
        stopCodeColumn.setMinWidth(80);
        stopCodeColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, Integer>("stopCodeProperty"));

        // Creates a column in the table with the stopDescriptionProperty which is connected to the BusStop class
        TableColumn stopDescriptionColumn = new TableColumn("Description");
        stopDescriptionColumn.setMinWidth(350);
        stopDescriptionColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, String>("stopDescriptionProperty"));

        TableColumn stopLocationColumn = new TableColumn("Stop Location");
        stopLocationColumn.setMinWidth(200);

        // Creates a column in the table with the stopLongitudeProperty which is connected to the BusStop class
        TableColumn stopLatitudeSubColumn = new TableColumn("Latitude");
        stopLatitudeSubColumn.setMinWidth(100);
        stopLatitudeSubColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, Double>("stopLatitudeProperty"));

        // Creates a column in the table with the stopLongitudeProperty which is connected to the BusStop class
        TableColumn stopLongitudeSubColumn = new TableColumn("Longitude");
        stopLongitudeSubColumn.setMinWidth(100);
        stopLongitudeSubColumn.setCellValueFactory(
                new PropertyValueFactory<BusStop, Double>("stopLongitudeProperty"));

        stopLocationColumn.getColumns().addAll(stopLatitudeSubColumn, stopLongitudeSubColumn);
        // Adds all the columns to the table
        tableView.getColumns().addAll(stopIdColumn, stopNameColumn, stopCodeColumn, stopDescriptionColumn, stopLocationColumn);

        // Creates a search bar
        TextField textField = new TextField();
        textField.setPromptText("Search");
        // Populates the table with all the stops
        tableView.setItems(FXCollections.observableArrayList(network.searchTrie("")));
        // Automatically change the lowercase values to uppercase since all stop names are uppercase
        textField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Updates the contents of the table with whatever is inputted into the search bar
        textField.textProperty().addListener((obs, newValue, oldValue) -> {
            ArrayList<BusStop> matchedNames = network.searchTrie(newValue);
            tableView.setItems(FXCollections.observableArrayList(matchedNames));
        });

        // Creates a home button
        Button returnButton = new Button("Return Home");
        // Adds a listener which brings you to the home page once the button is clicked
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
            textField.setText("");
        });

        vbox.getChildren().addAll(tableLabel, tableView, textField, returnButton);
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        FindBusStopScene = new Scene(vbox, X_SIZE, Y_SIZE);
    }

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