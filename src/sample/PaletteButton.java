package sample;
import javafx.scene.control.Button;

public class PaletteButton extends Button {


    private int mColour;
    private Main parent;
    public int getmColour() {
        return mColour;
    }

    PaletteButton(Main owner, int ColourNumber){
        parent = owner;


        mColour = ColourNumber;

        this.setOnAction(mouseEvent->{parent.setSelectedColour(mColour);
        parent.updateColourSelection(mColour);
        });
    }

}
