package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewSpriteScreen  {

    public Scene getScene() {
        return newSpriteScene;
    }

    Scene newSpriteScene;

    NewSpriteScreen(){
        Button button = new Button("Your mum");
        BorderPane contents = new BorderPane();
        contents.setCenter(button);
        newSpriteScene = new Scene(contents,500,300);

    }



}
