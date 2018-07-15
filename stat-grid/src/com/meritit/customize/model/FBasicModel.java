package com.meritit.customize.model;

import com.meritit.customize.model.parent.AbstractDataBean;

public class FBasicModel extends AbstractDataBean<String, String>{

	private static final String TASKID = "1510044073797";
	
	private static final String RULEID = "001";
	
	@Override
	public void init() {
		super.setValue("taskid", TASKID);
		super.setValue("ruleid", RULEID);
	}

}
