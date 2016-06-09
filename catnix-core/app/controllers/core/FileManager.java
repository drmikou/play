package controllers.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import be.objectify.deadbolt.java.actions.SubjectPresent;


/**
 * This controller handle file:
 * 				- upload
 * 				- download
 *
 * @author pelob
 *
 */
/*
 * Handling Async File Upload and Download
 * ---------------------------------------
 * Because of async upload, file are first stored in a tmp folder (tmpRoot).
 * If you use if on a form, the file name should be passed as hidden input,
 * To get the file from the controller, you just need to get them by getTmpFile(fileName)
 * Then you can move the file to the final destination folder by archiveDocument() or archiveData().
 * nb: all dest folder need to exist, there is script to do it if necessary.
 *
 *
 *
 * Document, Draft or Data ?
 * -------------------------
 * Every file uploaded should be a .pdf
 *
 * There is a difference between Data and Document,
 * 		Data have no logic, it is just a file, it is a support for a process
 * 		Draft is a part of a process but as it's html form
 * 		Document is a part of a process, converted to pdf when finalized
 *
 * Naming convention:
 * 		Data: 		data-uniqueId.pdf
 * 		Draft:		TYPE[-Optional-informations]-uniqueId.html
 * 		Document:	TYPE[-Optional-informations]-uniqueId.pdf
 */

@SubjectPresent
public class FileManager extends Controller {

    private static final String tmpRoot = "/tmp/catnix/tmpCatnix";
    private static final String dataRoot = "/tmp/catnix/data";
    private static final String documentRoot = "/tmp/catnix/document";
    private static final String draftRoot = "/tmp/catnix/draft";


    /*
     * ASync upload
     * ------------
     */

    /**
     * Retrieve the top level directory of catnix
     * @return
     */
    public static File getCatnixRootDir() {
        return new File(".");
    }

    /**
     * Retrieve the top level tmpDir for current session
     */
    public static File getSessionTmpDir() {
        File sessionTmpDir = new File(tmpRoot, session("userId"));
        sessionTmpDir.mkdirs();
        
        return sessionTmpDir;
    }

    /**
     * Retrieve an already uploaded file from tmpDir
     */
    public static File getTmpFile(String fileName) {
        return new File(getSessionTmpDir(), fileName);
    }

    /**
     * Check is a file is uploaded in the tmpDir
     */
    public static boolean isUploaded(String fileName) {
        return ((new File(getSessionTmpDir(), fileName)).exists());
    }

    /**
     * Upload the file to the tmpDir, this is meaned to be called asyncronously from a dropZone
     */
    public Result asyncUploadFile(String fileName) {
        MultipartFormData body = request().body().asMultipartFormData();
        for(MultipartFormData.FilePart file : body.getFiles()){
            if (file != null) {
                File tmpFile = file.getFile();
                File tmpDir = getSessionTmpDir();
                tmpFile.renameTo(new File(tmpDir, fileName));
            } else {
                flash("error", "Missing file");
                return badRequest("file not uploaded");
            }
        }
        return ok("upload succeeded");
    }

    /**
     * Upload a draft to the tmpDir, this is meaned to be called asyncronously from a documentEditor
     */
    public Result asyncUploadDraft(String fileName) {
        File tmpFile = new File(getSessionTmpDir(), fileName);
    	String documentContent = request().body().asText();

        try {
			FileUtils.writeStringToFile(tmpFile, documentContent);
		} catch (IOException e) {
            Logger.error("Missing document", e);
            flash("error", "Missing document");
            return badRequest("document not uploaded");
		}

        return ok("upload succeeded");
    }

    /**
     * Delete a file of the tmpDir
     */
    public Result asyncDeleteTmpFile(String fileName) {
        File tmpFile = new File(getSessionTmpDir(), fileName);

        if(tmpFile.exists()) {
            tmpFile.delete();
        }

        return ok("delete succeeded");
    }


    /*
     * File Manipulation
     * -----------------
     */
    /**
     * Retrieve the targeted archive folder if is exists
     */
    private static File getArchiveFolder(String archiveRoot, String folderName) {
        File targetFolder = new File(archiveRoot, folderName);
        return targetFolder.exists() ? targetFolder : null;
    }

    /**
     * Move and rename a file to it's Archive folder, the final filePath is returned
     */
    private static String archiveFile(String archiveRoot, String folderName, int id, File tmpFile, String compl, String fileType) {
        if(!tmpFile.exists()) {
            return null;
        }

        String fileName = folderName + "-";
        String strippedCompl = compl.replaceAll("\\s","");
        if(!strippedCompl.isEmpty()) {
            fileName += strippedCompl + "-";
        }
        fileName += String.valueOf(id) + "." + fileType;

        File destFile = new File(getArchiveFolder(archiveRoot, folderName), fileName);
        tmpFile.renameTo(destFile);

        return destFile.getAbsolutePath();
    }


    /*
     * Data Manipulation
     * -----------------
     */
    /**
     * Move and rename a tmpFile to its' Data folder, the filePath is returned
     */
    public static String archiveData(String folderName, int id, File tmpFile) {
    	return archiveFile(dataRoot, folderName, id, tmpFile, "", "pdf");
    }


    /*
     * Document Manipulation
     * ---------------------
     */
    /**
     * Move and rename a tmpFile to it's Document folder, the filePath is returned
     */
    public static String archiveDocument(String folderName, int id, File tmpFile, String compl) {
    	return archiveFile(documentRoot, folderName, id, tmpFile, compl, "pdf");
    }

    public static String archiveDocument(String folderName, int id, File tmpFile, List<String> compl) {
    	return archiveFile(documentRoot, folderName, id, tmpFile, Joiner.on("-").join(compl), "pdf");
    }


    /*
     * Draft Manipulation
     * ---------------------
     */
    /**
     * Move and rename a tmpFile to it's Draft folder, the filePath is returned
     */
    public static String archiveDraft(String folderName, int id, File tmpFile, String compl) {
    	return archiveFile(draftRoot, folderName, id, tmpFile, compl, "pdf");
    }

    public static String archiveDraft(String folderName, int id, File tmpFile, List<String> compl) {
    	return archiveFile(draftRoot, folderName, id, tmpFile, Joiner.on("-").join(compl), "pdf");
    }


    /*
     * File Retrieve
     * -------------
     */
    /**
     * Download a file from its' filePath
     */
    public Result downloadFile(String filePath) {
        File file = new File(filePath);
        return ok(file);
    }

    /**
     * Preview a document from its' filePath
     */
    public Result previewDocument(String filePath) {
        try {
        	Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);
	        return ok(data).as("application/pdf");
		} catch (IOException e) {
            Logger.error("Preview document", e);
			File file = new File(filePath);
			return ok(file);
		}
    }

    /**
     * Check if file exist in tmpDir
     */
    public Result asyncCheckTmpFileExists(String fileName) {
        File tmpFile = new File(getSessionTmpDir(), fileName);
        return ok(tmpFile.exists() ? tmpFile.toString() : "false");
    }


}
