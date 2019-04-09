package controller;

import entity.AnswerEntity;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AnswerService;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
@RestController
@RequestMapping("answer")
public class AnswerController {
    @Reference
    private AnswerService answerService;

    @ApiOperation("获取所有答案")
    @RequestMapping(value = "/findAllAnswer",method = RequestMethod.POST)
    public List<AnswerEntity> findAllAnswer(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "20")int pageSize){
        return answerService.answerList(new PageConverter(pageNum-1, pageSize));
    }

    @ApiOperation("添加答案")
    @RequestMapping(value = "/addAnswer",method = RequestMethod.POST)
    public int addAnswer(AnswerEntity answerEntity){
        return answerService.addAnswer(answerEntity);
    }

    @ApiOperation("删除答案")
    @RequestMapping(value = "/deleteAnswer",method = RequestMethod.POST)
    public void deleteAnswer(int aid){
        answerService.deleteAnswer(aid);
    }

    @ApiOperation("答案数目")
    @RequestMapping(value = "/countAnswer",method = RequestMethod.POST)
    public void countAnswer(){
        answerService.countAnswer();
    }
}
