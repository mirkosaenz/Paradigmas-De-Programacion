package app.model;

public class RobotLento extends Robot {

    public RobotLento(int x , int y, Tablero tablero) {
        super(x , y, tablero);
        super.cantidad_desplazamiento = 1;
    }

    @Override
    public String toString() {
        return "RB1";
    }
}
