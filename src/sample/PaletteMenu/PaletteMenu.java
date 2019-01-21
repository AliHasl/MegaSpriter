package sample.PaletteMenu;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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


    Stage newPaletteWindow, parentStage;
    Color selectedColor = null;
    Main parentScreen;
    GridPane palette1, palette2, palette3, palette4;
    Boolean existingPalettes = false;
    Color[] existingPalette1, existingPalette2, existingPalette3, existingPalette4;
    Text selectedColourText;

    public Rectangle getSampleRect() {
        return sampleRect;
    }

    Rectangle sampleRect;

    public void setSelectedColourText(String newColourText) {
        selectedColourText.setText(newColourText);
    }


    public Color getSelectedColor() {
        return selectedColor;
    }

    public Scene getScene() {
        return newPaletteScene;
    }

    Scene newPaletteScene;

    public PaletteMenu(Stage previousStage, Main parent) {
        parentScreen = parent;
        newPaletteWindow = new Stage();
        parentStage = previousStage;
        InitialisePaletteMenu();

    }

    public PaletteMenu(Stage previousStage, Main parent, ArrayList previousPalettes){
        parentScreen = parent;
        newPaletteWindow = new Stage();
        parentStage = previousStage;
        existingPalettes = true;
        existingPalette1 = (Color[])previousPalettes.get(0);
        existingPalette2 = (Color[])previousPalettes.get(1);
        existingPalette3 = (Color[])previousPalettes.get(2);
        existingPalette4 = (Color[])previousPalettes.get(3);

        InitialisePaletteMenu();
    }

    private void InitialisePaletteMenu(){
        newPaletteWindow.setTitle("New palette");
        BorderPane contents = new BorderPane();
        newPaletteScene = new Scene(contents, 500, 340);
        newPaletteWindow.setMinWidth(520);
        newPaletteWindow.setMinHeight(380);
        newPaletteWindow.setScene(newPaletteScene);
        newPaletteWindow.initModality(Modality.WINDOW_MODAL);
        newPaletteWindow.initOwner(parentStage);
        newPaletteWindow.show();
        contents.setPadding(new Insets(10,10,10,20));
        FlowPane bottomOptions = new FlowPane();
        contents.setBottom(bottomOptions);
        bottomOptions.setHgap(10);
        bottomOptions.setAlignment(Pos.BOTTOM_RIGHT);

        ////////////////////
        //Colour Selection//
        ////////////////////

        VBox centralVBox = new VBox();
        centralVBox.setSpacing(5.0);
        GridPane colourGrid = new GridPane();
        int colourCount = 0;
        double r,g,b;
        for(int x = 0; x < 32; x++){
            for(int y = 0; y < 16; y++){

                if(colourCount >= 64){
                    r = colourCount / 32 % 16 * 0.0666;
                    g = colourCount / 16 % 16 * 0.0666;
                    b = colourCount % 16 * 0.0666;
                }
                else if(colourCount >= 16){
                    r = 0;
                    g = colourCount / 16 % 16 * 0.0666;
                    b = colourCount % 16 * 0.0666;
                }
                else
                {
                    r = 0;
                    g = 0;
                    b = colourCount % 16 * 0.0666;
                }
                ColourButton colourButton = new ColourButton(new Color(r,g,b,1), this);
                colourButton.setBackground(new Background(new BackgroundFill( new Color(r,g, b, 1)
                        ,CornerRadii.EMPTY, Insets.EMPTY)));
                colourButton.setMinSize(10,10);
                colourButton.setPrefSize(10,10);
                colourGrid.add(colourButton,x, y);
                colourCount++;
            }
        }

        selectedColourText = new Text();
        sampleRect = new Rectangle(20,20);
        sampleRect.setVisible(false);
        HBox horizontalAlignment = new HBox(sampleRect, selectedColourText);
        horizontalAlignment.setSpacing(10);
        horizontalAlignment.setAlignment(Pos.CENTER);

        centralVBox.getChildren().addAll(colourGrid ,horizontalAlignment);
        contents.setCenter(centralVBox);
        centralVBox.setAlignment(Pos.CENTER);
        ////////////////
        //Palette Area//
        ////////////////

        VBox vbox  = new VBox();
        vbox.setPadding(new Insets(10,0,10,20));
        palette1 = new GridPane();
        palette2 = new GridPane();
        palette3 = new GridPane();
        palette4 = new GridPane();
        Text palette1Text = new Text("Palette 1");
        Text palette2Text = new Text("Palette 2");
        Text palette3Text = new Text("Palette 3");
        Text palette4Text = new Text("Palette 4");
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 2; y++)
            {
                palette1.add(new PaletteButton(this), x, y);
                palette2.add(new PaletteButton(this),x , y);
                palette3.add(new PaletteButton(this), x, y);
                palette4.add(new PaletteButton(this), x, y);
            }
        }

        if(existingPalettes){
            System.out.println("YOYOYO");
            int count = 0;
            for (Node n : palette1.getChildren()){
                if(n instanceof PaletteButton){
                    ((PaletteButton) n).setmColor(existingPalette1[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette2.getChildren()){
                if(n instanceof PaletteButton){
                    ((PaletteButton) n).setmColor(existingPalette2[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette3.getChildren()){
                if(n instanceof PaletteButton){
                    ((PaletteButton) n).setmColor(existingPalette3[count]);
                    count++;
                }
            }
            count = 0;
            for (Node n : palette4.getChildren()){
                if(n instanceof PaletteButton){
                    ((PaletteButton) n).setmColor(existingPalette4[count]);
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
        Button loadButton = new Button("Load...");
        bottomOptions.getChildren().addAll(saveAsButton, loadButton, applyButton, cancelButton);
        Stage thisStage = (Stage) this.getScene().getWindow();
        cancelButton.setCancelButton(true);
        applyButton.setDefaultButton(true);

        cancelButton.setOnAction(mouseEvent->thisStage.close());

        applyButton.setOnAction(mouseEvent->{
            parentScreen.setUpPalettes(palette1, palette2, palette3, palette4);
            thisStage.close();
        });
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public GridPane returnPalette(GridPane palette){
        return palette;
    }
}
