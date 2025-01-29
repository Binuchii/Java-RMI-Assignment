import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApplicationHandler extends Remote {
    long login(String username, String password) throws RemoteException, InvalidCredentialsException;
    ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionIDException;
    void submitApplicationForm(long sessionId, ApplicationForm form) throws RemoteException, InvalidSessionIDException;
}

// Custom exceptions
class InvalidCredentialsException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

class InvalidSessionIDException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidSessionIDException(String message) {
        super(message);
    }
}
