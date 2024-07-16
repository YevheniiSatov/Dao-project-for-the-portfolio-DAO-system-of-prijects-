/**
 * Model class representing a project.
 * This class contains attributes for project name, area, and cost, and provides methods for accessing and manipulating these attributes.
 * @Author Yevhenii Shatov
 */

package models;

import java.util.Comparator;

public class Project implements Comparable<Project> {
    private String name;
    private String area;
    private double cost;

    /**
     * Constructor for Project.
     * @param name Name of the project.
     * @param area Area of the project.
     * @param cost Cost of the project.
     */
    public Project(String name, String area, double cost) {
        setName(name);
        setArea(area);
        setCost(cost);
    }

    /**
     * Sets the name of the project.
     * @param name Name of the project.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    /**
     * Sets the area of the project.
     * @param area Area of the project.
     * @throws IllegalArgumentException if the area is null or empty.
     */
    public void setArea(String area) {
        if (area == null || area.isEmpty()) throw new IllegalArgumentException("Area cannot be empty");
        this.area = area;
    }

    /**
     * Sets the cost of the project.
     * @param cost Cost of the project.
     * @throws IllegalArgumentException if the cost is negative.
     */
    public void setCost(double cost) {
        if (cost < 0) throw new IllegalArgumentException("Cost cannot be negative");
        this.cost = cost;
    }

    /**
     * Returns a string representation of the project.
     * @return String representation of the project.
     */
    @Override
    public String toString() {
        return String.format("Project{name='%s', area='%s', cost=%.2f}", name, area, cost);
    }

    /**
     * Checks if two projects are equal based on their name.
     * @param o Object to be compared.
     * @return True if the projects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return name.equals(project.name);
    }

    /**
     * Returns the hash code of the project based on its name.
     * @return Hash code of the project.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Compares two projects based on their cost.
     * @param other Project to be compared.
     * @return A negative integer, zero, or a positive integer as this project's cost is less than, equal to, or greater than the specified project's cost.
     */
    @Override
    public int compareTo(Project other) {
        return Double.compare(this.cost, other.cost);
    }

    public static Comparator<Project> NameComparator = new Comparator<Project>() {
        @Override
        public int compare(Project p1, Project p2) {
            return p1.name.compareTo(p2.name);
        }
    };

    /**
     * Gets the name of the project.
     * @return Name of the project.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the area of the project.
     * @return Area of the project.
     */
    public String getArea() {
        return this.area;
    }

    /**
     * Gets the cost of the project.
     * @return Cost of the project.
     */
    public double getCost() {
        return this.cost;
    }
}
