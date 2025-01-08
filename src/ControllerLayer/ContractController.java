package ControllerLayer;

import ModelLayer.Client;
import ModelLayer.Project;
import ServiceLayer.ContractService;
import Exceptions.ValidationException;

public class ContractController {
    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    public void createContract(Client client, Project project, String contractType) {
        // Validate input parameters
        if (client == null) {
            throw new ValidationException("Client cannot be null.");
        }
        if (project == null) {
            throw new ValidationException("Project cannot be null.");
        }
        if (contractType == null || contractType.trim().isEmpty()) {
            throw new ValidationException("Contract type cannot be null or empty.");
        }

        contractService.createContract(client, project, contractType);
    }
}
