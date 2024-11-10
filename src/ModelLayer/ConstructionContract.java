package ModelLayer;

public class ConstructionContract extends Contract {
    private float penalties;
    private float totalCost;

    public ConstructionContract(String terms, Project project, Client client, float penalties, float totalCost) {
        super(terms, project, client);
        this.penalties = penalties;
        this.totalCost = totalCost;
    }

    public float getPenalties() {
        return penalties;
    }

    public void setPenalties(float penalties) {
        this.penalties = penalties;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
