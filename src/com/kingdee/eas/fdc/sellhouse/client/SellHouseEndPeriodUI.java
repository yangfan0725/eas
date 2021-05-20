/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SellHouseEndPeriodUI extends AbstractSellHouseEndPeriodUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellHouseEndPeriodUI.class);
    private PeriodInfo companyCurrentPeriod = null;
	private CompanyOrgUnitInfo companyInfo = null;
	private PeriodInfo prevPeriodInfo = null;
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    public SellHouseEndPeriodUI() throws Exception
    {
        super();
    }
    
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	companyInfo = SysContext.getSysContext().getCurrentFIUnit();
    	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		if (!companyInfo.isIsBizUnit() && !saleOrg.isIsBizUnit()) {
			MsgBox.showError(this, "��ǰ��˾Ϊ���壬������ʹ�øù��ܡ�");
			SysUtil.abort();
		}
		boolean flag = SystemStatusCtrolUtils.isStart(null,SystemEnum.FDC_SHE, companyInfo);
		if(!flag){
			MsgBox.showInfo("ϵͳδ���ã����ܽ��н��˷����ˣ�");
			SysUtil.abort();
		}
    	super.onLoad();
    	labPic.setIcon(EASResource.getIcon("imgGuide_pic260"));
    	radEndInit.setSelected(true);
    	companyCurrentPeriod = SystemStatusCtrolUtils
		.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    	PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	StringBuffer sb = new StringBuffer();
    	sb.append("��ǰ�ڼ�:"+companyCurrentPeriod.getPeriodYear()+"���"+companyCurrentPeriod.getPeriodNumber()+"��"+ "\n");
    	if(nextPeriod!=null){
    		sb.append("��һ�ڼ�:"+ nextPeriod.getPeriodYear()+"���"+ nextPeriod.getPeriodNumber()+"��"+ "\n\n");
    	}else{
    		FDCMsgBox.showWarning(this,"��һ�ڼ䲻��Ϊ�գ�");
    		SysUtil.abort();
    	}
    	kDTextArea2.setText(""+sb);
    }

    protected void radEndInit_stateChanged(ChangeEvent e) throws Exception {
    	super.radEndInit_stateChanged(e);
    	if(radEndInit.isSelected())
    	{
    		kDTextArea1.setText("���½�֮ǰ��Ӧ��鵱ǰʵ�������֯�������Ѿ�������ʼ���������ڼ��ڵ�" +
    				"ǰ�ڼ��֮ǰ��������Ŀ�ڲ�����֯�ĵ�ǰ�ڼ䶼�ѽ����½ᡣ");
    		if(companyCurrentPeriod!=null){
    			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("��ǰ�ڼ�:"+companyCurrentPeriod.getPeriodYear()+"���"+companyCurrentPeriod.getPeriodNumber()+"��"+ "\n");
    	    	sb.append("��һ�ڼ�:"+ nextPeriod.getPeriodYear()+"���"+ nextPeriod.getPeriodNumber()+"��"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
    	}
    }
    
    protected void radUnInit_stateChanged(ChangeEvent e) throws Exception {
    	super.radUnInit_stateChanged(e);
    	if(radUnInit.isSelected())
    	{
    		kDTextArea1.setText("�ڷ��½�֮ǰ���������˽��������ã�����뵱ǰʵ�������" +
    				"֯�����˵�ǰ�ڼ�Ϊǰһ�ڼ�ſ��Խ��з��½ᡣ");
    		if(companyCurrentPeriod!=null){
    			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("��ǰ�ڼ�:"+companyCurrentPeriod.getPeriodYear()+"���"+companyCurrentPeriod.getPeriodNumber()+"��"+ "\n");
    	    	sb.append("��һ�ڼ�:"+ nextPeriod.getPeriodYear()+"���"+ nextPeriod.getPeriodNumber()+"��"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
    	}
    }
    
    protected void btnConfim_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfim_actionPerformed(e);
        if(radEndInit.isSelected())
        {
        	//У��Ȩ��:ĳ����֯ĳ���û�ĳ�����ܵ�Ȩ���Ƿ���Ȩ��
    		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
    				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client.SellHouseEndPeriodUI"),
    				new MetaDataPK("ActionEndSale") )){
//    			throw new  Permission//AccountBooksException(AccountBooksException.NOSWITCH,new Object[]{scheme.getName()});
    			MsgBox.showError(this,"û���½�Ȩ��");
    			abort();
    		}
        	//�����ǰ��֯�ѽ�����ʼ������Ŀ�ĵ�ǰ�ڼ�С�ڵ��ڹ�˾�ĵ�ǰ�ڼ�Ļ�����ô˵����Ŀ
        	//��û���½ᣬ��ô�����������ĩ����
        	//��ǰ�û��ڵ�¼��֯���ܿ����ĳ�ʼ����������Ŀ�ļ���
        	String str = CommerceHelper.getPermitProjectIdSql(userInfo);
        	EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("id",str,CompareType.INNER));
    		filter.getFilterItems().add(new FilterItemInfo("isEndInit",Boolean.TRUE));
    		view.setFilter(filter);
    		view.getSelector().add("*");
    		view.getSelector().add("saleNowPeriod.*");
    		SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
        	String projectName = new String();
        	//��˾�ĵ�ǰ�ڼ�
        	companyCurrentPeriod = SystemStatusCtrolUtils
    		.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
        	for(int i=0;i<sellProjectColl.size();i++)
        	{
        		SellProjectInfo sellProInfo = (SellProjectInfo)sellProjectColl.get(i);
        		//��ǰ�ڼ�
        		PeriodInfo projectPeriod = sellProInfo.getSaleNowPeriod();
        		//�����ڼ�
        		PeriodInfo strartPeriod = sellProInfo.getSalePeriod();
        		//��Ŀ�ĵ�ǰ�ڼ�С�ڵ��ڹ�˾�ĵ�ǰ�ڼ䲢����Ŀ�������ڼ�Ҳ��С�ڵ��ڹ�˾�ĵ�ǰ�ڼ��
        		//��2����Ŀ ��Ŀ1 �����ڼ�200908 ��ǰ�ڼ�200909 ��Ŀ2 �����ڼ�200909 ��ǰ�ڼ�200909 ��˾�ĵ�ǰ�ڼ�Ϊ200908����8�·���ĩ����
        		//��ô��Ŀ2�ڽ���ʱ�Ͳ���Ҫ��顣ֻ�������������
        		if(projectPeriod.getNumber()<=companyCurrentPeriod.getNumber() && strartPeriod.getNumber()<= companyCurrentPeriod.getNumber())
        		{
        			projectName+="��"+sellProInfo.getName();
        		}
        	}
        	if(projectName.length()>0)
        	{
        		MsgBox.showInfo("��ǰʵ�������֯�´����ѽ�����ʼ�������ڼ��ڵ�ǰ�ڼ��֮ǰ��" +
        				"����ǰ�ڼ�δ�����½��������Ŀ����Ŀ����Ϊ"+projectName+"�����Ƚ���������Ŀ���½ᡣ");
        		this.abort();
        	}else
        	{
        		SellProjectFactory.getRemoteInstance().nextSystem(companyInfo.getId().toString(), userInfo);
        		PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    			if(companyCurrentPeriod!=null){
        	    	StringBuffer sb = new StringBuffer();
        	    	sb.append("��ǰ�ڼ�:"+companyCurrentPeriod.getPeriodYear()+"���"+companyCurrentPeriod.getPeriodNumber()+"��"+ "\n");
        	    	sb.append("��һ�ڼ�:"+ nextPeriod.getPeriodYear()+"���"+ nextPeriod.getPeriodNumber()+"��"+ "\n\n");
        	    	kDTextArea2.setText(""+sb);
    			}
        	}
        }else if(radUnInit.isSelected())
        {
        	//У��Ȩ��:ĳ����֯ĳ���û�ĳ�����ܵ�Ȩ���Ƿ���Ȩ��
    		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
    				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client.SellHouseEndPeriodUI"),
    				new MetaDataPK("ActionUnEndSale") )){
//    			throw new  Permission//AccountBooksException(AccountBooksException.NOSWITCH,new Object[]{scheme.getName()});
    			MsgBox.showError(this,"û�з��½�Ȩ��");
    			abort();
    		}
        	//�����ϵͳ״̬���Ѿ����������ã���ô���˵ĵ�ǰ�ڼ������ڵ��ڹ�˾�ĵ�ǰ�ڼ��
        	//���Է�����
        	String companyId = companyInfo.getId().toString();
			SystemStatusCtrolUtils sscUtils = new SystemStatusCtrolUtils();			
			//��ǰ�ڼ��Ѿ����������ã�����ȡ��������[����]��
			if(sscUtils.isRelatedAccount4Client(companyId,SystemEnum.FDC_SHE)){			
				PeriodInfo  glCurPeriod=SystemStatusCtrolUtils.getCurrentPeriod(null,SystemEnum.GENERALLEDGER,companyInfo);
				if(glCurPeriod!=null && companyCurrentPeriod!=null &&  companyCurrentPeriod.getPeriodYear()==glCurPeriod.getPeriodYear()&&
						companyCurrentPeriod.getPeriodNumber()==glCurPeriod.getPeriodNumber()){
					MsgBox.showInfo(this,"���ز���ǰ�ڼ�������һ�������ܷ����ˣ�");
					SysUtil.abort();
				}
				
			   if(sscUtils.isEqualsCurPeriodAndRelPeriod4Client(companyId,SystemEnum.CASHMANAGEMENT)){
				   MsgBox.showInfo("��ȡ�����������ã�");
				   SysUtil.abort();
			   }
			}
			PeriodInfo currentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			
			PeriodInfo startPeriod = SystemStatusCtrolUtils
			.getStartPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			if(currentPeriod.getNumber()==startPeriod.getNumber())
			{
				MsgBox.showInfo("��ǰ�ڼ�Ϊϵͳ�����ڼ䣬�޷����з��½�.");
				SysUtil.abort();
			}
			SellProjectFactory.getRemoteInstance().preSystem(companyId);
			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
			if(companyCurrentPeriod!=null){
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("��ǰ�ڼ�:"+companyCurrentPeriod.getPeriodYear()+"���"+companyCurrentPeriod.getPeriodNumber()+"��"+ "\n");
    	    	sb.append("��һ�ڼ�:"+ nextPeriod.getPeriodYear()+"���"+ nextPeriod.getPeriodNumber()+"��"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
        }
    }

    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel_actionPerformed(e);
        //setConfirm(false);
		//disposeUIWindow();
        this.destroyWindow();
    }

}