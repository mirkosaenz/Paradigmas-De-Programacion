(ns tp2.svg
  )

(defn reemplazar_coordenada [linea valores indice]
  (if (= indice (count valores))
    linea
    (case indice
      0 (reemplazar_coordenada (clojure.string/replace linea "x1_reemplazar" (nth valores indice)) valores (+ 1 indice))
      1 (reemplazar_coordenada (clojure.string/replace linea "y1_reemplazar" (nth valores indice)) valores (+ 1 indice))
      2 (reemplazar_coordenada (clojure.string/replace linea "x2_reemplazar" (nth valores indice)) valores (+ 1 indice))
      3 (reemplazar_coordenada (clojure.string/replace linea "y2_reemplazar" (nth valores indice)) valores (+ 1 indice))
      )
    )
  )

(defn linea-svg [x1 y1 x2 y2]
  ; Crea un string SVG de una linea
  (let [ linea "<line x1=\"x1_reemplazar\" y1=\"y1_reemplazar\" x2=\"x2_reemplazar\" y2=\"y2_reemplazar\" stroke-width=\"1\" stroke=\"black\" />"
        numeros (seq [x1 y1 x2 y2])
        valores (map (fn [n] (str n)) numeros)
        ]
    (reemplazar_coordenada linea valores 0)
    )
  )

(defn crear-linea-svg [coords]
  (let [ x1 (nth coords 0)
        y1 (nth coords 1)
        x2 (nth coords 2)
        y2 (nth coords 3)
        ]
    (linea-svg x1 y1 x2 y2)
    )
  )

(defn buscar-limites-coords [coord1 coord2]
  ; Encuentra los limites extremos del dibujo
  (let [ x1-1 (nth coord1 0)
        y1-1 (nth coord1 1)
        x2-1 (nth coord1 2)
        y2-1 (nth coord1 3)
        x1-2 (nth coord2 0)
        y1-2 (nth coord2 1)
        x2-2 (nth coord2 2)
        y2-2 (nth coord2 3)
        ]
    [(min x1-1 x2-1 x1-2 x2-2) (max y1-1 y2-1 y1-2 y2-2) (max x1-1 x2-1 x1-2 x2-2) (min y1-1 y2-1 y1-2 y2-2)]
    )
  )

(defn buscar-coords-marco [lista-coords]
  (reduce buscar-limites-coords lista-coords)
  )

(defn obtener-tag-ajustada [marco lista-coords]
  ; Ajusta el viewBox del SVG al dibujo generado
  (let [ coords-marco (buscar-coords-marco lista-coords)
        x-min (nth coords-marco 0)
        y-max (nth coords-marco 1)
        x-max (nth coords-marco 2)
        y-min (nth coords-marco 3)
        ]
    (-> marco
        (clojure.string/replace ,,, "XMIN" (str x-min))
        (clojure.string/replace ,,, "YMIN" (str y-min))
        (clojure.string/replace ,,, "ANCHO" (str (- x-max x-min)))
        (clojure.string/replace ,,, "ALTO" (str (- y-max y-min)))
        )
  )
)

(defn generar-archivo [lista-coords]
  (let [
        etiqueta_abrir "<svg viewBox=\"XMIN YMIN ANCHO ALTO\" xmlns=\"http://www.w3.org/2000/svg\">"
        etiqueta_cerrar "</svg>"
        ]
    (clojure.string/join "\n"
                         (concat [(obtener-tag-ajustada etiqueta_abrir lista-coords)]
                                 (map (fn [linea] (crear-linea-svg linea)) lista-coords)
                                 [etiqueta_cerrar]
                                 )
                         )
    )
  )