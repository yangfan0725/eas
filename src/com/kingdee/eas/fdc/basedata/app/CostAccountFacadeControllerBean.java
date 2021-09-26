/*jadclipse*/package com.kingdee.eas.fdc.basedata.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.app.dao.ReferenceDAO;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.BizReference;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
public class CostAccountFacadeControllerBean extends AbstractCostAccountFacadeControllerBean
{
            public CostAccountFacadeControllerBean()
            {
            }
            protected void _assignOrgsCostAccount(Context ctx, IObjectPK orgPK)
                throws BOSException, EASBizException
            {
                CostAccountCollection cac;
                FullOrgUnitCollection fouc;
                String sSql;
                Map accMap;

















































/*  86*/        FDCUtils.lockAssignCostAccount(ctx);


/*  89*/        cac = getOrgCostAccounts(ctx, orgPK.toString());

/*  91*/        IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
/*  92*/        FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(orgPK);
/*  93*/        String longNumber = fullOrgUnitInfo.getLongNumber();
/*  94*/        fouc = iFullOrgUnit.getFullOrgUnitCollection((new StringBuilder("select id where isCompanyOrgUnit = 1 and longNumber like '")).append(longNumber).append("!%'").toString());

/*  96*/        sSql = (new StringBuilder("SELECT T0.FID, T0.FLONGNUMBER, T0.FFULLORGUNIT,T0.FISENABLE  FROM T_FDC_COSTACCOUNT t0  INNER JOIN T_ORG_BaseUnit t1 ON t1.fid=t0.FFullOrgUnit  WHERE t1.fisCompanyOrgUnit = 1 and t1.flongNumber like '")).append(longNumber).append("!%'").toString();




/* 101*/        accMap = null;

/* 103*/        try
                {
/* <-MISALIGNED-> */ /* 103*/            IRowSet rs = DbUtil.executeQuery(ctx, sSql);
/* <-MISALIGNED-> */ /* 104*/            accMap = new HashMap();
/* <-MISALIGNED-> */ /* 105*/            Map caMap = null;
/* <-MISALIGNED-> */ /* 106*/            String myKey = null;/* 108*/            for(; rs.next(); accMap.put(myKey, caMap))
                    {
/* <-MISALIGNED-> */ /* 108*/                caMap = new HashMap();
/* <-MISALIGNED-> */ /* 109*/                caMap.put("id", rs.getString("FID"));
/* <-MISALIGNED-> */ /* 110*/                caMap.put("isEnable", Boolean.valueOf(rs.getBoolean("FIsEnable")));/* 112*/                myKey = (new StringBuilder(String.valueOf(rs.getString("FFullOrgUnit")))).append("_").append(rs.getString("FLongnumber")).toString();
                    }


/* 116*/            Set nodeIds = FDCHelper.getIds(fouc.toArray());
/* 117*/            List errorList = insertCostAccount(ctx, nodeIds, cac, accMap, null, false);
                }/* 118*/        catch(SQLException ex)
                {/* 119*/          
/* <-MISALIGNED-> */ /* 121*/        accMap.clear();
/* <-MISALIGNED-> */ /* 124*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '")).append(orgPK.toString()).append("' and fisSource = 1 and fisEnable = 1  ").toString();
/* 127*/        DbUtil.execute(ctx, sql);


/* 130*/        String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
/* 131*/        DbUtil.execute(ctx, sqlNew);

/* 133*/        String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
/* 134*/        DbUtil.execute(ctx, sqlOld);
				throw new BOSException(ex);
                }
            }
            private CostAccountCollection getCostAccountOrgOwn(Context ctx, String orgPK)
                throws BOSException
            {

/* 141*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select * where fullOrgUnit.id = '")).append(orgPK).append("' and isSource = 1 order by longNumber").toString());

/* 143*/        return costAccountCollection;
            }
            private CostAccountCollection getEnCostAccountOrgOwn(Context ctx, String orgPK)
                throws BOSException
            {


/* 150*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select * where fullOrgUnit.id = '")).append(orgPK).append("' and isSource = 1 and isEnabled = 1  order by longNumber").toString());

/* 152*/        return costAccountCollection;
            }
            private boolean isAssignToOrg(Context ctx, String costAccountPK, String orgPK)
                throws BOSException
            {


/* 159*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select id where srcCostAccountId = '")).append(costAccountPK).append("' and fullOrgUnit.id = '").append(orgPK).append("' and isSource = 0").toString());

/* 161*/        return costAccountCollection.size() != 0;
            }
            private CostAccountAssignInfo createCostAccountAssignOrg(Context ctx, String costAccountPK, String orgPK)
                throws EASBizException, BOSException
            {






/* 172*/        CostAccountAssignInfo costAccountAssignInfo = new CostAccountAssignInfo();
/* 173*/        CostAccountInfo costAccountInfo = new CostAccountInfo();
/* 174*/        costAccountInfo.setId(BOSUuid.read(costAccountPK));
/* 175*/        FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
/* 176*/        fullOrgUnitInfo.setId(BOSUuid.read(orgPK));
/* 177*/        costAccountAssignInfo.setCostAccount(costAccountInfo);
/* 178*/        costAccountAssignInfo.setFullOrgUnit(fullOrgUnitInfo);
/* 179*/        return costAccountAssignInfo;
            }
            protected void _disAssignOrgsCostAccount(Context ctx, IObjectPK orgPK)
                throws BOSException, EASBizException
            {








/* 192*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
/* 193*/        CostAccountCollection cac = getCostAccountOrgOwn(ctx, orgPK.toString());
/* 194*/        HashSet lnUps = new HashSet();
/* 195*/        for(int i = 0; i < cac.size(); i++)
/* 196*/            lnUps.add(cac.get(i).getId().toString());

/* 198*/        FilterInfo filter = new FilterInfo();
/* 199*/        if(lnUps.size() != 0)
                {
/* 201*/            filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));

/* 203*/            iCostAccount.delete(filter);
                }

/* 206*/        IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
/* 207*/        FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(orgPK);
/* 208*/        String longNumber = fullOrgUnitInfo.getLongNumber();
/* 209*/        FullOrgUnitCollection fouc = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection((new StringBuilder("select id where isCompanyOrgUnit = 1 and longNumber like '")).append(longNumber).append("!%'").toString());
/* 210*/        HashSet lnUpsOrg = new HashSet();
/* 211*/        for(int i = 0; i < fouc.size(); i++)
/* 212*/            lnUpsOrg.add(fouc.get(i).getId().toString());

