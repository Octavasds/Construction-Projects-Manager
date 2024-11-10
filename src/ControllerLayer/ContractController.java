package ControllerLayer;
import ModelLayer.Client;
import ModelLayer.Project;
import ServiceLayer.ContractService;

public class ContractController {
    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    public void createContract(Client client, Project project, String contractType) {
        contractService.createContract(client, project, contractType);
    }
}

