package trashy.txstate.cs4398.sm4.trashy.Model;

import java.util.ArrayList;

/**
 * The type Submission.
 */
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

    /**
     * Instantiates a new Submission.
     *
     * @param user the user
     */
    public Submission(User user) {
        this.user = user;
        //lastID must be pulled from server
        id = getLastId() + 1;
    }

    /**
     * Add trash item.
     *
     * @param trashItem the trash item
     */
    public void addTrashItem(TrashItem trashItem){
        this.trashBin.add(trashItem);
        this.totalPoints += trashItem.getPointWorth();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets total points.
     *
     * @return the total points
     */
    public Integer getTotalPoints() {
        return totalPoints;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets trash bin.
     *
     * @return the trash bin
     */
    public ArrayList<TrashItem> getTrashBin() {
        return trashBin;
    }
}