import com.huaban.analysis.jieba.JiebaSegmenter;
import config.DataConfig;
import config.RootConfig;
import dao.*;
import dto.UserDTO;
import entity.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.AnswerService;
import service.ClassificationService;
import service.ConversationService;
import service.ProblemService;
import util.PageConverter;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RootConfig.class})
public class TestDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerService answerService;
    @Test
    public void testUser() throws IOException {
        List<AnswerEntity> answerEntityList=answerService.answerList(new PageConverter(1,20));
        System.out.println("+++++++++++++++++++++++++++");
        for (AnswerEntity a:answerEntityList) {
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println(a);
        }
        System.out.println("+++++++++++++++++++++++++++");
        for (UserDTO u:userRepository.findAllUserByKeyword("%%",PageRequest.of(0,2))) {
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println(u);
        }
    }

    @Test
    public void testProblem(){
        System.out.println(problemRepository.countAllByContentLikeAndClassification_Cid("%%",1));
    }
    @Test
    public void testjieba(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        for (ProblemEntity p:problemRepository.findAll()) {
            p.setKeywords(StringUtils.join(segmenter.sentenceProcess(p.getContent())," "));
            problemRepository.save(p);
        }
    }

    @Test
    public void testConversation(){
        System.out.println(conversationRepository.findByCid(1));
        System.out.println(conversationRepository.getMeanEvaluate(1));
        ConversationEntity conversationEntity=new ConversationEntity();
        UserEntity userEntity1=new UserEntity();
        userEntity1.setUid(1);
        conversationEntity.setCustomer(userEntity1);
        UserEntity userEntity2=new UserEntity();
        userEntity2.setUid(2);
        conversationEntity.setStaff(userEntity1);
        conversationRepository.save(conversationEntity);
    }

}
