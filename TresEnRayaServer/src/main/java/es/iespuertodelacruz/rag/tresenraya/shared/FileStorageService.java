package es.iespuertodelacruz.rag.tresenraya.shared;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    private Path getFilenameFree(String filename) {
        Path pathCompleto = this.root.resolve(filename);
        String nombre = "";
        String extension = "";

        if (filename.contains(".")) {
            extension = filename.substring(filename.lastIndexOf(".") + 1);
            nombre = filename.substring(0, filename.length() -
                    extension.length() - 1);
        } else {
            nombre = filename;
        }

        int contador = 1;
        while (Files.exists(pathCompleto)) {
            String nuevoNombre = nombre + "(" + contador + ")";
            nuevoNombre += "." + extension;
            pathCompleto = this.root.resolve(nuevoNombre);
            contador++;
        }
        return (pathCompleto);
    }

    public String save(String nombrefichero, byte[] dataFile) {
        // creamos el directorio si no existe
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("no se puede crear el directorio");
        }

        try {
            Path filenameFree = getFilenameFree(nombrefichero);
            Files.write(filenameFree, dataFile);
            return filenameFree.getFileName().toString();
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public String get(String filename) {
        try {
            Path path = root.resolve(filename);
            if (Files.exists(path) == false) {
                return null;
            }
            byte[] fileBytes = Files.readAllBytes(path);
            String base64Content = Base64.getEncoder().encodeToString(fileBytes);

            String mimeType = Files.probeContentType(path);
            return "data:" + mimeType + ";base64," + base64Content;
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
    }
}