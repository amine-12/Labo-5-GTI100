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

/**
 * La classe PanneauChargerEnregistrer permet de charger et d'enregistrer des fichiers de dictionnaire. Ce panneau
 * contient deux boutons : un pour charger un fichier de dictionnaire et un
 * autre pour enregistrer le contenu du dictionnaire dans un fichier.
 * Cette classe travaille en collaboration avec DictioToolBox et LexiTree pour
 * gérer les opérations sur les fichiers de dictionnaire.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
public class PanneauChargerEnregistrer extends JPanel {
    private final PanneauListeMots panneauListeMots;

    /**
     * Constructeur de la classe PanneauChargerEnregistrer.
     * @param dimension - dimensions du panneau.
     * @param panneauListeMots - référence au panneau de liste de mots pour mettre à jour la liste des mots.
     */
    public PanneauChargerEnregistrer(Dimension dimension,PanneauListeMots panneauListeMots){
        this.panneauListeMots = panneauListeMots;
        initPanneauChargerEnregistrer(dimension);
    }

    /**
     * Initialise le panneau ChargerEnregistrer en configurant les dimensions, la disposition et les composants
     * du panneau, tels que les boutons "Charger" et "Enregistrer".
     *
     * @param dimension - Dimensions du panneau principal dont ce panneau fait partie.
     */
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

    /**
     * La classe BrowseButtonListener est une classe interne qui implémente
     * l'interface ActionListener. Elle est responsable de la gestion des actions
     * lorsqu'un utilisateur clique sur le bouton "Charger" pour sélectionner un fichier.
     * Lorsque l'utilisateur sélectionne un fichier, son contenu est lu et utilisé
     * pour générer un nouvel arbre de dictionnaire. Ensuite, la liste des mots est mise à jour.
     */
    private class BrowseButtonListener implements ActionListener {
        /**
         * Méthode appelée lorsqu'un utilisateur clique sur le bouton "Charger".
         * Elle ouvre un dialogue de sélection de fichier, lit le contenu du fichier
         * sélectionné, génère un nouvel arbre de dictionnaire à partir de son contenu
         * et met à jour la liste des mots.
         *
         * @param e - événement déclenché lors du clic sur le bouton "Charger".
         */
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

    /**
     * La classe EnregistrerButtonListener est une classe interne qui implémente
     * l'interface ActionListener. Elle gère l'action déclenchée lors de
     * l'appui sur le bouton "Enregistrer" du PanneauChargerEnregistrer.
     */
    private class EnregistrerButtonListener implements ActionListener{
        /**
         * Cette méthode est appelée lorsqu'une action est effectuée sur le bouton "Enregistrer".
         * Elle ouvre une boîte de dialogue pour enregistrer le fichier, écrit le contenu
         * du dictionnaire dans un fichier et met à jour le chemin du fichier dans DictioToolBox.
         * Si le fichier précédent n'est pas le même que le nouveau fichier enregistré,
         * le fichier précédent est supprimé.
         *
         * @param e - L'événement d'action déclenché par le bouton "Enregistrer".
         */
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
