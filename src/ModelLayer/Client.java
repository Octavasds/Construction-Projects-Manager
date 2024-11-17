package ModelLayer;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String address;
    private String phone;
    private String email;
    private List<Contract> contracts;

    public Client(String name, String address, String phone, String email, List<Contract> contracts) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contracts = contracts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    public String toString() {
        return this.name+','+this.address+','+this.email+','+this.phone;
    }

    public static Client fromString(String line) {
        String[] parts = line.split(",");
        List<Contract> contracts = new ArrayList<>();
        return new Client(parts[0], parts[1],parts[2],parts[3],contracts);
    }
}

