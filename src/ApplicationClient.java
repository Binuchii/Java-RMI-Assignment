import java.rmi.Naming;
import java.util.Scanner;

public class ApplicationClient {
    public static void main(String[] args) {
        try {
            ApplicationHandler handler = (ApplicationHandler) Naming.lookup("rmi://localhost/ApplicationService");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            long sessionID = handler.login(username, password);
            System.out.println("Logged in! Session ID: " + sessionID);

            ApplicationForm form = handler.downloadApplicationForm(sessionID);
            System.out.println(form.getFormInfo());

            for (int i = 0; i < form.getNumberOfQuestions(); i++) {
                System.out.println(form.getQuestion(i));
                String answer = scanner.nextLine();
                form.answerQuestion(i, answer);
            }

            handler.submitApplicationForm(sessionID, form);
            System.out.println("Application form submitted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
