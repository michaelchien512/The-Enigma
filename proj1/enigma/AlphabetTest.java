package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

/** Junit tests for Alphabet */
public class AlphabetTest {

    @Test
    public void size() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(26, a.size());
    }
    @Test
    public void testcontains() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(true, a.contains('A'));
        assertEquals(false, a.contains('a'));
    }
    @Test
    public void testToChar() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals('A', a.toChar(0));
        assertEquals('Z', a.toChar(25));
    }
    @Test
    public void testToInt() {
        Alphabet a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(0, a.toInt('A'));
        assertEquals(1, a.toInt('B'));
        assertEquals(18, a.toInt('S'));
        assertEquals(25, a.toInt('Z'));
    }


}

