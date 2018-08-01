package trashy.txstate.cs4398.sm4.trashy;

public class User {
    private String username;
    private Integer totalPoints;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void addPoints(Integer pts){
        this.totalPoints += pts;
    }

    public void subPoints(Integer pts){
        if ((this.totalPoints - pts) >= 0){
            this.totalPoints -= pts;
        }
        else
            this.totalPoints = 0;
    }
}
