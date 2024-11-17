package RepositoryLayer;

import Parser.EntityParser;

import java.io.*;
import java.util.*;

/**
 * A repository implementation that stores data in a file, using a Map to mimic in-memory behavior.
 */
public class FileRepository<T> implements IRepository<T> {
    private final String filePath;
    private final EntityParser<T> parser;
    private final Map<Integer, T> storage = new HashMap<>();
    private int currentId = 0;

    public FileRepository(String filePath, EntityParser<T> parser) {
        this.filePath = filePath;
        this.parser = parser;
        loadFromFile();
    }

    @Override
    public void add(T entity) {
        storage.put(currentId++, entity);
        saveToFile();
    }

    @Override
    public void delete(T entity) {
        Integer id = getID(entity);
        if (id != null) {
            storage.remove(id);
            saveToFile();
        }
    }

    @Override
    public void update(int id, T entity) {
        if (storage.containsKey(id)) {
            storage.put(id, entity);
            saveToFile();
        } else {
            throw new RuntimeException("Entity with ID " + id + " does not exist.");
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
        return null;
    }

    /**
     * Saves the current state of the storage map to the file.
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Map.Entry<Integer, T> entry : storage.entrySet()) {
                writer.write(entry.getKey() + ";" + parser.toString(entry.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to file: " + filePath, e);
        }
    }

    /**
     * Loads data from the file into the storage map.
     */
    private void loadFromFile() {
        storage.clear();
        currentId = 0;
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                int id = Integer.parseInt(parts[0]);
                T entity = parser.fromString(parts[1]);
                storage.put(id, entity);
                currentId = Math.max(currentId, id + 1);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading data from file: " + filePath, e);
        }
    }
}
