package com.kingdee.eas.fi.gl.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.framework.cache.ICacheService;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.service.message.Message;
import com.kingdee.bos.service.message.agent.MessageSender;
import com.kingdee.bos.service.message.agent.Sender;
import com.kingdee.bos.service.message.agent.SenderAgent;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.netctrl.MutexParameter;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.LicenseHelper;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolCollection;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusInfo;
import com.kingdee.eas.basedata.common.perf.CreateJobFacadeFactory;
import com.kingdee.eas.basedata.common.perf.ICreateJobFacade;
import com.kingdee.eas.basedata.common.perf.JobConfig;
import com.kingdee.eas.basedata.master.account.AccountRefContrastFactory;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.IAccountRefContrast;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fi.gl.AcctCussentHistoryFactory;
import com.kingdee.eas.fi.gl.AutoAssistantHGUpdateFacadeFactory;
import com.kingdee.eas.fi.gl.GLFlagCollection;
import com.kingdee.eas.fi.gl.GLFlagFactory;
import com.kingdee.eas.fi.gl.GLFlagInfo;
import com.kingdee.eas.fi.gl.GLGeneralFacadeFactory;
import com.kingdee.eas.fi.gl.GLPeriodEndFacadeFactory;
import com.kingdee.eas.fi.gl.GLPeriodEndFilter;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.IAcctCussentHistory;
import com.kingdee.eas.fi.gl.IAutoAssistantHGUpdateFacade;
import com.kingdee.eas.fi.gl.IGLFlag;
import com.kingdee.eas.fi.gl.IGLGeneralFacade;
import com.kingdee.eas.fi.gl.IGLPeriodEndFacade;
import com.kingdee.eas.fi.gl.IPeriodTransFacade;
import com.kingdee.eas.fi.gl.IVchAmortScheme;
import com.kingdee.eas.fi.gl.PeriodTransFacadeFactory;
import com.kingdee.eas.fi.gl.PostVoucherCheckReturn;
import com.kingdee.eas.fi.gl.VchAmortSchemeFactory;
import com.kingdee.eas.fi.gl.VchAmortUtils;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.fi.gr.cslrpt.client.CslRptUIUtil;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import org.apache.log4j.Logger;

