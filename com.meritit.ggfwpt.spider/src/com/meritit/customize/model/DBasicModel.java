package com.meritit.customize.model;

import com.meritit.common.model.parent.AbstractDataBean;

public class DBasicModel extends AbstractDataBean<String, String>{

	private static final String TASKID = "1510554070029";
	
	private static final String RULEID = "001";
	
	@Override
	public void init() {
		super.setValue("taskid", TASKID);
		super.setValue("ruleid", RULEID);
	}

}
