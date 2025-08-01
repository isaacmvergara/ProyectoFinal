package uno;

import javax.swing.*;
import java.awt.*;

public class CartaPanel extends JPanel {
    private String valor;
    private Color color;

    public CartaPanel(String valor, Color color) {
        this.valor = valor;
        this.color = color;
        setPreferredSize(new Dimension(80, 120));
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(valor)) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 10;
        g.drawString(valor, x, y);
    }
}