package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewSpriteScreen  {

    Main parentScreen;
    TextField setXText;
    TextField setYText;


    public Scene getScene() {
        return newSpriteScene;
    }

    Scene newSpriteScene;

    NewSpriteScreen(Stage PreviousStage, Main parent){
        parentScreen = parent;
        Stage newSpriteWindow = new Stage();
        newSpriteWindow.setTitle("New Sprite");
        newSpriteWindow.setMinWidth(380);
        newSpriteWindow.setMinHeight(140);
        BorderPane contents = new BorderPane();
        newSpriteScene = new Scene(contents,360,100);
        newSpriteWindow.setScene(newSpriteScene);
        newSpriteWindow.initModality(Modality.WINDOW_MODAL);
        newSpriteWindow.initOwner(PreviousStage);
        newSpriteWindow.show();

        contents.setPadding(new Insets(10,10,10,20));
        FlowPane bottomOptions = new FlowPane();
        contents.setBottom(bottomOptions);
        bottomOptions.setHgap(10);
        bottomOptions.setAlignment(Pos.BOTTOM_RIGHT);

        VBox vBox = new VBox();
        HBox horizontalBar1 = new HBox();
        HBox horizontalBar2 = new HBox();

        setXText = new TextField();
        setXText.setPrefColumnCount(2);
        setYText = new TextField();
        setYText.setPrefColumnCount(2);
        horizontalBar1.getChildren().addAll(new Text("Set X axis" ),setXText);
        horizontalBar2.getChildren().addAll(new Text("Set Y axis" ), setYText);
        vBox.getChildren().addAll(horizontalBar1, horizontalBar2);

        contents.setCenter(vBox);


        /////////////////
        //Lower Section//
        /////////////////
        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        bottomOptions.getChildren().addAll(errorText, createButton, cancelButton);
        Stage thisStage = (Stage) this.getScene().getWindow();
        cancelButton.setCancelButton(true);
        createButton.setDefaultButton(true);

        cancelButton.setOnAction(mouseEvent->thisStage.close());

        createButton.setOnAction(mouseEvent-> {

            try {
                if (Integer.parseInt(setXText.getText()) <= 0 || Integer.parseInt(setYText.getText()) <= 0) {
                    errorText.setText("Please enter positive number values");
                } else {
                    parentScreen.newCanvas(Integer.parseInt(setXText.getText()), Integer.parseInt(setYText.getText()));
                    thisStage.close();
                }
            } catch (NumberFormatException n) {
                errorText.setText("Please enter two number values");
            }

        });
    }



}
