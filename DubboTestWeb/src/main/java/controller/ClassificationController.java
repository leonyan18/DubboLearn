package controller;

import entity.ClassificationEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ClassificationService;

import java.util.List;

/**
 * @author yan
 */
@RestController
@Api("类别")
@RequestMapping("Classification")
public class ClassificationController {
    @Reference
    private ClassificationService classificationService;
    private static final Logger logger = LogManager.getLogger(ClassificationController.class);
    @ApiOperation("获取所有分类")
    @RequestMapping(value = "/findAllClassification",method = RequestMethod.POST)
    public List<ClassificationEntity> findAllClassification(){
        return classificationService.findAllClassification();
    }
}
