package app.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Robot extends Celda implements CosaQueSeMueve{
    protected int cantidad_desplazamiento;
    private final int DIST_MAX = 9999;

    public Robot(int x , int y, Tablero tablero){
        super(x , y, tablero);
    }

    public void Moverse(int x, int y ) {
        Integer[] nuevaPosicion = this.calcularSiguientePosicion(x, y);
        super.posicionX = nuevaPosicion[0];
        super.posicionY = nuevaPosicion[1];
    }

    private Integer[] calcularSiguientePosicion(int x, int y){
        List<Integer[]> adyacentes = this.obtenerCeldasAdyacentes();
        int xMin = this.getPosicionX();
        int yMin = this.getPosicionY();
        double distanciaMin = DIST_MAX;

        for(Integer[] adyacente: adyacentes){
            if(!enRango(adyacente[0], adyacente[1])){
                continue;
            }

            boolean hayChupetin = false;
            for(Chupetin chupetin: super.tablero.getChupetines()){
                if(chupetin.getPosicionX() == adyacente[0] && chupetin.getPosicionY() == adyacente[1]){
                    hayChupetin = true;
                    break;
                }
            }
            if(hayChupetin){
                continue;
            }

            double distancia = this.calcularDistancia(adyacente[0], adyacente[1], x, y);

            if(distancia < distanciaMin){
                distanciaMin = distancia;
                xMin = adyacente[0];
                yMin = adyacente[1];
            }
        }

        Integer[] nuevaPosicion = new Integer[2];
        nuevaPosicion[0] = xMin;
        nuevaPosicion[1] = yMin;
        return nuevaPosicion;
    }

    private boolean enRango(int x, int y){
        int tamanioTablero = this.tablero.getTamanio();
        boolean cond1 = x >= 0 && y >= 0;
        boolean cond2 = x < tamanioTablero && y < tamanioTablero;
        return cond1 && cond2;
    }

    private List<Integer[]> obtenerCeldasAdyacentes(){
        ArrayList<Integer[]> adyacentes = new ArrayList<Integer[]>();

        int minimoX = this.getPosicionX()-cantidad_desplazamiento;
        int maximoX = this.getPosicionX()+cantidad_desplazamiento;
        int minimoY = this.getPosicionY()-cantidad_desplazamiento;
        int maximoY = this.getPosicionY()+cantidad_desplazamiento;

        for(int posX = minimoX; posX < maximoX+1; posX++){
            for(int posY = minimoY; posY < maximoY+1; posY++){
                if(posX == this.getPosicionX() && posY == this.getPosicionY()){
                    continue;
                }
                Integer[] adyacente = new Integer[2];
                adyacente[0] = posX;
                adyacente[1] = posY;
                adyacentes.add(adyacente);
            }
        }

        return adyacentes;
    }

    private double calcularDistancia(int x1, int y1, int x2, int y2){
        int xCuadrado = (x2-x1)*(x2-x1);
        int yCuadrado = (y2-y1)*(y2-y1);
        return Math.sqrt(xCuadrado+yCuadrado);
    }

    public int getCantidadMovimientos() {
        return cantidad_desplazamiento;
    }
}