/* 214*/        filter = new FilterInfo();
/* 215*/        filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", lnUpsOrg, CompareType.INCLUDE));
/* 216*/        filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
/* 217*/        iCostAccount.delete(filter);
/* 218*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 0  where ffullOrgUnit = '")).append(orgPK.toString()).append("' and fisSource = 1 and fisEnable = 1  ").toString();
/* 219*/        DbUtil.execute(ctx, sql);
            }
            protected List _assignProjsCostAccount(Context ctx, IObjectPK projectPK)
                throws BOSException, EASBizException
            {








































































/* 296*/        FDCUtils.lockAssignCostAccount(ctx);

/* 298*/        ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
/* 299*/        CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(projectPK);
/* 300*/        String longNumber = curProjectInfo.getLongNumber();

/* 302*/        CostAccountCollection cac = getProjectCostAccounts(ctx, projectPK.toString());


/* 305*/        CurProjectCollection cpc = iCurProject.getCurProjectCollection((new StringBuilder("select * where longNumber like '")).append(longNumber).append("!%'").toString());


/* 308*/        Map projectMap = new HashMap();
                CurProjectInfo element;/* 309*/        for(Iterator iter = cpc.iterator(); iter.hasNext(); projectMap.put(element.getId().toString(), element.getDisplayName()))
/* 310*/            element = (CurProjectInfo)iter.next();



/* 314*/        EntityViewInfo view = new EntityViewInfo();
/* 315*/        FilterInfo filter = new FilterInfo();
/* 316*/        filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectMap.keySet(), CompareType.INCLUDE));
/* 317*/        view.setFilter(filter);
/* 318*/        view.getSelector().add("id");
/* 319*/        view.getSelector().add("longNumber");
/* 320*/        view.getSelector().add("isEnabled");
/* 321*/        view.getSelector().add("curProject.id");

/* 323*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);

/* 325*/        Map accMap = new HashMap();
/* 326*/        Map caMap = null;
                String comb;/* 327*/        for(Iterator iter = costAccountCollection.iterator(); iter.hasNext(); accMap.put(comb, caMap))
                {/* 328*/            CostAccountInfo element1 = (CostAccountInfo)iter.next();
/* 329*/            caMap = new HashMap();
/* 330*/            caMap.put("id", element1.getId().toString());
/* 331*/            caMap.put("isEnable", Boolean.valueOf(element1.isIsEnabled()));

/* 333*/            String projId = element1.getCurProject().getId().toString();
/* 334*/            String longNumber2 = element1.getLongNumber();
/* 335*/            comb = (new StringBuilder(String.valueOf(projId))).append("_").append(longNumber2).toString();
                }


/* 339*/        List errorList = insertCostAccount(ctx, projectMap.keySet(), cac, accMap, projectMap, true);

/* 341*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 1  where fCurproject = '")).append(projectPK.toString()).append("' and fisSource = 1 and fisEnable = 1  ").toString();
/* 342*/        DbUtil.execute(ctx, sql);



/* 346*/        String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
/* 347*/        DbUtil.execute(ctx, sqlNew);

/* 349*/        String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
/* 350*/        DbUtil.execute(ctx, sqlOld);

/* 352*/        return errorList;
            }
            private CostAccountCollection getCostAccountProjectOwn(Context ctx, String projectPK)
                throws BOSException
            {


/* 359*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select * where curProject.id = '")).append(projectPK).append("' and isSource = 1 order by longNumber").toString());

/* 361*/        return costAccountCollection;
            }
            private CostAccountCollection getEnCostAccountProjectOwn(Context ctx, String projectPK)
                throws BOSException
            {


/* 368*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select * where curProject.id = '")).append(projectPK).append("' and isSource = 1 and isEnabled = 1  order by longNumber").toString());

/* 370*/        return costAccountCollection;
            }
            private boolean isAssignToProject(Context ctx, String costAccountPK, String projectPK)
                throws BOSException
            {


/* 377*/        CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection((new StringBuilder("select id where srcCostAccountId = '")).append(costAccountPK).append("' and curProject.id = '").append(projectPK).append("'").toString());

/* 379*/        return costAccountCollection.size() != 0;
            }
            private CostAccountAssignInfo createCostAccountAssignProject(Context ctx, String costAccountPK, String projectPK)
                throws EASBizException, BOSException
            {






/* 390*/        CostAccountAssignInfo costAccountAssignInfo = new CostAccountAssignInfo();
/* 391*/        CostAccountInfo costAccountInfo = new CostAccountInfo();
/* 392*/        costAccountInfo.setId(BOSUuid.read(costAccountPK));
/* 393*/        CurProjectInfo curProjectInfo = new CurProjectInfo();
/* 394*/        curProjectInfo.setId(BOSUuid.read(projectPK));
/* 395*/        costAccountAssignInfo.setCostAccount(costAccountInfo);
/* 396*/        costAccountAssignInfo.setCurProject(curProjectInfo);
/* 397*/        return costAccountAssignInfo;
            }
            protected void _disAssignProjsCostAccount(Context ctx, IObjectPK projectPK)
                throws BOSException, EASBizException
            {








/* 410*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
/* 411*/        CostAccountCollection cac = getCostAccountProjectOwn(ctx, projectPK.toString());
/* 412*/        HashSet lnUps = new HashSet();
/* 413*/        for(int i = 0; i < cac.size(); i++)
/* 414*/            lnUps.add(cac.get(i).getId().toString());

/* 416*/        FilterInfo filter = new FilterInfo();
/* 417*/        if(lnUps.size() != 0)
                {
/* 419*/            filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));
/* 420*/            iCostAccount.delete(filter);
                }


/* 424*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 425*/        selector.add(new SelectorItemInfo("assigned"));
/* 426*/        for(int i = 0; i < cac.size(); i++)
                {/* 427*/            CostAccountInfo costAccountInfo = cac.get(i);
/* 428*/            costAccountInfo.setAssigned(false);
/* 429*/            iCostAccount.updatePartial(costAccountInfo, selector);
                }

/* 432*/        ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
/* 433*/        CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(projectPK);
/* 434*/        String longNumber = curProjectInfo.getLongNumber();
/* 435*/        CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection((new StringBuilder("select id where longNumber like '")).append(longNumber).append("!%'").toString());
/* 436*/        HashSet lnUpsProject = new HashSet();
/* 437*/        for(int i = 0; i < cpc.size(); i++)
/* 438*/            lnUpsProject.add(cpc.get(i).getId().toString());

