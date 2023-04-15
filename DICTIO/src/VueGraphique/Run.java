package VueGraphique;

import javax.swing.*;

/**
 * La classe Run contient la méthode main() qui lance l'application en créant une instance de la classe Cadre et en l'exécutant
 * sur le thread Swing.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 *
 */
public class Run {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Cadre());
    }
}
