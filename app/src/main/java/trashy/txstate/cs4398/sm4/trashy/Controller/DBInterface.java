package trashy.txstate.cs4398.sm4.trashy.Controller;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;

/**
 * Interfaces with firebase
 */
public class DBInterface {
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer lastID;
    String username;

    /**
     * instantiates DBInterface
     *
     * @param database the firebase server represented as an object
     * @param username user's username
     */
    public DBInterface(FirebaseDatabase database, String username) {
        this.database = database;
        this.reference = database.getReference("Subs");
        this.username = username;
        //Get last id;
        lastID = 11;

    }

    /**
     * Uploads a submission object to firebase
     *
     * @param submission contains username and information about the trash (converted into a score)
     */
    public void uploadSubmission(Submission submission) {
        reference.child("submissions").child(username.substring(0,8)).setValue(submission);
    }

    /**
     * generates an ID in the form of username
     *
     * @return ID in the form of username
     */
    public String genID(){
        return username;
    }

    /**
     * gets reference from firebase
     *
     * @return reference from firebase
     */
    public DatabaseReference getReference() {
        return reference;
    }
}