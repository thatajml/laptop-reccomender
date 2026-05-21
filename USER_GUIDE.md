# User Guide: How to Run the Laptop Recommender System

Follow these steps to run the Laptop Recommender application on your local machine.

## Prerequisites

1. **Java 17:** You must have Java 17 or higher installed. 
   - Open your terminal or command prompt.
   - Run `java -version` to verify you have it installed.
   - If not installed, download it from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or use [Adoptium (Eclipse Temurin)](https://adoptium.net/temurin/releases/?version=17).

## Running the Application

### Method 1: Using the Command Line (Recommended)

You don't need to install Maven on your machine because this project comes with the **Maven Wrapper** (`mvnw` for Linux/Mac, `mvnw.cmd` for Windows). This script will automatically download the required Maven version and all dependencies for you.

1. **Extract the ZIP file** and open a terminal (Command Prompt, PowerShell, or Bash) inside the extracted project folder (the folder containing the `pom.xml` file).
2. **Run the following command:**
   - **On Windows:**
     ```cmd
     .\mvnw.cmd spring-boot:run
     ```
   - **On Mac/Linux:**
     ```bash
     ./mvnw spring-boot:run
     ```
3. Wait for the application to download its dependencies and start up. You will see a log line that looks like `Started Server on port ...`.

### Method 2: Using an IDE / Text Editor

1. **Extract the ZIP file.**
2. Open your favorite IDE (like IntelliJ IDEA, Eclipse, or VS Code with the Java Extension Pack).
3. Select **"Open"** or **"Import Project"** and select the extracted folder.
4. Let the IDE import the Maven project dependencies automatically.
5. In your IDE, locate the main class:
   `src/main/java/com/example/laptoprecommender/LaptopRecommenderApplication.java`
6. Right-click anywhere in that file and select **"Run"** or click the play button in the code gutter.

## Accessing the Application

Once the backend server is running successfully, open your web browser and navigate to:
**http://localhost:8080**

### Troubleshooting
- **Port 8080 is in use:** If you see an error indicating that port 8080 is already in use, you can stop the conflicting process or change this app's port by modifying `src/main/resources/application.properties` (if it exists) to add: `server.port=8081`. 
- **Java version mismatch:** Ensure that your `JAVA_HOME` environment variable is strictly pointing to JDK 17 if the build fails with class version errors.
