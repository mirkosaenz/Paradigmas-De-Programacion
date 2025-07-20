package app.model;

import java.util.List;

public interface Tablero {
    public int getTamanio();
    public List<Robot> getRobots();
    public List<Chupetin> getChupetines();
}
