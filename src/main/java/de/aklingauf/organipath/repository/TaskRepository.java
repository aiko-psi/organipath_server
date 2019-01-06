package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    Page<Task> findByParentId(Long parentId, Pageable pageable);
    // why the fuck ist der name der Funktion ausschlaggebend?! findByTaskId geht nicht, findByPartentID schon

}
