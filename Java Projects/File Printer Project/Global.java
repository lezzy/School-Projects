// Global - variables global to the system
public class Global {
	static int NUMBER_OF_USERS = 4;
	static int NUMBER_OF_DISKS = 2;
	static int NUMBER_OF_PRINTERS = 3;

	static UserThread[] users;
	static Disk[] disks;
	static Printer[] printers;
	static Directory directory;

	static ResourceManager diskManager;
	static ResourceManager printerManager;
	static ResourceManager directoryManager;
}
