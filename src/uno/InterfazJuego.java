package uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazJuego extends JFrame {
    private JuegoUno juego;
    private JPanel panelCartas;
    private JLabel lblCartaActual;
    private JLabel lblTurno;
    private JButton btnRobar;

    public InterfazJuego(ArrayList<Jugador> jugadores) {
        juego = new JuegoUno(jugadores);
        setTitle("Juego UNO");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior: turno actual
        lblTurno = new JLabel("Turno de: " + juego.getJugadorActual().getNombre());
        lblTurno.setHorizontalAlignment(SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurno.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTurno, BorderLayout.NORTH);

        // Panel central: carta actual
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new FlowLayout());
        panelCentral.setBackground(new Color(34, 139, 34)); // Verde mesa

        lblCartaActual = new JLabel("Carta en juego:");
        lblCartaActual.setFont(new Font("Arial", Font.BOLD, 16));
        lblCartaActual.setForeground(Color.WHITE);
        panelCentral.add(lblCartaActual);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior: cartas del jugador
        panelCartas = new JPanel();
        panelCartas.setLayout(new FlowLayout());
        panelCartas.setBackground(new Color(139, 69, 19)); // Marrón mesa
        panelCartas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Tus cartas",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));
        add(panelCartas, BorderLayout.SOUTH);

        // Panel derecho: botón robar y info
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(new Color(139, 69, 19));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Botón para robar carta
        btnRobar = new JButton("<html><center>ROBAR<br>CARTA</center></html>");
        btnRobar.setPreferredSize(new Dimension(100, 80));
        btnRobar.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnRobar.setForeground(Color.WHITE);
        btnRobar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRobar.setFocusPainted(false);
        btnRobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jugador actual = juego.getJugadorActual();
                Cartas robada = juego.getMazo().robarCarta();
                if (robada != null) {
                    actual.agregarCarta(robada);
                    actualizarVista();
                }
            }
        });

        JLabel lblMazo = new JLabel("<html><center>Cartas<br>restantes:<br>" +
                juego.getMazo().cartasRestantes() + "</center></html>");
        lblMazo.setForeground(Color.WHITE);
        lblMazo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMazo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDerecho.add(btnRobar);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(lblMazo);

        add(panelDerecho, BorderLayout.EAST);

        actualizarVista();
    }

    // Método para obtener el color de la carta
    private Color getColorCarta(String color) {
        switch (color.toLowerCase()) {
            case "rojo":
                return new Color(220, 20, 20);
            case "verde":
                return new Color(20, 180, 20);
            case "azul":
                return new Color(20, 20, 220);
            case "amarillo":
                return new Color(255, 215, 0);
            case "negro":
                return new Color(50, 50, 50);
            default:
                return Color.GRAY;
        }
    }

    // Método para obtener el color del texto
    private Color getColorTexto(String color) {
        switch (color.toLowerCase()) {
            case "amarillo":
                return Color.BLACK; // Texto negro en fondo amarillo
            default:
                return Color.WHITE; // Texto blanco en otros colores
        }
    }

    // Método para crear un botón de carta con estilo
    private JButton crearBotonCarta(Cartas carta, ActionListener listener) {
        String textoValor = carta.getValor();

        // Ajustar texto para cartas especiales
        switch (textoValor) {
            case "Salta":
                textoValor = "⊘";
                break;
            case "Reversa":
                textoValor = "⟲";
                break;
            case "+2":
                textoValor = "+2";
                break;
            case "+4":
                textoValor = "+4";
                break;
            case "Comodín":
                textoValor = "★";
                break;
        }

        JButton boton = new JButton("<html><center>" + textoValor + "</center></html>");
        boton.setPreferredSize(new Dimension(70, 100));
        boton.setBackground(getColorCarta(carta.getColor()));
        boton.setForeground(getColorTexto(carta.getColor()));
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        boton.setFocusPainted(false);
        boton.addActionListener(listener);

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.YELLOW, 3),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        });

        return boton;
    }

    private void actualizarVista() {
        // Actualiza el texto del turno
        lblTurno.setText("Turno de: " + juego.getJugadorActual().getNombre());

        // Actualizar carta actual en el centro
        JPanel panelCentral = (JPanel) getContentPane().getComponent(1);
        panelCentral.removeAll();

        JLabel lblTexto = new JLabel("Carta en juego:");
        lblTexto.setFont(new Font("Arial", Font.BOLD, 16));
        lblTexto.setForeground(Color.WHITE);
        panelCentral.add(lblTexto);

        // Crear botón para la carta actual (no clickeable)
        JButton cartaActualBtn = crearBotonCarta(juego.getCartaActual(), null);
        cartaActualBtn.setPreferredSize(new Dimension(90, 130));
        cartaActualBtn.setEnabled(false);
        panelCentral.add(cartaActualBtn);

        panelCentral.revalidate();
        panelCentral.repaint();

        // Actualizar cartas del jugador actual
        panelCartas.removeAll();
        Jugador actual = juego.getJugadorActual();

        for (Cartas carta : actual.getMano()) {
            JButton btnCarta = crearBotonCarta(carta, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar si es un comodín antes de jugarlo
                    if (carta.getValor().equals("Comodín") || carta.getValor().equals("+4")) {
                        String[] colores = {"rojo", "verde", "azul", "amarillo"};
                        String nuevoColor = (String) JOptionPane.showInputDialog(
                                null,
                                "Elige un color:",
                                "Cambio de color",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                colores,
                                colores[0]
                        );

                        if (nuevoColor != null) {
                            Cartas cartaConColor = new Cartas(nuevoColor, carta.getValor());
                            jugarCarta(cartaConColor);
                        }
                    } else {
                        jugarCarta(carta);
                    }
                }

                private void jugarCarta(Cartas cartaAJugar) {
                    boolean jugadaValida = juego.jugarTurno(cartaAJugar);

                    if (jugadaValida) {
                        if (juego.haGanado(actual)) {
                            JOptionPane.showMessageDialog(null,
                                    actual.getNombre() + " ha ganado el juego!",
                                    "¡GANADOR!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        } else {
                            actualizarVista();
                        }
                    } else {
                        animarMazoInvalido();
                        JOptionPane.showMessageDialog(null,
                                "¡Carta inválida! Debe coincidir en color, número o ser comodín.",
                                "Jugada inválida",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            panelCartas.add(btnCarta);
        }

        // Actualizar contador de cartas en el mazo
        JPanel panelDerecho = (JPanel) getContentPane().getComponent(3);
        JLabel lblMazo = (JLabel) panelDerecho.getComponent(2);
        lblMazo.setText("<html><center>Cartas<br>restantes:<br>" +
                juego.getMazo().cartasRestantes() + "</center></html>");

        panelCartas.revalidate();
        panelCartas.repaint();
    }

    private void animarMazoInvalido() {
        Color original = btnRobar.getBackground();
        btnRobar.setBackground(Color.RED);

        Timer timer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRobar.setBackground(original);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear jugadores de prueba
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Lauro"));
        jugadores.add(new Jugador("Isaac"));
        jugadores.add(new Jugador("Janiskelys"));
        jugadores.add(new Jugador("Diego"));

        InterfazJuego gui = new InterfazJuego(jugadores);
        gui.setVisible(true);
    }
}
