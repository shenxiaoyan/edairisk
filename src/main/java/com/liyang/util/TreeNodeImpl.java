package com.liyang.util;

import java.util.List;
import java.util.Set;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

public class TreeNodeImpl<T extends TreeNode> implements TreeNode<T>, Comparable<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	String label;
	String href;
	Integer sort=0;
	T parent;
	String iconClass;
	String router;
	String entityKey;
	Integer workflowId;
	List<T> children;
	Integer parentId;
	
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public String getEntityKey() {
		return entityKey;
	}
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	public Integer getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public T getParent() {
		return parent;
	}
	public void setParent(T parent) {
		this.parent = parent;
	}
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	public Integer getParentId() {
		return parentId==null?0:parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Override
	public int compareTo(T o) {
		if(getSort() > o.getSort()){
			return 1;
		}else if(getSort().equals(o.getSort())){
			return 0;
		}else{
			return -1;
		}
	}





}
