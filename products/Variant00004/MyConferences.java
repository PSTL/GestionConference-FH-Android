package fr.upmc.pstl.gestionconference; 

import java.util.ArrayList; 
import java.util.List; 

public  class  MyConferences {
	
  String conferenceName;

	
  String conferenceDescription;

	

  public static List<MyConferences> getDataForListView() {
    List<MyConferences> codeLeanChaptersList = new ArrayList<MyConferences>();

    for (int i = 1; i <= 10; i++) {

      MyConferences chapter = new MyConferences();
      chapter.conferenceName = "Conference " + i;
      chapter.conferenceDescription = "This is description for conference " + i;
      codeLeanChaptersList.add(chapter);
    }

    return codeLeanChaptersList;

  }


}
