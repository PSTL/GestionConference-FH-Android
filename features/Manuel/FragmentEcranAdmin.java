package fr.upmc.pstl.gestionconference;

public class FragmentEcranAdmin extends Fragment {

  public void ajouterListenerBoutons() {
    original();
    boutonNouvelleConference.setOnTouchListener(new CreateConferencenClickListener());
  }

  public class CreateConferencenClickListener implements View.OnTouchListener {

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
