package sample.PaletteMenu;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.util.ArrayList;

public class PaletteMenu {

    private Stage newPaletteWindow, parentStage;
    private Color selectedColor = null;
    private Main parentScreen;
    private GridPane palette1, palette2, palette3, palette4;
    private Boolean existingPalettes = false;
    private Color[] existingPalette1, existingPalette2, existingPalette3, existingPalette4;
    private Text selectedColourText;
    private Rectangle sampleRect;
    private Scene newPaletteScene;

    public Rectangle getSampleRect() {
        return sampleRect;
    }

    public void setSelectedColourText(String newColourText) {
        selectedColourText.setText(newColourText);
    }


    public Color getSelectedColor() {
        return selectedColor;
    }

    public Scene getScene() {
        return newPaletteScene;
    }

    public PaletteMenu(Stage previousStage, Main parent) {
        parentScreen = parent;
        newPaletteWindow = new Stage();
        parentStage = previousStage;
        InitialisePaletteMenu();
    }

    public PaletteMenu(Stage previousStage, Main parent, ArrayList previousPalettes) {
        parentScreen = parent;
        newPaletteWindow = new Stage();
        parentStage = previousStage;
        existingPalettes = true;
        existingPalette1 = (Color[]) previousPalettes.get(0);
        existingPalette2 = (Color[]) previousPalettes.get(1);
        existingPalette3 = (Color[]) previousPalettes.get(2);
        existingPalette4 = (Color[]) previousPalettes.get(3);

        InitialisePaletteMenu();
    }

    private void InitialisePaletteMenu() {
        newPaletteWindow.setTitle("New palette");
        BorderPane contents = new BorderPane();
        newPaletteScene = new Scene(contents, 500, 340);
        newPaletteWindow.setMinWidth(520);
        newPaletteWindow.setMinHeight(380);
        newPaletteWindow.setScene(newPaletteScene);
        newPaletteWindow.initModality(Modality.WINDOW_MODAL);
        newPaletteWindow.initOwner(parentStage);
        newPaletteWindow.show();
        contents.setPadding(new Insets(10, 10, 10, 20));
        FlowPane bottomOptions = new FlowPane();
        contents.setBottom(bottomOptions);
        bottomOptions.setHgap(10);
        bottomOptions.setAlignment(Pos.BOTTOM_RIGHT);

        ////////////////////
        //Colour Selection//
        ////////////////////

        VBox centralVBox = new VBox();
        centralVBox.setSpacing(5.0);
        GridPane colourGrid1 = new GridPane();
        GridPane colourGrid2 = new GridPane();
        int colourCount = 0;
        double r, g, b;
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 8; y++) {

                r = colourCount / 64 % 8 * 0.125 + 0.01;
                g = colourCount / 8 % 8 * 0.125 + 0.01;
                b = colourCount % 8 * 0.125 + 0.01;

                ColourButton colourButton = new ColourButton(new Color(r, g, b, 1), this);
                colourButton.setBackground(new Background(new BackgroundFill(new Color(r, g, b, 1)
                        , CornerRadii.EMPTY, Insets.EMPTY)));
                colourButton.setMinSize(10, 10);
                colourButton.setPrefSize(10, 10);
                if (colourCount < 256) {
                    colourGrid1.add(colourButton, x, y);
                } else {
                    colourGrid2.add(colourButton, x, y);
                }
                colourCount++;
            }
        }

        selectedColourText = new Text();
        sampleRect = new Rectangle(20, 20);
        sampleRect.setVisible(false);
        HBox horizontalAlignment = new HBox(sampleRect, selectedColourText);
        horizontalAlignment.setSpacing(10);
        horizontalAlignment.setAlignment(Pos.CENTER);

        centralVBox.getChildren().addAll(colourGrid1, colourGrid2, horizontalAlignment);
        contents.setCenter(centralVBox);
        centralVBox.setAlignment(Pos.CENTER);
        ////////////////
        //Palette Area//
        ////////////////

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 0, 10, 20));
        palette1 = new GridPane();
        palette2 = new GridPane();
        palette3 = new GridPane();
        palette4 = new GridPane();
        Text palette1Text = new Text("Palette 1");
        Text palette2Text = new Text("Palette 2");
        Text palette3Text = new Text("Palette 3");
        Text palette4Text = new Text("Palette 4");
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 8; y++) {
                palette1.add(new PaletteButton(this), y, x);
                palette2.add(new PaletteButton(this), y, x);
                palette3.add(new PaletteButton(this), y, x);
                palette4.add(new PaletteButton(this), y, x);
            }
        }

        if (existingPalettes) {
            int count = 0;
            for (Node n : palette1.getChildren()) {
                if (n instanceof PaletteButton) {
                    ((PaletteButton) n).setMColor(existingPalette1[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette2.getChildren()) {
                if (n instanceof PaletteButton) {
                    ((PaletteButton) n).setMColor(existingPalette2[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette3.getChildren()) {
                if (n instanceof PaletteButton) {
                    ((PaletteButton) n).setMColor(existingPalette3[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette4.getChildren()) {
                if (n instanceof PaletteButton) {
                    ((PaletteButton) n).setMColor(existingPalette4[count]);
                    count++;
                }
            }
        }

        vbox.getChildren().addAll(palette1Text, palette1, palette2Text, palette2, palette3Text, palette3, palette4Text, palette4);
        contents.setRight(vbox);


        /////////////////
        //Lower Section//
        /////////////////
        Button applyButton = new Button("Apply");
        Button cancelButton = new Button("Cancel");
        Button saveAsButton = new Button("Save As...");
        saveAsButton.setDisable(true);
        Button loadButton = new Button("Load...");
        loadButton.setDisable(true);
        bottomOptions.getChildren().addAll(saveAsButton, loadButton, applyButton, cancelButton);
        Stage thisStage = (Stage) this.getScene().getWindow();
        cancelButton.setCancelButton(true);
        applyButton.setDefaultButton(true);

        cancelButton.setOnAction(mouseEvent -> thisStage.close());

        applyButton.setOnAction(mouseEvent -> {
            parentScreen.setUpPalettes(palette1, palette2, palette3, palette4);
            parentScreen.formatPaletteText();
            parentScreen.updateCanvas();
            parentScreen.setSelectedColour(parentScreen.getPaletteColor(0));
            parentScreen.updateColourSelection(0);
            parentScreen.getColourRect().setFill(parentScreen.getSelectedColour());
            parentScreen.getSelectedColourText().setText(parentScreen.formatColourText
                    (parentScreen.getSelectedColour().toString()));
            thisStage.close();
        });
    }

    void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
