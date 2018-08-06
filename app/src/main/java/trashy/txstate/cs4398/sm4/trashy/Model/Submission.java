package trashy.txstate.cs4398.sm4.trashy.Model;

import java.util.ArrayList;

/**
 * The type Submission.
 */
public class Submission {
    private String id;
    private Integer lastId = 0; // must be pulled from DB
    private Integer totalPoints;
    private User user;
    private TrashItem trashItem;

    /**
     * Instantiates a new Submission.
     *
     * @param user the user
     */
    public Submission(User user, String id) {
        this.user = user;
        this.id = id;
    }

    /**
     * Add trash item.
     *
     * @param trashItem the trash item
     */
    public void addTrashItem(TrashItem trashItem, Integer numberOfTrashItems){
        this.trashItem = trashItem;
        this.totalPoints = trashItem.getPointWorth() * numberOfTrashItems;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public  String getId() {
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

}