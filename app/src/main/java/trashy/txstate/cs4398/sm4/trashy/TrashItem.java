package trashy.txstate.cs4398.sm4.trashy;

public class TrashItem {
    private String trashDescription;
    private String trashType;
    private String locationFound;
    private boolean recyclable;
    private Integer pointWorth;

    public TrashItem(String trashDescription, String trashType, String locationFound, boolean recyclable) {
        setTrashType(trashType, recyclable);
        this.trashDescription = trashDescription;
        this.locationFound = locationFound;
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

    public void setTrashType(String trashType, boolean recyclable) {
        switch (trashType.toLowerCase()){
            case "plastic":
                this.trashType = trashType;
                pointWorth = (recyclable) ? 5 : 10;
                break;
            case "paper" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 5 : 10;
                break;
            case "metal" :
                this.trashType = trashType;
                pointWorth = (recyclable) ? 5 : 10;
                break;
                default:
                    this.trashType = "N/A";
                    pointWorth = 0;
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

}
