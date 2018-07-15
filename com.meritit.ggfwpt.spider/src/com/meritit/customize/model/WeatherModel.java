package com.meritit.customize.model;
/**
 * 天气
 * @author merit
 *
 */
public class WeatherModel {
	
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
	
	private String dim_maxt;
	private String dim_mint;
	private String dim_weather;
	private String dim_windd;
	private String dim_windp;
	

	
	public WeatherModel(){
		super();
	}

	public WeatherModel(String taskid, String ruleid, String id,
			String inserttime, String url, String insertdate, String statdate,
			String datefreq, String country, String province, String city,
			String district, String areacode, String dim_maxt, String dim_mint,
			String dim_weather, String dim_windd, String dim_windp) {
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
		this.dim_maxt = dim_maxt;
		this.dim_mint = dim_mint;
		this.dim_weather = dim_weather;
		this.dim_windd = dim_windd;
		this.dim_windp = dim_windp;
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

	public String getDim_maxt() {
		return dim_maxt;
	}

	public void setDim_maxt(String dim_maxt) {
		this.dim_maxt = dim_maxt;
	}

	public String getDim_mint() {
		return dim_mint;
	}

	public void setDim_mint(String dim_mint) {
		this.dim_mint = dim_mint;
	}

	public String getDim_weather() {
		return dim_weather;
	}

	public void setDim_weather(String dim_weather) {
		this.dim_weather = dim_weather;
	}

	public String getDim_windd() {
		return dim_windd;
	}

	public void setDim_windd(String dim_windd) {
		this.dim_windd = dim_windd;
	}

	public String getDim_windp() {
		return dim_windp;
	}

	public void setDim_windp(String dim_windp) {
		this.dim_windp = dim_windp;
	}
	
	
}
