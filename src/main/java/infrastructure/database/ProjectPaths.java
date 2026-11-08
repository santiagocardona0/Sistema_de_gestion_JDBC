package infrastructure.database;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectPaths {

    private static final Path PROJECT_ROOT = Paths.get("").toAbsolutePath();

    public static Path getProjectRoot() {
        return PROJECT_ROOT;
    }

    public static Path getSqlSchema() {
        return PROJECT_ROOT.resolve("sql").resolve("schema.sql");
    }

    public static Path requireSqlSchema() {
        Path path = getSqlSchema();

        if (!java.nio.file.Files.exists(path)) {
            throw new IllegalStateException(
                    "No se encuentra el archivo: " + path
            );
        }

        return path;
    }
}