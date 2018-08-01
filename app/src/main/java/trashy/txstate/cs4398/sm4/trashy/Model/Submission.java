package trashy.txstate.cs4398.sm4.trashy.Model;

import java.util.ArrayList;

public class Submission {
    private Integer id;
    private Integer lastId = 0; // must be pulled from DB
    private Integer totalPoints;
    private User user;
    private ArrayList<TrashItem> trashBin;

    public Submission(User user) {
        this.user = user;
        //lastID must be pulled from server
        id = lastId + 1;

        //Point algorithm (adds up total points of all trash in trashBin array list
        for (TrashItem trashItem : trashBin) {
          totalPoints += trashItem.getPointWorth();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<TrashItem> getTrashBin() {
        return trashBin;
    }

    public void setTrashBin(ArrayList<TrashItem> trashBin) {
        this.trashBin = trashBin;
    }
}
