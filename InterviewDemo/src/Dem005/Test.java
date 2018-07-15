package Dem005;

public class Test {
	
	public static void main(String[] args) {
		
		String cacheData = "cacheData";
		
		UserInfo info = new UserInfo();
		info.setUserId("1");
		info.setUserId("black");
		info.setCardNo("11");
		
		info.initUserInfoCache(info);
		
	//UserInfo userInfoFromCacheById = info.getUserInfoFromCacheById(cacheData);
		
		info.updateUserInfoCache();
		
		
	//	System.err.println(userInfoFromCacheById);
		
		
	}
	
	
}
