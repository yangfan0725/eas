/*jadclipse*/package com.kingdee.eas.fi.gl;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.framework.IDataBaseD;
import com.kingdee.eas.basedata.master.auxacct.*;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import java.io.PrintStream;
import java.util.Iterator;
import org.apache.log4j.Logger;
public class InitAcctCussentImportFDC extends InitAcctCussentImport
{
            public InitAcctCussentImportFDC()
            {
            }
            private static void printLog(String msg)
            {














/*  42*/        logger.error((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
/*  43*/        System.err.println((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
/*  44*/        System.out.println((new StringBuilder()).append("FDC\u2605\u2605\u2605").append(msg).toString());
            }
            protected DataBaseInfo findAsstActObject(Context ctx, String asstHGAttribute, AssistantHGInfo assistantHGInfo, String strAsstActTypeName, String number, String strAsstName, AsstActTypeCollection arrayAsstActTypeInfo)
                throws BOSException
            {/*  48*/        printLog((new StringBuilder()).append("findAsstActObject.").append(asstHGAttribute).toString());
/*  49*/        if(isFDCAsstAct(asstHGAttribute))
                {/*  50*/            printLog((new StringBuilder()).append("findAsstActObject in FDC.").append(asstHGAttribute).toString());
/*  51*/            return findAsstActObject4FDC(ctx, asstHGAttribute, assistantHGInfo, strAsstActTypeName, number, strAsstName, arrayAsstActTypeInfo);
                } else
                {
/*  54*/            return super.findAsstActObject(ctx, asstHGAttribute, assistantHGInfo, strAsstActTypeName, number, strAsstName, arrayAsstActTypeInfo);
                }
            }
            private boolean isFDCAsstAct(String asstHGAttribute)
            {




/*  63*/        if("curProject".equalsIgnoreCase(asstHGAttribute) || "ContractBaseData".equalsIgnoreCase(asstHGAttribute) || "ProductType".equalsIgnoreCase(asstHGAttribute) || "sellProject".equalsIgnoreCase(asstHGAttribute) || "building".equalsIgnoreCase(asstHGAttribute) || "room".equalsIgnoreCase(asstHGAttribute))
                {

/*  66*/            printLog((new StringBuilder()).append("asstHGAttribute hpw: ").append(asstHGAttribute).toString());
/*  67*/            return true;
                } else
                {/*  69*/            return false;
                }
            }
            private DataBaseInfo findAsstActObject4FDC(Context ctx, String asstHGAttribute, AssistantHGInfo assistantHGInfo, String strAsstActTypeName, String number, String strAsstName, AsstActTypeCollection arrayAsstActTypeInfo)
                throws BOSException
            {



/*  78*/        IAssistantHG iAssistantHG = AssistantHGFactory.getLocalInstance(ctx);
/*  79*/        com.kingdee.bos.util.BOSObjectType type = iAssistantHG.getType();
/*  80*/        IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
/*  81*/        String cuid = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();


/*  84*/        CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
/*  85*/        FullOrgUnitInfo org = company.castToFullOrgUnitInfo();

/*  87*/        com.kingdee.eas.basedata.org.SaleOrgUnitInfo sellUnit = ContextUtil.getCurrentSaleUnit(ctx);
/*  88*/        String companyId = company.getId().toString();
/*  89*/        String orgId = org.getId().toString();



/*  93*/        EntityObjectInfo eo = loader.getEntity(type);
/*  94*/        PropertyCollection props = eo.getInheritedNoDuplicatedProperties();
/*  95*/        Iterator itr = props.iterator();
/*  96*/        LinkPropertyInfo pi = null;

/*  98*/        DataBaseInfo data = null;

/* 100*/        try
                {
/* <-MISALIGNED-> */ /* 100*/            do
                    {
/* <-MISALIGNED-> */ /* 100*/                if(!itr.hasNext())
/* <-MISALIGNED-> */ /* 101*/                    break;
/* <-MISALIGNED-> */ /* 101*/                PropertyInfo p = (PropertyInfo)itr.next();
/* <-MISALIGNED-> */ /* 102*/                if(!asstHGAttribute.equalsIgnoreCase(p.getName()) || !(p instanceof LinkPropertyInfo))
/* <-MISALIGNED-> */ /* 103*/                    continue;
/* <-MISALIGNED-> */ /* 103*/                pi = (LinkPropertyInfo)p;
/* <-MISALIGNED-> */ /* 104*/                break;
                    } while(true);
/* <-MISALIGNED-> */ /* 107*/            if(pi != null)
                    {
/* <-MISALIGNED-> */ /* 108*/                EntityObjectInfo eobj = pi.getRelationship().getSupplierObject();
/* <-MISALIGNED-> */ /* 110*/                String strFacotry = eobj.getBusinessImplFactory();
/* <-MISALIGNED-> */ /* 111*/                String strLocal = eobj.getBusinessImplName();
/* <-MISALIGNED-> */ /* 112*/                EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 113*/                SelectorItemCollection sic = view.getSelector();
/* <-MISALIGNED-> */ /* 114*/                sic.add(new SelectorItemInfo("number"));
/* <-MISALIGNED-> */ /* 115*/                sic.add(new SelectorItemInfo("name"));
/* <-MISALIGNED-> */ /* 117*/                FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 118*/                view.setFilter(filter);
/* <-MISALIGNED-> */ /* 119*/                FilterItemCollection fic = filter.getFilterItems();
/* <-MISALIGNED-> */ /* 121*/                AsstActTypeInfo typeInfo = GlWebServiceUtil.findAsstActtypeNumberByAttribute(ctx, asstHGAttribute);
/* <-MISALIGNED-> */ /* 123*/                if(typeInfo.isUseLongNumber() && number != null)/* 125*/                    fic.add(new FilterItemInfo("longNumber", number.replace('.', '!')));

/* 127*/                else/* 127*/                if("room".equalsIgnoreCase(asstHGAttribute))
/* 128*/                    fic.add(new FilterItemInfo("name", strAsstName));



/* 132*/                else/* 132*/                    fic.add(new FilterItemInfo("number", number));


/* 135*/                if("curProject".equalsIgnoreCase(asstHGAttribute))
/* 136*/                    fic.add(new FilterItemInfo("fullOrgUnit.id", companyId));

/* 138*/                else/* 138*/                if("ContractBaseData".equalsIgnoreCase(asstHGAttribute))
/* 139*/                    fic.add(new FilterItemInfo("company.id", companyId));

/* 141*/                else/* 141*/                if(!"sellProject".equalsIgnoreCase(asstHGAttribute))






/* 148*/                    if("building".equalsIgnoreCase(asstHGAttribute))
                            {


/* 152*/                        if(assistantHGInfo.getSellProject() != null && assistantHGInfo.getSellProject().getId() != null)
/* 153*/                            fic.add(new FilterItemInfo("sellProject.id", assistantHGInfo.getSellProject().getId().toString()));
                            } else


/* 157*/                    if("room".equalsIgnoreCase(asstHGAttribute))
                            {


/* 161*/                        if(assistantHGInfo.getSellProject() != null && assistantHGInfo.getSellProject().getId() != null)
/* 162*/                            fic.add(new FilterItemInfo("building.sellProject.id", assistantHGInfo.getSellProject().getId()));


/* 165*/                        if(assistantHGInfo.getBuilding() != null && assistantHGInfo.getBuilding().getId() != null)
                                {/* 166*/                            fic.add(new FilterItemInfo("building.id", assistantHGInfo.getBuilding().getId().toString()));


/* 169*/                            if(assistantHGInfo.getSellProject() != null)/* 169*/                                if(assistantHGInfo.getSellProject().getId() == null);
                                }
                            }






/* 178*/                String strMethod = "getDataBaseCollection";
/* 179*/                Class argTypes[] = {/* 179*/                    view.getClass()
                        };/* 180*/                Object args[] = {/* 180*/                    view
                        };/* 181*/                printLog((new StringBuilder()).append("filter hpw: ").append(filter).append(";").append(view).toString());
/* 182*/                Object result = RunningBusinessMethod.runLocalMethod(ctx, strFacotry, strLocal, strMethod, argTypes, args);

/* 184*/                if(result != null)
                        {/* 185*/                    DataBaseCollection collection = (DataBaseCollection)result;
/* 186*/                    if(collection.size() != 0)
                            {/* 187*/                        printLog((new StringBuilder()).append("InitAcctCussentImportFDC hpw: ").append(collection.size()).toString());
/* 188*/                        if(collection.size() > 1)
                                {/* 189*/                            throw new Exception((new StringBuilder()).append(strAsstActTypeName).append(ResourceBase.getString("com.kingdee.eas.fi.gl.GLAutoGenerateResource", "1009_InitImportBase", ctx.getLocale())).toString());
                                } else
                                {/* 191*/                            data = collection.get(0);
/* 192*/                            return data;
                                }
                            }
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 196*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 197*/            printLog((new StringBuilder()).append("findAsstActObject in exception.").append(e.getMessage()).append(";").append(e).toString());
/* <-MISALIGNED-> */ /* 199*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /* 201*/        printLog("over hpw: ");
/* <-MISALIGNED-> */ /* 202*/        return null;
            }
            public static AsstActTypeInfo findAsstActtypeNumberByAttribute(Context ctx, String asstHGAttribute)
                throws BOSException
            {




/* 213*/        if(asstHGAttribute == null || asstHGAttribute.trim().equals(""))
/* 214*/            return null;

/* 216*/        IAsstActType asstActType = null;
/* 217*/        if(ctx != null)
/* 218*/            asstActType = AsstActTypeFactory.getLocalInstance(ctx);

/* 220*/        else/* 220*/            asstActType = AsstActTypeFactory.getRemoteInstance();

/* 222*/        EntityViewInfo view = new EntityViewInfo();
/* 223*/        SelectorItemCollection sic = view.getSelector();
/* 224*/        sic.add(new SelectorItemInfo("id"));
/* 225*/        sic.add(new SelectorItemInfo("number"));
/* 226*/        sic.add(new SelectorItemInfo("name"));
/* 227*/        sic.add(new SelectorItemInfo("asstHGAttribute"));
/* 228*/        sic.add(new SelectorItemInfo("useLongNumber"));
/* 229*/        sic.add(new SelectorItemInfo("mappingFieldName"));
/* 230*/        sic.add(new SelectorItemInfo("isMultilevel"));
/* 231*/        sic.add(new SelectorItemInfo("groupTableName"));
/* 232*/        sic.add(new SelectorItemInfo("useLongNumber"));
/* 233*/        sic.add(new SelectorItemInfo("glAsstActTypeGrp.id"));
/* 234*/        sic.add(new SelectorItemInfo("glAsstActTypeGrp.shareTactic"));
/* 235*/        FilterInfo filter = new FilterInfo();
/* 236*/        view.setFilter(filter);
/* 237*/        FilterItemCollection fic = filter.getFilterItems();
/* 238*/        fic.add(new FilterItemInfo("asstHGAttribute", asstHGAttribute.trim()));
/* 239*/        AsstActTypeInfo asstActTypeInfo = null;
/* 240*/        AsstActTypeCollection asstActTypeCollection = asstActType.getAsstActTypeCollection(view);
/* 241*/        if(asstActTypeCollection != null && asstActTypeCollection.size() != 0)
/* 242*/            asstActTypeInfo = asstActTypeCollection.get(0);


/* 245*/        return asstActTypeInfo;
            }
            public static FilterInfo getDFilterInfo(IDataBaseD iDaba, String cuid)
                throws Exception
            {


/* 252*/        ObjectUuidPK pk = new ObjectUuidPK(cuid);

/* 254*/        return iDaba.getDatabaseDFilter(pk, "id", "adminCU.id");
            }
            private static Logger logger = Logger.getLogger(com.kingdee.eas.fi.gl.InitAcctCussentImportFDC.class);
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\industry\fdc_merge-server.jar
	Total time: 87 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/