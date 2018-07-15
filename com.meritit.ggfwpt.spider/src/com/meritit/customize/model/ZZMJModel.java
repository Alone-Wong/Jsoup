package com.meritit.customize.model;
/**
 * 住宅总建筑面积
 * 
 * @author viki
 *
 */
public class ZZMJModel {
	
	private String taskid;
	private String ruleid;
	private String id;
	private String inserttime;
	private String url;
	
	private String insertdate;
	private String statdate;
	private String datefreq;
	private String country;
	private String province;
	private String city;
	
	private String district;
	private String areacode;
	private String dim_sgfwmj;
	private String dim_jgfwmj;
	
	private String unit;

	public ZZMJModel(String taskid, String ruleid, String id,
			String inserttime, String url, String insertdate, String statdate,
			String datefreq, String country, String province, String city,
			String district, String areacode, String dim_sgfwmj,
			String dim_jgfwmj, String unit) {
		super();
		this.taskid = taskid;
		this.ruleid = ruleid;
		this.id = id;
		this.inserttime = inserttime;
		this.url = url;
		this.insertdate = insertdate;
		this.statdate = statdate;
		this.datefreq = datefreq;
		this.country = country;
		this.province = province;
		this.city = city;
		this.district = district;
		this.areacode = areacode;
		this.dim_sgfwmj = dim_sgfwmj;
		this.dim_jgfwmj = dim_jgfwmj;
		this.unit = unit;
	}

	public ZZMJModel() {
		super();
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInserttime() {
		return inserttime;
	}

	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInsertdate() {
		return insertdate;
	}

	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	public String getStatdate() {
		return statdate;
	}

	public void setStatdate(String statdate) {
		this.statdate = statdate;
	}

	public String getDatefreq() {
		return datefreq;
	}

	public void setDatefreq(String datefreq) {
		this.datefreq = datefreq;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getDim_sgfwmj() {
		return dim_sgfwmj;
	}

	public void setDim_sgfwmj(String dim_sgfwmj) {
		this.dim_sgfwmj = dim_sgfwmj;
	}

	public String getDim_jgfwmj() {
		return dim_jgfwmj;
	}

	public void setDim_jgfwmj(String dim_jgfwmj) {
		this.dim_jgfwmj = dim_jgfwmj;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
