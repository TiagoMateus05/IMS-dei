package pt.ulisboa.tecnico.rnl.dei.dms.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.Resource;

@Repository
@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Long>{
    @Query("SELECT r FROM Resource r WHERE r._currentUser._id = :personId")
    List<Resource> findByCurrentUserId(@Param("personId") Long personId);
}
