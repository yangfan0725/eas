/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


 
/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��Ϣ�����ʷ
 *		
 * @author		��ΰ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierStockInfoFullUI extends AbstractSupplierStockInfoFullUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4327599948896406330L;
	private static final Logger logger = CoreUIObject.getLogger(SupplierStockInfoFullUI.class);
	private int isStart = 0;
    
    /**
     * output class constructor
     */
    public SupplierStockInfoFullUI() throws Exception
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
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	//���ò鿴��ť��ͼ����ʾ����
    	this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
    	this.menuItemView.setIcon(EASResource.getIcon("imgTbtn_view"));
    	//���÷���˵��Ĺ���ѡ��Ϊ����
    	this.MenuItemKnowStore.setVisible(true);
    	this.MenuItemKnowStore.setEnabled(true);
    	this.MenuItemAnwser.setVisible(true);
    	this.MenuItemAnwser.setEnabled(true);
    	this.MenuItemRemoteAssist.setVisible(true);
    	this.MenuItemRemoteAssist.setEnabled(true);
    	

    	initInfo();
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {
    	//�ж��Ƿ�ѡ����
   	 checkSelected();
   	 
		//��ȡѡ���¼��ID��״̬
		int rowIndex = kdtEditInfo.getSelectManager().getActiveRowIndex();
		Object infoId = kdtEditInfo.getRow(rowIndex).getCell("id").getValue();
		
		UIContext uiContext = new UIContext(this);
		
		//�ж�ѡ���¼��ID�Ƿ�Ϊ��
		if(null != infoId){
       	//��ѡ���¼��ID����
   		uiContext.put("ID", infoId);
   
		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
		IUIWindow uiWindow = null;
		
		//����ѡ����ת���鿴ҳ��
		uiWindow = getIUIWindow(uiContext,uiFactory,uiWindow);
 		uiWindow.show();
		}
    }
    
	//�ж��Ƿ�ѡ����
	public void checkSelected()
	{
		if (kdtEditInfo.getRowCount() == 0 || kdtEditInfo.getSelectManager().size() == 0)
		{
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}
    
	//����ѡ����ת���鿴ҳ��
    protected IUIWindow getIUIWindow(UIContext uiContext,IUIFactory uiFactory,IUIWindow uiWindow) throws Exception{

 		uiWindow = uiFactory.create(SupplierStockInfoEditUI.class.getName(), uiContext, null,"FINDVIEW");
 		
 		return uiWindow;
    }
    
    protected void kdtEditInfo_tableClicked(KDTMouseEvent e) throws Exception {
    	// δѡ�б���ʱ
		if (e.getType() == 0 || e.getType() == 3) {
			return;
		} else {
			this.kdtEditInfo.getStyleAttributes().setLocked(true);
    		this.kdtEditInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    		
			int activeRowIndex = this.kdtEditInfo.getSelectManager().getActiveRowIndex();
			
			if (activeRowIndex != -1 && e.getClickCount() == 2) {
				if (this.kdtEditInfo.getRow(activeRowIndex).getCell("id") != null) {
					
					// �ж��Ƿ�ѡ��
					checkSelected();
					
					// ��ȡѡ���¼��ID��״̬
					int rowIndex = kdtEditInfo.getSelectManager().getActiveRowIndex();
					Object infoId = kdtEditInfo.getRow(rowIndex).getCell("id").getValue();

					UIContext uiContext = new UIContext(this);
					
					// �ж�ѡ���¼��ID�Ƿ�Ϊ��
					if (null != infoId && isStart == 0) {
						// ��ѡ���¼��ID����
						uiContext.put("ID", infoId);
					

					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					IUIWindow uiWindow = null;
					
					// ����ѡ�����ת���鿴ҳ��
					uiWindow = getIUIWindow(uiContext, uiFactory, uiWindow);
					uiWindow.show();
					}
				}
			}
		}
	}
    
    /**
     * @description		��ȡ��ʷ����
     * @author			��ΰ		
     * @createDate		2010-12-9
     * @param	        ��Ӧ�̱��
     * @return			�������Collection		
     *	
     * @version			EAS1.0
     * @see						
     */
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection(String number) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("*")); 
		view.getSelector().add(new SelectorItemInfo("creator.*")); 
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplier.number", number));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		 
		SorterItemCollection sic = new SorterItemCollection();
		SorterItemInfo sitemInfo = new SorterItemInfo("createTime");
		sitemInfo.setSortType(SortType.DESCEND);		
		sic.add(sitemInfo);
		 
		view.setFilter(filter);
		view.setSorter(sic);
