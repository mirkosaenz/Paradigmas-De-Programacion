# Trabajo Práctico N°2 – Sistemas-L
## TB025 – Paradigmas de Programación
### Curso Essaya – 1° Cuatrimestre 2025
### Facultad de Ingeniería, Universidad de Buenos Aires (FIUBA)

---

## Consigna

Implementar un programa en **Clojure** que permita generar imágenes SVG mediante un algoritmo basado en sistemas-L, gráficos tortuga y un conjunto de reglas definidas en un archivo de entrada.

Consigna brindada por la catedra con especificaciones: [Link](https://algoritmos3ce.github.io/tps/tp2-2025c1/)

---

## Integrantes
- Melanie Belén García Lapegna. Padrón: 111848
- Mirko Uriel Sáenz Valiente. Padrón: 111960

## Ejecución
El programa permite crear imagenes en **formato SVG** a partir de [Sistemas-L](https://es.wikipedia.org/wiki/Sistema-L). El formato de entrada debe ser el siguiente:

```
<ángulo>
<axioma>
<predecesor1> <sucesor1>
<predecesor2> <sucesor2>
<predecesor3> <sucesor3>
...
```
Los argumentos que recibe el programa son los siguientes (en orden):
- Ruta del archivo que contiene el **Sistema-L** en el formato antes especificado.
- Cantidad de iteraciónes que se realizaran sobre el sistema.
- Ruta de salida esperada para la imagen resultante.

Teniendo esto, se pueden generar imagenes de la siguiente manera:
```
lein run archivo_entrada.sl n salida.svg
```