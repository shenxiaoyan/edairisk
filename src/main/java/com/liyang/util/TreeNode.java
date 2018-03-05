package com.liyang.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.liyang.domain.menu.Menu;

public interface TreeNode<T extends TreeNode> extends Serializable{
	
	Integer getId();
	void setId(Integer id);
	
	String getLabel();
	void setLabel(String str);

	/* (non-Javadoc)
	 * @see com.liyang.domain.MenuProjection#getSort()
	 */
	Integer getSort();
	void setSort(Integer sort);
	/* (non-Javadoc)
	 * @see com.liyang.domain.MenuProjection#getParent()
	 */
	T getParent();
	void setParent(T t);
	
	Integer getParentId();
	void setParentId(Integer id);
	
	/* (non-Javadoc)
	 * @see com.liyang.domain.MenuProjection#getChildren()
	 */
	List<T> getChildren();
	
	void setChildren(List<T> children);

}