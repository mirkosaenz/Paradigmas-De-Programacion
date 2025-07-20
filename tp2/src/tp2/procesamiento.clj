(ns tp2.procesamiento)

(defn parse [archivo]
  ; Lee el archivo pasado por parametro y devuelve una lista de lineas (sin parsear)
  (let [contenido (seq (.split (slurp archivo) "\n"))]
    contenido
    )
  )

(defn crear-linea [tortuga]
  ; Avanza la tortuga con pluma abajo (se hace con una distancia fija de 10 unidades)
  (tp2.tortuga/tortuga_adelante tortuga 10)
  )

(defn procesar-token [token tortuga angulo]
  ; Procesa tokens que no dibujan, actualizando el estado de la tortuga
  (cond
    (or (= token "f") (= token "g")) (tp2.tortuga/pluma_abajo (first (tp2.tortuga/tortuga_adelante (tp2.tortuga/pluma_arriba tortuga) 1)))
    (= token "+") (tp2.tortuga/tortuga_derecha tortuga angulo)
    (= token "-") (tp2.tortuga/tortuga_izquierda tortuga angulo)
    (= token "|") (tp2.tortuga/tortuga_derecha tortuga 180)
    :else tortuga
    )
  )

(defn actualizar-tortuga [tortuga-nueva pila]
  ; Reemplaza el tope de la pila con una tortuga-nueva
  (conj (pop pila) tortuga-nueva)
  )

(defn -procesar-secuencia-tokens [secuencia-final indice pila angulo]
  ; Recursivamente procesa cada token de la secuencia, devolviendo una lista de lineas SVG
  (if (= indice (count secuencia-final))
    []
    (let [ token (nth secuencia-final indice)
          tortuga (nth pila (- (count pila) 1))
          ]
      (cond
        (= token "[") (-procesar-secuencia-tokens secuencia-final (inc indice) (conj pila tortuga) angulo)
        (= token "]") (-procesar-secuencia-tokens secuencia-final (inc indice) (pop pila) angulo)
        (or (= token "F") (= token "G")) (let [resultado (crear-linea tortuga)
                                               tortuga_nueva (nth resultado 0)
                                               posiciones (nth resultado 1)
                                               x1 (nth posiciones 0)
                                               y1 (nth posiciones 1)
                                               x2 (nth posiciones 2)
                                               y2 (nth posiciones 3)
                                               resultado_sigs (lazy-seq (-procesar-secuencia-tokens secuencia-final (inc indice) (actualizar-tortuga tortuga_nueva pila) angulo))
                                               ]
                                           (concat [[x1 y1 x2 y2]] resultado_sigs)
                                           )
        :else (lazy-seq (-procesar-secuencia-tokens secuencia-final (inc indice) (actualizar-tortuga (procesar-token token tortuga angulo) pila) angulo))
        )
      )
    )
  )


(defn procesar-secuencia-tokens [secuencia-final angulo]
  (let [
        tortuga (tp2.tortuga/crear_tortuga )
        ]
    (-procesar-secuencia-tokens secuencia-final 0 [tortuga] angulo)
    )
  )