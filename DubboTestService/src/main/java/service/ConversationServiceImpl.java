package service;

import org.apache.dubbo.config.annotation.Service;
import dao.ConversationRepository;
import dto.ConversationDTO;
import entity.ConversationEntity;
import entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import util.PageConverter;

import java.util.Date;
import java.util.List;

/**
 * @author yan
 */
@Service
@Component
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    private static final Logger logger = LogManager.getLogger(ConversationServiceImpl.class);

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }


    @Override
    public List<ConversationDTO> findAllConversation(PageConverter pageable) {
        return conversationRepository.findAllConversation(pageable.getPageRequest());
    }

    @Override
    public int startConversation(int customerId) {
        ConversationEntity conversationEntity=new ConversationEntity();
        UserEntity userEntity1=new UserEntity();
        userEntity1.setUid(customerId);
        conversationEntity.setCustomer(userEntity1);
        conversationEntity.setStatus(true);
        conversationEntity.setStarttime(new Date(System.currentTimeMillis()));
        return conversationRepository.save(conversationEntity).getCid();
    }

    @Override
    public void endConversation(int customerId,double evaluate) {
        ConversationEntity conversationEntity=conversationRepository.findByCid(customerId);
        logger.info(conversationEntity);
        if (evaluate!=0.0) {
            conversationEntity.setEvaluate(evaluate);
        }
        if(!conversationEntity.getStatus()){
            return;
        }
        conversationEntity.setStatus(false);
        conversationEntity.setEndtime(new Date(System.currentTimeMillis()));
        logger.info(conversationEntity);
        conversationRepository.save(conversationEntity);
    }

    @Override
    public long countConversation() {
        return conversationRepository.count();
    }

    @Override
    public ConversationEntity matchStaff(int conversationId) {
        ConversationEntity conversationEntity=conversationRepository.findByCid(conversationId);
        UserEntity userEntity2=new UserEntity();
        userEntity2.setUid(3);
        conversationEntity.setStaff(userEntity2);
        return conversationRepository.save(conversationEntity);
    }

    @Override
    public ConversationEntity findByCid(int cid) {
        return conversationRepository.findByCid(cid);
    }

}
