package pt.ulisboa.tecnico.rnl.dei.dms.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.dms.logs.domain.Logs;

@Repository
@Transactional
public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findBy_resource_Id(Long resourceId);
    List<Logs> findBy_person_Id(Long personId);
    
    @Query("SELECT l FROM Logs l WHERE l._person.id = :personId AND l._resource.id = :resourceId")
    List<Logs> findByPersonAndResource(@Param("personId") Long personId, @Param("resourceId") Long resourceId);

    @Modifying
    @Transactional
    @Query("UPDATE Logs l SET l._resource = NULL WHERE l._resource._id = :resourceId")
    void nullifyResourceReferences(@Param("resourceId") Long resourceId);

    @Modifying
    @Transactional
    @Query("UPDATE Logs l SET l._person = NULL WHERE l._person._id = :personId")
    void nullifyPersonReferences(@Param("personId") Long personId);
}


