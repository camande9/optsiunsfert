
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
        configuration.setDictionaryPath("/home/alexandra/workspace/Iris/src/1702.dic");
        // Set path to the language model.
        configuration.setLanguageModelPath("/home/alexandra/workspace/Iris/src/1702.lm");
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
        //Check if recognizer recognized the speech
        while ((result = recognize.getResult()) != null) {
        	 
            //Get the recognized speech
            String command = result.getHypothesis();
            System.out.println(command);
            String work = null;
            String hostIP = null;
            String hostname = null;
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
            } else if (command.equalsIgnoreCase("close")) {//just for the test ,must add check battery to dictionary
            	work = "upower -i $(upower -e | grep 'BAT') | grep -E \"state|to\\ full|percentage\"";
            } else if (command.equalsIgnoreCase("check connection")) {
            	work = " nmcli d | grep \'connected\'";
            	hostname = " hostname  -s";
            	hostIP = "hostname --all-ip-addresses";
            	cmd = 2;
            } else if (command.equalsIgnoreCase("open")) {
            	work = "cheese";
            	
            } else if (command.equalsIgnoreCase("instant photo")) {//instant photo not added in the dictionary yet
            	work = " streamer -c /dev/video0 -b 16 -o outfile.jpeg";
            }
            //In case command recognized is none of the above hence work might be null
            if(work != null) {
               
                ProcessBuilder builder = new ProcessBuilder(work);
                List<String> commands = new ArrayList<String>();
                commands.add("/bin/sh");
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
