package RepositoryLayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository<T> implements IRepository<T> {
    private final String filePath;
    private final EntityParser<T> parser;

    /**
     * Constructor for FileRepository
     *
     * @param filePath Path to the file for data storage.
     * @param parser   A parser to convert entities to/from string.
     */
    public FileRepository(String filePath, EntityParser<T> parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    @Override
    public void add(T entity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(parser.toString(entity));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }

    @Override
    public void delete(T entity) {
        List<T> allEntities = getAll();
        Integer idToDelete = getID(entity);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (T current : allEntities) {
                if (!getID(current).equals(idToDelete)) {
                    writer.write(parser.toString(current));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting from file: " + filePath, e);
        }
    }

    @Override
    public void update(int id, T entity) {
        List<T> allEntities = getAll();
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (T current : allEntities) {
                if (getID(current) == id) {
                    writer.write(parser.toString(entity));
                    updated = true;
                } else {
                    writer.write(parser.toString(current));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating file: " + filePath, e);
        }

        if (!updated) {
            throw new RuntimeException("Entity with ID " + id + " not found.");
        }
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(parser.fromString(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filePath, e);
        }
        return entities;
    }

    @Override
    public T getById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = parser.fromString(line);
                if (getID(entity) == id) {
                    return entity;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filePath, e);
        }
        return null; 
    }

    @Override
    public Integer getID(T entity) {
        return parser.getId(entity);
    }

    public interface EntityParser<T> {
        String toString(T obj);
        T fromString(String line);
        int getId(T obj);
    }

}
