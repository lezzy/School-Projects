// Printer - Class to simulate printer
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
	private FileWriter file;

	public Printer(int printerNumber) throws IOException {
		String filename = "PRINTER_" + Integer.toString(printerNumber);
		file = new FileWriter(filename);
	}

	public void printLine(Buffer buffer) throws Exception {
		file.append(buffer.toString());
		file.append(System.lineSeparator());
		Thread.sleep(2750); // it takes 2750 ms to print a line
		file.flush();
	}
}
