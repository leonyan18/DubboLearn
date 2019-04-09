package service;

import com.alibaba.fastjson.JSONArray;
import dto.MessageDTO;
import entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import util.PageConverter;
import websocket.Msg;

import java.util.List;

public interface MessageService {
    long count(int cid);
    List<MessageDTO> findChatRecord(int cid, PageConverter pageable);
    JSONArray matchAnswer(String problem);
    int saveMessage(MessageEntity messageEntity);
}
