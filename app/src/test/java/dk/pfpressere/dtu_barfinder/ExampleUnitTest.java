package dk.pfpressere.dtu_barfinder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void bearing_isCorrect() {
        assertEquals(300, CompassController.bearing(55.783332, 12.518184, 55.786386, 12.525538), 10);
    }
}