public class ClosePeriodUI
  extends AbstractClosePeriodUI
{
  public static final String ACTIONKEY_UPLongame = "UPLongame";
  public static final String ACTIONKEY_HG = "HG";
  public static final String ACTIONKEY_SQL = "SQL";
  public static final String ACTIONKEY_UPDATE = "UPDATE";
  public static final String ACTIONKEY_CLEAR_BALANCE = "CLEAR_BALANCE";
  public static final String ACTIONKEY_UPDATEHISTORYDATD_INTIMEVERIFY = "UPDATEHISTORYDATD_INTIMEVERIFY";
  protected CompanyOrgUnitInfo curCompany;
  protected UserInfo curUser;
  protected PeriodInfo curPeriod;
  private IGLPeriodEndFacade periodEndCtrl;
  private GLPeriodEndFilter filter;
  private int iSuccess = 0;
  public static final String RESOURCE_PATH = "com.kingdee.eas.fi.gl.GLPeriodEndResource";
  public static final String INITRESOURCE_PATH = "com.kingdee.eas.fi.gl.InitResource";
  private static final Logger logger = CoreUIObject.getLogger(ClosePeriodUI.class);
  
  public ClosePeriodUI()
    throws Exception
  {
    jbinit();
  }
  
  private void jbinit() {}
  
  public void onLoad()
    throws Exception
  {
    boolean isFromTool = "isFromTool".equals(getUIContext().get("UIClassParam"));
    if (isFromTool)
    {
      handlePermissionForItemAction(this.checkFormTool);
      updateLoname();
      SysUtil.abort();
    }
    else
    {
      RptClientUtil.checkFIUnit(false);
    }
    super.onLoad();
    
    fetchInitData();
//    if ((this.curPeriod.getPeriodNumber() == 10) && (!LicenseHelper.keyPointLicenseCheck())) {
//      SysUtil.abort();
//    }
    initTableStyle();
    loadFields();
    this.isQueue.setEnabled(false);
    this.isQueue.setVisible(false);
  }
  
  protected void prepareMutexParameter(ActionEvent e, MutexParameter param)
  {
    param.setOrgUnitID(SysContext.getSysContext().getCurrentFIUnit().getId());
  }
  
  protected void initTableStyle()
  {
    InputMap imEntry = getInputMap(2);
    
    KeyStroke ctrl_shift_f08 = KeyStroke.getKeyStroke(119, 3);
    imEntry.put(ctrl_shift_f08, "HG");
    
    KeyStroke ctrl_shift_f7 = KeyStroke.getKeyStroke(118, 3);
    imEntry.put(ctrl_shift_f7, "UPLongame");
    
    KeyStroke ctrl_shift_f12 = KeyStroke.getKeyStroke(122, 3);
    imEntry.put(ctrl_shift_f12, "SQL");
    
    KeyStroke ctrl_shift_f9 = KeyStroke.getKeyStroke(120, 3);
    imEntry.put(ctrl_shift_f9, "UPDATE");
    
    KeyStroke ctrl_shift_f10 = KeyStroke.getKeyStroke(121, 3);
    imEntry.put(ctrl_shift_f10, "CLEAR_BALANCE");
    
    KeyStroke ctrl_shift_f6 = KeyStroke.getKeyStroke(117, 3);
    imEntry.put(ctrl_shift_f6, "UPDATEHISTORYDATD_INTIMEVERIFY");
  }
  
  public void loadFields()
  {
    detachListeners();
    super.loadFields();
    this.kdlblPic.setIcon(EASResource.getIcon("imgGuide_pic338"));
    
    String strMsg = this.curCompany.getNumber() + this.curCompany.getName();
    String period = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "currentPeriodMsg");
    strMsg = strMsg.concat(period);
    strMsg = strMsg.concat(":");
    
    String periodFormat = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "periodformat");
    MessageFormat mf = new MessageFormat(periodFormat);
    String txtPeriod = mf.format(new String[] { Integer.toString(this.curPeriod.getPeriodYear()), Integer.toString(this.curPeriod.getPeriodNumber()) });
    strMsg = strMsg.concat(txtPeriod);
    this.txtaResult.setText(strMsg);
    attachListeners();
  }
  
  public void storeFields()
  {
    super.storeFields();
  }
  
  public void actionCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    destroyWindow();
  }
  
  public void actionOK_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.iSuccess == 0) {
      doOK();
    } else {
      destroyWindow();
    }
  }
  
  public void actionOK2_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.iSuccess == 0) {
      doOK();
    } else {
      destroyWindow();
    }
  }
  
  public void actionYearOk_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.iSuccess == 0) {
      doOK();
    } else {
      destroyWindow();
    }
  }
  
  public void actionYearOk2_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.iSuccess == 0) {
      doOK();
    } else {
      destroyWindow();
    }
  }
  
  protected void rbClosePeriod_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.btnOK2.setVisible(false);
    this.btnOK.setVisible(true);
    
    this.btnOK.setEnabled(true);
    this.btnyearOK.setVisible(false);
    this.btnyearOK2.setVisible(false);
  }
  
  protected void rbUnClosePeriod_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.btnOK.setVisible(false);
    this.btnOK2.setVisible(true);
    this.btnOK2.setAction((IItemAction)ActionProxyFactory.getProxy(this.actionOK2, new Class[] { IItemAction.class }, getServiceContext()));
    this.btnOK2.setEnabled(true);
    this.btnOK2.setText(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "btnOKText2"));
  }
  
  protected void rbCloseYear_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.btnOK2.setVisible(false);
    this.btnOK.setVisible(false);
    
    this.btnyearOK.setVisible(true);
    this.btnyearOK.setEnabled(true);
    this.btnyearOK2.setVisible(false);
  }
  
  protected void rbUnCloseYear_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.btnOK2.setVisible(false);
    this.btnOK.setVisible(false);
    
    this.btnyearOK.setVisible(false);
    this.btnyearOK2.setEnabled(true);
    this.btnyearOK2.setVisible(true);
  }
  
  private void doOK()
    throws Exception
  {
    this.btnOK.setEnabled(false);
    
    this.btnOK2.setEnabled(false);
    this.btnyearOK.setEnabled(false);
    this.btnyearOK2.setEnabled(false);
    
    setCursorOfWair();
    GlUtils.updateBalance();
    if ((this.rbCloseYear.isSelected()) || (this.rbUnCloseYear.isSelected()))
    {
      int iClose = this.rbCloseYear.isSelected() ? 1 : 0;
      
      doYearOK(iClose);
    }
    else
    {
      int iClose = this.rbClosePeriod.isSelected() ? 1 : 0;
      doMonthOK(iClose, true);
    }
    setBtnOKOK();
    setCursorOfDefault();
  }
  
  private void doYearOK(int iClose)
    throws Exception
  {
    IPeriodTransFacade transInterface = PeriodTransFacadeFactory.getRemoteInstance();
    if ((this.filter.isCanNotClosedAfterReport()) && 
      (iClose == 0) && 
      (this.curPeriod != null) && (this.curCompany.getId() != null))
    {
      PeriodInfo prevAdjustPeriod = PeriodUtils.getPreAdjPeriodInfo(null, this.curPeriod);
      PeriodInfo newPeriodInfo = (PeriodInfo)this.curPeriod.clone();
      if (prevAdjustPeriod == null)
      {
        newPeriodInfo.setPeriodNumber(1);
      }
      else
      {
        newPeriodInfo = (PeriodInfo)prevAdjustPeriod.clone();
        newPeriodInfo.setPeriodNumber(newPeriodInfo.getPeriodNumber() + 1);
      }
      String errInfo = CslRptUIUtil.isHasReportSubmited(newPeriodInfo, this.curCompany.getId().toString());
      if (errInfo != null)
      {
        String errClosePeriod = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "errUnclosePeriod");
        MsgBox.showError(errClosePeriod + " " + errInfo);
        return;
      }
    }
    if (iClose == 1)
    {
      boolean post = true;
      String type = "2";
      
      transInterface.checkCanClosePeriod(iClose);
      if (this.curPeriod.isIsAdjustPeriod())
      {
        doMonthOK(iClose, false);
        post = false;
        type = "3";
      }
      else if (!this.filter.isParamCheckVoucherTemp())
      {
        Map param = new HashMap();
        param.put("period", this.curPeriod);
        param.put("isMonth", Boolean.valueOf(false));
        Map rtValue = GLGeneralFacadeFactory.getRemoteInstance().dealTask("CheckTempVocuher", param);
        if (rtValue != null)
        {
          String strConfirm = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "PARAM_CHECKVOUCHERTEMP");
          
          String message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "ColsePeriodNotValid");
          MsgBox.showDetailAndOK(this, message, strConfirm, 2);
          
          setCursorOfDefault();
          return;
        }
      }
      boolean enter = true;
      if (!closePeriod(enter, post, type))
      {
        destroyWindow();
        return;
      }
    }
    else
    {
      JobConfig jobCfg = creteJobCfg();
      if (CreateJobFacadeFactory.getRemoteInstance().checkDuplicateTask(jobCfg.getTaskType()))
      {
        String message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GLPeriodEndIsInQueue");
        MsgBox.showWarning(message);
        return;
      }
      transInterface.unClosePeriod();
    }
    String succClosePeriod = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", iClose == 1 ? "YearCloseSuccess" : "YearUnCloseSuccess");
    
    this.txtaResult.setText("");
    
    PeriodInfo period = null;
    if (iClose == 1) {
      period = this.curPeriod;
    } else {
      period = PeriodUtils.getPreAdjPeriodInfo(null, this.curPeriod);
    }
    String strMsg = null;
    if (period != null)
    {
      strMsg = String.valueOf(new Integer(period.getPeriodYear()).toString());
      this.txtaResult.setText(strMsg);
      
      this.txtaResult.append(succClosePeriod);
    }
    else
    {
      this.txtaResult.setText(strMsg);
    }
  }
  
  private void doMonthOK(int iClose, boolean isMonth)
    throws Exception
  {
    int result = 0;
    
    int confirm = 0;
    PostVoucherCheckReturn[] checkReturn = null;
    
    int hasVoucherUnCommit = 0;
    
    this.txtaResult.setText("");
    
    String message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "ColsePeriodNotValid");
    
    GLPeriodEndFilter gfilter = getBizInterface().fetchInitData();
    PeriodInfo serverCurPeriod = gfilter.getCurPeriod();
    if (!serverCurPeriod.getId().toString().equals(this.curPeriod.getId().toString()))
    {
      String strConfirm = EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "CurPeriodON");
      
      MsgBox.showDetailAndOK(this, message, StringUtils.replace(strConfirm, "{1}", serverCurPeriod.toString()), 2);
      return;
    }
    checkReturn = getBizInterface().checkCanClosePeriod(iClose);
    
    SystemStatusCtrolCollection ctrols = SystemStatusCtrolFactory.getRemoteInstance().getSystemStatusCtrolCollection("select acPeriod.* where company.id='" + this.curCompany.getId() + "' and systemStatus.name=6");
    
    PeriodInfo acPeriod = (ctrols.size() > 0) && (ctrols.get(0).getAcPeriod() != null) ? ctrols.get(0).getAcPeriod() : null;
    if (iClose == 1)
    {
      if ((checkReturn != null) && 
        (checkReturn.length > 0))
      {
        int iLenth = checkReturn.length;
        
        StringBuffer errStr = new StringBuffer();
        errStr.append(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "closePeriodErr"));
        errStr.append("\r\n");
        String balanceError = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "balanceNotCorrect");
        for (int i = 0; i < iLenth; i++)
        {
          if ((i == 0) && 
            (checkReturn[i].getHasVoucherUnCommit() == 1))
          {
            hasVoucherUnCommit = 1;
            break;
          }
          if (checkReturn[i].isBalanceError())
          {
            errStr.append(checkReturn[i].getAccountNumber());
            errStr.append(" ");
            errStr.append(checkReturn[i].getAccountName());
            errStr.append(" ");
            errStr.append(checkReturn[i].getCurrencyName());
            errStr.append(" ");
            errStr.append(balanceError);
            errStr.append("\r\n");
          }
          else
          {
            errStr.append(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherNumber"));
            
            errStr.append(checkReturn[i].getVoucherNumber().toString());
            errStr.append(" ");
            if (checkReturn[i].getVeSeq() != -1)
            {
              errStr.append(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherEntrySeq"));
              errStr.append(checkReturn[i].getVeSeq());
              errStr.append(" ");
              if (checkReturn[i].getAsstSeq() != -1)
              {
                errStr.append(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherAssistSeq"));
                
                errStr.append(checkReturn[i].getAsstSeq());
                errStr.append(" ");
              }
            }
            errStr.append(checkReturn[0].getErrorTip());
            errStr.append("\r\n");
          }
        }
        if (hasVoucherUnCommit == 0)
        {
          this.txtaResult.append(errStr.toString());
          setCursorOfDefault();
          return;
        }
      }
      if (this.filter.getParamCheckNumber() != 0)
      {
        int checkNumber = this.filter.getParamCheckNumber();
        
        Map param = new HashMap();
        param.put("period", this.curPeriod);
        Map rtValue = GLGeneralFacadeFactory.getRemoteInstance().dealTask("CheckNumberInte", param);
        if (rtValue != null)
        {
          String strConfirm = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "currentPeriodMsg") + ":" + this.curPeriod.getPeriodYear() + "." + this.curPeriod.getPeriodNumber();
          strConfirm = strConfirm + EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherNumberInteExist") + "\r\n";
          strConfirm = strConfirm + EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherNumberInte") + rtValue.get("number");
          if (checkNumber == 2)
          {
            MsgBox.showDetailAndOK(this, message, strConfirm, 2);
            setCursorOfDefault();
            return;
          }
          String msg = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "currentPeriodMsg") + EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "VoucherNumberInteExist");
          strConfirm = strConfirm + "\r\n" + EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GOonClosePeriod");
          confirm = MsgBox.showConfirm3a(this, msg, strConfirm);
          if (confirm != 0)
          {
            setCursorOfDefault();
            return;
          }
        }
      }
      if (hasVoucherUnCommit == 1)
      {
        String strConfirm = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "confirmCloseWhenExistsSave");
        
        confirm = MsgBox.showConfirm3(this, strConfirm);
        if (confirm != 0)
        {
          setCursorOfDefault();
          SysUtil.abort();
        }
      }
      if (!this.filter.isParamCheckVoucherTemp())
      {
        Map param = new HashMap();
        param.put("period", this.curPeriod);
        param.put("isMonth", Boolean.valueOf(isMonth));
        Map rtValue = GLGeneralFacadeFactory.getRemoteInstance().dealTask("CheckTempVocuher", param);
        if (rtValue != null)
        {
          String strConfirm = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "PARAM_CHECKVOUCHERTEMP");
          
          MsgBox.showDetailAndOK(this, message, strConfirm, 2);
          
          return;
        }
      }
      boolean isAdj = PeriodUtils.isAdjOfNext(new ObjectUuidPK(this.curPeriod.getId().toString()));
      boolean enter = true;
      if (isAdj)
      {
        String msg = EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "AdjPeriod");
        int value = MsgBox.showConfirm3(msg);
        if (MsgBox.isYes(value)) {
          enter = true;
        } else if (MsgBox.isNo(value)) {
          enter = false;
        } else {
          return;
        }
      }
      if (acPeriod != null)
      {
        PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(this.curPeriod);
        if ((!enter) && (nextPeriod != null)) {
          nextPeriod = PeriodUtils.getNextPeriodInfo(nextPeriod);
        }
        if ((nextPeriod != null) && (nextPeriod.getId().toString().equals(acPeriod.getId().toString())) && 
          (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "190_ClosePeriodBatchUI"))))) {
          SysUtil.abort();
        }
      }
      IVchAmortScheme schemeIface = VchAmortSchemeFactory.getRemoteInstance();
      Map schemes = schemeIface.queryNotGenVoucherScheme(this.curPeriod, this.curCompany);
      List records = (List)schemes.get("result");
      if (records.size() > 0)
      {
        StringBuffer sb = new StringBuffer();
        String number = VchAmortUtils.getResource("SCHEME_NUMBER", null);
        String name = VchAmortUtils.getResource("SCHEME_NAME", null);
        for (int i = 0; i < records.size(); i++)
        {
          Object[] record = (Object[])records.get(i);
          sb.append(number).append("£º").append(record[0]);
          sb.append("£¬").append(name).append("£º").append(record[1]);
          sb.append("\r\n");
        }
        int continueEndPeriod = MsgBox.showConfirm3a(VchAmortUtils.getResource("ENDPERIOD_WHEN_SCHEME_NOT_VOUCHER", null), sb.toString());
        if (continueEndPeriod != 0) {
          SysUtil.abort();
        }
      }
      if (isMonth)
      {
        boolean post = false;
        String type = "1";
        if (!closePeriod(enter, post, type))
        {
          destroyWindow();
          return;
        }
      }
    }
    else
    {
      int confirmFlag = 0;
      if ((isMonth) && (iClose == 0)) {
        if ((this.filter.isCanNotClosedAfterReport()) && 
          (iClose == 0) && 
          (this.curPeriod != null) && (this.curCompany.getId() != null))
        {
          PeriodInfo prevPeriod = PeriodUtils.getPrePeriodInfo(this.curPeriod);
          PeriodInfo newPeriodInfo = (PeriodInfo)this.curPeriod.clone();
          if (prevPeriod.isIsAdjustPeriod())
          {
            String msg = EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "AdjPeriod");
            confirm = MsgBox.showConfirm3(msg);
            if (MsgBox.isYes(confirm))
            {
              confirmFlag = 1;
              newPeriodInfo.setPeriodNumber(newPeriodInfo.getPeriodNumber() + 1);
            }
            else if (MsgBox.isNo(confirm))
            {
              confirmFlag = 2;
            }
            else
            {
              return;
            }
          }
          String errInfo = CslRptUIUtil.isHasReportSubmited(newPeriodInfo, this.curCompany.getId().toString());
          if (errInfo != null)
          {
            String errClosePeriod = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "errUnclosePeriod");
            MsgBox.showError(errClosePeriod + " " + errInfo);
            return;
          }
        }
      }
      JobConfig jobCfg = creteJobCfg();
      if (CreateJobFacadeFactory.getRemoteInstance().checkDuplicateTask(jobCfg.getTaskType()))
      {
        message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GLPeriodEndIsInQueue");
        MsgBox.showWarning(message);
        return;
      }
      SystemStatusCtrolCollection col = InitClientHelp.checkIsAlia(this.curPeriod, this.curCompany);
      if (col != null)
      {
        Iterator it = col.iterator();
        while (it.hasNext())
        {
          SystemStatusCtrolInfo info = (SystemStatusCtrolInfo)it.next();
          
          String msg = EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "endRelateGeneralSystem");
          int value = MsgBox.showConfirm2(StringUtils.replace(msg, "{1}", info.getSystemStatus().getName().getAlias()));
          if (MsgBox.isYes(value))
          {
            InitClientHelp.updateAlia(info);
          }
          else
          {
            setCursorOfDefault();
            return;
          }
        }
      }
      String confirmUnClose = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "confirmUnClose");
      confirm = MsgBox.showConfirm2(confirmUnClose);
      
      PeriodInfo lastPeriod = PeriodUtils.getPrePeriodInfo(this.curPeriod);
      switch (confirm)
      {
      case 0: 
        boolean isAdj = lastPeriod.isIsAdjustPeriod();
        String acPrepPeriodid = this.curPeriod.getId().toString();
        if (isAdj)
        {
          if (confirmFlag == 0)
          {
            String msg = EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "AdjPeriod");
            
            int value = MsgBox.showConfirm3(msg);
            if (MsgBox.isCancel(value)) {
              return;
            }
            if (!MsgBox.isYes(value)) {
              confirmFlag = 2;
            }
          }
          if (confirmFlag == 2)
          {
            IObjectValue ojbValue = AccountRefContrastFactory.getRemoteInstance().getLastAcctInfo(this.curCompany.getId().toString(), this.curCompany.getAccountTable().getId().toString(), lastPeriod.getId().toString());
            if (ojbValue != null)
            {
              MsgBox.showDetailAndOK(this, message, StringUtils.replace(EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "CanNotUnPeriodByAref"), "{1}", lastPeriod.toString()), 2);
              return;
            }
            if ((acPeriod != null) && (acPrepPeriodid.equals(acPeriod.getId().toString())) && 
              (!MsgBox.isYes(MsgBox.showConfirm2New(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "254_ClosePeriodUI"))))) {
              SysUtil.abort();
            }
            getBizInterface().unclosePeriod();
            acPrepPeriodid = lastPeriod.getId().toString();
          }
        }
        if (this.curPeriod.isIsAdjustPeriod())
        {
          IObjectValue ojbValue = AccountRefContrastFactory.getRemoteInstance().getLastAcctInfo(this.curCompany.getId().toString(), this.curCompany.getAccountTable().getId().toString(), this.curPeriod.getId().toString());
          if (ojbValue != null)
          {
            MsgBox.showDetailAndOK(this, message, StringUtils.replace(EASResource.getString("com.kingdee.eas.fi.gl.InitResource", "CanNotUnPeriodByAref"), "{1}", this.curPeriod.toString()), 2);
            return;
          }
        }
        if ((acPeriod != null) && (acPrepPeriodid.equals(acPeriod.getId().toString())) && 
          (!MsgBox.isYes(MsgBox.showConfirm2New(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "254_ClosePeriodUI"))))) {
          SysUtil.abort();
        }
        result = getBizInterface().unclosePeriod();
        break;
      case 2: 
        setCursorOfDefault();
        return;
      }
      boolean hasReportChecked = false;
      if (hasReportChecked)
      {
        IGLFlag flag = GLFlagFactory.getRemoteInstance();
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        view.setFilter(filter);
        filter.getFilterItems().add(new FilterItemInfo("orgunit.id", this.curCompany.getId().toString()));
        filter.getFilterItems().add(new FilterItemInfo("name", "glinit"));
        
        GLFlagCollection glcol = flag.getCollection(view);
        GLFlagInfo glInfo = glcol.get(0);
        flag.unclosePeriod(glInfo.getId(), this.curPeriod.getPeriodYear(), this.curPeriod.getPeriodNumber(), this.curCompany.getName());
      }
    }
    if (result == 0)
    {
      String succClosePeriod = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", iClose == 1 ? "succClosePeriod" : "succUnclosePeriod");
      
      String strMsg = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "currentPeriodMsg");
      strMsg = strMsg.concat(":");
      
      String periodFormat = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "periodformat");
      MessageFormat mf = new MessageFormat(periodFormat);
      String txtPeriod = mf.format(new String[] { Integer.toString(this.curPeriod.getPeriodYear()), Integer.toString(this.curPeriod.getPeriodNumber()) });
      strMsg = strMsg.concat(txtPeriod);
      this.txtaResult.append(strMsg);
      this.txtaResult.append("  \r");
      
      this.txtaResult.append(succClosePeriod);
      this.filter = getBizInterface().fetchInitData();
      this.curPeriod = this.filter.getCurPeriod();
      strMsg = "";
      strMsg = strMsg.concat(":");
      
      periodFormat = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "periodformat");
      txtPeriod = mf.format(new String[] { Integer.toString(this.curPeriod.getPeriodYear()), Integer.toString(this.curPeriod.getPeriodNumber()) });
      strMsg = strMsg.concat(txtPeriod);
      this.txtaResult.append(strMsg);
      this.txtaResult.append("  \r");
      
      setBtnOKOK();
      
      CacheServiceFactory.getInstance().discardType(new BOSObjectType("C49E6E58"));
    }
    else
    {
      String errClosePeriod = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", iClose == 1 ? "errClosePeriod" : "errUnclosePeriod");
      
      this.txtaResult.append(errClosePeriod);
    }
  }
  
  private void fetchInitData()
    throws Exception
  {
    IGLPeriodEndFacade ctrl = getBizInterface();
    
    this.filter = ctrl.fetchInitData();
    
    this.curUser = this.filter.getCurUser();
    this.curCompany = this.filter.getCurCompany();
    this.curPeriod = this.filter.getCurPeriod();
    
    boolean isExt = PeriodUtils.isExitAdjOfLastYear(this.curPeriod.getPeriodYear(), this.curPeriod.getPeriodNumber(), this.curCompany);
    if (isExt)
    {
      this.rbUnCloseYear.setEnabled(true);
      this.rbCloseYear.setEnabled(true);
    }
    if (this.curPeriod.isIsAdjustPeriod())
    {
      this.rbUnClosePeriod.setEnabled(true);
      this.rbClosePeriod.setEnabled(false);
      this.rbUnCloseYear.setEnabled(true);
      this.rbCloseYear.setEnabled(true);
      
      this.rbCloseYear.setSelected(true);
      rbCloseYear_actionPerformed(null);
    }
    if (!isExt) {
      this.rbUnCloseYear.setEnabled(false);
    }
  }
  
  protected IGLPeriodEndFacade getBizInterface()
    throws Exception
  {
    this.periodEndCtrl = GLPeriodEndFacadeFactory.getRemoteInstance();
    
    return this.periodEndCtrl;
  }
  
  private void setBtnOKOK()
  {
    this.iSuccess = 1;
    this.btnOK.setEnabled(true);
    this.btnOK2.setEnabled(true);
    this.btnyearOK.setEnabled(true);
    this.btnyearOK2.setEnabled(true);
    
    this.btnOK.setText(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "btnOKText"));
    this.btnOK2.setText(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "btnOKText"));
    
    this.btnyearOK.setText(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "btnOKText"));
    this.btnyearOK2.setText(EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "btnOKText"));
    
    this.rbCloseYear.setEnabled(false);
    this.rbUnCloseYear.setEnabled(false);
    
    this.rbClosePeriod.setEnabled(false);
    this.rbUnClosePeriod.setEnabled(false);
  }
  
  protected void attachListeners()
  {
    ActionMap entryActionMap = getActionMap();
    
    entryActionMap.put("UPLongame", this.updateLongname);
    
    entryActionMap.put("HG", this.showHG);
    
    entryActionMap.put("SQL", this.showSQL);
    
    entryActionMap.put("UPDATE", this.updateBalance);
    
    entryActionMap.put("CLEAR_BALANCE", this.clearBalance);
    
    entryActionMap.put("UPDATEHISTORYDATD_INTIMEVERIFY", this.updateHistoryData_inTimeVerify);
  }
  
  protected void detachListeners()
  {
    ActionMap entryActionMap = getActionMap();
    entryActionMap.put("UPLongame", null);
    entryActionMap.put("HG", null);
    entryActionMap.put("SQL", null);
    entryActionMap.put("UPDATE", null);
    entryActionMap.put("CLEAR_BALANCE", null);
    entryActionMap.put("UPDATEHISTORYDATD_INTIMEVERIFY", null);
  }
  
  protected VoucherEditShortcutListener updateLongname = new VoucherEditShortcutListener("UPLongame");
  protected VoucherEditShortcutListener showHG = new VoucherEditShortcutListener("HG");
  protected VoucherEditShortcutListener showSQL = new VoucherEditShortcutListener("SQL");
  protected VoucherEditShortcutListener updateBalance = new VoucherEditShortcutListener("UPDATE");
  protected VoucherEditShortcutListener clearBalance = new VoucherEditShortcutListener("CLEAR_BALANCE");
  protected VoucherEditShortcutListener updateHistoryData_inTimeVerify = new VoucherEditShortcutListener("UPDATEHISTORYDATD_INTIMEVERIFY");
  
  class VoucherEditShortcutListener
    extends AbstractAction
  {
    String shortcut;
    
    VoucherEditShortcutListener(String sc)
    {
      this.shortcut = sc;
    }
    
    public void actionPerformed(ActionEvent evt)
    {
      if ("UPLongame".equals(this.shortcut)) {
        ClosePeriodUI.this.updateLoname();
      } else if ("SQL".equals(this.shortcut)) {
        ClosePeriodUI.this.showSQL();
      } else if ("UPDATE".equals(this.shortcut)) {
        ClosePeriodUI.this.updateBalance();
      } else if ("CLEAR_BALANCE".equals(this.shortcut)) {
        ClosePeriodUI.this.clearBalance();
      } else if ("HG".equals(this.shortcut)) {
        ClosePeriodUI.this.showHG();
      } else if ("UPDATEHISTORYDATD_INTIMEVERIFY".equals(this.shortcut)) {
        ClosePeriodUI.this.updateHistoryData_inTimeVerify();
      }
    }
  }
  
  public void sendMessage(Message message)
  {
    try
    {
      if (SenderAgent.getSenderAgent().getState() != 1) {
        SenderAgent.getSenderAgent().start();
      }
      MessageSender.getInstance().sendMessage(message);
    }
    catch (Throwable ta)
    {
      throw new RuntimeException("Send JMS Exception", ta);
    }
  }
  
  public void updateLoname()
  {
    int confirm = MsgBox.showConfirm2(EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "255_ClosePeriodUI"));
    if (confirm != 0) {
      return;
    }
    try
    {
      IAutoAssistantHGUpdateFacade flag = AutoAssistantHGUpdateFacadeFactory.getRemoteInstance();
      flag.update();
      MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "256_ClosePeriodUI"));
    }
    catch (BOSException e)
    {
      logger.error(e.getMessage(), e);
    }
    catch (EASBizException e)
    {
      logger.error(e.getMessage(), e);
    }
  }
  
  public void showSQL()
  {
    IUIWindow uiWindow = null;
    try
    {
      uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UINewTabFactory").create("com.kingdee.eas.fm.common.client.FMIsqlUI", getUIContext(), null, OprtState.VIEW);
      
      destroyWindow();
      uiWindow.show();
    }
    catch (UIException e)
    {
      logger.error(e.getMessage(), e);
    }
  }
  
  public void showHG()
  {
    IUIWindow uiWindow = null;
    try
    {
      uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UINewTabFactory").create("com.kingdee.eas.fi.gl.client.AssistantHgListUI", getUIContext(), null, OprtState.VIEW);
      
      destroyWindow();
      uiWindow.show();
    }
    catch (UIException e)
    {
      logger.error(e.getMessage(), e);
    }
  }
  
  public void updateBalance()
  {
    UIContext uiContext = new UIContext(this);
    IUIWindow uiWindow = null;
    try
    {
      uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create("com.kingdee.eas.fi.gl.client.BalanceModifyUI", uiContext, null, OprtState.EDIT);
      destroyWindow();
      uiWindow.show();
    }
    catch (UIException e)
    {
      logger.error(e.getMessage(), e);
    }
  }
  
  public void clearBalance()
  {
    UIContext uiContext = new UIContext(this);
    IUIWindow uiWindow = null;
    try
    {
      uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create("com.kingdee.eas.fi.gl.client.BalanceClearUI", uiContext, null, OprtState.EDIT);
      destroyWindow();
      uiWindow.show();
    }
    catch (UIException e)
    {
      handUIException(e);
    }
  }
  
  private void updateHistoryData_inTimeVerify()
  {
    int confirm = MsgBox.showConfirm3(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "257_ClosePeriodUI"));
    if (confirm == 0) {
      try
      {
        IAcctCussentHistory factory = AcctCussentHistoryFactory.getRemoteInstance();
        
        Date startDate = null;
        Date stopDate = null;
        factory.restoreData(startDate, stopDate);
        MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "258_ClosePeriodUI"));
      }
      catch (BOSException e1)
      {
        logger.error(e1.getMessage(), e1);
      }
    }
  }
  
  private boolean closePeriod(boolean enter, boolean post, String type)
  {
    try
    {
      ICreateJobFacade createJobFacade = CreateJobFacadeFactory.getRemoteInstance();
      JobConfig jobCfg = creteJobCfg();
      if (createJobFacade.checkDuplicateTask(jobCfg.getTaskType()))
      {
        String message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GLPeriodEndIsInQueue");
        MsgBox.showWarning(message);
        return false;
      }
      Object[] params = { this.curCompany, new Boolean(enter), new Boolean(post), type, SysContext.getSysContext().getLocale(), SysContext.getSysContext().getCurrentUserInfo(), this.curPeriod };
      
      Object ret = createJobFacade.executeJob(jobCfg, params);
      if (((ret instanceof Map)) && (((Map)ret).get("result") != null)) {
        return true;
      }
      String message = EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GLPeriodEndInQueue");
      MsgBox.showWarning(message);
      destroyWindow();
      return false;
    }
    catch (Exception e)
    {
      handUIException(e);
    }
    return false;
  }
  
  private JobConfig creteJobCfg()
  {
    String title = "¡¾" + this.curCompany.getName() + "(" + this.curCompany.getNumber() + ")¡¿" + EASResource.getString("com.kingdee.eas.fi.gl.GLPeriodEndResource", "GLPeriodEnd");
    JobConfig jobCfg = new JobConfig();
    jobCfg.setTitle(title);
    jobCfg.setSyn(true);
    jobCfg.setTaskType("FI_GL_001" + this.curCompany.getNumber());
    jobCfg.setDescription(title);
    jobCfg.setNeedCheckTaskType(true);
    
    jobCfg.setNumber("FI_GL_001");
    
    return jobCfg;
  }
}
