package Dem005;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class UserInfo {
	private String userId;
	private String userName;
	private String cardNo;

	/**
	 * 自定义缓存map
	 */
	@SuppressWarnings("rawtypes")
	HashMap cacheMap = new HashMap();

	// 省略其它信息和getter 和setter
	public UserInfo() {
		super();
	}

	public UserInfo(String userId, String userName, String cardNo) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.cardNo = cardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", cardNo=" + cardNo + "]";
	}

	/**
	 * 初始化用户信息
	 */
	@SuppressWarnings("unchecked")
	public void initUserInfoCache(UserInfo userInfo) {
		// 载入缓存
		cacheMap.put("cacheData", userInfo);
		System.out.println("缓存添加完成-----OK");
	}

	/**
	 * 获取缓存
	 */
	public UserInfo getUserInfoFromCacheById(String cacheData) {
		UserInfo userInfo = (UserInfo) cacheMap.get(cacheData);
		if (userInfo == null || "".equals(userInfo)) {
			initUserInfoCache(this);
			System.out.println("0");//Test
			return (UserInfo) cacheMap.get(cacheData);
		} else {
			System.out.println("1");//Test
			return (UserInfo) cacheMap.get(cacheData);
		}

	}

	/**
	 * 更新缓存
	 */
	public void updateUserInfoCache() {

		final String cacheData = "cacheData";

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				getUserInfoFromCacheById(cacheData);
			}

		};

		Timer timer = new Timer();

		timer.schedule(task, 0, 1000 * 10 * 60 * 30);

	}

}
