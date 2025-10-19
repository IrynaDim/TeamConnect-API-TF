package fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public final class FixtureLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ConcurrentHashMap<String, List<?>> CACHE = new ConcurrentHashMap<>();

    private static final String CACHE_KEY_DELIMITER = "|";

    private FixtureLoader() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(Class<T> type) {
        String path = FixtureRegistry.PATHS.get(type);
        if (path == null || path.isBlank()) {
            throw new IllegalStateException("No collection path registered for type: " + type.getName());
        }
        String cacheKey = type.getName() + CACHE_KEY_DELIMITER + path;

        return (List<T>) CACHE.computeIfAbsent(
                cacheKey,
                k -> readJsonList(path, type)
        );
    }

    public static <T, R> T findOne(Class<T> type, Function<T, R> keyExtractor, R expectedValue) {
        List<T> matched = list(type).stream()
                .filter(item -> Objects.equals(keyExtractor.apply(item), expectedValue))
                .toList();

        if (matched.isEmpty()) {
            throw new NoSuchElementException(
                    "No element found in fixtures for type " + type.getName() +
                            " by value: " + expectedValue
            );
        }
        if (matched.size() > 1) {
            throw new IllegalStateException(
                    "More than one element matched in fixtures for type " + type.getName() +
                            " by value: " + expectedValue
            );
        }
        return matched.get(0);
    }


    private static <T> List<T> readJsonList(String resourcePath, Class<T> type) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            log.info("Loading fixture: {}", resourcePath);
            return MAPPER.readValue(
                    is,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, type)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON list from: " + resourcePath, e);
        }
    }
}
