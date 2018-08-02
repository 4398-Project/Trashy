package trashy.txstate.cs4398.sm4.trashy.Model;

import java.util.ArrayList;

public class Submission {
    private Integer id;
    private Integer lastId = 0; // must be pulled from DB
    private Integer totalPoints;
    private User user;
    private ArrayList<TrashItem> trashBin;

    private Integer getLastId(){
        //gets last id from server
        return 0;
    }
    public Submission(User user) {
        this.user = user;
        //lastID must be pulled from server
        id = getLastId() + 1;
    }

    public void addTrashItem(TrashItem trashItem){
        this.trashBin.add(trashItem);
        this.totalPoints += trashItem.getPointWorth();
    }

    public Integer getId() {
        return id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<TrashItem> getTrashBin() {
        return trashBin;
    }
}