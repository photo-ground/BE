package ceos.phototoground.spot.repository;

import ceos.phototoground.spot.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot,Long> {
    List<Spot> findByIdIn(List<Long> spotIds);
}
