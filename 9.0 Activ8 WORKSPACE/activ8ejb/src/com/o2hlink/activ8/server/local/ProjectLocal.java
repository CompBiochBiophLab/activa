package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.HasProjects;
import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.entity.Workpackage;

/**
 * Remote operations on a project
 * @author Miguel Angel Hernandez
 **/
@Local
public interface ProjectLocal {
	/**
	 * 
	 **/
	public Project save(Project project, User owner);
	/**
	 * 
	 **/
	public List<Project> getProjects(HasProjects provider);
	/**
	 * 
	 **/
	public List<Workpackage> getWorkpackages(Project project);
	/**
	 * 
	 **/
	public Workpackage addWorkpackage(Project project, Workpackage workpackage);
	/**
	 * 
	 **/
	public void removeWorkpackage(Project project, Workpackage workpackage);
}
