package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import java.util.Iterator;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.util.BOSUuid;

public class ScheduleBaseInfo extends AbstractScheduleBaseInfo implements Serializable 
{
	public static final int WEAK_MODE_SIZE = 50;
    public ScheduleBaseInfo()
    {
        super();
    }
    protected ScheduleBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * ��ΪԪ���ݷ�����AbstractXXXInfo ���޷��޸ģ��������ﲻ���ó���ķ������ṩ��Ĭ�ϵ�ʵ�֣���������
     * @return ScheduleTaskBaseCollection
     */
    public ScheduleTaskBaseCollection getScheduleTasks(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("taskEntrys");
    	return (ScheduleTaskBaseCollection)coll.cast(ScheduleTaskBaseCollection.class);
    }
    
    /**
     * ��ΪԪ���ݷ�����AbstractXXXInfo ���޷��޸ģ��������ﲻ���ó���ķ������ṩ��Ĭ�ϵ�ʵ�֣����������
     * Object: ���ز����� 's ����ʾ property 
     */
    public ScheduleTaskdisplaycolumnsCollection getScheduleDispColumns(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("dispColumns");
    	return (ScheduleTaskdisplaycolumnsCollection)coll.cast(ScheduleTaskdisplaycolumnsCollection.class);
    }
    
    /**
     * ��ΪԪ���ݷ�����AbstractXXXInfo ���޷��޸ģ��������ﲻ���ó���ķ������ṩ��Ĭ�ϵ�ʵ�֣����������
     * @return
     */
    public ScheduleCalendarInfo getCalendar(){
    	return (ScheduleCalendarInfo)get("calendar");
    }
/*    *//**
     * ��ΪԪ���ݷ�����AbstractXXXInfo ���޷��޸ģ��������ﲻ���ó���ķ������ṩ��Ĭ�ϵ�ʵ�֣����������
     * @return
     *//*
    public ScheduleTaskPropertyCollection getScheduleTaskPropertys(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("taskPropertys");
    	return (ScheduleTaskPropertyCollection)coll.cast(ScheduleTaskPropertyCollection.class);
    }*/
    
    private ScheduleTaskPropertyCollection  taskPropertys=null;
    public ScheduleTaskPropertyCollection getTaskPropertys(){
    	return this.taskPropertys;
    }
    
    public void setTaskPropertys(ScheduleTaskPropertyCollection taskPropertys){
    	this.taskPropertys=taskPropertys;
    }
    
    public void setDisplayColumn(String [] columnPropertyIds){
    	this.getScheduleDispColumns().clear();
    	for(int i=0;i<columnPropertyIds.length;i++){
        	ScheduleTaskdisplaycolumnsInfo item=new ScheduleTaskdisplaycolumnsInfo();
    		item.setId(BOSUuid.create(item.getBOSType()));
    		ScheduleTaskPropertyInfo taskProperty = getTaskProperty(columnPropertyIds[i]);
			item.setProperty(taskProperty);
    		item.setOrder(i);
			// item.setWidth(taskProperty.getWidth());
			this.getScheduleDispColumns().add(item);
		}
    }
    
	private ScheduleTaskPropertyInfo getTaskProperty(String properityId){
		for(Iterator iter=this.getTaskPropertys().iterator();iter.hasNext();){
			ScheduleTaskPropertyInfo item=(ScheduleTaskPropertyInfo)iter.next();
			if(item.getPropertyId().equals(properityId)){
				return item;
			}
		}
		return null;
	}
	
	/**
	 * ���Ƽƻ��Ƿ���Ա༭
	 * @return
	 *  by sxhong 2009-09-17 14:24:26
	 *  @see com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt.isKDTaskCellEditable(KDTask task, int column)
	 */
	public boolean isEditable(){
		return this.getBoolean("isEditable");
	}
	public void setEditable(boolean isEditable){
		this.setBoolean("isEditable", isEditable);
	}
	/**
	 * 
	 * �������ƻ��Ƿ�ֻ��; (�ƻ�ִ�У��ܽ��ȼƻ��� �Ǳ��桢�ύ״̬�ļƻ���ֻ��)
	 * @param isReadOnly
	 * ����ʱ�䣺2010-8-17
	 * �����ˣ�zhiqiao_yang
	 *
	 */
	public void setReadOnly(boolean isReadOnly){
		setBoolean("ScheduleIsReadOnly", isReadOnly);
	}
	public boolean isReadOnly(){
		return getBoolean("ScheduleIsReadOnly");
	}
}