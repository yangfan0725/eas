package com.kingdee.eas.fdc.aimcost;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * �ɷ������˷��أ����ڷ�װ�����adapter
 * @author xiaohong_shi
 *
 */
public class FullDynamicCostMap extends CostMap{
	public FullDynamicCostMap(){
	}
	
	public BigDecimal getDynBuildApp(){
		return (BigDecimal)get("DynBuildApp");
	}
	public void setDynBuildApp(BigDecimal dynBuildApp){
		put("DynBuildApp",dynBuildApp);
	}
	public BigDecimal getDynSellApp(){
		return (BigDecimal)get("DynSellApp");
	}
	public void setDynSellApp(BigDecimal dynSellApp){
		put("DynSellApp",dynSellApp);
	}
	public HappenDataGetter getHappenDataGetter(){
		return (HappenDataGetter)get("HappenDataGetter");
	}
	public void setHappenDataGetter(HappenDataGetter happenDataGetter){
		put("HappenDataGetter",happenDataGetter);
	}
	
	public Map getAimCostMap(){
		return (Map)get("aimCostMap");
	}
	public void setAimCostMap(Map aimCostMap){
		put("aimCostMap",aimCostMap);
	}
	
	public Map getDynamicCostMapp(){
		return (Map)get("dynamicCostMap");
	}
	public void setDynamicCostMapp(Map dynamicCostMap){
		put("dynamicCostMap",dynamicCostMap);
	}
	public void setNoHappen(Map noHappenMap){
		put("noHappenMap",noHappenMap);
	}
	public Map getNoHappen(){
		return (Map)get("noHappenMap");
	}
}
