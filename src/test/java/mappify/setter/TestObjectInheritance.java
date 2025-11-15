package mappify.setter;

import com.vicious.persist.annotations.Save;

public class TestObjectInheritance extends TestObject5{
    public boolean worked0 = false;
    public boolean worked1 = false;

    @Save.Setter("int0")
    public void setInt00(int value) {
        int0 = value;
        worked0 = true;
    }

    @Save.Setter("int1")
    public void setInt10(int value) {
        int1 = value;
        worked1 = true;
    }
}
