package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTaskAdjustEvent;

public class ScheduleTaskBaseInfo extends AbstractScheduleTaskBaseInfo implements Serializable 
{
    public ScheduleTaskBaseInfo()
    {
        super();
    }
    protected ScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * Object: ���� 
     */
    public ScheduleBaseInfo getScheduleBase()
    {
        return (ScheduleBaseInfo)get("schedule");
    }
    
    /**
     * ��ΪԪ���ݷ�����AbstractXXXInfo ���޷��޸ģ��������ﲻ���ó���ķ������ṩ��Ĭ�ϵ�ʵ�֣���������
     * @return ScheduleTaskDependCollection
     */
    public ScheduleTaskDependCollection getDepends(){
    	AbstractObjectCollection coll=(AbstractObjectCollection)get("dependEntrys");
    	return (ScheduleTaskDependCollection)coll.cast(ScheduleTaskDependCollection.class);
    }

	/**
	 * ������д�˷����Զ����Զ������
	 * 
	 * @return
	 * @throws BOSException
	 */
    public ScheduleTaskPropertyCollection getCustomPropertys(int uiMark)
			throws BOSException {
    	return new ScheduleTaskPropertyCollection();
    }

	/**
	 * ͨ������ȡ���Զ�������
	 * 
	 * @return
	 * @throws BOSException
	 */
    public ScheduleTaskPropertyInfo getCustomPropertyByName(String name)
			throws BOSException {
    	// ���������Ż�: ����ƿ�� 3.8% - 3,314 ms - 6,771 inv by skyiter_wang 2014-06-11
    	
    	// ���������Ż�: �˴��ǿ����Ż��� by skyiter_wang 2014-06-11
    	// T_SCH_ScheduleTaskProperty��һ���������Գ������ܹ�24����¼�����ڿ��������ء�Ĭ���п���������ԡ�
		// ��˿��Է���һ������Map�С�
		// �����Ѿ���д����FDCScheduleTaskInfo.getCustomPropertyByName
    	ScheduleTaskPropertyCollection customPropertys = getCustomPropertys(0);
    	if (customPropertys == null)
			return null;
    	for(int i=0;i<customPropertys.size();i++){
    		ScheduleTaskPropertyInfo scheduleTaskPropertyInfo = customPropertys.get(i);
    		String myName = scheduleTaskPropertyInfo.getName();
			if(myName!=null&&myName.equalsIgnoreCase(name)){
    			return scheduleTaskPropertyInfo;
    		}
    	}
    	return null;
    }
    
    public Object getCustomPropertyValue(String mapKey){
/*    	Map customDispayMap=getCustomDispayMap();
    	String disPlayKey=(String)customDispayMap.get(key);
    	if(disPlayKey==null){
    		disPlayKey=key;
    	}*/
    	if (mapKey == null) {
			return null;
		}
    	return getValueByExtendKey(mapKey);
    }
    
    /**
	 * ȡ��ö�ٵ�����
	 * <p>
	 * ����ö��·�����Լ�ö�ٶ�Ӧinfo�е��ֶΣ�������õ�ö�ٲ�ȡ������
	 * 
	 * @author emanon
	 * @param enumClass
	 * @param mapKey
	 * @return
	 */
    public Object getCustomPropertyEnum(String enumClass, String mapKey) {
		if (mapKey == null) {
			return null;
		}
		Object obj = this;
		if ((this.get(mapKey)) instanceof String) {
			obj = ((IObjectValue) obj).get(mapKey);
			try {
				Method method = Class.forName(enumClass).getMethod("getEnum",new Class[]{String.class});
				obj = method.invoke(null, new Object[]{obj});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				return obj;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			// ����IObjectValue�������Կ϶��Ǵ����ˣ�ֱ�ӷ���Null
			return ((IObjectValue) obj).get(mapKey) == null ? null : ((IObjectValue) obj).get(mapKey);
		}
		return obj;
	}
    
    public void setCustomPropertyValue(String name, Object value)
			throws BOSException {
    	// ���������Ż��� ����ƿ�� 4.2% - 3,697 ms - 6,771 inv by skyiter_wang 2014-06-11
    	
    	ScheduleTaskPropertyInfo prop = getCustomPropertyByName(name);
    	if(prop==null)
    		return;
    	String mapKey=prop.getMapKey();
    	if(mapKey==null){
    		return;
    	}
    	//��������
    	if(mapKey.indexOf('.')<0){
    		this.put(mapKey, value);
    		return;
    	}
    	//��������,�ҵ������ϼ���Ȼ����put��ȥ
    	String parentMapKey=mapKey.substring(0,mapKey.lastIndexOf('.'));
    	String subMapKey=mapKey.substring(mapKey.lastIndexOf('.')+1);
    	Object obj = getValueByExtendKey(parentMapKey);
    	if(obj instanceof IObjectValue){
    		((IObjectValue)obj).put(subMapKey, value);
    	}
    }
    
    /**
     * adminUser.CU.number ��ȡ��adminUserֵ�����CU��number
     * @param extendKey ��adminUser.CU.number
     * @return
     */
    public Object getValueByExtendKey(String extendKey){
    	String splits[]=extendKey.split("\\.");
    	Object obj=this;
    	for(int i=0;i<splits.length;i++){
    		if(obj instanceof IObjectValue){
    			String key=splits[i];
    			obj=((IObjectValue)obj).get(key);
    		}else{
    			//����IObjectValue�������Կ϶��Ǵ����ˣ�ֱ�ӷ���Null
    			return null;
    		}
    	}
/*    	if(obj instanceof BigDecimal){
    		obj=obj.toString();
    	}*/
    	return obj;
    }
    
	/**
	 * ���ƽڵ��Ƿ���Ա༭�����������ʹ��
	 * @return
	 */
	public boolean isEditable(){
		return this.getBoolean("isEditable");
	}
	public void setEditable(boolean isEditable){
		this.setBoolean("isEditable", isEditable);
	}
	
    public boolean isAdd(){
    	return this.getBoolean("isAdd");
    }
    public void setIsAdd(boolean isAdd){
    	this.setBoolean("isAdd", isAdd);
    }
    /**
     * �Ƿ��Ǳ��ƻ����������ϼ�������������
     * @return
     */
    public boolean isScheduleTask(){
    	return this.getBoolean("isScheduleTask");
    }
    public void setIsScheduleTask(boolean isScheduleTask){
    	this.setBoolean("isScheduleTask", isScheduleTask);
    }
    
    /**
     * ��ʼʱ������Ʒ�Χ�����ڿ����¼��ڵ�ĵ���
     * @param start
     */
    public void setBoundStart(Date start){
    	this.put("boundStart", start);
    }
    
    /**
     * ����ʱ������Ʒ�Χ�����ڿ����¼��ڵ�ĵ���
     * @param end
     */
    public void setBoundEnd(Date end){
    	this.put("boundEnd", end);
    }
    
    public Date getBoundStart(){
    	return (Date)this.get("boundStart");
    }
    
    public Date getBoundEnd(){
    	return (Date)this.get("boundEnd");
    }
    
    /**
     * �Ƿ���Ե���
     * @param event
     * @return
     */
    public boolean adjustable(KDTaskAdjustEvent event){
    	return true;
    }
    
    public Date getStart() {
		if (getDate("start") == null) {
			return FDCDateHelper.getDayBegin(new Date());
		}
		return FDCDateHelper.getDayBegin(getDate("start"));
	}

	public Date getEnd() {
		if (getDate("end") == null) {
			return FDCDateHelper.getDayBegin(new Date());
		}
		return FDCDateHelper.getDayBegin(getDate("end"));
	}
}