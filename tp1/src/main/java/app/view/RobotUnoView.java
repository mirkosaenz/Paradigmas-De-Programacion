package app.view;

import javafx.scene.image.Image;

public class RobotUnoView extends ElementoView{
    public RobotUnoView(Image imagen, int tamanioCelda){
        super(imagen, tamanioCelda);
        super.offsetX = (115*6)-10;
    }
}
