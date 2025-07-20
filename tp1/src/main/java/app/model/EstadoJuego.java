package app.model;

import java.util.List;

public class EstadoJuego {
    private int largoTablero;
    public boolean juegoTermino;
    public boolean jugadorSeMovio;
    public boolean pasoDeNivel;
    public boolean enTpSeguro;
    public int cantChupetines;
    public int cantTpsSeguros;
    private String[][] matriz;

    public EstadoJuego(int largoTablero, Tablero tablero){
        // Creo matriz de largoTablero * largoTablero.
        this.matriz = new String[largoTablero][largoTablero];

        for(int i = 0; i < largoTablero; i++){
            for(int j = 0; j < largoTablero; j++){
                this.matriz[i][j] = "CLD";
            }
        }

        this.largoTablero = largoTablero;
        this.pasoDeNivel=false;
        this.cantChupetines = 4;
    }

    public void setJugador(Jugador jugador){
        int posicionX = jugador.getPosicionX();
        int posicionY = jugador.getPosicionY();
        matriz[posicionX][posicionY] = "JUG";
    }

    public void setRobots(List<Robot> robots){
        for(Robot robot : robots){
            if(robot.getCantidadMovimientos() == 1){
                int posicionX = robot.getPosicionX();
                int posicionY = robot.getPosicionY();
                matriz[posicionX][posicionY] = "RB1";
            }else{
                int posicionX = robot.getPosicionX();
                int posicionY = robot.getPosicionY();
                matriz[posicionX][posicionY] = "RB2";
            }
        }
    }

    public void setChupetines(List<Chupetin> chupetines){
        for(Chupetin chupetin: chupetines){
            int posicionX = chupetin.getPosicionX();
            int posicionY = chupetin.getPosicionY();
            matriz[posicionX][posicionY] = "CPT";
        }
        if(chupetines.isEmpty()){
            this.pasoDeNivel = true;
        }
        this.cantChupetines = chupetines.size();
    }

    public void setFuegos(List<Fuego> fuegos){
        for(Fuego fuego: fuegos){
            int posicionX = fuego.getPosicionX();
            int posicionY = fuego.getPosicionY();
            matriz[posicionX][posicionY] = "FGO";
        }
    }

    public void setEnTpSeguro(boolean enTpSeguro){
        this.enTpSeguro = enTpSeguro;
    }

    public void setTpSeguro(PunteroTpSeguro puntero){
        if(enTpSeguro){
            int posicionX = puntero.getPosicionX();
            int posicionY = puntero.getPosicionY();
            matriz[posicionX][posicionY] = "TPS";
        }
    }

    public void setCantidadTps(int cantidadTpsSeguros){
        this.cantTpsSeguros = cantidadTpsSeguros;
    }

    public void setCeldas(List<Celda> celdas){
        for(Celda celda: celdas){
            int posicionX = celda.getPosicionX();
            int posicionY = celda.getPosicionY();
            matriz[posicionX][posicionY] = "CDA";
        }
    }

    public void setJugadorSeMovio(boolean jugadorSeMovio) {
        this.jugadorSeMovio = jugadorSeMovio;
    }

    public void setJuegoTermino(boolean juegoTermino) {
        this.juegoTermino = juegoTermino;
    }

    public String[][] obtenerRepresentacion(){
        String[][] matrizString = new String[largoTablero][largoTablero];

        for(int i = 0; i < this.largoTablero; i++){
            for(int j = 0; j < this.largoTablero; j++){
                matrizString[i][j] = this.matriz[i][j];
            }
        }

        return matrizString;
    }

    public void reiniciarEstado(){
        this.matriz = new String[largoTablero][largoTablero];
        for(int i = 0; i < largoTablero; i++){
            for(int j = 0; j < largoTablero; j++){
                this.matriz[i][j] = "CLD";
            }
        }

    }
}
