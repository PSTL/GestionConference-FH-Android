package fr.upmc.pstl.gestionconference; 

import java.util.ArrayList; 
import java.util.List; 

public  class  MyPresentation {
	

  String name;

	
  String description;

	

  public static List<MyPresentation> getDataForListView() {
    List<MyPresentation> presentations = new ArrayList<MyPresentation>();

    for (int i = 1; i <= 5; i++) {

      MyPresentation presentation = new MyPresentation();
      presentation.name = "Presentation " + i;
      presentation.description = "This is description for presentation " + i;
      presentations.add(presentation);
    }

    return presentations;

  }


}
