package fr.upmc.pstl.gestionconference;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentEcranAdmin extends Fragment {
  public final static String TAG = "fragmentEcranAdmin";
  private View myFragmentView;
  private Button boutonNouvelleConference;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    myFragmentView = inflater.inflate(R.layout.layout_ecran_admin, container, false);
    ajouterListenerBoutons();

    return myFragmentView;
  }

  public void ajouterListenerBoutons() {
    boutonNouvelleConference = (Button) myFragmentView.findViewById(R.id.boutonNouvelleConference);
  }

}
