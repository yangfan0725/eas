package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.EditUI;

/**
 * �༭����ģ�����,��ע���޸���ʷ
 * */
//��Ҫ���ܼ�������
//createNewData����ʱ��ʼ��ֵ����edit	Data,getValueΪ�鿴���޸Ĳ���ʱ��ʼ��ֵ����editData;
//loadFields��editData��ֵ���õ�����ؼ���
//storeFields���û��ڽ��������õ�ֵ���浽editDataֵ������
//ÿ���������ô��ⵥһ,�ʲ�Ҫ��createNewData���������ÿؼ�״̬,��Ҫ��storeFields�������֤�Ĳ���
public class TestEditUI extends EditUI{
	protected boolean isLoadFields = false;
	
	public TestEditUI() throws Exception {
		super();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		initControl();
		
		super.onLoad();
	}
	
	private void initControl() {
		//TODO ��ʼ���ؼ�
		//TODO �̶�����Ľ�������,��ť����
	}

	public void loadFields() {
		//Ϊ�˱���loadFieldsʱ������ؿؼ����¼�,ʹ�øñ���
		isLoadFields = true;
		//TODO ��Ҫ����editData���ص������ϵĲ���
		//TODO ����ĳһ����������һ�ؼ��Ĺ������������Գ�ɶ���������������ã����ܻᱻ�¼��е���
		super.loadFields();
		isLoadFields = false;
	}
	
	protected void f7_dataChanged(DataChangeEvent e) throws Exception {
		if(isLoadFields){
			return;
		}
		//TODO �¼������Ĳ���
	}
	
	//TODO ������ֵ�Ƿ�Ϸ���У���������
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	}
	
	public void storeFields() {
		//TODO ��Ҫ���������ϵ����ݻ�ȡд��editData�������߼�������Ҫд������
		super.storeFields();
	}
	
	public SelectorItemCollection getSelectors() {
		//TODO ����δ�󶨵�ֵ�����ֶ�,��Ҫ������ָ��,����getValueĬ�ϲ����ѯ���ֶ�
		return super.getSelectors();
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		//TODO �Է�¼���β�ѯ�Ĳ������Է�¼������Ȳ������Է�������
		return super.getValue(pk);
	}
	
	protected IObjectValue createNewData() {
		//TODO ����ʱֵ�����Ĭ��ֵ����������
		//TODO ����������ͨ��UIContext�����ֵ�������ȡ���õ���ֵ������
		return null;
	}
	
	//����setOprtState�������Ա�֤�����״̬�仯ʱ���ؼ�ʹ��״̬�仯
	//TODO �ؼ���ť״̬�����״̬���Ƶ�,���ƴ���д������
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	}
	
}
