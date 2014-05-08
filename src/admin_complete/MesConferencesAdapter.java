package fr.upmc.pstl.gestionconference; 

import java.io.File; 
import java.io.FilenameFilter; 
import java.util.ArrayList; 
import java.util.List; 

import android.content.Context; 
import android.os.Environment; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.TextView; 


public  class  MesConferencesAdapter  extends BaseAdapter {
	

  List<Conference> list = new ArrayList<Conference>();

	
  private final LayoutInflater inflater;

	

  private final int MAX_DESCRIPTION_LENGTH = 35;

	

  public MesConferencesAdapter(Context context) {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    File folder =
        new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/dossierConferences/");
    FilenameFilter filter = new FilenameFilter() {

      @Override
      public boolean accept(File dir, String filename) {
        if (filename.lastIndexOf('.') > 0) {
          // get last index for '.' char
          int lastIndex = filename.lastIndexOf('.');

          // get extension
          String str = filename.substring(lastIndex);

          // match path name extension
          if (str.equals(".xml")) {
            return true;
          }
        }
        return false;
      }
    };

    File[] xmlfiles = folder.listFiles(filter);

    for (File file : xmlfiles) {
      if (file.isFile()) {
        Conference conference = XmlFileManipulator.readConferenceFromFile(file.getName());
        List<Presentation> presentations =
            XmlFileManipulator.readPresentationsFromFile(file.getName());
        conference.setPresentations(presentations);
        list.add(conference);
      }
    }

  }

	

  @Override
  public int getCount() {
    return list.size();
  }

	

  @Override
  public Object getItem(int arg0) {
    return list.get(arg0);
  }

	

  @Override
  public long getItemId(int arg0) {
    return arg0;
  }

	

  @Override
  public View getView(int arg0, View arg1, ViewGroup arg2) {
    if (arg1 == null) {
      arg1 = inflater.inflate(R.layout.mesconf_fragment, arg2, false);
    }

    TextView title = (TextView) arg1.findViewById(R.id.tvTitle);
    TextView description = (TextView) arg1.findViewById(R.id.tvDescription);
    Conference conference = list.get(arg0);

    title.setText(conference.getNom());
    int length = conference.getDescription().length();

    if (length < MAX_DESCRIPTION_LENGTH) {
      description.setText(conference.getDescription());
    } else {
      description.setText(conference.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) + "...");
    }

    return arg1;
  }


}
