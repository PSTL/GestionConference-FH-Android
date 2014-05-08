package fr.upmc.pstl.gestionconference; 

import fr.upmc.pstl.gestionconference.R; 

import android.content.Intent; 
import android.os.Bundle; 
import android.support.v4.app.Fragment; 
import android.view.LayoutInflater; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.Button; 
import android.widget.Toast; 

public   class  FragmentEcranUtilisateur  extends Fragment {
	

  public final static String TAG = "fragmentEcranUtilisateur";

	
  private String NOT_AVAILABLE = "";

	

  private Button boutonCalendrier, boutonMesConferences, boutonConferenceProche, boutonOptions,
      boutonAdmin;

	
  private View myFragmentView;

	

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    myFragmentView = inflater.inflate(R.layout.layout_ecran_utilisateur, container, false);
    boutonCalendrier = (Button) myFragmentView.findViewById(R.id.boutonCalendrier);
    boutonAdmin = (Button) myFragmentView.findViewById(R.id.boutonModeAdmin);

    NOT_AVAILABLE = getResources().getString(R.string.notimplemented);
    ajouterListenerBoutons();
    return myFragmentView;
  }

	

  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

	

   private void  ajouterListenerBoutons__wrappee__Base  () {
    boutonAdmin.setVisibility(View.GONE);

    // Selection de tous les boutons
    boutonCalendrier.setOnTouchListener(new NotAvailableClickListener());

    boutonMesConferences = (Button) myFragmentView.findViewById(R.id.boutonMesConferences);
    boutonMesConferences.setOnTouchListener(new MonBoutonClickListener());

    boutonConferenceProche = (Button) myFragmentView.findViewById(R.id.boutonConferenceProche);
    boutonConferenceProche.setOnTouchListener(new MonBoutonClickListener());

    boutonOptions = (Button) myFragmentView.findViewById(R.id.boutonOptions);
    boutonOptions.setOnTouchListener(new NotAvailableClickListener());
  }

	

   private void  ajouterListenerBoutons__wrappee__Languages  () {
    ajouterListenerBoutons__wrappee__Base();

    boutonOptions.setOnTouchListener(new OptionClickListener());

  }

	


   private void  ajouterListenerBoutons__wrappee__Admin  () {
    ajouterListenerBoutons__wrappee__Languages();
    boutonAdmin.setVisibility(View.VISIBLE);
  }

	

  public void ajouterListenerBoutons() {
    ajouterListenerBoutons__wrappee__Admin();

    boutonCalendrier.setOnTouchListener(new CalendarClickListener());
  }

	

  public  class  MonBoutonClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      int action = event.getActionMasked();

      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      }
      if (action == MotionEvent.ACTION_UP) {
        v.setBackgroundResource(R.drawable.rounded_button);

        if (v == (Button) myFragmentView.findViewById(R.id.boutonMesConferences)) {
          Intent intent = new Intent(getActivity(), MesConferencesActivity.class);
          startActivity(intent);
        }
      }

      return true;
    }


	}

	

  public  class  NotAvailableClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      int action = event.getActionMasked();

      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      } else if (action == MotionEvent.ACTION_UP) {
        v.setBackgroundResource(R.drawable.rounded_button);
        Toast.makeText(getActivity(), NOT_AVAILABLE, Toast.LENGTH_SHORT).show();
      }

      return true;
    }


	}

	

  public  class  OptionClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      int action = event.getActionMasked();

      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      }
      if (action == MotionEvent.ACTION_UP) {
        v.setBackgroundResource(R.drawable.rounded_button);

        if (v == (Button) myFragmentView.findViewById(R.id.boutonOptions)) {
          Intent intent = new Intent(getActivity(), Option.class);
          startActivity(intent);
        }
      }

      return true;
    }


	}

	

  public  class  CalendarClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {

      int action = event.getActionMasked();

      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      }
      
      if (action == MotionEvent.ACTION_UP) {
        v.setBackgroundResource(R.drawable.rounded_button);

        Intent intent = new Intent(getActivity(), CalendrierActivity.class);
        startActivity(intent);
      }

      return true;
    }


	}


}
