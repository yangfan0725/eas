package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;

/**
 * �༭����ģ�����,��ע���޸���ʷ
 * */
//����ֻ��԰�Query����ʱ������
//1.���������ʱ��ˢ�µĲ���,ͳһʹ��this.refresh(null);
//2.��ʱ����Ҫ��ӵĹ�������,ͳһ��getQueryExecutor��ʵ��
public class TestListUI extends ListUI{

	public TestListUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	
	//�򿪱༭����ķ�ʽ
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	//��ʼ��Ĭ�Ϲ�������
	protected boolean initDefaultFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		//TODO �˵���ť������,���صȲ���
	}
	
	protected void initTree() throws Exception{
		//TODO ������������ʼ���Ĳ���
	}
	
	//���Ĭ������CU����,���ϣ������CU����,��д�˷�������true
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	//����ע�������ݵ���������Ϊ�������ʱ�ɷ���True����ִ��count�Ĳ���
	public boolean isIgnoreRowCount() {
		return false;
	}
	
	//���������Զ���������,�ڴ˴����	 
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(null/*�Զ���Ĺ������CustomerQueryPanel*/);
		return commonQueryDialog;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		//TODO ������Ҫ���ӵĹ�������,������������
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//TODO ����ѡ�м�¼��״̬���ò˵���ť״̬
		super.tblMain_tableClicked(e);
	}
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception{
		//����ѡ�е���������ò˵�״̬,�������������õ�getQueryExecutor������
		//ִ�в�ѯ,����ע�ⲻҪ�ظ�����
		this.refresh(null);
//		this.execQuery();
//		this.tblMain.removeRows();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		
		//ɾ��֮��Ĳ���,ע�����ķ���,�������"�Ƿ�ȷ��ɾ��"����ʾ�û�ѡ��"��",�����Ի�ִ�е�����
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		//TODO ĳЩ����༭�����ֵ��Ҫд��actionAddNew��,д������
		super.prepareUIContext(uiContext, e);
	}
	
	//������ð���ĳ���ֶζ�λ��¼,������ʵ��
	protected String[] getLocateNames() {
		return super.getLocateNames();
	}
}
