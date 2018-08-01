package trashy.txstate.cs4398.sm4.trashy;

public class TrashItem {
    private String trashDescription;
    private String trashType;
    private String locationFound;
    private boolean recyclable;
    private Integer pointWorth;

    public TrashItem(String trashDescription, String trashType, String locationFound, boolean recyclable, Integer pointWorth) {
        this.trashDescription = trashDescription;
        setTrashType(trashType);
        this.locationFound = locationFound;
        this.recyclable = recyclable;
        this.pointWorth = pointWorth;
    }

    public String getTrashDescription() {
        return trashDescription;
    }

    public void setTrashDescription(String trashDescription) {
        this.trashDescription = trashDescription;
    }

    public String getTrashType() {
        return trashType;
    }

    public void setTrashType(String trashType) {
        switch (trashType.toLowerCase()){
            case "plastic":
                this.trashType = trashType;
                break;
            case "paper" :
                this.trashType = trashType;
                break;
            case "metal" :
                this.trashType = trashType;
                break;
                default:
                    this.trashType = "unknown";
                    break;
        }
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public boolean isRecyclable() {
        return recyclable;
    }

    public void setRecyclable(boolean recyclable) {
        this.recyclable = recyclable;
    }

    public Integer getPointWorth() {
        return pointWorth;
    }

    public void setPointWorth(Integer pointWorth) {
        this.pointWorth = pointWorth;
    }
}
