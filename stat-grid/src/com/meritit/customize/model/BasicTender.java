package com.meritit.customize.model;

import java.io.Serializable;

public class BasicTender implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 536059271637899059L;
	private String id;
	private String taskid;
	private String ruleid;
	private String projectNum;
	private String gongg;
	private String projectState;
	private String publishCompany;
	private String title;
	private String url;
	private String createtime;
	private String inserttime;
	private String company;
	
	public BasicTender() {
		super();
	}
	
	public BasicTender(String id, String taskid, String ruleid, String projectNum, String gongg, String projectState, String publishCompany,
			String title, String url, String createtime, String inserttime, String company) {
		super();
		this.id = id;
		this.taskid = taskid;
		this.ruleid = ruleid;
		this.projectNum = projectNum;
		this.gongg = gongg;
		this.projectState = projectState;
		this.publishCompany = publishCompany;
		this.title = title;
		this.url = url;
		this.createtime = createtime;
		this.inserttime = inserttime;
		this.company = company;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	public String getGongg() {
		return gongg;
	}
	public void setGongg(String gongg) {
		this.gongg = gongg;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public String getPublishCompany() {
		return publishCompany;
	}
	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
