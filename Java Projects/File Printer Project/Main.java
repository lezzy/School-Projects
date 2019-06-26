// Main
public class Main {

	public static void main(String[] args) throws Exception {
		// initialize the resources

		// initialize users
		Global.users = new UserThread[Global.NUMBER_OF_USERS];
		for (int i = 0; i < Global.NUMBER_OF_USERS; i++) {
			Global.users[i] = new UserThread(i);
		}

		// initialize disks
		Global.disks = new Disk[Global.NUMBER_OF_DISKS];
		for (int i = 0; i < Global.NUMBER_OF_DISKS; i++) {
			Global.disks[i] = new Disk(256); // each disk will have 256 sectors
		}

		// initialize printers
		Global.printers = new Printer[Global.NUMBER_OF_PRINTERS];
		for (int i = 0; i < Global.NUMBER_OF_PRINTERS; i++) {
			Global.printers[i] = new Printer(i);
		}

		// initialize directory
		Global.directory = new Directory();

		// initialize the resource managers
		Global.diskManager = new ResourceManager(Global.NUMBER_OF_DISKS);
		Global.printerManager = new ResourceManager(Global.NUMBER_OF_PRINTERS);
		Global.directoryManager = new ResourceManager(1); // there is only a single directory

		// Start the user threads
		for (int i = 0; i < Global.NUMBER_OF_USERS; i++) {
			Global.users[i].start();
		}
		
		// the program will end after all threads have terminated
	}
}
