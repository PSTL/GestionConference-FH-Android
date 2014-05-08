package fr.upmc.pstl.gestionconference; 

import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStreamReader; 

import android.app.Activity; 
import android.os.Bundle; 
import android.os.Environment; 
import android.view.Gravity; 
import android.view.View; 
import android.view.ViewGroup.LayoutParams; 
import android.widget.Button; 
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.RelativeLayout; 
import android.widget.TextView; 
import android.widget.Toast; 

public  class  ImportActivity  extends Activity {
	

  File file[];

	

  File f;

	

  int numeroFichierAajouter;

	

  public static int nombreFichiers;

	

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_import);
    nombreFichiers = 0;

    listerFichiers();

  }

	

  public void listerFichiers() {
    String path =
        Environment.getExternalStorageDirectory().toString() + "/dossierImportConferences";

    f = new File(path);

    if (!f.exists()) {
      f.mkdirs();
    }

    file = f.listFiles();
    if (file != null)
      for (int i = 0; i < file.length; i++) {
        ajouterFichierConference(file[i].getName());
      }

  }

	

  public void ajouterFichierConference(String titre) {
    RelativeLayout relativeLayoutFichier = new RelativeLayout(this);
    relativeLayoutFichier.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT));
    relativeLayoutFichier.setPadding(0, 20, 0, 0);

    ImageView image = new ImageView(this);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(120, 120);
    layoutParams.addRule(Gravity.LEFT);
    image.setLayoutParams(layoutParams);
    image.setBackgroundResource(R.drawable.image_dossier);
    relativeLayoutFichier.addView(image);

    TextView textViewTitreFichier = new TextView(this);
    textViewTitreFichier.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT));
    textViewTitreFichier.setGravity(Gravity.CENTER);
    textViewTitreFichier.setText(titre);
    relativeLayoutFichier.addView(textViewTitreFichier);

    LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayoutImport);

    Button boutonImportConf = new Button(this);
    boutonImportConf.setText("+");
    RelativeLayout.LayoutParams layoutParamsBouton = new RelativeLayout.LayoutParams(120, 120);
    layoutParamsBouton.addRule(Gravity.RIGHT);
    layoutParamsBouton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

    boutonImportConf.setLayoutParams(layoutParamsBouton);
    final int numFichier = nombreFichiers;

    boutonImportConf.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {

        numeroFichierAajouter = numFichier;
        FileInputStream fis;
        try {
          fis = new FileInputStream(file[numeroFichierAajouter]);
          InputStreamReader isr = new InputStreamReader(fis);
          BufferedReader bufferedReader = new BufferedReader(isr);
          StringBuilder sb = new StringBuilder();
          String line;
          try {
            while ((line = bufferedReader.readLine()) != null) {
              sb.append(line);
            }
            writeToFile(file[numeroFichierAajouter].getName(), sb.toString());
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

        Toast.makeText(ImportActivity.this, getResources().getString(R.string.fileadded),
            Toast.LENGTH_SHORT).show();
      }
    });

    nombreFichiers++;

    relativeLayoutFichier.addView(boutonImportConf);

    ll.addView(relativeLayoutFichier);
  }

	

  public static void writeToFile(String fileName, String body) {
    FileOutputStream fos = null;

    try {
      final File dir =
          new File(Environment.getExternalStorageDirectory().getAbsolutePath()
              + "/dossierConferences/");

      if (!dir.exists()) {
        dir.mkdirs();
      }

      final File myFile = new File(dir, fileName);

      if (!myFile.exists()) {
        myFile.createNewFile();
      }

      fos = new FileOutputStream(myFile);

      fos.write(body.getBytes());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
