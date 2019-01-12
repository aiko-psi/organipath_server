package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByProjectId(Long projectId);

    List<Task> findByParentId(Long parentId);
    // parentId because Spring generates this automatically

}
