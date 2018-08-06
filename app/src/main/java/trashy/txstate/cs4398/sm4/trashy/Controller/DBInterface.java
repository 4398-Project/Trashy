package trashy.txstate.cs4398.sm4.trashy.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;

public class DBInterface {
    FirebaseDatabase database;
    DatabaseReference reference;

    public DBInterface(FirebaseDatabase database, DatabaseReference reference) {
        this.database = database;
        this.reference = reference;
    }

    public void uploadSubmission(Submission submission){
        reference.child("submissions").child(submission.getId().toString()).setValue(submission);
    }
}
