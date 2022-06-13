/*jadclipse*/package com.kingdee.eas.fi.gr.cslrpt.util;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.SyntaxErrorException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.*;
import com.kingdee.bos.ctrl.excel.model.struct.undo.UndoManager;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gr.cslrpt.*;
import com.kingdee.eas.fi.rpt.TableToolkit;
import com.kingdee.eas.fi.rpt.util.UserObjectHelper;
import com.kingdee.util.StringUtils;
import java.util.*;
public final class MnsRptGuideHelper
{
            private MnsRptGuideHelper()
            {
            }
            public static boolean isSupportCrossMerger(Context ctx, OrgTreeInfo orgtree)
            {







































































/*  97*/        return CSLParamUtil.isSupportCrossMerger(ctx, orgtree.getId().toString());
            }
            public static Book fillMnsRptSheet(Context ctx, List companys, Book book)
                throws EASBizException, BOSException
            {












/* 114*/        return prepareSheetData(ctx, companys, book);
            }
            public static Book fillMnsRptSheet(Context ctx, OrgTreeInfo orgTree, OrgUnitInfo orgUnitGroup, Book book)
                throws EASBizException, BOSException
            {













/* 132*/        List companys = getCompanys(ctx, orgTree, orgUnitGroup);
/* 133*/        return prepareSheetData(ctx, companys, book);
            }
            private static Book prepareSheetData(Context ctx, List companys, Book book)
                throws BOSException
            {
                boolean autoCalculate;















/* 154*/        if(companys == null)
/* 155*/            return book;

/* 157*/        autoCalculate = book.isAutoCalculate();
/* 158*/        book.setCalculate(false);
                Book book1;
/* 160*/        try
                {
/* <-MISALIGNED-> */ /* 160*/            book.getUndoManager().enable(false);/* 162*/            book.getUndoManager().startGroup();

/* 164*/            ArrayList ls = null;

/* 166*/            ItemFormula tmpFormula = null;
/* 167*/            int colWidth = 0;


/* 170*/            for(int x = 0; x < book.getSheetCount(); x++)
                    {/* 171*/                Sheet sheet = book.getSheet(x);

/* 173*/                if(!sheet.isEnableCalculation())
/* 174*/                    continue;


/* 177*/                Protection pro = sheet.getSheetOption().getProtection(false);
/* 178*/                boolean willProtect = false;
/* 179*/                String proPass = null;
/* 180*/                if(pro != null && pro.isProtected())
                        {/* 181*/                    proPass = pro.getEncryptedPassword();
/* 182*/                    pro.setPassword(proPass);
/* 183*/                    pro.stop(proPass);
/* 184*/                    willProtect = true;
                        }


/* 188*/                int left = 0;
/* 189*/                int top = 0;

/* 191*/                if(sheet != null)
                        {/* 192*/                    left = CslRptUtil.getBasePosition_Left(sheet);
/* 193*/                    top = CslRptUtil.getBasePosition_Top(sheet);

/* 195*/                    left = left < 0 ? 0 : left;
/* 196*/                    top = top < 0 ? 0 : top;
                        }

/* 199*/                ls = new ArrayList();


/* 202*/                int curComIxpos = 0;

/* 204*/                int count = 0;
/* 205*/                int curOriginal = 0;

/* 207*/                List dataElementList = new ArrayList();
/* 208*/                Set dataElementSet = new HashSet();
/* 209*/                List creditAndDebitLine = new ArrayList();
/* 210*/                Map mapSumItem = null;

/* 212*/                int maxCol = sheet.getMaxColIndex();



/* 216*/                int singleColCount = 0;/* 216*/                int mm = left;

/* 218*/                List removeColList = new ArrayList();
/* 219*/                int i = left;/* 219*/                for(int n = maxCol; i <= n; i++)
                        {/* 220*/                    Cell curCell = sheet.getCell(top, i, false);
/* 221*/                    if(curCell == null)
/* 222*/                        continue;/* 222*/                    ItemFormula itemFormula = (ItemFormula)curCell.getUserObjectValue("ITEM");


/* 225*/                    if(itemFormula == null)
/* 226*/                        mm++;


/* 229*/                    if(itemFormula == null || itemFormula.getExt("DataSrc") == null)


/* 232*/                        continue;/* 232*/                    Integer dataSrc = Integer.valueOf(itemFormula.getExt("DataSrc"));

/* 234*/                    if(dataSrc.intValue() == 1)
                            {
/* 236*/                        String currDataElementNumber = itemFormula.getDataElementNumber();
/* 237*/                        if(!dataElementSet.contains(currDataElementNumber))
                                {/* 238*/                            dataElementList.add(itemFormula);
/* 239*/                            dataElementSet.add(currDataElementNumber);
/* 240*/                            mm++;
                                }
/* 242*/                        singleColCount++;
/* 243*/                        Cell nextCell = sheet.getCell(top, i + 1, false);
/* 244*/                        if(nextCell != null)
                                {/* 245*/                            ItemFormula nextCellItemFormula = (ItemFormula)nextCell.getUserObjectValue("ITEM");
/* 246*/                            if(nextCellItemFormula != null && nextCellItemFormula.getExt("DataSrc") != null)
                                    {/* 247*/                                Integer nextItemDataSrc = Integer.valueOf(nextCellItemFormula.getExt("DataSrc"));
/* 248*/                                if(nextItemDataSrc.intValue() == 1)
/* 249*/                                    continue;
                                    }
                                }






/* 258*/                        int dataElementSize = dataElementList.size();

/* 260*/                        curComIxpos = (mm - dataElementSize) + 1;

/* 262*/                        curOriginal = curComIxpos;





/* 268*/                        count = 0;
/* 269*/                        Iterator iterCompany = companys.iterator();
/* 270*/                        int pos = 0;

/* 272*/                        while(iterCompany.hasNext()) 
                                {/* 273*/                            OrgUnitInfo curCom = (OrgUnitInfo)iterCompany.next();
/* 274*/                            pos = 0;
/* 275*/                            Iterator iterDataElement = dataElementList.iterator();/* 275*/                            while(iterDataElement.hasNext()) 
                                    {/* 276*/                                tmpFormula = (ItemFormula)((ItemFormula)iterDataElement.next()).clone();
/* 277*/                                tmpFormula.putExt("Company", curCom.getId().toString());
/* 278*/                                tmpFormula.putExt("CompanyNumber", curCom.getNumber());

/* 280*/                                tmpFormula.putExt("CompanyName", curCom.getName());
/* 281*/                                tmpFormula.putExt("Cover", Boolean.valueOf(count < singleColCount).toString());
/* 282*/                                tmpFormula.putExt("Index", Integer.toString(curComIxpos));
/* 283*/                                tmpFormula.putExt("Original", Integer.toString(curOriginal + pos));
/* 284*/                                ls.add(tmpFormula);


/* 287*/                                pos++;
/* 288*/                                curComIxpos++;
/* 289*/                                count++;
                                    }
                                }
/* 292*/                        if(count < singleColCount)
                                {/* 293*/                            removeColList.add(new int[] {/* 293*/                                curComIxpos, curComIxpos + (singleColCount - count - 1)
                                    });/* 294*/                            mm = (mm + singleColCount) - 1;
                                } else
                                {/* 296*/                            mm = (mm + count) - 1;
                                }
/* 298*/                        singleColCount = 0;
/* 299*/                        for(int j = (i - dataElementSize) + 1; j <= i; j++)
/* 300*/                            creditAndDebitLine.add(Integer.valueOf(j));


/* 303*/                        dataElementList.clear();
/* 304*/                        dataElementSet.clear();







/* 312*/                        continue;
                            }
/* <-MISALIGNED-> */ /* 312*/                    if(dataSrc.intValue() == 5 || dataSrc.intValue() == 4 || dataSrc.intValue() == 6 || dataSrc.intValue() == 2)
                            {

/* 317*/                        creditAndDebitLine.add(Integer.valueOf(i));
/* 318*/                        mm++;
/* 319*/                        continue;
                            }
/* <-MISALIGNED-> */ /* 319*/                    if(dataSrc.intValue() != 3)
/* <-MISALIGNED-> */ /* 320*/                        continue;
/* <-MISALIGNED-> */ /* 320*/                    mm++;/* 323*/                    if(creditAndDebitLine.size() == 0)
/* 324*/                        continue;

/* 326*/                    if(mapSumItem == null)
/* 327*/                        mapSumItem = new HashMap();

/* 329*/                    compatibleOldTemplate(ctx, sheet, creditAndDebitLine, i, mapSumItem);
                        }





/* 336*/                int ixPos = 0;
/* 337*/                int ixOriginal = 0;
/* 338*/                Iterator iterTableCons = ls.iterator();
                        StyleAttributes sa2;
/* 340*/                StyleAttributes sa1 = sa2 = Styles.getEmptySA();

/* 342*/                for(; iterTableCons.hasNext(); ixPos++)
                        {/* 343*/                    tmpFormula = (ItemFormula)iterTableCons.next();
/* 344*/                    ixPos = Integer.valueOf(tmpFormula.getExt("Index")).intValue();
/* 345*/                    ixOriginal = Integer.valueOf(tmpFormula.getExt("Original")).intValue();
/* 346*/                    if(!Boolean.valueOf(tmpFormula.getExt("Cover")).booleanValue())
                            {
/* 348*/                        int m = 0;/* 348*/label0:/* 348*/                        for(int s1 = book.getSheetCount(); m < s1; m++)
                                {/* 349*/                            Sheet sheet2 = book.getSheet(m);
/* 350*/                            if(sheet2.getSheetName().equalsIgnoreCase(sheet.getSheetName()))
/* 351*/                                continue;

/* 353*/                            int p = 0;/* 353*/                            int s2 = sheet2.getMaxColIndex();/* 353*/                            do
                                    {
/* <-MISALIGNED-> */ /* 353*/                                if(p >= s2)
/* <-MISALIGNED-> */ /* 354*/                                    continue label0;
/* <-MISALIGNED-> */ /* 354*/                                int q = 0;
/* <-MISALIGNED-> */ /* 354*/                                for(int s3 = sheet2.getMaxRowIndex(); q < s3; q++)
/* <-MISALIGNED-> */ /* 355*/                                    sheet2.getCell(q, p, true).getFormula();
/* <-MISALIGNED-> */ /* 353*/                                p++;
                                    } while(true);
                                }
/* <-MISALIGNED-> */ /* 360*/                        sheet.getColRange(ixPos, ixPos).insert(false, InsertType.NOSTYLE);
/* <-MISALIGNED-> */ /* 362*/                        colWidth = Sheet.getColWidth(sheet, ixOriginal);
/* <-MISALIGNED-> */ /* 363*/                        sheet.getColSpans().setSpanAttribute(new Span(ixPos, ixPos), null, Integer.valueOf(colWidth), null, null, null, null);
/* <-MISALIGNED-> */ /* 364*/                        autoFillFormula(sheet, ixPos);

/* 368*/                        if(mapSumItem != null && !mapSumItem.isEmpty() && !StringUtils.isEmpty(tmpFormula.getExt("Company")))
                                {
/* 370*/                            Iterator ite = mapSumItem.keySet().iterator();
/* 371*/                            Cell cell = null;
/* 372*/                            for(; ite.hasNext(); cell.setUserObject("1", null))
                                    {/* 373*/                                Integer row = (Integer)ite.next();
/* 374*/                                List lstTemp = (List)mapSumItem.get(row);
/* 375*/                                String strHeadKey = (String)lstTemp.get(0);
/* 376*/                                String strExp = (String)lstTemp.get(1);

/* 378*/                                String strHeadKeyNew = TableToolkit.int2pos(ixPos);
/* 379*/                                cell = sheet.getCell(row.intValue(), ixPos, true);
/* 380*/                                cell.setFormula(strExp.replaceAll(strHeadKey, strHeadKeyNew));
/* 381*/                                cell.setUserObject("0", null);
                                    }
                                }
                            }


/* 387*/                    if(tmpFormula.getExt("Company") == null)
/* 388*/                        continue;/* 388*/                    Cell tmpCell = sheet.getCell(top, ixPos, true);
/* 389*/                    sheet.getRange(top, ixOriginal).getStyle(sa1, sa2);
/* 390*/                    sa1.setDirty(StyleAttributes.getAllBits());
/* 391*/                    sa2.setDirty(StyleAttributes.getAllBits());

/* 393*/                    if(tmpCell != null && tmpFormula.getExt("CompanyName") != null)
                            {/* 394*/                        String columnValue = tmpFormula.getExt("CompanyName") + "(" + getDataElementName(ctx, tmpFormula.getDataElementNumber()) + ")";



/* 398*/                        tmpCell.setValue(new Variant(columnValue));
/* 399*/                        tmpFormula.removeExt("CompanyName");
                            }
/* 401*/                    tmpFormula.removeExt("Index");
/* 402*/                    tmpFormula.removeExt("Cover");
/* 403*/                    tmpFormula.removeExt("Original");

/* 405*/                    if(tmpCell != null && !tmpCell.getValue().isEmpty())
                            {/* 406*/                        sheet.getRange(tmpCell).setStyle(sa1, sa2);

/* 408*/                        int row = top + 1;/* 408*/                        for(int n = sheet.getMaxRowIndex(); row <= n; row++)
                                {/* 409*/                            Cell cellBase = sheet.getCell(row, left, true);
/* 410*/                            if(cellBase != null)
                                    {/* 411*/                                sheet.getRange(row, ixOriginal).getStyle(sa1, sa2);
/* 412*/                                sa1.setDirty(StyleAttributes.getAllBits());
/* 413*/                                sa2.setDirty(StyleAttributes.getAllBits());
/* 414*/                                sheet.getRange(row, ixPos).setStyle(sa1, sa2);
                                    }
                                }
                            }

/* 419*/                    if(tmpCell != null)
                            {/* 420*/                        tmpCell.setUserObject("ITEM", tmpFormula);
/* 421*/                        sheet.getRange(tmpCell).setCellLocked(true);
                            }
                        }




/* 428*/                for(int j = removeColList.size() - 1; j >= 0; j--)
                        {/* 429*/                    int col[] = (int[])(int[])removeColList.get(j);
/* 430*/                    UserObjectHelper.getUnprotectedRange(sheet.getColRange(col[0], col[1])).delete();
                        }


/* 434*/                if(willProtect)
                        {

/* 437*/                    pro.deleteOperation(32);
/* 438*/                    pro.startWithEncryptedPassword(proPass);
                        }
                    }

/* 442*/            clearCellFormulaByDataCollect(book);

/* 444*/            book1 = book;
                }/* 445*/        catch(Exception e)
                {/* 446*/            throw new BOSException(e);
                }
/* 448*/        book.getUndoManager().enable(true);
/* 449*/        book.getUndoManager().endGroup();
/* 450*/        book.setCalculate(autoCalculate);
/* 451*/        return book1;
            }
            private static String getDataElementName(Context ctx, String number)
            {



/* 464*/        if(ctx == null)
/* 465*/            return DataElementProvider.getDataElementName(number);

/* 467*/        else/* 467*/            return DataElementProvider.getDataElementName(ctx, number);
            }
            private static void compatibleOldTemplate(Context ctx, Sheet sheet, List creditAndDebitLine, int i, Map mapSumItem)
                throws BOSException
            {










/* 482*/        List cdAliasLine = new ArrayList();

/* 484*/        for(int k = 0; k < creditAndDebitLine.size(); k++)
                {/* 485*/            String cellName = Location.getCellName(0, ((Integer)creditAndDebitLine.get(k)).intValue());

/* 487*/            cdAliasLine.add(cellName.substring(0, cellName.length() - 1));
                }
/* 489*/        String cellName = Location.getCellName(0, i);
/* 490*/        String colAliase = cellName.substring(0, cellName.length() - 1);


/* 493*/        Map RPTItemInfoMap = getRPTItemInfoList(ctx, sheet, i);
/* 494*/        String regex = "=(.+!)?[A-Z]+\\d+([\\+|\\-](.+!)?[A-Z]+\\d+)*";
/* 495*/        String exp = null;
/* 496*/        ItemFormula item = null;
/* 497*/        int j = 0;/* 497*/        for(int m = sheet.getMaxRowIndex(); j <= m; j++)
                {/* 498*/            Cell cell = sheet.getCell(j, i, false);
/* 499*/            if(cell == null)
/* 500*/                continue;


/* 503*/            exp = cell.getFormula();
/* 504*/            if(exp == null || !exp.matches(regex))
/* 505*/                continue;


/* 508*/            item = (ItemFormula)cell.getUserObjectValue("ITEM");
/* 509*/            if(item == null)
/* 510*/                continue;

/* 512*/            String rptItemNumber = item.getItemNumber();

/* 514*/            RPTItemInfo rptitem = (RPTItemInfo)RPTItemInfoMap.get(rptItemNumber);
/* 515*/            if(rptItemNumber != null && rptitem != null && !rptitem.isIsSumItem())
/* 516*/                continue;
                    Cell cell2;

/* 519*/            for(int k = 0; k < creditAndDebitLine.size(); k++)
                    {/* 520*/                cell2 = sheet.getCell(j, ((Integer)creditAndDebitLine.get(k)).intValue(), false);
/* 521*/                if(cell2 != null && cell2.getFormula() == null)
                        {





/* 528*/                    String exp2 = exp.replaceAll(colAliase, (String)cdAliasLine.get(k));
/* 529*/                    cell2.setFormula(exp2);
/* 530*/                    cell2.setUserObject("0", null);
/* 531*/                    cell2.setUserObject("1", null);
                        }
                    }


/* 536*/            String strHeadKey = TableToolkit.int2pos(i);
/* 537*/            cell2 = sheet.getCell(j, ((Integer)creditAndDebitLine.get(0)).intValue(), false);
/* 538*/            if(cell2 != null)
                    {







/* 547*/                strHeadKey = TableToolkit.int2pos(((Integer)creditAndDebitLine.get(0)).intValue());
/* 548*/                TableToolkit.int2pos(((Integer)creditAndDebitLine.get(0)).intValue());
/* 549*/                exp = cell2.getFormula();
                    }
/* 551*/            List lstTemp = new ArrayList();
/* 552*/            lstTemp.add(strHeadKey);
/* 553*/            lstTemp.add(exp);
/* 554*/            mapSumItem.put(Integer.valueOf(j), lstTemp);
                }
            }
            private static List getCompanys(Context ctx, OrgTreeInfo orgTree, OrgUnitInfo orgUnitGroup)
                throws EASBizException, BOSException
            {













/* 573*/        com.kingdee.eas.fi.gr.cslrpt.MergeModeEnum mergeMode = CslRptUtil.getMergeMode(ctx, orgTree.getId().toString());

/* 575*/        HashMap scheme = new HashMap(4);
/* 576*/        scheme.put("OrgTree", orgTree.getId().toString());
/* 577*/        scheme.put("OrgUnitGroup", orgUnitGroup);
/* 578*/        scheme.put("MergeMode", mergeMode);
/* 579*/        scheme.put("queryAllName", Boolean.TRUE);

/* 581*/        IMnsRptSeqSchemeFacade controller = null;
/* 582*/        if(ctx == null)
/* 583*/            controller = MnsRptSeqSchemeFacadeFactory.getRemoteInstance();

/* 585*/        else/* 585*/            controller = MnsRptSeqSchemeFacadeFactory.getLocalInstance(ctx);


/* 588*/        HashMap orgUnitMap = controller.getOrgUnitList(scheme);

/* 590*/        List orgUnitCollection = (List)orgUnitMap.get("OrgUnitList");

/* 592*/        List orgUnitList = new ArrayList(orgUnitCollection.size());

/* 594*/        FullOrgUnitInfo info = null;
/* 595*/        for(int i = 0; i < orgUnitCollection.size(); i++)
                {/* 596*/            HashMap company = (HashMap)orgUnitCollection.get(i);
/* 597*/            info = new FullOrgUnitInfo();
/* 598*/            info.put("id", company.get("OrgUnitID"));

/* 600*/            info.setNumber((String)company.get("OrgUnitNumber"));
/* 601*/            info.setName((String)company.get("OrgUnitName"));
/* 602*/            info.setSimpleName((String)company.get("OrgUnitSimpleName"));
/* 603*/            orgUnitList.add(info);
                }
/* 605*/        return orgUnitList;
            }
            private static void clearCellFormulaByDataCollect(Book book)
                throws BOSException, SyntaxErrorException
            {

/* 611*/        int sheetCount = book.getSheetCount();
/* 612*/        for(int index = 0; index < sheetCount; index++)
                {/* 613*/            Sheet sheet = book.getSheet(index);
/* 614*/            int maxRows = sheet.getMaxRowIndex();
/* 615*/            int maxCols = sheet.getMaxColIndex();
/* 616*/            for(int rows = 0; rows <= maxRows; rows++)
                    {/* 617*/                for(int cols = 0; cols <= maxCols; cols++)
                        {/* 618*/                    Cell cell = sheet.getCell(rows, cols, false);
/* 619*/                    if(cell != null)
                            {/* 620*/                        cell.setUserObject("0", null);
/* 621*/                        cell.setUserObject("1", null);
                            }
                        }
                    }
                }
            }
            private static void autoFillFormula(Sheet sheet, int ixPos)
            {








/* 637*/        String tag = "=+-/*";
/* 638*/        StringBuffer newExp = null;
/* 639*/        int j = 0;/* 639*/        for(int m = sheet.getMaxRowIndex(); j <= m; j++)
                {/* 640*/            Cell cell = sheet.getCell(j, ixPos - 1, false);
/* 641*/            if(cell == null)
/* 642*/                continue;


/* 645*/            String exp = cell.getFormula();
/* 646*/            if(StringUtils.isEmpty(exp))
/* 647*/                continue;

/* 649*/            newExp = new StringBuffer();

/* 651*/            boolean hasRef = false;
/* 652*/            StringTokenizer temp = new StringTokenizer(exp, "+-*/", false);
/* 653*/            do
                    {
/* <-MISALIGNED-> */ /* 653*/                if(!temp.hasMoreElements())
/* <-MISALIGNED-> */ /* 654*/                    break;
/* <-MISALIGNED-> */ /* 654*/                String t = temp.nextToken();
/* <-MISALIGNED-> */ /* 655*/                if(!TableToolkit.isCellRef(t.replaceAll("=", "").replaceAll("\\(", "").replaceAll("\\)", "")))
/* <-MISALIGNED-> */ /* 656*/                    continue;
/* <-MISALIGNED-> */ /* 656*/                hasRef = true;
/* <-MISALIGNED-> */ /* 657*/                break;
                    } while(true);
/* <-MISALIGNED-> */ /* 660*/            if(hasRef)
                    {
/* <-MISALIGNED-> */ /* 661*/                for(StringTokenizer token = new StringTokenizer(exp, tag, true); token.hasMoreTokens();)
                        {
/* <-MISALIGNED-> */ /* 663*/                    String entry = token.nextToken();
/* <-MISALIGNED-> */ /* 664*/                    if(tag.indexOf(entry) >= 0)
                            {
/* <-MISALIGNED-> */ /* 665*/                        newExp.append(entry);
                            } else
                            {
/* <-MISALIGNED-> */ /* 667*/                        String items[] = entry.split("!");
/* <-MISALIGNED-> */ /* 669*/                        if(items.length > 1)
/* <-MISALIGNED-> */ /* 670*/                            newExp.append(items[0]).append("!");
/* <-MISALIGNED-> */ /* 673*/                        String s = items.length <= 1 ? items[0] : items[1];
/* <-MISALIGNED-> */ /* 675*/                        if(!TableToolkit.isCellRef(s.replaceAll("\\)", "").replaceAll("\\(", "")))
                                {
/* <-MISALIGNED-> */ /* 676*/                            newExp.append(s);
                                } else
                                {
/* <-MISALIGNED-> */ /* 678*/                            StringTokenizer st = new StringTokenizer(s, "\\(:\\)", true);
/* <-MISALIGNED-> */ /* 679*/                            while(st.hasMoreTokens()) 
                                    {
/* <-MISALIGNED-> */ /* 680*/                                String item = st.nextToken();
/* <-MISALIGNED-> */ /* 681*/                                if(!TableToolkit.isCellRef(item))
                                        {
/* <-MISALIGNED-> */ /* 682*/                                    newExp.append(item);
                                        } else
                                        {
/* <-MISALIGNED-> */ /* 684*/                                    int index = -1;
/* <-MISALIGNED-> */ /* 685*/                                    int k = item.length() - 1;
/* <-MISALIGNED-> */ /* 685*/                                    do
                                            {
/* <-MISALIGNED-> */ /* 685*/                                        if(k < 0)
/* <-MISALIGNED-> */ /* 688*/                                            break;
/* <-MISALIGNED-> */ /* 688*/                                        try
                                                {
/* <-MISALIGNED-> */ /* 688*/                                            Integer.parseInt(item.substring(k, k + 1));
                                                }
/* <-MISALIGNED-> */ /* 689*/                                        catch(Exception exc)
                                                {
/* <-MISALIGNED-> */ /* 690*/                                            index = k;
/* <-MISALIGNED-> */ /* 691*/                                            break;
                                                }
/* <-MISALIGNED-> */ /* 685*/                                        k--;
                                            } while(true);
/* <-MISALIGNED-> */ /* 695*/                                    String s1 = item.substring(0, index + 1);
/* <-MISALIGNED-> */ /* 696*/                                    String s2 = item.substring(index + 1, item.length());
/* <-MISALIGNED-> */ /* 698*/                                    s1 = TableToolkit.int2pos(TableToolkit.pos2int(s1) + 1);
/* <-MISALIGNED-> */ /* 700*/                                    newExp.append(s1).append(s2);
                                        }
                                    }
                                }
                            }
                        }
                    }
/* <-MISALIGNED-> */ /* 707*/            if(newExp.length() <= 1)
/* <-MISALIGNED-> */ /* 708*/                continue;
/* <-MISALIGNED-> */ /* 708*/            Cell newCell = sheet.getCell(j, ixPos, true);
/* <-MISALIGNED-> */ /* 709*/            if(newCell != null)
/* <-MISALIGNED-> */ /* 710*/                newCell.setFormula(newExp.toString());
                }
            }
            private static Map getRPTItemInfoList(Context ctx, Sheet sheet, int i)
                throws BOSException
            {
/* <-MISALIGNED-> */ /* 722*/        String regex = "=(.+!)?[A-Z]+\\d+([\\+|\\-](.+!)?[A-Z]+\\d+)*";
/* <-MISALIGNED-> */ /* 723*/        List rptItemNumberList = new ArrayList();
/* <-MISALIGNED-> */ /* 724*/        String exp = null;
/* <-MISALIGNED-> */ /* 725*/        ItemFormula item = null;
/* <-MISALIGNED-> */ /* 726*/        int j = 0;
/* <-MISALIGNED-> */ /* 726*/        for(int m = sheet.getMaxRowIndex(); j <= m; j++)
                {
/* <-MISALIGNED-> */ /* 727*/            Cell cell = sheet.getCell(j, i, false);
/* <-MISALIGNED-> */ /* 728*/            if(cell == null)
/* <-MISALIGNED-> */ /* 729*/                continue;
/* <-MISALIGNED-> */ /* 731*/            exp = cell.getFormula();
/* <-MISALIGNED-> */ /* 732*/            if(exp == null || !exp.matches(regex))
/* <-MISALIGNED-> */ /* 733*/                continue;
/* <-MISALIGNED-> */ /* 736*/            item = (ItemFormula)cell.getUserObjectValue("ITEM");
/* <-MISALIGNED-> */ /* 737*/            if(item != null)
/* <-MISALIGNED-> */ /* 740*/                rptItemNumberList.add(item.getItemNumber());
                }
/* <-MISALIGNED-> */ /* 742*/        IRPTItem iRptItem = null;
/* <-MISALIGNED-> */ /* 743*/        if(ctx == null)
/* <-MISALIGNED-> */ /* 744*/            iRptItem = RPTItemFactory.getRemoteInstance();
/* <-MISALIGNED-> */ /* 746*/        else
/* <-MISALIGNED-> */ /* 746*/            iRptItem = RPTItemFactory.getLocalInstance(ctx);
/* <-MISALIGNED-> */ /* 748*/        return iRptItem.getRPTItemInfoList(rptItemNumberList);
            }
            public static boolean isExistsDouble(KDTable kdt, String strID)
            {







/* 761*/        int col = 0;
/* 762*/        if(kdt.getColumn("id") != null)
/* 763*/            col = kdt.getColumn("id").getColumnIndex();

/* 765*/        return isExistsDouble(kdt, strID, col);
            }
            private static boolean isExistsDouble(KDTable kdt, String strID, int col)
            {







/* 776*/        for(int i = 0; i < kdt.getRowCount(); i++)
/* 777*/            if(kdt.getCell(i, col).getValue().toString().equals(strID))
/* 778*/                return true;


/* 781*/        return false;
            }
            public static FullOrgUnitCollection getUnionDebtChildren(String orgTreeId, String orgUnitId)
                throws BOSException, EASBizException
            {











/* 797*/        MessagePostManManager msgMng = new MessagePostManManager("getUnionDebtChildren");
/* 798*/        msgMng.addMessage(new MessagePostMan("treeId", orgTreeId));
/* 799*/        msgMng.addMessage(new MessagePostMan("orgUnitId", orgUnitId));

/* 801*/        msgMng = RpcHandleFacadeFactory.getRemoteInstance().getRpcZipData(msgMng);
/* 802*/        return (FullOrgUnitCollection)msgMng.getItem("returnValue").getValue();
            }
            public static final int CALC_TYPE_All = 0;
            public static final int CALC_TYPE_RPT = 1;
            public static final int CALC_TYPE_ELIM = 2;
            public static final String OrgUnitType_Company = "0";
            public static final String OrgUnitType_Unit = "1";
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fi_gr_cslrpt-client.jar
	Total time: 102 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method prepareSheetData
Couldn't fully decompile method prepareSheetData
Couldn't resolve all exception handlers in method prepareSheetData
	Exit status: 0
	Caught exceptions:
*/