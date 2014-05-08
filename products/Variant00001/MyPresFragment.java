package fr.upmc.pstl.gestionconference; 

import java.text.SimpleDateFormat; 
import java.util.Calendar; 

import android.content.Intent; 
import android.os.Bundle; 
import android.support.v4.app.ListFragment; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.ListView; 
import android.widget.TextView; 


public  class  MyPresFragment  extends ListFragment {
	

  private MyPresAdapter adapter;

	
  private TextView title, description, startDate, endDate, location;

	
  private Conference conference;

	
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.list_view_presentation, container, false);

    title = (TextView) view.findViewById(R.id.tvMesconf);
    description = (TextView) view.findViewById(R.id.tvConfDesc);
    startDate = (TextView) view.findViewById(R.id.tvConfDatedebval);
    endDate = (TextView) view.findViewById(R.id.tvConfDatefinval);
    location = (TextView) view.findViewById(R.id.tvConfLocationval);

    return view;
  }

	

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // get conference object from previous activity
    Intent intent = getActivity().getIntent();
    conference = (Conference) intent.getSerializableExtra("ConferenceObject");

    // we have to get the name of the conference
    // set activity title - which is the conference title
    title.setText(conference.getNom());

    // set activity description - which is the conference description
    description.setText(conference.getDescription());

    // dates
    Calendar calendar = conference.getDateDebut();

    startDate.setText(dateFormat.format(calendar.getTime()));

    calendar = conference.getDateFin();
    endDate.setText(dateFormat.format(calendar.getTime()));

    location.setText(conference.getLieuConference());

    adapter = new MyPresAdapter(getActivity());
    adapter.setPresentations(conference.getPresentations());
    setListAdapter(adapter);
  }

	

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Presentation presentation = (Presentation) adapter.getItem(position);

    // Open an activity with the presentation values
    Intent intent = new Intent(getActivity(), PresentationView.class);
    intent.putExtra("PresentationObject", presentation);

    startActivity(intent);
  }


}
