package enigma;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Michael Chien
 */
public class MachineTest {



    /** private variables go here */
    private  static final  ArrayList<Rotor> ALLROTORS = new ArrayList<>();
    static {
        ALLROTORS.add(new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
                        + "(MP) (RX) (SZ) (TV)", new Alphabet())));
        ALLROTORS.add(new Reflector("C",
                new Permutation("(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) "
                        + "(PW) (QZ) (SX) (UY)", UPPER)));
        ALLROTORS.add(new FixedRotor("BETA",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", UPPER)));
        ALLROTORS.add(new FixedRotor("GAMMA",
                new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", UPPER)));
        ALLROTORS.add(new MovingRotor("I",
                new Permutation(
                        "(AELTPHQXRU) (BKNW) (CMOY) "
                                + "(DFG) (IV) (JZ) (S)", UPPER), "Q"));
        ALLROTORS.add(new MovingRotor("II",
                new Permutation(
                        "(FIXVYOMW) (CDKLHUP) (ESZ) "
                                + "(BJ) (GR) (NT) (A) (Q)", UPPER), "E"));
        ALLROTORS.add(new MovingRotor("III",
                new Permutation(
                        "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", UPPER), "V"));
        ALLROTORS.add(new MovingRotor("IV",
                new Permutation(
                        "(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", UPPER), "J"));
        ALLROTORS.add(new MovingRotor("V",
                new Permutation(
                        "(AVOLDRWFIUQ) (BZKSMNHYC) (EGTJPX)", UPPER), "Z"));
        ALLROTORS.add(new MovingRotor("VI",
                new Permutation(
                        "(AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)", UPPER), "ZM"));
        ALLROTORS.add(new MovingRotor("VII",
                new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ)", UPPER), "ZM"));
        ALLROTORS.add(new MovingRotor("VIII",
                new Permutation(
                        "(AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)", UPPER), "ZM"));
    }
    @Test
    public void testInsertRotors() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s = {"C", "GAMMA", "I", "II", "III"};
        machine.insertRotors(s);
        assertEquals(ALLROTORS.get(1), machine._rotors[0]);
        assertEquals(ALLROTORS.get(3), machine._rotors[1]);
        assertEquals(ALLROTORS.get(4), machine._rotors[2]);
        assertEquals(ALLROTORS.get(5), machine._rotors[3]);
        assertEquals(ALLROTORS.get(6), machine._rotors[4]);
    }
    @Test
    public void testSetRotors() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s = {"C", "GAMMA", "I", "II", "III"};
        String setting = "FBCZ";
        machine.insertRotors(s);
        machine.setRotors(setting);
        assertEquals("reflector does not have a setting", 0,
                machine._rotors[0].setting());
        assertEquals(5, machine._rotors[1].setting());
        assertEquals(1, machine._rotors[2].setting());
        assertEquals(2, machine._rotors[3].setting());
        assertEquals(25, machine._rotors[4].setting());
    }
    @Test
    public void testConvertInt() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s = {"B", "BETA", "III", "IV", "I"};
        String setting = "AXLE";
        machine.setPlugboard(new Permutation("(YF) (ZH)", UPPER));
        machine.insertRotors(s);
        machine.setRotors(setting);
        assertEquals(25, machine.convert(24));
    }
    @Test
    public void testConvertInt2() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s = {"B", "BETA", "III", "IV", "I"};
        String setting = "ABJQ";
        machine.setPlugboard(new Permutation("(YF) (ZH)", UPPER));
        machine.insertRotors(s);
        machine.setRotors(setting);
    }

    @Test
    public void testConvertMsg() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s = {"B", "BETA", "III", "IV", "I"};
        String setting = "AXLE";
        machine.setPlugboard(new Permutation("(YF) (ZH)", UPPER));
        machine.insertRotors(s);
        machine.setRotors(setting);
        assertEquals("Z", machine.convert("Y"));
    }
    @Test
    public void testConvertMsg2() {
        Machine machine = new Machine(UPPER, 5, 3, ALLROTORS);
        String[] s1 = {"B", "BETA", "I", "II", "III"};
        String setting2 = "AAAA";
        machine.insertRotors(s1);
        machine.setRotors(setting2);
        machine.setPlugboard(new Permutation("(AQ) (EP)", UPPER));
        assertEquals("IHBDQQMTQZ", machine.convert("HELLOWORLD"));
    }

}

