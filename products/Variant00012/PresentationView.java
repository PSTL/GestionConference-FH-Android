package fr.upmc.pstl.gestionconference; 

import java.text.SimpleDateFormat; 
import java.util.Calendar; 
import java.util.Locale; 

import android.content.Intent; 
import android.os.Bundle; 
import android.provider.CalendarContract.Calendars; 
import android.support.v4.app.FragmentActivity; 
import android.view.View; 
import android.widget.Button; 
import android.widget.TextView; 

import java.util.TimeZone; 

import android.content.ContentResolver; 
import android.content.ContentValues; 
import android.content.Context; 

import android.content.SharedPreferences; 
import android.provider.CalendarContract.Events; 
import android.widget.Toast; 


public   class  PresentationView  extends FragmentActivity {
	

  /** Variables needed to manipulate calendar */

  public static final String[] EVENT_PROJECTION = new String[] {Calendars._ID, // 0
      Calendars.ACCOUNT_NAME, // 1
      Calendars.CALENDAR_DISPLAY_NAME, // 2
      Calendars.OWNER_ACCOUNT// 3
      };

	

  /** end variable for calendar */

  private TextView title;

	

  private TextView programme;

	

  private TextView author;

	

  private TextView startDate;

	

  private TextView endDate;

	

  private TextView location;

	

  private Button addToCalendarButton;

	

  private Presentation presentation;

	

  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	

  private final SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

	

  private StringBuilder sb = new StringBuilder();

	

  public static final String MyPREFERENCES = "MyPrefs";

	

  
   private void  onCreate__wrappee__Base(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.presentation_view);

    title = (TextView) findViewById(R.id.tvPresentation);
    programme = (TextView) findViewById(R.id.tvPresDesc);
    author = (TextView) findViewById(R.id.tvAuthorval);
    startDate = (TextView) findViewById(R.id.tvDatedebval);
    endDate = (TextView) findViewById(R.id.tvDatefinval);
    location = (TextView) findViewById(R.id.tvLieuval);
    addToCalendarButton = (Button) findViewById(R.id.addToCalendar);

    Intent intent = getIntent();
    presentation = (Presentation) intent.getSerializableExtra("PresentationObject");

    title.setText(presentation.getProgramme());
    programme.setText(presentation.getProgramme());
    author.setText(presentation.getAuteur());

    Calendar cal = presentation.getDateDebut();
    sb.append(dateFormat.format(cal.getTime()));
    sb.append(" à ");
    sb.append(minuteFormat.format(cal.getTime()));
    startDate.setText(sb.toString());

    cal = presentation.getDateFin();
    sb = new StringBuilder();
    sb.append(dateFormat.format(cal.getTime()));
    sb.append(" à ");
    sb.append(minuteFormat.format(cal.getTime()));
    endDate.setText(sb.toString());

    location.setText(presentation.getLieu());

    addToCalendarButton.setVisibility(View.GONE);
  }

	

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    onCreate__wrappee__Base(savedInstanceState);

    //make the add to calendar button visible and set an onclick listener
    addToCalendarButton.setVisibility(View.VISIBLE);
    addToCalendarButton.setOnClickListener(new VisibleCalendar());
  }

	

  public  class  VisibleCalendar  implements View.OnClickListener {
		
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
