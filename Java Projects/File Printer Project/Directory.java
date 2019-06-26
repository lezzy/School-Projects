// Directory - Maps filenames to FileInfo. Keeps track of where files
//             are stored on the system and what size the files are
//
// There will be one instance of Directory (Global.directory) shared
// by all threads. You must request access to Global.directory from
// Global.directoryManager before using the directory.
import java.util.ArrayList;

public class Directory {
	public Directory() {
		directory = new ArrayList<DirectoryEntry>();
	}

	// Insert FileInfo into the directory
	public void insert(Buffer filename, FileInfo file) {
		DirectoryEntry d = new DirectoryEntry();
		d.key = new Buffer();
		d.key.copyFrom(filename);
		d.value = file;
		directory.add(d);
	}

	// Look the file up in the dire
	public FileInfo lookup(Buffer filename) throws Exception {
		for (DirectoryEntry e : directory) {
			if (e.key.equals(filename)) {
				return e.value;
			}
		}
		throw new Exception("Directory.FileInfo: File not found");
	}

	private class DirectoryEntry {
		Buffer key;
		FileInfo value;
	};

	private ArrayList<DirectoryEntry> directory;
}
