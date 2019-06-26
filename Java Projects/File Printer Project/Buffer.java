// Buffer - General purpose data buffer
public class Buffer {
	private char[] data;

	@Override
	public boolean equals(Object arg) {
		if (arg instanceof Buffer) {
			Buffer b = (Buffer) arg;
			return equals(b);
		} else if (arg instanceof String) {
			String s = (String) arg;
			return equals(s);
		}
		return false;
	}

	public boolean equals(String s) {
		Buffer b = new Buffer();
		b.copyFrom(s);
		return equals(b);
	}

	public boolean equals(Buffer b) {
		if (this.data.length != b.data.length) {
			return false;
		}
		for (int i = 0; i < this.data.length; i++) {
			if (this.data[i] != b.data[i]) {
				return false;
			}
		}
		return true;
	}

	public void copyFrom(String s) {
		data = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			data[i] = s.charAt(i);
		}
	}

	public void copyFrom(Buffer b) {
		this.data = new char[b.data.length];
		for (int i = 0; i < b.data.length; i++) {
			this.data[i] = b.data[i];
		}
	}

	// This can only be used by Printer.printLine
	@Override
	public String toString() {
		return new String(data);
	}
}
