package app.model;

public class JuegoModel {
    private TableroJuego tablero;
    private int nivel;
    private int tamanio;
    private final int CANTIDAD_CHUPETINES = 4;
    private final int CANTIDAD_TPS_SEGUROS = 1;
    private boolean teletransporteEnCurso;


    public JuegoModel(){
        this.nivel = 1;
    }

    public EstadoJuego iniciarJuego(int tamanio){
        this.tamanio = tamanio;
        int cantidadRobots = this.obtenerRobotsNivel(this.nivel);
        this.tablero = new TableroJuego(tamanio, cantidadRobots, CANTIDAD_CHUPETINES, CANTIDAD_TPS_SEGUROS);
        return this.tablero.getEstadoActualTablero();
    }

    public void PasarDeNivel(){
        EstadoJuego estado = this.tablero.getEstadoActualTablero();
        this.nivel += 1;
        int cantidadRobots = this.obtenerRobotsNivel(this.nivel);
        int cantidadTpsSeguros = CANTIDAD_TPS_SEGUROS + estado.cantTpsSeguros;
        this.tablero = new TableroJuego(this.tamanio, cantidadRobots, CANTIDAD_CHUPETINES, cantidadTpsSeguros);
    }

    public EstadoJuego HacerMovimiento(int lado){
        EstadoJuego estado = this.tablero.ActualizarTablero(lado);
        if(estado.pasoDeNivel){
            this.PasarDeNivel();
            return this.obtenerRepresentacion();
        }
        return estado;
    }

    public EstadoJuego HacerMovimientoAleatorio(){
        EstadoJuego estado = this.tablero.ActualizarTablero();
        if(estado.pasoDeNivel){
            this.PasarDeNivel();
            return this.obtenerRepresentacion();
        }
        return estado;
    }

    public EstadoJuego TeletransportarseSeguro(){
        return this.tablero.TeletransporteSeguro();
    }

    public EstadoJuego TerminarTeletransporteSeguro(){
        EstadoJuego estado = this.tablero.TerminarTeletransporteSeguro();
        if(estado.pasoDeNivel){
            this.PasarDeNivel();
            return this.obtenerRepresentacion();
        }
        return estado;
    }

    private int obtenerRobotsNivel(int nivel){
        return nivel * 3;
    }

    public EstadoJuego obtenerRepresentacion(){
        return this.tablero.getEstadoActualTablero();
    }
}