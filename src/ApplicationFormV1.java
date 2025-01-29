import java.util.HashMap;
import java.util.Map;

public class ApplicationFormV1 implements ApplicationForm {
    private static final long serialVersionUID = 1L;
    private Map<Integer, String> questions;
    private Map<Integer, String> answers;

    public ApplicationFormV1() {
        questions = new HashMap<>();
        answers = new HashMap<>();
        initializeQuestions();
    }

    private void initializeQuestions() {
        questions.put(1, "Please enter your first name:");
        questions.put(2, "Please enter your last name:");
        questions.put(3, "Please enter your complete address:");
        questions.put(4, "Please enter your email address:");
        questions.put(5, "Please enter your contact number:");
        questions.put(6, "Please provide a personal statement including your qualifications:");
    }

    @Override
    public String getFormInformation() {
        return "University Course Application Form V1 - Basic Information and Personal Statement";
    }

    @Override
    public int getTotalQuestions() {
        return questions.size();
    }

    @Override
    public String getQuestion(int questionNumber) throws InvalidQuestionException {
        if (!questions.containsKey(questionNumber)) {
            throw new InvalidQuestionException("Invalid question number: " + questionNumber);
        }
        return questions.get(questionNumber);
    }

    @Override
    public void setAnswer(int questionNumber, String answer) throws InvalidQuestionException {
        if (!questions.containsKey(questionNumber)) {
            throw new InvalidQuestionException("Invalid question number: " + questionNumber);
        }
        answers.put(questionNumber, answer);
    }

    @Override
    public String getAnswer(int questionNumber) throws InvalidQuestionException {
        if (!questions.containsKey(questionNumber)) {
            throw new InvalidQuestionException("Invalid question number: " + questionNumber);
        }
        return answers.get(questionNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application Form Submission\n");
        sb.append("==========================\n");

        try {
            sb.append("First Name: ").append(getAnswer(1)).append("\n");
            sb.append("Last Name: ").append(getAnswer(2)).append("\n");
            sb.append("Address: ").append(getAnswer(3)).append("\n");
            sb.append("Email: ").append(getAnswer(4)).append("\n");
            sb.append("Contact Number: ").append(getAnswer(5)).append("\n");
            sb.append("Personal Statement: ").append(getAnswer(6)).append("\n");
        } catch (InvalidQuestionException e) {
            sb.append("Error generating form summary: ").append(e.getMessage());
        }

        return sb.toString();
    }
}