/* 440*/        if(lnUpsProject.size() != 0)
                {/* 441*/            filter = new FilterInfo();
/* 442*/            filter.getFilterItems().add(new FilterItemInfo("curProject.id", lnUpsProject, CompareType.INCLUDE));
/* 443*/            filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
/* 444*/            iCostAccount.delete(filter);
                }
            }
            protected List _assignOrgProject(Context ctx, IObjectPK orgPK)
                throws BOSException, EASBizException
            {










/* 460*/        FDCUtils.lockAssignCostAccount(ctx);


/* 463*/        CostAccountCollection cac = getOrgCostAccounts(ctx, orgPK.toString());


/* 466*/        CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection((new StringBuilder("select * where fullOrgUnit.id = '")).append(orgPK.toString()).append("' ").toString());



/* 470*/        Map projectMap = new HashMap();
                CurProjectInfo element;/* 471*/        for(Iterator iter = cpc.iterator(); iter.hasNext(); projectMap.put(element.getId().toString(), element.getDisplayName()))
/* 472*/            element = (CurProjectInfo)iter.next();












/* 485*/        String countSql = "SELECT count(*) FROM T_FDC_COSTACCOUNT where  ";
/* 486*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 487*/        builder.appendSql(countSql);
/* 488*/        builder.appendParam("FCURPROJECT", projectMap.keySet().toArray());
/* 489*/        IRowSet rs = builder.executeQuery();
/* 490*/        int count = 0;
/* 491*/        Map accMap = new HashMap();
/* 492*/        Map caMap = null;











/* 504*/        try
                {
/* <-MISALIGNED-> */ /* 504*/            if(rs.next())
/* <-MISALIGNED-> */ /* 505*/                count = rs.getInt(1);/* 507*/            Set idSet = new HashSet();
/* 508*/            if(count > 1)
                    {/* 509*/                int pageSize = 5000;
/* 510*/                int pageCount = count / pageSize;
/* 511*/                if(count % pageSize > 0)
/* 512*/                    pageCount++;

/* 514*/                for(int pageNo = 1; pageNo <= pageCount; pageNo++)
                        {/* 515*/                    builder.clear();
/* 516*/                    builder.appendSql((new StringBuilder("SELECT TOP ")).append(pageSize).append(" * from ( ").toString());
/* 517*/                    builder.appendSql((new StringBuilder("SELECT TOP ")).append(pageSize * pageNo).append(" FID, FLONGNUMBER, FCURPROJECT, FISENABLE FROM T_FDC_COSTACCOUNT where ").toString());
/* 518*/                    builder.appendParam("FCURPROJECT", projectMap.keySet().toArray());
/* 519*/                    builder.appendSql(" order  by fid) t order by fid desc ");
                            String key;/* 520*/                    for(rs = builder.executeQuery(); rs.next(); accMap.put(key, caMap))
                            {
/* 522*/                        caMap = new HashMap();
/* 523*/                        idSet.add(rs.getString(1));
/* 524*/                        caMap.put("id", rs.getString(1));
/* 525*/                        caMap.put("isEnable", Boolean.valueOf(rs.getBoolean(4)));
/* 526*/                        key = (new StringBuilder(String.valueOf(rs.getString(3)))).append("_").append(rs.getString(2)).toString();
                            }
                        }
                    }

/* 531*/            System.out.println((new StringBuilder("$$$$$$$$$$_assignOrgProject.id.size: ")).append(idSet.size()).append("; count:").append(count).toString());
                }/* 532*/        catch(SQLException e)
                {/* 533*/            logger.error(e.getMessage(), e);
                }


/* 537*/        List errorList = insertCostAccount(ctx, projectMap.keySet(), cac, accMap, projectMap, true);

/* 539*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '")).append(orgPK.toString()).append("' and fisEnable = 1  ").toString();
/* 540*/        DbUtil.execute(ctx, sql);



/* 544*/        String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
/* 545*/        DbUtil.execute(ctx, sqlNew);

/* 547*/        String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
/* 548*/        DbUtil.execute(ctx, sqlOld);

/* 550*/        return errorList;
            }
            protected void _disAssignOrgProject(Context ctx, IObjectPK orgPK)
                throws BOSException, EASBizException
            {/* 554*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);


/* 557*/        CostAccountCollection cac = getCostAccountOrgOwn(ctx, orgPK.toString());
/* 558*/        HashSet lnUps = new HashSet();
/* 559*/        for(int i = 0; i < cac.size(); i++)
/* 560*/            lnUps.add(cac.get(i).getId().toString());

/* 562*/        FilterInfo filter = new FilterInfo();
/* 563*/        if(lnUps.size() != 0)
                {
/* 565*/            filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));
/* 566*/            iCostAccount.delete(filter);
                }

/* 569*/        CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection((new StringBuilder("select id where fullOrgUnit.id = '")).append(orgPK.toString()).append("'").toString());
/* 570*/        HashSet lnUpsProject = new HashSet();
/* 571*/        for(int i = 0; i < cpc.size(); i++)
/* 572*/            lnUpsProject.add(cpc.get(i).getId().toString());

/* 574*/        filter = new FilterInfo();
/* 575*/        filter.getFilterItems().add(new FilterItemInfo("curProject.id", lnUpsProject, CompareType.INCLUDE));
/* 576*/        filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
/* 577*/        iCostAccount.delete(filter);
/* 578*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 0  where ffullOrgUnit = '")).append(orgPK.toString()).append("' and fisSource = 1 and fisEnable = 1  ").toString();
/* 579*/        DbUtil.execute(ctx, sql);
            }
            private CostAccountCollection getOrgCostAccounts(Context ctx, String orgPK)
                throws BOSException, EASBizException
            {





/* 589*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);

/* 591*/        CostAccountCollection cac = getEnCostAccountOrgOwn(ctx, orgPK);
/* 592*/        CostAccountCollection caac = iCostAccount.getCostAccountCollection((new StringBuilder("select * where fullOrgUnit.id = '")).append(orgPK).append("' and isSource = 0 and isEnabled = 1 order by longNumber").toString());


/* 595*/        if(caac != null && caac.size() != 0)
                {/* 596*/            CostAccountCollection tmp = new CostAccountCollection();
/* 597*/            for(int i = 0; i < caac.size(); i++)
                    {/* 598*/                CostAccountInfo cai = null;

/* 600*/                try
                        {
/* <-MISALIGNED-> */ /* 600*/                    cai = iCostAccount.getCostAccountInfo(new ObjectUuidPK(caac.get(i).getSrcCostAccountId()));
                        }
/* <-MISALIGNED-> */ /* 601*/                catch(Exception exception) { }/* 604*/                if(cai != null)
/* 605*/                    tmp.add(cai);
                    }

/* 608*/            cac.addCollection(tmp);
                }
/* 610*/        return cac;
            }
            private CostAccountCollection getProjectCostAccounts(Context ctx, String projectPK)
                throws BOSException, EASBizException
            {




/* 619*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);

/* 621*/        CostAccountCollection cac = getEnCostAccountProjectOwn(ctx, projectPK);
/* 622*/        CostAccountCollection caac = iCostAccount.getCostAccountCollection((new StringBuilder("select * where curProject.id = '")).append(projectPK).append("' and isSource = 0 and isEnabled = 1 order by longNumber ").toString());

/* 624*/        if(caac != null && caac.size() != 0)
                {/* 625*/            CostAccountCollection tmp = new CostAccountCollection();



/* 629*/            for(int i = 0; i < caac.size(); i++)
                    {/* 630*/                EntityViewInfo evi = new EntityViewInfo();
/* 631*/                FilterInfo filter = new FilterInfo();
/* 632*/                filter.getFilterItems().add(new FilterItemInfo("id", caac.get(i).getSrcCostAccountId()));
/* 633*/                evi.setFilter(filter);




/* 638*/                CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* 639*/                if(costAccountCollection.size() != 0)
/* 640*/                    tmp.add(costAccountCollection.get(0));
                    }

/* 643*/            cac.addCollection(tmp);
                }

/* 646*/        return cac;
            }
            private CostAccountCollection disAssignSelectCostAccount(Context ctx, CostAccountInfo cai)
                throws BOSException, EASBizException
            {
/* 651*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
/* 652*/        CostAccountCollection say = new CostAccountCollection();
/* 653*/        if(cai.isIsSource())
                {
/* 655*/            EntityViewInfo evi = new EntityViewInfo();
/* 656*/            FilterInfo filter = new FilterInfo();
/* 657*/            filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getId().toString(), CompareType.EQUALS));
/* 658*/            filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));

