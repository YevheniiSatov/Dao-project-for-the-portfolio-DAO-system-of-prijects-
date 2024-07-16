# Dao-project-for-the-portfolio-DAO-system-of-prijects
This project is a simple Project Management System implemented in Java. It allows you to add, view, update, and delete projects using a graphical user interface (GUI). Projects are stored either in memory or in the file system.

## Features

- Add new projects
- View existing projects
- Update project details
- Delete projects
- List all projects
- Filter projects by cost
- Count projects by specific criteria

## Getting Started

These instructions will help you set up and run the project on your local machine.

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven (for building the project)
- A file system directory for storing project data (if using `FileProjectDao`)

### Installing

1. **Clone the repository:**

   
    git clone https://github.com/your-username/project-management-system.git](https://github.com/YevheniiSatov/Dao-project-for-the-portfolio-DAO-system-of-prijects-
    cd DaoProject
 


3. **Run the application:**
 java -jar  out/artifacts/DaoProject_jar/DaoProject.jar
   

### Running the Application

The main entry point for the application is the `Main` class. You can run the application by executing the `Main` class. The GUI will start, and you can manage projects through the interface.

##Usage
1. Add Project:

Fill in the "Project Name", "Project Area", and "Project Cost" fields.
Click "Add Project".
2. View Project:

Enter the "Project Name".
Click "Show Project".
3. Update Project:

Enter the "Project Name".
Click "Update Project".
Modify the fields in the dialog that appears and confirm.
4. Delete Project:

Enter the "Project Name".
Click "Delete Project".
List All Projects:

Click "Show All Projects".
Filter Projects by Cost:

Enter the "Cost Threshold".
Click "Show Projects Above Threshold".
Count Projects by Criteria:

Enter the "Cost" and "Area".
Click "Count Projects by Criteria".
Project Structure
dal: Data Access Layer (DAO) classes for managing project storage (in memory and file system).
models: Data models representing projects.
representation: GUI classes for interacting with the user.
Main.java: Main entry point for the application
