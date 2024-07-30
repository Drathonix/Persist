package mappify;

import com.vicious.persist.annotations.Save;

public class TestObject1 {
    @Save(description = "This is a test comment. It is long enough to be multi-line. I am adding some extra comment lore. This comment was written at 3:28 PM on 7/30/2024. It was made to test if comments were written (and ignored properly). 23089231089213089e9dsa90sjisjk432ljk234jads q23e 423jk e dkjsajfejk435ij34234klj234kjdnkasm r32 n23r kdsakklclkdgjkt43jkjkresac")
    public byte byte0 = 120;

    @Save
    public short short0 = 18787;

    @Save
    public int int0 = Integer.MAX_VALUE;

    @Save
    public long long0 = Long.MAX_VALUE;

    @Save
    public float float0 = 23.382F;

    @Save
    public double double0 = 233244329802308923.33;

    @Save("thisbyteisspecial")
    public Byte byte1 = 120;

    @Save
    public Short short1 = 18787;

    @Save
    public Integer int1 = Integer.MAX_VALUE;

    @Save
    public Long long1 = Long.MAX_VALUE;

    @Save
    public Float float1 = 23.382F;

    @Save
    public Double double1 = 233244329802308923.33;

    @Save
    public char char0 = 'a';

    @Save
    public boolean boolean0 = true;

    @Save
    public Character char1 = 'b';

    @Save
    public Boolean boolean1 = false;

    @Save
    public static byte sbyte0 = 120;

    @Save("thisshortisspecial")
    public static short sshort0 = 18787;

    @Save
    public static int sint0 = Integer.MAX_VALUE;

    @Save
    public static long slong0 = Long.MAX_VALUE;

    @Save
    public static float sfloat0 = 23.382F;

    @Save
    public static double sdouble0 = 233244329802308923.33;

    @Save
    public static Byte sbyte1 = 120;

    @Save
    public static Short sshort1 = 18787;

    @Save
    public static Integer sint1 = Integer.MAX_VALUE;

    @Save
    public static Long slong1 = Long.MAX_VALUE;

    @Save
    public static Float sfloat1 = 23.382F;

    @Save
    public static Double sdouble1 = 233244329802308923.33;

    @Save
    public static char schar0 = 'a';

    @Save
    public static boolean sboolean0 = true;

    @Save
    public static Character schar1 = 'b';

    @Save
    public static Boolean sboolean1 = false;
}
