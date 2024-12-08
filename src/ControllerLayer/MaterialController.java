package ControllerLayer;
import Exceptions.ValidationException;
import ServiceLayer.MaterialService;

public class MaterialController {
    private MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    public void createMaterial(String name, String provider, int quantity, float unitPrice) {
        // Validate input parameters
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Material name cannot be null or empty.");
        }
        if (provider == null || provider.trim().isEmpty()) {
            throw new ValidationException("Provider cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than zero.");
        }
        if (unitPrice <= 0) {
            throw new ValidationException("Unit price must be greater than zero.");
        }

        materialService.createMaterial(name, provider, quantity, unitPrice);
    }
}
