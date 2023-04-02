package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiNode;
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
            LexiNode root = new LexiNode('*', "");
            String word = panneauRecherche.getBarRechercheText();
            String definition = panneauDefinition.getDefinitionText();

            if (word.isEmpty() || definition.isEmpty()) {
                // Show a warning message if either the word or the definition is empty
                JOptionPane.showMessageDialog(PanneauAjouterModifier.this, "Word and definition must not be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                DictioToolBox.updateOrAddWord(DictioToolBox.filePath ,root, word, definition);
                lexiTree.generateTreeFromString(DictioToolBox.content);
                panneauListeMots.updateList();
                System.out.println(lexiTree.displayAll());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

}
