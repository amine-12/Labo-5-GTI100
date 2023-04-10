package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * La classe PanneauRecherche est une classe qui permet à l'utilisateur de rechercher des mots
 * dans un dictionnaire. Elle contient une zone de texte pour saisir la recherche, une zone d'affichage pour
 * afficher les résultats de la recherche et une référence à un objet PanneauDefinition qui affiche la définition
 * d'un mot sélectionné dans la zone d'affichage.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class PanneauRecherche extends JPanel {
    private JTextField searchBar;
    private JTextArea displayArea;
    private final PanneauDefinition panneauDefinition;

    /**
     * Constructeur de la classe PanneauRecherche.
     * @param dimension La dimension du panneau.
     * @param panneauDefinition Le panneau de définition associé à cette instance.
     */
    public PanneauRecherche(Dimension dimension,PanneauDefinition panneauDefinition){
        initPanneauRecherche(dimension);
        this.panneauDefinition = panneauDefinition;
    }

    /**
     * Initialise le panneau de recherche avec la dimension spécifiée.
     * @param dimension La dimension du panneau.
     */
    public void initPanneauRecherche(Dimension dimension) {
        setPreferredSize(new Dimension((int)(dimension.getWidth() * 0.3), (int)(dimension.getHeight() * 0.5)));
        setLayout(new BorderLayout());

        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(200, 30));
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.add(searchBar, BorderLayout.CENTER);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        displayArea.setEditable(false);

        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDisplayArea();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDisplayArea();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDisplayArea();
            }
        });

        add(searchBarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Renvoie le texte actuel de la zone de recherche.
     * @return Le texte actuel de la zone de recherche.
     */
    public String getBarRechercheText(){
        return searchBar.getText();
    }

    /**
     * Met à jour la zone d'affichage avec les résultats de la recherche actuelle.
     */
    public void updateDisplayArea() {
        if (DictioToolBox.content != null){
            LexiTree lexiTree = new LexiTree();
            lexiTree.generateTreeFromString(DictioToolBox.content);
            String input = searchBar.getText();
            ArrayList<String> words = lexiTree.getWordsWith(input);
            displayArea.setText("");

            for (String word : words) {
                displayArea.append(word + "\n");
            }

            if (!words.isEmpty()) {
                String firstWord = words.get(0);
                ArrayList<String> firstWordDefinitions = (ArrayList<String>) lexiTree.getWordDefinitions(firstWord);
                panneauDefinition.updateDefinition(firstWordDefinitions);
            }else {
                panneauDefinition.updateDefinition(new ArrayList<>());
            }
            displayArea.getParent().repaint();
        }else {
            JOptionPane.showMessageDialog(null, "Aucun fichier est charger", "Warning", JOptionPane.WARNING_MESSAGE);
            displayArea.setText("");
            displayArea.getParent().repaint();
        }
    }


}
