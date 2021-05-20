package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * ѡ����Ŀ��������F7�Ի���
 * @author youzhen_qin
 *
 */
public final class FDCSellProjectDialog extends KDCommonPromptDialog
{
	protected IUIWindow classDlg = null;
	protected boolean isRefresh = false;
	/**
	 * �Ƿ��ѡ��
	 */
	private Boolean isMultiSelect = Boolean.FALSE;

	/**
	 * ����ϵͳ
	 *
	 */
	private MoneySysTypeEnum moneySysTypeEnum;
	

	public FDCSellProjectDialog()
	{
	}
	
	public FDCSellProjectDialog(Boolean isMultiSelect, MoneySysTypeEnum moneySysTypeEnum)
	{
		this.isMultiSelect = isMultiSelect;
		this.moneySysTypeEnum = moneySysTypeEnum;
	}


	public String getUITitle()
	{
		return new String("ѡ������Ŀ");
	}

	public void show()
	{

		Map map = new HashMap();
		
		map.put("isMultiSelect",this.isMultiSelect);
		map.put("moneySysTypeEnum",this.moneySysTypeEnum);
		IUIFactory uiFactory = null;
		try
		{

			if (isRefresh || classDlg == null)
			{
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(FDCSellProjectSelectUI.class.getName(),map,null,"VIEW");
			}
			//ȷ��ÿ����ʾisCanceled=true
			if (classDlg != null && classDlg.getUIObject() != null)
			{
				((FDCSellProjectSelectUI)classDlg.getUIObject()).isCanceled=true;
			}
			classDlg.show();
		} catch (BOSException ex)
		{
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	/**
	 * �ж��Ƿ���ȡ����ť�������Ƿ����ȡֵ����
	 * 
	 */
	public boolean isCanceled()
	{

        if(classDlg!=null)
        {
            return((FDCSellProjectSelectUI)classDlg.getUIObject()).isCanceled ;
        }
        else
        {
            return true;
        }
	}

	/**
	 * ����ֵ
	 */
	public Object getData()
	{
		FDCSellProjectSelectUI ui = (FDCSellProjectSelectUI) classDlg.getUIObject();
		Object[] returnValue;
		try
		{
			//�����ѡ
			if(this.isMultiSelect.booleanValue())
			{
				return ui.getReturnValueArray();
			}
			//��ѡ
			else
			{
				SellProjectInfo tempProject = (SellProjectInfo) ui.getReturnValue();
				if(tempProject != null)
				{
					returnValue = new Object[]{tempProject};
				}
				else
				{
					return null;
				}
			}
		} catch (Exception err)
		{
			return null;
		}
		return returnValue;
	}
}
