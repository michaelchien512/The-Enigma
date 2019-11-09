package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Michael Chien
 */
class FixedRotor extends Rotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is given by PERM. */
    FixedRotor(String name, Permutation perm) {
        super(name, perm);
    }

    /** fixed rotor does not rotate. */
    @Override
    boolean rotates() {
        return false;
    }

    /** fixed rotors can not advance so do nothing. */
    @Override
    void advance() {
    }

}
