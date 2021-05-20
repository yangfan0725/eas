/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.AcceptanceLetterCollection;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillCollection;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillFactory;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo;
import com.kingdee.eas.fdc.invite.client.InviteEntSuppChkBillEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			EnterHistoryUI.java			
 * ��Ȩ��		�����������������޹�˾��Ȩ����<P>		 	
 * ������		��Ӧ��.��Χ��ʷ<P>
 *
 * @author		�ﱦƽ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class EnterHistoryUI extends AbstractEnterHistoryUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterHistoryUI.class);
    
    /*
     * ���幩Ӧ��number
     */
    private String supplierNumber = "";
    
    /*
     * ���幩Ӧ��ID
     */
    private String supplierID = "";
    
    /*
     * ����ѡ����Ŀ��ID
     */
    private String entInviteProjectID = "";
    
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
 		 * ����ѡ����ת���鿴ҳ��
 		 */
 		uiWindow = getIUIWindow(uiContext,uiFactory,uiWindow);
  		uiWindow.show();
    }
    
    /**
     * 
    * @description		����ѡ����ת���鿴ҳ��
    * @author			�ﱦƽ	
    * @createDate		2010-11-15
    * @param			UIContext,IUIFactory,IUIWindow
    * @return			IUIWindow		
    *	
    * @version			EAS7.0
    * @see
     */
    protected IUIWindow getIUIWindow(UIContext uiContext,IUIFactory uiFactory,IUIWindow uiWindow) throws Exception{

    	uiWindow = uiFactory.create(InviteEntSuppChkBillEditUI.class.getName(), uiContext, null,"FINDVIEW");
 		
    	return uiWindow;
    }
    
    /**
     * 					˫��ѡ����ת���鿴ҳ��
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
    		 * ��ȡѡ���е�����
    		 */
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			
			if (activeRowIndex != -1 && e.getClickCount() == 2) {
				if (this.tblMain.getRow(activeRowIndex).getCell("id") != null) {
					
					/*
					 * �ж��Ƿ�ѡ��
					 */
					checkSelected();
					
					/*
					 * ��ȡѡ���е�����
					 */
					int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
					
					/*
					 * ��ȡѡ���¼��id
					 */
					Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();

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
					 * ����ѡ�����ת���鿴ҳ��
					 */
					uiWindow = getIUIWindow(uiContext, uiFactory, uiWindow);
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
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
		{
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
    		IRow row = null;
    		InviteEntSuppChkBillCollection stc = getSelectedCollection();
			Iterator it = (Iterator) stc.iterator();

			/*
			 * ��������ѯ�Ľ�����õ������
			 */
			while(it.hasNext()){
				InviteEntSuppChkBillInfo info = (InviteEntSuppChkBillInfo)it.next();
				
				/*
				 * ������
				 */
				row = this.tblMain.addRow();
				
				/*
				 * ���ݵ�Ԫ�������,���ν�ֵ���õ���Ԫ���У����ų�ΪNULL������
				 */
				
				row.getCell("SupplierID").setValue(supplierID);
				
				if(info !=null && info.getId().toString() != null){
					row.getCell("id").setValue(info.getId().toString());
				}else{
					row.getCell("id").setValue(null);
				}

			/*	if(info != null && info.getInviteProject() != null && info.getInviteProject().getProject()!=null && info.getInviteProject().getProject().getName() != null && info.getInviteProject().getProject().getName().trim().length() > 0){
					row.getCell("prjName").setValue(info.getInviteProject().getProject().getName());
				}else{
					row.getCell("prjName").setValue(null);
				}*/
				
				if(info != null && info.getInviteProject() != null && info.getInviteProject().getName() != null && info.getInviteProject().getName().trim().length() > 0){
					row.getCell("traTask").setValue(info.getInviteProject().getName());
				}else{
					row.getCell("traTask").setValue(null);
				}
				
				if(info !=null && info.getHandlerDate() != null){
					row.getCell("entDate").setValue(info.getHandlerDate());
				}else{
					row.getCell("entDate").setValue(null);
				}

				if(info !=null && info.getInviteProject() != null && info.getInviteProject().getId().toString() != null){
					
					/*
					 * ȡ��ѡ���еĹ�Ӧ��������Ŀ��ID
					 */
					entInviteProjectID = info.getInviteProject().getId().toString();
					
					/*
					 * �ж��Ƿ����б�֪ͨ����
					 */
					AcceptanceLetterCollection stc2 = getSelectedCollection2();

					/*
					 * �������б�֪ͨ���еĸ���
					 */
					int billNum = 0;
					if(stc2 !=null){
						billNum = stc2.size();
					}

					/*
					 * �����б�֪ͨ������Ϊ�б꣬����Ϊδ�б�
					 */
					if (billNum > 0) {
						row.getCell("isTarget").setValue(getResource("zhongBiao"));
					} else {
						row.getCell("isTarget").setValue(getResource("notZhongBiao"));
					}
				}else{
					row.getCell("isTarget").setValue(null);
				}
			}
			
			/*
			 * ������ĸ�ʽ����ҳ��ת��
			 */
			// ������Bug�ˣ�˵����������ʾ��ʽ��ͳһ Added by Owen_wen 2011-12-02
			// this.tblMain.getColumn("entDate").getStyleAttributes().setNumberFormat("yyyy.mm.dd");
			
			/*
			 * ���е���ʾ����Ϊ����
			 */
			this.tblMain.getColumn("prjName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("traTask").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("entDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("isTarget").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			tblMain.getHeadStyleAttributes().setLocked(true);
			
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
	/**
	 * 
	* @description		������Χ�����������¼�������Ϣ
	* @author			�ﱦƽ	
	* @createDate		2010-11-27
	* @param	
	* @return			ContractBillCollection		
	*	
	* @version			EAS7.0
	* @see
	 */
	protected InviteEntSuppChkBillCollection getSelectedCollection() throws EASBizException, BOSException
	{
		/*
		 * �����ѯʵ��
		 */
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("handlerDate"));	 
		view.getSelector().add(new SelectorItemInfo("entry.*"));	 
        view.getSelector().add(new SelectorItemInfo("entry.supplier.*")); 
		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));	 
		view.getSelector().add(new SelectorItemInfo("inviteProject.project.*"));	
        
		/*
		 * ��ѡ���¼��Number���õ�list��
		 */
		List list = new ArrayList();
		list.add(supplierNumber);

		/*
		 * ���ݴ��ݵ�list,����Զ�̷���,��ѯ��Χ��������¼�е�ParentID(����Χ��������ID),����������õ�set�з���
		 */
		Set setId = SupplierStockFactory.getRemoteInstance().getEHDBValue(list);
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		//filter.getFilterItems().add(new FilterItemInfo("entry.supplier.number",supplierNumber));
		filter.getFilterItems().add(new FilterItemInfo("id",setId,CompareType.INCLUDE));

        /*
         * ����ѯ����ע��
         */
	    view.setFilter(filter);
  		try {
			view.decode("ORDER BY handlerDate desc");
		} catch (ParserException e) {
			handleException(e);
		}
	    
	    /*
	     * ���ز�ѯ�Ľ��
	     */
        return InviteEntSuppChkBillFactory.getRemoteInstance().getInviteEntSuppChkBillCollection(view);
	}
	
	/**
	 * 
	* @description		�����б�֪ͨ��������Ϣ
	* @author			�ﱦƽ	
	* @createDate		2010-11-28
	* @param	
	* @return			AcceptanceLetterCollection		
	*	
	* @version			EAS7.0
	* @see
	 */
	protected AcceptanceLetterCollection getSelectedCollection2() throws EASBizException, BOSException
	{
		/*
		 * �����ѯʵ��
		 */
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		view.getSelector().add(new SelectorItemInfo("id"));	 
		view.getSelector().add(new SelectorItemInfo("supplier.*"));	  
		view.getSelector().add(new SelectorItemInfo("inviteProject.*"));	 
        
		/*
		 * ����һ���������������������ѯ�Ĳ�ѯ����
		 */
		FilterInfo filter = new FilterInfo();
		
		/*
		 * ״̬Ϊ������
		 */
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("supplier.number",supplierNumber));
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",entInviteProjectID));

        /*
         * ����ѯ����ע��
         */
	    view.setFilter(filter);
	    
	    /*
	     * ���ز�ѯ�Ľ��
	     */
        return AcceptanceLetterFactory.getRemoteInstance().getAcceptanceLetterCollection(view);
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
    public EnterHistoryUI() throws Exception
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