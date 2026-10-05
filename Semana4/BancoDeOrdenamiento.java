/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDeOrdenamiento - VERSION COMPLETA

   Cinco experimentos. Los vas descomentando por fases.
   Ejecuta:  java BancoDeOrdenamiento
   ============================================================ */

import java.util.Random;

public class BancoDeOrdenamiento {

  public static void ejecutarExperimentos() {
    experimentoUno();
    experimentoDos();
    experimentoTres();
    experimentoCuatro();
    experimentoCinco();
}

    // ---------- utilidades ----------

    /** Copia el arreglo para que cada algoritmo empiece en igualdad de condiciones. */
    private static LecturaSensor[] copiar(LecturaSensor[] original) {
        LecturaSensor[] copia = new LecturaSensor[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }

    /** Desordena un arreglo con una semilla fija, para que el experimento sea repetible. */
    private static LecturaSensor[] desordenar(LecturaSensor[] original) {
        LecturaSensor[] copia = copiar(original);
        Random azar = new Random(777L);

        for (int i = copia.length - 1; i > 0; i--) {
            int j = azar.nextInt(i + 1);
            LecturaSensor t = copia[i];
            copia[i] = copia[j];
            copia[j] = t;
        }

        return copia;
    }

    private static void reportar(String nombre, long milis) {
        System.out.printf(
                "%-14s comparaciones: %,14d   intercambios: %,14d   %6d ms%n",
                nombre,
                Ordenador.getComparaciones(),
                Ordenador.getIntercambios(),
                milis
        );
    }

    // ---------- EXPERIMENTO 1 ----------

    /** Los tres algoritmos simples sobre 10.000 lecturas DESORDENADAS. */
    private static void experimentoUno() {
        System.out.println("=== EXP 1: ALGORITMOS SIMPLES, 10.000 LECTURAS DESORDENADAS ===");

        LecturaSensor[] base = desordenar(GeneradorDatos.generar(10_000));

        LecturaSensor[] a = copiar(base);
        long t = System.currentTimeMillis();
        Ordenador.burbuja(a);
        reportar("Burbuja", System.currentTimeMillis() - t);

        LecturaSensor[] b = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.seleccion(b);
        reportar("Seleccion", System.currentTimeMillis() - t);

        LecturaSensor[] c = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.insercion(c);
        reportar("Insercion", System.currentTimeMillis() - t);

        System.out.println();
    }

    // ---------- EXPERIMENTO 2 ----------

    /** Los tres algoritmos simples sobre 10.000 lecturas ORDENADAS. */
    private static void experimentoDos() {
        System.out.println("=== EXP 2: ALGORITMOS SIMPLES, 10.000 LECTURAS ORDENADAS ===");

        LecturaSensor[] base = GeneradorDatos.generar(10_000);

        LecturaSensor[] a = copiar(base);
        long t = System.currentTimeMillis();
        Ordenador.burbuja(a);
        reportar("Burbuja", System.currentTimeMillis() - t);

        LecturaSensor[] b = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.seleccion(b);
        reportar("Seleccion", System.currentTimeMillis() - t);

        LecturaSensor[] c = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.insercion(c);
        reportar("Insercion", System.currentTimeMillis() - t);

        System.out.println();
    }

    // ---------- EXPERIMENTO 3 ----------

    /** Compara Insercion, MergeSort y HeapSort con diferentes tamaños. */
    private static void experimentoTres() {
        System.out.println("=== EXP 3: INSERCION, MERGESORT Y HEAPSORT ===");

        int[] tamanos = {1_000, 10_000, 100_000};

        for (int n : tamanos) {
            System.out.println("--- n = " + n + " ---");

            LecturaSensor[] base = desordenar(GeneradorDatos.generar(n));

            LecturaSensor[] a = copiar(base);
            long t = System.currentTimeMillis();
            Ordenador.insercion(a);
            reportar("Insercion", System.currentTimeMillis() - t);

            LecturaSensor[] b = copiar(base);
            t = System.currentTimeMillis();
            Ordenador.mergeSort(b);
            reportar("MergeSort", System.currentTimeMillis() - t);

            LecturaSensor[] c = copiar(base);
            t = System.currentTimeMillis();
            Ordenador.heapSort(c);
            reportar("HeapSort", System.currentTimeMillis() - t);

            System.out.println();
        }
    }

    // ---------- EXPERIMENTO 4 ----------

    private static void experimentoCuatro() {
    System.out.println("=== EXP 4: QUICKSORT CON PIVOTE ALEATORIO ===");

    // Datos desordenados
    LecturaSensor[] desordenados =
            desordenar(GeneradorDatos.generar(50_000));

    long t = System.currentTimeMillis();

    try {
        Ordenador.quickSortPivotePrimero(desordenados);
        reportar("Desordenados", System.currentTimeMillis() - t);
    } catch (StackOverflowError e) {
        System.out.println("Desordenados: StackOverflowError");
    }

    System.out.println();

    // Datos ordenados
    LecturaSensor[] ordenados =
            GeneradorDatos.generar(50_000);

    t = System.currentTimeMillis();

    try {
        Ordenador.quickSortPivotePrimero(ordenados);
        reportar("Ordenados", System.currentTimeMillis() - t);
    } catch (StackOverflowError e) {
        System.out.println("Ordenados: StackOverflowError");
    }

    System.out.println();
}

    // ---------- EXPERIMENTO 5 ----------

   // ---------- EXPERIMENTO 5 ----------

    // ---------- EXPERIMENTO 5 ----------

    private static void experimentoCinco() {
        System.out.println("=== EXP 5: ORDENAMIENTO Y BUSQUEDA BINARIA ===");

        // Generamos 10.000 lecturas
        LecturaSensor[] datos = GeneradorDatos.generar(10_000);

        // Elegimos una lectura que sabemos que existe
        LecturaSensor objetivo = datos[5_000];

        // Verificamos que los datos esten ordenados por timestamp
        System.out.println("¿Datos ordenados por timestamp? "
                + Ordenador.estaOrdenadoPorTimestamp(datos));

        // Buscamos la lectura por timestamp usando busqueda binaria
        int posicion = BuscadorLecturas.busquedaBinariaPorTimestamp(
                datos,
                objetivo.getTimestamp()
        );

        System.out.println("Posicion encontrada por busqueda binaria: " + posicion);
        System.out.println("Comparaciones de la busqueda binaria: "
                + BuscadorLecturas.getComparaciones());

        // Ahora ordenamos las lecturas por PM2.5
        Ordenador.ordenarPorPm25(datos);

        // Verificamos si siguen ordenadas por timestamp
        System.out.println("¿Siguen ordenados por timestamp despues de ordenar por PM2.5? "
                + Ordenador.estaOrdenadoPorTimestamp(datos));

        // Intentamos nuevamente la busqueda binaria por timestamp
        posicion = BuscadorLecturas.busquedaBinariaPorTimestamp(
                datos,
                objetivo.getTimestamp()
        );

        System.out.println("Posicion encontrada despues de ordenar por PM2.5: "
                + posicion);
        System.out.println("Comparaciones de la segunda busqueda binaria: "
                + BuscadorLecturas.getComparaciones());

        // Finalmente hacemos una busqueda lineal para comprobar el resultado
        posicion = BuscadorLecturas.busquedaLinealPorTimestamp(
                datos,
                objetivo.getTimestamp()
        );

        System.out.println("Posicion encontrada por busqueda lineal: " + posicion);
        System.out.println("Comparaciones de la busqueda lineal: "
                + BuscadorLecturas.getComparaciones());

        System.out.println();
    }
}