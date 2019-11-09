package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Michael Chien
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine m = readConfig();
        String toread = _input.nextLine();
        if (!toread.contains("*")) {
            throw new EnigmaException("bad setting");
        }
        exit:
        while (_input.hasNextLine()) {
            while (_input.hasNextLine()) {
                if (toread.contains("*")) {
                    setUp(m, toread);
                }
                toread = _input.nextLine();
                if (toread.isEmpty()) {
                    _output.println();
                }
                if (toread.indexOf('*') >= 0) {
                    break;
                }
                if (!_input.hasNext() || !_input.hasNextLine()) {
                    toread = toread.replaceAll("\\s", "");
                    printMessageLine(m.convert(toread));
                    if (_input.hasNextLine()) {
                        _output.println();
                    }
                    break exit;
                } else {
                    toread = toread.replaceAll("\\s", "");
                    printMessageLine(m.convert(toread));
                }
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            rotorslist = new ArrayList<Rotor>();
            if (_config.hasNext(".*+")) {
                _alphabet = new Alphabet(_config.next());
            } else {
                throw new EnigmaException("no alphabet");
            }
            if (_config.hasNextInt()) {
                _numrotors = _config.nextInt();
            } else {
                throw new EnigmaException("no Rotors");
            }
            if (_config.hasNextInt()) {
                _pawls = _config.nextInt();
            } else {
                throw new EnigmaException("no pawls");
            }
            while (_config.hasNext()) {
                rotorslist.add(readRotor());
            }
            return new Machine(_alphabet, _numrotors, _pawls, rotorslist);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name;
            String cycles = "";
            String notches = "";
            name = _config.next().toUpperCase();
            String rotornotches = _config.next();
            if (rotornotches.charAt(0) == 'R') {
                while (_config.hasNext("\\s*\\(.+\\)\\s*")) {
                    cycles += _config.next() + " ";
                }
                return new Reflector(name, new Permutation(cycles, _alphabet));
            }
            if (rotornotches.charAt(0) == 'N') {
                while  (_config.hasNext("\\s*\\(.+\\)\\s*")) {
                    cycles += _config.next() + " ";
                }
                return new FixedRotor(name, new Permutation(cycles, _alphabet));
            } else {
                notches += rotornotches.substring(1, rotornotches.length());
                while (_config.hasNext("\\s*\\(.+\\)\\s*")) {
                    String read = _config.next();
                    if (name.equals("V")) {
                        read = read.replaceAll("[)(]", " ");
                    }
                    cycles += read + " ";
                }
                return new MovingRotor(name,
                        new Permutation(cycles, _alphabet), notches);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] rotors = new String[M.numRotors()];
        Scanner scan = new Scanner(settings);
        String setting = "";
        String plugboard = "";
        if (scan.hasNext("[*]")) {
            scan.next();
            for (int i = 0; i < M.numRotors(); i++) {
                rotors[i] = (scan.next());
            }
        }
        for (int i = 0; i < rotors.length; i++) {
            for (int j = i + 1; j < rotors.length; j++) {
                if (rotors[j] == rotors[i]) {
                    throw new EnigmaException("rotors with same name");
                }
            }
        }
        M.insertRotors(rotors);
        if (!M._rotors[0].reflecting()) {
            throw new EnigmaException("wrong rotor type");
        }
        if (scan.hasNext()) {
            String temp = scan.next();
            if (temp.length() != M.numRotors() - 1) {
                throw new EnigmaException("setting has wrong length");
            }
            setting = temp;
        }
        M.setRotors(setting);
        while (scan.hasNext("\\s*\\(.+\\)\\s*")) {
            plugboard += scan.next() + " ";
        }
        M.setPlugboard(new Permutation(plugboard, _alphabet));
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        msg = msg.replaceAll("\\s", "");
        for (int i = 0; i < msg.length(); i += 5) {
            if (msg.length() - i <= 5) {
                _output.println(msg.substring(i, i + msg.length() - i));
            } else {
                _output.print(msg.substring(i, i + 5) + " ");
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
    /** num rotors. */
    private int _numrotors;
    /** pawls. */
    private int _pawls;
    /** type of rotor. */
    private char type;
    /** list of all rotors. */
    private Collection<Rotor> rotorslist;
}
