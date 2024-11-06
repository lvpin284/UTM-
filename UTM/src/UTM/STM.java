package UTM;

/**
 * Represents a standard Turing Machine (UTM.STM) by extending the baseUTM class.
 * This class specifically configures a baseUTM instance for a standard Turing Machine
 * by using a predefined configuration file upon instantiation.
 * @author Lv Pin, Qiu Teng, He Zhicheng, Deng Zheyi, Chen Xinrui, Su Zibo
 */
public class STM extends baseUTM {

    /**
     * Constructs an UTM.STM object with a specified input string and execution flag,
     * using a predefined configuration file for a standard Turing Machine.
     * The constructor automatically assigns the "StandardTM.desc" file as the Turing Machine
     * configuration file, then calls the superclass constructor baseUTM with this file, the provided input, and the flag.
     *
     * @param file The path to the Turing Machine configuration file, which is ignored
     *             in favor of a fixed file specific to the standard Turing Machine.
     * @param input The input string for the Turing Machine.
     * @param flag The execution flag (e.g., "--animation" for animation mode).
     * @throws Exception If an error occurs during the loading or execution of the Turing Machine
     *                   as defined in the superclass baseUTM.
     */
    STM(String file, String input, String flag) throws Exception {
        super(file, input, flag);
    }
}
