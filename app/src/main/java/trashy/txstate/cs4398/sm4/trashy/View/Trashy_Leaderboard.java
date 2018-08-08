package trashy.txstate.cs4398.sm4.trashy.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import trashy.txstate.cs4398.sm4.trashy.Model.Submission;
import trashy.txstate.cs4398.sm4.trashy.Model.User;
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Sorted ArrayList of submnission
        ArrayList<Submission> sortedSubmissions = sortSubs(listOfSub());

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

        entry1.setText(makeEntry(sortedSubmissions.get(0)));
        entry2.setText(makeEntry(sortedSubmissions.get(1)));
        entry3.setText(makeEntry(sortedSubmissions.get(2)));
        entry4.setText(makeEntry(sortedSubmissions.get(3)));
        entry5.setText(makeEntry(sortedSubmissions.get(4)));
        entry6.setText(makeEntry(sortedSubmissions.get(5)));
        entry7.setText(makeEntry(sortedSubmissions.get(6)));
        entry8.setText(makeEntry(sortedSubmissions.get(7)));
        entry9.setText(makeEntry(sortedSubmissions.get(8)));
        entry10.setText(makeEntry(sortedSubmissions.get(9)));

    }

    private ArrayList<Submission> listOfSub() {
        //ForTesting
        ArrayList<Submission> testList = new ArrayList<>();
        for (Integer i = 0; i < 10; i++){
            User user = new User();
            user.setUsername("usr" + i.toString());
            Submission sub = new Submission(user,i.toString());
            sub.setTotalPoints(i);
            testList.add(sub);
        }
        return testList;
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
        sortedList = new ArrayList<Submission>(unsortedList.subList(0, 10));
        return sortedList;
    }

    private String makeEntry(Submission sub){
        return (sub.getId().toString() + "       Score : " + sub.getTotalPoints());
    }

}
