package ModelLayer;

public class MaintenanceContract extends Contract {
    private float maintenanceInterval;
    private float maintenanceCost;

    public MaintenanceContract(String terms, Project project, Client client, float maintenanceInterval, float maintenanceCost) {
        super(terms, project, client);
        this.maintenanceInterval = maintenanceInterval;
        this.maintenanceCost = maintenanceCost;
    }

    public float getMaintenanceInterval() {
        return maintenanceInterval;
    }

    public void setMaintenanceInterval(float maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public float getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(float maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }
}
