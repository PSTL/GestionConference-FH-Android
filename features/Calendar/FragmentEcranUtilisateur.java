package fr.upmc.pstl.gestionconference;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentEcranUtilisateur extends Fragment {

  public void ajouterListenerBoutons() {
    original();

    boutonCalendrier.setOnTouchListener(new CalendarClickListener());
  }

  public class CalendarClickListener implements View.OnTouchListener {

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
