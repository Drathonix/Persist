package mappify.array;

import com.vicious.persist.annotations.Save;

public class ArrayTestObject {
    @Save
    public static int[] ints = new int[]{0,1,2,3,4,5};
    @Save
    public static boolean[][] nestedBool = new boolean[][]{new boolean[]{false,false},new boolean[]{false,true}};
    @Save
    public static double[][][] doubleNestedDoubles = new double[][][]{
            new double[][]{
                    new double[]{0.23,4.56},
                    new double[]{900D}},
            new double[][]{
                    new double[]{2387132987.219,-32.3211233
                    }
            }};
}
