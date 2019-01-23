package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.PaletteMenu.PaletteButton;
import sample.PaletteMenu.PaletteMenu;

import java.util.ArrayList;

public class Main extends Application {

    private TextArea hexOutput, paletteTextArea;
    private PixelBoard pixelBoard;
    private Color selectedColour;
    private int selectedPalette;
    private BorderPane mainBorderPane;
    private ComboBox paletteComboBox;
    private GridPane mainPalette;
    private Rectangle colourRect;
    private Text selectedColourText;

    public void setSelectedColourRef(int selectedColourRef) {
        this.selectedColourRef = selectedColourRef;
    }

    private int selectedColourRef;

    private Color[] palette1Array = {Color.web("0xe203e2ff"), Color.web("0x0303e2ff"), Color.web("0x0342e2ff"),
            Color.web("0x03a2e2ff"), Color.web("0x03e2e2ff"), Color.web("0x030303ff"), Color.web("0x034203ff"),
            Color.web("0x038203ff"), Color.web("0x03e203ff"), Color.web("0x038203ff"), Color.web("0xe28203ff"),
            Color.web("0xc242a2ff"), Color.web("0xe2e2e2ff"), Color.web("0x828282ff"), Color.web("0xa28282ff"),
            Color.web("0xe2e2e2ff")};
    private Color[] palette2Array, palette3Array, palette4Array;
    private ArrayList paletteList;

    public Text getSelectedColourText() {
        return selectedColourText;
    }

    private void setSelectedPalette(int selectedPalette) {
        this.selectedPalette = selectedPalette;
    }

    public Color getSelectedColour() {
        return selectedColour;
    }

    public void setSelectedColour(Color selectedColour) {
        this.selectedColour = selectedColour;
    }

    TextArea getHexOutput() {
        return hexOutput;
    }

