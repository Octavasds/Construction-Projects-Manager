package RepositoryLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T> implements IRepository<T> {
    private final Map<Integer, T> storage = new HashMap<>();
    private int currentId = 0;

    public void add(T entity) {
        storage.put(currentId++, entity);
    }

    public void delete(T entity) {
        storage.values().remove(entity);
    }

    public void update(int id, T entity) {
        storage.put(id, entity);
    }

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public T getById(int id) {
        return storage.get(id);
    }

    public Integer getID(T entity){
        for (Map.Entry<Integer, T> entry : storage.entrySet()) {
            if (entry.getValue().equals(entity)) {
                return entry.getKey();
            }
        }
        return null;
    }
}

