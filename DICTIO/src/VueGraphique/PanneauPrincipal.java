package VueGraphique;

import javax.swing.*;
import java.awt.*;

public class PanneauPrincipal  extends JPanel  {
   public PanneauPrincipal(Dimension dimension){
      setLayout(new BorderLayout());
      PanneauListeMots panneauListeMots = new PanneauListeMots(dimension);
      add(panneauListeMots, BorderLayout.EAST);

      PanneauChargerEnregistrer panneauChargerEnregistrer = new PanneauChargerEnregistrer(dimension, panneauListeMots);
      add(panneauChargerEnregistrer, BorderLayout.NORTH);

      PanneauDefinition panneauDefinition = new PanneauDefinition(dimension);
      add(panneauDefinition, BorderLayout.CENTER);

      PanneauRecherche panneauRecherche = new PanneauRecherche(dimension,panneauDefinition);
      add(panneauRecherche, BorderLayout.WEST);


      PanneauAjouterModifier panneauAjouterModifier = new PanneauAjouterModifier(dimension,panneauDefinition,panneauRecherche,panneauListeMots);
      add(panneauAjouterModifier, BorderLayout.SOUTH);
   }
}
