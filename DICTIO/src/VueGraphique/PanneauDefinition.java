package VueGraphique;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 * La classe PanneauDefinition est classe qui permet d'afficher les définitions du mot
 * rechercher dans la zone de recherche. Elle contient une zone d'affichage pour les définitions.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class PanneauDefinition extends JPanel {
    private JTextArea definitionArea;

    /**
     * Constructeur de la classe PanneauDefinition qui initialise le panneau avec une dimension donnée.
     *
     * @param dimension La dimension du panneau à initialiser.
     */
    public PanneauDefinition(Dimension dimension){
        initPanneauDefinition(dimension);
    }

    /**
     * Méthode d'initialisation du panneau.
     *
     * @param dimension La dimension du panneau à initialiser.
     */
    public void initPanneauDefinition(Dimension dimension) {
        setPreferredSize(new Dimension((int)(dimension.getWidth() * 0.5), (int)(dimension.getHeight() * 0.5)));
        setLayout(new BorderLayout());

        definitionArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(definitionArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Méthode qui retourne le texte affiché dans la zone de texte des définitions.
     *
     * @return Le texte affiché dans la zone de texte des définitions.
     */
    public String getDefinitionText(){
        return definitionArea.getText();
    }

    /**
     * Méthode qui met à jour les définitions affichées dans la zone de texte des définitions.
     *
     * @param definitions La liste des définitions à afficher.
     */
    public void updateDefinition(ArrayList<String> definitions) {
        definitionArea.setText("");
        int i = 1;
        for (String definition : definitions) {
            definitionArea.append(i + ") " + definition + "\n");
            i++;
        }
        definitionArea.getParent().repaint();
    }
}
