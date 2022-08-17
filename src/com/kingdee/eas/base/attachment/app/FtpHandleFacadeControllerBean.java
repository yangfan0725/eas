/*jadclipse*/package com.kingdee.eas.base.attachment.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.attachment.*;
import com.kingdee.eas.base.attachment.ConnectionObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.CryptException;
import com.kingdee.enterprisedt.net.ftp.FTPException;
import com.kingdee.enterprisedt.net.ftp.KDFileTransferClient;
import java.io.IOException;
import org.apache.log4j.Logger;
public class FtpHandleFacadeControllerBean extends AbstractFtpHandleFacadeControllerBean
{
            public FtpHandleFacadeControllerBean()
            {
            }
            protected boolean _upload(Context ctx, IObjectValue ftpConfigInfo, String localPath, String remotePath)
                throws BOSException, EASBizException
            {





/*  25*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/*  26*/        KDFileTransferClient fileTransferClient = null;

/*  28*/        try
                {
/* <-MISALIGNED-> */ /*  28*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /*  29*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /*  30*/                fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /*  31*/                if(fileTransferClient != null)
                        {
/* <-MISALIGNED-> */ /*  32*/                    createDir(fileTransferClient, remotePath);
/* <-MISALIGNED-> */ /*  33*/                    fileTransferClient.uploadFile(localPath, remotePath);
/* <-MISALIGNED-> */ /*  34*/                    releaseConn(conn);
/* <-MISALIGNED-> */ /*  35*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /*  38*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /*  39*/            logger.error(e);
/* <-MISALIGNED-> */ /*  41*/            try
                    {
/* <-MISALIGNED-> */ /*  41*/                if(fileTransferClient != null)
/* <-MISALIGNED-> */ /*  42*/                    fileTransferClient.deleteFile(remotePath);
                    }
/* <-MISALIGNED-> */ /*  44*/            catch(Exception e1)
                    {
/* <-MISALIGNED-> */ /*  45*/                logger.error(e1);
                    }
/* <-MISALIGNED-> */ /*  47*/            exceptionProcess(e);
                }
/* <-MISALIGNED-> */ /*  49*/        return false;
            }
            protected boolean _upload(Context ctx, IObjectValue ftpConfigInfo, byte fileBytes[], String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /*  54*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /*  55*/        KDFileTransferClient fileTransferClient = null;
/* <-MISALIGNED-> */ /*  57*/        try
                {
/* <-MISALIGNED-> */ /*  57*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /*  58*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /*  59*/                fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /*  60*/                if(fileTransferClient != null)
                        {
/* <-MISALIGNED-> */ /*  61*/                    createDir(fileTransferClient, remotePath);
/* <-MISALIGNED-> */ /*  62*/                    fileTransferClient.uploadFile(fileBytes, remotePath);
/* <-MISALIGNED-> */ /*  63*/                    releaseConn(conn);
/* <-MISALIGNED-> */ /*  64*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /*  67*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /*  68*/            logger.error(e);
/* <-MISALIGNED-> */ /*  70*/            try
                    {
/* <-MISALIGNED-> */ /*  70*/                if(fileTransferClient != null)
/* <-MISALIGNED-> */ /*  71*/                    fileTransferClient.deleteFile(remotePath);
                    }
/* <-MISALIGNED-> */ /*  73*/            catch(Exception e1)
                    {
/* <-MISALIGNED-> */ /*  74*/                logger.error(e1);
                    }
/* <-MISALIGNED-> */ /*  76*/            exceptionProcess(e);
                }
/* <-MISALIGNED-> */ /*  78*/        return false;
            }
            private void exceptionProcess(Exception e)
                throws EASBizException
            {
/* <-MISALIGNED-> */ /*  82*/        if(e.getMessage().indexOf("Connection timed out") > -1 || e.getMessage().indexOf("Connection refused") > -1 || e.getMessage().indexOf("Access is denied") > -1 || e.getMessage().indexOf("No such file") > -1 || e.getMessage().indexOf("Software caused connection abort: recv failed") > -1 || e.getMessage().indexOf("Not logged in") > -1 || e.getMessage().indexOf("Control channel unexpectedly closed") > -1)
/* <-MISALIGNED-> */ /*  86*/            throw new FtpException(FtpException.FTPCONNECTEDFAIL);
/* <-MISALIGNED-> */ /*  87*/        if(e.getMessage().indexOf("Could not create file") > -1 || e.getMessage().indexOf("Broken pipe") > -1 || e.getMessage().indexOf("Software caused connection abort: socket write error") > -1)
/* <-MISALIGNED-> */ /*  88*/            throw new FtpException(FtpException.DISKNOTENOUGH);
/* <-MISALIGNED-> */ /*  90*/        else
/* <-MISALIGNED-> */ /*  90*/            return;
            }
            protected byte[] _download(Context ctx, IObjectValue ftpConfigInfo, String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /*  94*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /*  97*/        try
                {
/* <-MISALIGNED-> */ /*  97*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /*  98*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /*  99*/                KDFileTransferClient fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /* 100*/                if(fileTransferClient != null)
/* <-MISALIGNED-> */ /* 101*/                    if(!fileTransferClient.exists(remotePath))
                            {
/* <-MISALIGNED-> */ /* 102*/                        throw new FtpException(FtpException.FILENOTFOUND);
                            } else
                            {
/* <-MISALIGNED-> */ /* 104*/                        byte tmp[] = fileTransferClient.downloadByteArray(remotePath);
/* <-MISALIGNED-> */ /* 105*/                        releaseConn(conn);
/* <-MISALIGNED-> */ /* 106*/                        return tmp;
                            }
                    }
                }
/* <-MISALIGNED-> */ /* 109*/        catch(FtpException e)
                {
/* <-MISALIGNED-> */ /* 110*/            logger.error(e);
/* <-MISALIGNED-> */ /* 111*/            throw e;
                }
/* <-MISALIGNED-> */ /* 113*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 114*/            logger.error(e);
/* <-MISALIGNED-> */ /* 115*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 117*/        return null;
            }
            protected boolean _deleteFile(Context ctx, IObjectValue ftpConfigInfo, String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 123*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /* 126*/        try
                {
/* <-MISALIGNED-> */ /* 126*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /* 127*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /* 128*/                KDFileTransferClient fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /* 129*/                if(fileTransferClient != null && fileTransferClient.exists(remotePath))
                        {
/* <-MISALIGNED-> */ /* 131*/                    fileTransferClient.deleteFile(remotePath);
/* <-MISALIGNED-> */ /* 132*/                    releaseConn(conn);
/* <-MISALIGNED-> */ /* 133*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 137*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 138*/            logger.error(e);
/* <-MISALIGNED-> */ /* 139*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 141*/        return false;
            }
            private ConnectionObject getConn(FtpConfigInfo ftpConfigInfo)
                throws FTPException, IOException, CryptException
            {
/* <-MISALIGNED-> */ /* 155*/        FtpConnectionPool ftpConnectionPool = FtpConnectionPool.getInstance();
/* <-MISALIGNED-> */ /* 157*/        ConnectionObject conn = ftpConnectionPool.getConn(ftpConfigInfo);
/* <-MISALIGNED-> */ /* 158*/        return conn;
            }
            private void releaseConn(ConnectionObject conn)
                throws FTPException, IOException
            {
/* <-MISALIGNED-> */ /* 171*/        if(conn.getInPool())
/* <-MISALIGNED-> */ /* 172*/            conn.setInUse(false);
/* <-MISALIGNED-> */ /* 174*/        else
/* <-MISALIGNED-> */ /* 174*/            conn.getFtpConn().disconnect();
            }
            private void createDir(KDFileTransferClient fileTransferClient, String path)
            {







/* 188*/        String separator = "/";
/* 189*/        String directoryName = path.substring(0, path.lastIndexOf(separator));

/* 191*/        try
                {
///* <-MISALIGNED-> */ /* 191*/            if(!fileTransferClient.exists(directoryName))
//                    {
/* <-MISALIGNED-> */ /* 192*/                String directoryNames[] = directoryName.split((new StringBuilder()).append("\\").append(separator).toString());
/* <-MISALIGNED-> */ /* 193*/                StringBuffer dir = new StringBuffer();
/* <-MISALIGNED-> */ /* 194*/                for(int i = 0; i < directoryNames.length; i++)
                        {
/* <-MISALIGNED-> */ /* 195*/                    dir.append(directoryNames[i]);
///* <-MISALIGNED-> */ /* 196*/                    if(fileTransferClient.exists(dir.toString()))
//                            {
///* <-MISALIGNED-> */ /* 197*/                        dir.append(separator);
//                            } else
//                            {
/* <-MISALIGNED-> */ /* 201*/                        try
                                {
/* <-MISALIGNED-> */ /* 201*/                            fileTransferClient.createDirectory(dir.toString());
                                }
/* <-MISALIGNED-> */ /* 202*/                        catch(Exception e)
                                {
/* <-MISALIGNED-> */ /* 203*/                            logger.error(e);
                                }
/* <-MISALIGNED-> */ /* 205*/                        dir.append(separator);
//                            }
                        }
//                    }
                }
/* <-MISALIGNED-> */ /* 209*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 210*/            logger.error(e.getMessage(), e);
                }
            }
            protected boolean _updateContent(Context ctx, IObjectValue ftpConfigInfo, String remotePath, byte content[])
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 217*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /* 220*/        try
                {
/* <-MISALIGNED-> */ /* 220*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /* 221*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /* 222*/                KDFileTransferClient fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /* 223*/                if(fileTransferClient != null)
                        {
/* <-MISALIGNED-> */ /* 224*/                    fileTransferClient.deleteFile(remotePath);
/* <-MISALIGNED-> */ /* 225*/                    fileTransferClient.uploadFile(content, remotePath);
/* <-MISALIGNED-> */ /* 226*/                    releaseConn(conn);
/* <-MISALIGNED-> */ /* 227*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 230*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 231*/            logger.error(e.getMessage(), e);
/* <-MISALIGNED-> */ /* 232*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 234*/        return false;
            }
            protected boolean _updateContent(Context ctx, IObjectValue ftpConfigInfo, String localPath, String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 240*/        FtpConfigInfo ftpConfigInfo2 = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /* 243*/        try
                {
/* <-MISALIGNED-> */ /* 243*/            ConnectionObject conn = getConn(ftpConfigInfo2);
/* <-MISALIGNED-> */ /* 244*/            if(conn != null)
                    {
/* <-MISALIGNED-> */ /* 245*/                KDFileTransferClient fileTransferClient = conn.getFtpConn();
/* <-MISALIGNED-> */ /* 246*/                if(fileTransferClient != null)
                        {
/* <-MISALIGNED-> */ /* 247*/                    fileTransferClient.deleteFile(remotePath);
/* <-MISALIGNED-> */ /* 248*/                    fileTransferClient.uploadFile(localPath, remotePath);
/* <-MISALIGNED-> */ /* 249*/                    releaseConn(conn);
/* <-MISALIGNED-> */ /* 250*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 253*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 254*/            logger.error(e.getMessage(), e);
/* <-MISALIGNED-> */ /* 255*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 257*/        return false;
            }
            protected boolean _deleteFile(Context ctx, ConnectionObject connObject, String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 264*/        try
                {
/* <-MISALIGNED-> */ /* 264*/            if(connObject != null)
                    {
/* <-MISALIGNED-> */ /* 265*/                KDFileTransferClient fileTransferClient = connObject.getFtpConn();
/* <-MISALIGNED-> */ /* 266*/                if(fileTransferClient != null && fileTransferClient.exists(remotePath))
                        {
/* <-MISALIGNED-> */ /* 268*/                    fileTransferClient.deleteFile(remotePath);
/* <-MISALIGNED-> */ /* 269*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 273*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 274*/            logger.error(e);
/* <-MISALIGNED-> */ /* 275*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 277*/        return false;
            }
            protected boolean _upload(Context ctx, ConnectionObject connObject, byte fileBytes[], String remotePath)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 282*/        KDFileTransferClient fileTransferClient = null;
/* <-MISALIGNED-> */ /* 284*/        try
                {
/* <-MISALIGNED-> */ /* 284*/            if(connObject != null)
                    {
/* <-MISALIGNED-> */ /* 285*/                fileTransferClient = connObject.getFtpConn();
/* <-MISALIGNED-> */ /* 286*/                if(fileTransferClient != null)
                        {
/* <-MISALIGNED-> */ /* 287*/                    if(fileTransferClient.exists(remotePath))
/* <-MISALIGNED-> */ /* 288*/                        fileTransferClient.deleteFile(remotePath);
/* <-MISALIGNED-> */ /* 290*/                    createDir(fileTransferClient, remotePath);
/* <-MISALIGNED-> */ /* 291*/                    fileTransferClient.uploadFile(fileBytes, remotePath);
/* <-MISALIGNED-> */ /* 292*/                    return true;
                        }
                    }
                }
/* <-MISALIGNED-> */ /* 295*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 296*/            logger.error(e);
/* <-MISALIGNED-> */ /* 298*/            try
                    {
/* <-MISALIGNED-> */ /* 298*/                if(fileTransferClient != null)
/* <-MISALIGNED-> */ /* 299*/                    fileTransferClient.deleteFile(remotePath);
                    }
/* <-MISALIGNED-> */ /* 301*/            catch(Exception e1)
                    {
/* <-MISALIGNED-> */ /* 302*/                logger.error(e1);
                    }
/* <-MISALIGNED-> */ /* 304*/            exceptionProcess(e);
                }
/* <-MISALIGNED-> */ /* 306*/        return false;
            }
            protected boolean _testConnection(Context ctx, IObjectValue ftpConfigInfo)
                throws BOSException, EASBizException
            {
                FtpConfigInfo newFtpConfigInfo;
                KDFileTransferClient fileTransferClient;
/* <-MISALIGNED-> */ /* 312*/        newFtpConfigInfo = (FtpConfigInfo)ftpConfigInfo;
/* <-MISALIGNED-> */ /* 313*/        fileTransferClient = new KDFileTransferClient();
                boolean flag;
/* <-MISALIGNED-> */ /* 315*/        
/* <-MISALIGNED-> */ /* 320*/        flag = true;
/* <-MISALIGNED-> */ /* 326*/        try
                {
										fileTransferClient.setRemoteHost(newFtpConfigInfo.getHost());
	/* <-MISALIGNED-> */ /* 316*/        fileTransferClient.setRemotePort(newFtpConfigInfo.getPort());
	/* <-MISALIGNED-> */ /* 317*/        fileTransferClient.setUserName(newFtpConfigInfo.getUserName());
	/* <-MISALIGNED-> */ /* 318*/        fileTransferClient.setPassword(newFtpConfigInfo.getPassword());
	/* <-MISALIGNED-> */ /* 319*/        fileTransferClient.connect();
/* <-MISALIGNED-> */ /* 326*/            fileTransferClient.disconnect();
                }
/* <-MISALIGNED-> */ /* 327*/        catch(Exception e1)
                {
/* <-MISALIGNED-> */ /* 328*/            logger.error(e1);
                }
/* <-MISALIGNED-> */ /* 330*/        return flag;
                }

            private static Logger logger = Logger.getLogger("com.kingdee.eas.base.attachment.app.FtpHandleFacadeControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-bs_attachment-server.jar
	Total time: 63 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method _testConnection
Couldn't fully decompile method _testConnection
Couldn't resolve all exception handlers in method _testConnection
	Exit status: 0
	Caught exceptions:
*/