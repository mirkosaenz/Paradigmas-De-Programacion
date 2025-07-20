package app.view;

import javafx.scene.image.Image;

public class ChupetinView extends ElementoView{
    public ChupetinView(Image imagen, int tamanioCelda){
        super(imagen, tamanioCelda);
        super.offsetX = (118*2)-10;
    }
}
