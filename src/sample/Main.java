package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {



    private TextArea octalOutput;
    private PixelBoard pixelBoard;
    private int selectedColour;
    private int selectedPalette;

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

    public TextArea getOctalOutput() {
        return octalOutput;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //////////////////
        //Set up Menubar//
        //////////////////
        final Menu fileMenu = new Menu("File");
        final MenuItem newMenuItem = new MenuItem("New...");
        final MenuItem importMenutItem = new MenuItem("Import...");
        final MenuItem exportMenuItem = new MenuItem("Export...");
        final MenuItem saveMenuItem = new MenuItem("Save");
        final MenuItem saveAsMenuItem = new MenuItem("Save As...");
        final MenuItem quit = new MenuItem("Quit");
        MenuBar menubar = new MenuBar();
        menubar.getMenus().add(fileMenu);
        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), saveMenuItem, saveAsMenuItem,
                new SeparatorMenuItem(), importMenutItem, exportMenuItem, new SeparatorMenuItem(), quit);

        //////////////////////////
        //Set up New... MenuItem//
        //////////////////////////
        newMenuItem.setOnAction(mouseEvent->{
            Stage newSpriteWindow = new Stage();
            newSpriteWindow.setTitle("New Sprite");
            NewSpriteScreen newSpriteScreen = new NewSpriteScreen();
            newSpriteWindow.setScene(newSpriteScreen.getScene());
            newSpriteWindow.initModality(Modality.WINDOW_MODAL);
            newSpriteWindow.initOwner(primaryStage);
            newSpriteWindow.show();
        });

        ////////////////////////
        //Set up Quit MenuItem//
        ////////////////////////
        quit.setOnAction(mouseEvent->System.exit(0));

        ////////////////////
        //Set up mainScene//
        ////////////////////

        //Parent PaintArea = FXMLLoader.load(getClass().getResource("sample.fxml"));

        VBox topLevelContainer = new VBox();
        BorderPane mainScene = new BorderPane();
        mainScene.setPadding(new Insets(10,10,10,10));
        primaryStage.setTitle("SpriteSheet");
        Scene scene = new Scene(topLevelContainer, 500,300);
        topLevelContainer.getChildren().addAll(menubar, mainScene);


        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);

        /////////////////////
        //Set up toolBoxArea//
        /////////////////////


        ////////////////////
        //Set up paintArea//
        ////////////////////
        Group paintArea = new Group();
        pixelBoard = new PixelBoard(this);
        paintArea.getChildren().add(pixelBoard.getGridPane());
        mainScene.setCenter(paintArea);

        ///////////////////
        //Set up palette//
        ///////////////////
        BorderPane borderPaneRight = new BorderPane();
        mainScene.setRight(borderPaneRight);

        GridPane palette = new GridPane();
        Group paletteGroup = new Group();

        for(int x = 0; x < 8; x++){
            for(int y = 0; y <= 1; y++){
                PaletteButton paletteButton = new PaletteButton(this, x + (y * 8));
                paletteGroup.getChildren().add(paletteButton);
                palette.add(paletteButton, x, y);
            }
        }
        borderPaneRight.setCenter(palette);

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
        borderPaneRight.setTop(comboBox);


        ///////////////////
        //Set up textArea//
        ///////////////////

        octalOutput = new TextArea();
        octalOutput.setPrefColumnCount(10);
        octalOutput.setPrefRowCount(10);
        octalOutput.setWrapText(true);
        borderPaneRight.setBottom(octalOutput);
        pixelBoard.returnOctalText();


        primaryStage.show();
    }

    public void updateColourSelection(int Colour){
        for(Node p:pixelBoard.getGridPane().getChildren()){
            if(p instanceof PixelButton){
                ((PixelButton) p).setmColour(Colour);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
