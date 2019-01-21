package sample;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class PixelBoard extends GridPane {

    private final int DEFAULT_X = 1;
    private final int DEFAULT_Y = 1;
    private int currentPalette;

    private GridPane spritePane, tilePane;
    private Group pixelButtonGroup;
    private Main parent;

    public GridPane getSpritePane() {
        return spritePane;
    }


    PixelBoard(Main owner){
        super();
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
        spritePane = new GridPane();
        pixelButtonGroup = new Group();

        for(int z = 0; z < y; z++)
        {
            for(int i = 0; i < x; i++)
            {
                tilePane = new GridPane();
                initialiseTile(tilePane);
                spritePane.add(tilePane, z, i);
            }
        }
    }

    private void initialiseTile(GridPane tile){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                PixelButton pixelButton = new PixelButton(this, parent);
                tile.add(pixelButton, y, x);
            }
        }
    }

    void zoom(Double mouseWheel) {
        for (Node n : spritePane.getChildren()) {

            if (n instanceof GridPane) {

                for(Node pixel : ((GridPane) n).getChildren()) {
                    ((PixelButton) pixel).setPrefSize(((PixelButton) pixel).getPrefWidth() + mouseWheel * 0.1,
                            ((PixelButton) pixel).getPrefHeight() + mouseWheel * 0.1);
                }

            }
        }
    }

    void returnHexText(){

        StringBuilder returnString = new StringBuilder();


        for (Node tile: spritePane.getChildren()) {
            if (tile instanceof GridPane) {
                boolean firstPart = true;
                int linePart = 0;
                StringBuilder tileString = new StringBuilder();
                for (Node pixel : ((GridPane) tile).getChildren()) {
                    if (pixel instanceof PixelButton) {

                        if (linePart == 7) {
                            tileString.append(String.format("%s\n", Integer.toHexString(((PixelButton) pixel)
                                    .getPaintedColour())).toUpperCase());
                            firstPart = true;
                            linePart = 0;

                        } else if (linePart == 0) {
                            tileString.append(String.format("DC.B    $%s", Integer.toHexString(((PixelButton) pixel)
                                    .getPaintedColour())).toUpperCase());
                            firstPart = false;
                            linePart++;
                        } else if (!firstPart) {
                            tileString.append(String.format("%s, ", Integer.toHexString(((PixelButton) pixel)
                                    .getPaintedColour())).toUpperCase());
                            firstPart = true;
                            linePart++;
                        } else {
                            tileString.append(String.format("$%s", Integer.toHexString(((PixelButton) pixel)
                                    .getPaintedColour())).toUpperCase());
                            firstPart = false;
                            linePart++;
                        }


                    }

                }
                tileString.append("\n");
                returnString.append(tileString.toString());
            }
        }

        parent.getHexOutput().setText(returnString.toString());

    }

    public void updatePalette(int palette){

        currentPalette = palette;



    }


}
