package service;

import entity.AnswerEntity;
import org.springframework.data.domain.Pageable;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
public interface AnswerService {
    int addAnswer(AnswerEntity answerEntity);
    List<AnswerEntity> answerList(PageConverter pageable);
    long countAnswer();
    void deleteAnswer(int aid);
}
