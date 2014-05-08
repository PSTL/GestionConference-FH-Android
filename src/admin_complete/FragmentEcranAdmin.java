package fr.upmc.pstl.gestionconference; 

import android.content.Intent; 
import android.os.Bundle; 
import android.support.v4.app.Fragment; 
import android.view.LayoutInflater; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.Button; 

import android.app.Dialog; 
import android.widget.RadioButton; 
import android.widget.RadioGroup; 

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

	


  public   class  CreateConferencenClickListener  implements View.OnTouchListener {
		

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      int action = event.getActionMasked();
      if (action == MotionEvent.ACTION_DOWN) {
        v.setBackgroundResource(R.drawable.rounded_button_clicked);

      }
      if (action == MotionEvent.ACTION_UP) {
        // CREATION DE LA BOITE DE DIALOGUE
        dialogueChoixImport = new Dialog(getActivity(), R.style.AlertDialogCustom);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewChoix = inflater.inflate(R.layout.layout_dialogue_choix_manuel_import, null);
        dialogueChoixImport.setContentView(viewChoix);

        // AJOUTER BOUTON IMPORT
        ajouterBoutonImport();

        dialogueChoixImport.show();

        // AJOUTER LISTENER AU BOUTON DU DIALOGUE
        Button boutonValider =
            (Button) dialogueChoixImport.findViewById(R.id.boutonValiderChoixManuel);
        boutonValider.setOnClickListener(new View.OnClickListener() {

          public void onClick(View v) {
            RadioButton radioBoutonManuel =
                (RadioButton) dialogueChoixImport.findViewById(R.id.radioButtonManuel);
            if (radioBoutonManuel.isChecked()) {
              dialogueChoixImport.dismiss();
              Intent intent = new Intent(getActivity(), NouvelleConferenceActivity.class);
              getActivity().startActivity(intent);

            } else {
              dialogueChoixImport.dismiss();
              Intent intent = new Intent(getActivity(), ImportActivity.class);
              getActivity().startActivity(intent);
            }
          }
        });

      }

      return true;
    }


	}

	

  private Dialog dialogueChoixImport;

	

  public void ajouterBoutonImport() {
    RadioButton radioBouton = new RadioButton(getActivity());
    radioBouton.setText("Import ");
    radioBouton.setId(2);

    RadioGroup radioGroup = (RadioGroup) dialogueChoixImport.findViewById(R.id.radioGroupChoix);
    radioGroup.addView(radioBouton);

  }


}
