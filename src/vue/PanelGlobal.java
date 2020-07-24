package vue;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controleur;
import modele.Chronologie;
import modele.Date;
import modele.Evenement;
import modele.ImportFrises;
import modele.ListeFrises;
import utils.LectureEcriture;
/**
 * Abstration du conteneur m�re. 
 * C'est ceci qui va contenenir tout les autres panels.
 * @author Marc BOURGEOIS et Iban CORNILY
 *
 */
public class PanelGlobal extends JPanel implements ActionListener, ConstantesCouleurs, ConstantesMenu {
	/**
	 * JPanel contenant le formulaire de cr�ation d'un nouvel �v�nement.
	 */
	private PanelCreationEvenement panelCreationEvenement;
	/**
	 * JPanel contenant le formulaire de cr�ation d'une nouvelle frise.
	 */
	private PanelCreationFrise panelCreationFrise;
	/**
	 * JPanel contenant la table.
	 */
	private PanelAffichage panelAffichage;
	/**
	 * Gestionnaire permettant d'afficher 1 panel � la fois.
	 */
	private CardLayout gestionnaire;
	/**
	 * chronologie principale.
	 */
	Chronologie chronologie;
	/**
	 * liste de toutes les chronologies.
	 */
	ListeFrises listeFrises = new ListeFrises();
	/**
	 * controleur du panelGlobal et donc de tous les autres panels
	 */
	Controleur controleur ;
	
	
	/**
	 * Constructeur par d�faut de PanelAgenda.
	 */
	public PanelGlobal () {
		
		gestionnaire = new CardLayout(25,25);
		setLayout(gestionnaire);
		
		// import de frise � partir de fichiers de sauvegardes
			int dialog = JOptionPane.showConfirmDialog(this, "Voulez-vous importez des frises pr�existantes?");
			if(dialog == 0) {
				File repertoireSauvegarde = new File("sauvegardes");
				
				String [] listeNomFichiers = repertoireSauvegarde.list();
			    
				ImportFrises importFrises = new ImportFrises(null, "Import de frises" , true, listeNomFichiers);
				String[] frisesSelectionnees = importFrises.getFrisesSelectionnees();
				
						
				Chronologie friseAAjouter; 
				File fichier;
				if(frisesSelectionnees.length>0) {
					for (int i = 0 ; i < frisesSelectionnees.length ; i++) {
						if(frisesSelectionnees[i] == null) {
							break;
						}
						fichier = new File ("sauvegardes\\" + frisesSelectionnees[i]);
						friseAAjouter = (Chronologie) LectureEcriture.lecture(fichier);
						listeFrises.ajout(friseAAjouter);
					}
				}
			}
			System.out.println(" \n ");
		
		Chronologie friseACopier = listeFrises.getFrise("Coronavirus: La menace invisible");
		chronologie = new Chronologie(friseACopier.getTitre(), friseACopier.getDateDebut(), friseACopier.getDateFin(), friseACopier.getEchelle(), friseACopier.getCheminFichier(), friseACopier.getEvenements());
		panelCreationEvenement = new PanelCreationEvenement(listeFrises); 
		panelCreationFrise = new PanelCreationFrise();
		panelAffichage = new PanelAffichage(chronologie);
		controleur = new Controleur (this, chronologie, panelCreationEvenement, panelAffichage, panelCreationFrise, listeFrises);
		
		this.add(panelCreationEvenement, ConstantesMenu.INTIT_CRE_EVT);	
		this.add(panelCreationFrise, ConstantesMenu.INTIT_CRE_FRI);
		this.add(panelAffichage, ConstantesMenu.INTIT_AFF);
		this.setBackground(GREY);
	}

	/**
	 * Donne le premier champ du formulaire (titre).
	 * @return
	 */
	public JTextField getChampRecevantFocus() {		
		return panelCreationEvenement.getChampRecevantFocus();
	}
	
	/**
	 * Capte des �venement clavier et souris de l'utilisateur sur la barre de menu.
	 */
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		if (actionCommand.equals(ConstantesMenu.INTIT_CRE_EVT)) {
			gestionnaire.show(this, ConstantesMenu.INTIT_CRE_EVT);
		}
		else if (actionCommand.equals(ConstantesMenu.INTIT_CRE_FRI)) {
			gestionnaire.show(this, ConstantesMenu.INTIT_CRE_FRI);
		}
		else if (actionCommand.equals(ConstantesMenu.INTIT_AFF)) {
			gestionnaire.show(this, ConstantesMenu.INTIT_AFF);
		}
		else if (actionCommand.equals(ConstantesMenu.INTIT_FER)) {
			int dialog = JOptionPane.showConfirmDialog(this, "�tes-vous s�r de vouloir quitter?");
			if(dialog == 0)
				System.exit(0);
		}
	}
	
	
	/**
	 * vide le panelCreation apr�s l'ajout d'un �v�nement
	 * @param parListeFrises liste de toutes les frises
	 */
	public void resetPanelCreation (ListeFrises parListeFrises) {
		listeFrises = parListeFrises;
		remove(panelCreationEvenement);
		
		panelCreationEvenement = new PanelCreationEvenement(listeFrises);
		this.add(panelCreationEvenement, ConstantesMenu.INTIT_CRE_EVT);
		controleur.setPanelCreation(panelCreationEvenement);
		panelCreationEvenement.getBoutonAjout().addActionListener(controleur);
		revalidate();
		repaint();
	}
	
	/**
	 * reset le panelAffichage pour afficher une nouvelle frise
	 * @param parFrise frise � afficher dans le panel affichage
	 */
	public void resetPanelAffichage (Chronologie parFrise) {
		remove(panelAffichage);
		chronologie = parFrise;
		panelAffichage = new PanelAffichage(parFrise);
		this.add(panelAffichage, ConstantesMenu.INTIT_AFF);
		controleur = new Controleur (this, chronologie, panelCreationEvenement, panelAffichage, panelCreationFrise, listeFrises);
		revalidate();
		repaint();
	}
	
	/**
	 * affiche une diapo (un panel) en fonction de l'�v�nement re�u
	 * @param intitCreEvt action de l'utilisateur sur la barre de menu
	 */
	public void showDiapo(String intitCreEvt) {
		gestionnaire.show(this, intitCreEvt);
	}

}
