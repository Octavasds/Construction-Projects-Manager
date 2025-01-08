package ServiceLayer;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
import ModelLayer.*;
import RepositoryLayer.IRepository;
import Exceptions.BusinessLogicException;

public class ContractService {
    private IRepository<Contract> contractRepository;

    /**
     * Description: Constructor
     * @param contractRepository
     */
    public ContractService(IRepository<Contract> contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Description: Creates a new Contract
     * @param client
     * @param project
     * @param contractType
     * @throws BusinessLogicException if a contract already exists for the project
     * @throws EntityNotFoundException if client or project is null
     * @throws DatabaseException if a repository-related error occurs
     */
    public void createContract(Client client, Project project, String contractType) {
        try {
            // Validate input parameters
            if (client == null) {
                throw new EntityNotFoundException("Client cannot be null.");
            }
            if (project == null) {
                throw new EntityNotFoundException("Project cannot be null.");
            }

            // Check if a contract already exists for the given project
            for (Contract existingContract : contractRepository.getAll()) {
                if (existingContract.getProject().equals(project)) {
                    throw new BusinessLogicException("A contract already exists for this project: " + project.getName());
                }
            }

            // Create contract based on type
            Contract contract;
            if ("Construction".equals(contractType)) {
                contract = new ConstructionContract("Terms", project, client, 0, 0);
            } else {
                contract = new MaintenanceContract("Terms", project, client, 6, 1500);
            }

            // Add contract to repository
            contractRepository.add(contract);

            // Link the contract to the client
            client.addContract(contract);

        } catch (RuntimeException e) {
            throw new DatabaseException("Error while creating contract: " + e.getMessage(), e);
        }
    }
}