    public Rectangle getColourRect() {
        return colourRect;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        palette2Array = new Color[16];
        palette3Array = new Color[16];
        palette4Array = new Color[16];
        paletteList = new ArrayList();
        paletteList.add(palette1Array);
        paletteList.add(palette2Array);
        paletteList.add(palette3Array);
        paletteList.add(palette4Array);
        selectedColourRef = 0;

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

        final Menu helpMenu = new Menu("Help");
        final MenuItem about = new MenuItem("About");

        menubar.getMenus().addAll(fileMenu, paletteMenu, helpMenu);
        helpMenu.getItems().add(about);
        fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), saveMenuItem, saveAsMenuItem,
                new SeparatorMenuItem(), importMenuItem, exportMenuItem, new SeparatorMenuItem(), quit);
        paletteMenu.getItems().addAll(newPalette, editPalette, new SeparatorMenuItem(), savePalette, loadPalette);

        //////////////////////////
        //Set up New... MenuItem//
        //////////////////////////
        newMenuItem.setOnAction(mouseEvent -> new NewSpriteScreen(primaryStage, this));

        ////////////////////////
        //Set up Quit MenuItem//
        ////////////////////////
        quit.setOnAction(mouseEvent -> System.exit(0));

        ///////////////////////////////
        //Set up New Palette MenuItem//
        ///////////////////////////////
        newPalette.setOnAction(mouseEvent -> new PaletteMenu(primaryStage, this));
        editPalette.setOnAction(mouseEvent -> new PaletteMenu(primaryStage, this, paletteList));

        /////////////////////////
        //Set up About MenuItem//
        /////////////////////////
        about.setOnAction(mouseEvent->{
            final Stage aboutPopup = new Stage();
            aboutPopup.setMinWidth(200);
            aboutPopup.setMinHeight(160);
            aboutPopup.setTitle("About MegaSpriter");
            BorderPane aboutBorderPane = new BorderPane();
            Scene aboutScene = new Scene(aboutBorderPane);
            aboutBorderPane.setPadding(new Insets(10,10,10,10));
            aboutPopup.initModality(Modality.WINDOW_MODAL);
            aboutPopup.initOwner(primaryStage);
            aboutPopup.setScene(aboutScene);

            Button okButton = new Button("Ok");
            HBox okHBox = new HBox(okButton);
            aboutBorderPane.setBottom(okHBox);
            okHBox.setAlignment(Pos.CENTER);
            okButton.setOnAction(click->aboutPopup.close());

            Text aboutText = new Text("MegaSpriter v1.0\n\u00A9 Alistair Haslam 2019");
            aboutBorderPane.setCenter(aboutText);
            aboutText.setTextAlignment(TextAlignment.CENTER);

            aboutPopup.show();

        });


        ////////////////////
        //Set up mainScene//
        ////////////////////

        VBox topLevelContainer = new VBox();
        mainBorderPane = new BorderPane();
        mainBorderPane.setPadding(new Insets(10, 10, 10, 10));
        primaryStage.setTitle("MegaSpriter");
        Scene scene = new Scene(topLevelContainer, 480, 480);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        topLevelContainer.getChildren().addAll(menubar, mainBorderPane);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);

        /////////////////////
        //Set up toolBoxArea//
        /////////////////////


        ////////////////////
        //Set up CanvasArea//
        ////////////////////
        pixelBoard = new PixelBoard(this);
        mainBorderPane.setCenter(pixelBoard.getSpritePane());
        pixelBoard.getSpritePane().setAlignment(Pos.CENTER);
        selectedColour = palette1Array[0];

        ///////////////////
        //Set up palette//
        ///////////////////
        VBox rightVBox = new VBox();
        rightVBox.setSpacing(5);
        mainBorderPane.setRight(rightVBox);
        mainPalette = new GridPane();
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y <= 7; y++) {
                MainPaletteButton mainPaletteButton = new MainPaletteButton(this, y + (x * 8));
                if (x == 0 && y == 0) {
                    mainPaletteButton.setText("T");
                }
                mainPalette.add(mainPaletteButton, y, x);
            }
        }
        updateColourPalette(0);
        updateCanvas();

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

        paletteComboBox.setOnAction(mouseEvent -> {
            updateColourPalette(paletteComboBox.getSelectionModel().getSelectedIndex());
            getCurrentColour();
            setColourPreview(selectedColour);
            updateCanvas();
        });

        //////////////////////////
        //Set up Canvas textArea//
        //////////////////////////
        hexOutput = new TextArea();
        hexOutput.setPrefColumnCount(14);
        hexOutput.setPrefRowCount(9);
        hexOutput.setMinHeight(165);
        pixelBoard.returnHexText();

        ///////////////////////////
        //Set up Palette textArea//
        ///////////////////////////
        paletteTextArea = new TextArea();
        paletteTextArea.setPrefColumnCount(18);
        paletteTextArea.setPrefRowCount(5);
        paletteTextArea.setMinHeight(100);
        formatPaletteText();
        HBox colourIndicator = new HBox();
        colourIndicator.setSpacing(10);

        colourRect = new Rectangle(20,20);
        colourRect.setVisible(false);
        selectedColourText = new Text();
        colourIndicator.getChildren().addAll(colourRect, selectedColourText);
        rightVBox.getChildren().addAll(paletteComboBox, mainPalette, colourIndicator, new Separator(),hexOutput, new Separator(), paletteTextArea);

        primaryStage.show();
    }

    public void formatPaletteText() {

        StringBuilder outputString = new StringBuilder();
        String[] paletteNames = {"PALETTE1:\nDC.W    ", "PALETTE2:\nDC.W    ", "PALETTE3:\nDC.W    ", "PALETTE4:\nDC.W    "};
        for (int index = 0; index < 4; index++) {
            outputString.append(paletteNames[index]);
            int count = 0;
            for (Color c : (Color[]) paletteList.get(index)) {
                if (count == 4) {
                    outputString.append("\nDC.W    ");
                    count = 0;
                }
                try {
                    outputString.append(formatColourText(c.toString()));
                    count++;
                } catch (NullPointerException nullPointerException) {
                    outputString.append(" $0000");
                    count++;
                }
                if (count != 4) {
                    outputString.append(",");
                }
            }
            outputString.append("\n\n");
        }
        paletteTextArea.setText(outputString.toString());
    }

    public String formatColourText(String inColour) {
        StringBuilder outputString = new StringBuilder(" $0");
        outputString.append(inColour.charAt(6));
        outputString.append(inColour.charAt(4));
        outputString.append(inColour.charAt(2));

        return outputString.toString().toUpperCase();
    }

    public Color getPaletteColor(int index) {
        Color[] tempColorPalette = (Color[]) paletteList.get(selectedPalette);
        return tempColorPalette[index];
    }

    public void setColourPreview(Color col){
        colourRect.setFill(col);
        try {
            selectedColourText.setText("Selected Colour = " + formatColourText(col.toString()));
        }catch(NullPointerException nullPointerException){
            selectedColourText.setText("Selected Colour = NULL");
        }
    }

    public void getCurrentColour(){
         int colourIndex = selectedColourRef;
        for(Node n : mainPalette.getChildren()){
            if(n instanceof MainPaletteButton){
                if(colourIndex != 0){
                    colourIndex--;
                }
                else{
                    setSelectedColour(((MainPaletteButton) n).getmColour());
                    break;
                }
            }
        }
    }

    public void updateColourSelection(int colourRef) {
        for (Node p : pixelBoard.getSpritePane().getChildren()) {
            if (p instanceof GridPane) {
                for (Node pixel : ((GridPane) p).getChildren()) {
                    if (pixel instanceof PixelButton) {
                        ((PixelButton) pixel).setMColour(colourRef);
                    }
                }
            }
        }
    }

    void newCanvas(int x, int y) {
        pixelBoard = null;
        pixelBoard = new PixelBoard(this, x, y);
        mainBorderPane.setCenter(pixelBoard.getSpritePane());
        pixelBoard.getSpritePane().setAlignment(Pos.CENTER);
        pixelBoard.returnHexText();
    }

    private void updateColourPalette(int colourPalette) {
        int count = 0;
        selectedPalette = colourPalette;
        switch (colourPalette) {
            case 0:
                for (Node c : mainPalette.getChildren()) {
                    if (c instanceof MainPaletteButton) {
                        ((MainPaletteButton) c).setMColour(palette1Array[count]);
                        count++;
                    }
                }
                break;
            case 1:
                for (Node c : mainPalette.getChildren()) {
                    if (c instanceof MainPaletteButton) {
                        ((MainPaletteButton) c).setMColour(palette2Array[count]);
                        count++;
                    }
                }
                break;
            case 2:
                for (Node c : mainPalette.getChildren()) {
                    if (c instanceof MainPaletteButton) {
                        ((MainPaletteButton) c).setMColour(palette3Array[count]);
                        count++;
                    }
                }
                break;
            case 3:
                for (Node c : mainPalette.getChildren()) {
                    if (c instanceof MainPaletteButton) {
                        ((MainPaletteButton) c).setMColour(palette4Array[count]);
                        count++;
                    }
                }
        }
    }

    public void setUpPalettes(GridPane one, GridPane two, GridPane three, GridPane four) {
        int count = 0;
        for (Node n : one.getChildren()) {
            if (n instanceof PaletteButton) {
                palette1Array[count] = ((PaletteButton) n).getMColor();
                count++;
            }
        }
        count = 0;
        for (Node n : two.getChildren()) {
            if (n instanceof PaletteButton) {
                palette2Array[count] = ((PaletteButton) n).getMColor();
                count++;
            }
        }
        count = 0;
        for (Node n : three.getChildren()) {
            if (n instanceof PaletteButton) {
                palette3Array[count] = ((PaletteButton) n).getMColor();
                count++;
            }
        }
        count = 0;
        for (Node n : four.getChildren()) {
            if (n instanceof PaletteButton) {
                palette4Array[count] = ((PaletteButton) n).getMColor();
                count++;
            }
        }
        setSelectedPalette(0);
        paletteComboBox.getSelectionModel().selectFirst();
        updateColourPalette(0);
    }

    public void updateCanvas() {
        pixelBoard.updateCanvas();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

