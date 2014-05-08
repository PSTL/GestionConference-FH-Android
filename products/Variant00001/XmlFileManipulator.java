package fr.upmc.pstl.gestionconference; 

import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.StringWriter; 
import java.io.UnsupportedEncodingException; 
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.ArrayList; 
import java.util.Calendar; 
import java.util.List; 

import org.xmlpull.v1.XmlPullParser; 
import org.xmlpull.v1.XmlPullParserException; 
import org.xmlpull.v1.XmlPullParserFactory; 
import org.xmlpull.v1.XmlSerializer; 

import android.os.Environment; 
import android.util.Xml; 

public  class  XmlFileManipulator {
	

  public static String generateXmlString(Conference conference) {
    XmlSerializer serializer = Xml.newSerializer();
    StringWriter writer = new StringWriter();
    Calendar cal = null;
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    try {
      serializer.setOutput(writer);
      serializer.startDocument("UTF-8", true);
      serializer.startTag("", "Conference");
      serializer.startTag("", "nom");
      serializer.text(conference.getNom());
      serializer.endTag("", "nom");

      serializer.startTag("", "datedebut");
      cal = conference.getDateDebut();

      serializer.text(dateFormat.format(cal.getTime()));
      serializer.endTag("", "datedebut");

      serializer.startTag("", "datefin");
      cal = conference.getDateFin();
      sb = new StringBuilder();

      serializer.text(dateFormat.format(cal.getTime()));
      serializer.endTag("", "datefin");

      serializer.startTag("", "description");
      serializer.text(conference.getDescription());
      serializer.endTag("", "description");

      // start presentations

      serializer.startTag("", "Presentations");
      List<Presentation> presentations = conference.getPresentations();
      if (presentations != null) {

        for (Presentation presentation : presentations) {
          serializer.startTag("", "presentation"); // start presentation

          serializer.startTag("", "auteur");
          serializer.text(presentation.getAuteur());
          serializer.endTag("", "auteur");

          serializer.startTag("", "datedebutPresentation");
          cal = presentation.getDateDebut();
          sb = new StringBuilder();

          // Set presentation date debut
          sb.append(cal.get(Calendar.DAY_OF_MONTH));
          sb.append("/");
          sb.append(cal.get(Calendar.MONTH));
          sb.append("/");
          sb.append(cal.get(Calendar.YEAR));

          serializer.text(dateFormat1.format(cal.getTime()));
          serializer.endTag("", "datedebutPresentation");

          serializer.startTag("", "datefinPresentation");
          cal = presentation.getDateFin();
          serializer.text(dateFormat1.format(cal.getTime()));
          serializer.endTag("", "datefinPresentation");

          serializer.startTag("", "lieuPresentation");
          serializer.text(presentation.getLieu());
          serializer.endTag("", "lieuPresentation");

          serializer.startTag("", "programme");
          serializer.text(presentation.getProgramme());
          serializer.endTag("", "programme");

          serializer.endTag("", "presentation"); // end presentation
        }
      }
      serializer.endTag("", "Presentations"); // end presentations

      serializer.startTag("", "lieuConference");
      serializer.text(conference.getLieuConference());
      serializer.endTag("", "lieuConference");

      serializer.endTag("", "Conference");
      serializer.endDocument();
      return writer.toString();

    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

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

	

  public static Conference readConferenceFromFile(String filename) {
    XmlPullParserFactory factory = null;
    XmlPullParser parser = null;
    Conference conference = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    File file =
        new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/dossierConferences/", filename);

    try {
      factory = XmlPullParserFactory.newInstance();
      parser = factory.newPullParser();
      InputStream is = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(is);

      // auto-detect encoding from the stream
      parser.setInput(reader);

      int eventType = parser.getEventType();

      boolean done = false;
      while (eventType != XmlPullParser.END_DOCUMENT && !done) {
        String name = null;
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            conference = new Conference();
            break;
          case XmlPullParser.START_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase("CONFERENCE")) {
              conference = new Conference();
            } else if (conference != null) {
              if (name.equalsIgnoreCase("NOM")) {
                conference.setNom(parser.nextText());
              } else if (name.equalsIgnoreCase("DATEDEBUT")) {
                Calendar dateDebut = Calendar.getInstance();
                try {
                  dateDebut.setTime(dateFormat.parse(parser.nextText()));
                } catch (ParseException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                conference.setDateDebut(dateDebut);
              } else if (name.equalsIgnoreCase("DATEFIN")) {
                Calendar dateFin = Calendar.getInstance();
                try {
                  dateFin.setTime(dateFormat.parse(parser.nextText()));
                } catch (ParseException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                conference.setDateFin(dateFin);
              } else if (name.equalsIgnoreCase("DESCRIPTION")) {
                conference.setDescription(parser.nextText());
              } else if (name.equalsIgnoreCase("LIEUCONFERENCE")) {
                conference.setLieuConference(parser.nextText());
              }
            }
            break;
          case XmlPullParser.END_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase("CONFERENCE")) {
              done = true;
            }
            break;
          default:

            break;
        }
        eventType = parser.next();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // conference.setNom("Name");
    // conference.setDescription("this is a desc  qsdvfdgsdfg");
    return conference;
  }

	

  public static List<Presentation> readPresentationsFromFile(String filename) {
    List<Presentation> presentations = null;
    Presentation presentation = null;
    XmlPullParserFactory factory = null;
    XmlPullParser parser = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    File file =
        new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/dossierConferences/", filename);

    try {
      factory = XmlPullParserFactory.newInstance();
      parser = factory.newPullParser();
      InputStream is = new FileInputStream(file);
      InputStreamReader reader = new InputStreamReader(is);

      // auto-detect encoding from the stream
      parser.setInput(reader);

      int eventType = parser.getEventType();

      boolean done = false;

      while (!done) {
        String name = null;
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            break;
          case XmlPullParser.START_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase("PRESENTATIONS")) {
              presentations = new ArrayList<Presentation>();
            } else if (name.equalsIgnoreCase("PRESENTATION")) {
              presentation = new Presentation();
            } else if (presentation != null) {
              if (name.equalsIgnoreCase("AUTEUR")) {
                presentation.setAuteur(parser.nextText());
              } else if (name.equalsIgnoreCase("PROGRAMME")) {
                presentation.setProgramme(parser.nextText());
              } else if (name.equalsIgnoreCase("DATEDEBUTPRESENTATION")) {
                Calendar dateDebut = Calendar.getInstance();
                try {
                  dateDebut.setTime(dateFormat.parse(parser.nextText()));
                } catch (ParseException e) {
                  e.printStackTrace();
                }
                presentation.setDateDebut(dateDebut);
              } else if (name.equalsIgnoreCase("DATEFINPRESENTATION")) {
                Calendar dateFin = Calendar.getInstance();
                try {
                  dateFin.setTime(dateFormat.parse(parser.nextText()));
                } catch (ParseException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                presentation.setDateFin(dateFin);
              } else if (name.equalsIgnoreCase("LIEUPRESENTATION")) {
                presentation.setLieu(parser.nextText());
              }
            }
            break;
          case XmlPullParser.END_TAG:
            name = parser.getName();
            if (name.equalsIgnoreCase("PRESENTATION") && presentation != null) {
              presentations.add(presentation);
            } else if (name.equalsIgnoreCase("PRESENTATIONS")) {
              done = true;
            }
            break;
          default:
            break;
        }

        eventType = parser.next();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return presentations;
  }

	

  public static String readFromFile(String filename) {

    String ret = "";
    try {
      File file =
          new File(Environment.getExternalStorageDirectory().getAbsolutePath()
              + "/dossierConferences/", filename);

      FileInputStream fis = new FileInputStream(file);

      StringBuffer fileContent = new StringBuffer("");

      byte[] buffer = new byte[1024];

      while (fis.read(buffer) != -1) {
        fileContent.append(new String(buffer));
      }

      ret = fileContent.toString();
      fis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return ret;
  }


}
