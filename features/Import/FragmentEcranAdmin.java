package fr.upmc.pstl.gestionconference;

import android.app.Dialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FragmentEcranAdmin extends Fragment {

  private Dialog dialogueChoixImport;

  public void ajouterBoutonImport() {
    RadioButton radioBouton = new RadioButton(getActivity());
    radioBouton.setText("Import ");
    radioBouton.setId(2);

    RadioGroup radioGroup = (RadioGroup) dialogueChoixImport.findViewById(R.id.radioGroupChoix);
    radioGroup.addView(radioBouton);

  }


  public class CreateConferencenClickListener implements View.OnTouchListener {

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
}
