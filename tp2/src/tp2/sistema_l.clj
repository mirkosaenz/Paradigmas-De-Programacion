(ns tp2.sistema-l)

(defn -obtener-reemplazo [caracter reglas indice]
  ; Busca la regla correspondiente a un caracter (indicado por el indice), si no existe devuelve el caracter original
  (if (= indice (count reglas))
    caracter
    (let [
          regla (seq (.split (str (nth reglas indice)) " "))
          antecesor (first regla)
          predecesor (last regla)
          ]
      (if (= caracter antecesor)
        predecesor
        (-obtener-reemplazo caracter reglas (+ 1 indice))
        )
      )
    )
  )

(defn obtener-reemplazo [caracter reglas]
  (-obtener-reemplazo caracter reglas 0)
  )

(defn -iteraciones [lista_axiomas reglas n actual]
  ; Aplica las reglas de transformacion N veces de forma recursiva
  (if (= n actual)
    lista_axiomas
    (let [
          proximo_estado (map (fn [caracter] (obtener-reemplazo caracter reglas)) lista_axiomas)
          proximo_estado_str (clojure.string/join proximo_estado)
          proximo_estado_lista (seq (.split proximo_estado_str ""))
          ]
      (-iteraciones proximo_estado_lista reglas n (+ 1 actual))
      )
    )
  )

(defn iteraciones [lista_axiomas reglas n]
  (-iteraciones lista_axiomas reglas n 0)
  )

