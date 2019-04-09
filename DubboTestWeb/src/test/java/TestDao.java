import config.DubboConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.AnswerService;
import util.PageConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DubboConfig.class})
public class TestDao {
    @Reference
    private AnswerService answerService;
    @Test
    public void TestAnswer(){
        System.out.println(answerService.countAnswer());
        answerService.answerList(new PageConverter(1,20));
    }
}
