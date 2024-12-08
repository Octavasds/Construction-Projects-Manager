package ServiceLayer;
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
     * @throws BusinessLogicException if contract type is unsupported
     */
    public void createContract(Client client, Project project, String contractType) {
        // Validate contract type for business logic
        if ("Construction".equals(contractType)) {
            Contract contract = new ConstructionContract("Terms", project, client, 0, 0);
            contractRepository.add(contract);
            client.addContract(contract);
        } else if ("Maintenance".equals(contractType)) {
            Contract contract = new MaintenanceContract("Terms", project, client, 6, 1500);
            contractRepository.add(contract);
            client.addContract(contract);
        } else {
            throw new BusinessLogicException("Unsupported contract type: " + contractType);
        }
    }
}
