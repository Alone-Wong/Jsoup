package com.meritit.customize.model;

/**
 * 发标公司编码对应实体
 * @author Lenovo
 *
 */
public class CompanyCode {

	/**
	 * 发标公司编码
	 */
	private String code;
	
	/**
	 * 发标公司
	 */
	private String company;

	public CompanyCode() {
		super();
	}
	
	public CompanyCode(String code, String company) {
		super();
		this.code = code;
		this.company = company;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
