package com.kingdee.eas.fdc.schedule.app;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class AchievementTypeControllerBean extends AbstractAchievementTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.AchievementTypeControllerBean");
    
    protected void _delete(Context arg0, IObjectPK[] arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		Set ids = new HashSet();
		for (int i = 0; i < arg1.length; i++) {
			ids.add(arg1[i]);
		}
		filter.getFilterItems().add(new FilterItemInfo("achievementType.id", ids, CompareType.INCLUDE));
		// �¼���һ���ж��Ƿ񱻳ɹ����������ã���Ϊ�ɹ�������������ֶ�����ҲΪachievementtype�����в���ͭһfilter��
		if (AchievementManagerFactory.getLocalInstance(arg0).exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("100", "ɾ���ĳɹ���𱻽׶��Գɹ����ã�����ɾ����"));
		}
		if (FDCScheduleTaskFactory.getLocalInstance(arg0).exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("100", "ɾ���ĳɹ������ڱ�ר��ƻ����ã�����ɾ����"));
		}
		super._delete(arg0, arg1);
	}
}