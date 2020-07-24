package modele;

import java.io.Serializable;

/**
 * Abstraction d'un �venement qui comporte une Date, un nom, un lieu.
 * @author Marc BOURGEOIS et Iban CORNILY
 *
 */
public class Evenement implements Comparable <Evenement> , Serializable{

	/**
	 * Date de l'�venement.
	 */
	private Date date;
	/**
	 * Nom de l'�venement.
	 */
	private String nom;
	/**
	 * Lieu de l'�venement.
	 */
	private String description;
	/**
	 * Importance de l'�v�nement de 1 � 4.
	 */
	private int poids;
	/**
	 * 
	 */
	private String cheminImage;
	/**
	 * Nombre d'�venement instanci�s.
	 * Commum pour tout les �venements.
	 */
	private static int nombreDobjets ; // les champs entiers sont initialis�s par d�faut � 0
				// on peut quand m�me faire l'initialisation ici avec la d�claration

	/**
	 * Constructeur par d�faut d'Evenement.
	 */
	public Evenement (){ 
    	nombreDobjets ++;
	}
	
	/**
	 * Constructeur d'�v�nement avec poids pr�d�fini
	 * @param parDate
	 * @param parNom
	 * @param parDescription
	 * @param parCheminImage
	 */
	public Evenement (Date parDate, String parNom , String parDescription, String parCheminImage) {
        date = parDate;
        nom = parNom;
        description = parDescription;
        poids = 1;
        nombreDobjets ++;
        cheminImage = parCheminImage;
    }
	
	/**
	 * Constructeur d'Evenement
	 * @param parDate
	 * @param parNom
	 * @param parDescription
	 * @param parPoids
	 * @param parCheminImage
	 */
	public Evenement (Date parDate, String parNom , String parDescription, int parPoids, String parCheminImage) {
		date = parDate;
		nom = parNom;
		description = parDescription;
		poids = parPoids;
		cheminImage = parCheminImage;
		nombreDobjets ++;     
		cheminImage = parCheminImage;
	}
  
  
  // 3) Accesseurs et modifieurs
  
	/**
	 * Retourne la date de l'�venement.
	 * @return
	 */
	public Date getDate ()  {
		return date;
	}
	
	/**
	 * Retourne le nom de l'�venement.
	 * @return
	 */
	public String getNom () {	
		return nom;
	}
	
	/**
	 * Retourne la description de l'�v�nement.
	 * @return
	 */
	public String getDescription() {	
		return description;
	}
	
	/**
	 * Retourne le nombre d'objets instanci�s.
	 * @return
	 */
	public static int getNombreDobjets () { // accesseur sur un champ static
		return nombreDobjets;
	}
  
	/**
	 * D�fini la description de l'�v�nnement.
	 * @param parDescription
	 */
	public void setDescription (String parDescriptionn) {
		description = parDescriptionn;
	}

	/**
	  * Compare l'�venement � un autre pour indiquer si il est plus vieux ou plus r�cent.
	  * si precede = -1: this pr�c�de parEvt
	  * si precede = 1: parEvt pr�c�de this
	  * si precede = 0: les �venements sont similaire en tout points
	  */
	public int compareTo (Evenement parEvt) {
		int precede = this.date.compareTo(parEvt.date);
		if (precede == 0)
			// les dates sont �gales on compare les noms des �v�nements par ordre alphab�tique
			return  (this.nom+this.description).compareTo (parEvt.nom+parEvt.description);		
		else return precede;
	} 
	
	/**
	 * Mani�re de repr�senter un �venement en cha�ne de caract�re.
	 */
	public String toString () {
		return date + " - " + nom + " - " + description ;
	}

	public int getPoids() {
		return poids;
	}
		
	public String getCheminImage() {
		return cheminImage;
	}
} // classe Evenement