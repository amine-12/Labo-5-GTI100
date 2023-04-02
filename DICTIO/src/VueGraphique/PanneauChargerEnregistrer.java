package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class PanneauChargerEnregistrer extends JPanel {
    private Dimension dimension;
    private PanneauListeMots panneauListeMots;
    public PanneauChargerEnregistrer(Dimension dimension,PanneauListeMots panneauListeMots){
        this.dimension = new Dimension(dimension);
        this.panneauListeMots = panneauListeMots;
        initPanneauChargerEnregistrer(dimension);
    }
    private void initPanneauChargerEnregistrer(Dimension dimension) {

        setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight() * 0.2)));
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0, 0, 0, 10);

        JButton chargerButton = new JButton("Charger");
        chargerButton.setPreferredSize(new Dimension(100, 30));

        chargerButton.addActionListener(new BrowseButtonListener());

        add(chargerButton,constraints);

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setPreferredSize(new Dimension(100, 30));
        add(enregistrerButton);
    }
    private class BrowseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LexiTree lexiTree = new LexiTree();
            FileDialog fileDialog = new FileDialog((Frame)
                    SwingUtilities.windowForComponent(PanneauChargerEnregistrer.this), "Select File", FileDialog.LOAD);

            fileDialog.setMode(FileDialog.LOAD);
            fileDialog.setVisible(true);
            String selectedFile = fileDialog.getFile();

            if (selectedFile != null) {
                String path = fileDialog.getDirectory() + selectedFile;
                try {
                    DictioToolBox.content = DictioToolBox.Dictionairy().readFileContents(path);
                    lexiTree.generateTreeFromString(DictioToolBox.content);
                    panneauListeMots.updateList();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
