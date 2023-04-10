package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * La classe PanneauAjouterModifier représente un panneau graphique qui permet à l'utilisateur d'ajouter ou
 * de modifier des mots et leurs définitions dans le dictionnaire. Ce panneau interagit avec les autres
 * panneaux de l'interface utilisateur, tels que PanneauDefinition, PanneauRecherche et PanneauListeMots.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class PanneauAjouterModifier extends JPanel {
    private final PanneauDefinition panneauDefinition;
    private final PanneauRecherche panneauRecherche;
    private final PanneauListeMots panneauListeMots;

    /**
     * Constructeur de la classe PanneauAjouterModifier.
     * @param dimension - dimensions du panneau.
     * @param panneauDefinition - instance du panneau PanneauDefinition.
     * @param panneauRecherche - instance du panneau PanneauRecherche.
     * @param panneauListeMots - instance du panneau PanneauListeMots.
     */
    public PanneauAjouterModifier(Dimension dimension, PanneauDefinition panneauDefinition,
                                  PanneauRecherche panneauRecherche, PanneauListeMots panneauListeMots){
        initPanneauAjouterModifier(dimension);
        this.panneauDefinition = panneauDefinition;
        this.panneauRecherche = panneauRecherche;
        this.panneauListeMots = panneauListeMots;
    }

    /**
     * Initialise le panneau PanneauAjouterModifier avec ses composants.
     * @param dimension - dimensions du panneau.
     */
    public void initPanneauAjouterModifier(Dimension dimension){
        setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight() * 0.1)));
        setLayout(new BorderLayout());
        JButton ajouterModifierButton = new JButton("ajouter/modifier");
        ajouterModifierButton.addActionListener(new AjouterModifierListener());
        add(ajouterModifierButton, BorderLayout.CENTER);
    }


    /**
     * La classe AjouterModifierListener est un ActionListener qui gère les actions d'ajout ou
     * de modification de mots et de définitions dans le dictionnaire.
     */
    private class AjouterModifierListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LexiTree lexiTree = new LexiTree();
            String word = panneauRecherche.getBarRechercheText();
            String definition = panneauDefinition.getDefinitionText();

            if (word.isEmpty() || definition.isEmpty()) {
                JOptionPane.showMessageDialog(PanneauAjouterModifier.this,
                        "Le mot et la définition ne doivent pas être vides", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LexiTree.WordStatus status = lexiTree.addOrUpdateWord(word, definition);
            try {
                DictioToolBox.Dictionary().readFileContents(DictioToolBox.filePath);

                if (status == LexiTree.WordStatus.ADDED) {
                    DictioToolBox.Dictionary().addWordToFile(DictioToolBox.filePath, word, definition);
                    DictioToolBox.Dictionary().readFileContents(DictioToolBox.filePath);
                    panneauListeMots.updateList();
                } else if (status == LexiTree.WordStatus.UPDATED) {
                    DictioToolBox.Dictionary().updateWordDefinitionInFile(DictioToolBox.filePath, word, definition);
                    DictioToolBox.Dictionary().readFileContents(DictioToolBox.filePath);
                }
            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lexiTree.generateTreeFromString(DictioToolBox.content);
        }
    }

}