//		try {
//			view.decode("ORDER BY createTime desc");
//		} catch (ParserException e) {
//			handleException(e);
//		}
		 
		FDCSplChangeHistroyCollection fchSplCHC = FDCSplChangeHistroyFactory.getRemoteInstance().getFDCSplChangeHistroyCollection(view);
		return fchSplCHC;
    	
    }
   
    /**
     * @description		��ʼ��
     * @author			��ΰ		
     * @createDate		2010-11-10
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @throws BOSException 
     * @throws EASBizException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @see						
     */
    public void initInfo() throws EASBizException, BOSException, IOException, ClassNotFoundException{
    	FDCSplChangeHistroyInfo info = (FDCSplChangeHistroyInfo)getUIContext().get("FDCSplChangeHistroyInfo");
    	kdtEditInfo.checkParsed();
    	kdtEditInfo.getColumn("dete").getStyleAttributes().setNumberFormat(
		"yyyy-MM-dd HH:mm"); 
		  kdtEditInfo.removeRows();
  		  this.kdtEditInfo.getStyleAttributes().setLocked(true);
		  this.kdtEditInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	if(null != info){
 
        		this.txtSupplierName.setText(info.getSupplier().getName());
      
        	isStart=1;
    		kdtEditInfo.removeRows();
    		this.kdtEditInfo.getStyleAttributes().setLocked(true);
    		this.kdtEditInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    		initkdtEditInfo(info);
    		
 
    	}else{
        	/*
        	 * ��ʷ�����Ϣ
        	 */
    		SupplierStockInfo sInfo = (SupplierStockInfo)getUIContext().get("SupplierInfo");
    		this.txtSupplierName.setText(sInfo.getName());
    		
    		FDCSplChangeHistroyCollection sicc  =getFDCSplChangeHistroyCollection(sInfo.getNumber()); 
    		
    		if(null != sicc && sicc.size()>0){
    		  

    		  Iterator it = sicc.iterator();
    		  while(it.hasNext()){
    			  FDCSplChangeHistroyInfo sseinfo = (FDCSplChangeHistroyInfo)it.next();
    		      initkdtEditInfo(sseinfo);
    		  }
    		}
    	}
    	this.txtSupplierName.setEditable(false);
    }
 
    /**
     * @description		�ڱ���в�������
     * @author			��ΰ		
     * @createDate		2010-11-10
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @see						
     */
    public void initkdtEditInfo(FDCSplChangeHistroyInfo info) throws IOException, ClassNotFoundException{ 
    	if(null == info.getCreator()){
    		return;
    	}
    	IRow row = this.kdtEditInfo.addRow();
 
    	row.getCell("id").setValue(info.getId());
 		row.getCell("dete").setValue(info.getCreateTime());
 		
		row.getCell("editPerson").setValue(info.getCreator().getName());
		row.getCell("info").setValue(info.getChangeText()); 
		
		//������ĸ�ʽ����ҳ��ת��
		this.kdtEditInfo.getColumn("dete").getStyleAttributes().setNumberFormat("yyyy.mm.dd");
		
    }
    /**
     * @description		������
     * @author			��ΰ		
     * @createDate		2010-11-14
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public SelectorItemCollection getSelectors() {
	       SelectorItemCollection sic = new SelectorItemCollection();
	       sic.add(new SelectorItemInfo("*"));
	       sic.add(new SelectorItemInfo("supplier.*"));
	       sic.add(new SelectorItemInfo("supplier.supplierType.*"));
	       sic.add(new SelectorItemInfo("supplier.city.*"));
	       sic.add(new SelectorItemInfo("supplier.linkPerson.*"));
	       sic.add(new SelectorItemInfo("supplier.yearSale.*"));
	       sic.add(new SelectorItemInfo("supplier.aptitudeFile.*"));
	       sic.add(new SelectorItemInfo("supplier.province.*"));
	       sic.add(new SelectorItemInfo("creator.*"));
	       super.getSelectors();
		return sic;
    }
}