package mappify.special;

import com.vicious.persist.annotations.Save;

public class TestSpecialObject {
    //Test storing a class that is child of A.
    @Save
    public TestObjectA a = new TestObjectB();

    //Test storing a class that implements A.
    @Save
    public ITestInterfaceA b = new TestObjectC();
}
