package de.aklingauf.organipath.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SingularAttribute<Project, User> owner;
	public static volatile SingularAttribute<Project, Date> createdAt;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, Boolean> privacy;
	public static volatile SingularAttribute<Project, Long> id;
	public static volatile SingularAttribute<Project, Long> ownerId;
	public static volatile ListAttribute<Project, Task> tasks;
	public static volatile SingularAttribute<Project, Date> updatedAt;

	public static final String OWNER = "owner";
	public static final String CREATED_AT = "createdAt";
	public static final String NAME = "name";
	public static final String PRIVACY = "privacy";
	public static final String ID = "id";
	public static final String OWNER_ID = "ownerId";
	public static final String TASKS = "tasks";
	public static final String UPDATED_AT = "updatedAt";

}

