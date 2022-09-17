package com.kingdee.eas.base.attachment.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.*;
import com.kingdee.eas.base.attachment.common.AttachmentHelper;
import com.kingdee.eas.base.form.web.attachment.BosAttachmentAdapter;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.ChangeManageAttachEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeManageAttachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryInfo;
import com.kingdee.util.NumericExceptionSubItem;

import org.apache.log4j.Logger;
public class AttachmentFtpHandleFacadeControllerBean extends AbstractAttachmentFtpHandleFacadeControllerBean
{
            public AttachmentFtpHandleFacadeControllerBean()
            {
            }
            protected boolean _upload(Context ctx, String attachmentId, String fileId)
                throws BOSException, EASBizException
            {
/*  34*/        FtpConfigInfo ftpConfigInfo = FtpConfigHelper.getFtpConfig(ctx);
/*  35*/        AttachmentInfo attachmentInfo = AttachmentHelper.getAttachmentInfo(ctx, attachmentId);
/*  36*/        String remotePath = null;
				if(attachmentInfo.getBoAttchAsso().get(0).getAssoBusObjType().equals("08C8DF31")||attachmentInfo.getBoAttchAsso().get(0).getAssoBusObjType().equals("1A4B8B7D")){
					if(attachmentInfo.getName()==null){
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("id"));
						sic.add(new SelectorItemInfo("boAttchAsso.assoBusObjType"));
						sic.add(new SelectorItemInfo("attachID"));
						sic.add(new SelectorItemInfo("simpleName"));
						sic.add(new SelectorItemInfo("remotePath"));
						sic.add(new SelectorItemInfo("ftp"));
						sic.add(new SelectorItemInfo("name"));
						sic.add(new SelectorItemInfo("beizhu"));
						attachmentInfo=AttachmentFactory.getLocalInstance(ctx).getAttachmentInfo(new ObjectUuidPK(attachmentId),sic);
					}
					if((attachmentInfo.getBeizhu().split("/")[attachmentInfo.getBeizhu().split("/").length-1]+attachmentInfo.getName()).length()>85){
						throw new EASBizException(new NumericExceptionSubItem("100","附件名太长！"));
					}
					remotePath=attachmentInfo.getBeizhu()+attachmentInfo.getName().trim()+"."+attachmentInfo.getSimpleName();
					AttachmentCollection attCol=AttachmentFactory.getLocalInstance(ctx).getAttachmentCollection("select id from where remotePath='"+remotePath+"'");
					for(int i=0;i<attCol.size();i++){
						BoAttchAssoCollection boAttCol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection("select boId from where attachment.id='"+attCol.get(i).getId()+"'");
						boolean isDel=true;
						for(int j=0;j<boAttCol.size();j++){
							String boId=boAttCol.get(j).getBoID();
							if(BOSUuid.read(boId).getType().equals(new SHEAttachBillEntryInfo().getBOSType())){
								if(!SHEAttachBillEntryFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(boId))){
									BoAttchAssoFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(boAttCol.get(j).getId()));
								}else{
									isDel=false;
								}
							}else if(BOSUuid.read(boId).getType().equals(new ChangeManageAttachEntryInfo().getBOSType())){
								if(!ChangeManageAttachEntryFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(boId))){
									BoAttchAssoFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(boAttCol.get(j).getId()));
								}else{
									isDel=false;
								}
							}
							
						}
						if(isDel){
							AttachmentFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(attCol.get(i).getId().toString()));
						}
					}
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("remotePath",remotePath));
					if(AttachmentFactory.getLocalInstance(ctx).exists(filter)){
						throw new EASBizException(new NumericExceptionSubItem("100","附件名重复！"));
					}
				}else{
					remotePath = FtpConfigHelper.getRemotePath(ctx, ftpConfigInfo.getRootPath(), attachmentInfo);
				}
/*  37*/        FtpHandleFacadeFactory.getLocalInstance(ctx).upload(ftpConfigInfo, fileId, remotePath);
/*  39*/        updateAttachment(ctx, attachmentInfo, remotePath, ftpConfigInfo);
/*  40*/        return true;
            }
            protected boolean _upload(Context ctx, IObjectValue attachmentInfo)
                throws BOSException, EASBizException
            {
/*  47*/        FtpConfigInfo ftpConfigInfo = FtpConfigHelper.getFtpConfig(ctx);
/*  48*/        AttachmentInfo attachmentInfo2 = (AttachmentInfo)attachmentInfo;
				String remotePath=null;
				if(attachmentInfo2.getBoAttchAsso().get(0).getAssoBusObjType().equals("08C8DF31")||attachmentInfo2.getBoAttchAsso().get(0).getAssoBusObjType().equals("1A4B8B7D")){
					if((attachmentInfo2.getBeizhu().split("/")[attachmentInfo2.getBeizhu().split("/").length-1]+attachmentInfo2.getName()).length()>85){
						throw new EASBizException(new NumericExceptionSubItem("100","附件名太长！"));
					}
					remotePath=attachmentInfo2.getBeizhu()+attachmentInfo2.getName().trim()+"."+attachmentInfo2.getSimpleName();
					AttachmentCollection attCol=AttachmentFactory.getLocalInstance(ctx).getAttachmentCollection("select id from where remotePath='"+remotePath+"'");
					for(int i=0;i<attCol.size();i++){
						BoAttchAssoCollection boAttCol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection("select boId from where attachment.id='"+attCol.get(i).getId()+"'");
						boolean isDel=true;
						for(int j=0;j<boAttCol.size();j++){
							String boId=boAttCol.get(j).getBoID();
							if(BOSUuid.read(boId).getType().equals(new SHEAttachBillEntryInfo().getBOSType())){
								if(!SHEAttachBillEntryFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(boId))){
									BoAttchAssoFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(boAttCol.get(j).getId()));
								}else{
									isDel=false;
								}
							}else if(BOSUuid.read(boId).getType().equals(new ChangeManageAttachEntryInfo().getBOSType())){
								if(!ChangeManageAttachEntryFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(boId))){
									BoAttchAssoFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(boAttCol.get(j).getId()));
								}else{
									isDel=false;
								}
							}
							
						}
						if(isDel){
							AttachmentFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(attCol.get(i).getId().toString()));
						}
					}
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("remotePath",remotePath));
					if(AttachmentFactory.getLocalInstance(ctx).exists(filter)){
						throw new EASBizException(new NumericExceptionSubItem("100","附件名重复！"));
					}
				}else{
					remotePath = FtpConfigHelper.getRemotePath(ctx, ftpConfigInfo.getRootPath(), attachmentInfo2);
				}
				FtpHandleFacadeFactory.getLocalInstance(ctx).upload(ftpConfigInfo, attachmentInfo2.getFile(), remotePath);
