//Daniels
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
import java.util.Arrays;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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
        entry9 = findViewById(R.id.user_LDB_Field09);
        entry10 = findViewById(R.id.user_LDB_Field10);


        String value;
        final String[] submissionsList = new String [100];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Subs");
        ref.child("submissions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String delims = "[=,]+";
                String delims2 = "[=}]+";
                String[] tokens, tokens2;
                String [] topUsernames = new String[9999];
                String tempS;
                int [] submissionsCompare = new int[9999];
                int i, temp;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                i = 0;
                for (DataSnapshot child : children) {
                    String value = child.getValue().toString();
                    tokens = value.split(delims);//score
                    tokens2 = value.split(delims2);//username

                    submissionsCompare[i] = Integer.parseInt(tokens[1]);
                    topUsernames[i] = tokens2[4];

                    i++;
                }

                for (i = 0; i < submissionsCompare.length - 1; i++) {
                    for(int j = 0; j < submissionsCompare.length - 1; j++) {
                        if(submissionsCompare[i] < submissionsCompare[j + 1] && i < (j + 1)) {
                            temp = submissionsCompare [j + 1];
                            submissionsCompare [j + 1]= submissionsCompare [i];
                            submissionsCompare [i] = temp;

                            tempS = topUsernames [j + 1];
                            topUsernames [j + 1]= topUsernames [i];
                            topUsernames [i] = tempS;
                        }
                    }
                }

                for (i = 0; i < 10; i ++){
                    //10) gets indented strangely if this isn't done
                    if (i != 9)
                        submissionsList[i] = (i + 1) + ")\t\t" + submissionsCompare[i] + "\t\t" + topUsernames[i];
                    else
                        submissionsList[i] = (i + 1) + ") " + submissionsCompare[i] + "\t\t" + topUsernames[i];

                }
                entry1.setText((submissionsList[0] != null) ? submissionsList[0]: "N/A" );
                entry2.setText((submissionsList[1] != null) ? submissionsList[1]: "N/A" );
                entry3.setText((submissionsList[2] != null) ? submissionsList[2]: "N/A" );
                entry4.setText((submissionsList[3] != null) ? submissionsList[3]: "N/A" );
                entry5.setText((submissionsList[4] != null) ? submissionsList[4]: "N/A" );
                entry6.setText((submissionsList[5] != null) ? submissionsList[5]: "N/A" );
                entry7.setText((submissionsList[6] != null) ? submissionsList[6]: "N/A" );
                entry8.setText((submissionsList[7] != null) ? submissionsList[7]: "N/A" );
                entry9.setText((submissionsList[8] != null) ? submissionsList[8]: "N/A" );
                entry10.setText((submissionsList[9] != null) ? submissionsList[9]: "N/A" );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private ArrayList<Submission> sortSubs(ArrayList<Submission> unsortedList){

        Submission theChosenOne; // Temporary place holder
        ArrayList<Submission> sortedList;
        for (Integer i = 0; i < unsortedList.size(); i++){
            for (int j = 1; j < (unsortedList.size() - 1); j++){
                if (unsortedList.get(j-1).getTotalPoints() > unsortedList.get(j).getTotalPoints()){
                    theChosenOne = unsortedList.get(j-1);
                    unsortedList.add(j - 1, unsortedList.get(j));
                    unsortedList.add(j, theChosenOne);
                }
            }
        }
        //sortedList = new ArrayList<Submission>(unsortedList.subList(0, 10));
        return unsortedList;
    }
}