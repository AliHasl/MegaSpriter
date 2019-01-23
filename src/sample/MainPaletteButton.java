package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class MainPaletteButton extends Button {

    private int mColourRef;

    public Color getmColour() {
        return mColour;
    }

    private Color mColour;
    private Main parent;

    MainPaletteButton(Main owner, int colourRef) {
        parent = owner;
        mColourRef = colourRef;
        this.setMinSize(25, 30);
        this.setPrefSize(25, 30);
        this.setOnAction(mouseEvent -> {
            parent.setSelectedColour(mColour);
            parent.updateColourSelection(mColourRef);
            if(!parent.getColourRect().isVisible()){
                parent.getColourRect().setVisible(true);
            }
            parent.setColourPreview(mColour);
            parent.setSelectedColourRef(mColourRef);
        });
    }

    void setMColour(Color newColor) {
        mColour = newColor;
        this.setBackground(new Background(new BackgroundFill(mColour, CornerRadii.EMPTY, Insets.EMPTY)));

    }

}
