package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;

public interface IRevDistillFace {

	//�жϼ��ᵥ�Ƿ��Ѿ��Ը��տ��Ӷ��¼����
	boolean isAlreadyDistill(Context ctx,ReceiveDistillCommisionEntryInfo recDistillEntryInfo)throws BOSException;
}
