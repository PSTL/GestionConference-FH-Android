package fr.upmc.pstl.gestionconference; 

import java.util.Locale; 

import android.app.Activity; 
import android.content.Intent; 
import android.content.res.Configuration; 
import android.os.Bundle; 
import android.widget.RadioGroup; 
import android.widget.RadioGroup.OnCheckedChangeListener; 

public  class  Option  extends Activity  implements OnCheckedChangeListener {
	

  private RadioGroup languages;

	
  private String lang = "en";

	

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.option);
    languages = (RadioGroup) findViewById(R.id.languageRadioGroup);

    languages.setOnCheckedChangeListener(this);
  }

	

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

  }

	

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
    if (checkedId == R.id.rbEnglish) {
      lang = "en";
    } else if (checkedId == R.id.rbFrench) {
      lang = "fr";
    }
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext().getResources().updateConfiguration(config,
        getBaseContext().getResources().getDisplayMetrics());
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }


}
