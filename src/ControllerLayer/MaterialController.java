package ControllerLayer;
import ServiceLayer.MaterialService;

public class MaterialController {
    private MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    public void createMaterial(String name, String provider, int quantity, float unitPrice) {
        materialService.createMaterial(name, provider, quantity, unitPrice);
    }
}
