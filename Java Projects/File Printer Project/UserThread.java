// UserThread - Models a users interaction with the system
//
// This class will read tasks from a file and execute the correct
// actions. It will write data to disks itself, and will start a
// PrintJobThread to perform printing.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserThread extends Thread {
	// The file with the user's actions
	BufferedReader file;
	// The buffer used to process the input
	Buffer buffer;

	public UserThread(int userNumber) throws IOException {
		String filename = "USER_" + Integer.toString(userNumber);
		file = new BufferedReader(new FileReader(filename));
		buffer = new Buffer();
	}

	// readLine will read a line from the file USER_i and insert it
	// into buffer. When the end of the file has been reached, it will
	// return -1.
	private int readLine() throws IOException {
		String line;
		line = file.readLine();
		if (line == null) {
			return -1;
		}
		buffer.copyFrom(line);
		return 0;
	}


        // You must implement run. Here is a starting point, but you
        // can modify the code of run however you like. You must read
        // the file one line at a time and execute the actions
        // indicated by the file. Only use the buffer member variable
        // to process the input.
        //
        // You must write files to the Disk objects in this function,
        // and launch PrintJobThread (call start() on the
        // PrintJobThread object) to execute printing concurrently.
        //
        // When start() is called (inherited from thread) this
        // function will execute in a separate thread.
	@Override
	public void run() {
		try {
			while (readLine() != -1) {
			    // TODO: Process the input
				if (buffer.toString() == ".print")
				{
					readLine();
					int free_directory = Global.directoryManager.request();
					FileInfo file_info = Global.directory.lookup(buffer);
					PrintJobThread printing;
					printing = new PrintJobThread(file_info);
					printing.start();
					printing.run();
					printing.join();
					Global.directoryManager.release(free_directory);
				}
				if (buffer.toString() == ".write")
				{
					readLine();
					String file_name = buffer.toString();
					int counter = 0;
					FileInfo new_file = new FileInfo();
					new_file.diskNumber = Global.diskManager.request();
					new_file.startingSector = Global.disks[new_file.diskNumber].getNextFreeSector();
					//Global.directory.insert(buffer, new_file);
					while (true)
					{
						readLine();
						if (buffer.toString() == ".end")
						{
							break;
						}
						int free_sector = Global.disks[new_file.diskNumber].getNextFreeSector();
						Global.disks[new_file.diskNumber].write(free_sector, buffer);
						counter++;
					}
					new_file.fileLength = counter;
					buffer.copyFrom(file_name);
					int free_direct = Global.directoryManager.request();
					Global.directory.insert(buffer, new_file);
					Global.directoryManager.release(free_direct);
					Global.diskManager.release(new_file.diskNumber);
				}
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
