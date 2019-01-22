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
import sample.PaletteMenu.PaletteButton;
import sample.PaletteMenu.PaletteMenu;

import java.util.ArrayList;

public class Main extends Application {

    private TextArea hexOutput;
    private PixelBoard pixelBoard;
    private Color selectedColour;
    private int selectedPalette;
    private BorderPane mainBorderPane;
    private VBox rightVBox;
    ComboBox paletteComboBox;

    private Color[] palette1Array = {Color.BLACK, Color.GREEN, Color.PURPLE, Color.RED, Color.OLDLACE, Color.ORANGE,
            Color.PINK, Color.DEEPPINK, Color.LAVENDERBLUSH, Color.YELLOW, Color.CYAN, Color.PURPLE, Color.ALICEBLUE,
            Color.AZURE, Color.FUCHSIA, Color.PEACHPUFF};
    private Color[]  palette2Array, palette3Array, palette4Array;
    private ArrayList paletteList;

    GridPane mainPalette;

    public int getSelectedPalette() {
        return selectedPalette;
    }

    public void setSelectedPalette(int selectedPalette) {
        this.selectedPalette = selectedPalette;
    }

    public Color getSelectedColour() {
        return selectedColour;
    }

    public void setSelectedColour(Color selectedColour) {
        this.selectedColour = selectedColour;
    }

