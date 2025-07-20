package app.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

public abstract class ElementoView {
    private Image imagen;
    private final double ANCHO_IMAGEN = 115;
    private final double ALTO_IMAGEN = 120;
    private int tamanioCelda;
    protected double offsetX;

    public ElementoView(Image imagen, int tamanioCelda){
        this.imagen = imagen;
        this.tamanioCelda = tamanioCelda;
    }

    public void setGrilla(GridPane grid, int i, int j){
        Canvas canvasImagen = new Canvas(this.tamanioCelda, this.tamanioCelda);
        GraphicsContext imagenCortada = canvasImagen.getGraphicsContext2D();

        // Calculo el escalado de la imagen para cada eje
        double factorEscalado = (tamanioCelda / this.ANCHO_IMAGEN) * 0.95;
        imagenCortada.scale(factorEscalado, factorEscalado);

        imagenCortada.drawImage(imagen, this.offsetX, 0, ANCHO_IMAGEN, ALTO_IMAGEN, 0,0,ANCHO_IMAGEN, ALTO_IMAGEN);
        grid.add(canvasImagen, i, j);
    }
}