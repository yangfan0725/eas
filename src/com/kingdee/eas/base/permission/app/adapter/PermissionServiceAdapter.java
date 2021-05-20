package com.kingdee.eas.base.permission.app.adapter;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.impl.ImplUtils;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.bo.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.service.*;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.metas.AssignFactory;
import com.kingdee.bos.workflow.metas.IAssign;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.*;
import com.kingdee.eas.base.permission.app.adapter.MutiOrgPermissionServiceAdapter;
import com.kingdee.eas.base.permission.app.adapter.PermissionServiceAdapterHelper;
import com.kingdee.eas.base.permission.app.cache.IPermItemCache;
import com.kingdee.eas.base.permission.app.cache.PermissionCacheFactory;
import com.kingdee.eas.base.permission.app.config.PermissionFilterConfiguration;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.LowTimer;
import com.kingdee.util.StringUtils;
import org.apache.log4j.Logger;
public class PermissionServiceAdapter extends AbstractServiceAdapter
    implements IServiceAdapter
{
            public PermissionServiceAdapter()
            {
            }
            public int getPriority()
            {




























/*  61*/        return 100;
            }
            public String getName()
            {

/*  66*/        return "PERMISSION_SERVICE";
            }
            public void execute(IServiceContext serviceContext)
                throws BOSException
            {
/*  71*/        LowTimer lowTimer = new LowTimer();
/*  72*/        long beginTime = lowTimer.msValue();

/*  74*/        Context context = serviceContext.getContext();
/*  75*/        Object objForKScript = context.get("disablePermissionForKScript");
				if(context.getCaller()!=null){
					try {
						UserInfo u=UserFactory.getLocalInstance(context).getUserByID(context.getCaller());
						if(u.getNumber().equals("webservice")){
							return;
						}
					} catch (EASBizException e) {
						e.printStackTrace();
					}
				}

/*  77*/        if(objForKScript != null && (objForKScript instanceof Boolean) && ((Boolean)objForKScript).booleanValue())





/*  83*/            return;






/*  90*/        try
                {
/* <-MISALIGNED-> */ /*  90*/            if(serviceContext.getExecutionMode() == 0)/*  92*/                handleServiceBefore(serviceContext);
                }
/*  94*/        catch(EASBizException e)
                {
/*  96*/            logger.error("Permission Service", e);
/*  97*/            throw new PermissionServiceException(e.getMessage());
                }

/* 100*/        if(logger.isDebugEnabled())
                {
/* 102*/            long endTime = lowTimer.msValue();
/* 103*/            logger.debug((new StringBuilder()).append("execute():  ").append(endTime - beginTime).toString());
                }
            }
            public boolean enableDataPermFromContext(IServiceContext serviceContext)
            {


/* 110*/        boolean defaultVaue = true;
/* 111*/        if(serviceContext == null || serviceContext.getContext() == null)
/* 112*/            return defaultVaue;

/* 114*/        String disableDataPerm = StringUtils.cnulls(serviceContext.getContext().get("DISABLE_DATA_PERM"));


/* 117*/        if(disableDataPerm.equalsIgnoreCase("true"))
/* 118*/            return !defaultVaue;

/* 120*/        else/* 120*/            return defaultVaue;
            }
            private void handleServiceBefore(IServiceContext serviceContext)
                throws EASBizException, BOSException
            {







/* 132*/        String permItemNames[] = getPermItemNames(serviceContext);
/* 133*/        if(permItemNames == null || permItemNames.length == 0)

/* 135*/            return;


/* 138*/        IPermissionServiceProvider provider = PermissionServiceProviderFactory.getLocalInstance(serviceContext.getContext());


/* 141*/        boolean isEnableDataPermFromContext = enableDataPermFromContext(serviceContext);
/* 142*/        boolean isEnableDataPermission = provider.isEnableDataPermission();

/* 144*/        for(int i = 0; i < permItemNames.length; i++)

/* 146*/            if(isEnableDataPermission && isEnableDataPermFromContext)

/* 148*/                handleWithEnableDataPermision(serviceContext, permItemNames[i]);


/* 151*/            else/* 151*/                handleWithoutEnableDataPermision(serviceContext, permItemNames[i]);
            }
            private boolean isFacadeObject(IServiceContext serviceContext)
            {










/* 165*/        return serviceContext.getMetaType() == null ? false : serviceContext.getMetaType() == MetaDataTypeList.FACADE;
            }
            private boolean isEntityObject(IServiceContext serviceContext)
            {








/* 177*/        return serviceContext.getMetaType() == null ? false : serviceContext.getMetaType() == MetaDataTypeList.ENTITY;
            }
            private boolean isQueryObject(IServiceContext serviceContext)
            {








/* 189*/        return serviceContext.getMetaType() == null ? false : serviceContext.getMetaType() == MetaDataTypeList.BASEQUERY;
            }
            private void bindPermissionContext(IServiceContext serviceContext, String rule)
                throws EASBizException, BOSException
            {


/* 196*/        PermissionServiceAdapterHelper.bindPermissionContext(serviceContext, rule);
            }
            private String[] getPermItemNames(IServiceContext serviceContext)
            {








/* 208*/        return (String[])(String[])serviceContext.getServiceParameter("PERMISSION_SERVICE", "PERMISSION_ITEMS");
            }
            private IObjectPK getMainBizOrgPK(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {


/* 215*/        return PermissionServiceAdapterHelper.getMainBizOrgPK(serviceContext, permItemInfo);
            }
            private void handleWithoutEnableDataPermision(IServiceContext serviceContext, String permItemName)
                throws EASBizException, BOSException
            {



/* 223*/        PermItemInfo permItemInfo = PermissionCacheFactory.getPermItemCache(serviceContext.getContext()).getPermItem(serviceContext.getContext(), permItemName);



/* 227*/        if(isFacadeObject(serviceContext) || isEntityObject(serviceContext))

/* 229*/            handleMethodFunctionPermission(serviceContext, permItemInfo);
/* 230*/        else/* 230*/        if(isQueryObject(serviceContext))

/* 232*/            handleQueryFunctionPermission(serviceContext, permItemInfo);
            }
            private void handleMethodFunctionPermission(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {


/* 239*/        Context context = serviceContext.getContext();
/* 240*/        IObjectPK userPK = context.getCaller();
/* 241*/        IObjectPK orgPK = getMainBizOrgPK(serviceContext, permItemInfo);

/* 243*/        IPermission iPermission = PermissionFactory.getLocalInstance(serviceContext.getContext());

/* 245*/        iPermission.checkFunctionPermission(userPK, orgPK, permItemInfo.getName());
            }
            private boolean hasFunctionPermission(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {


/* 252*/        Context context = serviceContext.getContext();
/* 253*/        IObjectPK userPK = context.getCaller();
/* 254*/        IObjectPK orgPK = getMainBizOrgPK(serviceContext, permItemInfo);

/* 256*/        IPermission iPermission = PermissionFactory.getLocalInstance(serviceContext.getContext());


/* 259*/        return iPermission.hasFunctionPermission(userPK, orgPK, permItemInfo.getName());
            }
            private void handleQueryFunctionPermission(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {


/* 266*/        if(!hasFunctionPermission(serviceContext, permItemInfo))

/* 268*/            bindPermissionContext(serviceContext, getNoRightRuleInfo(permItemInfo.getName()));
            }
            private void handleMethodDataPermission(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {



/* 276*/        Context context = serviceContext.getContext();
/* 277*/        IObjectPK userPK = context.getCaller();
/* 278*/        IObjectPK orgPK = getMainBizOrgPK(serviceContext, permItemInfo);

/* 280*/        IPermission iPermission = PermissionFactory.getLocalInstance(serviceContext.getContext());


/* 283*/        if(StringUtils.isEmpty(permItemInfo.getObjectType()) || !permItemInfo.isEnableDataPermission())
                {

/* 286*/            iPermission.checkFunctionPermission(userPK, orgPK, permItemInfo.getName());
                } else
                {

/* 290*/            BOSObjectType objectType = BOSObjectType.create(permItemInfo.getObjectType());

/* 292*/            OperationType oprtType = permItemInfo.getOperationType();

/* 294*/            if(oprtType.equals(OperationType.ADDNEW) || oprtType.equals(OperationType.UPDATE))
                    {

/* 297*/                IObjectValue objectValue = getObjectValue(serviceContext, objectType);

/* 299*/                if(objectValue != null)

/* 301*/                    iPermission.checkDataPermission(userPK, orgPK, permItemInfo.getName(), objectValue);
                    } else

/* 304*/            if(oprtType.equals(OperationType.READ))
                    {
/* 306*/                IObjectPK objectPK = getObjectPK(serviceContext, objectType);

/* 308*/                if(isIgnoreAndAssigned(serviceContext, permItemInfo))

/* 310*/                    return;

/* 312*/                if(objectPK != null)

/* 314*/                    iPermission.checkDataPermission(userPK, orgPK, permItemInfo.getName(), objectPK);



/* 318*/                setMethodService(permItemInfo, serviceContext, userPK, orgPK);
                    } else
/* 320*/            if(oprtType.equals(OperationType.DELETE) || oprtType.equals(OperationType.EXECUTE) || oprtType.equals(OperationType.OTHER))
                    {


/* 324*/                IObjectPK objectPK = getObjectPK(serviceContext, objectType);
/* 325*/                if(objectPK != null)

/* 327*/                    iPermission.checkDataPermission(userPK, orgPK, permItemInfo.getName(), objectPK);
                    }
                }
            }
            private void setMethodService(PermItemInfo permItemInfo, IServiceContext serviceContext, IObjectPK userPK, IObjectPK orgPK)
                throws BOSException, EASBizException
            {





/* 339*/        if(!StringUtils.isEmpty(permItemInfo.getObjectType()) && permItemInfo.isEnableDataPermission())
                {

/* 342*/            IPermissionServiceProvider provider = PermissionServiceProviderFactory.getLocalInstance(serviceContext.getContext());

/* 344*/            String rule = provider.getPermissionRule(userPK, orgPK, permItemInfo.getName());


/* 347*/            if(!StringUtils.isEmpty(rule))



/* 351*/                try
                        {
/* <-MISALIGNED-> */ /* 351*/                    EntityViewInfo resultEvi = new EntityViewInfo(rule);
/* <-MISALIGNED-> */ /* 352*/                    serviceContext.setServiceResult("PERMISSION_SERVICE", resultEvi.getFilter());

/* 356*/                    logger.debug((new StringBuilder()).append("resultEntityViewInfo is:").append(resultEvi).toString());

/* 358*/                    serviceContext.getContext().put(getMethodPK(serviceContext), resultEvi);
                        }
/* 360*/                catch(ParserException e)
                        {
/* 362*/                    logger.error((new StringBuilder()).append(" rule: ").append(rule).toString(), e);
/* 363*/                    throw new PermissionException(PermissionException.CHECK_PERMISSION_FAIL, e);
                        }
                }
            }
            private String getMethodPK(IServiceContext serviceContext)
            {



/* 372*/        String methodNamePK = serviceContext.getOperationPK().getName();

/* 374*/        IMetaDataLoader iMetaDataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(serviceContext.getContext());


/* 377*/        BusinessObjectInfo entity = iMetaDataLoader.getBusinessObject(serviceContext.getBoType());


/* 380*/        MethodCollection methodCol = entity.getAllMethodsRuntime();
/* 381*/        String methodName = "";
/* 382*/        int i = 0;/* 382*/        int size = methodCol.size();/* 382*/        do
                {
/* <-MISALIGNED-> */ /* 382*/            if(i >= size)/* 384*/                break;/* 384*/            MethodInfo methodInfo = methodCol.get(i);
/* 385*/            logger.debug((new StringBuilder()).append("innerId is:").append(methodInfo.getInnerID()).toString());

/* 387*/            if(methodInfo.getInnerID().equals(methodNamePK))
                    {
/* 389*/                methodName = methodInfo.getName();
/* 390*/                break;
                    }
/* <-MISALIGNED-> */ /* 382*/            i++;
                } while(true);/* 393*/        Object obj[] = serviceContext.getOperationParameters();
/* 394*/        String pk = ImplUtils.buildPermissionServiceKey(serviceContext.getBoType(), methodName, obj);

/* 396*/        logger.debug((new StringBuilder()).append(" methodPK is:").append(pk).toString());
/* 397*/        return pk;
            }
            private void handleQueryDataPermission(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws EASBizException, BOSException
            {

/* 403*/        if(!hasFunctionPermission(serviceContext, permItemInfo))

/* 405*/            bindPermissionContext(serviceContext, getNoRightRuleInfo(permItemInfo.getName()));



/* 409*/        else/* 409*/        if(!StringUtils.isEmpty(permItemInfo.getObjectType()) && permItemInfo.isEnableDataPermission())
                {

/* 412*/            Context context = serviceContext.getContext();
/* 413*/            IObjectPK userPK = context.getCaller();
/* 414*/            IObjectPK orgPK = getMainBizOrgPK(serviceContext, permItemInfo);

/* 416*/            IPermissionServiceProvider provider = PermissionServiceProviderFactory.getLocalInstance(serviceContext.getContext());

/* 418*/            String permName = permItemInfo.getName();
/* 419*/            String rule = provider.getPermissionRule(userPK, orgPK, permName);

/* 421*/            if(!StringUtils.isEmpty(rule))

/* 423*/                bindPermissionContext(serviceContext, rule);
                }
            }
            private String getNoRightRuleInfo(String strKey)
            {



/* 431*/        return PermissionFilterConfiguration.getList().getFilterString(strKey);
            }
            private boolean isIgnoreAndAssigned(IServiceContext serviceContext, PermItemInfo permItemInfo)
                throws BOSException, EASBizException
            {













/* 449*/        String isIgnore = ParamManager.getParamValue(serviceContext.getContext(), null, "IgnoreDataPermForAssignUser");

/* 451*/        boolean isIgnoreDataPermForAssignUser = false;
/* 452*/        if(!StringUtils.isEmpty(isIgnore))

/* 454*/            isIgnoreDataPermForAssignUser = Boolean.valueOf(isIgnore).booleanValue();



/* 458*/        logger.debug((new StringBuilder()).append("####now isIgnoreAndAssigned is :").append(isIgnoreDataPermForAssignUser).toString());


/* 461*/        if(!isIgnoreDataPermForAssignUser)

/* 463*/            return false;

/* 465*/        logger.debug((new StringBuilder()).append("isIgnoreAndAssigned:permItemInfo").append(permItemInfo.getName()).toString());

/* 467*/        logger.debug((new StringBuilder()).append("isIgnoreAndAssigned:permItemInfo.getOperationType():").append(permItemInfo.getOperationType()).toString());


/* 470*/        if(permItemInfo.getOperationType().equals(OperationType.READ))
                {
/* 472*/            BOSObjectType objectTYpe = BOSObjectType.create(permItemInfo.getObjectType());

/* 474*/            IObjectPK objectPK = getObjectPK(serviceContext, objectTYpe);
/* 475*/            String objectPKString = StringUtils.cnulls(objectPK);
/* 476*/            logger.debug((new StringBuilder()).append("isIgnoreAndAssigned:objectPKString:").append(objectPKString).toString());



/* 480*/            if(StringUtils.isEmpty(objectPKString))
                    {

/* 483*/                Object params[] = serviceContext.getOperationParameters();
/* 484*/                if(params.length == 2)
                        {
/* 486*/                    String idString = null;
/* 487*/                    if(params[1] != null)

/* 489*/                        idString = params[1].toString();

/* 491*/                    if(idString != null && idString.indexOf("'") > 0 && idString.indexOf("'") < idString.lastIndexOf("'"))




/* 496*/                        objectPKString = idString.substring(idString.indexOf("'") + 1, idString.lastIndexOf("'"));


/* 499*/                    if(!BOSUuid.isValid(objectPKString, true))


/* 502*/                        objectPKString = "";
                        }
                    }

/* 506*/            if(!StringUtils.isEmpty(objectPKString))
                    {
/* 508*/                IAssign iAssign = AssignFactory.getLocalInstance(serviceContext.getContext());

/* 510*/                IObjectPK userPK = serviceContext.getContext().getCaller();
/* 511*/                logger.debug((new StringBuilder()).append("isIgnoreAndAssigned:userPK:").append(userPK).append(" ,objectPKString:").append(objectPKString).toString());


/* 514*/                FilterInfo filter = new FilterInfo();
/* 515*/                filter.getFilterItems().add(new FilterItemInfo("personUserID", userPK.toString()));

/* 517*/                filter.getFilterItems().add(new FilterItemInfo("bizObjID", objectPKString));


/* 520*/                IObjectPK pks[] = iAssign.getPKList(filter, null);
/* 521*/                if(pks != null && pks.length > 0)

/* 523*/                    return true;
                    }
                }

/* 527*/        return false;
            }
            private void handleWithEnableDataPermision(IServiceContext serviceContext, String permItemName)
                throws EASBizException, BOSException
            {

/* 533*/        PermItemInfo permItemInfo = PermissionCacheFactory.getPermItemCache(serviceContext.getContext()).getPermItem(serviceContext.getContext(), permItemName);



/* 537*/        if(isFacadeObject(serviceContext) || isEntityObject(serviceContext))

/* 539*/            handleMethodDataPermission(serviceContext, permItemInfo);
/* 540*/        else/* 540*/        if(isQueryObject(serviceContext))
                {
/* 542*/            MutiOrgPermissionServiceAdapter adapter = new MutiOrgPermissionServiceAdapter();
/* 543*/            if(adapter.isMutiOrgPerm(serviceContext))

/* 545*/                adapter.handleQueryDataPermission(serviceContext, permItemInfo);


/* 548*/            else/* 548*/                handleQueryDataPermission(serviceContext, permItemInfo);
                }
            }
            private IObjectPK getObjectPK(IServiceContext serviceContext, BOSObjectType objectType)
            {










/* 563*/        return PermissionServiceAdapterHelper.getObjectPK(serviceContext, objectType);
            }
            private IObjectValue getObjectValue(IServiceContext serviceContext, BOSObjectType objectType)
            {











/* 578*/        return PermissionServiceAdapterHelper.getObjectValue(serviceContext, objectType);
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.base.permission.app.adapter.PermissionServiceAdapter");
            protected static final String PERMISSION_ITEMS = "PERMISSION_ITEMS";
            private static final String DISABLE_DATA_PERM = "DISABLE_DATA_PERM";
            private static final int PERMISSION_PRIORITY = 100;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\server\bos\bs_permission-server.jar
	Total time: 45 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/