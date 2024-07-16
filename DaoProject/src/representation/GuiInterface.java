/**
 * GUI class for managing project operations.
 * This class provides a graphical user interface for adding, viewing, updating, and deleting projects.
 * @Author Yevhenii Shatov
 */

package representation;

import dal.Storage;
import models.Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GuiInterface extends JFrame {
    private final Storage storage;
    private JTextField nameField;
    private JTextField areaField;
    private JTextField costField;
    private JTextField costThresholdField;
    private JTextArea outputArea;
    private JButton addButton;
    private JButton displayButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton displayAllButton;
    private JButton displayAboveCostButton;
    private JButton countCriteriaButton;

    /**
     * Constructor for GuiInterface.
     * @param storage Storage instance for managing projects.
     */
    public GuiInterface(Storage storage) {
        this.storage = storage;
        initializeUI();
    }

    /**
     * Initializes the GUI components and layout.
     */
    private void initializeUI() {
        setTitle("Project Management System");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("Project Name:"));
        nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new JLabel("Project Area:"));
        areaField = new JTextField(20);
        panel.add(areaField);

        panel.add(new JLabel("Project Cost:"));
        costField = new JTextField(20);
        panel.add(costField);

        addButton = new JButton("Add Project");
        addButton.addActionListener(this::addProject);
        panel.add(addButton);

        displayButton = new JButton("Show Project");
        displayButton.addActionListener(this::chooseProjectToShow);
        panel.add(displayButton);

        updateButton = new JButton("Update Project");
        updateButton.addActionListener(this::chooseProjectToUpdate);
        panel.add(updateButton);

        deleteButton = new JButton("Delete Project");
        deleteButton.addActionListener(this::chooseProjectToDelete);
        panel.add(deleteButton);

        displayAllButton = new JButton("Show All Projects");
        displayAllButton.addActionListener(this::displayAllProjects);
        panel.add(displayAllButton);

        panel.add(new JLabel("Cost Threshold:"));
        costThresholdField = new JTextField(20);
        panel.add(costThresholdField);

        displayAboveCostButton = new JButton("Show Projects Above Threshold");
        displayAboveCostButton.addActionListener(this::displayProjectsAboveCost);
        panel.add(displayAboveCostButton);

        countCriteriaButton = new JButton("Count Projects by Criteria");
        countCriteriaButton.addActionListener(this::countProjectsByCriteria);
        panel.add(countCriteriaButton);

        outputArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(panel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Handles the action for adding a new project.
     * @param event ActionEvent instance.
     */
    private void addProject(ActionEvent event) {
        try {
            String name = nameField.getText();
            String area = areaField.getText();
            double cost = Double.parseDouble(costField.getText().replace(',', '.'));
            Project project = new Project(name, area, cost);
            storage.addProject(project);
            outputArea.setText("Project added successfully.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handles the action for displaying a project's details.
     * @param event ActionEvent instance.
     */
    private void displayProject(ActionEvent event) {
        String name = nameField.getText();
        Project project = storage.getProject(name);
        if (project != null) {
            outputArea.setText("Project Details: " + project);
        } else {
            outputArea.setText("Project not found.");
        }
    }

    /**
     * Opens a dialog to select and display a project's details.
     * @param event ActionEvent instance.
     */
    private void chooseProjectToShow(ActionEvent event) {
        List<Project> projects = storage.getAllProjects();
        if (projects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No projects available to show.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
        String selectedProject = (String) JOptionPane.showInputDialog(
                this,
                "Select a project to view:",
                "View Project",
                JOptionPane.QUESTION_MESSAGE,
                null,
                projectNames,
                projectNames[0]
        );

        if (selectedProject != null && !selectedProject.isEmpty()) {
            Project project = storage.getProject(selectedProject);
            if (project != null) {
                JOptionPane.showMessageDialog(this, "Project Details:\nName: " + project.getName() +
                                "\nArea: " + project.getArea() + "\nCost: " + String.format("%.2f", project.getCost()),
                        "Project Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Project not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opens a dialog to select and delete a project.
     * @param event ActionEvent instance.
     */
    private void chooseProjectToDelete(ActionEvent event) {
        List<Project> projects = storage.getAllProjects();
        if (projects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No projects available to delete.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
        String selectedProject = (String) JOptionPane.showInputDialog(
                this,
                "Select a project to delete:",
                "Delete Project",
                JOptionPane.QUESTION_MESSAGE,
                null,
                projectNames,
                projectNames[0]
        );

        if (selectedProject != null && !selectedProject.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete project \"" + selectedProject + "\"?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    storage.deleteProject(selectedProject);
                    outputArea.setText("Project \"" + selectedProject + "\" deleted successfully.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Delete error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Displays all projects.
     * @param event ActionEvent instance.
     */
    private void displayAllProjects(ActionEvent event) {
        List<Project> projects = storage.getAllProjects();
        if (projects.isEmpty()) {
            outputArea.setText("No projects available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Project project : projects) {
                sb.append(project).append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    /**
     * Displays projects with a cost above a specified threshold.
     * @param event ActionEvent instance.
     */
    private void displayProjectsAboveCost(ActionEvent event) {
        try {
            double threshold = Double.parseDouble(costThresholdField.getText().replace(',', '.'));
            List<Project> filteredProjects = storage.getProjectsFilteredByCost(threshold);
            if (filteredProjects.isEmpty()) {
                outputArea.setText("No projects with cost above " + threshold + " available.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Project project : filteredProjects) {
                    sb.append(project).append("\n");
                }
                outputArea.setText(sb.toString());
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Enter a valid cost.");
        }
    }

    /**
     * Counts the number of projects that meet specified criteria.
     * @param event ActionEvent instance.
     */
    private void countProjectsByCriteria(ActionEvent event) {
        try {
            double costCriteria = Double.parseDouble(costField.getText().replace(',', '.'));
            String area = areaField.getText();
            int count = storage.countProjectsByCriteria(costCriteria, area);
            outputArea.setText("Number of projects matching criteria: " + count);
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Enter a valid cost.");
        }
    }

    /**
     * Opens a dialog to select and update a project's details.
     * @param event ActionEvent instance.
     */
    private void chooseProjectToUpdate(ActionEvent event) {
        List<Project> projects = storage.getAllProjects();
        if (projects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No projects available for updating.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
        String selectedProject = (String) JOptionPane.showInputDialog(
                this,
                "Select a project to update:",
                "Update Project",
                JOptionPane.QUESTION_MESSAGE,
                null,
                projectNames,
                projectNames[0]
        );

        if (selectedProject != null && !selectedProject.isEmpty()) {
            updateProjectDialog(selectedProject);
        }
    }

    /**
     * Opens a dialog to update the details of a selected project.
     * @param projectName Name of the project to be updated.
     */
    private void updateProjectDialog(String projectName) {
        Project project = storage.getProject(projectName);
        if (project == null) {
            JOptionPane.showMessageDialog(this, "Project not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField newNameField = new JTextField(project.getName());
        JTextField newAreaField = new JTextField(project.getArea());
        JTextField newCostField = new JTextField(String.valueOf(project.getCost()));

        Object[] message = {
                "Name:", newNameField,
                "Area:", newAreaField,
                "Cost:", newCostField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Project", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double cost = Double.parseDouble(newCostField.getText().replace(',', '.'));
                project.setName(newNameField.getText());
                project.setArea(newAreaField.getText());
                project.setCost(cost);
                storage.updateProject(project);
                outputArea.setText("Project updated successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}
