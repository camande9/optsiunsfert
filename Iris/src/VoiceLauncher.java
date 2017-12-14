
//Imports
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author ex094
 */
public class VoiceLauncher {
	public static void main(String[] args) throws IOException, InterruptedException {
        //Configuration Object
        Configuration configuration = new Configuration();

        // Set path to the acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to the dictionary.
        configuration.setDictionaryPath("/home/darius/Facultate/mps/optsiunsfert/Iris/src/1702.dic");
        // Set path to the language model.
        configuration.setLanguageModelPath("/home/darius/Facultate/mps/optsiunsfert/Iris/src/1702.lm");
        Logger cmRootLogger = Logger.getLogger("default.config");
        cmRootLogger.setLevel(java.util.logging.Level.OFF);
        String conFile = System.getProperty("java.util.logging.config.file");
        if (conFile == null) {
              System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }
        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
        
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognize.startRecognition(true);
        
        //Creating SpeechResult object
        SpeechResult result;
        //correct output
        int cmd = 0;
        ArrayList<String> playlist = new ArrayList<String>(10);
    	List<String>  interval = Arrays.asList("one","two","three","four","five","six","seven","eight","nine","ten");
    	List<String>  cities = Arrays.asList("Tirana","Andorra","Yerevan","Vienna","Baku","Minsk","Brussels","Sofia","Zagreb","Nicosia","Republic","Copenhagen","Tallinn","Helsinki","Paris","Tbilisi","Berlin","Athens","Budapest","Reykjavik","Dublin","Rome","Astana","Pristina","Capital","Riga","Vaduz","Vilnius","Luxembourg","Skopje","Valletta","Chisinau","Monaco","Podgorica","Amsterdam","Oslo","Warsaw","Lisbon","Bucharest","Moscow","Marino","Belgrade","Bratislava","Ljubljana","Madrid","Stockholm","Bern","Ankara","Kiev","London","Vatican");
    	
    	playlist.add("firefox \"https://www.youtube.com/watch?v=25wovUAb8rU&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=1\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=UHvGpxOMN6U&index=2&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=GnHMp8WIg1Q&index=3&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=3tUh-x-fp8Q&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=4\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=n0l0YOHO5jg&index=5&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=ZXorliBQMxg&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=6\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=tKJrbUaUmfs&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=7\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=JPp-oLkQPQQ&index=8&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=J_QGZspO4gg&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=9\"");
    	playlist.add("firefox \"https://www.youtube.com/watch?v=IEvEbTqaW1Y&list=PL2lX82lL7yCcTb-ue24Ci_bqndSfYKRjZ&index=10\"");
        //Check if recognizer recognized the speech
    	
        while ((result = recognize.getResult()) != null) {
        	 
            //Get the recognized speech
            String command = result.getHypothesis();
            System.out.println(command);
            String work = null;
            Process p;
 
            if(command.equalsIgnoreCase("open file manager")) {
            	System.out.println("nautilus");
                work = "nautilus";
            } else if (command.equalsIgnoreCase("close file manager")) {
            	System.out.println("close nautilus");
                work = "pkill nautilus";
            } else if (command.equalsIgnoreCase("open browser")) {
            	System.out.println("open firefox");
                work = "firefox";
            } else if (command.equalsIgnoreCase("close browser")) {
            	System.out.println("close firefox");
                work = "pkill firefox";
            } else if (command.equalsIgnoreCase("get battery status")) {
            	work = "upower -i $(upower -e | grep 'BAT') | grep -E \"state|to\\ full|percentage\"";
            } else if (command.equalsIgnoreCase("connection")) {
            	work =  "./src/weather.sh 3";
            } else if (command.equalsIgnoreCase("calculus")) {
            	work = "gnome-calculator";
            } else if (command.equalsIgnoreCase("calendar")) {
            	work = "gnome-calendar";
            }else if (command.equalsIgnoreCase("game")) {
            	work = "sol";
            }else if (command.equalsIgnoreCase("picture")) {
            	work = "cheese";
            } else if (command.equalsIgnoreCase("take picture")) {
            	work = " streamer -c /dev/video0 -b 16 -o outfile.jpeg";
            } else if (command.toLowerCase().startsWith("weather")) {
            	work = "./src/weather.sh 1 ".concat(command.substring(8,command.length()).trim().toLowerCase());
            } else if (command.equalsIgnoreCase("say time")) {
            	work =  "./src/weather.sh 2";
            } else if (command.equalsIgnoreCase("get inbox")) {
            	work =  "";
            	GmailInbox mail=new GmailInbox();
            	System.out.println("Inbox Email Works");
            	mail.read();
            	
            } else if (command.toLowerCase().startsWith("song number")) {
                System.out.println(command.substring(12, command.length()).trim());
                System.out.println(interval.indexOf(command.substring(12, command.length()).trim().toLowerCase()));
            	work=playlist.get(interval.indexOf(command.substring(12, command.length()).trim().toLowerCase()));     	
            } else if (command.equalsIgnoreCase("open music player")) {
            	System.out.println("open music player");
            	work = "rhythmbox";
            } else if (command.equalsIgnoreCase("close music player")) {
            	System.out.println("close music player");
            	work = "pkill rhythmbox";
            } else if (command.equalsIgnoreCase("open image viewer")) {
            	System.out.println("open image viewer");
            	work = "eog ~/Facultate/mps/optsiunsfert/image";
            } else if (command.equalsIgnoreCase("close image viewer")) {
            	System.out.println("close image viewer");
            	work = "pkill eog";
            }
            else {
            	System.out.println("Sorry, I don't understand");
            }
            	
            //In case command recognized is none of the above hence work might be null
            if(work != null) {
                //Execute the command
               // p = Runtime.getRuntime().exec(work);
                ProcessBuilder builder = new ProcessBuilder(work);
                List<String> commands = new ArrayList<String>();
                commands.add("/bin/bash");
                commands.add("-c");
                commands.add(work);
              
                SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
                int res = commandExecutor.executeCommand();

                StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
                StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();

                System.out.println("STDOUT");
                System.out.println(stdout);
                if(cmd == 2){
                	String s = stdout.toString();
                	int i = s.indexOf(' ');
                	String ip = s.substring(0, i);
                	System.out.println(ip);
                	
                }

                System.out.println("STDERR");
                System.out.println(stderr);
            }
        
        }
        
    }

}
