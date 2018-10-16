package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BorderPane borderPane = new BorderPane();

        primaryStage.setTitle("Hello World");
        Group root = new Group();
        Scene scene = new Scene(borderPane, 500,200);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);

        GridPane gridPane = new GridPane();

        borderPane.setCenter(root);
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++) {
                //RowConstraints row = new RowConstraints(100);
                PixelButton button = new PixelButton();
                /*
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setEffect(shadow);
                    }
                });
                */
                //gridPane.getRowConstraints().add(row);
                gridPane.add(button, x, y);
            }
        }
        /*
        Button button1 = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();
        Button button5 = new Button();
        Button button6 = new Button();
        Button button7 = new Button();
        Button button8 = new Button();
        Button button9 = new Button();
        */
        //gridPane.setAlignment(Pos.CENTER);

        root.getChildren().add(gridPane);

        /*
        tilePane/getChildren().add(button1);
        tilePane.getChildren().add(button2);
        tilePane.getChildren().add(button3);
        tilePane.getChildren().add(button4);
        tilePane.getChildren().add(button5);
        tilePane.getChildren().add(button6);
        tilePane.getChildren().add(button7);
        tilePane.getChildren().add(button8);
        tilePane.getChildren().add(button9);
        */



       // primaryStage.setScene(new Scene(tilePane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
