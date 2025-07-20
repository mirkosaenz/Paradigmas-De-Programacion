package app.model;

import java.util.ArrayList;
import java.util.List;

public class Jugador extends Celda implements CosaQueSeMueve {
    public Jugador(int x, int y, Tablero tablero){
        super(x, y, tablero);
    }

    public void Moverse(int x, int y) {
        super.posicionX = x;
        super.posicionY = y;
    }

    public void Teletransportarse(){
        int largoTablero = super.tablero.getTamanio();
        int posXRandom = (int)(Math.random()*(largoTablero));
        int posYRandom = (int)(Math.random()*(largoTablero));

        if(posXRandom == super.posicionX && posYRandom == super.posicionY){
            if(posXRandom > 0 && posYRandom < largoTablero-1){
                posXRandom += 1;
            } else{
                posXRandom -= 1;
            }
        }

        this.Moverse(posXRandom, posYRandom);
    }


    //todo cambiar de ArrayList a HashSet
    public void TeletransportarseSeguro(){
        int largoTablero = super.tablero.getTamanio();
        ArrayList<ArrayList<Integer>> posicionesValidas = new ArrayList<ArrayList<Integer>>();

        // Creo la matriz de todas las posiciones validas
        for(int i = 0; i < largoTablero; i++){
            for(int j = 0; j < largoTablero; j++){
                ArrayList<Integer> posicion = new ArrayList<Integer>();
                posicion.add(i);
                posicion.add(j);
                posicionesValidas.add(posicion);
            }
        }

        // Elimino los robots de las posiciones validas
        for(Robot robot : super.tablero.getRobots()){
           int posXRobot = robot.getPosicionX();
           int posYRobot = robot.getPosicionY();

            List<List<Integer>> posicionesABorrar = new ArrayList<List<Integer>>();


           for(ArrayList<Integer> posicion: posicionesValidas){
               if(posicion.get(0) == posXRobot && posicion.get(1) == posYRobot){
                  posicionesABorrar.add(posicion);
               }
           }
           for(List<Integer> posicion : posicionesABorrar){
               posicionesValidas.remove(posicion);
           }
        }

        // Obtengo una posicion aleatoria entre las posiciones validas
        int indicePosicionRandom = (int)(Math.random()*(posicionesValidas.size()));
        ArrayList<Integer> posicionRandom = posicionesValidas.get(indicePosicionRandom);
        this.Moverse(posicionRandom.get(0), posicionRandom.get(1));
    }

    @Override
    public String toString() {
        return "JUG";
    }
}

