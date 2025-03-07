package pt.ulisboa.tecnico.rnl.dei.dms.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.ResourceType;

@Repository
@Transactional
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long>{
    boolean existsByName(String name); //Uniqueness constraint
}
