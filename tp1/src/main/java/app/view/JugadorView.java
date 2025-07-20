package app.view;

import javafx.scene.image.Image;

public class JugadorView extends ElementoView{
    public JugadorView(Image imagen, int tamanioCelda){
        super(imagen, tamanioCelda);
        super.offsetX = -6;
    }
}
