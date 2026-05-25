package aetp.taller;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ftmMain extends JFrame {

    private JButton btnArticulos;

    public ftmMain() {
        initComponents();
    }

    private void initComponents() {
        setTitle("CRUD AETP - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 200);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Artículos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        btnArticulos = new JButton("Abrir módulo");
        btnArticulos.addActionListener(evt -> abrirArticulos());

        panel.add(titulo, BorderLayout.CENTER);
        panel.add(btnArticulos, BorderLayout.SOUTH);
        setContentPane(panel);
    }

    private void abrirArticulos() {
        JOptionPane.showMessageDialog(this, "El módulo de artículos será reconstruido en el siguiente paso.");
    }
}
