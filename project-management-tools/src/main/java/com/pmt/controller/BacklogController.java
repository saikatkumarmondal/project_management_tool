package com.pmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmt.entity.ProjectTask;
import com.pmt.service.MapValidationExceptionService;
import com.pmt.service.ProjectTaskService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationExceptionService exceptionService;
	
	//add backlog
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,@PathVariable("backlog_id")String backlog_id,BindingResult result){
		ResponseEntity<Object> handleMapValidationExceptionService = this.exceptionService.handleMapValidationExceptionService(result);
		if(handleMapValidationExceptionService != null) {
			return handleMapValidationExceptionService;
		}
	ProjectTask addProjectTask = this.projectTaskService.addProjectTask(backlog_id, projectTask);
	return new ResponseEntity<ProjectTask>(addProjectTask,HttpStatus.CREATED);
	}
	
	//get single
	@GetMapping("/{backlog_id}")
	public ProjectTask getProjectBacklog(@PathVariable("backlog_id")String backlog_id){
	return this.projectTaskService.getSingleProject(backlog_id);
	}
	// delete
	@DeleteMapping("/{backlog_id}")
	public ResponseEntity<?> deleteProject(@PathVariable("backlog_id")String backlog_id){
		this.projectTaskService.deleteProjectTask(backlog_id);
		return new ResponseEntity<String>("Successfully deleted="+backlog_id,HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/all")
	public ResponseEntity<List<ProjectTask>> getAllProjectTask(){
	List<ProjectTask> all = this.projectTaskService.getAll();
	return new ResponseEntity<List<ProjectTask>>(all,HttpStatus.OK);
	}
	//get PT by projectSequnece
		@GetMapping("/{backlog_id}/{pt_id}")
		public ResponseEntity<?> getProjectTaskBySequence(@PathVariable("backlog_id")String backlog_id,@PathVariable("pt_id")String pt_id){
			ProjectTask projectTaskByProjectSequnce = this.projectTaskService.getProjectTaskByProjectSequnce(backlog_id,pt_id);
	
		return new ResponseEntity<ProjectTask>(projectTaskByProjectSequnce,HttpStatus.OK);
		}
		
		//update
		@PatchMapping("/{backlog_id}/{pt_id}")
		public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,@PathVariable("backlog_id")String backlog_id,@PathVariable("pt_id")String pt_id,BindingResult req){
			ResponseEntity<Object> handleMapValidationExceptionService = this.exceptionService.handleMapValidationExceptionService(req);
		if(handleMapValidationExceptionService != null) {
			return handleMapValidationExceptionService;
		}
		ProjectTask updateByProjectSequence = this.projectTaskService.updateByProjectSequence(projectTask, pt_id, backlog_id);
		return new ResponseEntity<ProjectTask>(updateByProjectSequence,HttpStatus.OK);
		}
}
