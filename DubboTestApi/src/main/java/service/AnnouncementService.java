package service;

import entity.AnnouncementEntity;
import org.springframework.data.domain.Pageable;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
public interface AnnouncementService {
    int addAnnouncement(AnnouncementEntity answerEntity);
    List<AnnouncementEntity> announcementList(PageConverter pageable);
    long countAnnouncement();
    void deleteAnnouncement(int aid);
}
