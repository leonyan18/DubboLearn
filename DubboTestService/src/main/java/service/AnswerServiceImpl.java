package service;

import dao.AnswerRepository;
import entity.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import util.ErrorMessage;
import util.LogicException;
import util.PageConverter;

import java.sql.Date;
import java.util.List;

/**
 * @author yan
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public int addAnswer(AnswerEntity answerEntity) {
        answerEntity.setCreateTime(new Date(System.currentTimeMillis()));
        return answerRepository.save(answerEntity).getAid();
    }


    @Override
    public List<AnswerEntity> answerList(PageConverter pageable) {

        return answerRepository.findAll(pageable.getPageRequest()).getContent();
    }

    @Override
    public long countAnswer() {
        return answerRepository.count();
    }

    @Override
    public void deleteAnswer(int aid) {
        if(answerRepository.existsById(aid)){
            answerRepository.deleteById(aid);
        }else{
            throw LogicException.le(ErrorMessage.NO_SUCH_ENTITY);
        }
    }
}
