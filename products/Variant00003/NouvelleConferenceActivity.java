package fr.upmc.pstl.gestionconference; 

import java.util.ArrayList; 
import java.util.Calendar; 
import java.util.GregorianCalendar; 

import android.app.Dialog; 
import android.graphics.Color; 
import android.os.Bundle; 
import android.support.v4.app.FragmentActivity; 
import android.view.LayoutInflater; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.ViewGroup.LayoutParams; 
import android.widget.Button; 
import android.widget.DatePicker; 
import android.widget.EditText; 
import android.widget.LinearLayout; 
import android.widget.RelativeLayout; 
import android.widget.ScrollView; 
import android.widget.TextView; 
import android.widget.TimePicker; 
import android.widget.Toast; 

public  class  NouvelleConferenceActivity  extends FragmentActivity {
	
  static Dialog myDialog, dialogPresentation, dialogHeure;

	
  static LinearLayout presentations;

	
  static Conference conference;

	
  static String lieuConference, nomConference, descriptionConference;

	
  ArrayList<Presentation> listePresentations = new ArrayList<Presentation>();

	
  public static Calendar dateFinConference;

	
  public static Calendar dateDebutConference;

	

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_ecran_nouvelle_conference);

    reglerPrioritesScrolls();

    creationBoitesDialogue();
    ajouterListeners();
    ajouterListenerBoutonValider();
    presentations = (LinearLayout) findViewById(R.id.linearLayoutPresentations);
  }

	

  private void ajouterListenerBoutonValider() {
    Button boutonValiderConference = (Button) findViewById(R.id.boutonValiderConference);
    boutonValiderConference.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        EditText nomConf = (EditText) findViewById(R.id.editTextNomDeLaConference);
        NouvelleConferenceActivity.nomConference = nomConf.getText().toString();
        nomConf = (EditText) findViewById(R.id.editTextLieuConference);
        NouvelleConferenceActivity.lieuConference = nomConf.getText().toString();
        nomConf = (EditText) findViewById(R.id.editTextDescriptionConference);
        NouvelleConferenceActivity.descriptionConference = nomConf.getText().toString();
        NouvelleConferenceActivity.conference =
            new Conference(NouvelleConferenceActivity.nomConference,
                NouvelleConferenceActivity.lieuConference,
                NouvelleConferenceActivity.descriptionConference,
                NouvelleConferenceActivity.dateDebutConference,
                NouvelleConferenceActivity.dateFinConference, listePresentations);

        // ajoute au fichier xml
        String xmlString = XmlFileManipulator.generateXmlString(conference);
        XmlFileManipulator.writeToFile(conference.getNom() + "_conference.xml", xmlString);
        finish();
      }
    });

  }

	

  public void reglerPrioritesScrolls() {

    ScrollView parentScrollView = (ScrollView) findViewById(R.id.scrollableContents);
    ScrollView childScrollView = (ScrollView) findViewById(R.id.scrollablePresentations);

    parentScrollView.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        findViewById(R.id.scrollablePresentations).getParent().requestDisallowInterceptTouchEvent(
            false);
        return false;
      }
    });

    childScrollView.setOnTouchListener(new View.OnTouchListener() {

      public boolean onTouch(View v, MotionEvent event) {

        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
      }
    });
  }

	

  public void creationBoitesDialogue() {
    myDialog = new Dialog(this, R.style.AlertDialogCustom);

    LayoutInflater inflater = this.getLayoutInflater();
    View v = inflater.inflate(R.layout.layout_choix_date, null);
    myDialog.setContentView(v);

    dialogPresentation = new Dialog(this, R.style.AlertDialogCustom);

    LayoutInflater inflater2 = this.getLayoutInflater();
    View v2 = inflater2.inflate(R.layout.layout_nouvelle_presentation, null);
    dialogPresentation.setContentView(v2);

    dialogHeure = new Dialog(this, R.style.AlertDialogCustom);
    LayoutInflater inflater3 = this.getLayoutInflater();
    View v3 = inflater3.inflate(R.layout.layout_nouvelle_presentation, null);
    dialogHeure.setContentView(v3);
  }

	

  public void ajouterListeners() {
    Button boutonDateDebut = (Button) findViewById(R.id.boutonDebutConference);
    boutonDateDebut.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        NouvelleConferenceActivity.myDialog.setTitle("Date de début de la conférence");
        NouvelleConferenceActivity.myDialog.show();

        Button validerDebutConference = (Button) myDialog.findViewById(R.id.boutonValiderDate);
        validerDebutConference.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            DatePicker datePicker = (DatePicker) myDialog.findViewById(R.id.datePickerConference);
            int jour = datePicker.getDayOfMonth();
            int mois = datePicker.getMonth() + 1;
            int annee = datePicker.getYear();
            NouvelleConferenceActivity.dateDebutConference =
                new GregorianCalendar(annee, mois, jour, 0, 0);
            // NouvelleConferenceActivity.dateDebutConference = new Date(annee, mois, jour);
            Toast.makeText(NouvelleConferenceActivity.this,
                "date de debut de la conference " + jour + "/" + mois + "/" + annee,
                Toast.LENGTH_SHORT).show();
            myDialog.dismiss();
          }
        });

      }
    });

    Button boutonDateFin = (Button) findViewById(R.id.boutonFinConference);
    boutonDateFin.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        NouvelleConferenceActivity.myDialog.setTitle("Date de fin de la conférence");
        NouvelleConferenceActivity.myDialog.show();
        Button validerFinConference = (Button) myDialog.findViewById(R.id.boutonValiderDate);
        validerFinConference.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            DatePicker datePicker = (DatePicker) myDialog.findViewById(R.id.datePickerConference);
            int jour = datePicker.getDayOfMonth();
            int mois = datePicker.getMonth() + 1;
            int annee = datePicker.getYear();
            NouvelleConferenceActivity.dateFinConference =
                new GregorianCalendar(annee, mois, jour, 0, 0);
            Toast.makeText(NouvelleConferenceActivity.this,
                "date de fin de la conference " + jour + "/" + mois + "/" + annee,
                Toast.LENGTH_SHORT).show();
            myDialog.dismiss();
          }
        });
      }
    });

    Button boutonAjouterPresentation = (Button) findViewById(R.id.boutonAjouterPresentation);
    boutonAjouterPresentation.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        NouvelleConferenceActivity.dialogPresentation.setTitle("Nouvelle Presentation");
        NouvelleConferenceActivity.dialogPresentation.show();

        Button boutonValiderPresentation =
            (Button) NouvelleConferenceActivity.dialogPresentation
                .findViewById(R.id.boutonValiderPresentation);

        boutonValiderPresentation.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            EditText editText =
                (EditText) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.editTextProgramme);
            String textProgramme = editText.getText().toString();

            editText =
                (EditText) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.editTextAuteur);
            String textAuteur = editText.getText().toString();

            editText =
                (EditText) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.editTextLieu);
            String textLieu = editText.getText().toString();

            DatePicker datePicker =
                (DatePicker) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.datePickerPresentation);
            int jour = datePicker.getDayOfMonth();
            int mois = datePicker.getMonth() + 1;
            int annee = datePicker.getYear();

            TimePicker timePicker =
                (TimePicker) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.timePickerDebutPresentation);
            int heure = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            Calendar dateDebut = new GregorianCalendar(annee, mois, jour, heure, minute);

            timePicker =
                (TimePicker) NouvelleConferenceActivity.dialogPresentation
                    .findViewById(R.id.timePickerFinPresentation);
            heure = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
            Calendar dateFin = new GregorianCalendar(annee, mois, jour, heure, minute);

            RelativeLayout relativeLayout = new RelativeLayout(NouvelleConferenceActivity.this);
            relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

            // Ajout d'un textView
            LinearLayout layout = new LinearLayout(NouvelleConferenceActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

            TextView titleView = new TextView(NouvelleConferenceActivity.this);
            LayoutParams lparams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            titleView.setLayoutParams(lparams);
            titleView.setText(textProgramme + " par : " + textAuteur);
            layout.addView(titleView);

            TextView heureView = new TextView(NouvelleConferenceActivity.this);
            LayoutParams lparams2 =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            heureView.setLayoutParams(lparams2);

            heureView.setText("de : " + dateDebut.get(Calendar.HOUR_OF_DAY) + ":"
                + dateDebut.get(Calendar.MINUTE) + " à " + dateFin.get(Calendar.HOUR_OF_DAY) + ":"
                + dateFin.get(Calendar.MINUTE));
            layout.addView(heureView);

            Button boutonSupprimerPresentation = new Button(NouvelleConferenceActivity.this);
            boutonSupprimerPresentation.setText("-");
            @SuppressWarnings("unused")
            RelativeLayout.LayoutParams paramBouton =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

            RelativeLayout relativeLayoutBouton =
                new RelativeLayout(NouvelleConferenceActivity.this);
            RelativeLayout.LayoutParams paramLayoutBouton =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            relativeLayoutBouton.setLayoutParams(paramLayoutBouton);
            paramLayoutBouton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeLayoutBouton.setHorizontalGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeLayoutBouton.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeLayoutBouton.addView(boutonSupprimerPresentation);

            relativeLayout.addView(layout);
            relativeLayout.addView(relativeLayoutBouton);

            TextView separateur = new TextView(NouvelleConferenceActivity.this);
            LayoutParams lparams3 = new LayoutParams(LayoutParams.MATCH_PARENT, 5);
            separateur.setLayoutParams(lparams3);
            separateur.setBackgroundColor(Color.GRAY);
            separateur.setText(" ");

            NouvelleConferenceActivity.presentations.addView(relativeLayout);
            NouvelleConferenceActivity.presentations.addView(separateur);

            Presentation presentation =
                new Presentation(textProgramme, textAuteur, textLieu, dateDebut, dateFin);
            listePresentations.add(presentation);

            NouvelleConferenceActivity.dialogPresentation.dismiss();
          }
        });

      }

    });

  }


}
