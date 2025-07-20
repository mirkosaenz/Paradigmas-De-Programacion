package app;

import app.controller.JuegoController;
import app.model.JuegoModel;
import app.view.JuegoView;
import javafx.application.Application;
import javafx.stage.Stage;

public class JuegoApp extends Application{
    public static void main(String[] args){
        Application.launch();
    }

    @Override
    public void start(Stage stage){
        JuegoModel juego = new JuegoModel();
        JuegoView vista = new JuegoView(stage);
        JuegoController controlador = new JuegoController(juego,vista);
        controlador.jugar();
    }
}
