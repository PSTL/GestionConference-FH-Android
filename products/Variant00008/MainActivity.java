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

public  class  MainActivity  extends FragmentActivity {
	
  private String mFragment;

	
  private FragmentEcranUtilisateur fragmentEcranUtilisateur;

	
  public final FragmentManager fragmentmanager = getSupportFragmentManager();

	

  private String accountName;

	
  public static boolean calenderCreated = false;

	

  public static final String MyPREFERENCES = "MyPrefs";

	

  public static SharedPreferences sharedpreferences;

	

  private Editor editor;

	
  private boolean firstTime;

	
  private long calid;

	

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupFragments();
    System.setProperty("org.joda.time.DateTimeZone.Provider",
        "com.example.objetConference.FastDateTimeZoneProvider");

    accountName = getAccountName();

    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    firstTime = sharedpreferences.getBoolean("First Time", true);

    calid = calenderId();

    if (firstTime || (calid == -1)) {
      editor = sharedpreferences.edit();
      editor.putBoolean("First Time", false);
      // Toast.makeText(this, "first time", Toast.LENGTH_SHORT).show();

      calid = calenderId();
      // Toast.makeText(this, " " + calid, Toast.LENGTH_SHORT).show();

      if (calid == -1) { // if shared prefs holds no calender values check the phone
        // Toast.makeText(this, "entered  if", Toast.LENGTH_SHORT).show();
        calid = createCalendar();
      }

      // Toast.makeText(this, "calid : " + calid, Toast.LENGTH_SHORT).show();
      editor.putBoolean("ConferenceCalendar", true);
      editor.putLong("CalendarID", calid);
      editor.commit();

      long id = sharedpreferences.getLong("CalendarID", -1);
      boolean b = sharedpreferences.getBoolean("ConferenceCalendar", false);
      // Toast.makeText(this, id + ":" + b, Toast.LENGTH_LONG).show();
    } else {
      // Toast.makeText(this, "not first time", Toast.LENGTH_SHORT).show();
      long id = sharedpreferences.getLong("CalendarID", -1);
      boolean b = sharedpreferences.getBoolean("ConferenceCalendar", false);
      // Toast.makeText(this, id + ":" + b, Toast.LENGTH_LONG).show();
    }

  }

	

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

	

  private void setupFragments() {
    // final FragmentManager fm = getSupportFragmentManager();

    this.fragmentEcranUtilisateur =
        (FragmentEcranUtilisateur) fragmentmanager.findFragmentByTag(FragmentEcranUtilisateur.TAG);

    if (this.fragmentEcranUtilisateur == null) {
      this.fragmentEcranUtilisateur = new FragmentEcranUtilisateur();
    }

    showFragment(this.fragmentEcranUtilisateur);

  }

	

  public void showFragment(final Fragment fragment) {
    if (fragment == null)
      return;

    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction ft = fm.beginTransaction();

    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    ft.replace(R.id.frame, fragment);
    ft.addToBackStack(null);
    ft.commit();
  }

	

  public void affichageAdmin(View v) {
    String notAvailable = getResources().getString(R.string.notimplemented);
    Toast.makeText(this, notAvailable, Toast.LENGTH_SHORT).show();
  }

	

  public void affichageUtilisateur(View v) {

    showFragment(this.fragmentEcranUtilisateur);

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

        if (v == (Button) findViewById(R.id.boutonCalendrier)) {

          Intent i = new Intent();
          i.setClassName("com.android.calendar", "com.android.calendar.AgendaActivity");
          startActivity(i);

        }
      }

      return true;
    }


	}

	

  private long createCalendar() {
    ContentValues values = new ContentValues();
    values.put(Calendars.ACCOUNT_NAME, accountName);
    values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
    values.put(Calendars.NAME, "Conference Calendar");
    values.put(Calendars.CALENDAR_DISPLAY_NAME, "Conference Calendar");
    values.put(Calendars.CALENDAR_COLOR, 0xffff0000);
    values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
    values.put(Calendars.OWNER_ACCOUNT, accountName);
    values.put(Calendars.CALENDAR_TIME_ZONE, "Europe/Paris");
    values.put(Calendars.VISIBLE, 1);
    values.put(Calendars.SYNC_EVENTS, 1);

    Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
    builder.appendQueryParameter(Calendars.ACCOUNT_NAME, "com.conference.calendar");
    builder.appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
    builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
    Uri uri = getContentResolver().insert(builder.build(), values);
    return Long.parseLong(uri.getLastPathSegment());
  }

	

  private long calenderId() {
    Cursor cursor =
        getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"),
            new String[] {"_id", "calendar_displayName"}, null, null, null);

    cursor.moveToFirst();
    // fetching calendars name
    String names[] = new String[cursor.getCount()];
    // fetching calendars id
    int[] id = new int[cursor.getCount()];
    for (int i = 0; i < names.length; i++) {
      id[i] = cursor.getInt(0);
      names[i] = cursor.getString(1);
      if (names[i].equalsIgnoreCase("Conference Calendar")) {
        return id[i];
      }
      cursor.moveToNext();
    }

    return -1;
  }

	

  private String getAccountName() {
    AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
    Account[] list = manager.getAccounts();
    String gmail = null;

    for (Account account : list) {
      if (account.type.contains("com.google")) {
        gmail = account.name;
        break;
      }
    }
    return gmail;
  }


}
