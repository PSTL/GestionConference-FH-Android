package fr.upmc.pstl.gestionconference;  

import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.Calendar; 
import java.util.List; 

public  class  Conference  implements Serializable {
	

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

	
  private String nom;

	
  private Calendar dateDebut;

	
  private Calendar dateFin;

	
  String description;

	
  List<Presentation> presentations = new ArrayList<Presentation>();

	
  String lieuConference;

	

  public Conference() {
  }

	

  public Conference(String name, String lieu, String descr, Calendar dd, Calendar df,
      List<Presentation> presentations) {
    this.nom = name;
    this.lieuConference = lieu;
    this.description = descr;
    this.dateDebut = dd;
    this.dateFin = df;
    this.presentations = presentations;
  }

	

  public void addPresentation(Presentation presentation) {
    presentations.add(presentation);
  }

	

  public String getNom() {
    return nom;
  }

	

  public Calendar getDateDebut() {
    return dateDebut;
  }

	

  public Calendar getDateFin() {
    return dateFin;
  }

	

  public String getDescription() {
    return description;
  }

	

  public List<Presentation> getPresentations() {
    return presentations;
  }

	

  public String getLieuConference() {
    return lieuConference;
  }

	

  public void setNom(String nom) {
    this.nom = nom;
  }

	

  public void setDateDebut(Calendar dateDebut) {
    this.dateDebut = dateDebut;
  }

	

  public void setDateFin(Calendar dateFin) {
    this.dateFin = dateFin;
  }

	

  public void setDescription(String description) {
    this.description = description;
  }

	

  public void setPresentations(List<Presentation> presentations) {
    this.presentations = presentations;
  }

	

  public void setLieuConference(String lieuConference) {
    this.lieuConference = lieuConference;
  }

	

  public String toString() {
    return "nom de la conf : " + nom + " qui se déroule à " + lieuConference;
  }


}
