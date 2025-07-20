package app.controller;

import app.model.EstadoJuego;
import app.view.JuegoView;
import app.model.JuegoModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.application.Platform.exit;

public class JuegoController implements EventHandler<KeyEvent> {
    private JuegoView vista;
    private JuegoModel modelo;
    private int tamanio;
    private ListenerTamanio listener;

    public JuegoController(JuegoModel modelo,JuegoView vista){
        this.vista = vista;
        this.modelo = modelo;
    }

    public void jugar(){
        ListenerTamanio listener = new ListenerTamanio();
        ListenerJugar listenerJugar = new ListenerJugar(listener, this);
        this.mostrarPantalla(listener, listenerJugar);
        this.listener = listener;
    }

    private void mostrarPantalla(EventHandler<ActionEvent> listenerTamanio, EventHandler<ActionEvent> listenerJugar){
        this.vista.mostrarPantallaInicio(listenerTamanio, listenerJugar);
    }

    public void iniciarJuego(int tamanio){
        this.tamanio = tamanio;
        EstadoJuego estadoInicial = this.modelo.iniciarJuego(tamanio);
        String[][] matriz = estadoInicial.obtenerRepresentacion();
        Integer cantChupetines = estadoInicial.cantChupetines;
        Integer cantTpS = estadoInicial.cantTpsSeguros;
        this.vista.iniciarEscena(matriz,cantChupetines,cantTpS);
        this.vista.agregarSuscriberTeclaApretada(this); //todo se puede hacer el listener y mandarlo
    }

    @Override
    public void handle(KeyEvent evento){
        KeyCode codigo = evento.getCode();
        boolean juegoTermino = false;
        EstadoJuego estado = null;

        switch(codigo){

            //Movimientos fijos
            case W:
                estado = modelo.HacerMovimiento(0);
                break;
            case S:
                estado = modelo.HacerMovimiento(1);
                break;
            case D:
                estado = modelo.HacerMovimiento(2);
                break;
            case A:
                estado = modelo.HacerMovimiento(3);
                break;
            case Q:
                estado = modelo.HacerMovimiento(4);
                break;
            case E:
                estado = modelo.HacerMovimiento(5);
                break;
            case Z:
                estado = modelo.HacerMovimiento(6);
                break;
            case C:
                estado = modelo.HacerMovimiento(7);
                break;

            //Teletransportacion aleatoria
            case T:
                estado = modelo.HacerMovimientoAleatorio();
                break;

            //Teletransportacion segura
            case R:
                estado = modelo.TeletransportarseSeguro();
                break;
            case ENTER:
                estado = modelo.TerminarTeletransporteSeguro();
                break;
            default:
                break;
        }
        if(estado == null){
            return;
        }
        juegoTermino = estado.juegoTermino;

        if(juegoTermino){
            //todo video :p
            this.jugar();
        }

        this.vista.render(estado.obtenerRepresentacion(),estado.cantChupetines, estado.cantTpsSeguros);
    }
}

class ListenerTamanio implements EventHandler<ActionEvent>{
    private int tamanio;
    
    @Override
    public void handle(ActionEvent evento) {
        ChoiceBox<String> choice = (ChoiceBox<String>) evento.getSource();
        switch(choice.getValue()){
            case "15x15":
                this.tamanio = 15;
                break;
            case "20x20":
                this.tamanio = 20;
                break;
            case "25x25":
                this.tamanio = 25;
                break;
            case "30x30":
                this.tamanio = 30;
                break;
            case "35x35":
                this.tamanio = 35;
                break;
            case "40x40":
                this.tamanio = 40;
                break;
        }
    }

    public int getTamanio() {
        return tamanio;
    }
}

class ListenerJugar implements EventHandler<ActionEvent>{
    JuegoController controlador;
    ListenerTamanio listenerTamanio;

    public ListenerJugar(ListenerTamanio listener, JuegoController controlador){
        this.listenerTamanio = listener;
        this.controlador = controlador;
    }

    @Override
    public void handle(ActionEvent evento) {
        controlador.iniciarJuego(listenerTamanio.getTamanio());
    }
}