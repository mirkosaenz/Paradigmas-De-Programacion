package app.view;

import javafx.scene.image.Image;

public class CuadradoTpView extends ElementoView{
    public CuadradoTpView(Image imagen, int tamanioCelda){
        super(imagen, tamanioCelda);
        super.offsetX = (118*7)-30;
    }
}
