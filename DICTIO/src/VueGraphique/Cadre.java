package VueGraphique;

import javax.swing.*;
import java.awt.*;

public class Cadre extends JFrame implements Runnable {

    public Cadre() {
        super("Dictio");
    }


    public void initCadre(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(900, 300);
        setPreferredSize(dimension);

        PanneauPrincipal panneauPrincipal = new PanneauPrincipal(dimension);
        setContentPane(panneauPrincipal);

        pack();
    }

    @Override
    public void run() {
        initCadre();
        setVisible(true);
    }

}
