package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PanneauAjouterModifier extends JPanel {
    private PanneauDefinition panneauDefinition;
    private  PanneauRecherche panneauRecherche;
    private PanneauListeMots panneauListeMots;
    public PanneauAjouterModifier(Dimension dimension, PanneauDefinition panneauDefinition,
                                  PanneauRecherche panneauRecherche, PanneauListeMots panneauListeMots){
        initPanneauAjouterModifier(dimension);
        this.panneauDefinition = panneauDefinition;
        this.panneauRecherche = panneauRecherche;
        this.panneauListeMots = panneauListeMots;
    }

    public void initPanneauAjouterModifier(Dimension dimension){
        setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight() * 0.1)));
        setLayout(new BorderLayout());
        JButton ajouterModifierButton = new JButton("ajouter/modifier");
        ajouterModifierButton.addActionListener(new AjouterModifierListener());
        add(ajouterModifierButton, BorderLayout.CENTER);
    }

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
