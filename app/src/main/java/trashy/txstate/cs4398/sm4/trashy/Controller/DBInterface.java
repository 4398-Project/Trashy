package trashy.txstate.cs4398.sm4.trashy.Controller;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;

public class DBInterface {
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer lastID;
    String username;

    public DBInterface(FirebaseDatabase database, String username) {
        this.database = database;
        this.reference = database.getReference("Subs");
        this.username = username;
        //Get last id;
        lastID = 11;

    }

    public void uploadSubmission(Submission submission) {
        reference.child("submissions").child(username.substring(0,8)).setValue(submission);
    }

    public String genID(){
        return username;
    }

    public DatabaseReference getReference() {
        return reference;
    }
}