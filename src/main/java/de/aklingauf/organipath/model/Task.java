package de.aklingauf.organipath.model;

// from Tutorial https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "subtasks"},
        allowGetters = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private Long id;

    @NotBlank
    private String name;

    private String notes;


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name= "parent_project_id")
    @NotNull
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false) //, referencedColumnName = "parent_project_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(columnDefinition="integer", name = "parent_task_id",  nullable=true, insertable=true, updatable=true)
            //referencedColumnName = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task parent;

    @Column(name= "parent_id")
    private Long parentId;

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Task> subtasks;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }

    public Task getParent() { return parent; }

    public void setParent(Task parent) { this.parent = parent; }

    public List<Task> getSubstaks() { return subtasks; }

    public void setSubstaks(List<Task> substaks) { this.subtasks = substaks; }

    public void addSubtask(Task task) { this.subtasks.add(task); }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getParentId() { return parentId; }

    public void setParentId(Long parentId) { this.parentId = parentId; }

}
