package trashy.txstate.cs4398.sm4.trashy.Model;

import org.junit.Test;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;
import trashy.txstate.cs4398.sm4.trashy.Model.TrashItem;
import trashy.txstate.cs4398.sm4.trashy.Model.User;

import static org.junit.Assert.*;

/**
 * Local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SubmissionUnitTest {
    @Test
    public void pointsAreCorrect() {
        Integer expected = 250;
        Integer actual;


        TrashItem trashItem = new TrashItem("Test trash item","plastic","mockLocation",true);
        Submission testSub = new Submission();

        testSub.addTrashItem(trashItem, 10);

        actual = testSub.getTotalPoints();

        assertEquals(expected, actual);
    }

    @Test
    public void User_isCorrect(){
        String expected = "testUser@te";
        String actual;


        User idTestUser = new User();
        idTestUser.setUsername("testUser@te");

        Submission idTestSub = new Submission(idTestUser, null);



        actual = idTestSub.getUser().getUsername();

        assertEquals(expected,actual);
    }

}