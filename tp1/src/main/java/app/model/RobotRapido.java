package app.model;

public class RobotRapido extends Robot {
    private int cantidad_desplazamiento;

    public RobotRapido(int x , int y, Tablero tablero) {
        super(x , y, tablero);
        super.cantidad_desplazamiento = 2;
    }

    @Override
    public String toString() {
        return "RB2";
    }
}