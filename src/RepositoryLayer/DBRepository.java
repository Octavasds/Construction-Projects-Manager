package RepositoryLayer;

import ModelLayer.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBRepository<T> implements IRepository<T> {
    private final String tableName;
    private final Connection connection;
    private final Map<Integer, T> storage = new HashMap<>();

    public DBRepository(String tableName) {
        this.tableName = tableName;

        // Establish connection
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project_management",
                    "root",
                    "2004"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e.getMessage());
        }

        loadFromDatabase();
    }

    private void loadFromDatabase() {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                T entity = mapEntity(rs);
                int id = rs.getInt("id");
                storage.put(id, entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading data from the database: " + e.getMessage());
        }
    }

    // Maps a database row to an entity
    @SuppressWarnings("unchecked")
    private T mapEntity(ResultSet rs) throws SQLException {
        switch (tableName.toLowerCase()) {
            case "employees":
                return (T) mapEmployee(rs);
            case "clients":
                return (T) mapClient(rs);
            case "projects":
                return (T) mapProject(rs);
            default:
                throw new SQLException("Mapping for table " + tableName + " not implemented");
        }
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if ("Worker".equals(type)) {
            return new Worker(
                    rs.getString("lastName"),
                    rs.getString("firstName"),
                    rs.getString("role"),
                    rs.getFloat("salary"),
                    null,
                    rs.getString("extra")
            );
        } else if ("Engineer".equals(type)) {
            return new Engineer(
                    rs.getString("lastName"),
                    rs.getString("firstName"),
                    rs.getString("role"),
                    rs.getFloat("salary"),
                    null,
                    rs.getString("extra")
            );
        } else {
            throw new SQLException("Unknown employee type: " + type);
        }
    }

    private Client mapClient(ResultSet rs) throws SQLException {
        return new Client(
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getString("email"),
                null
        );
    }

    private Project mapProject(ResultSet rs) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Map basic project details
        String name = rs.getString("name");
        String location = rs.getString("location");
        Date beginDate = null, finalDate = null;

        try {
            beginDate = dateFormat.parse(rs.getString("beginDate"));
            finalDate = dateFormat.parse(rs.getString("finalDate"));
        } catch (ParseException e) {
            throw new SQLException("Invalid date format: " + e.getMessage());
        }

        float budget = rs.getFloat("budget");

        // Get associated client
        int clientId = rs.getInt("client_id");
        Client client = null;
        if (clientId != 0) {
            IRepository<Client> clientRepo = new DBRepository<>("clients");
            client = clientRepo.getById(clientId);
        }

        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(
                "SELECT employee_id FROM project_employees WHERE project_id = ?")) {
            pstmt.setInt(1, rs.getInt("id"));
            ResultSet empRs = pstmt.executeQuery();
            IRepository<Employee> employeeRepo = new DBRepository<>("employees");

            while (empRs.next()) {
                int employeeId = empRs.getInt("employee_id");
                Employee employee = employeeRepo.getById(employeeId);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        }

        // Return the constructed Project
        return new Project(name, location, beginDate, finalDate, budget, client, employees, new ArrayList<>());
    }

    @Override
    public void add(T entity) {
        try {
            switch (tableName.toLowerCase()) {
                case "employees":
                    addEmployee((Employee) entity);
                    break;
                case "clients":
                    addClient((Client) entity);
                    break;
                case "projects":
                    addProject((Project) entity);
                    break;
                default:
                    throw new IllegalArgumentException("Add method not implemented for " + tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding entity to the database: " + e.getMessage());
        }
    }

    private void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (lastName, firstName, role, salary, type,extra) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getRole());
            pstmt.setFloat(4, employee.getSalary());
            pstmt.setString(5, employee instanceof Worker ? "Worker" : "Engineer");
            pstmt.setString(6,"tehnician");
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                storage.put(rs.getInt(1), (T) employee);
            }
        }
    }

    private void addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (name, address, phone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getAddress());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getEmail());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                storage.put(rs.getInt(1), (T) client);
            }
        }
    }

    private void addProject(Project project) throws SQLException {
        String query = "INSERT INTO projects (name, location, beginDate, finalDate, budget, client_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getLocation());
            pstmt.setDate(3, new java.sql.Date(project.getBeginDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(project.getFinalDate().getTime()));
            pstmt.setFloat(5, project.getBudget());
            pstmt.setObject(6, project.getClient() != null ? getID((T) project.getClient()) : null);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                storage.put(rs.getInt(1), (T) project);
            }

            // Insert into project_employees table
            for (Employee employee : project.getEmployees()) {
                int employeeId = getID((T) employee);
                if (employeeId != -1) {
                    addProjectEmployee(rs.getInt(1), employeeId);
                }
            }
        }
    }

    private void addProjectEmployee(int projectId, int employeeId) throws SQLException {
        String query = "INSERT INTO project_employees (project_id, employee_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(int id, T entity) {
        try {
            switch (tableName.toLowerCase()) {
                case "employees":
                    updateEmployee(id, (Employee) entity);
                    break;
                case "clients":
                    updateClient(id, (Client) entity);
                    break;
                default:
                    throw new IllegalArgumentException("Update method not implemented for " + tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating entity in the database: " + e.getMessage());
        }
    }

    private void updateEmployee(int id, Employee employee) throws SQLException {
        String query = "UPDATE employees SET firstName = ?, lastName = ?, role = ?, salary = ?, type = ?, extra = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getRole());
            pstmt.setFloat(4, employee.getSalary());
            pstmt.setString(5, employee instanceof Worker ? "Worker" : "Engineer");
            pstmt.setString(6,"tehnician");
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        }
        storage.put(id, (T) employee); // Update the in-memory storage
    }

    private void updateClient(int id, Client client) throws SQLException {
        String query = "UPDATE clients SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getAddress());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getEmail());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
        storage.put(id, (T) client); // Update the in-memory storage
    }

    public void updateProject(int id, Project project,int clientID) throws SQLException {
        String query = "UPDATE projects SET name = ?, location = ?, beginDate = ?, finalDate = ?, budget = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getLocation());
            pstmt.setDate(3, new java.sql.Date(project.getBeginDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(project.getFinalDate().getTime()));
            pstmt.setFloat(5, project.getBudget());
            pstmt.setInt(6, project.getClient() != null ? clientID : null);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        }

        // Update project_employees table
        String deleteEmployeesQuery = "DELETE FROM project_employees WHERE project_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteEmployeesQuery)) {
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
        }

        for (Employee employee : project.getEmployees()) {
            int employeeId = getID((T) employee);
            if (employeeId != -1) {
                addProjectEmployee(id, employeeId);
            }
        }

        storage.put(id, (T) project); // Update the in-memory storage
    }


    @Override
    public void delete(T entity) {
        int entityId = getID(entity); // This method will retrieve the entity's ID

        if (entityId == -1) {
            System.out.println("Entity does not have a valid ID.");
            return;
        }

        try {
            // Step 1: Delete from the appropriate table based on the entity's class (Project, Client, Employee, etc.)
            String deleteQuery = getDeleteQuery(entity);  // A helper method to get the delete query
            try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, entityId);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Step 2: If the entity was successfully deleted from the database, remove it from the storage map
                    storage.remove(entityId);
                    System.out.println(entity.getClass().getSimpleName() + " with id " + entityId + " has been deleted.");
                } else {
                    System.out.println("No entity found with id " + entityId + ".");
                }
            }


            handleRelatedEntitiesDeletion(entity);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while deleting entity with ID " + entityId + ": " + e.getMessage());
        }
    }



    // Helper method to get the correct delete query based on entity type
    private String getDeleteQuery(T entity) {
        if (entity instanceof Project) {
            return "DELETE FROM projects WHERE id = ?";
        } else if (entity instanceof Employee) {
            return "DELETE FROM employees WHERE id = ?";
        } else if (entity instanceof Client) {
            return "DELETE FROM clients WHERE id = ?";
        }
        return "";
    }


    private void handleRelatedEntitiesDeletion(T entity) throws SQLException {
        if (entity instanceof Project) {
            String deleteProjectEmployeesQuery = "DELETE FROM project_employees WHERE project_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteProjectEmployeesQuery)) {
                pstmt.setInt(1, getID(entity));
                pstmt.executeUpdate();
            }
        }
        if (entity instanceof Employee) {
            String deleteProjectEmployeesQuery = "DELETE FROM project_employees WHERE employee_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteProjectEmployeesQuery)) {
                pstmt.setInt(1, getID(entity));
                pstmt.executeUpdate();
            }
        }

    }



    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T getById(int id) {
        return storage.get(id);
    }

    @Override
    public Integer getID(T entity) {
        for (Map.Entry<Integer, T> entry : storage.entrySet()) {
            if (entry.getValue().equals(entity)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
