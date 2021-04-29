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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class UIApp extends Application {

    public static BusNetwork network;
    public static TripDatabase tripDatabase;
    public static final int X_SIZE = 1280;
    public static final int Y_SIZE = 720;
    public static Scene HomePageScene, ShortestPathScene, FindBusStopScene, FindBusByTimeScene, TeamInfoScene, FindBusByTimeSecondScene;
    ArrayList<TripDatabase.Trip> results;

    @Override
    public void init() throws Exception {
        setupNetwork();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox();
        vbox.setSpacing(Y_SIZE/4);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        VBox vboxTitle = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vboxTitle.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Welcome to the Vancouver Bus Network App");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label descriptionLabel = new Label("This application gives the user access to the Vancouver Bus Network featuring a variety of" +
                "\nfunctionality giving them knowledge on which busses to take to get around the city.");

        VBox vboxButtons = new VBox();
        vboxButtons.setSpacing(5);
        vboxButtons.setPadding(new Insets(10, 10, 10, 10));
        vboxButtons.setAlignment(Pos.CENTER);

        Button functionalityOneButton = new Button("Shortest Path");
        functionalityOneButton.setStyle("-fx-font-size: 16px;");
        Label buttonOneLabel = new Label("This button will bring you to a page where you are given the functionality to search two stop" +
                "\nIDs and the program will display the shortest path between these two bus stops.");
        VBox buttonOne = new VBox();
        buttonOne.setSpacing(5);
        buttonOne.setPadding(new Insets(10, 10, 50, 10));
        buttonOne.getChildren().addAll(functionalityOneButton, buttonOneLabel);
        buttonOne.setAlignment(Pos.CENTER);

        Button functionalityTwoButton = new Button("Find Bus Stop");
        functionalityTwoButton.setStyle("-fx-font-size: 16px;");
        Label buttonTwoLabel = new Label("This button will bring you to a page where you are given a table of all bus stops, you then" +
                "are given the option to\nsearch all the stops by stop name, which will update the table with none, one or more than one" +
                "stop with the matching name.");
        VBox buttonTwo = new VBox();
        buttonTwo.setSpacing(5);
        buttonTwo.setPadding(new Insets(10, 10, 50, 10));
        buttonTwo.getChildren().addAll(functionalityTwoButton, buttonTwoLabel);
        buttonTwo.setAlignment(Pos.CENTER);

        Button functionalityThreeButton = new Button("Find Buses by Time");
        functionalityThreeButton.setStyle("-fx-font-size: 16px;");
        Label buttonThreeLabel = new Label("This button will bring you to a page where you are given a prompt to fill in an arrival time" +
                ", once you do this\nit will display a table with all routes that contain an arrival time equal to the one you input.");
        VBox buttonThree = new VBox();
        buttonThree.setSpacing(5);
        buttonThree.setPadding(new Insets(10, 10, 180, 10));
        buttonThree.getChildren().addAll(functionalityThreeButton, buttonThreeLabel);
        buttonThree.setAlignment(Pos.CENTER);

        Button teamButton = new Button("Team Info");
        teamButton.setAlignment(Pos.BOTTOM_LEFT);

        vboxTitle.getChildren().addAll(titleLabel, descriptionLabel);
        vboxButtons.getChildren().addAll(buttonOne, buttonTwo, buttonThree);
        vbox.getChildren().addAll(vboxTitle, vboxButtons, teamButton);

        functionalityOneButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(ShortestPathScene);
        });

        functionalityTwoButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(FindBusStopScene);
        });

        functionalityThreeButton.setOnAction((ActionEvent e) ->  {
            stage.setScene(FindBusByTimeScene);
        });

        teamButton.setOnAction((ActionEvent e) -> {
            stage.setScene(TeamInfoScene);
        });

        initShortestPathScene(stage);
        initFindBusStopScene(stage);
        initFindBusByTimeScene(stage);
        initTeamInfoScene(stage);

        HomePageScene = new Scene(vbox, X_SIZE, Y_SIZE);
        stage.setTitle("Vancouver Bus Network");
        stage.setScene(HomePageScene);
        stage.getIcons().add(new Image("bus.png"));
        stage.show();
    }

    public void initTeamInfoScene(Stage stage){
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        Label titleLabel = new Label("Information About the Team Behind this Application");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        VBox CianJinks = new VBox();
        CianJinks.setSpacing(5);
        CianJinks.setPadding(new Insets(10, 10, 20, 10));

        Label CianName = new Label("Cian Jinks:");
        CianName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label CianDescription = new Label("Cian Jinks was the back-end developer, he worked on making the back-end of functionality 1" +
                " and 2 work.");
        CianJinks.getChildren().addAll(CianName, CianDescription);

        VBox AjchanMamedov = new VBox();
        AjchanMamedov.setSpacing(5);
        AjchanMamedov.setPadding(new Insets(10, 10, 20, 10));

        Label AjchanName = new Label("Ajchan Mamedov:");
        AjchanName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label AjchanDescription = new Label("Ajchan Mamedov worked both on back-end and front-end, he worked on reading and parsing the" +
                "stop_times file. He also worked on functionality 3 UI (display stops by arrival time).");
        AjchanMamedov.getChildren().addAll(AjchanName, AjchanDescription);

        VBox JamesCowan = new VBox();
        JamesCowan.setSpacing(5);
        JamesCowan.setPadding(new Insets(10, 10, 20, 10));

        Label JamesName = new Label("James Cowan:");
        JamesName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label JamesDescription = new Label("James Cowan worked on both the front-end and back-end, he worked on reading and parsing the" +
                "transfer and stops files. He worked on functionality 1 UI (display the shortest distance between two stops) also.");
        JamesCowan.getChildren().addAll(JamesName, JamesDescription);

        VBox VitaliBorsak = new VBox();
        VitaliBorsak.setSpacing(5);
        VitaliBorsak.setPadding(new Insets(10, 10, 20, 10));

        Label VitaliName = new Label("Vitali Borsak:");
        VitaliName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label VitaliDescription = new Label("Vitali Borsak worked on both the back-end and front-end functionality, he implemented the back" +
                "-end of Ternary Search Trie data structure along with the search function and the front-end of functionality 3 UI " +
                "\n(search all bus stops by name with the user input). He also read in and parsed the stops file and helped read in and parse" +
                " the stop_times file.");
        VitaliBorsak.getChildren().addAll(VitaliName, VitaliDescription);

        Button returnButton = new Button("Return Home");
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });
        vbox.getChildren().addAll(titleLabel, CianJinks, AjchanMamedov, JamesCowan, VitaliBorsak, returnButton);

        TeamInfoScene = new Scene(vbox, X_SIZE, Y_SIZE);
    }

    static class StopCell extends ListCell<Integer> {
        @Override
        protected void updateItem(Integer item, boolean empty) {
            Image image = new Image("Stop.png");
            super.updateItem(item, empty);
            if (getIndex() == 0) {
                image = new Image("FirstStop.png");
            } else if (getIndex() == getListView().getItems().size() - 1) {
                image = new Image("LastStop.png");
            }
            String stopName = "No Stop Name";
            try {
                stopName = network.lookup(item).stopName;
            }catch (NullPointerException ignored) {}
            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            HBox box = new HBox(20, imageView, new Label(String.valueOf(item)), new Label(stopName));
            box.setFillHeight(true);
            setGraphic(box);
        }
    }
    public void initShortestPathScene(Stage stage){
        ArrayList<Integer> stops = new ArrayList<>();
        double[] distance = new double[1];
        Label title = new Label("Find Stops Between Start to End");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
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
        ListView<Integer> stopView = new ListView<>();
        stopView.setCellFactory(stopCell -> new StopCell());
        Label distanceLabel = new Label("");
        distanceLabel.setVisible(false);
        Label invalidSearchLabel = new Label("That route does not exist, please try another route.");
        invalidSearchLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        invalidSearchLabel.setAlignment(Pos.CENTER);

        invalidSearchLabel.setVisible(false);

        searchButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                distanceLabel.setVisible(true);
                stopView.setVisible(true);
                invalidSearchLabel.setVisible(false);
                stops.clear();
                distance[0] = -1.0;
                int start = Integer.parseInt(startField.getText());
                int end = Integer.parseInt(endField.getText());
                ArrayList<Integer> route =  network.getShortestPath(start, end, distance);
                stops.addAll(route);
                ObservableList<Integer> observableStops = FXCollections.observableArrayList(stops);
                stopView.setItems(observableStops);
                distanceLabel.setText(String.format("Total trip distance of: %.1f", distance[0]));
                if (distance[0] == -1) {
                    stopView.setVisible(false);
                    invalidSearchLabel.setVisible(true);
                    distanceLabel.setVisible(false);
                }
            }
        });

        Button returnButton = new Button("Back");
        HBox searchBox = new HBox(20, startField, endField, searchButton, distanceLabel);
        VBox vBox = new VBox(20, searchBox, invalidSearchLabel, stopView);
        vBox.setAlignment(Pos.CENTER);
        HBox bottomButtons = new HBox(20, returnButton);
        VBox content = new VBox(20, title, vBox, bottomButtons);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        ShortestPathScene = new Scene(content, X_SIZE, Y_SIZE);
    }

    public void initFindBusStopScene(Stage stage){
        // Vertical box of the screen
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        // Horizontal box of the screen
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0, 10, 10, 0));

        // Creating and styling the header text
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
        // If enter is pressed, generate table
        textField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                ArrayList<BusStop> matchedNames = network.searchTrie(textField.getText());
                tableView.setItems(FXCollections.observableArrayList(matchedNames));
            }
        });

        // Creates a home button
        Button returnButton = new Button("Return Home");
        // Adds a listener which brings you to the home page once the button is clicked
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
            textField.setText("");
        });

        // Search button which reads in the contents of the search bar and populates the table accordingly
        Button searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent e) -> {
            ArrayList<BusStop> matchedNames = network.searchTrie(textField.getText());
            tableView.setItems(FXCollections.observableArrayList(matchedNames));
        });

        // Adding all components into the vbox to then display it
        hbox.getChildren().addAll(textField, searchButton, returnButton);
        vbox.getChildren().addAll(tableLabel, tableView, textField, hbox);
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        FindBusStopScene = new Scene(vbox, X_SIZE, Y_SIZE);
    }

    public void initFindBusByTimeScene(Stage stage){
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setAlignment(Pos.TOP_CENTER);

        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setAlignment(Pos.TOP_CENTER);

        VBox hourVBox = new VBox();
        hourVBox.setSpacing(5);
        hourVBox.setPadding(new Insets(5, 5, 5, 5));
        hourVBox.setAlignment(Pos.TOP_CENTER);

        VBox minuteVBox = new VBox();
        minuteVBox.setSpacing(5);
        minuteVBox.setPadding(new Insets(5, 5, 5, 5));
        minuteVBox.setAlignment(Pos.TOP_CENTER);

        VBox secondVBox = new VBox();
        secondVBox.setSpacing(5);
        secondVBox.setPadding(new Insets(5, 5, 5, 5));
        secondVBox.setAlignment(Pos.TOP_CENTER);

        VBox buttonsVBox = new VBox();
        buttonsVBox.setSpacing(5);
        buttonsVBox.setPadding(new Insets(5, 5, 5, 5));
        buttonsVBox.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Search Stops by Arrival Time");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        //limits the numbers you can enter
        Pattern pattern = Pattern.compile(".{0,2}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        TextFormatter formatter1 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        TextFormatter formatter2 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        Label searchLabelHH = new Label("  HH  :");

        //hour search field
        TextField searchFieldHH = new TextField();
        searchFieldHH.setPrefWidth(30);
        searchFieldHH.setTextFormatter(formatter);
        searchFieldHH.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            searchFieldHH.setText(newValue.replaceAll("[^\\d]", ""));
        });

        Label searchLabelMM = new Label("  MM  :");

        //minute search field
        TextField searchFieldMM = new TextField();
        searchFieldMM.setPrefWidth(30);
        searchFieldMM.setTextFormatter(formatter1);
        searchFieldMM.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            searchFieldMM.setText(newValue.replaceAll("[^\\d]", ""));
        });

        Label searchLabelSS = new Label("  SS   ");

        //seconds search field
        TextField searchFieldSS = new TextField();
        searchFieldSS.setPrefWidth(30);
        searchFieldSS.setTextFormatter(formatter2);
        searchFieldSS.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            searchFieldSS.setText(newValue.replaceAll("[^\\d]", ""));
        });

        Button enterButton = new Button("Enter");

        Label errorLabel = new Label("Your time input is not a valid, retry again ");
        //displaying when the button is pressed
        enterButton.setOnAction((ActionEvent e) -> {
            int hours = Integer.parseInt(searchFieldHH.getText());
            int minutes = Integer.parseInt(searchFieldMM.getText());
            int seconds = Integer.parseInt(searchFieldSS.getText());
            if(hours < 24 && minutes < 60 && seconds < 60) {
                results = new ArrayList<>();
                results = tripDatabase.searchForArrivalTime(hours, minutes, seconds);
                vbox.getChildren().remove(errorLabel);
                initFindBusByTimeSecondScene(stage);
                stage.setScene(FindBusByTimeSecondScene);
            }
            else{
                vbox.getChildren().add(errorLabel);
            }
            searchFieldHH.setText("");
            searchFieldMM.setText("");
            searchFieldSS.setText("");
        });

        Button returnButton = new Button("Home");
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        hourVBox.getChildren().addAll(searchLabelHH, searchFieldHH);
        minuteVBox.getChildren().addAll(searchLabelMM, searchFieldMM);
        buttonsVBox.getChildren().addAll(enterButton, returnButton);
        secondVBox.getChildren().addAll(searchLabelSS, searchFieldSS);
        hbox.getChildren().addAll(hourVBox, minuteVBox, secondVBox, buttonsVBox);
        vbox.getChildren().addAll(titleLabel, hbox);
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        FindBusByTimeScene = new Scene(vbox, X_SIZE, Y_SIZE);
    }

    public void initFindBusByTimeSecondScene(Stage stage){
        // Vertical box of the screen
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label tableLabel = new Label("Search for Trips by Arrival Time");
        tableLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Creates a table class
        TableView tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.prefHeightProperty().bind(stage.heightProperty());
        tableView.prefWidthProperty().bind(stage.widthProperty());
        // Allow the editing of the table
        tableView.setEditable(true);

        // Creates a column in the table with the tripIDProperty which is connected to the TripSection class
        TableColumn TripIdColumn = new TableColumn("Trip ID");
        TripIdColumn.setMinWidth(100);
        TripIdColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Integer>("tripIDProperty"));

        // Creates a column in the table with the stopIDProperty which is connected to the TripSection class
        TableColumn stopIdColumn = new TableColumn("Stop Id");
        stopIdColumn.setMinWidth(100);
        stopIdColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, String>("stopIDProperty"));

        // Creates a column in the table with the arrivalTimeProperty which is connected to the TripSection class
        TableColumn arrivalTimeColumn = new TableColumn("Arrival time");
        arrivalTimeColumn.setMinWidth(100);
        arrivalTimeColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Integer>("arrivalTimeProperty"));

        // Creates a column in the table with the departureTimeProperty which is connected to the TripSection class
        TableColumn departureTimeColumn = new TableColumn("Departure time");
        departureTimeColumn.setMinWidth(100);
        departureTimeColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, String>("departureTimeProperty"));

        // Creates a column in the table with the stopSequenceProperty which is connected to the TripSection class
        TableColumn stopSequenceColumn = new TableColumn("Stop sequence");
        stopSequenceColumn.setMinWidth(100);
        stopSequenceColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Integer>("stopSequenceProperty"));

        // Creates a column in the table with the stopHeadsignProperty which is connected to the TripSection class
        TableColumn stopHeadsignColumn = new TableColumn("Stop Headsign");
        stopHeadsignColumn.setMinWidth(100);
        stopHeadsignColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Double>("stopHeadsignProperty"));

        // Creates a column in the table with the pickupTypeProperty which is connected to the TripSection class
        TableColumn stopPickupTypeColumn = new TableColumn("Pickup type");
        stopPickupTypeColumn.setMinWidth(100);
        stopPickupTypeColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Double>("pickupTypeProperty"));

        // Creates a column in the table with the dropOffTypeProperty which is connected to the TripSection class
        TableColumn dropOffTypeColumn = new TableColumn("Drop off type");
        dropOffTypeColumn.setMinWidth(100);
        dropOffTypeColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Double>("dropOffTypeProperty"));

        // Creates a column in the table with the distTraveledProperty which is connected to the TripSection class
        TableColumn distTraveledColumn = new TableColumn("Distance traveled");
        distTraveledColumn.setMinWidth(100);
        distTraveledColumn.setCellValueFactory(
                new PropertyValueFactory<TripDatabase.TripSection, Double>("distTraveledProperty"));

        // Adds all the columns to the table
        tableView.getColumns().addAll(TripIdColumn, stopIdColumn, arrivalTimeColumn, departureTimeColumn,
                stopSequenceColumn, stopHeadsignColumn, stopPickupTypeColumn, dropOffTypeColumn, distTraveledColumn);

        //fill the table with ArrayList with TripSection information
        if(results!=null) {
            ArrayList<TripDatabase.TripSection> data = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                for(int j = 0; j<results.get(i).trip.size(); j++)
                    data.add(results.get(i).trip.get(j));
            }
            tableView.setItems(FXCollections.observableArrayList(data));
        }

        // Creates a home button
        Button returnButton = new Button("Return Home");
        // Adds a listener which brings you to the home page once the button is clicked
        returnButton.setOnAction((ActionEvent e) -> {
            stage.setScene(HomePageScene);
        });

        vbox.getChildren().addAll(tableLabel, tableView, returnButton);
        vbox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        FindBusByTimeSecondScene = new Scene(vbox, X_SIZE, Y_SIZE);
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
                network.addConnection(firstSection.getStopID(), secondSection.getStopID(), 1);    // Add this part of the trip to the network
            }
        }
        System.out.println("Done!");
    }
}