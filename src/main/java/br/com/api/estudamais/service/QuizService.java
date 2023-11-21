package br.com.api.estudamais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.api.estudamais.model.Quiz;
import br.com.api.estudamais.repository.QuizRepository;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long quizId, Quiz newQuiz) {
        Optional<Quiz> existingQuiz = quizRepository.findById(quizId);
        if (existingQuiz.isPresent()) {
            newQuiz.setId(quizId);
            return quizRepository.save(newQuiz);
        }
        return null;
    }

    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    public List<Quiz> getQuizzesByCommunity(Long communityId) {
        return null;
    }
}
