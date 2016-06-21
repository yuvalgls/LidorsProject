package tools;

import java.io.File;
import java.io.FilenameFilter;

public class files {
	public static String[] FindSubFolders(String Folder) {
		File file = new File(Folder);
		String[] SubFolder = file.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return SubFolder;
	}

	public static String[] FindSubFiles(String Folder) {
		File file = new File(Folder);
		String[] SubFiles = file.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isFile();
			}
		});
		return SubFiles;
	}

}
