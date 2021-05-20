package com.kingdee.eas.fdc.schedule.framework.ext.uodo;
import java.io.File;
import java.io.IOException;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.document.Document;
import net.sourceforge.ganttproject.undo.UndoManagerImpl;


/**
 * @author bard
 */
public class KDGPUndoableEditImpl extends AbstractUndoableEdit {
    private String myPresentationName;

    private Runnable myEditImpl;

    private Document myDocumentBefore;

    private Document myDocumentAfter;

    private KDGPUndoManagerImpl myManager;

    public KDGPUndoableEditImpl(String localizedName, Runnable editImpl,
            KDGPUndoManagerImpl manager) throws IOException {
        // System.out.println ("UndoableEditImpl : " + localizedName);
        myManager = manager;
        myPresentationName = localizedName;
        myEditImpl = editImpl;
        myDocumentBefore = saveFile();
        editImpl.run();
        myDocumentAfter = saveFile();
    }

    private Document saveFile() throws IOException {
        File tempFile = createTemporaryFile();
        tempFile.deleteOnExit();
        Document doc = myManager.getDocumentManager().getDocument(
                tempFile.getAbsolutePath());
        doc.write();
        //GPSaver saver = myManager.getParserFactory().newSaver();
        //saver.save(doc.getOutputStream());
        return doc;
    }

    public boolean canUndo() {
        return myDocumentBefore.canRead();
    }

    public boolean canRedo() {
        return myDocumentAfter.canRead();
    }

    public void redo() throws CannotRedoException {
        try {
            restoreDocument(myDocumentAfter);
        } catch (IOException e) {
        	if (!GPLogger.log(e)) {
        		e.printStackTrace(System.err);
        	}
            throw new CannotRedoException();
        }
    }

    public void undo() throws CannotUndoException {
        try {
            restoreDocument(myDocumentBefore);
        } catch (IOException e) {
        	if (!GPLogger.log(e)) {
        		e.printStackTrace(System.err);
        	}
            throw new CannotRedoException();
        }
    }

    private void restoreDocument(Document document) throws IOException {
        Document projectDocument = myManager.getProject().getDocument(); 
		myManager.getProject().close();
        document.read();
        myManager.getProject().setDocument(projectDocument);
        
    }

    public String getPresentationName() {
        return myPresentationName;
    }

    File createTemporaryFile() throws IOException {
        return File.createTempFile("_GanttProject_qSave", ".gan");
    }

}
