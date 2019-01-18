package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private TextArea hexOutput;
    private PixelBoard pixelBoard;
    private int selectedColour;
    private int selectedPalette;
    private BorderPane mainBorderPane;

    public int getSelectedPalette() {
        return selectedPalette;
    }

    public void setSelectedPalette(int selectedPalette) {
        this.selectedPalette = selectedPalette;
    }

    public int getSelectedColour() {
        return selectedColour;
    }

    public void setSelectedColour(int selectedColour) {
        this.selectedColour = selectedColour;
    }

    public TextArea getHexOutput() {
        return hexOutput;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //////////////////
        //Set up Menubar//
        //////////////////
        MenuBar menubar = new MenuBar();
        final Menu fileMenu = new Menu("File");
        final MenuItem newMenuItem = new MenuItem("New...");
        final MenuItem importMenuItem = new MenuItem("Import...");
        importMenuItem.setDisable(true);
        final MenuItem exportMenuItem = new MenuItem("Export...");
        exportMenuItem.setDisable(true);
        final MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setDisable(true);
        final MenuItem saveAsMenuItem = new MenuItem("Save As...");
        saveAsMenuItem.setDisable(true);
        final MenuItem quit = new MenuItem("Quit");

        final Menu paletteMenu = new Menu("Palette");
        final MenuItem newPalette = new MenuItem("New palette...");
        final MenuItem savePalette = new MenuItem("Save palette...");
        savePalette.setDisable(true);
        final MenuItem loadPalette = new MenuItem("Load palette...");
        loadPalette.setDisable(true);

        menubar.getMenus().addAll(fileMenu, paletteMenu);
        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), saveMenuItem, saveAsMenuItem,
                new SeparatorMenuItem(), importMenuItem, exportMenuItem, new SeparatorMenuItem(), quit);
        paletteMenu.getItems().addAll(newPalette,new SeparatorMenuItem(), savePalette, loadPalette);

        //////////////////////////
        //Set up New... MenuItem//
        //////////////////////////
        newMenuItem.setOnAction(mouseEvent-> new NewSpriteScreen(primaryStage, this));

        ////////////////////////
        //Set up Quit MenuItem//
        ////////////////////////
        quit.setOnAction(mouseEvent->System.exit(0));

        ///////////////////////////////
        //Set up New Palette MenuItem//
        ///////////////////////////////
        newPalette.setOnAction(mouseEvent-> new PaletteMenu(primaryStage, this));
        ////////////////////
        //Set up mainScene//
        ////////////////////

        //Parent PaintArea = FXMLLoader.load(getClass().getResource("sample.fxml"));

        VBox topLevelContainer = new VBox();

        mainBorderPane = new BorderPane();
        mainBorderPane.setPadding(new Insets(10,10,10,10));
        primaryStage.setTitle("SpriteSheet");
        Scene scene = new Scene(topLevelContainer, 400,300);
        primaryStage.setMinHeight(340);
        primaryStage.setMinWidth(420);
        topLevelContainer.getChildren().addAll(menubar,mainBorderPane);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);

        /////////////////////
        //Set up toolBoxArea//
        /////////////////////


        ////////////////////
        //Set up paintArea//
        ////////////////////


        //Group paintArea = new Group();

        pixelBoard = new PixelBoard(this);
        mainBorderPane.setCenter(pixelBoard.getGridPane());
        pixelBoard.getGridPane().setAlignment(Pos.CENTER);


        /**
         * Code for implementation of scrollbar on grid. Does not add anything but,
         * I want to keep for possible implementation later.
         *
         *
         //ScrollPane scrollPane = new ScrollPane();
        //StackPane canvasHolder = new StackPane(pixelBoard.getGridPane());
        //scrollPane.setContent(canvasHolder);
        //paintArea.getChildren().add(scrollPane);
        //paintArea.getChildren().add(canvasHolder);
        //canvasHolder.minWidthProperty().bind(Bindings.createDoubleBinding(()->
        //        scrollPane.getViewportBounds().getWidth(),scrollPane.viewportBoundsProperty()));

        //paintArea.getChildren().add(canvasHolder);
        //mainScene.setCenter(paintArea);
        */

        ///////////////////
        //Set up palette//
        ///////////////////

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        mainBorderPane.setRight(vBox);
        GridPane palette = new GridPane();
        Group paletteGroup = new Group();

        for(int x = 0; x < 8; x++){
            for(int y = 0; y <= 1; y++){
                PaletteButton paletteButton = new PaletteButton(this, x + (y * 8));
                paletteGroup.getChildren().add(paletteButton);
                palette.add(paletteButton, x, y);
            }
        }


        ///////////////////////
        //Set up palletteArea//
        ///////////////////////

        ObservableList<String> availablePalettes = FXCollections.observableArrayList(
                "Palette 1",
                "Palette 2",
                "Palette 3",
                "Palette 4");
        ComboBox comboBox = new ComboBox(availablePalettes);
        comboBox.getSelectionModel().selectFirst();
        setSelectedPalette(0);
        comboBox.setOnAction(mouseEvent->{setSelectedPalette(comboBox.getSelectionModel().getSelectedIndex());
        pixelBoard.updatePalette(getSelectedPalette());
        });
        comboBox.getVisibleRowCount();



        ///////////////////
        //Set up textArea//
        ///////////////////

        hexOutput = new TextArea();
        hexOutput.setPrefColumnCount(14);
        hexOutput.setPrefRowCount(9);
        hexOutput.setMinHeight(165);
        pixelBoard.returnHexText();
        vBox.getChildren().addAll(comboBox, palette,new Separator(), hexOutput);


        primaryStage.show();
    }

    public void updateColourSelection(int Colour){
        for(Node p:pixelBoard.getGridPane().getChildren()){
            if(p instanceof PixelButton){
                ((PixelButton) p).setmColour(Colour);
            }
        }
    }

    public void newCanvas(int x, int y){
        pixelBoard = null;
        pixelBoard = new PixelBoard(this, x, y);
        mainBorderPane.setCenter(pixelBoard.getGridPane());
        pixelBoard.getGridPane().setAlignment(Pos.CENTER);
        pixelBoard.returnHexText();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
