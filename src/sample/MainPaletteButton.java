package sample;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainPaletteButton extends Button {


    private int mColourRef;
    private Color mColour;
    private Main parent;
    public int getmColourRef() {
        return mColourRef;
    }

    public String gettText() {
        return tText.getText();
    }

    private Text tText;
    private Label textLabel;

    public void setTVisible(Boolean t){
        textLabel.setVisible(t);
    }

    MainPaletteButton(Main owner, int colourRef){
        parent = owner;
        mColour = Color.PURPLE;
        mColourRef = colourRef;
        this.setMinSize(25,30);
        this.setPrefSize(25,30);

        tText = new Text("T");
        buttonActions();

        //this.setOnAction(mouseEvent->{parent.setSelectedColourText(mColour);
        //parent.updateColourSelection(mColourRef);
        //});
    }

    MainPaletteButton(Main owner, int colourRef, Color initialColour){
        parent = owner;
        mColour = Color.PURPLE;
        mColourRef = colourRef;
        mColour = initialColour;
        tText = new Text("T");
        this.setBackground(new Background(new BackgroundFill(mColour, CornerRadii.EMPTY, Insets.EMPTY)));
        tText.setFill(mColour.invert());
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
        try {
            tText.setFill(mColour.invert());
        }catch(NullPointerException nullPointerException){

        }
    }

}
