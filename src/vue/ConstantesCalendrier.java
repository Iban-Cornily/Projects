package vue;

import java.awt.Color;

/**
 * Cha�nes de caract�res utilis�es pour les dates, horaires et boutons.
 * @author Marc
 *
 */
public interface ConstantesCalendrier {
	/**
	 * Abr�viations des noms des jours de la semaine en 2 caract�res.
	 */
	final String [] JOURS_SEMAINE = {"lu","ma","me","je","ve","sa","di"} ;  
	/**
	 * Noms des mois de l'ann�e.
	 */
	final  String [] MOIS = {"janvier", "f�vrier","mars","avril","mai","juin","juillet", "ao�t","septembre","octobre","novembre","d�cembre"};
	final  String [] MOIS_RAC = {"janv", "f�vr","mars","avril","mai","juin","juil", "ao�t","sep","oct","nov","d�c"};
	final  String [] MOIS_ULTRA_RAC = {"jan", "f�v","mar","avr","mai","juin","juil", "ao�","sep","oct","nov","d�c"};
	/**
	 * Intitul� du bouton "pr�c�dent".
	 */
	final String INTIT_PRECEDENT = "<";
	/**
	 * Intitul� du bouton "suivant".
	 */
	final String INTIT_SUIVANT = ">";
	/**
	 * Groupement des diff�rents boutons de navigation.
	 */
	final  String [] INTITULES_BOUTONS_NAVIGATION = {INTIT_PRECEDENT, INTIT_SUIVANT};
	/**
	 * Intitul� du bouton "ajouter".
	 */
	final String INTITULE_BOUTON_AJOUT_EVENEMENT ="+";
	/**
	 * Intitul� du bouton "ajouter".
	 */
	final String INTITULE_BOUTON_AJOUT_FRISE =" + ";
	/**
	 * Intitul� du bouton "supprimer frise".
	 */
	final String INTITULE_BOUTON_SUPRESSION_FRISE ="Supprimer une frise";
	/**
	 * Intitul� du bouton "supprimer �v�nement".
	 */
	final String INTITULE_BOUTON_SUPRESSION_EVT ="Supprimer un �v�nement";
	/**
	 * Intitul� du bouton "afficher".
	 */
	final String INTITULE_BOUTON_AFFICHAGE_FRISE ="Afficher une frise";
	
	public final String [] ECHELLE = {"Jours", "Mois", "Ann�es"};
	
	public final String [] POIDS = {"1","2","3","4"};
	
} // ConstantesCalendrier
