/**
 * Implementation of Storage interface for managing projects in memory.
 * This class provides methods to add, get, update, and delete projects stored in a HashMap.
 * @Author Yevhenii Shatov
 */

package dal;

import models.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectDao implements Storage {
    private final Map<String, Project> projects = new HashMap<>();

    /**
     * Adds a new project to the in-memory storage.
     * @param project Project to be added.
     * @throws Exception if a project with the same name already exists.
     */
    @Override
    public void addProject(Project project) throws Exception {
        if (this.projects.containsKey(project.getName())) {
            throw new Exception("Project with this name already exists");
        }
        this.projects.put(project.getName(), project);
    }

    /**
     * Retrieves a project by its name from the in-memory storage.
     * @param name Name of the project.
     * @return Project object if found, otherwise null.
     */
    @Override
    public Project getProject(String name) {
        return this.projects.get(name);
    }

    /**
     * Updates an existing project in the in-memory storage.
     * @param project Project with updated details.
     * @throws Exception if the project does not exist.
     */
    @Override
    public void updateProject(Project project) throws Exception {
        if (!this.projects.containsKey(project.getName())) {
            throw new Exception("Project does not exist");
        }
        this.projects.put(project.getName(), project);
    }

    /**
     * Deletes a project from the in-memory storage.
     * @param name Name of the project to be deleted.
     * @throws Exception if the project does not exist.
     */
    @Override
    public void deleteProject(String name) throws Exception {
        if (!this.projects.containsKey(name)) {
            throw new Exception("Project does not exist");
        }
        this.projects.remove(name);
    }

    /**
     * Retrieves all projects stored in memory.
     * @return List of all projects.
     */
    @Override
    public List<Project> getAllProjects() {
        return new ArrayList<>(this.projects.values());
    }

    /**
     * Retrieves all projects with a cost above a specified threshold.
     * @param threshold Cost threshold.
     * @return List of projects with a cost above the threshold.
     */
    @Override
    public List<Project> getProjectsFilteredByCost(double threshold) {
        return this.projects.values().stream()
                .filter(p -> p.getCost() > threshold)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of projects that meet specified criteria.
     * @param costCriteria Minimum cost of the projects.
     * @param area Area of the projects.
     * @return Number of projects that meet the criteria.
     */
    @Override
    public int countProjectsByCriteria(double costCriteria, String area) {
        return (int)this.projects.values().stream()
                .filter(p -> p.getCost() >= costCriteria && p.getArea().equals(area))
                .count();
    }
}