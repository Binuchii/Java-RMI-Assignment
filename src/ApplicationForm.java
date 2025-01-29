import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApplicationForm extends Remote {
    String getFormInfo() throws RemoteException; // Correct method name
    int getNumberOfQuestions() throws RemoteException;
    String getQuestion(int questionNumber) throws RemoteException;
    void answerQuestion(int questionNumber, String answer) throws RemoteException;
    String getAnswer(int questionNumber) throws RemoteException;
}
