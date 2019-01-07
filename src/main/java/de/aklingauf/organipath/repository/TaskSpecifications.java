package de.aklingauf.organipath.repository;

import de.aklingauf.organipath.model.Task;
import de.aklingauf.organipath.model.Task_;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
// Specifications: https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
// https://www.logicbig.com/tutorials/spring-framework/spring-data/specifications.html
// Needs Metamodel (Task_): https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.metamodel
// Remember to turn on Annotations
// as in https://docs.jboss.org/hibernate/orm/5.3/topical/html_single/metamodelgen/MetamodelGenerator.html

public class TaskSpecifications{
    public static Specification<Task> isDirectChildOf(Long parentId) {
        return new Specification<Task>(){
            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get(Task_.parentId), parentId);
                return equalPredicate;
            }
        };
    }

    public static Specification<Task> isInProject(Long projectId) {
        return new Specification<Task>(){
            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get(Task_.projectId), projectId);
                return equalPredicate;
            }
        };
    }

}
