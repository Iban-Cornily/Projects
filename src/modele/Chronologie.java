package modele;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;

/**
 * Abstraction d'un agenda.
 * Poss�de des Evenements.
 * @author Marc BOURGEOIS et Iban CORNILY
 *
 */
public class Chronologie implements Serializable{
	/**
	 * Liste d'�venements de l'agenda.
	 */
	private ArrayList <Evenement> listeEvts;
	/**
	 * Arbre d'�venements de l'agenda.
	 */
	private TreeSet <Evenement> arbreEvts;
	private String titre;
	private Date dateDebut;
	private Date dateFin;
	private String echelle;
	private String cheminFichier;
	private int duree;
   
	/**
	 * Constructeur par d�faut de Chronologie.
	 */
	public Chronologie() {
		listeEvts = new ArrayList <> ();
		arbreEvts = new TreeSet <> ();
	}
	
	/**
	 * Constructeur de Chronologie 
	 * @param parTitre
	 * @param parDateDebut
	 * @param parDateFin
	 * @param parPeriode
	 * @param parCheminFichier
	 */
	public Chronologie(String parTitre ,Date parDateDebut, Date parDateFin, String parPeriode, String parCheminFichier) {
		listeEvts = new ArrayList <> ();
		arbreEvts = new TreeSet <> ();
		titre = parTitre;
		dateDebut = parDateDebut;
		dateFin = parDateFin;
		echelle = parPeriode;
		cheminFichier = parCheminFichier;
		
		if(echelle.equals("Ann�es")) {
			duree = dateFin.getAnnee()-dateDebut.getAnnee()+1;
		}
		else if(echelle.equals("Mois")) {
			Date courant = new Date(1, dateDebut.getMois(), dateDebut.getAnnee());
			duree = 1;
			while((courant.getMois() != dateFin.getMois()) || (courant.getAnnee() != dateFin.getAnnee())) {
				courant = courant.moisProchain();
				duree++;
			}
		}
		else if(echelle.equals("Jours")) {
			Date courant = dateDebut;
			duree = 1;
			while(courant.compareTo(dateFin) != 0) {
				courant = courant.dateDuLendemain();
				duree++;
			}
		}
	}
	
	/**
	 * 
	 * @param parTitre
	 * @param parDateDebut
	 * @param parDateFin
	 * @param parPeriode
	 * @param parCheminFichier
	 * @param parEvts TreeSet contenant les �v�nements � charger
	 */
	public Chronologie(String parTitre ,Date parDateDebut, Date parDateFin, String parPeriode, String parCheminFichier, TreeSet <Evenement> parEvts) {
		listeEvts = new ArrayList <> ();
		arbreEvts = new TreeSet <> ();
		titre = parTitre;
		dateDebut = parDateDebut;
		dateFin = parDateFin;
		echelle = parPeriode;
		cheminFichier = parCheminFichier;
		
		Iterator<Evenement> iterator = parEvts.iterator();
		Evenement evenement ;
		while(iterator.hasNext()) {
			evenement = iterator.next();
			this.ajout(evenement);
		}
		
		if(echelle.equals("Ann�es")) {
			duree = dateFin.getAnnee()-dateDebut.getAnnee()+1;
		}
		else if(echelle.equals("Mois")) {
			Date courant = new Date(1, dateDebut.getMois(), dateDebut.getAnnee());
			System.out.println(courant);
			duree = 1;
			while((courant.getMois() != dateFin.getMois()) || (courant.getAnnee() != dateFin.getAnnee())) {
				courant = courant.moisProchain();
				duree++;
			}
		}
		else if(echelle.equals("Jours")) {
			Date courant = dateDebut;
			duree = 1;
			while(courant.compareTo(dateFin) != 0) {
				courant = courant.dateDuLendemain();
				duree += 1;
			}
		}
		else
			duree = 0;
	}
  
	/**
	 * Ajoute un �venement � la chronologie.
	 * @param parEvt
	 */
  public void ajout (Evenement parEvt) {
	// Ajout dans la ArrayList
	listeEvts.add (parEvt);
	// Ajout dans le TreeSet
	arbreEvts.add (parEvt);
  }  
  
  @Override
  /**
   * Mani�re de repr�senter l'agenda en cha�ne de caract�re.
   */
  public String toString () {
    String chaine = "\n" + listeEvts + "\n" + arbreEvts + "\n";
	return chaine;
  }

  /** Retourne le nombre d'�v�nements de l'agenda qui ont lieu � la date parDate.
    **/
  	public int compteNbEvt (Date parDate) { 
  		int nbEvt = 0;
  		Iterator <Evenement> iterateur = arbreEvts.iterator();
  		while (iterateur.hasNext()) {
  			Evenement evt = iterateur.next();
  			if (evt.getDate().compareTo (parDate) == 0)
				nbEvt++;
		}
	return nbEvt;
  } // compteNbEvt
  
  /** Retourne le nombre d'�v�nements de l'agenda dont le titre contient la cha�ne de caracat�res parString.
    **/
  public int compteNbEvt (String parString) {
    int nbEvt = 0;
    for (Evenement evt : listeEvts) {
		if (evt.getNom().contains (parString)) {
					nbEvt++;         
		}
	}	 	
	return nbEvt;
  } // compteNbEvt
  
   
  /**
   * Trie avec la m�thode du "tri fusion".
   */
  public void triFusion(){
	triFusion(listeEvts, 0,listeEvts.size());			  
  }
  
