package com.pmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	User getById(Long id);
}
