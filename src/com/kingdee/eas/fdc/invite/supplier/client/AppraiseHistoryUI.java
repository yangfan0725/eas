/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			AppraiseHistoryUI.java			
 * ��Ȩ��		�����������������޹�˾��Ȩ����<P>		 	
 * ������		��Ӧ��.������ʷ<P>
 *
 * @author		�ﱦƽ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class AppraiseHistoryUI extends AbstractAppraiseHistoryUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseHistoryUI.class);
	
    /*
     * ���幩Ӧ��number
     */
    private String supplierNumber = "";
    
    /*
     * ���幩Ӧ��ID
     */
    private String supplierID = "";
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	/*
    	 * ��ʼ����ʽ
    	 */
    	initStyle();
    	
    	/*
    	 * ҳ�����ʱ��ѯ��������ı���ͱ��
    	 */
    	setValueToTable();
    }
    
    /*
     * ��ȡ��Դ�ļ�
     */
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    
    /**
     * 
    * @description		��ʼ����ʽ
    * @author			�ﱦƽ	
    * @createDate		2010-12-1
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
    public void initStyle(){
    	
    	/*
    	 * ���ò鿴��ť��ͼ����ʾ����
    	 */
    	this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
    	
    	/*
    	 * ���÷���˵��Ĺ���ѡ��Ϊ����
    	 */
    	this.MenuItemKnowStore.setVisible(true);
    	this.MenuItemKnowStore.setEnabled(true);
    	this.MenuItemAnwser.setVisible(true);
    	this.MenuItemAnwser.setEnabled(true);
    	this.MenuItemRemoteAssist.setVisible(true);
    	this.MenuItemRemoteAssist.setEnabled(true);
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {

    	/*
    	 * �ж��Ƿ�ѡ����
    	 */
    	 checkSelected();
    	 
    	 /*
  		 * ��ȡѡ���¼������
  		 */
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		
		/*
		 * ��ȡѡ���¼��ID
		 */
		Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
		
		/*
		 * ��ȡѡ���¼������
		 */
		Object cType = tblMain.getRow(rowIndex).getCell("cType").getValue();
		
		UIContext uiContext = new UIContext(this);
		/*
		 * �ж�ѡ���¼��ID�Ƿ�Ϊ��
		 */
 		if(null != infoId){
        	/*
        	 * ��ѡ���¼��ID����
        	 */
    		uiContext.put("ID", infoId);
    	}
 		
 		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
 		IUIWindow uiWindow = null;
 		
 		/*
 		 * ����ѡ��Ĳ�ͬ���ͣ���ת����ͬ�Ĳ鿴ҳ��
 		 */
 		uiWindow = getIUIWindow(uiContext,uiFactory,uiWindow,cType);
  		uiWindow.show();
    }
    
    /**
     * 
    * @description		����ѡ��Ĳ�ͬ������ת����ͬ�Ĳ鿴ҳ��
    * @author			�ﱦƽ	
    * @createDate		2010-11-15
    * @param			UIContext,IUIFactory,IUIWindow,Object
    * @return			IUIWindow		
    *	
    * @version			EAS7.0
    * @see
     */
    protected IUIWindow getIUIWindow(UIContext uiContext,IUIFactory uiFactory,IUIWindow uiWindow,Object cType) throws Exception{
    	
    	/*
    	 * ��ת���ʸ񿼲����
    	 */
 		if(getResource("review").equals(cType)){
 			uiWindow = uiFactory.create(SupplierQuaReviewEditUI.class.getName(), uiContext, null,"FINDVIEW");
 	 	}
 		/*
 		 * ��ת����Լ��������
 		 */
 		else if(getResource("evaluate").equals(cType)){
 	 		uiWindow = uiFactory.create(SupplierPerformEvalulationEditUI.class.getName(), uiContext, null,"FINDVIEW");	
 	 	}
 		/*
 		 * ��ת�������������
 		 */
 		else{
 	 		uiWindow = uiFactory.create(SupplierEvaluateEditUI.class.getName(), uiContext, null,"FINDVIEW");
 	 	}
 		
 		return uiWindow;
    }
    
    /**
     * 					����˫��ѡ��Ĳ�ͬ���ͣ���ת����ͬ�Ĳ鿴ҳ��
    * @description		
    * @author			�ﱦƽ	
    * @createDate		2010-11-15
    *	
    * @version			EAS7.0
    * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractAppraiseHistoryUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
     */
	final protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
		/*
		 * δѡ�б���ʱ
		 */
		if (e.getType() == 0 || e.getType() == 3) {
			return;
		} else {
			this.tblMain.getStyleAttributes().setLocked(true);
    		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
			
    		/*
     		 * ��ȡѡ���¼������
     		 */
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			
			if (activeRowIndex != -1 && e.getClickCount() == 2) {
				if (this.tblMain.getRow(activeRowIndex).getCell("id") != null) {
					
					/*
					 * �ж��Ƿ�ѡ��
					 */
					checkSelected();
					
					/*
					 * ��ȡѡ���¼��ID��״̬
					 */
					int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
					Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
					Object cType = tblMain.getRow(rowIndex).getCell("cType").getValue();

					UIContext uiContext = new UIContext(this);
					
					/*
					 * �ж�ѡ���¼��ID�Ƿ�Ϊ��
					 */
					if (null != infoId) {
						/*
						 * ��ѡ���¼��ID����
						 */
						uiContext.put("ID", infoId);
					}

					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					IUIWindow uiWindow = null;
					
					/*
					 * ����ѡ��Ĳ�ͬ���ͣ���ת����ͬ�Ĳ鿴ҳ��
					 */
					uiWindow = getIUIWindow(uiContext, uiFactory, uiWindow,cType);
					uiWindow.show();
				}
			}
		}
	}
    
	/**
	 * 
	* @description		�ж��Ƿ�ѡ����
	* @author			�ﱦƽ	
	* @createDate		2010-12-1
	* @param	
	* @return			void		
	*	
	* @version			EAS7.0
	* @see
	 */
	public void checkSelected()
	{
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0){
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	* @description		������ı����ٲ�ѯ������ѯ�����������
	* @author			�ﱦƽ	
	* @createDate		2010-11-15
	* @param	
	* @return			void		
	*	
	* @version			EAS7.0
	 * @throws EASBizException 
	* @see
	 */
    private void setValueToTable() throws EASBizException{
    	
    	if(null != getUIContext().get("SupplierInfo")){
    		
    		SupplierStockInfo infoTMP = (SupplierStockInfo) getUIContext().get("SupplierInfo");
    		
    		/*
    		 * �����ݵĹ�Ӧ�����Ʒ��õ��ı�����
    		 */
    		this.txtSupplierName.setText(infoTMP.getName());
    		
        	/*
        	 * ��ȡѡ���¼��Number��ID
        	 */
    		supplierNumber = infoTMP.getNumber().toString();
    		supplierID = infoTMP.getId().toString();
    	}
    	
    	/*
    	 * �����ı��������Ϊֻ��
    	 */
    	this.txtSupplierName.setEnabled(false);
    	
		/*
		 * �������
		 */
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
    	try {
    		/*
    		 * �ʸ񿼲���������
    		 */
    		IRow row = null;
    		FDCSplQualificationAuditBillCollection stc = getSelectedCollection();
			Iterator it = (Iterator) stc.iterator();

	    	/*
	    	 * ��������ѯ�Ľ�����õ������
	    	 */
			while(it.hasNext()){
				FDCSplQualificationAuditBillInfo info = (FDCSplQualificationAuditBillInfo)it.next();
				
				/*
				 * �����¼�ĸ���
				 */
				int billNum = 0;
				if(info !=null && info.getAuditResult() != null){
					billNum = info.getAuditResult().size();
				}
				
				/*
				 * �����ڷ�¼����Ӧ�ķ�¼��Ϣ������������������
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * ������
						 */
						row = this.tblMain.addRow();
						
						/*
						 * ���ݵ�Ԫ�������,���ν�ֵ���õ���Ԫ����,���ų�ΪNULL������
						 */
						
						if(info !=null && info.getSupplier()!= null && info.getSupplier().getId() != null){
							row.getCell("SupplierID").setValue(info.getSupplier().getId());
						}else{
							row.getCell("SupplierID").setValue(null);
						}
						
						if(info !=null && info.getId().toString() != null){
							row.getCell("id").setValue(info.getId().toString());
						}else{
							row.getCell("id").setValue(null);
						}

						row.getCell("cType").setValue(getResource("review"));
						
						if(info !=null && info.getBusinessDate() != null){
							row.getCell("businessDate").setValue(info.getBusinessDate());
						}else{
							row.getCell("businessDate").setValue(null);
						}
						
						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getScore() != null){
							row.getCell("FScore").setValue(info.getAuditResult().get(k).getScore());
						}else{
							row.getCell("FScore").setValue(null);
						}
						
						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getSupplierType() != null && info.getAuditResult().get(k).getSupplierType().getName() != null){
							row.getCell("FName").setValue(info.getAuditResult().get(k).getSupplierType().getName());
						}else{
							row.getCell("FName").setValue(null);
						}

						if(info !=null && info.getAuditResult() != null && info.getAuditResult().get(k).getGrade() != null){
							row.getCell("FState").setValue(info.getAuditResult().get(k).getGrade());
						}else{
							row.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * ��û�з�¼����ֻ��������ݱ����Ϣ
				 */
				else{
					/*
					 * ������
					 */
					row = this.tblMain.addRow();
					
					if(info !=null && info.getSupplier()!= null && info.getSupplier().getId() != null){
						row.getCell("SupplierID").setValue(info.getSupplier().getId());
					}else{
						row.getCell("SupplierID").setValue(null);
					}
					
					if(info !=null && info.getId().toString() != null){
						row.getCell("id").setValue(info.getId().toString());
					}else{
						row.getCell("id").setValue(null);
					}
					
					row.getCell("cType").setValue(getResource("review"));
					
					if(info !=null && info.getBusinessDate() != null){
						row.getCell("businessDate").setValue(info.getBusinessDate());
					}else{
						row.getCell("businessDate").setValue(null);
					}
					
					row.getCell("FScore").setValue(null);
					row.getCell("FName").setValue(null);
					row.getCell("FState").setValue(null);
				}
			}
			
			/*
			 * ��Լ�������������
			 */
			IRow row2 = null;
			FDCSplKeepContractAuditBillCollection stc2 = getSelectedCollection2();
			Iterator it2 = (Iterator) stc2.iterator();

	    	/*
	    	 * ��������ѯ�Ľ�����õ������
	    	 */
			while(it2.hasNext()){
				FDCSplKeepContractAuditBillInfo info2 = (FDCSplKeepContractAuditBillInfo)it2.next();
				
				/*
				 * �����¼�ĸ���
				 */
				int billNum = 0;
				if(info2 !=null && info2.getAuditBill() != null){
					billNum = info2.getAuditBill().size();
				}

				/*
				 * �����ڷ�¼����Ӧ�ķ�¼��Ϣ������������������
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * ������
						 */
						row2 = this.tblMain.addRow();
						
						if(info2 !=null && info2.getSupplier()!= null && info2.getSupplier().getId().toString() != null){
							row2.getCell("SupplierID").setValue(info2.getSupplier().getId().toString());
						}else{
							row2.getCell("SupplierID").setValue(null);
						}
						
						if(info2 !=null && info2.getId().toString() != null){
							row2.getCell("id").setValue(info2.getId().toString());
						}else{
							row2.getCell("id").setValue(null);
						}
						
						row2.getCell("cType").setValue(getResource("evaluate"));
						
						if(info2 !=null && info2.getBusinessDate() != null){
							row2.getCell("businessDate").setValue(info2.getBusinessDate());
						}else{
							row2.getCell("businessDate").setValue(null);
						}
						
						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getScore() != null){
							row2.getCell("FScore").setValue(info2.getAuditBill().get(k).getScore());
						}else{
							row2.getCell("FScore").setValue(null);
						}
						
						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getSupplierType() != null && info2.getAuditBill().get(k).getSupplierType().getName() != null){
							row2.getCell("FName").setValue(info2.getAuditBill().get(k).getSupplierType().getName());
						}else{
							row2.getCell("FName").setValue(null);
						}

						if(info2 !=null && info2.getAuditBill() != null && info2.getAuditBill().get(k).getGrade() != null){
							row2.getCell("FState").setValue(info2.getAuditBill().get(k).getGrade());
						}else{
							row2.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * ��û�з�¼����ֻ��������ݱ����Ϣ
				 */
				else{
					/*
					 * ������
					 */
					row2 = this.tblMain.addRow();

					if(info2 !=null && info2.getSupplier()!= null && info2.getSupplier().getId() != null){
						row2.getCell("SupplierID").setValue(info2.getSupplier().getId());
					}else{
						row2.getCell("SupplierID").setValue(null);
					}
					
					if(info2 !=null && info2.getId().toString() != null){
						row2.getCell("id").setValue(info2.getId().toString());
					}else{
						row2.getCell("id").setValue(null);
					}
					
					row2.getCell("cType").setValue(getResource("evaluate"));

					if(info2 !=null && info2.getBusinessDate() != null){
						row.getCell("businessDate").setValue(info2.getBusinessDate());
					}else{
						row.getCell("businessDate").setValue(null);
					}
					
					row2.getCell("FScore").setValue(null);
					row2.getCell("FName").setValue(null);
					row2.getCell("FState").setValue(null);
				}
			}
			
			/*
			 * ����������������
			 */
			IRow row3 = null;
			FDCSplPeriodAuditBillCollection stc3 = getSelectedCollection3();
			Iterator it3 = (Iterator) stc3.iterator();

	    	/*
	    	 * ��������ѯ�Ľ�����õ������
	    	 */
			while(it3.hasNext()){
				FDCSplPeriodAuditBillInfo info3 = (FDCSplPeriodAuditBillInfo)it3.next();
				
				/*
				 * �����¼�ĸ���
				 */
				int billNum = 0;
				if(info3 !=null && info3.getAuditBill() != null){
					billNum = info3.getAuditBill().size();
				}
				
				/*
				 * �����ڷ�¼����Ӧ�ķ�¼��Ϣ������������������
				 */
				if(billNum > 0 ){
					for(int k = 0 ;k < billNum;k++){
						/*
						 * ������
						 */
						row3 = this.tblMain.addRow();

						if(info3 !=null && info3.getSupplier()!= null && info3.getSupplier().getId() != null){
							row3.getCell("SupplierID").setValue(info3.getSupplier().getId());
						}else{
							row3.getCell("SupplierID").setValue(null);
						}
						
						if(info3 !=null && info3.getId().toString() != null){
							row3.getCell("id").setValue(info3.getId().toString());
						}else{
							row3.getCell("id").setValue(null);
						}
						
						row3.getCell("cType").setValue(getResource("syndic"));
						
						if(info3 !=null && info3.getBusinessDate() != null){
							row3.getCell("businessDate").setValue(info3.getBusinessDate());
						}else{
							row3.getCell("businessDate").setValue(null);
						}
						
						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getScore() != null){
							row3.getCell("FScore").setValue(info3.getAuditBill().get(k).getScore());
						}else{
							row3.getCell("FScore").setValue(null);
						}
						
						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getSupplierType() != null && info3.getAuditBill().get(k).getSupplierType().getName() != null){
							row3.getCell("FName").setValue(info3.getAuditBill().get(k).getSupplierType().getName());
						}else{
							row3.getCell("FName").setValue(null);
						}

						if(info3 !=null && info3.getAuditBill() != null && info3.getAuditBill().get(k).getGrade() != null){
							row3.getCell("FState").setValue(info3.getAuditBill().get(k).getGrade());
						}else{
							row3.getCell("FState").setValue(null);
						}
					}
				}
				/*
				 * ��û�з�¼����ֻ��������ݱ����Ϣ
				 */
				else{
						row3 = this.tblMain.addRow();

						if(info3 !=null && info3.getSupplier()!= null && info3.getSupplier().getId() != null){
							row3.getCell("SupplierID").setValue(info3.getSupplier().getId());
						}else{
							row3.getCell("SupplierID").setValue(null);
						}
						
						if(info3 !=null && info3.getId().toString() != null){
							row3.getCell("id").setValue(info3.getId().toString());
						}else{
							row3.getCell("id").setValue(null);
						}
						
						row3.getCell("cType").setValue(getResource("syndic"));

						if(info3 !=null && info3.getBusinessDate() != null){
							row3.getCell("businessDate").setValue(info3.getBusinessDate());
						}else{
							row3.getCell("businessDate").setValue(null);
						}
						
						row3.getCell("FScore").setValue(null);
						row3.getCell("FName").setValue(null);
						row3.getCell("FState").setValue(null);
				}
			}
			/*
			 * ������ĸ�ʽ����ҳ��ת��
			 */
			this.tblMain.getColumn("businessDate").getStyleAttributes().setNumberFormat("yyyy-mm-dd");
			this.tblMain.getColumn("FScore").getStyleAttributes().setNumberFormat("#0.00");
			
			/*
			 * ���е���ʾ����Ϊ����
			 */
			this.tblMain.getColumn("cType").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("businessDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("FScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			this.tblMain.getColumn("FName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("FState").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			tblMain.getHeadStyleAttributes().setLocked(true);		
			
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
    /**
     * 
    * @description		�����ʸ񿼲�������Ϣ
    * @author			�ﱦƽ	
    * @createDate		2010-11-27
    * @param	
    * @return			FDCSplQualificationAuditBillCollection		
    *	
    * @version			EAS7.0
    * @see
     */
	 protected FDCSplQualificationAuditBillCollection getSelectedCollection() throws EASBizException, BOSException
	 {
			/*
			 * �����ѯʵ��
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditResult.*"));	
			view.getSelector().add(new SelectorItemInfo("auditResult.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * ����һ���������������������ѯ�Ĳ�ѯ����
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * ״̬Ϊ������
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * ����ѯ����ע��
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  ���ð�ʱ�併��
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
		    
		    /*
		     * ���ز�ѯ�Ľ��
		     */
		    return FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillCollection(view);
	}
	 
	 /**
	  * 
	 * @description		������Լ�����������Ϣ
	 * @author			�ﱦƽ	
	 * @createDate		2010-11-27
	 * @param	
	 * @return			FDCSplKeepContractAuditBillCollection		
	 *	
	 * @version			EAS7.0
	 * @see
	  */
	 protected FDCSplKeepContractAuditBillCollection getSelectedCollection2() throws EASBizException, BOSException
	 {
			/*
			 * �����ѯʵ��
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditBill.*"));
			view.getSelector().add(new SelectorItemInfo("auditBill.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * ����һ���������������������ѯ�Ĳ�ѯ����
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * ״̬Ϊ������
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * ����ѯ����ע��
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  ���ð�ʱ�併��
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
			
		    /*
		     * ���ز�ѯ�Ľ��
		     */
		    return FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillCollection(view);
	}
	
	 /**
	  * 
	 * @description		���˶�������������Ϣ
	 * @author			�ﱦƽ	
	 * @createDate		2010-11-27
	 * @param	
	 * @return			FDCSplPeriodAuditBillCollection		
	 *	
	 * @version			EAS7.0
	 * @see
	  */
	 protected FDCSplPeriodAuditBillCollection getSelectedCollection3() throws EASBizException, BOSException
	 {
			/*
			 * �����ѯʵ��
			 */
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));        
			view.getSelector().add(new SelectorItemInfo("auditBill.*"));	
			view.getSelector().add(new SelectorItemInfo("auditBill.supplierType.*"));
			view.getSelector().add(new SelectorItemInfo("supplier.*"));
	        
			/*
			 * ����һ���������������������ѯ�Ĳ�ѯ����
			 */
			FilterInfo filter = new FilterInfo();
			
			/*
			 * ״̬Ϊ������
			 */
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
			
	        /*
	         * ����ѯ����ע��
	         */
		    view.setFilter(filter);
	  		try {
	  			/*
	  			 *  ���ð�ʱ�併��
	  			 */
				view.decode("ORDER BY businessDate desc");
			} catch (ParserException e) {
				handleException(e);
			}
		    
		    /*
		     * ���ز�ѯ�Ľ��
		     */
		    return FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillCollection(view);
	}

	public void onShow() throws Exception {
		/*
		 * �ϲ���Ԫ��
		 */
		unitTable();
	}
	
    /**
     * 
    * @description		�ϲ���Ԫ��
    * @author			�ﱦƽ	
    * @createDate		2010-11-26
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
    public void unitTable() {
		KDTMergeManager mm =tblMain.getMergeManager();
		int longth = tblMain.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * ȡ��������
			 */
			row = tblMain.getRow(i);
			
			/*
			 * ȡ�õ�Ԫ��id��ֵ
			 */
			String type = (String) row.getCell("id").getValue();
			if (null == type) {
				continue;
			}
			if (map.get(type) == null) {
				map.put(type, Boolean.TRUE);
			}
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();
			int ben =getStartEnd( -1 , -1 , type, longth)[0];
			int end =getStartEnd( -1 , -1 , type, longth)[1];
            if(ben<end){
			  mm.mergeBlock(ben,0,end,0,KDTMergeManager.SPECIFY_MERGE);
            }
		}
	}
    
    /**
     * 
    * @description		��ȡҪ�ϲ�����
    * @author			�ﱦƽ	
    * @createDate		2010-12-1
    * @param	
    * @return			int[]		
    *	
    * @version			EAS7.0
    * @see
     */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * ȡ��������
			 */
			row = tblMain.getRow(i);
			
			/*
			 * ȡ�õ�Ԫ��id��ֵ
			 */
			String Str = (String) row.getCell("id").getValue();
			if (null == Str) {
				continue;
			}
			if (Str.equals(type)) {
				if (ben < 0) {
					ben = i;
				} else {
					end = i;
				}
			}
		} 
       int obj [] = new int[2];
       obj[0]=ben;
       obj[1]=end;
       return obj;
	}
    
	/**
	 * 
	* @description		�رմ���ˢ��֮ǰ���õ�ҳ��
	* @author			�ﱦƽ	
	* @createDate		2010-11-21
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.CoreUI#destroyWindow()
	 */
    public boolean destroyWindow() {
   	 boolean b = super.destroyWindow();
   	 Object ui = null ;
   	 ui = getUIContext().get("Owner");
   	 if( b && null !=ui && ui instanceof SupplierStockListUI){
   		 SupplierStockListUI newUI = (SupplierStockListUI)ui;
       	 try {
				newUI.getTblMain();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return b;
   }
    
    /**
     * output class constructor
     */
    public AppraiseHistoryUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

}