package uno;

public class Cartas {
    //Variables para guardar el color y el valor de la carta
    private String color;
    private String valor;

    public Cartas(String color, String valor) {
        this.color = color;
        this.valor = valor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValor() {
        return valor;
    }

    // usamos este boolean que devuelve true si la carta es comodin
    // osea el cambio de color o el mismisimo +4
    public boolean esComodin() {
        return valor.equals("Comodín") || valor.equals("+4");
    }

    public String toString() {
        return color + " " + valor;
    }
}
