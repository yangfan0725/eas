package com.kingdee.eas.fi.cas.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.core.fm.ContextHelperFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.fi.cas.IJournal;
import com.kingdee.eas.fi.cas.JournalFactory;
import com.kingdee.eas.fi.cas.JournalSourceEnum;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class BankJournalListUICTEx extends BankJournalListUI{

	public BankJournalListUICTEx() throws Exception {
		super();
	}

	@Override
	public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
		if(tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager() == null)
			/* <-MISALIGNED-> */ /* 614*/            return;
			/* 617*/        if(MsgBox.isCancel(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete"))))



			/* 621*/            return;


			/* 624*/        java.util.List ids = new ArrayList();
			/* 625*/        KDTSelectBlock selectBlock = null;
			/* 626*/        IRow row = null;
			/* 627*/        for(int i = 0; i < tblMain.getSelectManager().size(); i++)
			                {/* 628*/            selectBlock = tblMain.getSelectManager().get(i);
			/* 629*/            for(int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++)
			                    {/* 630*/                row = tblMain.getRow(j);
			/* 631*/                String id = (String)row.getCell("id").getValue();
			/* 632*/                if(id == null)
			                        {/* 633*/                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fi.cas.client.CashMgtResource", "SelectValidRecord"));

			/* 635*/                    return;
			                        }
			/* 637*/                JournalSourceEnum source = (JournalSourceEnum)row.getCell("source").getValue();

			/* 639*/                if(source == JournalSourceEnum.BYBILL || source == JournalSourceEnum.BYVOUCHER)
			                        {
//			/* 641*/                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fi.cas.client.CashMgtResource", "BillOrVoucherBook"));
//
//			/* 643*/                    return;
			                        }

			/* 646*/                PeriodInfo currPeriodInfo = SystemStatusCtrolUtils.getCurrentPeriod(null, SystemEnum.CASHMANAGEMENT, ContextHelperFactory.getRemoteInstance().getCurrentCompany());


			/* 649*/                int currYear = currPeriodInfo.getPeriodYear();
			/* 650*/                int currPeriodNumber = currPeriodInfo.getPeriodNumber();

			/* 652*/                Integer year = (Integer)(Integer)tblMain.getRow(j).getCell("periodYear").getValue();

			/* 654*/                Integer period = (Integer)(Integer)tblMain.getRow(j).getCell("periodNumber").getValue();

			/* 656*/                if(year.intValue() < currYear || year.intValue() == currYear && period.intValue() < currPeriodNumber)
			                        {
			/* 658*/                    String stru = EASResource.getString("com.kingdee.eas.fi.cas.client.CashMgtResource", "cm79");

			/* 660*/                    MsgBox.showInfo(this, stru);
			/* 661*/                    return;
			                        }
			/* 663*/                ids.add(id);
			                    }
			                }
			/* 666*/        for(int i = 0; i < ids.size(); i++)
			                {/* 667*/            String id = (String)ids.get(i);
			/* 668*/            IJournal iJournal = JournalFactory.getRemoteInstance();
			/* 669*/            iJournal.delete(new ObjectUuidPK(BOSUuid.read(id)));
			                }
			/* 671*/        fillData();
			/* 672*/        String strDeleteSuccessfully = EASResource.getString("com.kingdee.eas.fi.cas.client.CashMgtResource", "cm44");


			/* 675*/        MsgBox.showInfo(this, strDeleteSuccessfully);
	}

}
