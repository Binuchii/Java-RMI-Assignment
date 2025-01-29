import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) {
        try {
            // Create and export the remote object
            ApplicationHandler handler = new ApplicationHandlerImpl();

            // Create and start the registry on port 1099
            Registry registry = LocateRegistry.createRegistry(8080);

            // Bind the remote object to the registry
            registry.rebind("ApplicationHandler", handler);

            System.out.println("Application Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}