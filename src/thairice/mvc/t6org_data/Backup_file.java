package thairice.mvc.t6org_data;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Backup_file {
    private StringBuffer strBuf = null;
    private static StringBuffer fileNameBuf = new StringBuffer();
    private Properties prop = new Properties();
    static final int buffer = 2048;

    public static void main(String[] args) throws Exception {
	Backup_file app = new Backup_file();
	System.out.println("Start......");
	System.out.println("Start delete the dst folder......");
	app.delete();
	System.out.println("Start create the dst folder......");
	app.mkdir();
	System.out.println("Start copy src folder to dst folder......");
	app.copy();
	System.out.println("Start compress dst folder......");
	app.compress();
	System.out.println(fileNameBuf);
	System.out.println("End!");
    }

    public void mkdir() {
	try {
	    InputStream is = getClass().getResourceAsStream("/init_rice.properties");
	    prop.load(is);
	    String dest = prop.getProperty("dst");
	    String zipdir = prop.getProperty("zipdir");
	    File f = new File(dest);
	    File zipFile = new File(zipdir);
	    if (f.exists() == false) {
		f.mkdirs();
	    }
	    if (zipFile.exists() == false) {
		f.mkdirs();
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public void copy() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String currentDate = sdf.format(new Date());
	try {
	    InputStream is = getClass().getResourceAsStream("/init_rice.properties");
	    prop.load(is);
	    String[] src = prop.getProperty("src").split(",");
	    String dest = prop.getProperty("dst");
	    for (int i = 0; i < src.length; i++) {
		File file = new File(src[i]);
		getAllFiles(file, currentDate, dest, src[i]);
	    }
	    is.close();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public void compress() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String currentDate = sdf.format(new Date());
	boolean flag = false;
	try {
	    InputStream is = getClass().getResourceAsStream("/init_rice.properties");
	    prop.load(is);
	    String zipDir = prop.getProperty("zipdir");
	    String destDir = prop.getProperty("dst");
	    ;
	    File filein = new File(destDir);
	    File[] file = filein.listFiles();
	    for (int i = 0; i < file.length; i++) {
		if (file[i].isDirectory() || file[i].length() > 0) {
		    flag = true;
		    break;
		}
	    }
	    if (flag == true) {
		File fileout = new File(zipDir);
		if (fileout.exists() == false) {
		    fileout.mkdir();
		}
		String strAbsFilename = fileout.getAbsolutePath() + File.separator + currentDate + "_backup.zip";
		OutputStream os = new FileOutputStream(strAbsFilename);
		BufferedOutputStream bs = new BufferedOutputStream(os);
		ZipOutputStream zo = new ZipOutputStream(bs);
		zip(destDir, new File(destDir), zo, true, true);
		zo.closeEntry();
		zo.close();
		saveAll(zipDir);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void zip(String path, File basePath, ZipOutputStream zo, boolean isRecursive, boolean isOutBlankDir)
	    throws Exception {
	File inFile = new File(path);
	File[] files = new File[0];
	if (inFile.isDirectory()) {
	    files = inFile.listFiles();
	} else if (inFile.isFile()) {
	    files = new File[1];
	    files[0] = inFile;
	}
	byte[] buf = new byte[1024];
	int len;
	for (int i = 0; i < files.length; i++) {
	    String pathName = "";
	    if (basePath != null) {
		if (basePath.isDirectory()) {
		    pathName = files[i].getPath().substring(basePath.getPath().length() + 1);
		} else {
		    pathName = files[i].getPath().substring(basePath.getParent().length() + 1);

		}
	    } else {
		pathName = files[i].getName();
	    }
	    if (files[i].isDirectory()) {
		if (isOutBlankDir && basePath != null) {
		    zo.putNextEntry(new ZipEntry(pathName + "/")); // 可以使空目录也放进去
		}
		if (isRecursive) {
		    zip(files[i].getPath(), basePath, zo, isRecursive, isOutBlankDir);
		}
	    } else {
		FileInputStream fin = new FileInputStream(files[i]);
		zo.putNextEntry(new ZipEntry(pathName));
		while ((len = fin.read(buf)) > 0) {
		    zo.write(buf, 0, len);
		}
		fin.close();
	    }
	}

    }

    public void delete() {
	try {
	    InputStream is = getClass().getResourceAsStream("/init_rice.properties");
	    prop.load(is);
	    String dest = prop.getProperty("dst");
	    File f = new File(dest);
	    if (f.exists()) {
		File[] allFiles = f.listFiles();
		for (int i = 0; i < allFiles.length; i++) {
		    if (allFiles[i].isFile()) {
			allFiles[i].delete();
		    } else {
			deleteFolder(allFiles[i]);
		    }

		    System.out.println(allFiles[i].getAbsolutePath());
		}
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public void deleteFolder(File folder) {
	String childs[] = folder.list();
	if (childs == null || childs.length <= 0) {
	    folder.delete();
	}
	for (int i = 0; i < childs.length; i++) {
	    String childName = childs[i];
	    String childPath = folder.getPath() + File.separator + childName;
	    File filePath = new File(childPath);
	    if (filePath.exists() && filePath.isFile()) {
		filePath.delete();
	    } else if (filePath.exists() && filePath.isDirectory()) {
		deleteFolder(filePath);
	    }
	}

	folder.delete();
    }

    public String getAllFiles(File dir, String modifiedDate, String dest, String src) throws Exception {
	strBuf = new StringBuffer();
	searchDirectory(dir, modifiedDate, dest, src);
	save(dest);
	return strBuf.toString();
    }

    public String searchDirectory(File dir, String modifiedDate, String dest, String src) throws Exception {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	File[] dirs = dir.listFiles();
	File fileout = new File(dest);
	if (!fileout.exists()) {
	    fileout.mkdirs();
	}
	for (int i = 0; i < dirs.length; i++) {
	    if (dirs[i].isDirectory()) {
		searchDirectory(dirs[i], modifiedDate, dest, src);
	    } else {
		try {
		    if ((df.format(new Date(dirs[i].lastModified())).equals(df.format(df.parse(modifiedDate))))) {
			File file = new File(dirs[i].toString());
			FileInputStream fileIn = new FileInputStream(file);
			String destDir = file.getAbsolutePath().substring(src.length(),
				file.getAbsolutePath().lastIndexOf("//"));
			File temp = new File(dest + destDir);
			if (!temp.exists()) {
			    temp.mkdirs();
			}
			String strAbsFilename = fileout.getAbsolutePath() + destDir + File.separator + file.getName();
			FileOutputStream fileOut = new FileOutputStream(strAbsFilename);
			String pathName = strAbsFilename.substring(dest.length());
			fileNameBuf.append(pathName);
			fileNameBuf.append("/r/n");
			byte[] br = new byte[1024];
			while (fileIn.read(br) > 0) {
			    fileOut.write(br);
			    fileOut.flush();
			}
			fileIn.close();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	return strBuf.toString();
    }

    public void save(String dest) {
	try {

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String currentDate = sdf.format(new Date());
	    File f = new File(dest + "//" + "filelist_" + currentDate + ".txt");
	    FileWriter fout = new FileWriter(f);
	    BufferedWriter buf = new BufferedWriter(fout);
	    buf.write(fileNameBuf.toString());
	    buf.close();
	    fout.close();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public void saveAll(String zipdir) {
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String currentDate = sdf.format(new Date());
	    File f = new File(zipdir + "//" + "filelist.txt");
	    FileWriter fout = new FileWriter(f, true);
	    BufferedWriter buf = new BufferedWriter(fout);
	    buf.write("/r/n");
	    buf.write(currentDate);
	    buf.write("/r/n");
	    buf.write(fileNameBuf.toString());
	    buf.close();
	    fout.close();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
