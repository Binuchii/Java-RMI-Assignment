import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationClient {
    public static void main(String[] args) {
        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);

            // Look up the remote object
            ApplicationHandler handler = (ApplicationHandler)
                    registry.lookup("ApplicationHandler");

            System.out.println("Connected to server successfully");

            // Login
            long sessionId = handler.login("student", "password123");
            System.out.println("Login successful - Session ID: " + sessionId);

            // Download application form
            ApplicationForm form = handler.downloadApplicationForm(sessionId);
            System.out.println("Downloaded application form: " + form.getFormInformation());

            // Complete the form
            form.setAnswer(1, "John");
            form.setAnswer(2, "Doe");
            form.setAnswer(3, "123 University Street, City, Country");
            form.setAnswer(4, "john.doe@email.com");
            form.setAnswer(5, "1234567890");
            form.setAnswer(6, "Bachelor's degree in Computer Science with 3.8 GPA. " +
                    "Strong background in programming and software development.");

            // Submit the completed form
            handler.submitApplicationForm(sessionId, form);
            System.out.println("Application form submitted successfully");

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}