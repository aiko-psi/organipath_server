package de.aklingauf.organipath.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, Task> parent;
	public static volatile SingularAttribute<Task, String> notes;
	public static volatile SingularAttribute<Task, Boolean> mini;
	public static volatile SingularAttribute<Task, Long> dangerZone;
	public static volatile ListAttribute<Task, Task> subtasks;
	public static volatile SingularAttribute<Task, Project> project;
	public static volatile SingularAttribute<Task, Long> parentId;
	public static volatile SingularAttribute<Task, Date> createdAt;
	public static volatile ListAttribute<Task, Change> statChanges;
	public static volatile SingularAttribute<Task, Boolean> draft;
	public static volatile SingularAttribute<Task, Date> daily;
	public static volatile SingularAttribute<Task, String> name;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, Date> deadline;
	public static volatile SingularAttribute<Task, Long> projectId;
	public static volatile SingularAttribute<Task, Boolean> fun;
	public static volatile SingularAttribute<Task, Date> updatedAt;

	public static final String PARENT = "parent";
	public static final String NOTES = "notes";
	public static final String MINI = "mini";
	public static final String DANGER_ZONE = "dangerZone";
	public static final String SUBTASKS = "subtasks";
	public static final String PROJECT = "project";
	public static final String PARENT_ID = "parentId";
	public static final String CREATED_AT = "createdAt";
	public static final String STAT_CHANGES = "statChanges";
	public static final String DRAFT = "draft";
	public static final String DAILY = "daily";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String DEADLINE = "deadline";
	public static final String PROJECT_ID = "projectId";
	public static final String FUN = "fun";
	public static final String UPDATED_AT = "updatedAt";

}

