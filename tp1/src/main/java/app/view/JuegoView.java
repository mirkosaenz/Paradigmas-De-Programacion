package app.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


public class JuegoView {
    private Stage stage;
    private final Image sprite;
    private int cantidadFilas;
    private GridPane grilla;
    private Scene scene;
    private final int TAMANIO_PANTALLA = 800;
    private VBox columna;

    public JuegoView(Stage stage){
        this.stage = stage;

        // Abro la imagen del sprite y la guardo en el atributo
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("src/main/resources/app/view/sprite.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.sprite = new Image(inputStream);
    }

    public void iniciarEscena(String[][] matriz, int cantChupetines , int cantTpS){
        this.cantidadFilas = matriz.length;

        Parent padre = this.obtenerInterfaz(cantChupetines,cantTpS);
        Scene scene = new Scene(padre, TAMANIO_PANTALLA, TAMANIO_PANTALLA);

        this.render(matriz, cantChupetines, cantTpS);

        this.scene = scene;
        stage.setScene(scene);
        stage.setTitle("El grito de Tinky Winky");
        stage.setResizable(false);
        stage.show();
    }

    public void mostrarPantallaInicio(EventHandler<ActionEvent> listenerTamanio, EventHandler<ActionEvent> listenerJugar){
        VBox columna = new VBox();

        //hacer el fondo y asignarselo al VBox

        FileInputStream fondo;
        try {
            fondo = new FileInputStream("src/main/resources/app/view/fondoInicio.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image imagen = new Image(fondo);
        BackgroundImage backgroundImage = new BackgroundImage(imagen,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100.0, 100.0, true, true,true,true));
        //creas el objeto background
        Background background = new Background(backgroundImage);
        columna.setBackground(background);


        Scene pantallaInicio = new Scene(columna, TAMANIO_PANTALLA, TAMANIO_PANTALLA);

        //espacio para botones
        Region spacio= new Region();
        VBox.setVgrow(spacio, Priority.ALWAYS);

        //choiceBox tamanio
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("15x15", "20x20", "25x25", "30x30", "35x35", "40x40");
        choiceBox.setOnAction(listenerTamanio);
        Button boton = new Button();
        boton.setOnAction(listenerJugar);
        boton.setText("Jugar");
        boton.setStyle(
                "-fx-background-color: #2e2e2e;"+
                "-fx-text-fill: white;" +
                "-fx-border-color: #f5deb3;"+
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 4px;" +
                "-fx-background-radius: 4px;" +
                "-fx-font-size: 12px;"+
                "-fx-font-family: 'Press Start 2P';"
        );

        // los pongo en el centro
        VBox botones = new VBox(8); // para que no queden pegados
        botones.getChildren().addAll(choiceBox, boton);
        botones.setPadding(new Insets(0, 0, 20, -260)); // Margen inferior
        botones.setStyle("-fx-alignment: center;");


        columna.getChildren().addAll(spacio, botones);

        stage.setScene(pantallaInicio);
        stage.setTitle("El grito de Tinky Winky!");
        stage.setResizable(false);
        stage.show();
    }

    public void render(String[][] matriz, int cantChupetines, int cantTps){
        grilla.setGridLinesVisible(false);
        List<Node> listaAborrar= this.grilla.getChildren();
        listaAborrar.clear();
        grilla.setGridLinesVisible(true);
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){
                this.ponerElemento(matriz[i][j], i, j);
            }
        }

        this.columna.getChildren().clear();
        this.columna.getChildren().addAll(grilla, obtenerLabelChupetines(cantChupetines), obtenerTpsSeguros(cantTps));
    }

    private Parent obtenerInterfaz(int cantidadChupetines,int cantidadTpSeguros){
    //private Parent obtenerInterfaz(){
        // Creo el Pane vertical
        VBox columnaPane = new VBox();
        this.columna = columnaPane;

        //hacer el fondo y asignarselo al VBox

        FileInputStream fondo;
        try {
            fondo = new FileInputStream("src/main/resources/app/view/fondo.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image imagen = new Image(fondo);
        BackgroundImage backgroundImage = new BackgroundImage(imagen,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100.0, 100.0, true, true,true,true));
        //creas el objeto background
        Background background = new Background(backgroundImage);
        columnaPane.setBackground(background);

        // Creo la grilla donde iran los elementos
        GridPane grilla = new GridPane();
        grilla.setGridLinesVisible(true);
        this.grilla = grilla;

        for(int i = 0; i < this.cantidadFilas; i++){
            // Crear fila en el indice i
            RowConstraints fila = new RowConstraints();
            fila.setVgrow(Priority.SOMETIMES); //para agrandar/encoger pestaña
            fila.setMinHeight(10);
            fila.setPrefHeight(100);
            grilla.getRowConstraints().add(fila);

            // Crear columna en el indice i
            ColumnConstraints columna_actual = new ColumnConstraints();
            columna_actual.setHgrow(Priority.SOMETIMES);
            columna_actual.setMinWidth(10);
            columna_actual.setPrefWidth(100);
            grilla.getColumnConstraints().add(columna_actual);
        }

        // Pongo la grilla dentro del Pane vertical
        columnaPane.getChildren().add(grilla);

        // Carteles con cantidad de chupetines y tps
        columnaPane.getChildren().add(obtenerLabelChupetines(cantidadChupetines));
        columnaPane.getChildren().add(obtenerTpsSeguros(cantidadTpSeguros));

        return (Parent)columnaPane;
    }

    private void ponerElemento(String nombre, int i, int j){
        int tamanioCelda = TAMANIO_PANTALLA / cantidadFilas;
        switch(nombre){
            case "JUG":
                JugadorView jugadorView = new JugadorView(this.sprite, tamanioCelda);
                jugadorView.setGrilla(this.grilla, j, i);
                break;
            case "RB1":
                RobotUnoView robotUnoView = new RobotUnoView(this.sprite, tamanioCelda);
                robotUnoView.setGrilla(this.grilla, j, i);
                break;
            case "RB2":
                RobotDosView robotDosView = new RobotDosView(this.sprite, tamanioCelda);
                robotDosView.setGrilla(this.grilla, j, i);
                break;
            case "CPT":
                ChupetinView chupetinView = new ChupetinView(this.sprite, tamanioCelda);
                chupetinView.setGrilla(this.grilla, j, i);
                break;
            case "FGO":
                FuegoView fuegoView = new FuegoView(this.sprite, tamanioCelda);
                fuegoView.setGrilla(this.grilla, j, i);
                break;
            case "TPS":
                CuadradoTpView cuadrado = new CuadradoTpView(this.sprite, tamanioCelda);
                cuadrado.setGrilla(this.grilla, j, i);
                break;
        }
    }

    public Label obtenerLabelChupetines(int cantidadChupetines){
        Label chups = new Label("- Chupetines Restantes: " + cantidadChupetines);
        chups.setFont(Font.font("Monospaced", 18));
        chups.setTextFill(Color.WHITE);
        return chups;
    }

    public Label obtenerTpsSeguros(int cantidadTps){
        Label tps = new Label("- Teletransportaciones seguras disponibles: " + cantidadTps);
        tps.setFont(Font.font("Monospaced", 18));
        tps.setTextFill(Color.WHITE);
        return tps;
    }

    public void agregarSuscriberTeclaApretada(EventHandler<KeyEvent> listener){
        scene.setOnKeyPressed(listener);
    }
}
