package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

}
