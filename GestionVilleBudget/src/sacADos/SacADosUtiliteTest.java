package sacADos;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SacADosUtiliteTest {

    @Test
    public void testUtiliteTotale() {
        Objet o1 = new Objet(10, new int[]{1});
        Objet o2 = new Objet(5,  new int[]{1});

        SacADos sac = new SacADos(1, new int[]{10}, List.of(o1, o2));

        assertEquals(15, sac.utiliteTotale(List.of(o1, o2)));
    }
}
