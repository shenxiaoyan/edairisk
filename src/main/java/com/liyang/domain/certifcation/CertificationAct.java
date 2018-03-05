package com.liyang.domain.certifcation;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "certification_act")
public class CertificationAct extends AbstractAuditorAct<CertificationState> {

	public CertificationAct(String label, String actCode, Integer sort,ActGroup actGroup) {
		super(label, actCode, sort,actGroup);
		// TODO Auto-generated constructor stub
	}
	public CertificationAct(){
		super();
	}

}
