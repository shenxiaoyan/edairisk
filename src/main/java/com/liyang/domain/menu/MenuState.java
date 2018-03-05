package com.liyang.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
@Entity
@Table(name = "menu_state")
public class MenuState extends AbstractAuditorState<Menu, MenuAct> {

	public MenuState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public MenuState(){
		super();
	}

}
