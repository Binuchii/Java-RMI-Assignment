import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ApplicationServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ApplicationHandler handler = new ApplicationHandlerImpl();
            Naming.rebind("rmi://localhost/ApplicationService", handler);
            System.out.println("Application Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
