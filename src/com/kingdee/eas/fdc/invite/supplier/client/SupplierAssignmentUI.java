/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.framework.IDataBaseD;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;

/**
 * output class name
 */
public class SupplierAssignmentUI extends AbstractSupplierAssignmentUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierAssignmentUI.class);
    
    /**
     * output class constructor
     */
    public SupplierAssignmentUI() throws Exception
    {
        super();
    }
    private CtrlUnitInfo curCtrlUnitInfo;
    
    public void onLoad() throws Exception {
    	curCtrlUnitInfo = (CtrlUnitInfo)getUIContext().get("CurrentCtrlUnit");
    	if(curCtrlUnitInfo == null){
    		curCtrlUnitInfo = SysContext.getSysContext().getCurrentCtrlUnit();
    	}
    	super.onLoad();
    	//ID��
    	//CU.ID��
    	tblData.getColumn(13).getStyleAttributes().setHided(true);
    	tblData.getColumn(14).getStyleAttributes().setHided(true);
    }

    
    //ͨ�������ݹ�Ӧ�̽ӿڵ���(DataBaseD)��������Զ�̽ӿ� ʹ�����еķ��乩Ӧ�̷���
    protected IDataBaseD getBizInterfaceFromUIContext(){
    	try {
			return (IDataBaseD)SupplierFactory.getRemoteInstance();
		} catch (BOSException e) {
			e.printStackTrace();
			return null;
		}
	}
}