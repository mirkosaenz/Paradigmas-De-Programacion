package app.model;

public class Celda {
    protected Tablero tablero;
    protected int posicionX;
    protected int posicionY;


    public Celda(int x,int y, Tablero tablero) {
        this.posicionX = x;
        this.posicionY = y;
        this.tablero = tablero;
    }

    public int getPosicionX() {
        return this.posicionX;
    }

    public int getPosicionY() {
        return this.posicionY;
    }

    @Override
    public String toString() {
        return "CLD";
    }
}
