import java.io.Serializable;

public interface ApplicationForm extends Serializable {
    String getFormInformation();
    int getTotalQuestions();
    String getQuestion(int questionNumber) throws InvalidQuestionException;
    void setAnswer(int questionNumber, String answer) throws InvalidQuestionException;
    String getAnswer(int questionNumber) throws InvalidQuestionException;
}

class InvalidQuestionException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidQuestionException(String message) {
        super(message);
    }
}