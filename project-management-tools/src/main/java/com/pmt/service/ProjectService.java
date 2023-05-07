package com.pmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmt.entity.Backlog;
import com.pmt.entity.Project;
import com.pmt.exceptions.ProjectIdException;
import com.pmt.repository.BacklogRepository;
import com.pmt.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId() == null) {
				Backlog backlog=new Backlog();
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				project.setBacklog(backlog);
			}
			
			if(project.getId() != null) {
				project.setBacklog(this.backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return this.projectRepository.save(project);
		} catch (Exception e) {
		throw new ProjectIdException("Project ID is '"+project.getProjectIdentifier()+"'already exist");
		}
	}
	
	
	public Project getSingleProject(String projectIdentifier) {
	Project project= this.projectRepository.findByProjectIdentifier(projectIdentifier);
	if(project == null) {
		throw new ProjectIdException("Project ID ' "+projectIdentifier+"'doesnot exist");
	}
	return project;
	}
	
	public List<Project> getAll(){
		return this.projectRepository.findAll();
	}
	
	public void delete(String projectIdentifier) {
		Project project = this.projectRepository.findByProjectIdentifier(projectIdentifier);
	if(project == null) {
		throw new ProjectIdException("Project ID' "+projectIdentifier+"'does not exist");
	}
	this.projectRepository.delete(project);
	}
}