  /**
   * Trie avec la m�thode du "tri fusion".
   * @param list
   * @param indiceDebut
   * @param longueur
   * @return
   */
  public ArrayList <Evenement>  triFusion (ArrayList <Evenement>  list, int indiceDebut, int longueur) {
	if (longueur == 1){
		  
		 ArrayList <Evenement> arrayList =new ArrayList <Evenement> ();
		 arrayList.add(list.get(indiceDebut));
		 return arrayList;
		
	}
	else 
			return fusion (triFusion(list,indiceDebut ,longueur/2),
			        triFusion (list, indiceDebut + longueur/2, longueur-longueur/2)
					);
				
		 
  }
  /**
   * Fusionne pour pr�parer le "tri fusion".
   * @param liste1
   * @param liste2
   * @return
   */
  public ArrayList <Evenement> fusion (ArrayList <Evenement> liste1, ArrayList <Evenement> liste2){
  	if (liste1.isEmpty()) {
               return liste2;
	}
    if (liste2.isEmpty()) {
               return liste1;
	}
	ArrayList <Evenement> arrayList =new ArrayList <Evenement> (); 
	Evenement premierDeListe1 = liste1.get(0);
	Evenement premierDeListe2 = liste2.get(0);
    if (premierDeListe1.compareTo(premierDeListe2) <= 0) {
			liste1.remove(0);			
            arrayList = fusion(liste1,liste2);
			arrayList.add(0,premierDeListe1);
			
	}
	else {  liste2.remove(0);
            arrayList = fusion(liste1,liste2);
			arrayList.add(0,premierDeListe2);
			
			}
	return arrayList;		  
    }

	/**
	 * Donne les �venements de la chronologie.
	 * @return
	 */
	public TreeSet <Evenement>getEvenements() { 
		return arbreEvts;
	}
	
	/**
	 * Donne l'�chelle de la chronologie.
	 * @return
	 */
	public String getEchelle() {
		return echelle;
	}
	
	/**
	 * Donne la date de d�but de la chronologie.
	 * @return
	 */
	public Date getDateDebut() {
		return dateDebut;
	}
	
	/**
	 * Donne la date de fin de la chronologie.
	 * @return
	 */
	public Date getDateFin() {
		return dateFin;
	}
	
	/**
	 * Donne la dur�e de la chronologie.
	 * @return
	 */
	public int getDuree() {
		return duree;
	}
	
	/**
	 * Donne le titre de la chronologie.
	 * @return
	 */
	public String getTitre() {
		return titre;
	}
	
	/**
	 * Donne la colonne de l'�v�nement.
	 * @return
	 */
	public int getColEvt(Evenement e) {
		int colEvt = 0;
		if(echelle.equals("Ann�es")) {
			int annee = dateDebut.getAnnee();
			while(annee != e.getDate().getAnnee()) {
				annee++;
				colEvt++;
			}
		}
		else if(echelle.equals("Mois")) {
			Date courant = new Date(1, dateDebut.getMois(), dateDebut.getAnnee());
			while(courant.getMois() != e.getDate().getMois() && courant.getAnnee() != e.getDate().getAnnee()) {
				courant = courant.moisProchain();
				colEvt++;
			}
		}
		else if(echelle.equals("Jours")) {
			Date current = dateDebut;
			while(current.compareTo(e.getDate()) != 0) {
				current = current.dateDuLendemain();
				colEvt++;
			}
		}
		return colEvt;
	}
	
	/**
	 * donne le chemin en m�moire du fichier de sauvegarde de la chronologie.
	 * @return
	 */
	public String getCheminFichier(){
		return cheminFichier;
	}
	
	/**
	 * regarde si le nom existe en double dans les �v�nements
	 * @param parNom nom � tester
	 * @return
	 */
	public boolean checkNomDoublon(String parNom) {
		for(Evenement e : arbreEvts) {
			if(e.getNom().equals(parNom))
				return true;
		}
		return false;
	}

	/**
	 * Check les �v�nements pour ne pas superposer les �v�nements dans la JTable
	 * @param parDate date � laquelle il faut tester
	 * @param parPoids poids auquel il faut tester
	 * @return 
	 */
	public boolean checkEmplacementVide(Date parDate, int parPoids) {
		if(echelle.equals("Ann�es")) {
			for(Evenement e : arbreEvts) {
				if(e.getDate().getAnnee() == parDate.getAnnee() && e.getPoids() == parPoids)
					return false;
			}
		}
		else if(echelle.equals("Mois")) {
			for(Evenement e : arbreEvts) {
				if(e.getDate().getMois() == parDate.getMois() && e.getDate().getAnnee() == parDate.getAnnee() && e.getPoids() == parPoids)
					return false;
			}
		}
		else if(echelle.equals("Jours")) {
			for(Evenement e : arbreEvts) {
				if((e.getDate().compareTo(parDate) == 0) && (e.getPoids() == parPoids))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * donne les noms d'�v�nements pour la suppression
	 * @return
	 */
	public String [] getNomsEvenementsSuppression () {
		String [] resultat = new String [arbreEvts.size()];
		int i = 0;
		String titre;
		for (Evenement e : arbreEvts) {
			titre = e.getNom();
			resultat[i] = titre;
			i++;
		}
		return resultat;
	}
	
	/**
	 * retourne un �v�nement
	 * @param nomEvt nom de l'�v�nement recherch�
	 * @return l'�v�nement s'il existe, null sinon
	 */
	public Evenement getEvenement(String nomEvt) {
		for(Evenement evt : arbreEvts) {
			if(nomEvt.equals(evt.getNom()))
				return evt;
		}
		return null;
	}
	
	/**
	 * suppression d'un �v�nement
	 * @param evt �v�nement � supprimer
	 */
	public void remove(Evenement evt) {
		listeEvts.remove(evt);
		arbreEvts.remove(evt);
	}
	
	/**
	 * permet de d�finir le chemin du fichier de sauvegarde d'une frise
	 * @param parCheminFichier
	 */
	public void setCheminFichier(String parCheminFichier) {
		cheminFichier = parCheminFichier;
	}
}