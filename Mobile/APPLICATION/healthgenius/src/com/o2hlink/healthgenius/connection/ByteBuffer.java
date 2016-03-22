package com.o2hlink.healthgenius.connection;

/**
 * This class represents an increasable byte array.
 */
public class ByteBuffer {
	/**
	 * Description of the field
	 * <p>incremental size.</p>
	 */
	private static final int SIZE_INC = 256;

	/**
	 * Description of the field
	 * <p>append position in the buffer.</p>
	 */
	private int pos = 0;

	/**
	 * Description of the field
	 * <p>buffer space.</p>
	 */
	private byte[] buf = new byte[SIZE_INC];

	/**
	 * Method description
	 * <p>append a byte to the end of buffer.</p>
	 * Method extra info
	 * <p></p>
	 * @param b - the byte to append.
	 */
	public void append(byte b) {
		if (pos == buf.length) {
			byte[] tmp = new byte[pos+SIZE_INC];
			int i;
			for (i = 0; i < pos; i++) {
				tmp[i] = buf[i];
			}
			buf = tmp;
		}
		buf[pos++] = b;
	}

	/**
	 * Method description
	 * <p>get the byte array saved in the buffer.</p>
	 * Method extra info
	 * <p></p>
	 * @return the byte array saved in the buffer.
	 */
	public byte[] getBytes() {
		if (pos == buf.length) {
			return buf;
		}else {
			byte[] tmp = new byte[pos];
			int i;
			for (i = 0; i < pos; i++) {
				tmp[i] = buf[i];
			}
			return tmp;
		}
	}
} // private class ByteBuffer

