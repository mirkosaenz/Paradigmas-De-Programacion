(ns tp2.core
  (:gen-class)
  (:use [tp2.tortuga])
  (:use [tp2.sistema-l])
  (:use [tp2.svg])
  (:use [tp2.procesamiento])
  )

(defn -main [archivo n salida]
  ; Funcion principal
  ; Recibe:
  ;   - archivo : nombre del archivo ".sl" con el sistema-L
  ;   - n : cantidad de iteraciones del sistema-L
  ;   - salida : nombre del archivo ".svg" a generar
  (let [
        argumentos (tp2.procesamiento/parse archivo)        ; Lee lineas del archivo ".sl"
        angulo (Float/parseFloat (nth argumentos 0))        ; Angulo definido por archivo
        axioma ( str(nth argumentos 1))                     ; Axioma inicial
        reglas (rest (rest argumentos))                     ; Reglas del sistema
        lista-axiomas (seq (.split axioma ""))              ; Lista de caracteres del axioma
        secuencia_final (tp2.sistema-l/iteraciones lista-axiomas reglas (Integer/parseInt n))
        lineas (tp2.procesamiento/procesar-secuencia-tokens secuencia_final angulo) ; Lineas para dibujas
        ]
    ; Escribe el archivo SVG con las lineas anteriormente generadas
    (spit salida (tp2.svg/generar-archivo lineas))
    )
  )