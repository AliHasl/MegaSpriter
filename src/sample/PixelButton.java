package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by u1773783 on 16/10/2018.
 */


public class PixelButton extends Button {

    PixelBoard parent;

    private Color[] paletteOneColours = {Color.BLACK, Color.GREEN, Color.PURPLE, Color.RED, Color.OLDLACE, Color.ORANGE,
            Color.PINK, Color.DEEPPINK, Color.LAVENDERBLUSH, Color.YELLOW, Color.CYAN, Color.PURPLE, Color.ALICEBLUE,
            Color.AZURE, Color.FUCHSIA, Color.PEACHPUFF};

    private int mColour;
    private int mPalette;
    private int paintedColour;

    public int getPaintedColour() {
        return paintedColour;
    }

    public int getmPalette() {
        return mPalette;
    }

    public void setmPalette(int mPalette) {
        this.mPalette = mPalette;
    }


    public int getMColour() {
        return mColour;
    }

    public void setmColour(int mColour) {
        this.mColour = mColour;
    }

    public PixelButton(PixelBoard owner) {
        parent = owner;
        mColour = 0;
        this.setMinSize(10, 10);
        this.setPrefSize(10, 10);

        this.setOnDragDetected(mouseEvent -> this.startFullDrag());
        this.setOnMouseDragEntered(mouseEvent -> fire());

        this.setOnMousePressed(mouseEvent -> fire());

        this.setOnAction(mouseEvent -> {
            this.setBackground(new Background(new BackgroundFill
                    (paletteOneColours[mColour], CornerRadii.EMPTY, Insets.EMPTY)));
            paintedColour = mColour;
            updateOctalText();
        });

        this.setOnScroll(mouseEvent -> {parent.zoom(mouseEvent.getDeltaY());

        });




    }

    private void updateOctalText(){
        parent.returnHexText();
    }

    public void increasePixelSize(){
        this.resize(20,20);
    }



}
