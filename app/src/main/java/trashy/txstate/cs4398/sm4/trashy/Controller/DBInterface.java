package trashy.txstate.cs4398.sm4.trashy.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;

public class DBInterface {
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer lastID;

    public DBInterface(FirebaseDatabase database) {
        this.database = database;
        this.reference = database.getReference("Subs");

        //Get last id;
        lastID = 0;
    }

    public void uploadSubmission(Submission submission) {
        reference.child("submissions").child(genID()).setValue(submission);
    }

    public String genID(){
        Integer ID = lastID + 1;
        return ID.toString();
    }

    public DatabaseReference getReference() {
        return reference;
    }
}