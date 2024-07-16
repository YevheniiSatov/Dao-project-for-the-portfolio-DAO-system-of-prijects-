/**
 * Interface defining the methods for project storage management.
 * This interface provides methods to add, get, update, delete, and query projects.
 * @Author Yevhenii Shatov
 */

package dal;

import models.Project;
import java.util.List;

public interface Storage {
    /**
     * Adds a new project to the storage.
     * @param project Project to be added.
     * @throws Exception if the project cannot be added.
     */
    void addProject(Project project) throws Exception;

    /**
     * Retrieves a project by its name.
     * @param name Name of the project.
     * @return Project object if found, otherwise null.
     */
    Project getProject(String name);

    /**
     * Updates an existing project in the storage.
     * @param project Project with updated details.
     * @throws Exception if the project cannot be updated.
     */
    void updateProject(Project project) throws Exception;

    /**
     * Deletes a project by its name from the storage.
     * @param name Name of the project to be deleted.
     * @throws Exception if the project cannot be deleted.
     */
    void deleteProject(String name) throws Exception;

    /**
     * Retrieves all projects from the storage.
     * @return List of all projects.
     */
    List<Project> getAllProjects();

    /**
     * Retrieves all projects with a cost above a specified threshold.
     * @param threshold Cost threshold.
     * @return List of projects with a cost above the threshold.
     */
    List<Project> getProjectsFilteredByCost(double threshold);

    /**
     * Counts the number of projects that meet specified criteria.
     * @param costCriteria Minimum cost of the projects.
     * @param area Area of the projects.
     * @return Number of projects that meet the criteria.
     */
    int countProjectsByCriteria(double costCriteria, String area);
}