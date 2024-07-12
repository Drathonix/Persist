package mappify.special;

import com.vicious.persist.annotations.Save;

public interface IBadInterface {
    @Save("i")
    int getI();
}
