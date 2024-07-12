package mappify.setter;

import com.vicious.persist.annotations.Save;

public class TestObject5 {
    @Save
    public int int0 = 0;
    public int int1 = 1;

    @Save.Setter("int0")
    public void setInt0(int value) {
        int0 = value;
    }

    @Save("int1")
    public int getInt1(){
        return int1;
    }

    @Save.Setter("int1")
    public void setInt1(int value) {
        int1 = value;
    }
}
