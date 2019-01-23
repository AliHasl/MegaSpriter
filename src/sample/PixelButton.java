package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by Alistair on 16/10/2018.
 */


class PixelButton extends Button {

    private Main mainScreen;
    private PixelBoard parent;
    private int mColour;
    private int paintedColour;

    int getPaintedColour() {
        return paintedColour;
    }

    void setBackgroundColor(Color newColor) {
        this.setBackground(new Background(new BackgroundFill
                (newColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    void setMColour(int mColour) {
        this.mColour = mColour;
    }

    PixelButton(PixelBoard owner, Main topLevel) {
        mainScreen = topLevel;
        parent = owner;
        mColour = 0;
        this.setMinSize(2, 2);
        this.setPrefSize(10, 10);
        this.setOnDragDetected(mouseEvent -> this.startFullDrag());
        this.setOnMouseDragEntered(mouseEvent -> fire());
        this.setOnMousePressed(mouseEvent -> fire());

        this.setOnAction(mouseEvent -> {
            this.setBackground(new Background(new BackgroundFill
                    (mainScreen.getSelectedColour(), CornerRadii.EMPTY, Insets.EMPTY)));
            paintedColour = mColour;
            updateHexText();
        });

        this.setOnScroll(mouseEvent ->
            parent.zoom(mouseEvent.getDeltaY()));
    }

    private void updateHexText() {
        parent.returnHexText();
    }
}
