package ServiceLayer;

import ModelLayer.Material;
import RepositoryLayer.IRepository;
import Exceptions.ValidationException;

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
        // Input validation
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Material name cannot be null or empty.");
        }
        if (provider == null || provider.trim().isEmpty()) {
            throw new ValidationException("Provider cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than 0.");
        }
        if (unitPrice <= 0) {
            throw new ValidationException("Unit price must be greater than 0.");
        }

        Material newMaterial = new Material(name, provider, quantity, unitPrice);
        materialRepository.add(newMaterial);
        System.out.println("Material created successfully: " + newMaterial.getName());
    }
}
