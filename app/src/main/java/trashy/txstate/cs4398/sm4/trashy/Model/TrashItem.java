package trashy.txstate.cs4398.sm4.trashy.Model;

/**
 * The type Trash item.
 */
public class TrashItem {
    private String trashDescription;
    private String trashType;
    private String locationFound;
    private boolean recyclable;
    private Integer pointWorth;


    /**
     * Instantiates a new Trash item.
     *
     * @param trashDescription the trash description
     * @param trashType        the trash type
     * @param locationFound    the location found
     * @param recyclable       the recyclable
     */
    public TrashItem(String trashDescription, String trashType, String locationFound, boolean recyclable) {
        setTrashType(trashType, recyclable);
        this.trashDescription = trashDescription;
        this.locationFound = locationFound;
    }

    /**
     * Gets trash description.
     *
     * @return the trash description
     */
    public String getTrashDescription() {
        return trashDescription;
    }

    /**
     * Sets trash description.
     *
     * @param trashDescription the trash description
     */
    public void setTrashDescription(String trashDescription) {
        this.trashDescription = trashDescription;
    }

    /**
     * Gets trash type.
     *
     * @return the trash type
     */
    public String getTrashType() {
        return trashType;
    }

    /**
     * Sets trash type.
     *
     * @param trashType  the trash type
     * @param recyclable the recyclable
     */
    public void setTrashType(String trashType, boolean recyclable) {
        switch (trashType.toLowerCase()){
            case "plastic":
                this.trashType = trashType;
                pointWorth = (recyclable) ? 25 : 20;
                break;
            case "paper" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 15 : 10;
                break;
            case "metal" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 35 : 30;
                break;
            case "wood" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 30 : 25;
                break;
            case "glass" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 45 : 40;
                break;
            default:
                    this.trashType = "N/A";
                    pointWorth = 0;
                    break;
        }
    }

    /**
     * Gets location found.
     *
     * @return the location found
     */
    public String getLocationFound() {
        return locationFound;
    }

    /**
     * Sets location found.
     *
     * @param locationFound the location found
     */
    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    /**
     * Is recyclable boolean.
     *
     * @return the boolean
     */
    public boolean isRecyclable() {
        return recyclable;
    }

    /**
     * Sets recyclable.
     *
     * @param recyclable the recyclable
     */
    public void setRecyclable(boolean recyclable) {
        this.recyclable = recyclable;
    }

    /**
     * Gets point worth.
     *
     * @return the point worth
     */
    public Integer getPointWorth() {
        return pointWorth;
    }

}
