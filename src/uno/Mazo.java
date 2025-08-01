package uno;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Cartas> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        inicializarMazo();
        barajar();
    }
    // crea todas las cartas del mazo
    private void inicializarMazo() {
        String[] colores = {"rojo", "verde", "azul", "amarillo"};
        String[] valores = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Salta", "Reversa", "+2"};

        // Agregar cartas de colores
        for (String color : colores) {
            // Una carta "0" por color
            cartas.add(new Cartas(color, "0"));

            // Dos copias de cada una del 1 al 9, y cartas especiales
            for (int i = 1; i <= 9; i++) {
                cartas.add(new Cartas(color, String.valueOf(i)));
                cartas.add(new Cartas(color, String.valueOf(i)));
            }
            for (int i = 10; i < valores.length; i++) {
                cartas.add(new Cartas(color, valores[i]));
                cartas.add(new Cartas(color, valores[i]));
            }
        }

        // Agregar las comodines
        for (int i = 0; i < 4; i++) {
            cartas.add(new Cartas("negro", "Comodín"));
            cartas.add(new Cartas("negro", "+4"));
        }
    }
    // mezcla las cartas
    public void barajar() {
        Collections.shuffle(cartas);
    }
    //verifica si ya no quedan cartas
    public boolean estaVacio() {
        return cartas.isEmpty();
    }
    // devuelve la primera carta del mazo y la elimina
    public Cartas robarCarta() {
        if (!estaVacio()) {
            return cartas.remove(0);
        } else {
            return null; // O lanzar excepción personalizada
        }
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}
