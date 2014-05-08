package fr.upmc.pstl.gestionconference; 

import android.content.Intent; 
import android.os.Bundle; 
import android.support.v4.app.Fragment; 
import android.view.LayoutInflater; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.Button; 

public   class  FragmentEcranAdmin  extends Fragment {
	
  public final static String TAG = "fragmentEcranAdmin";

	
  private View myFragmentView;

	
  private Button boutonNouvelleConference;

	

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    myFragmentView = inflater.inflate(R.layout.layout_ecran_admin, container, false);
    ajouterListenerBoutons();

    return myFragmentView;
  }

	

   private void  ajouterListenerBoutons__wrappee__Admin  () {
    boutonNouvelleConference = (Button) myFragmentView.findViewById(R.id.boutonNouvelleConference);
  }

	

  public void ajouterListenerBoutons() {
    ajouterListenerBoutons__wrappee__Admin();
    boutonNouvelleConference.setOnTouchListener(new CreateConferencenClickListener());
  }

	

  public  class  CreateConferencenClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      int action = event.getActionMasked();

      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      }
      if (action == MotionEvent.ACTION_UP) {
        v.setBackgroundResource(R.drawable.rounded_button);

        if (v == (Button) myFragmentView.findViewById(R.id.boutonNouvelleConference)) {

          Intent intent = new Intent(getActivity(), NouvelleConferenceActivity.class);
          startActivity(intent);
        }
      }

      return true;
    }


	}


}
