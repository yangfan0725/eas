///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.ActionEvent;
//import java.util.ArrayList;
//
//import javax.swing.Action;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.bos.ui.face.IUIWindow;
//import com.kingdee.bos.ui.face.UIFactory;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.common.client.OprtState;
//import com.kingdee.eas.common.client.UIContext;
//import com.kingdee.eas.common.client.UIFactoryName;
//import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.customerservice.ServiceRegisterBillStatusEnum;
//import com.kingdee.eas.fdc.customerservice.ServiceRergisterBillInfo;
//import com.kingdee.eas.fdc.sellhouse.IWebMarkSchema;
//import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.EASResource;
//import com.kingdee.eas.util.client.MsgBox;
//
///**
// * output class name
// */
//public class WebMarkSchemaListUI extends AbstractWebMarkSchemaListUI {
//	private static final Logger logger = CoreUIObject
//			.getLogger(WebMarkSchemaListUI.class);
//
//	/**
//	 * output class constructor
//	 */
//	public WebMarkSchemaListUI() throws Exception {
//		super();
//	}
//
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.actionFieldRelation.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_createcredence"));
//		
//		this.actionFieldRelation.setEnabled(true);
//	}
//
//	/**
//	 * output storeFields method
//	 */
//	public void storeFields() {
//		super.storeFields();
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		// TODO �Զ����ɷ������
//		return WebMarkSchemaFactory.getRemoteInstance();
//	}
//
//	protected IObjectValue createNewData() {
//		// TODO �Զ����ɷ������
//		WebMarkSchemaInfo objectValue = new WebMarkSchemaInfo();
//		return objectValue;
//	}
//
//	protected String getEditUIName() {
//		// TODO �Զ����ɷ������
//
//		return WebMarkSchemaEditUI.class.getName();
//	}
//
//	public void loadFields() {
//		super.loadFields();
//	}
//
//	protected FDCDataBaseInfo getBaseDataInfo() {
//		return new WebMarkSchemaInfo();
//	}
//
//	/**
//	 * output actionFieldRelation_actionPerformed method
//	 */
//	public void actionFieldRelation_actionPerformed(ActionEvent e)
//			throws Exception {
//		checkSelected();
//		
//		int activeRowIndex = this.tblMain.getSelectManager()
//				.getActiveRowIndex();
//		String schemaId = (String) this.tblMain.getCell(activeRowIndex, "id")
//				.getValue();
//		
//		//�ж��Ƿ�����
//		isEnable(schemaId);
//		UIContext uiContext = new UIContext(this);
//		String	sOprt = OprtState.ADDNEW;
//		uiContext.put(UIContext.ID, null);
//		uiContext.put(UIContext.OWNER, this);
//		uiContext.put("schemaid", schemaId);
//		// prepareUIContext(uiContext, e);
//		IUIWindow uiWindow = UIFactory
//				.createUIFactory(UIFactoryName.NEWTAB)
//				.create(WebMarkFieldEditUI.class.getName(),	uiContext, null, sOprt);
//		uiWindow.show();
//		//this.repaint();
//	}
//	
//	/**
//	 * �ж���ǩ�����Ƿ�����
//	 * @param id
//	 */
//	private void isEnable(String id){
//
//		boolean result = true;
//		
//		FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("id",id));
//    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));//�Ƿ�����
//    	try {
//			if(!WebMarkSchemaFactory.getRemoteInstance().exists(filter)){//�жϼ�¼�Ƿ����
//				result =false;
//			}
//		} catch (EASBizException e) {
//			logger.error(e.getMessage()+"�����ǩ�����Ƿ�����ʧ�ܣ�");
//		} catch (BOSException e) {
//			logger.error(e.getMessage()+"�����ǩ�����Ƿ�����ʧ�ܣ�");
//		}
//		
//		if(!result){
//			FDCMsgBox.showWarning(this,"�������ã�");
//			SysUtil.abort();
//		}
//		
//	}
//	
//	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		checkStatus("�˼�¼�����ã������޸ģ�");
//		super.actionEdit_actionPerformed(e);
//	}
//	
//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		checkStatus("�˼�¼�����ã�����ɾ����");
//		super.actionRemove_actionPerformed(e);
//	}
//	
//	private void checkStatus(String msg){
//		try {
//			
//			ArrayList idList = getSelectedIdValues();
//			if(idList==null || idList.size()<=0){
//				FDCMsgBox.showWarning(this,"����ѡ���У�");
//				SysUtil.abort();
//			}
//			IWebMarkSchema serviceReg = WebMarkSchemaFactory.getRemoteInstance();
//			
//			EntityViewInfo evi = new EntityViewInfo();
//			FilterInfo filterInfo = new FilterInfo();
//			filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(0).toString(), CompareType.EQUALS));
//			evi.setFilter(filterInfo);
//
//			SelectorItemCollection coll = new SelectorItemCollection();
//			coll.add(new SelectorItemInfo("id"));
//			coll.add(new SelectorItemInfo("isEnabled"));
//
//			evi.setSelector(coll);
//
//			WebMarkSchemaCollection collection = serviceReg.getWebMarkSchemaCollection(evi);
//
//			if (collection != null && collection.size() > 0) {
//				for (int i = 0; i < collection.size(); i++) {
//					WebMarkSchemaInfo info = collection.get(i);
//					if (info.isIsEnabled()) {
//						MsgBox.showWarning(this,msg);
//						SysUtil.abort();
//					}
//				}
//			} else {
//				MsgBox.showWarning(this, "�˼�¼�Ѳ����ڣ�");
//				SysUtil.abort();
//			}
//
//		} catch (BOSException e) {
//			logger.error(e + "��ȡ��ǩ����״̬ʧ�ܣ�");
//		}
//	}
//}