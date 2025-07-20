package app.model;

public class Fuego extends Celda {

    public Fuego(int x , int y, Tablero tablero){
        super(x,y, tablero);
    }

    @Override
    public String toString() {
        return "FGO";
    }
}
