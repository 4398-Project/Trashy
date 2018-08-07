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

    public DBInterface(FirebaseDatabase database) {
        this.database = database;
        this.reference = database.getReference("Subs");

        //Get last id;
        lastID = 11;

        Query lastQ = reference.child("submissions").orderByChild("id").limitToLast(1);
        lastQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    Submission lastSub = dataSnapshot.child("id").getValue(Submission.class);
                    lastID = Integer.parseInt(lastSub.getId());
                }catch (NullPointerException ex){
                    lastID = 50;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void uploadSubmission(Submission submission) {
        reference.child("submissions").child(genID()).setValue(submission);
    }

    public String genID(){
        Integer ID;
        try{
            ID = lastID + 1;
        }catch (NullPointerException ex){
            ID = 200;
        }
        return ID.toString();
    }

    public DatabaseReference getReference() {
        return reference;
    }
}