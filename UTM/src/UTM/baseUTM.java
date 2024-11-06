package UTM;

import utm.*;
import utmeditor.*;
import java.io.*;
import java.util.*;

/**
 * Represents a base class for a Universal Turing Machine (UTM).
 * This class extends the UniversalTuringMachine and implements the UTMController interface,
 * providing mechanisms to load, initialize, and execute a Turing Machine from a given configuration file and input.
 * @author Lv Pin, Qiu Teng, He Zhicheng, Deng Zheyi, Chen Xinrui, Su Zibo
 */
public class baseUTM extends UniversalTuringMachine implements UTMController{

    /** The path to the Turing Machine configuration file. */
    String file;

    /** The input string for the Turing Machine. */
    String input;

    /** Flag to determine the mode of execution, e.g., with or without animation. */
    String flag;

    /** Initial state of the Turing Machine. */
    String initialState = "";

    /** Accept state of the Turing Machine. */
    String acceptState = "";

    /** Reject state of the Turing Machine. */
    String rejectState = "";

    /** Initial position of the Turing Machine's head. */
    int initialPointer = 0;

    /** List of transition rules for the Turing Machine. */
    List<String[]> rules;

    /** Instance of UniversalTuringMachine. */
    UniversalTuringMachine utm;

    /**
     * Constructs a baseUTM object with specified configuration file, input string, and execution flag.
     * Initializes the Turing Machine by loading the configuration, setting the input, and executing the machine.
     *
     * @param file The path to the Turing Machine configuration file.
     * @param input The input string for the Turing Machine.
     * @param flag The execution flag (e.g., "--animation" for animation mode).
     * @throws Exception If an error occurs during the loading or execution of the Turing Machine.
     */
    public baseUTM(String file,String input,String flag) throws Exception {
        this.file = file;
        this.input = input;
        this.flag = flag;
        loadTM(file);
        initializeTM(input);
        exeTM(initialPointer);
        this.utm = this;
    }

    /**
     * Constructs a baseUTM object and initializes it with a UTMEditor instance.
     * Sets the execution flag to "--animation" and initializes the UTM with this instance.
     *
     * @throws IOException If an error occurs during the initialization.
     */
    public baseUTM() throws IOException {
        UTMEditor UTMeditor = new UTMEditor();
        UTMeditor.setUTMController(this);

        this.flag = "--animation";
        this.utm = this;
    }

    /**
     * Loads a Turing Machine configuration from a specified file.
     * This method only sets the file path; it does not read or process the file.
     *
     * @param file The path to the Turing Machine configuration file.
     */
    public void loadTuringMachineFrom(String file) {
        this.file = file;
    }

    /**
     * Runs the UTM with a specified input string.
     * Loads the Turing Machine configuration from the file, initializes the machine with the input, and executes it.
     *
     * @param input The input string for the Turing Machine.
     */
    public void runUTM(String input) {
        this.input = input;
        try {
            this.loadTM(file);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        this.initializeTM(this.input);
        this.exeTM(initialPointer);
    }

    /**
     * Loads the Turing Machine configuration from the specified file.
     * Reads the file, parses the configuration (states, transition rules, etc.), and stores them in the class fields.
     *
     * @param file The path to the Turing Machine configuration file.
     * @throws IOException If an error occurs while reading the file.
     */
    public void loadTM(String file) throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        rules = new ArrayList<>();

        while ((line = buffer.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("#")) continue;

            String[] parts = line.split("=");
            if(parts[0].equals("initialState")) {
                initialState = parts[1];
                continue;
            }
            if(parts[0].equals("acceptState")) {
                acceptState = parts[1];
                continue;
            }
            if(parts[0].equals("rejectState")) {
                rejectState = parts[1];
                continue;
            }
            if(parts[0].equals("variant")) {
                if(parts[1].equals("BUSY_BEAVER"))
                    initialPointer = 10;
                continue;
            }
            if(parts[0].equals("rules")) {

                String[] ruleParts = parts[1].split("<>");

                for (String rulePart : ruleParts) {
                    String[] rule = rulePart.split(",");
                    rules.add(rule);
                }
            }

            buffer.close();
            break;
        }
    }

    /**
     * Initializes the Turing Machine with the given input string.
     * Configures the Turing Machine's states, transition rules, and input based on the previously loaded configuration.
     *
     * @param input The input string for the Turing Machine.
     */
    public void initializeTM(String input){
        TuringMachine tm = new TuringMachine(rules.size(), initialState, acceptState, rejectState);
        for (String[] rule : rules) {
            if (rule[4].equals("RESET"))
                tm.addRule(rule[0], rule[1].charAt(0), rule[2], rule[3].charAt(0), MoveLeftReset.valueOf(rule[4]));
            else
                tm.addRule(rule[0], rule[1].charAt(0), rule[2], rule[3].charAt(0), MoveClassical.valueOf(rule[4]));
        }

        utm = new UniversalTuringMachine();
        utm.loadTuringMachine(tm);

        if(initialPointer!=0) input = "00000000000000000000";

        utm.loadInput(input);
        utm.display();
    }

    /**
     * Executes the Turing Machine starting from the initial head position.
     * Performs the computation based on the loaded transition rules and input, moving the head and writing to the tape as specified.
     *
     * @param initialPointer The initial position of the Turing Machine's head.
     */
    public void exeTM(int initialPointer)  {
        for(int i = 0; i < initialPointer; i++) {
            utm.moveHead(MoveClassical.RIGHT, false);//utm.moveHead(MoveClassical.RIGHT, (flag.equals("--animation")));
        }
        boolean isContinue = true;
        while (isContinue) {
            for (String[] rule : utm.getTuringMachine().getRules()) {

                if (utm.getTuringMachine().getHead().getCurrentState().equals(rule[0])
                        && utm.getTuringMachine().getTape().get(utm.getTuringMachine().getHead().getCurrentCell()).toString().equals(rule[1])){
                    utm.writeOnCurrentCell(rule[3].charAt(0));
                    if (rule[4].equals("RESET")) {
                        for(int i = utm.getTuringMachine().getHead().getCurrentCell(); i >0; i--) {
                            utm.moveHead(MoveClassical.LEFT, false);//utm.moveHead(MoveClassical.LEFT, !(flag.equals("--animation")));
                        }
                    } else {
                        utm.moveHead(MoveClassical.valueOf(rule[4]), flag.equals("--animation"));
                    }
                    utm.updateHeadState(rule[2]);
                }
                if (utm.getTuringMachine().getHead().getCurrentState().equals(utm.getTuringMachine().getAcceptState())) {
                    utm.displayHaltState(HaltState.ACCEPTED);
                    isContinue = false;
                    break;
                }
                if (utm.getTuringMachine().getHead().getCurrentState().equals(utm.getTuringMachine().getRejectState())) {
                    utm.displayHaltState(HaltState.REJECTED);
                    isContinue = false;
                    break;
                }
            }
        }
    }

    /**
     * Enumeration representing the movements of the Turing Machine's head,
     * including a special RESET movement that moves the head back to the start of the tape.
     */
    public enum MoveLeftReset implements Move {

        /** The right movement of a Left Reset TM. */
        RIGHT,

        /** The reset movement of a Left Reset TM. */
        RESET;

        private void MoveLeftReset() {
        }
    }
}
