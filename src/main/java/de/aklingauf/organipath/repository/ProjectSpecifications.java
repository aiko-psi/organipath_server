package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Project;
//import de.aklingauf.organipath.model.Project_;
import de.aklingauf.organipath.model.Project_;
import de.aklingauf.organipath.model.User;
import de.aklingauf.organipath.security.UserPrincipal;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static de.aklingauf.organipath.model.Task_.parentId;
import static de.aklingauf.organipath.model.Task_.projectId;

// Specifications: https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
// https://www.logicbig.com/tutorials/spring-framework/spring-data/specifications.html
// Needs Metamodel (Task_): https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.metamodel
// Remember to turn on Annotations
// as in https://docs.jboss.org/hibernate/orm/5.3/topical/html_single/metamodelgen/MetamodelGenerator.html

public class ProjectSpecifications {
    public static Specification<Project> isOwnedBy(UserPrincipal currentUser) {
        return new Specification<Project>(){
            @Override
            public Predicate toPredicate(Root<Project> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get(Project_.OWNER_ID), currentUser.getId());
                return equalPredicate;
            }
        };
    }


}
