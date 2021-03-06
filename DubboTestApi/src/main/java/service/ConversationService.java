package service;

import dto.ConversationDTO;
import entity.ConversationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
public interface ConversationService {
    /**
     * description:获取所有的对话
     * @param
     * @return java.util.List<dto.ConversationDTO>
     */
    List<ConversationDTO> findAllConversation(PageConverter pageable);
    int startConversation(int customerId);
    void endConversation(int customerId, double evaluate);
    long countConversation();
    ConversationEntity matchStaff(int conversationId);
    ConversationEntity findByCid(int cid);
}
