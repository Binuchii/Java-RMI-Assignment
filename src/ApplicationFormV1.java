import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ApplicationFormV1 extends UnicastRemoteObject implements ApplicationForm {
    private final String[] questions = {
            "Full Name:",
            "Address:",
            "Email:",
            "Contact Number:",
            "Personal Statement:"
    };
    private final Map<Integer, String> answers;

    protected ApplicationFormV1() throws RemoteException {
        answers = new HashMap<>();
    }

    @Override
    public String getFormInfo() {
        return "University Course Application Form - Version 1";
    }

    @Override
    public int getNumberOfQuestions() {
        return questions.length;
    }

    @Override
    public String getQuestion(int questionNumber) {
        if (questionNumber >= 0 && questionNumber < questions.length) {
            return questions[questionNumber];
        }
        return "Invalid question number.";
    }

    @Override
    public void answerQuestion(int questionNumber, String answer) {
        if (questionNumber >= 0 && questionNumber < questions.length) {
            answers.put(questionNumber, answer);
        }
    }

    public String getAnswer(int questionNumber) {
        return answers.getOrDefault(questionNumber, "Not answered");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFormInfo()).append("\n");
        for (int i = 0; i < questions.length; i++) {
            sb.append(questions[i]).append(" ").append(answers.getOrDefault(i, "Not answered")).append("\n");
        }
        return sb.toString();
    }
}
