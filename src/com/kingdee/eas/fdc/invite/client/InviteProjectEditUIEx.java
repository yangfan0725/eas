package com.kingdee.eas.fdc.invite.client;

/**
 * �޸��������ʱ���û������޸ĵ���
 * @author kingdee0001
 *
 */
public class InviteProjectEditUIEx extends InviteProjectEditUICTEx
{

	public InviteProjectEditUIEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ���ذ�ť
	 */
	private void hideButtons()
	{
		this.btnAddNew.setEnabled(false);
		this.btnRemove.setEnabled(false);
		this.btnCancel.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnAddLine.setEnabled(false);
		this.btnInsertLine.setEnabled(false);
		this.btnInsertPlan.setEnabled(false);
		this.btnRemoveLine.setEnabled(false);
		this.btnRemovePlan.setEnabled(false);
		this.btnRelate.setEnabled(false);
	}
	
	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		hideButtons();
		
	}

}
