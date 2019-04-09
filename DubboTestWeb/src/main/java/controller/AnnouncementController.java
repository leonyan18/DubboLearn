package controller;
import entity.AnnouncementEntity;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AnnouncementService;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
@RestController
@RequestMapping("Announcement")
public class AnnouncementController {
    @Reference
    private  AnnouncementService announcementService;

    @ApiOperation("获取所有公告")
    @RequestMapping(value = "/findAllAnnouncement",method = RequestMethod.POST)
    public List<AnnouncementEntity> findAllAnnouncement(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "20")int pageSize){
        return announcementService.announcementList(new PageConverter(pageNum-1, pageSize));
    }

    @ApiOperation("添加公告")
    @RequestMapping(value = "/addAnnouncement",method = RequestMethod.POST)
    public int addAnnouncement(AnnouncementEntity answerEntity){
        return announcementService.addAnnouncement(answerEntity);
    }

    @ApiOperation("删除公告")
    @RequestMapping(value = "/deleteAnnouncement",method = RequestMethod.POST)
    public void deleteAnnouncement(int aid){
        announcementService.deleteAnnouncement(aid);
    }

    @ApiOperation("公告数量")
    @RequestMapping(value = "/countAnnouncement",method = RequestMethod.POST)
    public void countAnnouncement(){
        announcementService.countAnnouncement();
    }
}
