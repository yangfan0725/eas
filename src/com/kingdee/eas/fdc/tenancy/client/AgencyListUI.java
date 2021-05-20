/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.tenancy.AgencyContractFactory;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgencyListUI extends AbstractAgencyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgencyListUI.class);
    
    /**
     * output class constructor
     */
    public AgencyListUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_inputoutput"));//����ģ�嵼������
		this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_emend"));//�޸ĵ�������
	}


	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionImport_actionPerformed(e);
		String strSolutionName ="eas.fdc.tenancy.TenancyImport";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		String solutionName = strSolutionName;
		param.solutionName = solutionName;
		param.alias = kDWorkButton1.getText();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, 0, true);
	}

	public void actionTenancySql_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionTenancySql_actionPerformed(e);
		if(TenancyImport.tenancyUpdata()){
			MsgBox.showInfo("�޸������ͬ�����ѳɹ�");
		}else{
			MsgBox.showInfo("û��Ҫ�޸��ĵ����ͬ");
		}
//		super.actionRefresh_actionPerformed(e);
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected String getEditUIName() {
		return AgencyEditUI.class.getName();
	}
    
    protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.AgencyFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.AgencyInfo objectValue = new com.kingdee.eas.fdc.tenancy.AgencyInfo();
		
        return objectValue;
    }
    
    protected String getLongNumberFieldName() {
		return "number";
	}
    
    /**
     * ɾȥǰ��Ҫ����Ƿ����н��ͬ���� xin_wang 2010.11.15 
     */
   public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
	   String pk = getSelectedKeyValue();
	   FilterInfo filter = new FilterInfo();
	   filter.getFilterItems().add(new FilterItemInfo("agency.id",pk));
	   if(AgencyContractFactory.getRemoteInstance().exists(filter)){
		   MsgBox.showInfo("������¼���н��ͬ���ò���ɾ����");
		   this.abort();
	   }
	   super.actionRemove_actionPerformed(e);
}
}