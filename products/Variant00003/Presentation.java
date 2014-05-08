package fr.upmc.pstl.gestionconference;  

import java.io.Serializable; 
import java.util.Calendar; 

public  class  Presentation  implements Serializable {
	

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

	
  private String auteur;

	
  private Calendar dateDebut;

	
  private Calendar dateFin;

	
  private String lieu;

	

  private String programme;

	

  public Presentation(String prog, String auteur, String lieu, Calendar dd, Calendar df) {
    this.programme = prog;
    this.auteur = auteur;
    this.lieu = lieu;
    dateDebut = dd;
    dateFin = df;
  }

	

  public Presentation() {
  }

	

  public String getAuteur() {
    return auteur;
  }

	

  public String getLieu() {
    return lieu;
  }

	

  public Calendar getDateDebut() {
    return dateDebut;
  }

	

  public Calendar getDateFin() {
    return dateFin;
  }

	

  public String getProgramme() {
    return programme;
  }

	

  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

	

  public void setDateDebut(Calendar dateDebut) {
    this.dateDebut = dateDebut;
  }

	

  public void setDateFin(Calendar dateFin) {
    this.dateFin = dateFin;
  }

	

  public void setLieu(String lieu) {
    this.lieu = lieu;
  }

	

  public void setProgramme(String programme) {
    this.programme = programme;
  }


}
