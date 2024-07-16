/**
 * Implementation of Storage interface for managing projects in a file system.
 * This class provides methods to add, get, update, and delete projects stored as files.
 * @Author Yevhenii Shatov
 */

package dal;

import models.Project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileProjectDao implements Storage {
    private final String dbDir;

    /**
     * Constructor for FileProjectDao.
     * @param dbDir Directory path where project files are stored.
     */
    public FileProjectDao(String dbDir) {
        this.dbDir = dbDir;
        try {
            Files.createDirectories(Paths.get(this.dbDir));
        } catch (IOException e) {
            System.err.format("Failed to create directory %s: %s%n", this.dbDir, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a Project object to a formatted string suitable for file storage.
     * @param project Project object to be converted.
     * @return Formatted string representing the project.
     */
    private String convertProjectToData(Project project) {
        return String.format("%s%n%s%n%f%n",
                project.getName(), project.getArea(), project.getCost());
    }

    /**
     * Generates a file path based on the project name.
     * The project name is converted to lowercase and spaces are replaced with underscores.
     * @param name Name of the project.
     * @return Path object representing the file path.
     */
    private Path getPathByProjectName(String name) {
        String safeName = name.toLowerCase().replaceAll("\\s+", "_");
        return Paths.get(dbDir, safeName + ".txt");
    }

    /**
     * Parses a project from a file.
     * @param path Path to the project file.
     * @return Project object parsed from the file.
     * @throws IOException if an I/O error occurs reading from the file or a malformed input is detected.
     * @throws NumberFormatException if the cost value in the file is not a valid double.
     */
    private Project parseProject(Path path) throws IOException, NumberFormatException {
        List<String> lines = Files.readAllLines(path);
        if (lines.size() != 3) {
            throw new IOException("Incorrect file format");
        }
        String projectName = lines.get(0);
        String projectArea = lines.get(1);
        double projectCost = Double.parseDouble(lines.get(2).replace(',', '.'));
        return new Project(projectName, projectArea, projectCost);
    }

    /**
     * Adds a new project to the file system.
     * @param project Project to be added.
     * @throws Exception if a project with the same name already exists or an I/O error occurs.
     */
    @Override
    public void addProject(Project project) throws Exception {
        Path path = getPathByProjectName(project.getName());
        if (Files.exists(path)) {
            throw new Exception("Project with this name already exists");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(convertProjectToData(project));
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to add project");
        }
    }

    /**
     * Retrieves a project by its name.
     * @param name Name of the project.
     * @return Project object if found, otherwise null.
     */
    @Override
    public Project getProject(String name) {
        Path path = getPathByProjectName(name);
        if (!Files.exists(path)) {
            return null;
        }
        try {
            return parseProject(path);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing project in the file system.
     * @param project Project with updated details.
     * @throws Exception if the project does not exist or an I/O error occurs.
     */
    @Override
    public void updateProject(Project project) throws Exception {
        Path path = getPathByProjectName(project.getName());
        if (!Files.exists(path)) {
            throw new Exception("Project does not exist");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(convertProjectToData(project));
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to update project");
        }
    }

    /**
     * Deletes a project from the file system.
     * @param name Name of the project to be deleted.
     * @throws Exception if the project does not exist or an I/O error occurs.
     */
    @Override
    public void deleteProject(String name) throws Exception {
        Path path = getPathByProjectName(name);
        if (!Files.exists(path)) {
            throw new Exception("Project does not exist");
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete project");
        }
    }

    /**
     * Retrieves all projects stored in the file system.
     * @return List of all projects.
     */
    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dbDir))) {
            for (Path path : directoryStream) {
                try {
                    projects.add(parseProject(path));
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    /**
     * Retrieves all projects with a cost above a specified threshold.
     * @param threshold Cost threshold.
     * @return List of projects with a cost above the threshold.
     */
    @Override
    public List<Project> getProjectsFilteredByCost(double threshold) {
        List<Project> projects = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dbDir))) {
            for (Path path : directoryStream) {
                try {
                    Project project = parseProject(path);
                    if (project.getCost() > threshold) {
                        projects.add(project);
                    }
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    /**
     * Counts the number of projects that meet specified criteria.
     * @param costCriteria Minimum cost of the projects.
     * @param area Area of the projects.
     * @return Number of projects that meet the criteria.
     */
    @Override
    public int countProjectsByCriteria(double costCriteria, String area) {
        int count = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dbDir))) {
            for (Path path : directoryStream) {
                try {
                    Project project = parseProject(path);
                    if (project.getArea().equals(area) && project.getCost() >= costCriteria) {
                        count++;
                    }
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Lists the names of all project files in the directory.
     * @return List of project file names.
     */
    public List<String> listProjectFiles() {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dbDir))) {
            for (Path path : stream) {
                fileNames.add(path.getFileName().toString());
            }
        } catch (IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
        }
        return fileNames;
    }
}
