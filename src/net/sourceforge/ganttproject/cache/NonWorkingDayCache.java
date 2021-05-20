package net.sourceforge.ganttproject.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * ����������ĳ���Ƿ�Ϊ�ǹ���ʱ��(TODO �迼�ǹ�������������)
 * @author zhiqiao_yang date��2010-8-20<p>
 * @version <EAS600>
 */
public class NonWorkingDayCache {
	private boolean enableCache;
	private Map cache;
	private static NonWorkingDayCache instance = new NonWorkingDayCache();
	public static NonWorkingDayCache getInstance(){
		return instance;
	}
	private NonWorkingDayCache(){
		cache = new HashMap(3000);
		enableCache = true;
	}
	public Boolean get(Date date){
		return (Boolean) cache.get(date);
	}
	
	public void put(Date date, boolean b){
		if(enableCache){
			cache.put(date, Boolean.valueOf(b));
		}
	}
	public int size(){
		return cache.size();
	}
	
	public void clear(){
		cache.clear();
	}
	
	public void setEnableCache(boolean enableCache){
		this.enableCache = enableCache;
	}
}
