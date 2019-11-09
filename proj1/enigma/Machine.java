package enigma;


import java.util.Collection;
import java.util.HashMap;
/** Class that represents a complete enigma machine.
 *  @author Michael Chien
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _pawls = pawls;
        _numRotors = numRotors;
        _rotors = new Rotor[numRotors];
        _maprotors = new HashMap<>();
        for (Rotor r : allRotors) {
            _maprotors.put(r.name(), r);
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (_rotors.length != rotors.length) {
            throw new EnigmaException("wrong number of total rotors");
        } else {
            for (int i = 0; i < rotors.length; i++) {
                for (String name : _maprotors.keySet()) {
                    if (name.equals(rotors[i].toUpperCase())) {
                        _rotors[i] = _maprotors.get(name);
                    }
                }
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        for (int i = 0; i < setting.length(); i++) {
            if (!_alphabet.contains(setting.charAt(i))) {
                throw new EnigmaException("character not in alphabet");
            }
        }
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("setting is of the wrong length ");
        } else {
            for (int j = 1; j < setting.length() + 1; j++) {
                _rotors[j].set((setting.charAt(j - 1)));
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }
    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        int conversion = c;
        Boolean[] movingrotors = new Boolean[numRotors()];
        int rRotor = numRotors() - 1;
        movingrotors[rRotor] = true;
        for (int i = movingrotors.length - 1; i > 0; i--) {
            if (_rotors[i].atNotch()) {
                if (_rotors[i] instanceof MovingRotor
                        && _rotors[i - 1] instanceof MovingRotor) {
                    movingrotors[i] = true;
                    movingrotors[i - 1] = true;
                }
            } else  {
                movingrotors[i - 1] = false;
            }
        }
        movingrotors[rRotor] = true;
        for (int k = 0; k < movingrotors.length; k++) {
            if (movingrotors[k] == null) {
                movingrotors[k] = false;
            }
        }
        for (int j = 0; j  < movingrotors.length; j++) {
            if (movingrotors[j]) {
                _rotors[j].advance();
            }
        }
        if (_plugboard != null) {
            conversion = _plugboard.permute(conversion);
        }
        for (int i = _numRotors - 1; i >= 0; i--) {
            conversion = _rotors[i].convertForward(conversion);
        }
        for (int j = 1; j < _rotors.length; j++) {
            conversion = _rotors[j].convertBackward(conversion);
        }
        if (_plugboard != null) {
            conversion = _plugboard.permute(conversion);
        }
        return conversion;
    }


    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String conversion = "";
        for (int i = 0; i < msg.length(); i++) {
            conversion += (_alphabet.toChar
                   (convert(_alphabet.toInt(msg.charAt(i)))));
        }
        return conversion;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** number of pawls I have. */
    private int _pawls;

    /** number of rotors I have. */
    private int _numRotors;

    /** current plugboard. */
    private Permutation _plugboard;

    /**  where i will put my rotors in. */
    protected Rotor[] _rotors;

    /** hashmap of rotors. */
    private HashMap<String, Rotor> _maprotors;

}
