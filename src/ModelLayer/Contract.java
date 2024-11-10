package ModelLayer;

public abstract class Contract {
    protected String terms;
    protected Project project;
    protected Client client;

    public Contract(String terms, Project project, Client client) {
        this.terms = terms;
        this.project = project;
        this.client = client;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

