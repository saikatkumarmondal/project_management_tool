package com.pmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmt.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findAll();
	
	Project findByProjectIdentifier(String projectIdentifier);
	
}
