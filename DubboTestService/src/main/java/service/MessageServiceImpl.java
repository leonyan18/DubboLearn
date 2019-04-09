package service;

import org.apache.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.MessageRepository;
import dto.MessageDTO;
import entity.MessageEntity;
import entity.ProblemEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import util.PageConverter;
import util.ResultUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yan
 */
@Service
@Component
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final ProblemService problemService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ProblemService problemService) {
        this.messageRepository=messageRepository;
        this.problemService=problemService;
    }


    @Override
    public long count(int cid) {
        return messageRepository.countByConversation_Cid(cid);
    }

    @Override
    public List<MessageDTO> findChatRecord(int cid, PageConverter pageable) {
        return messageRepository.findChatRecord(cid,pageable.getPageRequest());
    }

    @Override
    public JSONArray matchAnswer(String problem) {
        List<ProblemEntity> problemEntityList=problemService.findAll();
        HashMap<String, ProblemEntity> problemEntityHashMap=new HashMap<>();
        List<String> strings=new ArrayList<>();
        for (ProblemEntity p:problemEntityList) {
            strings.add(p.getContent());
            problemEntityHashMap.put(p.getContent(),p);
        }
        List<String> newStrs= ResultUtil.getResult(strings,problem);
        JSONArray jsonArray=new JSONArray();
        for (String s:newStrs) {
            ProblemEntity problemEntity=problemEntityHashMap.get(s);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("problem",problemEntity.getContent());
            jsonObject.put("answer",problemEntity.getAnswer().getContent());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public int saveMessage(MessageEntity messageEntity) {
        return messageRepository.save(messageEntity).getMid();
    }
}
