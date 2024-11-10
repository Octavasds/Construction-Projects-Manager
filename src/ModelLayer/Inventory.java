package ModelLayer;

import java.util.List;
import java.util.Objects;

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

    public boolean hasMaterial(Material material){
        for(Material mat: this.materials){
            if(Objects.equals(mat,material))
                return true;
        }
        return false;
    }
}

