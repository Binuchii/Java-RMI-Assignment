import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ApplicationHandlerImpl extends UnicastRemoteObject implements ApplicationHandler {
    private static final long serialVersionUID = 1L;
    private static final String VALID_USERNAME = "student";
    private static final String VALID_PASSWORD = "password123";
    private Map<Long, Long> activeSessions;
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes

    public ApplicationHandlerImpl() throws RemoteException {
        super();
        activeSessions = new HashMap<>();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        System.out.println("Login attempt - Username: " + username);

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            long sessionId = generateSessionId();
            activeSessions.put(sessionId, System.currentTimeMillis());
            System.out.println("Login successful - Session ID: " + sessionId);
            return sessionId;
        }

        throw new InvalidCredentialsException("Invalid username or password");
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId)
            throws RemoteException, InvalidSessionIDException {
        validateSession(sessionId);
        System.out.println("Downloading application form for session: " + sessionId);
        return new ApplicationFormV1();
    }

    @Override
    public void submitApplicationForm(long sessionId, ApplicationForm form)
            throws RemoteException, InvalidSessionIDException {
        validateSession(sessionId);
        System.out.println("Receiving application form submission for session: " + sessionId);

        try {
            ApplicationFormV1 formV1 = (ApplicationFormV1) form;
            String firstName = formV1.getAnswer(1);
            String lastName = formV1.getAnswer(2);
            String filename = firstName + "_" + lastName + "_application.txt";

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(form.toString());
                System.out.println("Application saved to file: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Error saving application: " + e.getMessage());
            throw new RemoteException("Failed to save application", e);
        } catch (InvalidQuestionException e) {
            System.err.println("Error accessing form data: " + e.getMessage());
            throw new RemoteException("Failed to process application", e);
        }
    }

    private long generateSessionId() {
        return new Random().nextLong() & Long.MAX_VALUE;
    }

    private void validateSession(long sessionId) throws InvalidSessionIDException {
        Long timestamp = activeSessions.get(sessionId);
        if (timestamp == null) {
            throw new InvalidSessionIDException("Invalid or expired session ID");
        }

        if (System.currentTimeMillis() - timestamp > SESSION_TIMEOUT) {
            activeSessions.remove(sessionId);
            throw new InvalidSessionIDException("Session has expired");
        }

        activeSessions.put(sessionId, System.currentTimeMillis());
    }
}