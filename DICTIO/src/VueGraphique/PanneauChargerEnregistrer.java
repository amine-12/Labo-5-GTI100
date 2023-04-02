package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class PanneauChargerEnregistrer extends JPanel {
    private final PanneauListeMots panneauListeMots;
    public PanneauChargerEnregistrer(Dimension dimension,PanneauListeMots panneauListeMots){
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
        enregistrerButton.addActionListener(new EnregistrerButtonListener());
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
                    DictioToolBox.content = DictioToolBox.Dictionary().readFileContents(path);
                    lexiTree.generateTreeFromString(DictioToolBox.content);
                    panneauListeMots.updateList();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class EnregistrerButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            FileDialog fileDialog = new FileDialog((Frame) SwingUtilities.windowForComponent(PanneauChargerEnregistrer.this), "Enregistrer sous", FileDialog.SAVE);
            fileDialog.setFile("*.txt");
            fileDialog.setVisible(true);

            String selectedFile = fileDialog.getFile();
            if (selectedFile != null) {
                String newPath = fileDialog.getDirectory() + selectedFile;
                if (!newPath.toLowerCase().endsWith(".txt")) {
                    newPath += ".txt";
                }
                File newFile = new File(newPath);
                try (FileWriter writer = new FileWriter(newFile)) {
                    writer.write(DictioToolBox.content);
                    JOptionPane.showMessageDialog(PanneauChargerEnregistrer.this, "Fichier enregistré avec succès.");
                    if (DictioToolBox.filePath != null && !DictioToolBox.filePath.equals(newPath)) {
                        File oldFile = new File(DictioToolBox.filePath);
                        oldFile.delete();
                    }
                    DictioToolBox.filePath = newPath;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
