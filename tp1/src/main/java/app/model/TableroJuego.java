package app.model;

import java.util.*;


public class TableroJuego implements Tablero{
    private final double PORCENTAJE_ROBOTS = 0.7;
    private int tamanio;
    private Jugador jugador;
    private List<Robot> robots;
    private List<Chupetin> chupetines;
    private List<Fuego> incendiadas;
    private EstadoJuego estadoActualTablero;
    private boolean enTpSeguro;
    private PunteroTpSeguro tpSeguro;
    private int tpsSegurosRestantes;

    public TableroJuego(int tamanio, int cantRobots , int cantChupetines, int cantTpsSeguros){

        //Tamaño
        this.tamanio = tamanio;

        //Mantengo un Set de las celdas disponibles para constructor
        HashSet<ArrayList<Integer>> posicionesValidas = this.generadorPosicionesDisponibles();


        //Jugador
        this.ubicarJugador(posicionesValidas);

        //Robots
        this.robots= ubicarRobots(posicionesValidas,cantRobots);

        //Chupetines
        this.chupetines= ubicarChupetines(posicionesValidas,cantChupetines);

        //Tp seguro
        this.enTpSeguro = false;
        this.tpsSegurosRestantes = cantTpsSeguros;

        //Incendios
        List<Fuego> incendiadas = new ArrayList<Fuego>();
        this.incendiadas = incendiadas;
        this.estadoActualTablero = new EstadoJuego(this.tamanio,this);

        this.estadoActualTablero.setJuegoTermino(false);
        this.estadoActualTablero.setJugadorSeMovio(true);
        this.estadoActualTablero.reiniciarEstado();
        this.estadoActualTablero.setRobots(this.robots);
        this.estadoActualTablero.setChupetines(this.chupetines);
        this.estadoActualTablero.setCantidadTps(this.tpsSegurosRestantes);
        this.estadoActualTablero.setFuegos(this.incendiadas);
        this.estadoActualTablero.setJugador(this.jugador);
    }

    //Metodos del constructor

