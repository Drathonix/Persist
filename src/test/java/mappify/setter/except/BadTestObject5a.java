package mappify.setter.except;

import com.vicious.persist.annotations.Save;

public class BadTestObject5a {
    @Save
    public int int0 = 0;

    @Save.Setter("int0")
    public void setInt0(int value) {
        int0 = value;
    }

    @Save
    public int int1(){
        return int0;
    }
}
