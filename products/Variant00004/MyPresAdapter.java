package fr.upmc.pstl.gestionconference; 

import java.util.ArrayList; 
import java.util.List; 

import android.content.Context; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.TextView; 


public  class  MyPresAdapter  extends BaseAdapter {
	

  List<Presentation> list = new ArrayList<Presentation>();

	
  private final LayoutInflater inflater;

	

  public MyPresAdapter(Context context) {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
      arg1 = inflater.inflate(R.layout.presentation_fragment, arg2, false);
    }

    TextView title = (TextView) arg1.findViewById(R.id.tvPresentationTitle);
    TextView description = (TextView) arg1.findViewById(R.id.tvPresentationDescription);
    Presentation presentation = list.get(arg0);

    title.setText(presentation.getProgramme());
    description.setText(presentation.getAuteur());

    return arg1;
  }

	

  public void setPresentations(List<Presentation> presentations) {
    this.list = presentations;
  }


}
