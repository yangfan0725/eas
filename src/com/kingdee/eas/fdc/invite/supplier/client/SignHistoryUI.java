/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
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
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			SignHistoryUI.java			
 * ��Ȩ��		�����������������޹�˾��Ȩ����<P>		 	
 * ������		��Ӧ��.ǩԼ��ʷ<P>
 *
 * @author		�ﱦƽ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class SignHistoryUI extends AbstractSignHistoryUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignHistoryUI.class);
    
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
 		 * ��ȡѡ���¼��ID��״̬
 		 */
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
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
	 * @description		������ı����ٲ�ѯ������ѯ������������
	 * @author			�ﱦƽ		
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
     * @throws EASBizException 
	 * @see						
	 */
	private void setValueToTable() throws EASBizException {

		if(null != getUIContext().get("SupplierInfo")){
    		SupplierStockInfo infoTMP = (SupplierStockInfo) getUIContext().get("SupplierInfo");
    		
    		/*
    		 * �����ݵĹ�Ӧ�����Ʒ��õ��ı�����
    		 */
    		this.txtSupplierName.setText(infoTMP.getName());
    		
        	/*
        	 * /��ȡѡ���¼��Number,ID
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
		this.tblMain.removeRows();
		this.tblMain.getStyleAttributes().setLocked(true);
 		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		 
    	try {
    		IRow row = null;
			ContractBillCollection stc = getSelectedCollection();
			Iterator it = (Iterator) stc.iterator();

			/*
			 * ��������ѯ�Ľ�����õ������
			 */
			while (it.hasNext()) {
				ContractBillInfo info = (ContractBillInfo) it.next();
				
				/*
				 * ������
				 */
				row = this.tblMain.addRow();

				/*
				 * ���ݵ�Ԫ�������,���ν�ֵ���õ���Ԫ����,���ų�ΪNULL������
				 */
				
				if(info !=null && info.getId() != null){
					row.getCell("id").setValue(info.getId());
				}else{
					row.getCell("id").setValue(null);
				}
				
				if(info !=null && info.getCurProject() != null && info.getCurProject().getName() != null){
					row.getCell("projectName").setValue(info.getCurProject().getName());
				}else{
					row.getCell("projectName").setValue(null);
				}
				
				if(info !=null && info.getName() != null){
					row.getCell("contractName").setValue(info.getName());
				}else{
					row.getCell("contractName").setValue(null);
				}
				
				if(info !=null && info.getSignDate() != null){
					row.getCell("signDate").setValue(info.getSignDate());
				}else{
					row.getCell("signDate").setValue(null);
				}
				
				if(info !=null && info.getAmount() != null){
					row.getCell("signSum").setValue(info.getAmount());
				}else{
					row.getCell("signSum").setValue(null);
				}
			}

			/*
			 * ������ĸ�ʽ����ҳ��ת��
			 */
			// ������Bug�ˣ�˵����������ʾ��ʽ��ͳһ Added by Owen_wen 2011-12-02
			//this.tblMain.getColumn("signDate").getStyleAttributes().setNumberFormat("yyyy.mm.dd");
			this.tblMain.getColumn("signSum").getStyleAttributes().setNumberFormat("#,##0.00");
			
			/*
			 * ���е���ʾ����Ϊ����
			 */
			this.tblMain.getColumn("projectName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("contractName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("signDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			this.tblMain.getColumn("signSum").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			tblMain.getHeadStyleAttributes().setLocked(true);
			
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	/**
	 * 
	* @description		���˲鿴��ͬ�������Ϣ
	* @author			�ﱦƽ	
	* @createDate		2010-11-27
	* @param	
	* @return			ContractBillCollection		
	*	
	* @version			EAS7.0
	* @see
	 */
	protected ContractBillCollection getSelectedCollection() throws EASBizException, BOSException
	{
		/*
		 * �����ѯʵ��
		 */
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("name"));	 
		view.getSelector().add(new SelectorItemInfo("amount"));	 
		view.getSelector().add(new SelectorItemInfo("signDate"));	 
        view.getSelector().add(new SelectorItemInfo("curProject.*"));   
        
        /*
         * �����ѯʵ��
         */
		FilterInfo filter = new FilterInfo();
		
		/*
		 * ״̬Ϊ������
		 */
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

		/*
		 * ���¶���һ������������������µĲ�ѯ����
		 */
		FilterInfo  tenderDiscussionFilter = new FilterInfo();
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("partB.number",supplierNumber));
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("partC.number",supplierNumber));
		/*
		 * numberΪ�ұ��ĺ�ͬ,or����
		 */
		tenderDiscussionFilter.setMaskString("#0 or #1");
		
		/*
		 * ����������or�Ĺ���������state�Ĺ�ϵ����Ϊand����
		 */
        filter.mergeFilter(tenderDiscussionFilter, "and");
        
        /*
         * ����ѯ����ע��
         */
	    view.setFilter(filter);
  		try {
			view.decode("ORDER BY signDate desc");
		} catch (ParserException e) {
			handleException(e);
		}
	    
	    /*
	     * ���ز�ѯ�Ľ��
	     */
        return ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
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

 		uiWindow = uiFactory.create(ContractBillEditUI.class.getName(), uiContext, null,"FINDVIEW");
 		
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
		 *  δѡ�б���ʱ
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
					 * ��ȡѡ���¼��ID
					 */
					int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
					
					/*
					 * ��ȡѡ���¼��״̬
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
    public SignHistoryUI() throws Exception
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