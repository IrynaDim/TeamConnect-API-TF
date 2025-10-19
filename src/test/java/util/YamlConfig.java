package util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public final class YamlConfig {

    private static final Map<String, Object> ROOT = loadYaml();

    private YamlConfig() {
    }

    private static Map<String, Object> loadYaml() {
        String env = System.getProperty("tc.env", System.getenv("TC_ENV"));
        if (env == null || env.isBlank()) env = "dev-test";

        String resource = "application-" + env + ".yml";
        try (InputStream in = YamlConfig.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException(String.format("YAML resource '%s' not found (env=%s)", resource, env));
            }
            return new Yaml().load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + resource, e);
        }
    }

    private static Object getValue(String path) {
        String[] parts = path.split("\\.");
        Object current = ROOT;
        for (String key : parts) {
            if (!(current instanceof Map<?, ?> map)) {
                throw new IllegalStateException(
                        String.format("Invalid YAML structure for path '%s' (expected nested map)", path)
                );
            }

            current = map.get(key);
            if (current == null) {
                throw new IllegalStateException(
                        String.format("Missing required YAML property: '%s'", path)
                );
            }
        }

        return current;
    }

    public static String getString(String path) {
        Object value = getValue(path);
        return value == null ? null : value.toString();
    }
}
