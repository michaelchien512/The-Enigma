package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;
import static enigma.TestUtils.*;
/** The suite of all JUnit tests for the Permutation class.
 *  @author Michael Chien
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    /** tests that when i invert a number i get the number of the character
     * to the left of it
     */
    @Test
    public void testInvertInt() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) "
                + "(JC) (V)", new Alphabet());
        assertEquals(13,
                p.invert(7));
        assertEquals(7, p.invert(15));
        assertEquals(9, p.invert(2));
        assertEquals(2, p.invert(9));
        assertEquals(21, p.invert(21));
    }
    /** tests that I permute an integer to the right integer */
    @Test
    public void testIntPermute() {
        Permutation p = new Permutation("(PNH) "
                + "(ABDFIKLZYXW) (JC) (V)", new Alphabet());
        assertEquals(7,  p.permute(13));
        assertEquals(15, p.permute(7));
        assertEquals(1, p.permute(0));
        assertEquals(3, p.permute(27));
        assertEquals(21, p.permute(21));
        assertEquals(3, p.permute(-25));
        Permutation p1 = new Permutation("(V)", new Alphabet());
        assertEquals(21, p.permute(21));
    }

    /** tests that I permute a character to the right character using
     * the intPermute method
     */
    @Test
    public void testCharPermute() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) "
                + "(JC) (V)", new Alphabet());
        assertEquals('H', p.permute('N'));
        assertEquals('P', p.permute('H'));
        assertEquals('V', p.permute('V'));
    }

    /** tests that I when i invert a character i get the character
     * to the left of it
     */
    @Test
    public void testInvertChar() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) "
                + "(JC) (V)", new Alphabet());
        assertEquals('N', p.invert('H'));
        assertEquals('V', p.invert('V'));
        assertEquals('W', p.invert('A'));
        Permutation p1 = new Permutation("", new Alphabet());
        assertEquals('A', p1.invert('A'));
    }
    /** tests to see if its a dearrangement */
    @Test
    public void testDerrangement() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) "
                + "(JC) (V)", new Alphabet());
        Permutation p1 = new Permutation("(PNH) "
                + "(ABDFIKLZYXW) (JC)", new Alphabet());
        assertEquals(false, p.derangement());
        assertEquals(true, p1.derangement());
    }
    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }
    @Test
    public void testOtherAlphabets() {
        Permutation p = new Permutation("(pnh) (abdfiklzyxw) (jc) (v)",
                new Alphabet("abcdefghijklmnopqrstuvwxyz"));
        assertEquals('n', p.invert('h'));
        assertEquals('v', p.invert('v'));
        assertEquals('w', p.invert('a'));
    }


}
