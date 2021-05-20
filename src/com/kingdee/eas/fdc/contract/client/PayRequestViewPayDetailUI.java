/*jadclipse*/package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDMenuBar;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractListUI;
import com.kingdee.eas.util.client.EASResource;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import org.apache.log4j.Logger;
public class PayRequestViewPayDetailUI extends AbstractPayRequestViewPayDetailUI
{
            public PayRequestViewPayDetailUI()
                throws Exception
            {
            }
            protected boolean isIgnoreCUFilter()
            {

























/*  59*/        return true;
            }
            protected String getEditUIName()
            {
/*  63*/        return null;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {/*  67*/        return null;
            }
            protected void beforeExcutQuery(EntityViewInfo ev)
            {
/*  71*/        tblMain.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");

/*  73*/        String fdcPayReqID = getUIContext().get("fdcPayReqID").toString();
/*  74*/        Timestamp createTime = (Timestamp)getUIContext().get("createTime");

///*  76*/        EntityViewInfo view = new EntityViewInfo();
///*  77*/        FilterInfo filter = new FilterInfo();
///*  78*/        view.setFilter(filter);
///*  79*/        filter.appendFilterItem("contractId", contractId);
///*  80*/        filter.getFilterItems().add(new FilterItemInfo("createTime", createTime, CompareType.LESS));
//
//
///*  83*/        view.getSelector().add("entrys.paymentBill.id");
//
///*  85*/        Set ids = new HashSet();
//
///*  87*/        try
//                {
///* <-MISALIGNED-> */ /*  87*/            PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);/*  89*/            if(c.size() > 0)
//                    {/*  90*/                for(Iterator it = c.iterator(); it.hasNext();)
//                        {/*  91*/                    PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
///*  92*/                    if(payRequestInfo.getEntrys().size() > 0)
//                            {/*  93*/                        for(int i = 0; i < payRequestInfo.getEntrys().size(); i++)
///*  94*/                            ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId());
//                            }
//                        }
//                    }
//                }
///*  99*/        catch(BOSException e)
//                {/* 100*/            logger.error(e);
///* 101*/            super.handUIException(e);
//                }


/* 105*/       FilterInfo filter = new FilterInfo();
/* 108*/        filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", fdcPayReqID));
/* 112*/        filter.getFilterItems().add(new FilterItemInfo("billStatus", new Integer(15)));



/* 116*/        ev.setFilter(filter);

/* 117*/        super.beforeExcutQuery(ev);
            }
            protected String getKeyFieldName()
            {
/* 121*/        return "";
            }
            protected String getSelectedKeyValue()
            {/* 124*/        return "";
            }
            public void onLoad()
                throws Exception
            {
/* 129*/        tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
                    public void afterDataFill(KDTDataRequestEvent e)
                    {
/* 132*/                appendFootRow();
                    }
        }
);
/* <-MISALIGNED-> */ /* 136*/        super.onLoad();
            }
            protected IRow appendFootRow()
            {
/* <-MISALIGNED-> */ /* 141*/        tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
/* <-MISALIGNED-> */ /* 142*/        IRow footRow = null;
/* <-MISALIGNED-> */ /* 143*/        KDTFootManager footRowManager = tblMain.getFootManager();
/* <-MISALIGNED-> */ /* 144*/        if(footRowManager == null)
                {
/* <-MISALIGNED-> */ /* 146*/            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
/* <-MISALIGNED-> */ /* 147*/            footRowManager = new KDTFootManager(tblMain);
/* <-MISALIGNED-> */ /* 148*/            footRowManager.addFootView();
/* <-MISALIGNED-> */ /* 149*/            tblMain.setFootManager(footRowManager);
/* <-MISALIGNED-> */ /* 150*/            footRow = footRowManager.addFootRow(0);
/* <-MISALIGNED-> */ /* 151*/            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
/* <-MISALIGNED-> */ /* 152*/            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
/* <-MISALIGNED-> */ /* 153*/            tblMain.getIndexColumn().setWidth(30);
/* <-MISALIGNED-> */ /* 154*/            footRowManager.addIndexText(0, total);
                } else
                {
/* <-MISALIGNED-> */ /* 156*/            footRow = footRowManager.getFootRow(0);
                }
/* <-MISALIGNED-> */ /* 158*/        BigDecimal sumAmount = FDCHelper.ZERO;
/* <-MISALIGNED-> */ /* 159*/        for(int i = 0; i < tblMain.getRowCount(); i++)
                {
/* <-MISALIGNED-> */ /* 160*/            BigDecimal add = FDCHelper.ZERO;
/* <-MISALIGNED-> */ /* 161*/            if(tblMain.getRow(i).getCell("amount").getValue() instanceof BigDecimal)
/* <-MISALIGNED-> */ /* 162*/                add = (BigDecimal)tblMain.getRow(i).getCell("amount").getValue();
/* <-MISALIGNED-> */ /* 163*/            sumAmount = sumAmount.add(add);
                }
/* <-MISALIGNED-> */ /* 165*/        String colFormat = "%{0.##########}f";
/* <-MISALIGNED-> */ /* 168*/        ICell cell = footRow.getCell("amount");
/* <-MISALIGNED-> */ /* 169*/        cell.getStyleAttributes().setNumberFormat(colFormat);
/* <-MISALIGNED-> */ /* 170*/        cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
/* <-MISALIGNED-> */ /* 171*/        cell.getStyleAttributes().setFontColor(Color.BLACK);
/* <-MISALIGNED-> */ /* 173*/        cell.setValue(sumAmount);
/* <-MISALIGNED-> */ /* 174*/        footRow.getStyleAttributes().setBackground(new Color(246, 246, 191));
/* <-MISALIGNED-> */ /* 175*/        FDCClientUtils.fmtFootNumber(tblMain, new String[] {
/* <-MISALIGNED-> */ /* 175*/            "amount"
                });
/* <-MISALIGNED-> */ /* 176*/        return footRow;
            }
            protected void initWorkButton()
            {
/* <-MISALIGNED-> */ /* 180*/        super.initWorkButton();
/* <-MISALIGNED-> */ /* 181*/        actionAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 182*/        actionAddNew.setVisible(false);
/* <-MISALIGNED-> */ /* 183*/        actionEdit.setEnabled(false);
/* <-MISALIGNED-> */ /* 184*/        actionEdit.setVisible(false);
/* <-MISALIGNED-> */ /* 185*/        actionView.setEnabled(false);
/* <-MISALIGNED-> */ /* 186*/        actionView.setVisible(false);
/* <-MISALIGNED-> */ /* 188*/        actionRemove.setEnabled(false);
/* <-MISALIGNED-> */ /* 189*/        actionRemove.setVisible(false);
/* <-MISALIGNED-> */ /* 191*/        actionPrint.setEnabled(false);
/* <-MISALIGNED-> */ /* 192*/        actionPrint.setVisible(false);
/* <-MISALIGNED-> */ /* 193*/        actionPrintPreview.setEnabled(false);
/* <-MISALIGNED-> */ /* 194*/        actionPrintPreview.setVisible(false);
/* <-MISALIGNED-> */ /* 196*/        actionLocate.setEnabled(false);
/* <-MISALIGNED-> */ /* 197*/        actionLocate.setVisible(false);
/* <-MISALIGNED-> */ /* 199*/        actionQuery.setEnabled(false);
/* <-MISALIGNED-> */ /* 200*/        actionQuery.setVisible(false);
/* <-MISALIGNED-> */ /* 201*/        menuBar.setVisible(false);
/* <-MISALIGNED-> */ /* 202*/        toolBar.setVisible(true);
            }
            protected boolean isFootVisible()
            {
/* <-MISALIGNED-> */ /* 206*/        return true;
            }
            private static final long serialVersionUID = -3550410046609038209L;
            private static final Logger logger = CoreUIObject.getLogger(PayRequestViewPayDetailUI.class.getName());
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 121 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/