package com.kingdee.eas.fdc.schedule.framework.util;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;

public interface IRESchTask {
	// id
	public BOSUuid getId();

	public void setId(BOSUuid id);
	
	/**
	 * Դid
	 */
	public String getSrcID();
	public void setSrcID(String srcID);
	
	/**
	 * ��������
	 */
	public String getName();
	public void setName(String name);

	/**
	 * �������
	 */
	public String getNumber();	
	public void setNumber(String number);

	/**
	 * �ϼ�����
	 */	
	public IRESchTask getParentTask();
	public void setParentTask(IRESchTask item);

	
	/**
	 * �������
	 */
	public RESchTaskTypeEnum getTaskType();
	public void setTaskType(RESchTaskTypeEnum item);

	/**
	 * ��Ч����
	 */
	public BigDecimal getDay();
	public void setDay(BigDecimal day);

	/**
	 * ǰ������
	 */
	public void addPredecessor(IRESchTaskPredecessor predecessor);

	/**
	 * �ƻ���ʼ����
	 * 
	 * @return
	 */
	public Date getPlanStart();
	public void setPlanStart(Date start);

	/**
	 * �ƻ���������
	 * @return
	 */
	public Date getPlanEnd();
	public void setPlanEnd(Date end);
	/**
	 * ������
	 */
	public void setAdminPerson(PersonInfo person);
	public PersonInfo getAdminPerson();

	/**
	 * ���β���
	 */
	public void setAdminDept(FullOrgUnitInfo dept);
	public FullOrgUnitInfo getAdminDept();
    
	public BOSObjectType getBOSType();
	
	
	/**
	 *�ɹ����
	 */
	public AchievementTypeInfo getAchievementType();
	public void setAchievementType(AchievementTypeInfo getAchievementType);
	
	/**
	 * ��׼����
	 */
	public StandardTaskGuideInfo getStandardTask();
	public void setStandardTask(StandardTaskGuideInfo standardTaskGuideInfo);
	
	/**
	 * ģ������ҵ������ 
	 */
	public RESchTemplateTaskBizTypeCollection getRESchBusinessType();
	public String getRESchBusinessTypeDesc();
	public void setRESchBusinessTypeDesc(String item);
	
	/**
	 * ��ע
	 */
	public String getDescription();
	public void setDescription(String description);
	
	/**
	 * MsProjectId
	 */
	public String getMsProjectId();
	public void setMsProjectId(String msProjectId);
	
	public String getMsParentPrjId();
	public void setMsParentPrjId(String msParentPrjId);
	
	/**
	 * ǰ����������
	 */
	public String getPredecessorDesc();
	public void setPredecessorDesc(String item);
	
	/**
	 * �ο�����
	 */
	public int getReferenceDay();
	public void setReferenceDay(int referenceDay);
	
	/**
	 *�������� 	 
	 */
	public Date checkDate();
	public void setCheckDate(Date checkDate);
	
	/**
	 * ����
	 */
	public int getLevel();

	public void setLevel(int level);

	/**
	 * �Ƿ���ϸ
	 */
	public boolean isIsLeaf();

	public void setIsLeaf(boolean isLeaf);
}
