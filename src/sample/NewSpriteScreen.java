package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

class NewSpriteScreen {

    private Main parentScreen;
    private TextField setXText;
    private TextField setYText;


    private Scene getScene() {
        return newSpriteScene;
    }

    private Scene newSpriteScene;

    NewSpriteScreen(Stage PreviousStage, Main parent) {
        parentScreen = parent;
        Stage newSpriteWindow = new Stage();
        newSpriteWindow.setTitle("New Sprite");
        newSpriteWindow.setMinWidth(380);
        newSpriteWindow.setMinHeight(140);
        BorderPane contents = new BorderPane();
        newSpriteScene = new Scene(contents, 360, 100);
        newSpriteWindow.setScene(newSpriteScene);
        newSpriteWindow.initModality(Modality.WINDOW_MODAL);
        newSpriteWindow.initOwner(PreviousStage);
        newSpriteWindow.show();
        contents.setPadding(new Insets(10, 10, 10, 20));

        //////////////////////////
        //Set Size Entry Section//
        //////////////////////////
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        HBox horizontalBar1 = new HBox();
        HBox horizontalBar2 = new HBox();
        horizontalBar1.setSpacing(10);
        horizontalBar2.setSpacing(10);
        setXText = new TextField();
        setXText.setPrefColumnCount(2);
        setXText.setAlignment(Pos.CENTER);
        setYText = new TextField();
        setYText.setPrefColumnCount(2);
        setYText.setAlignment(Pos.CENTER);
        horizontalBar1.getChildren().addAll(new Text("Set X axis"), setXText);
        horizontalBar2.getChildren().addAll(new Text("Set Y axis"), setYText);
        vBox.getChildren().addAll(horizontalBar1, horizontalBar2);
        contents.setLeft(vBox);

        ///////////////////////////
        //Set Description Section//
        ///////////////////////////
        Text descriptionText = new Text("Set number of tiles in canvas,\neach tile is an 8x8 pixel grid.");
        contents.setCenter(descriptionText);
        descriptionText.setTextAlignment(TextAlignment.CENTER);

        /////////////////////
        //Set Lower Section//
        /////////////////////
        FlowPane bottomOptions = new FlowPane();
        contents.setBottom(bottomOptions);
        bottomOptions.setHgap(10);
        bottomOptions.setAlignment(Pos.BOTTOM_RIGHT);
        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");
        Text errorText = new Text();
        errorText.setFill(Color.RED);
        bottomOptions.getChildren().addAll(errorText, createButton, cancelButton);
        Stage thisStage = (Stage) this.getScene().getWindow();
        cancelButton.setCancelButton(true);
        createButton.setDefaultButton(true);

        cancelButton.setOnAction(mouseEvent -> thisStage.close());

        createButton.setOnAction(mouseEvent -> {
            try {
                if (Integer.parseInt(setXText.getText()) <= 0 || Integer.parseInt(setYText.getText()) <= 0) {
                    errorText.setText("Please enter positive number values");
                } else {
                    parentScreen.newCanvas(Integer.parseInt(setYText.getText()), Integer.parseInt(setXText.getText()));
                    parentScreen.updateCanvas();
                    thisStage.close();
                }
            } catch (NumberFormatException n) {
                errorText.setText("Please enter two number values");
            }
        });
    }
}
