package enigma;




/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Michael Chien
 */
class Alphabet {

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        _chars = chars;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }


    /** Returns the size of the alphabet. */
    int size() {
        return _chars.length();
    }

    /** Returns true if preprocess(CH) is in this alphabet. */
    boolean contains(char ch) {
        for (int index = 0; index < size(); index++) {
            if (_chars.charAt(index) == ch) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        if (0 <= index && index < size()) {
            return _chars.charAt(index);
        } else {
            throw new EnigmaException(" character not in the current alph ");
        }
    }

    /** Returns the index of character preprocess(CH), which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        for (int index = 0; index < size(); index++) {
            if (_chars.charAt(index) == ch) {
                return index;
            }
        }
        throw new EnigmaException("character is not in the current alphabet");
    }


    /** my chars. */
    private String _chars;
}
