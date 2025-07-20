package app.model;

public class Chupetin extends Celda {
    public Chupetin(int x , int y,Tablero tablero){
        super(x,y,tablero);
    }

    @Override
    public String toString() {
        return "CHP";
    }
}
