package trashy.txstate.cs4398.sm4.trashy.Model;

/**
 * The type User.
 */
public class User {
    private String username;
    private Integer totalPoints;

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * Add points.
     *
     * @param pts the pts
     */
    public void addPoints(Integer pts){
        this.totalPoints += pts;
    }

    /**
     * Sub points.
     *
     * @param pts the pts
     */
    public void subPoints(Integer pts){
        if ((this.totalPoints - pts) >= 0){
            this.totalPoints -= pts;
        }
        else
            this.totalPoints = 0;
    }
}
