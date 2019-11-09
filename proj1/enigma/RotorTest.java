package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

/** junit tests for the rotor class */
public class RotorTest {
    /** test that the numbers I put in are being converted forwards
     * accordng to permutations
     */
    @Test
    public void testConvertForwards() {
        Rotor r = new Rotor("R", new Permutation("(AELTPHQXRU) "
                + "(BKNW) (CMOY) (DFG) (IV) (JZ) (S)",
                new Alphabet()));
        r.set(5);
        assertEquals(8, r.convertForward(5));
    }
    /** test that the numbers I put in are being converted backwards
     * to the inverse of my permutation
     */
    @Test
    public void testConvertBackwards() {
        Rotor r = new Rotor("R", new Permutation("(AELTPHQXRU) (BKNW) "
                + "(CMOY) (DFG) (IV) (JZ) (S)",
                new Alphabet()));
        r.set(5);
        assertEquals(22, r.convertBackward(5));
    }

    @Test
    public void testAdvance() {
        Rotor r = new Rotor("R", new Permutation("(AELTPHQXRU) "
                + "(BKNW) (CMOY) (DFG) (IV) (JZ) (S)",
                new Alphabet()));
        r.set(5);
        r.advance();
        assertEquals(6, r.setting());
        r.set(-1);
        r.advance();
        assertEquals(0, r.setting());
        r.set(28);
        r.advance();
        assertEquals(3, r.setting());
        r.set(-4);
        r.advance();
        assertEquals(23, r.setting());
        Rotor r1 =  new Rotor("R", new Permutation("(ABC)",
                new Alphabet("ABC")));
        r1.set(2);
        r1.advance();
        assertEquals(0, r1.setting());
        r1.advance();
        assertEquals(1, r1.setting());
        r1.advance();
        assertEquals(2, r1.setting());
        r1.advance();
        assertEquals(0, r1.setting());
    }
}

