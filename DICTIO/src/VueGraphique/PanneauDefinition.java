package VueGraphique;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class PanneauDefinition extends JPanel {
    private JTextArea definitionArea;
    public PanneauDefinition(Dimension dimension){
        initPanneauDefinition(dimension);
    }
    public void initPanneauDefinition(Dimension dimension) {
        setPreferredSize(new Dimension((int)(dimension.getWidth() * 0.5), (int)(dimension.getHeight() * 0.5)));
        setLayout(new BorderLayout());

        definitionArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(definitionArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getDefinitionText(){
        return definitionArea.getText();
    }

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
