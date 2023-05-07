package com.pmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmt.entity.Backlog;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String projectIdentifier);
}
