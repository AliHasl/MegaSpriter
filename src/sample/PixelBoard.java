package sample;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PixelBoard {

    private final int DEFAULT_X = 10;
    private final int DEFAULT_Y = 10;
    private int currentPalette;

    private GridPane gridPane;
    private Group pixelButtonGroup;
    private Main parent;

    public GridPane getGridPane() {
        return gridPane;
    }


    PixelBoard(Main owner){
        parent = owner;
        currentPalette = 0;
        initialiseBoard(DEFAULT_X, DEFAULT_Y);
    }

    PixelBoard(Main owner, int x, int y){
        parent = owner;
        currentPalette = 0;
        initialiseBoard(x, y);
    }

    private void initialiseBoard(int x, int y){
        gridPane = new GridPane();
        pixelButtonGroup = new Group();

        for(int i = 0; i < x; i++)
        {
            for(int z = 0; z < y; z++) {

                PixelButton pixelbutton = new PixelButton(this);
                pixelButtonGroup.getChildren().add(pixelbutton);
                gridPane.add(pixelbutton, i, z);


            }
        }
    }

    void returnOctalText(){
        StringBuilder returnString = new StringBuilder();

        for (Node n:gridPane.getChildren()
             ) {
            if(n instanceof PixelButton){
                if(((PixelButton) n).getPaintedColour() < 8){
                    returnString.append(String.format("$0%s ",  Integer.toOctalString(((PixelButton) n).getPaintedColour())));
                }
                else{
                    returnString.append(String.format("$%.2s ",  Integer.toOctalString(((PixelButton) n).getPaintedColour())));
                }

            }

        }

        parent.getOctalOutput().setText(returnString.toString());

    }

    public void updatePalette(int palette){
        currentPalette = palette;
    }


}
