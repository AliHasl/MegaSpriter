package sample;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainPaletteButton extends Button {


    private int mColourRef;
    private Color mColour;
    private Main parent;
    public int getmColourRef() {
        return mColourRef;
    }

    MainPaletteButton(Main owner, int colourRef){
        parent = owner;
        mColour = Color.PURPLE;
        mColourRef = colourRef;
        buttonActions();

        //this.setOnAction(mouseEvent->{parent.setSelectedColour(mColour);
        //parent.updateColourSelection(mColourRef);
        //});
    }

    MainPaletteButton(Main owner, int colourRef, Color initialColour){
        parent = owner;
        mColour = Color.PURPLE;
        mColourRef = colourRef;
        mColour = initialColour;
        this.setBackground(new Background(new BackgroundFill(mColour, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonActions();
    }

    private void buttonActions(){
        this.setOnAction(mouseEvent-> {
            parent.setSelectedColour(mColour);
            parent.updateColourSelection(mColourRef);
        });
    }

    public void setMColour(Color newColor){
        mColour = newColor;
        this.setBackground(new Background(new BackgroundFill(mColour, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
