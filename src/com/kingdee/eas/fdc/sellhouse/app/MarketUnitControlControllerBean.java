package com.kingdee.eas.fdc.sellhouse.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan;
import com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManFactory;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManInfo;
import com.kingdee.eas.fdc.basecrm.MarketUnitSellProFactory;
import com.kingdee.eas.fdc.basecrm.MarketUnitSellProInfo;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;

public class MarketUnitControlControllerBean extends AbstractMarketUnitControlControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.MarketUnitControlControllerBean");
    
    
    
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	IObjectPK pk = super._addnew(ctx, model);
    	
    	//ͬ������Ӫ����ĿȨ�޺�Ӫ������Ȩ�ޱ�
    	MarketUnitControlInfo muCtrolUser = (MarketUnitControlInfo)this._getValue(ctx, 
    			"select orgUnit.id,orgUnit.longNumber,controler.id  where id = '"+pk.toString()+"'");
    	updateMuPermission(ctx,muCtrolUser.getOrgUnit(),muCtrolUser.getControler(),true);
    	return pk;
    }
    
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	MarketUnitControlInfo muCtrolUser = (MarketUnitControlInfo)this._getValue(ctx, "select *,orgUnit.longNumber " +
				" where id = '"+pk.toString()+"'");
    	super._delete(ctx, pk);
    	
    	updateMuPermission(ctx,muCtrolUser.getOrgUnit(),muCtrolUser.getControler(),false);
    }
    
    /**Ӫ���ܿ���Ա�Ķ�ʱ��ͬ������Ӫ����ĿȨ�޺�Ӫ������Ȩ�ޱ�*/
	protected void _updateMuPermission(Context ctx,	FullOrgUnitInfo orgUnitInfo, UserInfo ctrolUserInfo, boolean isAdd)	throws BOSException, EASBizException {

    	//����Ӫ���ܿ���Ա��˵��ֻ����������ɾ��ʱ�ᴥ��Ӫ��Ȩ�޵ı仯  
    	IMarketUnitSellPro muSpFactory = MarketUnitSellProFactory.getLocalInstance(ctx);
    	IMarketUnitSaleMan muSmFactory = MarketUnitSaleManFactory.getLocalInstance(ctx);
    	
		muSpFactory.delete("where orgUnit.id = '"+orgUnitInfo.getId()+"' and saleMan.id = '"+ctrolUserInfo.getId()+"'  ");
		muSmFactory.delete("where orgUnit.id = '"+orgUnitInfo.getId()+"' and dutyPerson.id = '"+ ctrolUserInfo.getId() +"' ");
    	
    	if(!isAdd)  return;	//����
		//�ܿ���Ա����������֯�����¼���֯�е���Ŀ��Ȩ�ޣ���Ŀ�����νṹ��,�����ǰ�������Ŀ�ģ�
    	SelectorItemCollection selector = new SelectorItemCollection();
    	selector.add(new SelectorItemInfo("id"));
    	selector.add(new SelectorItemInfo("number"));
    	selector.add(new SelectorItemInfo("longNumber"));
    	SaleOrgUnitInfo saleOrgUnit = SaleOrgUnitFactory.getLocalInstance(ctx).getSaleOrgUnitInfo(new ObjectUuidPK(orgUnitInfo.getId()), selector); 
    	String sonOrgUnitIds = "select fid from t_org_sale where fid = '"+saleOrgUnit.getId()+"' or flongNumber like '"+saleOrgUnit.getLongNumber()+"!%' ";
		SellProjectCollection spColl = SellProjectFactory.getLocalInstance(ctx)
					.getSellProjectCollection("select id,parent.id where orgUnit.id in ("+sonOrgUnitIds+")  and isForShe = 1 ");
		for (int i = 0; i < spColl.size(); i++) {
    		MarketUnitSellProInfo addMuSpInfo = new MarketUnitSellProInfo();
    		addMuSpInfo.setOrgUnit(orgUnitInfo);
    		addMuSpInfo.setSellProject(spColl.get(i));
    		addMuSpInfo.setParentSpro(spColl.get(i).getParent());
    		addMuSpInfo.setSaleMan(ctrolUserInfo);	
    		muSpFactory.addnew(addMuSpInfo);
		}
		
		//�ܿ���Ա�Լ�
		MarketingUnitSellProjectCollection muSpColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx)
						.getMarketingUnitSellProjectCollection("select sellProject.id,head.id where head.orgUnit.id = '"+orgUnitInfo.getId()+"' and head.isEnabled = 1 " );
		if(muSpColl.size()>0) {
			for (int i = 0; i < muSpColl.size(); i++) {
				MarketUnitSaleManInfo controlUserInfo = createNewMuSaleManInfo(orgUnitInfo,muSpColl.get(i).getHead(),muSpColl.get(i).getSellProject(),ctrolUserInfo,ctrolUserInfo,null,null,false);		
				muSmFactory.addnew(controlUserInfo);
			}
		}else{
			MarketUnitSaleManInfo controlUserInfo = createNewMuSaleManInfo(orgUnitInfo,null,null,ctrolUserInfo,ctrolUserInfo,null,null,false);		
			muSmFactory.addnew(controlUserInfo);
		}
		
		//��ǰ��֯������Ӫ����Ԫ��ĳ�Ա   ϸ�ֵ�Ӫ����Ԫ�µ���Ŀ����������Ŀ
		Map muMap = new HashMap();
		Map spMap = new HashMap();	//��Ŀid ����Ӧ����Ŀ����
		MarketingUnitMemberCollection saleManColl = MarketingUnitMemberFactory.getLocalInstance(ctx)  //��Ϊ�ܿ���Ա��Ȩ�ޣ�Ӧ�ò����ĳ�Ա���ϸں��¸����ڵ�
						.getMarketingUnitMemberCollection("select * where head.orgUnit.id = '"+orgUnitInfo.getId()+"' and head.isEnabled = 1 ");
		for (int i = 0; i < saleManColl.size(); i++) {
			MarketingUnitMemberInfo muMemeberInfo = saleManColl.get(i);
			
			MarketingUnitInfo muInfo = (MarketingUnitInfo)muMap.get(muMemeberInfo.getHead().getId().toString()); 
			if(muInfo==null)	{
				muInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(
						"select sellProject.sellProject.* where id = '"+muMemeberInfo.getHead().getId()+"'");
				muMap.put(muMemeberInfo.getHead().getId().toString(), muInfo);
			}    				
			
			for (int j = 0; j < muInfo.getSellProject().size(); j++) {
				SellProjectInfo currSpInfo = muInfo.getSellProject().get(j).getSellProject();
				
        		MarketUnitSaleManInfo addMuManInfo = createNewMuSaleManInfo(orgUnitInfo,muMemeberInfo.getHead(),currSpInfo,
        				ctrolUserInfo,muMemeberInfo.getMember(),muMemeberInfo.getAccessionDate(),muMemeberInfo.getDimissionDate(),muMemeberInfo.isIsDuty());
        		muSmFactory.addnew(addMuManInfo);
        		
        		if(currSpInfo.getLevel()==1) {
    				SellProjectCollection sonSpColl = (SellProjectCollection)spMap.get(currSpInfo.getId().toString());
    				if(sonSpColl==null){
    					sonSpColl = SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection(
    								"select id where longNumber like '"+currSpInfo.getLongNumber()+"!%' ");
    					spMap.put(currSpInfo.getId().toString(), sonSpColl);
    				}
    				for (int k = 0; k < sonSpColl.size(); k++) {
    					addMuManInfo.setId(null);
    					addMuManInfo.setSellProject(sonSpColl.get(k));
    					muSmFactory.addnew(addMuManInfo);
					}
        		}            		
			}   		
		}
    
    
	}
    
    private MarketUnitSaleManInfo createNewMuSaleManInfo(FullOrgUnitInfo orgUnit,MarketingUnitInfo muInfo,SellProjectInfo currSpInfo
    		,UserInfo dutyUserInfo,UserInfo memberUserIfo,Date accessionDate,Date dimissionDate,boolean isDuty) {
		MarketUnitSaleManInfo addMuManInfo = new MarketUnitSaleManInfo();
		addMuManInfo.setOrgUnit(orgUnit);
		addMuManInfo.setMarketUnit(muInfo);
		addMuManInfo.setSellProject(currSpInfo);
		addMuManInfo.setDutyPerson(dutyUserInfo);
		addMuManInfo.setMember(memberUserIfo);
		Date accDate = accessionDate;
		Date disDate = dimissionDate;        		
		try {
			if(accDate==null)
				accDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("1999-01-01");
			if(disDate==null)
				disDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("2999-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		addMuManInfo.setAccessionDate(accDate);        		
		addMuManInfo.setDimissionDate(disDate);
		addMuManInfo.setIsDuty(isDuty);
    	return addMuManInfo;
    }
	
	
    
    
    
}