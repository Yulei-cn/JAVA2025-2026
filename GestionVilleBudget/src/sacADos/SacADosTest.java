package sacADos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe {@link SacADos}.
 *
 * Chaque test suit la méthode AAA (Arrange, Act, Assert).
 *
 * @author ZHU YULEI
 */
public class SacADosTest {

    private SacADos sac;
    private Objet o1, o2;

    @BeforeEach
    public void init() {
        o1 = new Objet(10, new int[]{1});
        o2 = new Objet(5,  new int[]{1});
        sac = new SacADos(1, new int[]{10}, List.of(o1, o2));
    }

    // ------------------------------------------------------------
    // TEST 1 : Utilité sur plusieurs objets
    // ------------------------------------------------------------

    @Test
    public void utiliteTotale_DeuxObjets_SommeCorrecte() {

        // Act
        int util = sac.utiliteTotale(List.of(o1, o2));

        // Assert
        assertEquals(15, util);
    }

    // ------------------------------------------------------------
    // TEST 2 : Liste vide
    // ------------------------------------------------------------

    @Test
    public void utiliteTotale_ListeVide_RetourneZero() {

        // Act
        int util = sac.utiliteTotale(List.of());

        // Assert
        assertEquals(0, util);
    }

    // ------------------------------------------------------------
    // TEST 3 : Un seul objet
    // ------------------------------------------------------------

    @Test
    public void utiliteTotale_UnObjet_RetourneUtiliteObjet() {

        // Act
        int util = sac.utiliteTotale(List.of(o1));

        // Assert
        assertEquals(10, util);
    }
}
