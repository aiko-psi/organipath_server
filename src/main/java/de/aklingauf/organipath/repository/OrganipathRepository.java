package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganipathRepository extends JpaRepository<Task, Long> {

}
