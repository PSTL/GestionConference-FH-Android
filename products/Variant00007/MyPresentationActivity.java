package fr.upmc.pstl.gestionconference; 

import android.os.Bundle; 
import android.support.v4.app.FragmentActivity; 
import android.support.v4.app.FragmentManager; 
import android.support.v4.app.FragmentTransaction; 

public  class  MyPresentationActivity  extends FragmentActivity {
	

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    ft.replace(R.id.frame, new MyPresFragment());

    ft.addToBackStack(null);
    ft.commit();
  }


}
