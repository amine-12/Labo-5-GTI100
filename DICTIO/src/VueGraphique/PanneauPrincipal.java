package VueGraphique;

import javax.swing.*;
import java.awt.*;

/**
 * La classe PanneauPrincipal est un panneau qui contient les différents panneaux de l'interface graphique.
 * Il est composé de :
 * - PanneauListeMots : panneau qui affiche la liste des mots existants dans le dictionnaire.
 * - PanneauChargerEnregistrer : panneau qui contient les boutons pour charger/enregistrer le dictionnaire.
 * - PanneauDefinition : panneau qui affiche la définition du mot sélectionné dans le panneau de recherche.
 * - PanneauRecherche : panneau qui permet de rechercher un mot dans le dictionnaire.
 * - PanneauAjouterModifier : panneau qui permet d'ajouter ou de modifier un mot dans le dictionnaire.
 *
 * @author Mohammed Amine Mazigh
 * @author Muhammet Kayhan
 */
 public class PanneauPrincipal  extends JPanel  {

   /**
    * Constructeur de la classe PanneauPrincipal.
    *
    * @param dimension la dimension du panneau principal.
    */
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
