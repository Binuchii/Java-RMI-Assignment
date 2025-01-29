import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ApplicationHandlerImpl extends UnicastRemoteObject implements ApplicationHandler {
    private final Map<Long, Boolean> activeSessions;
    private final String validUsername = "admin";
    private final String validPassword = "password";

    protected ApplicationHandlerImpl() throws RemoteException {
        activeSessions = new HashMap<>();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        if (username.equals(validUsername) && password.equals(validPassword)) {
            long sessionID = new Random().nextLong();
            activeSessions.put(sessionID, true);
            System.out.println("User logged in successfully. Session ID: " + sessionID);
            return sessionID;
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionID) throws RemoteException, InvalidSessionIDException {
        if (activeSessions.containsKey(sessionID)) {
            return new ApplicationFormV1();
        } else {
            throw new InvalidSessionIDException("Invalid session ID.");
        }
    }

    @Override
    public void submitApplicationForm(long sessionID, ApplicationForm form) throws RemoteException, InvalidSessionIDException {
        if (!activeSessions.containsKey(sessionID)) {
            throw new InvalidSessionIDException("Invalid session ID.");
        }

        String fileName = form.getAnswer(0) + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Full Name: " + form.getAnswer(0) + "\n" + "Address: " + form.getAnswer(1) + "\n" +
                    "Email: " + form.getAnswer(2) + "\n" +
                    "Contact Number: " + form.getAnswer(3) + "\n"+
                    "Personal Statement: " + form.getAnswer(4) + "\n");
            System.out.println("Application form saved as " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
