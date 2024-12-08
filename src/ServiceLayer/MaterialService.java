package ServiceLayer;
import Exceptions.BusinessLogicException;
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
     * @throws BusinessLogicException if quantity or unitPrice are invalid
     */
    public void createMaterial(String name, String provider, int quantity, float unitPrice) {
        // Logica specifică legată de valori invalide (în ServiceLayer, doar logica complexă)
        if (quantity <= 0) {
            throw new BusinessLogicException("Quantity must be greater than zero.");
        }
        if (unitPrice <= 0) {
            throw new BusinessLogicException("Unit price must be greater than zero.");
        }

        Material newMaterial = new Material(name, provider, quantity, unitPrice);
        materialRepository.add(newMaterial);
    }
}