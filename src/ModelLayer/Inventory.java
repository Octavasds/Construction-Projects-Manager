package ModelLayer;

import java.util.List;

public class Inventory {
    private List<Material> materials;

    public Inventory(List<Material> materials) {
        this.materials = materials;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}

