package com.kingdee.eas.fdc.schedule.client;

import java.io.File;
import java.util.Map;

import javax.swing.JFileChooser;

import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.gl.ExtensionFileFilter;

public class FileUtil {
	/**
	 * ������ͨ���ļ��Ի���õ��ļ�
	 * 
	 * @throws EASBizException
	 */
	public static String getSelectFile(CoreUIObject ui,boolean isSave,String ext) throws Exception {
		// ���·���Ƿ����
		KDFileChooser chooser = new KDFileChooser(new File("."));

		// ����ѡ��Ի���ġ��������͡�Ϊ"ȫ��"
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*."+ext));
		//chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*.*"));

		int returnVal = 0;
		if(isSave){
			chooser.setDialogTitle("����Ϊ");
			returnVal = chooser.showSaveDialog(ui);
		}else{
			chooser.setDialogTitle("ѡ���ļ�");
			returnVal = chooser.showOpenDialog(ui);
		}
		String strFullPath = null;
		/*
		 * JFileChooser.APPROVE_OPTION Ϊ 0
		 * 
		 */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// ��ȡ�ļ�
			File file = chooser.getSelectedFile();
			strFullPath = file.getPath();
			
		}

		if(isSave && strFullPath != null && !strFullPath.endsWith("."+ext)){
			return strFullPath+"."+ext;
		}else{
			return strFullPath;
		}
	}
	
	
	public static File[] getSelectFiles(CoreUIObject ui, boolean isSave, String ext) throws Exception {
		// ���·���Ƿ����
		KDFileChooser chooser = new KDFileChooser(new File("."));
		chooser.setMultiSelectionEnabled(true);

		// ����ѡ��Ի���ġ��������͡�Ϊ"ȫ��"
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*." + ext));
		// chooser.addChoosableFileFilter(new ExtensionFileFilter(ext, "*.*"));

		int returnVal = 0;
		if (isSave) {
			chooser.setDialogTitle("����Ϊ");
			returnVal = chooser.showSaveDialog(ui);
		} else {
			chooser.setDialogTitle("ѡ���ļ�");
			returnVal = chooser.showOpenDialog(ui);
		}
		String strFullPath = null;
		/*
		 * JFileChooser.APPROVE_OPTION Ϊ 0
		 */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// ��ȡ�ļ�
			File[] files = chooser.getSelectedFiles();
			return files;
		}
		
		return null;

		// if (isSave && strFullPath != null && !strFullPath.endsWith("." +
		// ext)) {
		// return strFullPath + "." + ext;
		// } else {
		// return strFullPath;
		// }
	}
	
	//��Ӹ�������UI
	public static KDPanel getAttachmentUI(CoreUIObject owner,Map uiContext,IObjectValue editData,String title){
        uiContext.put("owner",owner);
        uiContext.put("editData",editData);
        
		// ����UI������ʾ
		IUIWindow uiWindow = null;
		AttachmentUI ui = null;
		try {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(AttachmentUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			
			ui =(AttachmentUI)uiWindow.getUIObject();    	
	    	ui.setTitle(title);
		} catch (UIException e) {
			e.printStackTrace();
			owner.handUIException(e);
		}

    	return ui;
	}
}
