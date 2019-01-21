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

        this.setOnAction(mouseEvent-> {
            parentMenu.setSelectedColor(mColor);
            String mColourString = mColor.toString().toUpperCase();
            StringBuilder formattedColourString = new StringBuilder("Selected Colour = 0x0");
            formattedColourString.append(mColourString.charAt(3));
            formattedColourString.append(mColourString.charAt(5));
            formattedColourString.append(mColourString.charAt(7));
            parentMenu.setSelectedColourText(formattedColourString.toString());

            if(!parentMenu.getSampleRect().isVisible()){
                parentMenu.getSampleRect().setVisible(true);
            }

            parentMenu.getSampleRect().setFill(mColor);
        });

    }


}
