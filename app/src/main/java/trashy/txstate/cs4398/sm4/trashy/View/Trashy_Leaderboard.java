package trashy.txstate.cs4398.sm4.trashy.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;
import trashy.txstate.cs4398.sm4.trashy.R;

public class Trashy_Leaderboard extends AppCompatActivity {


    private TextView entry1;
    private TextView entry2;
    private TextView entry3;
    private TextView entry4;
    private TextView entry5;
    private TextView entry6;
    private TextView entry7;
    private TextView entry8;
    private TextView entry9;
    private TextView entry10;

    Intent changeActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeActivity = new Intent(Trashy_Leaderboard.this, Trash.class);
                    startActivity(changeActivity);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    changeActivity = new Intent(Trashy_Leaderboard.this, Login.class);
                    startActivity(changeActivity);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trashy__leaderboard);
        final List<String> submissions = new ArrayList<>();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Associate handles with UI
        entry1 = findViewById(R.id.user_LDB_Field01);
        entry2 = findViewById(R.id.user_LDB_Field02);
        entry3 = findViewById(R.id.user_LDB_Field03);
        entry4 = findViewById(R.id.user_LDB_Field04);
        entry5 = findViewById(R.id.user_LDB_Field05);
        entry6 = findViewById(R.id.user_LDB_Field06);
        entry7 = findViewById(R.id.user_LDB_Field07);
        entry8 = findViewById(R.id.user_LDB_Field08);
        entry10 = findViewById(R.id.user_LDB_Field10);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Subs");
        ref.child("submissions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    String value = child.getValue().toString();
                    submissions.add(value);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
