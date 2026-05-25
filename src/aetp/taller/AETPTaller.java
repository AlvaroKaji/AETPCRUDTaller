package aetp.taller;

public class AETPTaller {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ftmMain ventana = new ftmMain();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
