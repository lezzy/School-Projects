// ResourceManager - A ResourceManager is responsible for allocating a
//                   resource like a Disk or a Printer to a specific
//                   Thread.
//
// Make sure you understand what this class does, how it works, and
// why it must be syncronized. ResourceManager is how you will share
// resources between threads.
public class ResourceManager {
	boolean[] isFree;

	ResourceManager(int numberOfItems) {
		isFree = new boolean[numberOfItems];
		for (int i = 0; i < isFree.length; ++i)
			isFree[i] = true;
	}

	synchronized int request() throws InterruptedException {
		while (true) {
			for (int i = 0; i < isFree.length; ++i)
				if (isFree[i]) {
					isFree[i] = false;
					return i;
				}
			this.wait(); // block until someone releases a Resource
		}
	}

	synchronized void release(int index) {
		isFree[index] = true;
		this.notify(); // let a waiting thread run
	}
}
