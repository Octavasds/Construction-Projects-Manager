package ServiceLayer;
import ModelLayer.*;
import RepositoryLayer.IRepository;

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
     */
    public void createContract(Client client, Project project, String contractType) {
        Contract contract;
        if ("Construction".equals(contractType)) {
            contract = new ConstructionContract("Terms", project, client, 0, 0);
        } else {
            contract = new MaintenanceContract("Terms", project, client, 6, 1500);
        }
        contractRepository.add(contract);
        client.addContract(contract);
    }
}

