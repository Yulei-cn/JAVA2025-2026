package sacADos;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;


public class SacADosTest {

    @Test
    public void testEstAdmissible() {
        Objet o1 = new Objet(10, new int[]{3, 2});
        Objet o2 = new Objet(8,  new int[]{4, 3});

        SacADos sac = new SacADos(
            2,
            new int[]{10, 10},
            List.of(o1, o2)
        );

        assertTrue(sac.estAdmissible(List.of(o1, o2)));  
    }
}
