package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.VisualItemInfo;

/**
 * ������������Ԥ����������BG056�ʽ�ҵ�񵥾�ʹ����Ŀ����ʾԤ����Ŀ���Ǽƻ���Ŀ�ĸ�����
 * 
 * @author cassiel 2010-09-30
 */
public class FDCDealFPOrBudgetItemUtil {
	/**
	 * ����:����false ������ʾԤ����Ŀ ������ʾ�ƻ���Ŀ
	 */
	public static boolean getIsFpOrBg() {
		String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, null, "BG056");
			if (FMHelper.isEmpty(paramValue) || ("0").equals(paramValue)) {
				return false;
			} else {
				return true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static FDCBgItemProp getBgItemProps() {
		return new FDCBgItemProp("outBgItemId", "outBgItemNumber",
				"outBgItemName");
	}

	public static void loadFieldsBgItem(KDBizPromptBox prmt, CoreBaseInfo info,
			FDCBgItemProp bgItemProp) {
		String valueBgItemID = info.getString(bgItemProp.getBgItemIDProp());
		String valueBgItemNumber = info.getString(bgItemProp
				.getBgItemNumberProp());
		String valueBgItemName = info.getString(bgItemProp.getBgItemNameProp());
		if (valueBgItemNumber == null) {
			prmt.setValue(null);
			return;
		} else {
			VisualItemInfo bgItem = new VisualItemInfo();
			BOSUuid uuId = valueBgItemID == null ? null : BOSUuid
					.read(valueBgItemID);
			bgItem.setId(uuId);
			bgItem.setNumber(valueBgItemNumber);
			bgItem.setName(valueBgItemName);
			prmt.setValue(bgItem);
		}
	}

	/** Ԥ��ؼ���info��info��������:Ԥ����ĿID,Ԥ����ĿNumber,Ԥ����Ŀname */

	public static void storeFieldsBgItem(KDBizPromptBox prmt,
			CoreBaseInfo info, FDCBgItemProp bgItemProp) {
		if (prmt.getValue() == null) {
			info.setString(bgItemProp.getBgItemIDProp(), null);
			info.setString(bgItemProp.getBgItemNumberProp(), null);
			info.setString(bgItemProp.getBgItemNameProp(), null);
		} else {
			VisualItemInfo bgItem = (VisualItemInfo) prmt.getValue();
			String bgItemId = bgItem.getId() == null ? null : bgItem.getId()
					.toString();
			info.setString(bgItemProp.getBgItemIDProp(), bgItemId);
			info
					.setString(bgItemProp.getBgItemNumberProp(), bgItem
							.getNumber());
			info.setString(bgItemProp.getBgItemNameProp(), bgItem.getName());
		}
	}
}