    public TextArea getHexOutput() {
        return hexOutput;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        palette2Array = new Color[16];
        palette3Array = new Color[16];
        palette4Array = new Color[16];
        paletteList = new ArrayList();
        paletteList.add(palette1Array);
        paletteList.add(palette2Array);
        paletteList.add(palette3Array);
        paletteList.add(palette4Array);

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
        final MenuItem editPalette = new MenuItem("Edit palette...");
        final MenuItem savePalette = new MenuItem("Save palette...");
        savePalette.setDisable(true);
        final MenuItem loadPalette = new MenuItem("Load palette...");
        loadPalette.setDisable(true);

        menubar.getMenus().addAll(fileMenu, paletteMenu);
        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), saveMenuItem, saveAsMenuItem,
                new SeparatorMenuItem(), importMenuItem, exportMenuItem, new SeparatorMenuItem(), quit);
        paletteMenu.getItems().addAll(newPalette ,editPalette ,new SeparatorMenuItem(), savePalette, loadPalette);

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
        editPalette.setOnAction(mouseEvent->new PaletteMenu(primaryStage, this, paletteList));
        ////////////////////
        //Set up mainScene//
        ////////////////////

        //Parent PaintArea = FXMLLoader.load(getClass().getResource("sample.fxml"));

        VBox topLevelContainer = new VBox();

        mainBorderPane = new BorderPane();
        mainBorderPane.setPadding(new Insets(10,10,10,10));
        primaryStage.setTitle("MegaSpriter");
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

        pixelBoard = new PixelBoard(this);
        mainBorderPane.setCenter(pixelBoard.getSpritePane());
        pixelBoard.getSpritePane().setAlignment(Pos.CENTER);

        selectedColour = palette1Array[0];

        /**
         * Code for implementation of scrollbar on grid. Does not add anything but,
         * I want to keep for possible implementation later.
         *
         *
         //ScrollPane scrollPane = new ScrollPane();
        //StackPane canvasHolder = new StackPane(pixelBoard.getSpritePane());
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

        rightVBox = new VBox();
        rightVBox.setSpacing(5);
        mainBorderPane.setRight(rightVBox);
        mainPalette = new GridPane();
        Group paletteGroup = new Group();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y <= 7; y++){
                MainPaletteButton mainPaletteButton = new MainPaletteButton(this, y + (x * 8));
                paletteGroup.getChildren().add(mainPaletteButton);
                mainPalette.add(mainPaletteButton, y, x);
            }
        }
        updateColourPalette(0);


        ///////////////////////
        //Set up palletteArea//
        ///////////////////////

        ObservableList<String> availablePalettes = FXCollections.observableArrayList(
                "Palette 1",
                "Palette 2",
                "Palette 3",
                "Palette 4");
        paletteComboBox = new ComboBox(availablePalettes);
        paletteComboBox.getSelectionModel().selectFirst();
        setSelectedPalette(0);
        paletteComboBox.setOnAction(mouseEvent->{
            updateColourPalette( paletteComboBox.getSelectionModel().getSelectedIndex());
            pixelBoard.updatePalette(getSelectedPalette());
            pixelBoard.updateCanvas();
        });



        ///////////////////
        //Set up textArea//
        ///////////////////

        hexOutput = new TextArea();
        hexOutput.setPrefColumnCount(14);
        hexOutput.setPrefRowCount(9);
        hexOutput.setMinHeight(165);
        pixelBoard.returnHexText();
        rightVBox.getChildren().addAll(paletteComboBox, mainPalette,new Separator(), hexOutput);


        primaryStage.show();
    }

    public Color getPaletteColor(int index){
        Color[] tempColorPalette = (Color[])paletteList.get(selectedPalette);
        return tempColorPalette[index];
    }

    public void updateColourSelection(int Colour){
        for(Node p:pixelBoard.getSpritePane().getChildren()){
            if(p instanceof GridPane){
                for(Node pixel: ((GridPane) p).getChildren()) {
                    if (pixel instanceof PixelButton) {
                        ((PixelButton) pixel).setmColour(Colour);
                    }
                }
            }
        }
    }

    public void newCanvas(int x, int y){
        pixelBoard = null;
        pixelBoard = new PixelBoard(this, x, y);
        mainBorderPane.setCenter(pixelBoard.getSpritePane());
        pixelBoard.getSpritePane().setAlignment(Pos.CENTER);
        pixelBoard.returnHexText();
    }

    private void updateColourPalette(int colourPalette){
        int count = 0;
        selectedPalette = colourPalette;
        switch (colourPalette){
            case 0: for(Node c : mainPalette.getChildren()){
                if(c instanceof MainPaletteButton){
                    ((MainPaletteButton) c).setMColour(palette1Array[count]);
                    count++;
                }
            }
                break;
            case 1: for(Node c : mainPalette.getChildren()) {
                if (c instanceof MainPaletteButton) {
                    ((MainPaletteButton) c).setMColour(palette2Array[count]);
                    count++;
                }
            }
                break;
            case 2: for(Node c : mainPalette.getChildren()) {
                if (c instanceof MainPaletteButton) {
                    ((MainPaletteButton) c).setMColour(palette3Array[count]);
                    count++;
                }
            }
                break;
            case 3: for(Node c : mainPalette.getChildren()) {
                if (c instanceof MainPaletteButton) {
                    ((MainPaletteButton) c).setMColour(palette4Array[count]);
                    count++;
                }
            }
        }

    }

    public void setUpPalettes(GridPane one, GridPane two, GridPane three, GridPane four){
        int count = 0;
        for(Node n: one.getChildren()){
            if(n instanceof PaletteButton){
                palette1Array[count] = ((PaletteButton) n).getmColor();
                count++;
            }
        }
        count = 0;
        for(Node n: two.getChildren()){
            if(n instanceof PaletteButton){
                palette2Array[count] = ((PaletteButton) n).getmColor();
                count++;
            }
        }
        count = 0;
        for(Node n: three.getChildren()){
            if(n instanceof PaletteButton){
                palette3Array[count] = ((PaletteButton) n).getmColor();
                count++;
            }
        }
        count = 0;
        for(Node n: four.getChildren()){
            if(n instanceof PaletteButton){
                palette4Array[count] = ((PaletteButton) n).getmColor();
                count++;
            }
        }
        setSelectedPalette(0);
        paletteComboBox.getSelectionModel().selectFirst();
        updateColourPalette(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

