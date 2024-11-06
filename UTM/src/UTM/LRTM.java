package UTM;

/**
 * Represents a specialized implementation of the baseUTM class, configuring it specifically for a Left Reset Turing Machine (UTM.LRTM).
 * This class extends baseUTM, setting a fixed description file upon instantiation
 * and allowing for the specification of input and execution flags.
 * @author Lv Pin, Qiu Teng, He Zhicheng, Deng Zheyi, Chen Xinrui, Su Zibo
 */
public class LRTM extends baseUTM {

    /**
     * Constructs an UTM.LRTM object with a specified input string and execution flag,
     * using a predefined configuration file for a Left Reset Turing Machine.
     * The constructor automatically assigns the "LeftResetTM.desc" file as the Turing Machine
     * configuration file, then calls the superclass constructor baseUTM with this file, the provided input, and the flag.
     *
     * @param file The path to the Turing Machine configuration file, which is ignored
     *             in favor of a fixed file specific to the Left Reset Turing Machine.
     * @param input The input string for the Turing Machine.
     * @param flag The execution flag (e.g., "--animation" for animation mode).
     * @throws Exception If an error occurs during the loading or execution of the Turing Machine
     *                   as defined in the superclass baseUTM.
     */
    LRTM(String file, String input, String flag) throws Exception {
        super("LeftResetTM.desc", input, flag);
    }
}
