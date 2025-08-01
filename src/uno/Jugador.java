package uno;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Cartas> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Cartas> getMano() {
        return mano;
    }
    // agrega la carta a la mano del jugador
    public void agregarCarta(Cartas carta) {
        mano.add(carta);
    }
    //quita la carta de la mano
    public void removerCarta(Cartas carta) {
        mano.remove(carta);
    }
    //verifica si el jugador tiene cartas
    public boolean tieneCartas() {
        return !mano.isEmpty();
    }
    //checkea si el jugador tiene una carta que se puede jugar
    public boolean puedeJugar(Cartas cartaSuperior) {
        for (Cartas carta : mano) {
            if (carta.getColor().equals(cartaSuperior.getColor()) ||
                    carta.getValor().equals(cartaSuperior.getValor()) ||
                    carta.esComodin()) {
                return true;
            }
        }
        return false;
    }
    //devuelve la primera carta jugable encontrada
    public Cartas obtenerCartaJugable(Cartas cartaSuperior) {
        for (Cartas carta : mano) {
            if (carta.getColor().equals(cartaSuperior.getColor()) ||
                    carta.getValor().equals(cartaSuperior.getValor()) ||
                    carta.esComodin()) {
                return carta;
            }
        }
        return null;
    }
    public void robarVariasCartas(Mazo mazo, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Cartas carta = mazo.robarCarta();
            if (carta != null) {
                this.agregarCarta(carta);
            }
        }
    }

    //muestra la mano del jugador
    public void mostrarMano() {
        System.out.println("Cartas de " + nombre + ":");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println((i + 1) + ". " + mano.get(i));
        }
    }
}
