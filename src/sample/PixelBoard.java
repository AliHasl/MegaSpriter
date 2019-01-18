package sample;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class PixelBoard extends GridPane {

    private final int DEFAULT_X = 8;
    private final int DEFAULT_Y = 8;
    private int currentPalette;

    private GridPane gridPane;
    private Group pixelButtonGroup;
    private Main parent;

    public GridPane getGridPane() {
        return gridPane;
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
        gridPane = new GridPane();
        pixelButtonGroup = new Group();

        for(int z = 0; z < y; z++)
        {
            for(int i = 0; i < x; i++)
            {
                PixelButton pixelbutton = new PixelButton(this);
                pixelButtonGroup.getChildren().add(pixelbutton);
                gridPane.add(pixelbutton, i, z);
            }
        }
    }

    void zoom(Double mouseWheel) {
        for (Node n : gridPane.getChildren()
                ) {
            if (n instanceof PixelButton) {
                ((PixelButton) n).setPrefSize(((PixelButton) n).getPrefWidth() + mouseWheel * 0.1,
                        ((PixelButton) n).getPrefHeight() + mouseWheel * 0.1);

            }
        }
    }

    void returnHexText(){
        StringBuilder returnString = new StringBuilder();

        boolean firstPart = true;
        int linePart = 0;
        int newSquare = 0;


        for (Node n:gridPane.getChildren()
             ) {
                if(n instanceof PixelButton){
                    if(linePart == 7){
                        returnString.append(String.format("%s\n",Integer.toHexString(((PixelButton) n)
                                .getPaintedColour())).toUpperCase());
                        firstPart = true;
                        linePart = 0;
                        newSquare++;
                        if(newSquare == 8 && gridPane.getChildren().size() > 64){
                            newSquare = 0;
                            returnString.append("\n");
                        }

                    }
                    else if(linePart == 0){
                        returnString.append(String.format("DC.B    $%s",Integer.toHexString(((PixelButton) n)
                                .getPaintedColour())).toUpperCase());
                        firstPart = false;
                        linePart++;
                    }
                    else if(!firstPart){
                        returnString.append(String.format("%s, ",Integer.toHexString(((PixelButton) n)
                                .getPaintedColour())).toUpperCase());
                        firstPart = true;
                        linePart++;
                    }
                    else
                    {
                        returnString.append(String.format("$%s",Integer.toHexString(((PixelButton) n)
                                .getPaintedColour())).toUpperCase());
                        firstPart = false;
                        linePart++;
                    }


            }

        }

        parent.getHexOutput().setText(returnString.toString());

    }

    public void updatePalette(int palette){
        currentPalette = palette;
    }


}
