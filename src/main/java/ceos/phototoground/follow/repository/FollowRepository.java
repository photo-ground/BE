package ceos.phototoground.follow.repository;

import ceos.phototoground.follow.domain.Follow;
import ceos.phototoground.photoProfile.domain.PhotoProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