    //Generar posiciones disponibles del tablero
    private HashSet<ArrayList<Integer>> generadorPosicionesDisponibles() {
        HashSet<ArrayList<Integer>> posicionesValidas = new HashSet<ArrayList<Integer>>();

        // Creo la matriz de todas las posiciones validas
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                ArrayList<Integer> posicion = new ArrayList<Integer>();
                posicion.add(i);
                posicion.add(j);
                posicionesValidas.add(posicion);
            }
        }
        return posicionesValidas;
    }

    //Ubico en una posicion random al jugador y elimino esa celda de las disponibles
    private void ubicarJugador( HashSet<ArrayList<Integer>> posicionesValidas){
        this.jugador = new Jugador(this.tamanio/2,this.tamanio/2,this);

        ArrayList<Integer> posicionJugador = new ArrayList<Integer>();
        posicionJugador.add(jugador.getPosicionX());
        posicionJugador.add(jugador.getPosicionY());
        posicionesValidas.remove(posicionJugador);
    }


    //Ubico en una posicion random a los robots y elimino celdas de las disponibles
    private List<Robot> ubicarRobots(HashSet<ArrayList<Integer>> posicionesValidas, int cantRobots){
        //Genero ubicaciones randoms para robots
        HashSet<ArrayList<Integer>> posicionesRobot = new HashSet<ArrayList<Integer>>();

        for (int i = cantRobots; i>0;i--){
            this.hallarPosicionesRandom(posicionesValidas,posicionesRobot,false);
        }

        //ubico a los robots en lugares randoms correspondientes
        int cantidadLentos = (int)(cantRobots * PORCENTAJE_ROBOTS);
        List<Robot> robots = new ArrayList<Robot>();
        for (ArrayList<Integer> celda : posicionesRobot){
            if (cantidadLentos>0){
                Robot robot = new RobotLento(celda.get(0),celda.get(1),this);
                robots.add(robot);
                cantidadLentos--;
            }else{
                Robot robot = new RobotRapido(celda.get(0),celda.get(1),this);
                robots.add(robot);
            }
        }
        return robots;

    }

    //Ubico en una posicion random a los chupetines y elimino celdas de las disponibles
    private List<Chupetin> ubicarChupetines(HashSet<ArrayList<Integer>> posicionesValidas, int cantChupetines){
        HashSet<ArrayList<Integer>> posicionesChupetines = new HashSet<ArrayList<Integer>>();
        HashSet<ArrayList<Integer>> posicionesValidasChupetines = new HashSet<>(posicionesValidas);
        for (int i = cantChupetines; i>0;i--){
            this.hallarPosicionesRandom(posicionesValidasChupetines,posicionesChupetines,true);
        }
        List<Chupetin> chupetines = new ArrayList<Chupetin>();
        for (ArrayList<Integer> celda : posicionesChupetines){
            Chupetin chupetin = new Chupetin(celda.get(0),celda.get(1),this);
            posicionesValidas.remove(celda);
            chupetines.add(chupetin);
        }
        return chupetines;
    }

    //Genera una posicion random y la quita de las disponibles
    private void hallarPosicionesRandom(HashSet<ArrayList<Integer>> posicionesValidas,HashSet<ArrayList<Integer>> posicionesAcumuladas, boolean noATresDeDistancia){

        ArrayList<ArrayList<Integer>> listaCeldasDisponibles  = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> celda : posicionesValidas){
            listaCeldasDisponibles.add(celda);
        }
        int posicionCeldaRandom = (int)(Math.random()*( posicionesValidas.size()));

        ArrayList<Integer> celdaRandom = listaCeldasDisponibles.get(posicionCeldaRandom);

        if(noATresDeDistancia){
            int posicionX = celdaRandom.get(0);
            int posicionY = celdaRandom.get(1);
            for(int posX = posicionX-3; posX < posicionX+4; posX++){
                if(posX<0){
                    continue;
                }
                for(int posY = posicionY-3; posY < posicionY+4; posY++){
                    if(posY<0){
                        continue;
                    }
                    if(posX == posicionX && posY == posicionY){
                        continue;
                    }
                    ArrayList<Integer> adyacente = new ArrayList<Integer>();
                    adyacente.add(posX);
                    adyacente.add(posY);
                    posicionesValidas.remove(adyacente);
                }
            }
        }
        posicionesAcumuladas.add(celdaRandom);
        posicionesValidas.remove(celdaRandom);
    }



    // Metodos de la interfaz

    //Devuelve la cantidad de celdas
    public int getTamanio(){
        return tamanio;
    }

    //Devuelve la lista de robots
    public List<Robot> getRobots() {
        List<Robot> copiaRobots = new ArrayList<Robot>(robots);
        return copiaRobots;
    }

    public List<Chupetin> getChupetines(){
        return new ArrayList<Chupetin>(this.chupetines);
    }

    public EstadoJuego getEstadoActualTablero(){
        return this.estadoActualTablero;
    }

    //Logica del tablero

    //LogicaTablero para movimientos fijos del jugador, devuelve una matriz con el estado en el que quedo el juego
    //EstadoJuego -> bool(se murio), matriz, paso_de_nivel
    public EstadoJuego ActualizarTablero(int lado){
        if (this.enTpSeguro) {
            this.movimientoJugador(this.tpSeguro, lado);
            this.actualizarEstadoJuego();

            return this.estadoActualTablero;
        }
        //movimiento jugador
        boolean movimientoCorrecto = this.movimientoJugador(this.jugador, lado);
        if (!movimientoCorrecto){
            this.estadoActualTablero.setJugadorSeMovio(false);
            return this.estadoActualTablero;

        }
        this.logicaTablero();
        return this.estadoActualTablero;
    }

    //LogicaTablero para movimientos aleatorios del jugador
    //EstadoJuego -> bool(se murio), matriz, paso_de_nivel
    public EstadoJuego ActualizarTablero() {
        //movimiento jugador
        jugador.Teletransportarse();
        this.logicaTablero();
        return this.estadoActualTablero;

    }

    public EstadoJuego TeletransporteSeguro(){
        if(this.tpsSegurosRestantes == 0){
            return this.estadoActualTablero;
        }
        this.tpsSegurosRestantes -= 1;
        this.enTpSeguro = true;
        this.tpSeguro = new PunteroTpSeguro(this.jugador.getPosicionX(), this.jugador.getPosicionY(), this);
        this.actualizarEstadoJuego();
        return this.estadoActualTablero;
    }

    public EstadoJuego TerminarTeletransporteSeguro(){
        this.enTpSeguro = false;
        this.jugador.Moverse(tpSeguro.getPosicionX(), tpSeguro.getPosicionY());
        this.logicaTablero();
        return this.estadoActualTablero;
    }

    //resto de la logica del tablero
    private void logicaTablero(){
        //movimiento robots
        this.movimientoRobots();
        this.actualizarEstadoJuego();
    }

    public void actualizarEstadoJuego(){
        //chequeos
        boolean sigueVivo = this.chequeoJugador();
        this.chequeoRobots();

        //armado del EstadoJuego
        this.estadoActualTablero.setJuegoTermino(!sigueVivo);
        this.estadoActualTablero.setJugadorSeMovio(true);
        this.estadoActualTablero.reiniciarEstado();
        this.estadoActualTablero.setRobots(this.robots);
        this.estadoActualTablero.setChupetines(this.chupetines);
        this.estadoActualTablero.setJugador(this.jugador);
        this.estadoActualTablero.setFuegos(this.incendiadas);
        this.estadoActualTablero.setEnTpSeguro(this.enTpSeguro);
        this.estadoActualTablero.setTpSeguro(this.tpSeguro);
        this.estadoActualTablero.setCantidadTps(this.tpsSegurosRestantes);
    }

    //Movimientos

    //maneja los movimientos fijos del jugador
    private boolean movimientoJugador(CosaQueSeMueve elemento, int lado){
        //determina para que lado debe hacer el movimiento

        if(lado == 0){ //0 = ARRIBA
            if(elemento.getPosicionX()==0){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()-1, elemento.getPosicionY());

        }else if(lado == 1){ //1 = ABAJO
            if(elemento.getPosicionX()==tamanio-1){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()+1, elemento.getPosicionY());

        }else if(lado == 2){//2 = DERECHA
            if(elemento.getPosicionY()==tamanio-1){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX(), elemento.getPosicionY()+1);

        }else if(lado == 3){//3 = IZQUIERDA
            if(elemento.getPosicionY()==0){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX(), elemento.getPosicionY()-1);

        }else if(lado == 4){//4 = DIAGONAL SUPERIOR IZQUIERDA
            if(elemento.getPosicionY()==0 || elemento.getPosicionX()==0 ) {
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()-1, elemento.getPosicionY()-1);

        }else if(lado == 5){//5 = DIAGONAL SUPERIOR DERECHA
            if(elemento.getPosicionX()==0 || elemento.getPosicionY()==tamanio-1 ){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()-1, elemento.getPosicionY()+1);

        }else if(lado == 6){//6 = DIAGONAL INFERIOR IZQUIERDA
            if(elemento.getPosicionX()==tamanio-1 || elemento.getPosicionY()==0 ){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()+1, elemento.getPosicionY()-1);

        }else {//7 = DIAGONAL INFERIOR DERECHA
            if(elemento.getPosicionX()==tamanio-1 || elemento.getPosicionY()==tamanio-1){
                return false;
            }
            elemento.Moverse(elemento.getPosicionX()+1, elemento.getPosicionY()+1);
        }
        if(this.robotMatoJugador()){
            return false;
        }

        return true;
    }


    //maneja los movimientos de los robots
    private void movimientoRobots(){
        for(Robot robot : robots){ // muevo robots
            robot.Moverse(jugador.getPosicionX(),jugador.getPosicionY());
        }
    }

    //Chequeos

    // chequea que cambios hubo en el tablero dependiendo nueva posicion del jugador
    // Devuelve un booleano indicando si el jugador sigue vivo.
    private boolean chequeoJugador() {
        this.chequeoChupetinesJugador();
        boolean chequeo1 = this.incendioMatoJugador();
        boolean chequeo2 = this.robotMatoJugador();
        if (chequeo1 || chequeo2){
            return false;
        }
        return true;
    }


    //chequea que cambios hubo en el tablero dependiendo movimientos de robots
    private void chequeoRobots () {
        this.chequeoIncendiosRobots();
        this.chequeoChoquesRobots();
        return;
    }


    //chequea si jugador cayo arriba de algun chupetin
    private void chequeoChupetinesJugador(){
        // si el jugador cayo en algun chupetin lo saco de la lista, eventualmente si era el ultimo, cuando le pase el tablero al juego vera que ya no hay chupetines y pasaremos al siguiente nivel
        for (Chupetin chupetin : chupetines) {
            if (chupetin.getPosicionX() == jugador.getPosicionX() && chupetin.getPosicionY() == jugador.getPosicionY()) {
                chupetines.remove(chupetin);
                return;
            }
        }
    }

    //chequea si el jugador cayo arriba de un fuego
    private boolean incendioMatoJugador(){
        // si el jugador cayo en algun incendio -> GAME OVER (perdimos)
        for (Fuego fuego : incendiadas) {
            if (fuego.getPosicionX() == jugador.getPosicionX() && fuego.getPosicionY() == jugador.getPosicionY()) {
                return true;
            }
        }
        return false;
    }

    //chequea si al jugador lo atrapo un robot
    private boolean robotMatoJugador(){
        //si lo atrapo un robot(estan en misma celda) -> GAME OVER
        for (Robot robot : robots){
            if (robot.getPosicionX() == jugador.getPosicionX() && robot.getPosicionY() == jugador.getPosicionY()) {
                return true;
            }
        }
        return false;
    }

    //chequea y elimina a los robots que cayeron en celdas incendiadas
    private void chequeoIncendiosRobots(){
        ArrayList<Robot> robotsABorrar = new ArrayList<Robot>();
        for (Robot robot : robots){
            for (Fuego fuego : incendiadas){
                if (robot.getPosicionX() == fuego.getPosicionX() && robot.getPosicionY() == fuego.getPosicionY() ){
                    robotsABorrar.add(robot);
                }
            }
        }
        for(Robot robot: robotsABorrar){
            robots.remove(robot);
        }
    }

    //chequea elimina y pone celda incendiada en caso de choques entre dos o mas robots
    private void chequeoChoquesRobots(){
        //diccionario de tipo [x,y]=[Robots]
        HashMap<ArrayList<Integer>,ArrayList<Robot>> diccRobots = new HashMap<ArrayList<Integer>,ArrayList<Robot>>();
        for (Robot robot : robots) {
            ArrayList<Integer> celdaRobotActual = new ArrayList<Integer>();
            celdaRobotActual.add(robot.getPosicionX());
            celdaRobotActual.add(robot.getPosicionY());
            if (!diccRobots.containsKey(celdaRobotActual)){
                ArrayList<Robot> listaRobots = new ArrayList<Robot>();
                listaRobots.add(robot);
                diccRobots.put(celdaRobotActual,listaRobots);
            }else{
                ArrayList<Robot> listaRobots = diccRobots.get(celdaRobotActual);
                listaRobots.add(robot);
                diccRobots.put(celdaRobotActual,listaRobots);
            }
        }
        //si en una celda mas de un robot añado a celdas incendiadas y se eliminan robots
        for (ArrayList<Robot> celda : diccRobots.values()) {
            if (celda.size() > 1) {
                boolean primera = true;
                for (Robot robot : celda) {
                    if (primera) {
                        Fuego nuevoIncendio = new Fuego(robot.getPosicionX(), robot.getPosicionY(),this);
                        incendiadas.add(nuevoIncendio);
                        primera = false;
                    }
                    robots.remove(robot);
                }
            }
        }
    }

}
