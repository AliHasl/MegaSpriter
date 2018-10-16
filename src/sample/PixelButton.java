package sample;

import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * Created by u1773783 on 16/10/2018.
 */


public class PixelButton extends Button {





    public PixelButton() {
        //Button button = this;





        this.setMinSize(10, 10);
        this.setPrefSize(10, 10);
        //this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

       // this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY))));
        if(mouseDown) {
            this.addEventHandler(MouseEvent.DRAG_DETECTED, event -> this.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY))));
        }

    }
    
    private boolean mouseDown;
    public void handle(MouseEvent t) {
        if (t.getButton() == MouseButton.PRIMARY)
            mouseDown = true;
        else
            mouseDown = false;
    }


}
/*
    @Override
    public void changeColour() {
        cancelButtonProperty().colour = pink
    }
    getColour{
        return this.rgb;
    }
*/
