package VueGraphique;

import DictioMoteur.DictioToolBox;
import DictioMoteur.LexiTree;

import javax.swing.*;
import java.awt.*;


public class PanneauListeMots extends JPanel{
    private Dimension dimension;
    private JTextArea displayArea;
    private LexiTree lexiTree;
    public PanneauListeMots(Dimension dimension) {
        this.dimension = new Dimension(dimension);
        initPanneauListeMots();
    }

    public void initPanneauListeMots(){
        lexiTree = new LexiTree();
        setPreferredSize(new Dimension((int)(dimension.getWidth() * 0.2), (int)(dimension.getHeight() * 0.5)));
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
    }


    public void updateList(){
        lexiTree.generateTreeFromString(DictioToolBox.content);
        String listeDeMots = lexiTree.displayOnlyWords();
        displayArea.setText(listeDeMots);
        displayArea.getParent().repaint();
    }


}
