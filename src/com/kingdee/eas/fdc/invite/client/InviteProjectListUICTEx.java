package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.fdchrchooser.HROpenUI;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

public class InviteProjectListUICTEx extends InviteProjectListUI
{

	public InviteProjectListUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT); // ���ö�ѡ
		this.btnGrant.setVisible(true);
		this.btnGrant.setEnabled(true);
		// ���Ӱ�ť�������ɳ����࣬��������뱻�޸Ĺ�
//		KDWorkButton btnGrantAuth = new KDWorkButton();
//		btnGrantAuth.setText("�����ƽ�");
//		this.toolBar.add(btnGrantAuth);
//		btnGrantAuth.addActionListener(new java.awt.event.ActionListener()
//		{
//			public void actionPerformed(java.awt.event.ActionEvent e)
//			{
//				beforeActionPerformed(e);
//				try
//				{
//					grantAuthority_actionPerformed(e);
//				}
//				catch (Exception exc)
//				{
//					handUIException(exc);
//				}
//				finally
//				{
//					afterActionPerformed(e);
//				}
//			}
//		});

	}

	/**
	 * /** �ж��Ƿ��ѡ
	 * 
	 * @return
	 */
	private boolean isMultiSelect()
	{
		boolean flag = false;

		int size = this.tblMain.getSelectManager().size(); // ѡ��Ŀ�

		// �϶���ѡ
		if (size > 1)
		{
			return true;
		}
		else if (size == 1) // �ж��Ƿ���һ�����ж�ѡ��
		{

			KDTSelectBlock sb = this.tblMain.getSelectManager().get(0);
			int startRow = sb.getBeginRow();
			int endRow = sb.getEndRow();

			// ˵������ͬһ��
			if (startRow != endRow)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		else
		{
			return false;
		}

	}

	@Override
	public void actionRelate_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("�뵥ѡ");
			SysUtil.abort();
		}
		super.actionRelate_actionPerformed(e);
	}

	// ����
	@Override
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("�뵥ѡ");
			SysUtil.abort();
		}

		super.btnUpdate_actionPerformed(e);
	}

	// ����
	@Override
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("�뵥ѡ");
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * ����ѡ��ĵ������ƽ�Ȩ�ޣ� ��ѡ����е�FID���浽�����
	 */
	@Override
	public void grantAuthor_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		
		
		
		
		
		ArrayList al = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		for (int i = 0; i < selectRows.length; i++)
		{
			int selectedRow = selectRows[i]; //���ѡ�����
			if(selectedRow==-1)
			{
				
			}else
			{
				Object id = tblMain.getRow(selectedRow).getCell("id").getValue();
				if(id!=null)
				{
					String inviteProjectId = id.toString();
					al.add(inviteProjectId);
				}
			}
		}
		
		//�ж��Ƿ��Ѿ�ѡ������,���δѡ������ʾ��Ϣ
		if(selectRows==null||selectRows.length==0||selectRows[0]==-1)
		{
			MsgBox.showInfo("��ѡ����");
			SysUtil.abort();
		}  
		
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", al);
	    IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(HROpenUI.class.getName(), uiContext, null, OprtState.VIEW);
	    uiWindow.show();
	}

}
