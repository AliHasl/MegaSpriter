package sample.PaletteMenu;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import java.awt.*;

public class ColourButton extends Button {

    public Color getmColor() {
        return mColor;
    }

    private Color mColor;
    private PaletteMenu parentMenu;

    ColourButton(Color thisColor, PaletteMenu parent){
        mColor = thisColor;
        parentMenu = parent;

        this.setOnAction(mouseEvent-> parentMenu.setSelectedColor(mColor));

    }


}
