package fr.upmc.pstl.gestionconference;

import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.provider.CalendarContract.Events;
import android.widget.Toast;


public class PresentationView extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    original(savedInstanceState);

    //make the add to calendar button visible and set an onclick listener
    addToCalendarButton.setVisibility(View.VISIBLE);
    addToCalendarButton.setOnClickListener(new VisibleCalendar());
  }

  public class VisibleCalendar implements View.OnClickListener {
    public void onClick(View v) {
      Calendar begintime = Calendar.getInstance();
      begintime = presentation.getDateDebut();

      Calendar endtime = Calendar.getInstance();
      endtime = presentation.getDateFin();

      SharedPreferences sharedpreferences =
          getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

      sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

      boolean calenderCreated = sharedpreferences.getBoolean("ConferenceCalendar", false);

      if (calenderCreated) {
        long calId = sharedpreferences.getLong("CalendarID", -1);
        if (calId == -1) {
          // no calendar account; react meaningfully
          Toast.makeText(getApplicationContext(), "calid is -1 : " + calId, Toast.LENGTH_LONG)
              .show();
          return;
        }
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.CALENDAR_ID, calId);
        values.put(Events.TITLE, presentation.getProgramme());
        values.put(Events.DTSTART, begintime.getTimeInMillis());
        values.put(Events.DTEND, endtime.getTimeInMillis());
        values.put(Events.EVENT_LOCATION, presentation.getLieu());
        values.put(Events.DESCRIPTION, presentation.getAuteur());
        values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
        values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
        cr.insert(Events.CONTENT_URI, values);

        Toast.makeText(getApplicationContext(), "Presentation event added to calendar " + calId,
            Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(getApplicationContext(), "ERROR: Calendar not yet created",
            Toast.LENGTH_LONG).show();

      }
    }

  }

}
