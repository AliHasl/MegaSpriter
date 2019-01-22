package sample.PaletteMenu;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PaletteButton extends Button {

    private PaletteMenu parentMenu;

    public Color getMColor() {
        return mColor;
    }

    private Color mColor = null;


    void setMColor(Color mColor) {
        this.mColor = mColor;
        this.setBackground(new Background(new BackgroundFill(mColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    PaletteButton(PaletteMenu parent) {
        parentMenu = parent;
        this.setOnAction(mouseEvent -> setMColor(parentMenu.getSelectedColor()));
    }
}
