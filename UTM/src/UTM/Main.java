package UTM;

/**
 * The UTM.Main class serves as the entry point for a Turing Machine simulation program.
 * It demonstrates how to instantiate the baseUTM class using either command line arguments
 * or default settings if no arguments are provided.
 * @author Lv Pin, Qiu Teng, He Zhicheng, Deng Zheyi, Chen Xinrui, Su Zibo
 */
public class Main {

    /**
     * The main method serves as the entry point of the application.
     * It checks for the presence of command line arguments to configure and start a Turing Machine simulation.
     * If three arguments are provided (file path, input string, and flag), it uses them to create an instance
     * of baseUTM and start the Turing Machine simulation with those configurations.
     * If the arguments are not provided, it displays a message and starts the baseUTM with default settings.
     *
     * @param args Command line arguments used to configure the Turing Machine.
     *             Expected to receive three arguments:
     *             1. file - The path to the Turing Machine configuration file.
     *             2. input - The input string for the Turing Machine.
     *             3. flag - The execution flag (e.g., "--animation" for animation mode).
     * @throws Exception If an error occurs during the instantiation of baseUTM.
     */
    public static void main(String[] args) throws Exception {
        String file;
        String input;
        String flag;

        // if command line arguments are provided, use them
        if (args.length == 3) {
            file = args[0];
            input = args[1];
            flag = args[2];
            new baseUTM(file, input, flag);
        } else {
            System.out.println("No command line arguments provided.");
            new baseUTM();
        }
        // test
        // new STM("D:\\nowlearn\\gaoji\\UTM\\description\\classical-2.desc","00011","--animation");
    }
}


