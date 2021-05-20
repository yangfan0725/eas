package com.kingdee.eas.fdc.basecrm.client;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.fdc.customerservice.client.FDCStdQuestionPromptBox;

public class FDCCommonPromptBox extends KDCommonPromptDialog {
	
	private static final Logger logger = Logger.getLogger(FDCStdQuestionPromptBox.class);	
	protected IUIWindow currCommonUI;
	
    private CoreUI thisUserUI;
    
    private UIContext uiContext = new UIContext(this);
    private String thisClassName;
    
	
    public FDCCommonPromptBox(String uiClassName){
    	thisClassName = uiClassName;
        uiContext.put(UIContext.OWNER,this);
    }
    
    public FDCCommonPromptBox(String uiClassName,Object owner){
    	thisClassName = uiClassName;
        uiContext.put(UIContext.OWNER,this);
        
        uiContext.put("ParentObject", owner);
    }    
    
    
    /**
     * ����F7�ؼ�ʱ��������
     */
    public void show()
    {
        IUIFactory uiFactory = null;
        try
        {            
            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
            currCommonUI = uiFactory.create(thisClassName,uiContext);
            thisUserUI = ((CoreUI)this.currCommonUI.getUIObject());
            currCommonUI.show();
        }
        catch (BOSException ex)
        {
        	logger.error(ex.getMessage());
        }
    }
    
    /**
     * ��ȡ�û�ѡȡ������
     */
    public Object getData()
    {   
    	//һ��Ҫ��������RetObi�з���ѡ��Ķ���    //����ֵΪ��ʱ����ȡ������
    	Map retMap = thisUserUI.getUIContext();
    	Object retObj = retMap.get("RetObj");
    	Object[] objs = new Object[1];
    	objs[0] = retObj;    	
        return objs;
    }
    
    public boolean isCanceled() {//����ֵΪ��ʱ����ȡ������
    	return thisUserUI.getUIContext().get("RetObj")==null?true:false;
    }


}
