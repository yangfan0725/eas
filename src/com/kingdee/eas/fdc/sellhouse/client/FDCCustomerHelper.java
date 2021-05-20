package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.PositionHierarchyCollection;
import com.kingdee.eas.basedata.org.PositionHierarchyFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyInfo;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class FDCCustomerHelper {
	private static final Logger log = Logger.getLogger(FDCCustomerHelper.class);
	
	/**
	 * ��֤֤�������Ƿ���Ա�¼��
	 * */
	public static boolean verifyCertificateNumber(CertifacateNameEnum nameEnum, String certificateNum, String exceptCusId) throws BOSException {
		if(StringUtils.isEmpty(certificateNum)){
			return true;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateName", nameEnum.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNum));
		if(exceptCusId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cuses);
		int result = MsgBox.showConfirm2New(null, "�ͻ�֤���������� <" + info.getCreateTime().toString() +"> ¼��,����Ϊ <" + info.getName()+"> ,���۹���Ϊ <"+ info.getSalesman().getName() + "> ,�Ƿ������");
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * ��֤֤�������Ƿ���Ա�¼��
	 * update by renliang at 2011-3-2
	 * */ 
	public static boolean verifyCertificateNumber(String certificateId, String certificateNum, String exceptCusId) throws BOSException {
		if(StringUtils.isEmpty(certificateNum)){
			return true;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateName.id", certificateId));
		filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNum));
		if(exceptCusId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cuses);
		int result = MsgBox.showConfirm2New(null, "�ͻ�֤���������� <" + info.getCreateTime().toString() +"> ¼��,����Ϊ <" + info.getName()+"> ,���۹���Ϊ <"+ info.getSalesman().getName() + "> ,�Ƿ������");
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * ��֤�ͻ������Ƿ���Ա�¼��
	 * @throws BOSException 
	 * */
	public static boolean verifyCustomerName(String customerName,MarketDisplaySetting setting,SellProjectInfo sp,String id) throws BOSException
	{
		if(StringUtils.isEmpty(customerName)){
			return true;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", customerName));
		filter.getFilterItems().add(new FilterItemInfo("project.id", sp.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("id", id,CompareType.NOTEQUALS));
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}else if(cuses!=null && setting.getNameRepeat()==0)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cuses);
			StringBuffer msg = new StringBuffer();
			msg.append("�ͻ��������� <");
			msg.append(info.getCreateTime().toString());
			msg.append("> ¼��,����Ϊ <");
			msg.append(info.getName());
			msg.append("> ,���۹���Ϊ <");
			msg.append(info.getSalesman().getName());
			msg.append(">");
			MsgBox.showInfo(msg.toString());
			return false;
		}else if(cuses!=null && setting.getNameRepeat()==1)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cuses);
			int result = MsgBox.showConfirm2New(null, "�ͻ�֤���������� <" + info.getCreateTime().toString() +"> ¼��,����Ϊ <" + info.getName()+"> ,���۹���Ϊ <"+ info.getSalesman().getName() + "> ,�Ƿ������");
			if(result == MsgBox.YES){
				return true;
			}
		}else if(cuses!=null && setting.getNameRepeat()==2)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * �ж�txt�������Ƿ�Ϊ��
	 * @param txt
	 * 			�ı��ؼ�
	 * @param msg
	 * 			���Ϊ����ʾ������
	 * @return �ı������Ƿ�Ϊ��
	 * */
	public static boolean verifyInputNotNull(KDTextField txt, String msg) {
		String str = txt.getText().trim();
		if(str.equals("")){
			MsgBox.showWarning(msg);
			return false;
		}
		return true;
	}
	
	/**
	 * ��֤�õ绰�����Ƿ��ܱ�¼��
	 * @param phone
	 * 			����֤�ĺ���
	 * @param exceptCusId
	 * 			Ҫ����Ŀͻ����루�ò�����Ҫ��Կͻ��޸�ʱ,Ҫ����ǰ�޸ĵĿͻ���¼���⣩
	 * */
	public static boolean verifyPhone(String phone, String exceptCusId) throws BOSException {
		if(phone == null  ||  phone.equals("") ||  !isValidInput(phone)){
			return false;
		}
		
		FDCCustomerCollection cusList = getSamePhoneRecord(phone, exceptCusId);
		if(cusList == null){
			return false;
		}
		
		if(cusList.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cusList);
		
		StringBuffer msg = new StringBuffer();
		msg.append("�ͻ���ϵ�绰���� <");
		msg.append(info.getCreateTime().toString());
		msg.append("> ¼��,����Ϊ <");
		msg.append(info.getName());
		msg.append("> ,���۹���Ϊ <");
		msg.append(info.getSalesman().getName());
		msg.append("> ,�Ƿ������");
		
//		String msg1 = "�ͻ���ϵ�绰���� <" + info.getBookedDate().toString() +"> ¼��,����Ϊ <" + info.getName()+"> ,���۹���Ϊ <"+ info.getSalesman().getName() + "> ,�Ƿ������";
		int result = MsgBox.showConfirm2New(null, msg.toString());
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * ��֤�õ绰�����Ƿ��ܱ�¼��
	 * @param phone
	 * 			����֤�ĺ���
	 * @return ������ȷ�����м�¼��û����ͬ����ļ�¼�򷵻�True������ͬ��¼�����û�ѡ�񷵻�
	 * */
	public static boolean verifyPhone(String phone) throws BOSException {
		return verifyPhone(phone, null);
	}
	
	/**
	 * ��֤�õ绰�����Ƿ��ܱ�¼��
	 * @param phone
	 * 			����֤�ĺ���
	 * @return ������ȷ�����м�¼��û����ͬ����ļ�¼�򷵻�True������ͬ��¼�����û�ѡ�񷵻�
	 * */
	public static boolean verifyPhone2(String phone,MarketDisplaySetting setting) throws BOSException {
		return verifyPhone(phone, null,setting);
	}
	
	/**
	 * ��֤�õ绰�����Ƿ��ܱ�¼��
	 * @param phone
	 * 			����֤�ĺ���
	 * @param exceptCusId
	 * 			Ҫ����Ŀͻ����루�ò�����Ҫ��Կͻ��޸�ʱ,Ҫ����ǰ�޸ĵĿͻ���¼���⣩
	 * */
	public static boolean verifyPhone(String phone, String exceptCusId,MarketDisplaySetting setting) throws BOSException {
		if(phone == null  ||  phone.equals("") ||  !isValidInput(phone)){
			return false;
		}
		
		FDCCustomerCollection cusList = getSamePhoneRecord(phone, exceptCusId);
		
		
		if(cusList.isEmpty()){
			return true;
		}
		
		if(cusList != null && setting.getPhoneRepeat()==0){
			FDCCustomerInfo info = getFirstBookedCustomer(cusList);
			
			StringBuffer msg = new StringBuffer();
			msg.append("�ͻ���ϵ�绰���� <");
			msg.append(info.getCreateTime().toString());
			msg.append("> ¼��,����Ϊ <");
			msg.append(info.getName());
			msg.append("> ,���۹���Ϊ <");
			msg.append(info.getSalesman().getName());
			msg.append(">");
			MsgBox.showInfo(msg.toString());
			return false;
		}if(cusList != null && setting.getPhoneRepeat()==1)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cusList);
			
			StringBuffer msg = new StringBuffer();
			msg.append("�ͻ���ϵ�绰���� <");
			msg.append(info.getCreateTime().toString());
			msg.append("> ¼��,����Ϊ <");
			msg.append(info.getName());
			msg.append("> ,���۹���Ϊ <");
			msg.append(info.getSalesman().getName());
			msg.append("> ,�Ƿ������");
			int result = MsgBox.showConfirm2New(null, msg.toString());
			if(result == MsgBox.YES){
				return true;
			}
			return false;
		}if(cusList != null && setting.getPhoneRepeat()==2)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * ��֤�����Ƿ���Ч
	 * */
	private static boolean isValidInput(String phone) {
		if(phone == null  ||  phone.length() == 0){
			return false;
		}
		String regEx="[\\d]{1,}[\\d|;|��]{0,}";
		
//		String regEx="[\\d|;|��]{0,}";
        boolean result=Pattern.compile(regEx).matcher(phone).matches();
        if(!result){
        	MsgBox.showInfo("��ϵ�绰ֻ����������,����绰����֮����;�ָ�!");
        	return false;
        }
		return true;
	}
	
	/**
	 * ��þ�����ͬ�绰����Ŀͻ���¼
	 * @param exceptCusId 
	 * */
	private static FDCCustomerCollection getSamePhoneRecord(String phone, String exceptCusId) throws BOSException{
		FilterInfo cusFilter = getCusFilter(phone, exceptCusId);
		if(cusFilter == null){
			return null;
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(cusFilter);
		
		return FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
	}
	
	/**
	 * ��ò�ѯ������ͬ�绰�����FilterInfo
	 * @param exceptCusId 
	 * */
	private static FilterInfo getCusFilter(String phone, String exceptCusId){
		String[] phones = phone.split("[��|;]{1}");
		if(phones.length == 0){//�û�ֻ����;������Żᵼ�¸����
			MsgBox.showInfo("��ϵ�绰ֻ����������,����绰����֮����;�ָ�!");
			return null;
		}
		
		FilterInfo filter = null;
		for(int i=0; i<phones.length; i++){
			if(phones[i].trim().equals("")){
				continue;
			}
			if(filter == null){
				filter = createFilterPerPhone(phones[i]);
			}else{
				FilterInfo tmp = createFilterPerPhone(phones[i]);
				try {
					filter.mergeFilter(tmp, "or");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(exceptCusId != null){
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
			try {
				filter.mergeFilter(filter2, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return filter;
	}
	
	/**
	 * ����һ���绰���빹���ѯFilter,���е绰������1���ֶ�,�������֮����,�򣬷ָ���
	 * ע:���ֱ����like %phone% �ᵼ��ƥ�䲻��ȷ,����д��ں���12345,��123ȥƥ��Ͳ�׼ȷ��
	 * */
	private static FilterInfo createFilterPerPhone(String phone) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phone", phone, CompareType.EQUALS));
		
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+"��%", CompareType.LIKE));
		
		mergeOr(filter, new FilterItemInfo("phone", "%��"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+"��%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%��"+phone+"��%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%��"+phone+";%", CompareType.LIKE));
		
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone, CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", phone+";%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone+";%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone+"��%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%��"+phone, CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", phone+"��%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%��"+phone+"��%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%��"+phone+";%", CompareType.LIKE));
//		filter.setMaskString("#1 or #2 or #3 or #4 or #5 or #6 or #7 or #8 or #9");
		return filter;
	}
	
	private static void mergeOr(FilterInfo filter, FilterItemInfo info) {
		FilterInfo tmp = new FilterInfo();
		tmp.getFilterItems().add(info);
		try {
			filter.mergeFilter(tmp, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ӿͻ������л������¼����Ǹ��ͻ�
	 * */
	private static FDCCustomerInfo getFirstBookedCustomer(FDCCustomerCollection cusList) {
		FDCCustomerInfo firstBookedCustomer = null;
		for(int i=0; i<cusList.size(); i++){
			FDCCustomerInfo tmp = cusList.get(i);
			if(firstBookedCustomer == null){
				firstBookedCustomer = tmp;
				continue;
			}
			if(tmp.getCreateTime().before(firstBookedCustomer.getCreateTime())){
				firstBookedCustomer = tmp;
			}
		}
		return firstBookedCustomer;
	}
	
	/**
	 * ��֤�ͻ����������Ƿ񱻿ͻ�����
	 * @param key
	 * 			�ͻ��й����Ļ������ϵ���������
	 * @param selectedIds
	 * 			ѡ�еĻ������ϵ���������
	 * @return �Ƿ�����
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	private static boolean hasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return FDCCustomerFactory.getRemoteInstance().exists(filter);
	}
	
	/**
	 * ��֤�ͻ����������Ƿ񱻿ͻ�����,���������,��ʾ����ʾ,����ֹ��������
	 * @param key
	 * 			�ͻ��й����Ļ������ϵ���������
	 * @param selectedIds
	 * 			ѡ�еĻ������ϵ���������
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static void checkHasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByFDCCustomer(key, selectedIds)){
			MsgBox.showInfo(FDCCustomerConstant.RELATED_BY_CUSTOMER_DES);
			SysUtil.abort();
		}
	}
	
	/**
	 * ��֤�ͻ����������Ƿ񱻿ͻ�������¼����,���������,��ʾ����ʾ,����ֹ��������
	 * @param key
	 * 			�ͻ�������¼�й����Ļ������ϵ���������
	 * @param selectedIds
	 * 			ѡ�еĻ������ϵ���������
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static void checkHasRelatedByTrackRecord(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		if(TrackRecordFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo(FDCCustomerConstant.RELATED_BY_TRACK_RECORD_DES);
			SysUtil.abort();
		}
	}
	
	/**
	 * ����ְԱ������ְλ���п���1��ְԱ��Ӧ���ְλ���ʷ��ؼ���.
	 * @param personInfo
	 * 			ְԱʵ�����
	 * @return ����ְλ�ļ��ϣ���ѯ��̨����ʱ����null
	 * */
	public static List getCurrentPositions(PersonInfo personInfo) {
		List list = new ArrayList();
		FilterInfo pmFilter = new FilterInfo();
		pmFilter.getFilterItems().add(new FilterItemInfo("person.id", personInfo.getId()));
		
    	SelectorItemCollection sel = new SelectorItemCollection();
//    	sel.add(new SelectorItemInfo("position.*"));
//    	sel.add(new SelectorItemInfo("position"));
    	sel.add(new SelectorItemInfo("position.id"));
    	sel.add(new SelectorItemInfo("position.number"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(pmFilter);
		
		PositionMemberCollection pmList = null;
		try {
			pmList = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
		} catch (BOSException e) {
			log.warn("ȡְԱְλ����"+e.getMessage());
			log.debug("", e);
			return null;
		}
		
		for(int i=0; i<pmList.size(); i++){
			PositionMemberInfo memberInfo = pmList.get(i);
			PositionInfo positionInfo = memberInfo.getPosition();
			if(positionInfo != null)
				list.add(positionInfo);
		}
		return list;
	}
	
	/**
	 * ����ְλ��id���������Щְλ�µ�Person��ID����
	 * @param belongedPositionIds
	 * 			ְλ��number����
	 * @return ��ְλ�µ�����ְԱ��ID����
	 * */
	public static Set getPersonIds(Set belongedPositionIds) {
		Set set = new HashSet();
		FilterInfo pmFilter = new FilterInfo();
		pmFilter.getFilterItems().add(new FilterItemInfo("position.id", belongedPositionIds, CompareType.INCLUDE));
//    	SelectorItemCollection sel = new SelectorItemCollection();
//    	sel.add(new SelectorItemInfo("person.id"));
    	EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().addObjectCollection(sel);
		view.setFilter(pmFilter);
		
		PositionMemberCollection pmList = null;
		try {
			pmList = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
		} catch (BOSException e) {
			log.warn(e.getMessage());
			log.debug("", e);
		}
		for(int i=0; i<pmList.size(); i++){
			PositionMemberInfo info = pmList.get(i);
			set.add(info.getPerson().getId().toString());
		}
		return set;
	}
	
	/**
	 * ��õ�ǰ�û�����������ְԱ��ID����,������������ְԱ�͵�ǰ�û�����ְԱ����ְԱ�û�����Null
	 * @throws BOSException 
	 * */
	public static Set getChildPersonIdsOfCurrentUser(){
		Set childPersonIds = new HashSet();
		
		//��õ�ǰ�û�
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo personInfo = currentUserInfo.getPerson();
		if(personInfo == null){//��ְԱ�û�
			return null;
		}
		
		childPersonIds.add(personInfo.getId().toString());//��ǰ�û�����ְԱ
		
		//��õ�ǰ�û���ְλ
		List currentPositions = FDCCustomerHelper.getCurrentPositions(personInfo);
		if(currentPositions == null  ||  currentPositions.isEmpty()){//��ְλְԱ
			return childPersonIds;
		}
		
		//�����Щְλ����������ְλ��ż��ϣ�����������ְλ
		Set childPositionIds = null;
		try {
			childPositionIds = getChildPositionIds(currentPositions);
		} catch (BOSException e) {
			log.warn("�������ְλ����"+e.getMessage());
			log.debug("", e);
		}
		
		if(childPositionIds == null  ||  childPositionIds.isEmpty()){
			return childPersonIds;
		}
		
		//���������Щ����ְλ�µ�ְԱ����
		Set personIds = FDCCustomerHelper.getPersonIds(childPositionIds);
		
		if(personIds==null  ||  personIds.isEmpty()){
			return childPersonIds;
		}
		childPersonIds.addAll(personIds);
		return childPersonIds;
	}
	
	private static Set getChildPositionIds(List currentPositions) throws BOSException {
		Set childPositionIds = new HashSet();
		
		//�����ְλΪ��ǰ�û�ְλ�ļ�¼������
		Set longNums = new HashSet();
		for(int i=0; i<currentPositions.size(); i++){
			PositionInfo positionInfo = (PositionInfo) currentPositions.get(i);
			String currentPositionId = positionInfo.getId().toString();
			
			FilterInfo phFilter = new FilterInfo();
			phFilter.getFilterItems().add(new FilterItemInfo("child.id", currentPositionId, CompareType.EQUALS));
	    	SelectorItemCollection sel = new SelectorItemCollection();
	    	sel.add(new SelectorItemInfo("longNumber"));
	    	EntityViewInfo view = new EntityViewInfo();
			view.getSelector().addObjectCollection(sel);
			view.setFilter(phFilter);
			PositionHierarchyCollection hierarchyCollection = PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection(view);
			if(hierarchyCollection.isEmpty()){
				longNums.add(positionInfo.getNumber());
			}else if(hierarchyCollection.size() == 1){
				longNums.add(hierarchyCollection.get(0).getLongNumber());
			}else{
				log.warn("ְλ���¼��ж��߼��������顣");
				return null;
			}
		}
		
		//������Щ������ȥ��ƥ��,������ΪlongNumber*��ƥ��ļ�¼����Ϊ���ϵ���ְλ
		FilterInfo phFilter = new FilterInfo();
		for(Iterator itor = longNums.iterator(); itor.hasNext(); ){
			String longNum = (String) itor.next();
			FilterItemInfo tmpItem = new FilterItemInfo("longNumber", longNum+"!%", CompareType.LIKE);
			FilterInfo tmpFilter = new FilterInfo();
			tmpFilter.getFilterItems().add(tmpItem);
			
			phFilter.mergeFilter(tmpFilter, "or");
		}
		SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add(new SelectorItemInfo("child.id"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(phFilter);
		PositionHierarchyCollection hierarchyCollection = PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection(view);
		
		for(int i=0; i<hierarchyCollection.size(); i++){
			PositionHierarchyInfo hierarchyInfo = hierarchyCollection.get(i);
			childPositionIds.add(hierarchyInfo.getChild().getId().toString());
		}
		return childPositionIds;
	}
	

/***
 * ����Ӫ�������ж��ظ�����������������Ƿ����ظ���
 * @param rMap
 * @param ui
 * @return
 * �������Ӫ�������е����ƺ��ƶ��绰�ظ���ǿ�Ʋ����棬����������ظ����ƶ��绰�ظ����жϣ��������ж������Ƿ��ظ������ж��ƶ��绰�Ƿ��ظ���
 * ����ж����ƺ��ƶ��绰�Ƿ��ظ���
 * 
 */
public static boolean verifyPhoneAndName(Map rMap,CoreUI ui){
	    MarketDisplaySetting setting = new MarketDisplaySetting();
		boolean isAbort = false;
//		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
//			//������ǿ�Ʋ����棬�ϲ���ʾ
//			FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
////			abort();//ǿ�Ʋ�����
//			return true;
//		}
		//by tim_gao 2010-11-17 �ͻ�¼�룬�����������ظ��ж�
		//���1:���ж������ظ��Ƿ�ͬʱ���ڣ������ڶ���������3����ʾ
		//���2:�������ظ�ֻ��һ�ִ��ڣ����߶����������߸��Ե�3����ʾ
		if(rMap.containsKey("dupNameMsg")&&rMap.containsKey("dupPhoneMsg")){//���1:�����ظ����绰�ظ�������
			if(setting.getNameAndPhoneRepeat()==2){
				//����ʾ��ֱ�ӱ���
			}else if(setting.getNameAndPhoneRepeat()==0){
				FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg")+(String)rMap.get("dupPhoneMsg"));
				return true;//ǿ�Ʋ�����
			}else if(setting.getNameAndPhoneRepeat()==1){
				//��ʾ
				if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameMsg")+(String)rMap.get("dupPhoneMsg"))){
					return true;
				}
			}else {
				return true;
			}
			
		}else{//ֻ��һ���ظ�����
			if(rMap.containsKey("dupNameMsg")){//���2:�������ظ�����
				if(setting.getNameRepeat()==2)
					{
						//����ʾ��ֱ�ӱ���
					}else if(setting.getNameRepeat()==0){
						FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg"));
						return true;//ǿ�Ʋ�����
					}else if(setting.getNameRepeat()==1){
						//������ʾ���ͻ�ѡ�񱣴�;
						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameMsg"))){
							return true;
						}
					}else{
						return true;
					}
				}
			if(rMap.containsKey("dupPhoneMsg")){//���绰�ظ�����
				if(setting.getPhoneRepeat()==2)
				{
					//����ʾ��ֱ�ӱ���
				}else if(setting.getPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupPhoneMsg"));
					return true;//ǿ�Ʋ�����
				}else if(setting.getPhoneRepeat()==1){
					//������ʾ���ͻ�ѡ�񱣴�;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupPhoneMsg"))){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		
		
		
		
		
		
		
		
		
//		
//		if(setting.getNameAndPhoneRepeat() == 0){
//			if(rMap.containsKey("dupNameAndPhoneMsg")){
//				FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameAndPhoneMsg"));
//				return true;//ǿ�Ʋ�����
//			}
//		}else{
//			if(rMap.containsKey("dupNameMsg")){
//				if(setting.getNameRepeat()==2)
//					{
//						//����ʾ��ֱ�ӱ���
//					}else if(setting.getNameRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg"));
//						return true;//ǿ�Ʋ�����
//					}else if(setting.getNameRepeat()==1){
//						//������ʾ���ͻ�ѡ�񱣴�;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//				
//				if(rMap.containsKey("dupPhoneMsg")){
//					if(setting.getPhoneRepeat()==2)
//					{
//						//����ʾ��ֱ�ӱ���
//					}else if(setting.getPhoneRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupPhoneMsg"));
//						return true;//ǿ�Ʋ�����
//					}else if(setting.getPhoneRepeat()==1){
//						//������ʾ���ͻ�ѡ�񱣴�;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupPhoneMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//				if(rMap.containsKey("dupNameAndPhoneMsg")){
//					if(setting.getNameAndPhoneRepeat() == 2){
//						
//					}else if(setting.getNameAndPhoneRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameAndPhoneMsg"));
//						return true;//ǿ�Ʋ�����
//					}else if(setting.getNameAndPhoneRepeat()==1){
//						//������ʾ���ͻ�ѡ�񱣴�;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameAndPhoneMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//		}
		
		
			return isAbort;
	}
	
}
