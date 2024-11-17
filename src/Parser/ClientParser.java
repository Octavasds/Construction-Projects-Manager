package Parser;

import ModelLayer.Client;

public class ClientParser implements EntityParser<Client> {
    @Override
    public String toString(Client obj) {
        return obj.toString();
    }

    @Override
    public Client fromString(String data) {
        return Client.fromString(data);
    }
}

