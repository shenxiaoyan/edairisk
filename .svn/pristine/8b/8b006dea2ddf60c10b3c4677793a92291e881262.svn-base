package com.liyang.domain.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@MappedSuperclass
public  class AbstractWorkflowState<E extends AbstractWorkflowEntity, W extends AbstractWorkflow , A extends AbstractWorkflowAct> extends AbstractAuditorState<E, A>{

	public AbstractWorkflowState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public AbstractWorkflowState(){
		super();
	}
	

	@ManyToMany(mappedBy="states")
	private Set<W> workflows;
		
	
	@OneToMany(mappedBy="targetState")
	private Set<A> fromActs;
	
	@Transient
	private Boolean done=false;
	
	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getDone()
	 */
	public Boolean getDone(){
		return this.done;
	}
	



	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getFromActs()
	 */
	public Set<A> getFromActs() {
		return fromActs;
	}

	public void setFromActs(Set<A> fromActs) {
		this.fromActs = fromActs;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getWorkflows()
	 */
	public Set<W> getWorkflows() {
		return workflows;
	}

	public void setWorkflows(Set<W> workflows) {
		this.workflows = workflows;
	}

}
