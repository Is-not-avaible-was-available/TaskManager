package com.rajat.TaskManager.Repository;

import com.rajat.TaskManager.Model.Priority;
import com.rajat.TaskManager.Model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    List<Task> findAll(Specification<Task> specification);
}