/* 660*/            evi.setFilter(filter);
/* 661*/            evi.getSelector().add(new SelectorItemInfo("*"));
/* 662*/            evi.getSelector().add(new SelectorItemInfo("parent.*"));
/* 663*/            evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
/* 664*/            evi.getSelector().add(new SelectorItemInfo("curProject.*"));

/* 666*/            CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);

/* 668*/            SelectorItemCollection selector = new SelectorItemCollection();
/* 669*/            selector.add(new SelectorItemInfo("assigned"));
/* 670*/            if(costAccountCollection.size() != 0)
                    {
/* 672*/                for(int i = 0; i < costAccountCollection.size(); i++)
                        {/* 673*/                    CostAccountCollection tmp = iCostAccount.getCostAccountCollection((new StringBuilder("where parent.id = '")).append(costAccountCollection.get(i).getId().toString()).append("'").toString());
/* 674*/                    if(tmp.size() != 0)
                            {/* 675*/                        say.add(costAccountCollection.get(i));
                            } else
                            {/* 677*/                        BizReference ref = null;

/* 679*/                        try
                                {
/* <-MISALIGNED-> */ /* 679*/                            ref = ReferenceDAO.getReference(ctx, BOSUuid.read(costAccountCollection.get(i).getId().toString()));
                                }
/* <-MISALIGNED-> */ /* 680*/                        catch(Exception e)
                                {
/* <-MISALIGNED-> */ /* 681*/                            throw new ObjectReferedException(ref, e);
                                }
/* <-MISALIGNED-> */ /* 684*/                        if(ref != null)
/* <-MISALIGNED-> */ /* 685*/                            say.add(costAccountCollection.get(i));
/* <-MISALIGNED-> */ /* 687*/                        else
/* <-MISALIGNED-> */ /* 687*/                            iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
                            }
                        }
                    }
/* <-MISALIGNED-> */ /* 692*/            costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* <-MISALIGNED-> */ /* 693*/            SelectorItemCollection selector1 = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 694*/            selector1.add(new SelectorItemInfo("assigned"));
/* <-MISALIGNED-> */ /* 695*/            if(costAccountCollection.size() == 0)
                    {
/* <-MISALIGNED-> */ /* 696*/                cai.setAssigned(false);
/* <-MISALIGNED-> */ /* 697*/                iCostAccount.updatePartial(cai, selector1);
                    }
                } else
/* <-MISALIGNED-> */ /* 701*/        if(cai.getFullOrgUnit() != null)
                {
/* <-MISALIGNED-> */ /* 702*/            CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection((new StringBuilder("select id where fullOrgUnit.id = '")).append(cai.getFullOrgUnit().getId().toString()).append("' ").toString());
/* <-MISALIGNED-> */ /* 703*/            if(cpc.size() == 0)
                    {
/* <-MISALIGNED-> */ /* 704*/                EntityViewInfo evi = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 705*/                FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 706*/                filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
/* <-MISALIGNED-> */ /* 707*/                HashSet hs = getSubOrgUnit(ctx, cai.getFullOrgUnit().getLongNumber());
/* <-MISALIGNED-> */ /* 708*/                if(hs.size() != 0)
                        {
/* <-MISALIGNED-> */ /* 709*/                    filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", hs, CompareType.INCLUDE));
/* <-MISALIGNED-> */ /* 711*/                    evi.setFilter(filter);
/* <-MISALIGNED-> */ /* 712*/                    evi.getSelector().add(new SelectorItemInfo("*"));
/* <-MISALIGNED-> */ /* 713*/                    evi.getSelector().add(new SelectorItemInfo("parent.*"));
/* <-MISALIGNED-> */ /* 714*/                    evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
/* <-MISALIGNED-> */ /* 715*/                    evi.getSelector().add(new SelectorItemInfo("curProject.*"));
/* <-MISALIGNED-> */ /* 716*/                    CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* <-MISALIGNED-> */ /* 718*/                    SelectorItemCollection selector = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 719*/                    selector.add(new SelectorItemInfo("assigned"));
/* <-MISALIGNED-> */ /* 720*/                    if(costAccountCollection.size() != 0)
                            {
/* <-MISALIGNED-> */ /* 722*/                        for(int i = 0; i < costAccountCollection.size(); i++)
                                {
/* <-MISALIGNED-> */ /* 723*/                            CostAccountCollection tmp = iCostAccount.getCostAccountCollection((new StringBuilder("where parent.id = '")).append(costAccountCollection.get(i).getId().toString()).append("'").toString());
/* <-MISALIGNED-> */ /* 724*/                            if(tmp.size() != 0)
/* <-MISALIGNED-> */ /* 725*/                                say.add(costAccountCollection.get(i));/* 729*/                            else/* 729*/                                iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
                                }
                            }

/* 733*/                    costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* 734*/                    SelectorItemCollection selector1 = new SelectorItemCollection();
/* 735*/                    selector1.add(new SelectorItemInfo("assigned"));
/* 736*/                    if(costAccountCollection.size() == 0)
                            {/* 737*/                        cai.setAssigned(false);
/* 738*/                        iCostAccount.updatePartial(cai, selector1);
                            }
                        }
                    } else
                    {
/* 743*/                HashSet hs = new HashSet();
/* 744*/                for(int i = 0; i < cpc.size(); i++)
/* 745*/                    hs.add(cpc.get(i).getId().toString());

/* 747*/                EntityViewInfo evi = new EntityViewInfo();
/* 748*/                FilterInfo filter = new FilterInfo();
/* 749*/                filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
/* 750*/                filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
/* 751*/                evi.setFilter(filter);
/* 752*/                evi.getSelector().add(new SelectorItemInfo("*"));
/* 753*/                evi.getSelector().add(new SelectorItemInfo("parent.*"));
/* 754*/                evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
/* 755*/                evi.getSelector().add(new SelectorItemInfo("curProject.*"));
/* 756*/                CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* 757*/                if(costAccountCollection.size() != 0)
                        {
/* 759*/                    for(int i = 0; i < costAccountCollection.size(); i++)
                            {/* 760*/                        CostAccountCollection tmp = iCostAccount.getCostAccountCollection((new StringBuilder("where parent.id = '")).append(costAccountCollection.get(i).getId().toString()).append("'").toString());
/* 761*/                        if(tmp.size() != 0)
/* 762*/                            say.add(costAccountCollection.get(i));

/* 764*/                        else/* 764*/                            iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
                            }
                        }


/* 769*/                costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* 770*/                SelectorItemCollection selector = new SelectorItemCollection();
/* 771*/                selector.add(new SelectorItemInfo("assigned"));
/* 772*/                if(costAccountCollection.size() == 0)
                        {/* 773*/                    cai.setAssigned(false);
/* 774*/                    iCostAccount.updatePartial(cai, selector);
                        }
                    }
                } else
