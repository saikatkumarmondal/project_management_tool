package com.pmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmt.entity.Project;
import com.pmt.entity.ProjectTask;
import com.pmt.service.MapValidationExceptionService;
import com.pmt.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MapValidationExceptionService mapValidationExceptionService;
	//add backlog
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,BindingResult result){
		ResponseEntity<Object> handleMapValidationExceptionService = this.mapValidationExceptionService.handleMapValidationExceptionService(result);
		if(handleMapValidationExceptionService != null) {
			return handleMapValidationExceptionService;
		}
		
		Project saveOrUpdateProject = this.projectService.saveOrUpdateProject(project);
	return new ResponseEntity<Project>(saveOrUpdateProject,HttpStatus.CREATED);
	}
	//get single
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getSingleProject(@PathVariable("projectIdentifier")String projectIdentifier){
		Project singleProject = this.projectService.getSingleProject(projectIdentifier);
	
	return new ResponseEntity<>(singleProject,HttpStatus.OK);
			}
	//get all
	@GetMapping("/all")
	public List<Project> getAll(){
		return this.projectService.getAll();
	}
	//delete
	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProject(@PathVariable("projectIdentifier")String projectIdentifier){
		this.projectService.delete(projectIdentifier);
		return new ResponseEntity<String>("Project ID'"+projectIdentifier+"' was deleted",HttpStatus.OK);
	}
	
	
}
