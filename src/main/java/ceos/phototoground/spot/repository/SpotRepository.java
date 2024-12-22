package ceos.phototoground.spot.repository;

import ceos.phototoground.spot.domain.Spot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByIdIn(List<Long> spotIds);

    List<Spot> findByUniv_Id(Long univId);
}
