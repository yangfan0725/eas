package com.kingdee.eas.framework.client.service;

import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.base.license.LicenseException;
import com.kingdee.eas.base.license.LicenseUserInfo;
import com.kingdee.eas.base.license.client.LicenseController;
import com.kingdee.eas.base.license.client.monitor.LicenseClientUtil;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import java.util.*;
import org.apache.log4j.Logger;

public class LicenseService {
	public LicenseService(IUIObject ui) {

		logger = Logger
				.getLogger(com.kingdee.eas.framework.client.service.LicenseService.class);
		hasLicence = false;

		this.ui = ui;
	}

	public void checkLicence() throws Exception {
		
	}

	public void releaseLicense() throws Exception {
		
	}

	private String getUIClassName(IUIObject uiObject) {

		String className = null;
		if (uiObject.getMetaDataPK() != null)
			className = uiObject.getMetaDataPK().getFullName();
		else
			className = uiObject.getClass().getName();
		return className;
	}

	private boolean needCheck(LicenseController lc, String fullClassName)
			throws LicenseException {
		return false;
	}

	private String constructMessage(LicenseController lc, String className)
			throws LicenseException {

		if (lc == null || className == null) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ClassName"));
			buffer.append((new StringBuilder()).append(ui.getUITitle()).append("[").append(getUIClassName(ui)).append("]").toString());
			buffer.append("\n");
			buffer.append(constructLicenseMessage(lc, className));
			return buffer.toString();
		}
	}

	private final String getPackageNameForClass(Class cls) {

		String packageName = getUIClassName(ui);
		return packageName.substring(0,	packageName.lastIndexOf(".") + 1);
	}

	private void abort() {
		((CoreUI) ui).setCursorOfDefault();
		 SysUtil.abort();
	}

	public static final List getLicenseRecordList() {
		return licenseRecordList;
	}

	public static final String constructLicenseMessage(LicenseController lc,
			String className) throws LicenseException {

		if (lc == null || className == null)
			return "";

		StringBuffer buffer = new StringBuffer();
		buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_ModuleName"));
		String moduleName = lc.getModuleByPackage(className);
		if (moduleName == null || moduleName.trim().length() == 0)
			buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));
		else
			buffer.append((new StringBuilder()).append(LicenseClientUtil.getModularAliasNameByModularName(moduleName)).append("[").append(moduleName).append("]").toString());
		buffer.append("\n");
		buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_SubSystem"));
		String subSysName = LicenseClientUtil.getSubSystemNameByModularName(moduleName);
		if (subSysName == null || subSysName.trim().length() == 0)
			buffer.append(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_UnlimitUI"));
		else
			buffer.append((new StringBuilder()).append(LicenseClientUtil.getModularAliasNameByModularName(subSysName)).append("[").append(subSysName).append("]").toString());
		buffer.append("\n");
		return buffer.toString();
	}

	private Logger logger;
	private boolean hasLicence;
	private IUIObject ui;
	private static List licenseRecordList = new ArrayList();
}

