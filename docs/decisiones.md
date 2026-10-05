# Decisiones de diseño — Semana 3

## 1. Punto de entrada

El proyecto mantiene un único punto de entrada:

```text
IngestaSensores.main()
```

No se crean aplicaciones independientes por semana.

`BancoDePruebas` es una clase auxiliar y no contiene `main`.

## 2. Búsqueda por timestamp

Se utilizan dos estrategias:

- Búsqueda lineal: no requiere ordenamiento.
- Búsqueda binaria: requiere que el arreglo esté ordenado por timestamp.

Los datos sintéticos de `GeneradorDatos` se generan en orden cronológico, por lo que la búsqueda binaria por timestamp cumple su precondición.

## 3. Búsqueda por PM2.5

No se asume que los datos estén ordenados por PM2.5.

Por tanto, la búsqueda binaria por PM2.5 se conserva como experimento para demostrar el efecto de una precondición incumplida.

## 4. Comparación de String

Los identificadores de estación se comparan mediante:

```java
equals()
```

y no mediante:

```java
==
```

porque se necesita comparar contenido.

## 5. Medición

La comparación principal entre algoritmos utiliza el número de comparaciones.

El tiempo en milisegundos se conserva como evidencia experimental, pero no es la única medida utilizada.

## 6. Evolución del proyecto

La Semana 3 agrega una nueva capacidad a la misma plataforma:

```text
Sensores
   ↓
Ingesta
   ↓
Repositorio
   ↓
Búsqueda
   ↓
Medición de eficiencia
```

La Semana 4 podrá extender esta misma arquitectura para estudiar ordenamiento.


## 7. Elección del pivote en QuickSort — DEC-04

En la primera prueba de QuickSort se utilizó el primer elemento del arreglo como pivote.

Con 50.000 datos desordenados el algoritmo funcionó correctamente, pero al utilizar datos ordenados se produjo un `StackOverflowError`. Esto ocurrió porque elegir siempre el primer elemento como pivote puede generar particiones muy desequilibradas y una recursión demasiado profunda.

Después se modificó el algoritmo para elegir el pivote de manera aleatoria.

Con esta modificación, QuickSort pudo trabajar tanto con los datos desordenados como con los datos ordenados sin producir el error.

Por esta razón, se decide utilizar un pivote aleatorio para evitar depender de que los datos de entrada tengan una distribución específica.

## 8. Relación entre ordenamiento y búsqueda — DEC-05

La búsqueda binaria por timestamp solo se puede utilizar cuando las lecturas están ordenadas por timestamp.

En el experimento 5 se comprobó esta condición. Inicialmente, los datos estaban ordenados por timestamp y la búsqueda binaria encontró la lectura en la posición 5000 realizando 13 comparaciones.

Después se ordenaron las lecturas por PM2.5. Como consecuencia, el arreglo dejó de estar ordenado por timestamp. Al intentar nuevamente la búsqueda binaria por timestamp, no se encontró correctamente la lectura.

Para comprobar que la lectura seguía existiendo, se utilizó una búsqueda lineal, que la encontró en la posición 2099 realizando 2100 comparaciones.

Por lo tanto, se decide que antes de utilizar búsqueda binaria se debe verificar que los datos estén ordenados según el mismo criterio utilizado para realizar la búsqueda.
=======