/* 778*/        if(cai.getCurProject() != null)
                {
/* 780*/            CurProjectInfo cpi = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo((new StringBuilder("select id,fullOrgUnit.id where id = '")).append(cai.getCurProject().getId().toString()).append("'").toString());
/* 781*/            if(cpi != null)
                    {/* 782*/                EntityViewInfo evi = new EntityViewInfo();
/* 783*/                FilterInfo filter = new FilterInfo();
/* 784*/                filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
/* 785*/                HashSet hs = getSubProject(ctx, cai.getCurProject().getLongNumber(), cpi.getFullOrgUnit().getId().toString());
/* 786*/                if(hs.size() != 0)
                        {/* 787*/                    filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
/* 788*/                    evi.setFilter(filter);
/* 789*/                    evi.getSelector().add(new SelectorItemInfo("*"));
/* 790*/                    evi.getSelector().add(new SelectorItemInfo("parent.*"));
/* 791*/                    evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
/* 792*/                    evi.getSelector().add(new SelectorItemInfo("curProject.*"));
/* 793*/                    CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);

/* 795*/                    if(costAccountCollection.size() != 0)
                            {
/* 797*/                        for(int i = 0; i < costAccountCollection.size(); i++)
                                {/* 798*/                            CostAccountCollection tmp = iCostAccount.getCostAccountCollection((new StringBuilder("where parent.id = '")).append(costAccountCollection.get(i).getId().toString()).append("'").toString());
/* 799*/                            if(tmp.size() != 0)
/* 800*/                                say.add(costAccountCollection.get(i));



/* 804*/                            else/* 804*/                                iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
                                }
                            }


/* 809*/                    costAccountCollection = iCostAccount.getCostAccountCollection(evi);
/* 810*/                    if(costAccountCollection.size() == 0)
                            {/* 811*/                        SelectorItemCollection selector = new SelectorItemCollection();
/* 812*/                        selector.add(new SelectorItemInfo("assigned"));
/* 813*/                        cai.setAssigned(false);
/* 814*/                        iCostAccount.updatePartial(cai, selector);
                            }
                        }
                    }
                }





