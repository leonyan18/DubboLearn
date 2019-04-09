package service;

import org.apache.dubbo.config.annotation.Service;
import dao.AnnouncementRepository;
import entity.AnnouncementEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import util.ErrorMessage;
import util.LogicException;
import util.PageConverter;

import java.util.List;

/**
 * @author yan
 */
@Service
@Component
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private static final Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);
    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public int addAnnouncement(AnnouncementEntity answerEntity) {
        return announcementRepository.save(answerEntity).getAid();
    }

    @Override
    public List<AnnouncementEntity> announcementList(PageConverter pageable) {
        logger.info(announcementRepository);
        logger.info(pageable);
        return announcementRepository.findAll(pageable.getPageRequest()).getContent();
    }

    public List<AnnouncementEntity> announcementList(int pageNumber, int pageSize) {
        return announcementRepository.findAll(PageRequest.of(pageNumber,pageSize)).getContent();
    }

    @Override
    public long countAnnouncement() {
        return announcementRepository.count();
    }

    @Override
    public void deleteAnnouncement(int aid) {
        if (announcementRepository.existsById(aid)) {
            announcementRepository.delete(announcementRepository.findByAid(aid));
        } else {
            throw LogicException.le(ErrorMessage.NO_SUCH_ENTITY);
        }
    }
}
