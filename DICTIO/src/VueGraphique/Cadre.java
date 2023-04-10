package VueGraphique;

import javax.swing.*;
import java.awt.*;

/**
 * La classe Cadre étend JFrame et implémente Runnable. Elle représente la fenêtre
 * principale de l'application Dictio avec une taille définie et un panneau principal.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class Cadre extends JFrame implements Runnable {

    /**
     * Constructeur de la classe Cadre, initialise le titre de la fenêtre.
     */
    public Cadre() {
        super("Dictio");
    }

    /**
     * Initialise les propriétés de la fenêtre principale, y compris la taille,
     * l'action par défaut lors de la fermeture, et ajoute un PanneauPrincipal
     * comme contenu.
     */
    public void initCadre(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(900, 300);
        setPreferredSize(dimension);

        PanneauPrincipal panneauPrincipal = new PanneauPrincipal(dimension);
        setContentPane(panneauPrincipal);

        pack();
    }

    /**
     * Méthode run() implémentée pour l'interface Runnable, qui initialise la fenêtre
     * principale et la rend visible.
     */
    @Override
    public void run() {
        initCadre();
        setVisible(true);
    }

}