/* 824*/        return say;
            }
            private HashSet getSubOrgUnit(Context ctx, String longNumber)
                throws BOSException
            {

/* 830*/        HashSet hs = new HashSet();
/* 831*/        FullOrgUnitCollection fouc = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection((new StringBuilder("select id where isCompanyOrgUnit = 1 and longNumber like '")).append(longNumber).append("!%'").toString());
/* 832*/        if(fouc.size() != 0)
                {/* 833*/            for(int i = 0; i < fouc.size(); i++)
/* 834*/                hs.add(fouc.get(i).getId().toString());
                }

/* 837*/        return hs;
            }
            private HashSet getSubProject(Context ctx, String longNumber, String ouid)
                throws BOSException
            {
/* 842*/        HashSet hs = new HashSet();
/* 843*/        CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection((new StringBuilder("select id where fullOrgUnit.id = '")).append(ouid).append("' and longNumber like '").append(longNumber).append("!%'").toString());
/* 844*/        if(cpc.size() != 0)
                {/* 845*/            for(int i = 0; i < cpc.size(); i++)
/* 846*/                hs.add(cpc.get(i).getId().toString());
                }

/* 849*/        return hs;
            }
            protected void _disAssignSelectOrgsCostAccount(Context ctx, IObjectCollection costAccounts)
                throws BOSException, EASBizException
            {/* 853*/        if(costAccounts.size() != 0)
                {
/* 855*/            ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
/* 856*/            CostAccountCollection cac = (CostAccountCollection)costAccounts;

/* 858*/            CostAccountCollection say = new CostAccountCollection();

/* 860*/            HashSet lnUps = new HashSet();





/* 866*/            for(int i = 0; i < costAccounts.size(); i++)
/* 867*/                say.addCollection(disAssignSelectCostAccount(ctx, cac.get(i)));



/* 871*/            if(say.size() != 0)
                    {/* 872*/                lnUps.clear();
/* 873*/                for(int i = 0; i < say.size(); i++)
/* 874*/                    lnUps.add(say.get(i).getName());



/* 878*/                throw new FDCBasedataException(FDCBasedataException.DISASSIGN_ISREFERENCE, new Object[] {/* 878*/                    lnUps
                        });
                    }
                }
            }
            protected void _disAssignSelectProjsCostAccount(Context context, IObjectCollection iobjectcollection)
                throws BOSException, EASBizException
            {
            }
            protected ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection costAccounts)
                throws BOSException, EASBizException
            {/* 889*/        ArrayList al = new ArrayList();
/* 890*/        ArrayList itemOnOrg = new ArrayList();
/* 891*/        ArrayList itemOnProject = new ArrayList();
/* 892*/        if(costAccounts.size() != 0)
                {/* 893*/            CostAccountCollection cac = (CostAccountCollection)costAccounts;
/* 894*/            CostAccountCollection say = new CostAccountCollection();


/* 897*/            ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
/* 898*/            CostAccountCollection cactemp = null;
/* 899*/            for(int i = 0; i < costAccounts.size(); i++)
                    {/* 900*/                cactemp = new CostAccountCollection();
/* 901*/                if(cac.get(i).getFullOrgUnit() != null)

/* 903*/                    cactemp = iCostAccount.getCostAccountCollection((new StringBuilder("select *,fullOrgUnit.* where longNumber like '")).append(cac.get(i).getLongNumber()).append("!%' and fullOrgUnit.id = '").append(cac.get(i).getFullOrgUnit().getId().toString()).append("' order by longNumber desc").toString());


/* 906*/                else/* 906*/                if(cac.get(i).getCurProject() != null)

/* 908*/                    cactemp = iCostAccount.getCostAccountCollection((new StringBuilder("select *,curproject.* where longNumber like '")).append(cac.get(i).getLongNumber()).append("!%' and curproject.id = '").append(cac.get(i).getCurProject().getId().toString()).append("' order by longNumber desc").toString());



/* 912*/                if(cactemp.size() != 0)
                        {/* 913*/                    for(int j = 0; j < cactemp.size(); j++)
/* 914*/                        say.addCollection(disAssignSelectCostAccount(ctx, cactemp.get(j)));
                        }

/* 917*/                say.addCollection(disAssignSelectCostAccount(ctx, cac.get(i)));
                    }

/* 920*/            CostAccountInfo cai = null;
/* 921*/            for(int i = 0; i < say.size(); i++)
                    {/* 922*/                cai = say.get(i);
/* 923*/                if(cai.getFullOrgUnit() != null)
/* 924*/                    itemOnOrg.add((new StringBuilder(String.valueOf(cai.getFullOrgUnit().getName()))).append("\u7EC4\u7EC7\u8282\u70B9\u4E0B\u4E0B").append(cai.getLongNumber().replace('!', '.')).toString());

/* 926*/                else/* 926*/                    itemOnProject.add((new StringBuilder(String.valueOf(cai.getCurProject().getName()))).append("\u5DE5\u7A0B\u9879\u76EE\u8282\u70B9\u4E0B").append(cai.getLongNumber().replace('!', '.')).toString());
                    }

/* 929*/            if(!itemOnOrg.isEmpty())
/* 930*/                al.add(itemOnOrg);

/* 932*/            if(!itemOnProject.isEmpty())
/* 933*/                al.add(itemOnProject);
                }

/* 936*/        return al;
            }
            protected void _handleEnterDB(Context ctx, IObjectValue model, boolean isEnterDB)
                throws BOSException, EASBizException
            {
/* 941*/        if(model == null)/* 941*/            return;
/* 942*/        CostAccountInfo acct = (CostAccountInfo)model;
/* 943*/        if(acct.getId() == null || acct.getLongNumber() == null)/* 943*/            return;
/* 944*/        if(acct.isIsEnterDB() == isEnterDB)
/* 945*/            return;



/* 949*/        try
                {
/* <-MISALIGNED-> */ /* 949*/            if(isEnterDB)
/* <-MISALIGNED-> */ /* 950*/                enterDB(ctx, acct);/* 952*/            else/* 952*/                cancleEnterDB(ctx, acct);
                }
/* 954*/        catch(Exception e)
                {/* 955*/            throw new BOSException(e);
                }
            }
            private void enterDB(Context ctx, CostAccountInfo acct)
                throws BOSException, EASBizException, SQLException
            {
/* 961*/        FDCSQLBuilder builder = new FDCSQLBuilder();

/* 963*/        String longNumber = (new StringBuilder(String.valueOf(acct.getLongNumber()))).append("!%").toString();
/* 964*/        String id = acct.getId().toString();
/* 965*/        builder.appendSql("update t_Fdc_Costaccount set fisenterdb=? where fid in (select fid from (");
/* 966*/        builder.appendSql(" (Select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
/* 967*/        builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where charIndex(c1.flongnumber,c2.flongnumber)>0 and c1.flevel<c2.flevel and");
/* 968*/        builder.appendSql(" (c1.ffullorgunit=c2.ffullorgunit or (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and");
/* 969*/        builder.appendSql(" (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null and c2.fcurproject is null)))");
/* 970*/        builder.appendSql(" union (select ? as fid)");
/* 971*/        builder.appendSql(" union (select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
/* 972*/        builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
/* 973*/        builder.appendSql(" (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null");
/* 974*/        builder.appendSql(" and c2.fcurproject is null))))a);");

/* 976*/        builder.addParam(new Integer(1));
/* 977*/        builder.addParam(id);
/* 978*/        builder.addParam(id);
/* 979*/        builder.addParam(id);
/* 980*/        builder.addParam(longNumber);












/* 993*/        builder.executeUpdate(ctx);
            }
            private void cancleEnterDB(Context ctx, CostAccountInfo acct)
                throws BOSException, EASBizException, SQLException
            {
/* 998*/        String longNumber = (new StringBuilder(String.valueOf(acct.getLongNumber()))).append("!%").toString();
/* 999*/        String id = acct.getId().toString();

/*1001*/        FDCSQLBuilder builder = new FDCSQLBuilder();

/*1003*/        builder.appendSql("update t_Fdc_Costaccount set fisenterdb=? where fid in (select fid from ");
/*1004*/        builder.appendSql(" ((select ? as fid)");
/*1005*/        builder.appendSql(" union (select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
/*1006*/        builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
/*1007*/        builder.appendSql(" (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null");
/*1008*/        builder.appendSql(" and c2.fcurproject is null)))) a );");

/*1010*/        builder.addParam(new Integer(0));
/*1011*/        builder.addParam(id);
/*1012*/        builder.addParam(id);
/*1013*/        builder.addParam(longNumber);
/*1014*/        builder.executeUpdate(ctx);



/*1018*/        builder.clear();
/*1019*/        builder.appendSql("select fparentid from T_FDC_CostAccount where fid=?");
/*1020*/        builder.addParam(id);
/*1021*/        IRowSet rowSet = builder.executeQuery(ctx);
/*1022*/        if(rowSet.size() != 1)
/*1023*/            return;


/*1026*/        rowSet.next();
/*1027*/        String pid = rowSet.getString("fparentid");
/*1028*/        Set set = new HashSet();

/*1030*/        for(; pid != null; pid = rowSet.getString("fparentid"))
                {
/* <-MISALIGNED-> */ /*1030*/            builder.clear();
/* <-MISALIGNED-> */ /*1031*/            builder.appendSql("select fisenterdb from T_FDC_CostAccount where fparentid=? and fisenterdb=1");
/* <-MISALIGNED-> */ /*1032*/            builder.addParam(pid);
/* <-MISALIGNED-> */ /*1033*/            if(set.size() > 0)
                    {
/* <-MISALIGNED-> */ /*1034*/                builder.appendSql(" and fid not in (");
/* <-MISALIGNED-> */ /*1035*/                builder.appendParam(set.toArray());
/* <-MISALIGNED-> */ /*1036*/                builder.appendSql(")");
                    }
/* <-MISALIGNED-> */ /*1038*/            rowSet = builder.executeQuery(ctx);
/* <-MISALIGNED-> */ /*1039*/            if(rowSet.size() > 0)
/* <-MISALIGNED-> */ /*1040*/                break;
/* <-MISALIGNED-> */ /*1042*/            builder.clear();
/* <-MISALIGNED-> */ /*1043*/            set.add(pid);
/* <-MISALIGNED-> */ /*1044*/            builder.appendSql("select fparentid from T_FDC_CostAccount where fid=?");
/* <-MISALIGNED-> */ /*1045*/            builder.addParam(pid);
/* <-MISALIGNED-> */ /*1046*/            rowSet = builder.executeQuery(ctx);
/* <-MISALIGNED-> */ /*1047*/            if(rowSet.size() != 1)
/* <-MISALIGNED-> */ /*1048*/                break;
/*1051*/            rowSet.next();
                }



/*1056*/        if(set.size() > 0)
                {/*1057*/            builder.clear();
/*1058*/            builder.appendSql("update T_FDC_CostAccount set FIsEnterDB=? where ");
/*1059*/            builder.addParam(new Integer(0));
/*1060*/            builder.appendParam("fid", set.toArray());
/*1061*/            builder.executeUpdate(ctx);
                }
            }
            protected List _assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet)
                throws BOSException, EASBizException
            {



/*1070*/        FDCUtils.lockAssignCostAccount(ctx);

/*1072*/        ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);

/*1074*/        CostAccountCollection cac = iCostAccount.getCostAccountCollection((new StringBuilder("select * where fullOrgUnit.id = '")).append(orgPK.toString()).append("' and isEnabled = 1 order by longNumber").toString());


/*1077*/        Map projectMap = new HashMap();
/*1078*/        if(isProjectSet)
                {/*1079*/            EntityViewInfo pView = new EntityViewInfo();
/*1080*/            FilterInfo pFilter = new FilterInfo();
/*1081*/            pFilter.getFilterItems().add(new FilterItemInfo("id", nodeIds, CompareType.INCLUDE));
/*1082*/            pView.setFilter(pFilter);
/*1083*/            pView.getSelector().add("id");
/*1084*/            pView.getSelector().add("displayName");
/*1085*/            CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(pView);
                    CurProjectInfo element;/*1086*/            for(Iterator iter = cpc.iterator(); iter.hasNext(); projectMap.put(element.getId().toString(), element.getDisplayName()))
/*1087*/                element = (CurProjectInfo)iter.next();
                }



/*1092*/        EntityViewInfo view = new EntityViewInfo();
/*1093*/        FilterInfo filter = new FilterInfo();
/*1094*/        filter.getFilterItems().add(new FilterItemInfo(isProjectSet ? "curProject.id" : "fullOrgUnit.id", nodeIds, CompareType.INCLUDE));
/*1095*/        view.setFilter(filter);
/*1096*/        view.getSelector().add("id");
/*1097*/        view.getSelector().add("isEnabled");
/*1098*/        view.getSelector().add("longNumber");
/*1099*/        view.getSelector().add(isProjectSet ? "curProject.id" : "fullOrgUnit.id");

/*1101*/        CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(view);
/*1102*/        Map accMap = new HashMap();
/*1103*/        Map caMap = null;
/*1104*/        String ownerId = null;
                CostAccountInfo element;/*1105*/        for(Iterator iter = costAccountCollection.iterator(); iter.hasNext(); accMap.put((new StringBuilder(String.valueOf(ownerId))).append("_").append(element.getLongNumber()).toString(), caMap))
                {/*1106*/            element = (CostAccountInfo)iter.next();
/*1107*/            caMap = new HashMap();
/*1108*/            caMap.put("id", element.getId().toString());
/*1109*/            caMap.put("isEnable", Boolean.valueOf(element.isIsEnabled()));
/*1110*/            ownerId = isProjectSet ? element.getCurProject().getId().toString() : element.getFullOrgUnit().getId().toString();
                }


/*1114*/        List errorList = insertCostAccount(ctx, nodeIds, cac, accMap, projectMap, isProjectSet);

/*1116*/        String sql = (new StringBuilder("update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '")).append(orgPK.toString()).append("' and fisEnable = 1  ").toString();


/*1119*/        DbUtil.execute(ctx, sql);



/*1123*/        String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
/*1124*/        DbUtil.execute(ctx, sqlNew);

/*1126*/        String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
/*1127*/        DbUtil.execute(ctx, sqlOld);

/*1129*/        return errorList;
            }
            private List insertCostAccount(Context ctx, Set nodeIds, CostAccountCollection cac, Map accMap, Map ownerMap, boolean isProjectSet)
                throws BOSException, EASBizException
            {
                List errorList;
                Map parentMap;
                Connection connection;
                PreparedStatement insertStmt;
                PreparedStatement updateStmt;
/* <-MISALIGNED-> */ /*1133*/        errorList = new ArrayList();
/* <-MISALIGNED-> */ /*1134*/        parentMap = new HashMap();
/* <-MISALIGNED-> */ /*1136*/        connection = null;
/* <-MISALIGNED-> */ /*1137*/        insertStmt = null;
/* <-MISALIGNED-> */ /*1138*/        updateStmt = null;
/* <-MISALIGNED-> */ /*1140*/        try
                {
/* <-MISALIGNED-> */ /*1140*/            connection = getConnection(ctx);
/* <-MISALIGNED-> */ /*1141*/            insertStmt = connection.prepareStatement(insert_sql);
/* <-MISALIGNED-> */ /*1142*/            updateStmt = connection.prepareStatement(update_sql);
/* <-MISALIGNED-> */ /*1143*/            Timestamp now = new Timestamp(System.currentTimeMillis());
/* <-MISALIGNED-> */ /*1144*/            String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
/* <-MISALIGNED-> */ /*1146*/            Locale l1 = new Locale("L1");
/* <-MISALIGNED-> */ /*1147*/            Locale l2 = new Locale("L2");
/* <-MISALIGNED-> */ /*1148*/            Locale l3 = new Locale("L3");
/* <-MISALIGNED-> */ /*1152*/            Set acctIdSet = new HashSet();
/* <-MISALIGNED-> */ /*1153*/            Iterator tor = nodeIds.iterator();
/* <-MISALIGNED-> */ /*1154*/            String longNumber = "";/*1156*/            for(; tor.hasNext(); parentMap.clear())
                    {
/* <-MISALIGNED-> */ /*1156*/                String nodeId = tor.next().toString();
/* <-MISALIGNED-> */ /*1157*/                for(int j = 0; j < cac.size(); j++)
                        {
/* <-MISALIGNED-> */ /*1158*/                    String comb = (new StringBuilder(String.valueOf(nodeId))).append("_").append(cac.get(j).getLongNumber()).toString();
/* <-MISALIGNED-> */ /*1160*/                    if(accMap.containsKey(comb))/*1162*/                        continue;/*1162*/                    CostAccountInfo info = cac.get(j);
/*1163*/                    BOSUuid uuid = BOSUuid.create(info.getBOSType());
/*1164*/                    String id = uuid.toString();
/*1165*/                    acctIdSet.add(id);
/*1166*/                    longNumber = info.getLongNumber();

/*1168*/                    boolean isEnable = info.isIsEnabled();
/*1169*/                    String parentlongNumber = null;
/*1170*/                    String parentId = null;
/*1171*/                    int idx = longNumber.lastIndexOf("!");
/*1172*/                    if(idx > 0)
                            {/*1173*/                        parentlongNumber = longNumber.substring(0, idx);
/*1174*/                        String key = (new StringBuilder(String.valueOf(nodeId))).append("_").append(parentlongNumber).toString();

/*1176*/                        Map ca = null;
/*1177*/                        if(accMap.containsKey(key))
/*1178*/                            ca = (HashMap)accMap.get(key);

/*1180*/                        if(ca != null)
                                {/*1181*/                            isEnable = ((Boolean)ca.get("isEnable")).booleanValue();
/*1182*/                            parentId = ca.get("id").toString();
/*1183*/                            parentMap.put(parentlongNumber, parentId);
/*1184*/                            if(isProjectSet)
                                    {
/*1186*/                                BizReference reference = ReferenceDAO.getReference(ctx, BOSUuid.read(parentId));
/*1187*/                                if(reference != null && ownerMap != null && !reference.getRefEntityName().equalsIgnoreCase("CostAccountWithAccount"))
                                        {
/*1189*/                                    String errorInfo = (new StringBuilder()).append(ownerMap.get(nodeId)).append(" ").append(info.getLongNumber()).toString();
/*1190*/                                    errorList.add(errorInfo);
/*1191*/                                    continue;
                                        }
                                    }
                                }

/*1196*/                        if(parentId == null)/*1196*/                            continue;
                            }

/*1199*/                    int x = 1;
/*1200*/                    insertStmt.setString(x++, id);
/*1201*/                    insertStmt.setString(x++, userId);
/*1202*/                    insertStmt.setTimestamp(x++, now);
/*1203*/                    insertStmt.setString(x++, userId);
/*1204*/                    insertStmt.setTimestamp(x++, now);
/*1205*/                    insertStmt.setString(x++, info.getCU().getId().toString());

/*1207*/                    insertStmt.setString(x++, info.getName(l1));
/*1208*/                    insertStmt.setString(x++, info.getName(l2));
/*1209*/                    insertStmt.setString(x++, info.getName(l3));
/*1210*/                    insertStmt.setString(x++, info.getNumber());
/*1211*/                    insertStmt.setString(x++, info.getDescription(l1));
/*1212*/                    insertStmt.setString(x++, info.getDescription(l2));
/*1213*/                    insertStmt.setString(x++, info.getDescription(l3));
/*1214*/                    insertStmt.setString(x++, info.getSimpleName());
/*1215*/                    insertStmt.setBoolean(x++, true);
/*1216*/                    insertStmt.setInt(x++, info.getLevel());
/*1217*/                    insertStmt.setString(x++, longNumber);
/*1218*/                    insertStmt.setString(x++, info.getDisplayName(l1));
/*1219*/                    insertStmt.setString(x++, info.getDisplayName(l2));
/*1220*/                    insertStmt.setString(x++, info.getDisplayName(l3));
/*1221*/                    insertStmt.setBoolean(x++, false);

/*1223*/                    insertStmt.setBoolean(x++, isEnable);
/*1224*/                    insertStmt.setString(x++, isProjectSet ? nodeId : null);

/*1226*/                    if(parentId == null && parentlongNumber != null)
/*1227*/                        parentId = (String)parentMap.get(parentlongNumber);

/*1229*/                    if(parentId != null)
                            {/*1230*/                        DbUtil.prepareVarcharParam(updateStmt, 1, parentId);
/*1231*/                        updateStmt.addBatch();
                            }
/*1233*/                    insertStmt.setString(x++, parentId);

/*1235*/                    insertStmt.setString(x++, isProjectSet ? null : nodeId);
/*1236*/                    insertStmt.setBoolean(x++, false);
/*1237*/                    insertStmt.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcCostAccountId());
/*1238*/                    String type = info.getType() != null ? info.getType().getValue() : null;

/*1240*/                    type = info.getLevel() != 1 ? type : null;
/*1241*/                    insertStmt.setString(x++, type);
/*1242*/                    insertStmt.setBoolean(x++, info.isIsCostAccount());
/*1243*/                    insertStmt.setString(x++, info.getCodingNumber());
/*1244*/                    insertStmt.setBoolean(x++, info.isIsProgramming());
							insertStmt.setBoolean(x++, info.isIsMarket());
							String yjType=info.getYjType() != null ? info.getYjType().getValue() : null;
							insertStmt.setString(x++, yjType);
/*1245*/                    parentMap.put(info.getLongNumber(), id);

/*1247*/                    info.setId(uuid);


/*1250*/                    info.setIsSource(false);

/*1252*/                    Map caMap = new HashMap();
/*1253*/                    caMap.put("id", id);
/*1254*/                    caMap.put("isEnable", Boolean.valueOf(isEnable));
/*1255*/                    accMap.put((new StringBuilder(String.valueOf(nodeId))).append("_").append(longNumber).toString(), caMap);
/*1256*/                    insertStmt.addBatch();
                        }
                    }



/*1262*/            insertStmt.executeBatch();
/*1263*/            updateStmt.executeBatch();

/*1265*/            if(acctIdSet.size() > 0)
                    {/*1266*/                Map param = new HashMap();
/*1267*/                param.put("acctIdSet", acctIdSet);
/*1268*/                CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
                    }
                }/*1270*/        catch(SQLException ex)
                {/*1271*/            throw new BOSException(ex);
                }
                return errorList;
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountFacadeControllerBean");
            private static String insert_sql = "insert into t_fdc_costAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCCOSTACCOUNTID, FTYPE,FIsCostAccount,FCodingNumber,FIsProgramming,FisMarket,FYjType) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            private static String update_sql = "update t_fdc_costAccount set FIsLeaf=0 where FID=?";
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_basedata_server.jar
	Total time: 140 ms
	Jad reported messages/errors:
Couldn't fully decompile method _assignOrgsCostAccount
Couldn't resolve all exception handlers in method _assignOrgsCostAccount
Couldn't fully decompile method insertCostAccount
Couldn't resolve all exception handlers in method insertCostAccount
	Exit status: 0
	Caught exceptions:
*/