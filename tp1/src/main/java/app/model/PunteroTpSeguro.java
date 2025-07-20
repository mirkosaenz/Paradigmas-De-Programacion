package app.model;

public class PunteroTpSeguro extends Celda implements CosaQueSeMueve{
    public PunteroTpSeguro(int x, int y, Tablero tablero){
        super(x, y, tablero);
    }

    @Override
    public void Moverse(int x, int y) {
        super.posicionX = x;
        super.posicionY = y;
    }
}
