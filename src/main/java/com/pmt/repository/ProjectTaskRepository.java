package com.pmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmt.entity.ProjectTask;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
	
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);

	ProjectTask findByProjectIdentifier(String projectIdentifier);
	
	ProjectTask findByProjectSequence(String projectSequence);
}
