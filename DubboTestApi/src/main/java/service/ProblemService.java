package service;

import entity.AnswerEntity;
import entity.ProblemEntity;
import org.springframework.data.domain.Pageable;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
public interface ProblemService {
    int addProblem(ProblemEntity problemEntity, AnswerEntity answerEntity);
    int addProblem(ProblemEntity problemEntity, int aid);
    List<ProblemEntity> findAllProblemByKeywordAndClassification(String keyword, PageConverter pageable, int classificationId);
    void deleteProblem(int pid);
    void updateProblem(ProblemEntity problemEntity);
    long countAllByContentLikeAndClassification_Cid(String keyword, int cid);
    List<ProblemEntity> findAll();
}
