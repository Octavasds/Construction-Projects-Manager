package Parser;

/**
 * Interface for parsing entities to/from strings.
 */
public interface EntityParser<T> {
    String toString(T obj); // Converts an entity to a string representation.
    T fromString(String data); // Parses an entity from its string representation.
}

