package dao;

import entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yan
 */
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity,Integer> {
    AnnouncementEntity findByAid(int aid);
}
