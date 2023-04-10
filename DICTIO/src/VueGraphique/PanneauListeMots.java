package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;

/**
 * La classe PanneauListeMots est une classe qui affiche la liste de tous les mots du dictionnaire.
 * Elle contient une zone d'affichage JTextArea et un objet LexiTree pour stocker les mots du dictionnaire.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class PanneauListeMots extends JPanel{
    private final Dimension dimension;
    private JTextArea displayArea;
    private LexiTree lexiTree;

    /**
     * Constructeur de la classe PanneauListeMots qui initialise la dimension du panneau et appelle la méthode d'initialisation.
     * @param dimension La dimension du panneau.
     */
    public PanneauListeMots(Dimension dimension) {
        this.dimension = new Dimension(dimension);
        initPanneauListeMots();
    }

    /**
     * Méthode d'initialisation de la classe PanneauListeMots qui crée un LexiTree et un JTextArea pour afficher la liste de mots.
     */
    public void initPanneauListeMots(){
        lexiTree = new LexiTree();
        setPreferredSize(new Dimension((int)(dimension.getWidth() * 0.2), (int)(dimension.getHeight() * 0.5)));
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Méthode pour mettre à jour la liste de mots affichée dans le JTextArea à partir du contenu du fichier de dictionnaire.
     */
    public void updateList(){
        lexiTree.generateTreeFromString(DictioToolBox.content);
        String listeDeMots = lexiTree.displayOnlyWords();
        displayArea.setText(listeDeMots);
        displayArea.getParent().repaint();
    }


}
