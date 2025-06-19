package com.rajat.TaskManager.Model;

import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class TaskSpecification {

    public static Specification<Task> hasPriority(Priority priority){
        return (root, query, criteriaBuilder) ->
                (priority==null) ? null : criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> lessThanDeadLine(Instant deadLine){
        return (root, query, criteriaBuilder) -> (deadLine==null) ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("deadLine"), deadLine);
    }

    public static Specification<Task> hasStatus(Status status){
        return (root, query, criteriaBuilder) -> (status==null) ?
                null :criteriaBuilder.equal(root.get("status"), status);
    }
}