/*  52*/        updateAttachment(ctx, attachmentInfo2, remotePath, ftpConfigInfo);
/*  53*/        return true;
            }
            protected byte[] _download(Context ctx, String attachmentId)
                throws BOSException, EASBizException
            {



/*  61*/        AttachmentInfo attachmentInfo = AttachmentHelper.getAttachmentInfo(ctx, attachmentId);
/*  62*/        FtpConfigInfo ftpConfigInfo = getFtpConfig(ctx, attachmentInfo.getFtp().getId().toString());

/*  64*/        return FtpHandleFacadeFactory.getLocalInstance(ctx).download(ftpConfigInfo, attachmentInfo.getRemotePath());
            }
            protected boolean _deleteFile(Context ctx, String attachmentId)
                throws BOSException, EASBizException
            {



/*  72*/        AttachmentInfo attachmentInfo = AttachmentHelper.getAttachmentInfo(ctx, attachmentId);
/*  73*/        FtpConfigInfo ftpConfigInfo = getFtpConfig(ctx, attachmentInfo.getFtp().getId().toString());

/*  75*/        return FtpHandleFacadeFactory.getLocalInstance(ctx).deleteFile(ftpConfigInfo, attachmentInfo.getRemotePath());
            }
            private void updateAttachment(Context ctx, AttachmentInfo attachmentInfo, String remotePath, FtpConfigInfo ftpConfigInfo)
                throws EASBizException, BOSException
            {













/*  93*/        attachmentInfo.setRemotePath(remotePath);
/*  94*/        attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
/*  95*/        attachmentInfo.setFtp(ftpConfigInfo);
/*  96*/        attachmentInfo.setFile(null);
/*  97*/        AttachmentFactory.getLocalInstance(ctx).update(new ObjectUuidPK(attachmentInfo.getId().toString()), attachmentInfo);
            }
            private FtpConfigInfo getFtpConfig(Context ctx, String ftpId)
                throws BOSException, EASBizException
            {














/* 116*/        IFtpConfig iFtpConfig = FtpConfigFactory.getLocalInstance(ctx);
/* 117*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 118*/        sic.add(new SelectorItemInfo("id"));
/* 119*/        sic.add(new SelectorItemInfo("host"));
/* 120*/        sic.add(new SelectorItemInfo("port"));
/* 121*/        sic.add(new SelectorItemInfo("userName"));
/* 122*/        sic.add(new SelectorItemInfo("password"));
/* 123*/        sic.add(new SelectorItemInfo("rootPath"));
/* 124*/        FtpConfigInfo ftpConfigInfo = iFtpConfig.getFtpConfigInfo(new ObjectUuidPK(ftpId), sic);

/* 126*/        return ftpConfigInfo;
            }
            protected boolean _updateContent(Context ctx, String attachmentId, byte content[])
                throws BOSException, EASBizException
            {

/* 132*/        AttachmentInfo attachmentInfo = AttachmentFactory.getLocalInstance(ctx).getAttachmentInfo(new ObjectUuidPK(attachmentId));
/* 133*/        FtpConfigInfo ftpConfigInfo = getFtpConfig(ctx, attachmentInfo.getFtp().getId().toString());
/* 134*/        return FtpHandleFacadeFactory.getLocalInstance(ctx).updateContent(ftpConfigInfo, attachmentInfo.getRemotePath(), content);
            }
            protected boolean _updateContent(Context ctx, String attachmentId, String fileId)
                throws BOSException, EASBizException
            {

/* 140*/        AttachmentInfo attachmentInfo = AttachmentFactory.getLocalInstance(ctx).getAttachmentInfo(new ObjectUuidPK(attachmentId));
/* 141*/        FtpConfigInfo ftpConfigInfo = getFtpConfig(ctx, attachmentInfo.getFtp().getId().toString());
/* 142*/        return FtpHandleFacadeFactory.getLocalInstance(ctx).updateContent(ftpConfigInfo, fileId, attachmentInfo.getRemotePath());
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.base.attachment.app.AttachmentFtpHandleFacadeControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-bs_attachment-server.jar
	Total time: 58 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/