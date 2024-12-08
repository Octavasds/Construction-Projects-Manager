package ServiceLayer;
import ModelLayer.Material;
import RepositoryLayer.IRepository;

public class MaterialService {
    private IRepository<Material> materialRepository;

    /**
     * Description: Constructor
     * @param materialRepository
     */
    public MaterialService(IRepository<Material> materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Description: Creates a new material
     * @param name
     * @param provider
     * @param quantity
     * @param unitPrice
     */
    public void createMaterial(String name, String provider, int quantity, float unitPrice) {
        Material newMaterial = new Material(name, provider, quantity, unitPrice);
        materialRepository.add(newMaterial);
        System.out.println("Material created successfully: " + newMaterial.getName());
    }
}