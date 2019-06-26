// Disk - class to stimulate  a disk
public class Disk {
	private Buffer[] sectors;
	private int nextFreeSector;

	public Disk(int NumberOfSectors) {
		sectors = new Buffer[NumberOfSectors];
		nextFreeSector = 0;
		// initialize all sectors
		for(int i = 0; i < NumberOfSectors; i++) {
		    sectors[i] = new Buffer();
		}
	}

	public int getNextFreeSector() {
		return nextFreeSector;
	}

	// write sector to disk
	public void write(int sector, Buffer data) throws InterruptedException {
	    sectors[sector].copyFrom(data);
		if (sector >= nextFreeSector) {
			nextFreeSector = sector + 1;
		}
		Thread.sleep(200); // writes take 200 ms to complete
	}

	// read sector from disk
	public void read(int sector, Buffer data) throws InterruptedException {
		data.copyFrom(sectors[sector]);
		Thread.sleep(200); // reads take 200 ms to complete
	}
}
