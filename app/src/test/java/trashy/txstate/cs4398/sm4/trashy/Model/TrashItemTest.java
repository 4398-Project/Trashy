package trashy.txstate.cs4398.sm4.trashy.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrashItemTest {

    @Test
    public void getTrashType() {
        String expected = "plastic";
        String actual;

        TrashItem testTrash = new TrashItem(null,"plastic",null,true);
        actual = testTrash.getTrashType();

        assertEquals(expected,actual);
    }

    @Test
    public void getPointWorth() {
        Integer expected = 45;
        Integer actual;

        TrashItem testTrash = new TrashItem(null,"glass",null,true);
        actual = testTrash.getPointWorth();

        assertEquals(expected,actual);
    }
}