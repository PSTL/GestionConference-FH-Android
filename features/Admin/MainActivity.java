package fr.upmc.pstl.gestionconference;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
  
  private FragmentEcranAdmin fragmentEcranAdmin;

  private void setupFragments() {
    original();
    this.fragmentEcranAdmin = (FragmentEcranAdmin) fragmentmanager.findFragmentByTag(FragmentEcranAdmin.TAG);
    if (this.fragmentEcranAdmin == null) {
      this.fragmentEcranAdmin = new FragmentEcranAdmin();
    }
  }

  public void affichageAdmin(View v) {
    showFragment(this.fragmentEcranAdmin);
  }

}
