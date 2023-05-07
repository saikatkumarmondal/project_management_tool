package com.pmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmt.entity.Backlog;
import com.pmt.entity.Project;
import com.pmt.entity.ProjectTask;
import com.pmt.exceptions.ProjectIdException;
import com.pmt.exceptions.ProjectNotFoundException;
import com.pmt.repository.BacklogRepository;
import com.pmt.repository.ProjectRepository;
import com.pmt.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		try {
			Backlog backlog = this.backlogRepository.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backlog);
			Integer BacklogPT=backlog.getPTSequence();
			BacklogPT++;
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogPT);
			projectTask.setProjectIdentifier(projectIdentifier);
			if( projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("OmNamahShivaya");
			}
			
			return this.projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
	}
	
	public ProjectTask getSingleProject(String projectIdentifier) {
		Project project = this.projectRepository.findByProjectIdentifier(projectIdentifier);
	if(project == null) {
		throw new ProjectIdException("Project Id:-"+projectIdentifier+" does not exist!!");
	}
		return this.projectTaskRepository.findByProjectIdentifier(projectIdentifier);
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
		
	}
	
	public void deleteProjectTask(String projectId) {
	 ProjectTask projectTask = this.projectTaskRepository.findByProjectIdentifier(projectId);
	projectTaskRepository.delete(projectTask);
	}
	
	public List<ProjectTask> getAll(){
	return	this.projectTaskRepository.findAll();
	}
	
	public ProjectTask getProjectTaskByProjectSequnce(String backlog_id,String pt_id) {
		Backlog backlog = this.backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project with ID="+backlog_id+" des not exist");
		}
		
		ProjectTask projectTask = this.projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project with ID="+pt_id+" des not exist");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("ProjectTask with ID="+pt_id+" does not exist in Project'"+backlog_id);
		}
		
		return this.projectTaskRepository.findByProjectSequence(pt_id);
	}
	//update 
	
	public ProjectTask updateByProjectSequence(ProjectTask updateTask,String pt_id,String backlog_id) {
		ProjectTask projectTask = this.projectTaskRepository.findByProjectSequence(pt_id);
	projectTask=updateTask;
	
	return this.projectTaskRepository.save(projectTask);
	}
}
