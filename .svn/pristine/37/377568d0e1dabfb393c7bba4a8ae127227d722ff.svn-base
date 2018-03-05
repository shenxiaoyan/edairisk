package com.liyang.domain.base;

import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class AbstractWorkflow<E extends AbstractWorkflowEntity, S extends AbstractWorkflowState> extends BaseEntity {
	

	@OneToMany(mappedBy="workflow")
	private Set<E> entities;
	
	
	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "workflow_id") }, inverseJoinColumns = {
			@JoinColumn(name = "state_id") })
	private Set<S> states;


	private String code;
	
	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Set<E> getEntities() {
		return entities;
	}


	public void setEntities(Set<E> entities) {
		this.entities = entities;
	}


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowProjection#getStates()
	 */
	public Set<S> getStates() {
		return states;
	}


	public void setStates(Set<S> states) {
		this.states = states;
	}




	

}
