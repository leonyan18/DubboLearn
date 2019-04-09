package controller;

import com.alibaba.fastjson.JSONArray;
import dto.MessageDTO;
import entity.ConversationEntity;
import entity.MessageEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ConversationService;
import service.MessageService;
import util.PageConverter;
import util.ResultUtil;
import websocket.Msg;
import websocket.Shout;

import java.util.List;

/**
 * @author yan
 * @date 2018/11/3 20:04
 * @descripition
 */
@RestController
@Api(tags = "消息接口")
@RequestMapping("message")
public class MessageController {
    @Reference
    private MessageService messageService;
    @Reference
    private ConversationService conversationService;
    private final SimpMessagingTemplate messaging;
    private static final Logger logger = LogManager.getLogger(MessageController.class);

    public MessageController(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    @MessageMapping("/message")
    @ApiOperation("消息处理")
    public void handle(Msg msg) {
        msg.setFrom(msg.getFrom());
        logger.info(msg);
        handleMessage(msg);
    }

    public void handleMessage(Msg msg) {
        ConversationEntity conversationEntity=conversationService.findByCid(msg.getCid());
        logger.info(conversationEntity.getStaff());
        if(conversationEntity.getCustomer().getUid()==msg.getFrom()){
            if(conversationEntity.getStaff()!=null) {
                msg.setTo(conversationEntity.getStaff().getUid());
            }
        }else{
            msg.setTo(conversationEntity.getCustomer().getUid());
        }
        logger.info(msg.toString());
        MessageEntity messageEntity=new MessageEntity(msg);
        logger.info(msg.toString());
        messageService.saveMessage(messageEntity);
        if(conversationEntity.getStaff()!=null) {
            messaging.convertAndSendToUser(""+msg.getTo(),"/queue/notifications",msg.toString());
        }else{
            logger.info(ResultUtil.checkMotion(msg.getContent()));
            if (ResultUtil.checkMotion(msg.getContent())){
                Msg newMsg=new Msg();
                newMsg.setContent(matchAnswer(msg.getContent()).toString());
                newMsg.setTo(msg.getFrom());
                newMsg.setCid(msg.getCid());
                messaging.convertAndSendToUser(""+newMsg.getTo(),"/queue/notifications",newMsg.toString());
                logger.info(newMsg.toString());
            }else{
                msg.setTo(conversationService.matchStaff(conversationEntity.getCid()).getStaff().getUid());
                logger.info(msg.toString());
                messaging.convertAndSendToUser(""+msg.getTo(),"/queue/notifications",msg.toString());
            }

        }
    }
    @ApiOperation("消息数目")
    @RequestMapping(value = "countMessage", method = RequestMethod.POST)
    public long count(int cid) {
        return messageService.count(cid);
    }

    @ApiOperation("消息记录")
    @RequestMapping(value = "findChatRecord", method = RequestMethod.POST)
    public List<MessageDTO> findChatRecord(int cid, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
        return messageService.findChatRecord(cid, new PageConverter(pageNum-1, pageSize));
    }

    @ApiOperation("匹配答案")
    @RequestMapping(value = "matchAnswer", method = RequestMethod.POST)
    public JSONArray matchAnswer(String problem) {
        return messageService.matchAnswer(problem);
    }


    @MessageExceptionHandler
    public void handleMessageException(Throwable t){
        logger.error("Error handling messsage"+t.getMessage());
    }

}
