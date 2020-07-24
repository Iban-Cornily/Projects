package modele;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import vue.ConstantesCalendrier;

/**
 * Abstraction d'un tableau pour afficher les �v�nements de la frise � afficher.
 * @author Marc BOURGEOIS et Iban CORNILY
 *
 */
public class ModeleTable extends DefaultTableModel{
	
	String [] colId;
	int duree;
	/**
	 * Constructeur par d�faut de ModeleTable.
	 * Permet la formation des colonnes (nb de cases, intitul�s) et ajoute les �v�nements dans les bonnes cases
	 * @param frise Frise � afficher.
	 */
	public ModeleTable (Chronologie frise) {
		Date date = frise.getDateDebut();
		duree = frise.getDuree();
		
		// formation des colonnes pour l'�chelle en ann�es
		if(frise.getEchelle().equals("Ann�es")) {
			colId = new String[duree];
			int annee = date.getAnnee();
			
			for (int i = 0; i < duree; i++) {
				colId[i] = Integer.toString(annee);
				annee++;
			}
			setColumnCount(duree);
			setRowCount(4);
		}
		
		// formation des colonnes pour l'�chelle en mois
		else if(frise.getEchelle().equals("Mois")) {
			colId = new String[duree];
			for (int i = 0; i < duree; i++) {
				colId[i] = ConstantesCalendrier.MOIS_ULTRA_RAC[date.getMois()-1] + "." + date.getAnnee();
				date = date.moisProchain();
			}
			setColumnCount(duree);
			setRowCount(4);
		}
		
		// formation des colonnes pour l'�chelle en jours 
		else if(frise.getEchelle().equals("Jours")) {
			colId = new String[duree];
			
			for (int i = 0; i < duree; i++) {
				colId[i] = Integer.toString(date.getJour()) + " " +ConstantesCalendrier.MOIS_RAC[date.getMois()-1];
				date = date.dateDuLendemain();
			}
			setColumnCount(duree);
			setRowCount(4);
		}
		//pour la frise vide (lorsqu'il ny a plus de frise � afficher, une frise sans �chelle est cr��e)
		else {}
			
		
		Collection <Evenement> evts = frise.getEvenements();
		if (evts != null) {
			for (Evenement evt : frise.getEvenements()) {
				ajoutEvenement(evt, frise);
			}
		}
	} // ModelTable()
	
	/**
	 * Ajoute tout les �v�nements de la frise � la table dans les bonnnes cases.
	 * @param evt
	 */
	public void ajoutEvenement (Evenement evt, Chronologie frise) {
		
		// �chelle ann�es
		if(frise.getEchelle().equals("Ann�es")) {
			int indiceColonne = 0;
			int annee = frise.getDateDebut().getAnnee();
			Iterator<Evenement> iterator = frise.getEvenements().iterator();
			Evenement evenement = iterator.next();
			int oneMoreLoop = 0;
			
			while(annee <= frise.getDateFin().getAnnee()) {
				if(oneMoreLoop < 2 && annee == evenement.getDate().getAnnee()) {
					setValueAt(evenement.getCheminImage().toString(),4-evenement.getPoids(),indiceColonne);
					if(iterator.hasNext())
						evenement = iterator.next();
					else if(oneMoreLoop == 0)
						oneMoreLoop = 1;
					else
						oneMoreLoop = 2;
				}
				else {
					annee++;
					indiceColonne += 1;
				}
			}
		}
		
		// �chelle mois
		else if(frise.getEchelle().equals("Mois")) {
			int indiceColonne = 0;
			Date courante = new Date(frise.getDateDebut().getJour(), frise.getDateDebut().getMois(), frise.getDateDebut().getAnnee());
			Iterator<Evenement> iterator = frise.getEvenements().iterator();
			Evenement evenement = iterator.next();
			int oneMoreLoop = 0;
			
			while(courante.getMois() <= frise.getDateFin().getMois() || courante.getAnnee() <= frise.getDateFin().getAnnee() ) {
				if(oneMoreLoop < 2 && courante.getMois() == evenement.getDate().getMois() && courante.getAnnee() == evenement.getDate().getAnnee()) {
					setValueAt(evenement.getCheminImage().toString(),4-evenement.getPoids(),indiceColonne);
					if(iterator.hasNext())
						evenement = iterator.next();
					else if(oneMoreLoop == 0)
						oneMoreLoop = 1;
					else
						oneMoreLoop = 2;
				}
				else {
					courante = courante.moisProchain();
					System.out.println(courante);
					indiceColonne += 1;
				}
			}
		}
		
		// �chelle jours
		else if(frise.getEchelle().equals("Jours")) {   
			int indiceColonne = 0;
			Date courante = new Date(frise.getDateDebut().getJour(), frise.getDateDebut().getMois(), frise.getDateDebut().getAnnee());
			Iterator<Evenement> iterator = frise.getEvenements().iterator();
			Evenement evenement = iterator.next();
			int oneMoreLoop = 0;
			
			while(courante.compareTo(frise.getDateFin()) <= 0) {
				if(oneMoreLoop < 2 && courante.compareTo(evenement.getDate()) == 0) {
					setValueAt(evenement.getCheminImage().toString(),4-evenement.getPoids(),indiceColonne);
					if(iterator.hasNext())
						evenement = iterator.next();
					else if(oneMoreLoop == 0)
						oneMoreLoop = 1;
					else
						oneMoreLoop = 2;
				}
				else {
					courante = courante.dateDuLendemain();
					indiceColonne += 1;
				}
			}
		}
	} // ajoutEvenement()
	
	/**
	 * Emp�che que l'utilisateur puisse modifier les cases du tableau manuellement.
	 */
	public boolean isCellEditable (int indiceLigne, int indiceColonne) {
		return false;
	} // isCellEditable()
	
	/**
	 * Retourne le nom de la colonne pour un indice donn�.
	 */
	public String getColumnName(int index) { 
        return colId[index]; 
    } 
} // class ModeleTable
