(ns tp2.tortuga)

(defn tortuga_adelante [tortuga n]
  ; Mueve la tortuga N unidades hacia adelante segun su angulo actual
  (let [ x-inicial (get tortuga :x)
        y-inicial (get tortuga :y)
        angulo (get tortuga :angulo)
        x-nuevo-suma (* n (Math/cos (Math/toRadians angulo)))
        y-nuevo-suma (* n (Math/sin (Math/toRadians angulo)))
        x-nuevo (+ x-inicial x-nuevo-suma)
        y-nuevo (+ y-inicial y-nuevo-suma)
        tortuga-nueva (assoc (assoc tortuga :x x-nuevo) :y y-nuevo)
        ]
      [tortuga-nueva [x-inicial y-inicial x-nuevo y-nuevo]]
    )
  )

(defn tortuga_derecha [tortuga alpha]
  ; Gira la tortuga a la derecha en el angulo especificado
  (assoc tortuga :angulo (rem (- (get tortuga :angulo) alpha) 360))
  )

(defn tortuga_izquierda [tortuga alpha]
  ; Gira la tortuga a la izquierda en el angulo especificado
  (assoc tortuga :angulo (rem (+ (get tortuga :angulo) alpha) 360))
  )

(defn pluma_arriba [tortuga]
  ;Levanta la pluma de la tortuga -> NO dibuja
  (assoc tortuga :pluma_abajo false)
  )

(defn pluma_abajo [tortuga]
  ; Baja la pluma de la tortuga -< dibuja
  (assoc tortuga :pluma_abajo true)
  )

(defn crear_tortuga []
  ; Crea una tortuga inicial en:
  ;                              - Coordenada inicial (0,0)
  ;                              - Apuntando a 0°
  ;                              - Con la pluma abajo -> dibuja
  {:x 0, :y 0, :angulo 0, :pluma_abajo true}
  )