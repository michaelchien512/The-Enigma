package enigma;

import java.util.ArrayList;
import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Michael Chien
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = new ArrayList<>();
        String[] splitcycles;
        cycles = cycles.replace("(", "");
        cycles = cycles.replace(")", "");
        splitcycles = cycles.split(" ");
        for (String s : splitcycles) {
            _cycles.add(s);
        }
        for (String t : _cycles) {
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                if (!_alphabet.contains(c)) {
                    throw error("character does not appear in alphabet");
                }
            }
        }
    }




    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char translatep = _alphabet.toChar(wrap(p));
        int numberout = wrap(p);
        for (String item : _cycles) {
            int index = item.indexOf(translatep) + 1;
            if (item.contains(Character.toString(translatep))) {
                if (item.indexOf(translatep) == item.length() - 1) {
                    char letterout = item.charAt(0);
                    numberout = _alphabet.toInt(letterout);
                } else {
                    numberout = _alphabet.toInt(item.charAt(index));
                }
            }
        }
        return numberout;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char translatec = _alphabet.toChar(wrap(c));
        int numberout = wrap(c);
        for (String item : _cycles) {
            int stylechecker = item.length() - 1;
            int stylechecker2 = item.indexOf(translatec) - 1;
            if (item.contains(Character.toString(translatec))) {
                if (item.indexOf(translatec) == 0) {
                    numberout = _alphabet.toInt(item.charAt(stylechecker));
                } else {
                    numberout = _alphabet.toInt(item.charAt(stylechecker2));
                }
            }
        }
        return numberout;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int indexp = _alphabet.toInt(p);
        return _alphabet.toChar(permute(indexp));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        int indexc = _alphabet.toInt(c);
        return _alphabet.toChar(invert(indexc));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (String item: _cycles) {
            for (int i = 0; i < item.length(); i++) {
                if (Character.toString(item.charAt(i)).equals(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;


    /** Arraylist to store string of cycles. */
    private ArrayList<String> _cycles;
}
