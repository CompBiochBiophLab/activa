package com.o2hlink.healthgenius;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.location.Location;

/**
 * @author Adrian Rejas
 * 
 * This is a class with useful functions for the development of the rest of classes.
 */

public final class HealthGeniusUtil
{
      /**
       * The debug flag for this file only
       */
      public static final boolean DEBUG = HealthGenius.DEBUG_ALL | false;

      /**
       * Characters used to represent a hex string.
       */
      private static final java.lang.String[] hex = { "0", "1", "2", "3", "4",
                                                     "5", "6", "7", "8", "9",
                                                     "A", "B", "C", "D", "E",
                                                     "F" };

      /**
       * Convert an int to a hex string as an address at line head.
       * 
       * @param n - the int
       * @return hex string represents the int
       */
      public static java.lang.String intDec2strHex(int n)
      {

            java.lang.String str = java.lang.Integer.toString(n, 16);
            int len = str.length();
            int i;
            for (i = len; i < 4; i++)
            {
                  str = "0" + str;
            }
            return str;
      }

      /**
       * <p>
       * convert a pdu to a hex string.
       * </p>
       * 
       * @param pdu - the pdu
       * @return hex string represents the pdu
       */
      public static java.lang.String dumpBytes(byte[] pdu)
      {

            if (null == pdu)
            {
                  return "\ndumpBytes:null bytes";
            }
            java.lang.String ret = "";
            java.lang.String line = "";
            java.lang.String suffix = "";
            int i, count = pdu.length;
            byte h, l; 
            try
            {
                  for (i = 0; i < count; i++)
                  {
                        l = (byte) (pdu[i] & ((byte) 0x0F));
                        h = (byte) ((pdu[i] >> 4) & ((byte) 0x0F));
                        if ((i & 0x03) == 0)
                        {
                              ret += line + suffix;
                              suffix = " ; ";
                              line = "\n" + intDec2strHex(i) + "h:";
                        }
                        line += " " + hex[h] + hex[l];
                        if ((pdu[i] > 31) && (pdu[i] < 128))
                        {
                              suffix += new java.lang.String(pdu, i, 1);
                        }
                        else
                        {
                              suffix += ".";
                        }
                  }
            }
            catch (Exception e)
            {
                  if (DEBUG)
                  {
                        e.printStackTrace();
                  }
                  else
                  {
                        System.out.println(e.getMessage());
                  }
                  return "";
            }
            count = line.length();
            if (count > 7)
            {
                  for (i = count; i < 19; i++)
                  {
                        line += " ";
                  }
            }
            ret += line + suffix;
            return ret;
      }

      /**
       * <p>
       * convert a pdu to a hex string in reverse.
       * </p>
       * 
       * @param pdu - the pdu
       * @return hex string represents the pdu
       */
      public static java.lang.String reverseToHexdecimal(byte[] pdu)
      {

            if (null == pdu)
            {
                  return "";
            }
            java.lang.String line = "";
            byte h, l;
            for (int i = pdu.length - 1; i >= 0; i--)
            {
                  l = (byte) (pdu[i] & ((byte) 0x0F));
                  h = (byte) ((pdu[i] >> 4) & ((byte) 0x0F));
                  line += hex[h] + hex[l];
            }
            return line;
      }

      /**
       * <p>
       * make the current thread to sleep for millis milliseconds.
       * </p>
       * 
       * @param millis - how many milliseconds to sleep.
       */
      public static void sleep(int millis)
      {

            try
            {
                  Thread.sleep(millis);
            }
            catch (InterruptedException ie)
            {}
      }

      /**
       * <p>
       * get the integer value from a byte
       * </p>
       * 
       * @param byte - one byte for data type transfer
       */
      public static int byteToInt(byte b)
      {

            return (int) b & 0xFF;
      }

      /**
       * <p>
       * get the integer value from two bytes
       * </p>
       * 
       * @param buf[] - byte buffer for integer transfer
       * @param pos - start position for integer transfer
       */
      public static int byte2ToInt(byte buf[], int pos)
      {

            int i = 0;
            i += byteToInt(buf[pos++]) << 0;
            i += byteToInt(buf[pos++]) << 8;
            return i;
      }

      /**
       * <p>
       * get the integer value from four bytes
       * </p>
       * 
       * @param buf[] - byte buffer for integer transfer
       * @param pos - start position for integer transfer
       */
      public static int byte4ToInt(byte buf[], int pos)
      {

            int i = 0;
            i += byteToInt(buf[pos++]) << 0;
            i += byteToInt(buf[pos++]) << 8;
            i += byteToInt(buf[pos++]) << 16;
            i += byteToInt(buf[pos++]) << 24;
            return i;
      }

      /**
       * <p>
       * Checks if a String value corresponds to a Float value
       * </p>
       * 
       * @param measure - String to be tested
       * @return boolean indicating if the value has only digits and points
       */
      public static boolean checkFloat(String measure)
      {
            if ((measure==null) || (measure.length()==0)) {
                  return false;
            }
            else {
	            for (int i = 0; i < measure.length(); i++)
	            {
	                  switch (measure.charAt(i))
	                  {
	                        case '0':
	                        case '1':
	                        case '2':
	                        case '3':
	                        case '4':
	                        case '5':
	                        case '6':
	                        case '7':
	                        case '8':
	                        case '9':
	                        case '.':
	                              break;
	                        default:
	                              return false;
	                  }
	            }
	            return true;
            }
      }
      
      /**
       * <p>
       * Trim the Zeros on the left of a float represented by a String value
       * </p>
       * 
       * @param measure - String to be trimmed
       * @return String trimmed value
       */
      public static String trimZeros(String measure)
      {
            int firstNonZeroValue=0;
            
            //If first != '0' or ==null, return directly the incoming String
            if ((measure==null) || (measure.charAt(0)!='0')) { 
                  return measure;
            }
            
            else {
                  //Process the string to find the first nonZero value or the "."
                  boolean allZeros = true;
                  
                  outer_label:
                  {
                        while (allZeros==true) {
                              for (int i=0; i < measure.length(); i++) {
                                    if (measure.charAt(i) != '0') {
                                          firstNonZeroValue = i;
                                          allZeros = false;
                                          break outer_label;
                                    }
                              }
                              break outer_label;
                        }
                  }//outer label
                  if (allZeros!=true) {
                        return new String(measure.substring(firstNonZeroValue,measure.length()));
                  }
                  else {
                        return new String("");
                  }
            }
      }
      
      /**
       * <p>
       * Get the integer number represented by a series of bytes
       * </p>
       * 
       * @param arrayValue	Array of bytes representing the data to be transformed
       * @return int	Integer value for the array representation
       */
      public static int getIntegerFromByteArray (byte [] arrayValue) throws NumberFormatException {
            return Integer.parseInt(new String(arrayValue),16); 
      }
      
      /**
       * <p>
       * Get a subarray of an array of bytes
       * </p>
       * 
       * @param src	source array from which to extract the data
       * @param offset	byte from which the data would be extracted
       * @param len	Amount of bytes to extract
       * @return byte []	Subarray from the src one (could throw a Runtime exception)
       */
      public static byte[] subArray(byte[] src, int offset, int len)
      {
          byte[] out = new byte[len];
          System.arraycopy(src, offset, out, 0, len);
          if (DEBUG) {
                System.out.println("SubArray: " + dumpBytes(out));
          }
          return out;
      }

      /**
       * <p>
       * Get a subarray of an array of integers
       * </p>
       * 
       * @param src		source array from which to extract the data
       * @param offset	position from which the data would be extracted
       * @param len		Amount of values to extract
       * @return int []	Subarray from the src one (could throw a Runtime exception)
       */
      public static int[] subArray(int[] src, int offset, int len)
      {
          int[] out = new int[len];
          System.arraycopy(src, offset, out, 0, len);
          return out;
      }

      /**
       * <p>
       * Get the integer value for a little-endian 2-element byte array
       * </p>
       * 
       * @param word	little endian ordered 2-element byte array
       * @param	signed	if the word is signed or not
       * @return int 	value represented from the word
       */
      public static int wordLEToInt(byte[] word, boolean signed)
      {
            int result = (word[0] & 0xFF) | ((word[1] & 0xFF) << 8);
            if (!signed) {
                  if (DEBUG) {
                        System.out.println("wordLEtoInt: IN: " + dumpBytes(word) + ";OUT: " + result);
                  }
                  return result;
            }
            else {
                  if (DEBUG) {
                        System.out.println("wordLEtoInt: IN: " + dumpBytes(word) + ";OUT: " + (short)result);
                  }
                  return (short) result;
            }
      }

      /**
       * <p>
       * Get the integer value for a big-endian 2-element byte array
       * </p>
       * 
       * @param word	big endian ordered 2-element byte array
       * @param	signed	if the word is signed or not
       * @return int 	value represented from the word
       */
      public static int wordBEToInt(byte[] word, boolean signed)
      {
            int result = (word[1] & 0xFF) | ((word[0] & 0xFF) << 8);
            if (!signed) {
                  if (DEBUG) {
                        System.out.println("wordBEtoInt: IN: " + dumpBytes(word) + ";OUT: " + result);
                  }
                  return result;
            }
            else {
                  if (DEBUG) {
                        System.out.println("wordBEtoInt: IN: " + dumpBytes(word) + ";OUT: " + (short)result);
                  }
                  return (short) result;
            }
      }

      /**
       * <p>
       * Converts an integer array to a comma-separated-value String
       * </p>
       * 
       * @param values	Integer array with the values
       * @return String 	Csv str
       */
      public static String arrayToCsv(int[] values) {
            StringBuffer buffer = new StringBuffer();
            for (int i=0; i<values.length; i++) {
                buffer.append((i>0 ? "," : "") + values[i]);
            }
            return buffer.toString();
      }
      
      /**
       * <p>
       * Calculate CCITT CRC-16 for a given data array
       * </p>
       * 
       * @param data	data array
       * @return int 	CRC value
       */
      public static int CRC16(byte[] data) {
          final short[] crctab = { (short) 0x0000, (short) 0x1189,
                                  (short) 0x2312, (short) 0x329b, (short) 0x4624,
                                  (short) 0x57ad, (short) 0x6536, (short) 0x74bf,
                                  (short) 0x8c48, (short) 0x9dc1, (short) 0xaf5a,
                                  (short) 0xbed3, (short) 0xca6c, (short) 0xdbe5,
                                  (short) 0xe97e, (short) 0xf8f7, (short) 0x1081,
                                  (short) 0x0108, (short) 0x3393, (short) 0x221a,
                                  (short) 0x56a5, (short) 0x472c, (short) 0x75b7,
                                  (short) 0x643e, (short) 0x9cc9, (short) 0x8d40,
                                  (short) 0xbfdb, (short) 0xae52, (short) 0xdaed,
                                  (short) 0xcb64, (short) 0xf9ff, (short) 0xe876,
                                  (short) 0x2102, (short) 0x308b, (short) 0x0210,
                                  (short) 0x1399, (short) 0x6726, (short) 0x76af,
                                  (short) 0x4434, (short) 0x55bd, (short) 0xad4a,
                                  (short) 0xbcc3, (short) 0x8e58, (short) 0x9fd1,
                                  (short) 0xeb6e, (short) 0xfae7, (short) 0xc87c,
                                  (short) 0xd9f5, (short) 0x3183, (short) 0x200a,
                                  (short) 0x1291, (short) 0x0318, (short) 0x77a7,
                                  (short) 0x662e, (short) 0x54b5, (short) 0x453c,
                                  (short) 0xbdcb, (short) 0xac42, (short) 0x9ed9,
                                  (short) 0x8f50, (short) 0xfbef, (short) 0xea66,
                                  (short) 0xd8fd, (short) 0xc974, (short) 0x4204,
                                  (short) 0x538d, (short) 0x6116, (short) 0x709f,
                                  (short) 0x0420, (short) 0x15a9, (short) 0x2732,
                                  (short) 0x36bb, (short) 0xce4c, (short) 0xdfc5,
                                  (short) 0xed5e, (short) 0xfcd7, (short) 0x8868,
                                  (short) 0x99e1, (short) 0xab7a, (short) 0xbaf3,
                                  (short) 0x5285, (short) 0x430c, (short) 0x7197,
                                  (short) 0x601e, (short) 0x14a1, (short) 0x0528,
                                  (short) 0x37b3, (short) 0x263a, (short) 0xdecd,
                                  (short) 0xcf44, (short) 0xfddf, (short) 0xec56,
                                  (short) 0x98e9, (short) 0x8960, (short) 0xbbfb,
                                  (short) 0xaa72, (short) 0x6306, (short) 0x728f,
                                  (short) 0x4014, (short) 0x519d, (short) 0x2522,
                                  (short) 0x34ab, (short) 0x0630, (short) 0x17b9,
                                  (short) 0xef4e, (short) 0xfec7, (short) 0xcc5c,
                                  (short) 0xddd5, (short) 0xa96a, (short) 0xb8e3,
                                  (short) 0x8a78, (short) 0x9bf1, (short) 0x7387,
                                  (short) 0x620e, (short) 0x5095, (short) 0x411c,
                                  (short) 0x35a3, (short) 0x242a, (short) 0x16b1,
                                  (short) 0x0738, (short) 0xffcf, (short) 0xee46,
                                  (short) 0xdcdd, (short) 0xcd54, (short) 0xb9eb,
                                  (short) 0xa862, (short) 0x9af9, (short) 0x8b70,
                                  (short) 0x8408, (short) 0x9581, (short) 0xa71a,
                                  (short) 0xb693, (short) 0xc22c, (short) 0xd3a5,
                                  (short) 0xe13e, (short) 0xf0b7, (short) 0x0840,
                                  (short) 0x19c9, (short) 0x2b52, (short) 0x3adb,
                                  (short) 0x4e64, (short) 0x5fed, (short) 0x6d76,
                                  (short) 0x7cff, (short) 0x9489, (short) 0x8500,
                                  (short) 0xb79b, (short) 0xa612, (short) 0xd2ad,
                                  (short) 0xc324, (short) 0xf1bf, (short) 0xe036,
                                  (short) 0x18c1, (short) 0x0948, (short) 0x3bd3,
                                  (short) 0x2a5a, (short) 0x5ee5, (short) 0x4f6c,
                                  (short) 0x7df7, (short) 0x6c7e, (short) 0xa50a,
                                  (short) 0xb483, (short) 0x8618, (short) 0x9791,
                                  (short) 0xe32e, (short) 0xf2a7, (short) 0xc03c,
                                  (short) 0xd1b5, (short) 0x2942, (short) 0x38cb,
                                  (short) 0x0a50, (short) 0x1bd9, (short) 0x6f66,
                                  (short) 0x7eef, (short) 0x4c74, (short) 0x5dfd,
                                  (short) 0xb58b, (short) 0xa402, (short) 0x9699,
                                  (short) 0x8710, (short) 0xf3af, (short) 0xe226,
                                  (short) 0xd0bd, (short) 0xc134, (short) 0x39c3,
                                  (short) 0x284a, (short) 0x1ad1, (short) 0x0b58,
                                  (short) 0x7fe7, (short) 0x6e6e, (short) 0x5cf5,
                                  (short) 0x4d7c, (short) 0xc60c, (short) 0xd785,
                                  (short) 0xe51e, (short) 0xf497, (short) 0x8028,
                                  (short) 0x91a1, (short) 0xa33a, (short) 0xb2b3,
                                  (short) 0x4a44, (short) 0x5bcd, (short) 0x6956,
                                  (short) 0x78df, (short) 0x0c60, (short) 0x1de9,
                                  (short) 0x2f72, (short) 0x3efb, (short) 0xd68d,
                                  (short) 0xc704, (short) 0xf59f, (short) 0xe416,
                                  (short) 0x90a9, (short) 0x8120, (short) 0xb3bb,
                                  (short) 0xa232, (short) 0x5ac5, (short) 0x4b4c,
                                  (short) 0x79d7, (short) 0x685e, (short) 0x1ce1,
                                  (short) 0x0d68, (short) 0x3ff3, (short) 0x2e7a,
                                  (short) 0xe70e, (short) 0xf687, (short) 0xc41c,
                                  (short) 0xd595, (short) 0xa12a, (short) 0xb0a3,
                                  (short) 0x8238, (short) 0x93b1, (short) 0x6b46,
                                  (short) 0x7acf, (short) 0x4854, (short) 0x59dd,
                                  (short) 0x2d62, (short) 0x3ceb, (short) 0x0e70,
                                  (short) 0x1ff9, (short) 0xf78f, (short) 0xe606,
                                  (short) 0xd49d, (short) 0xc514, (short) 0xb1ab,
                                  (short) 0xa022, (short) 0x92b9, (short) 0x8330,
                                  (short) 0x7bc7, (short) 0x6a4e, (short) 0x58d5,
                                  (short) 0x495c, (short) 0x3de3, (short) 0x2c6a,
                                  (short) 0x1ef1, (short) 0x0f78 };

          short current = (short) 0x0000;
          for (int i = 0; i < data.length; i++)
          {
              current = (short) (((current >>> 8) & 0xff) ^ crctab[(current ^ data[i]) & 0xff]);
          }
          return ((int) current) & 0xFFFF;
    }

    /**
     * Dumps a calendar using format mm/dd/yyyy hh:mm:ss milliseconds AMPM TimeZone
     * 
     * @param cal
     * @return
     */
    public static String calendarToString(Calendar cal){
        if(cal==null){
            return "[null]";
        }
        StringBuffer buf = new StringBuffer();
        buf.append(" "+ leftPadText(""+(cal.get(Calendar.MONTH)+1), '0', 2));
        buf.append("/"+ leftPadText(""+cal.get(Calendar.DAY_OF_MONTH), '0', 2)); 
        buf.append("/"+ cal.get(Calendar.YEAR));
        buf.append(" "+ leftPadText(""+cal.get(Calendar.HOUR_OF_DAY), '0', 2));
        buf.append(":"+ leftPadText(""+cal.get(Calendar.MINUTE), '0', 2));
        buf.append(":"+ leftPadText(""+cal.get(Calendar.SECOND), '0', 2));
        buf.append(" Millis:"+ cal.get(Calendar.MILLISECOND));
        buf.append(" AMPM:"+ cal.get(Calendar.AM_PM));
        buf.append(" timeZone:"+ cal.getTimeZone().getID());
        return buf.toString();
    }

    /**
     * Returns a vector with a string splitted into parts
     * 
     * @param s The string to split
     * @param separator The string that separates each part
     * @return
     */
    public static Vector<String> splitText(String s, String separator) {
        Vector<String> v = new Vector<String>();
        int pos = s.indexOf(separator);
        if (pos < 0) {
            v.addElement(s);
        } else {
            int posOld = -1;
            while (pos >= 0) {
                v.addElement(s.substring(posOld + 1, pos));
                posOld = pos;
                pos = s.indexOf(separator, posOld + 1);
            }
            if (posOld < s.length() - 1) {
                v.addElement(s.substring(posOld + 1, s.length()));
            }
        }
        return v;
    }

    /**
     * Fill with characters at left of a string
     * 
     * @param txt The string to fill with characters
     * @param car The character to fill
     * @param len Maximum longitude of string
     * @return
     */
    public static String leftPadText(String txt, char car, int len) {
        if (txt == null) {
            return null;
        }
        if (txt.length() > len) {
            return txt.substring(txt.length() - len, txt.length());
        } else {
            StringBuffer ret = new StringBuffer();
            for (int i = len; i > txt.length(); i--) {
                ret.append(car);
            }
            for (int i = 0; i < txt.length(); i++) {
                ret.append(txt.charAt(i));
            }
            return ret.toString();
        }
    }

    /**
     * Separates the stringList in sub-strings considering the character separator.
     * If choicesString is null or it has only the separator character, returns an array with only one empty string
     * 
     * @param 	stringList	A string separated by the separator character 
     * @param 	separator 	A string containing the characters used to separate the stringList
     *  
     * @return	A string array with all the strings contained in the choicesString
     */
    public static String[] getArrayFromList(String stringList, String separator) {
        final int SEPARATOR_NOT_FOUND = -1;
        if((stringList == null) || (stringList.equals(separator))) {
            return new String[] {""};
        }
        
        int choicesCnt = 1;
        int separatorIdx = 0;
        for (int i = 0; i < stringList.length(); i++) {
            separatorIdx = stringList.indexOf(separator, i);
            if(separatorIdx != SEPARATOR_NOT_FOUND) {
                choicesCnt ++;
                i = separatorIdx;
            }else {
                break;
            }
        }
        String[] choices = new String[choicesCnt];
        int beginIdx = 0;
        int endIdx = stringList.indexOf(separator);
        for (int i = 0; i < choicesCnt-1; i++) {
            choices[i] = stringList.substring(beginIdx, endIdx);
            beginIdx = endIdx + separator.length();
            endIdx = stringList.indexOf(separator, beginIdx);
        }
        
        choices[choicesCnt-1] = stringList.substring(beginIdx);
        return choices;
    }
	public static byte[] numberEncode(long num, int size) {

		/* encode the num to a byte array in given size or determin its size if size < 0.  */
		if (size > 0) {
			byte[] a = new byte[size];
			int i;
			long val = num;
			for (i = 1; i <= size; i++) {
				a[size - i] = (byte) (val & 0xFF);
				val >>= 8;
			}
			return a;
		} else {
			if ((num > java.lang.Byte.MIN_VALUE) && (num < java.lang.Byte.MAX_VALUE)) {
				return numberEncode(num, 1);
			} else if ((num > java.lang.Short.MIN_VALUE) && (num < java.lang.Short.MAX_VALUE)) {
				return numberEncode(num, 2);
			} else if ((num > java.lang.Integer.MIN_VALUE) && (num < java.lang.Integer.MAX_VALUE)) {
				return numberEncode(num, 4);
			} else {
				return numberEncode(num, 8);
			}
		}

	}

	/**
	 * Convert mg/dL glucose value to MMOL unit
	 * 
	 * @param mg The mg/dL value
	 * @return MMOL value
	 */
	public static String mgToMmol(String mg) {
		int mmol_i = Integer.parseInt(mg) * 100 / 18;
		mmol_i = (mmol_i % 10 >= 5) ? (mmol_i + 10) / 10: mmol_i / 10;

		String mmol_s = Integer.toString(mmol_i); 
		mmol_s = (mmol_s.length() < 2) ? "0" + mmol_s : mmol_s;  
		mmol_s = mmol_s.substring(0, mmol_s.length() - 1) + "." + mmol_s.substring(mmol_s.length() - 1);   

		return mmol_s;
	}	
	
	/**
	 * Convert a Date object in a string representing the date at readable style.
	 */
	public static String dateToReadableString (Date date) {
		if (date == null) return "";
		else return ("" + (date.getYear() + 1900) + "/" + (date.getMonth() + 1) + "/" + date.getDate());
	}	
	
	/**
	 * Convert a string representing the date at readable style into a Date object.
	 * @throws Exception 
	 */
	public static Date ReadableStringToDate (String string) throws Exception {
		Date date = new Date(0);
		try {
			date.setDate(Integer.parseInt(string.substring(0, 2)));
			date.setMonth(Integer.parseInt(string.substring(3, 5)) - 1);
			date.setYear(Integer.parseInt(string.substring(6, 10)) - 1900);
		} catch (Exception e) {
			throw e;
		}
		return date;
	}
	
	public static Date universalReadableStringToDate (String string) throws Exception {
		Date date = new Date(0);
		try {
			date.setYear(Integer.parseInt(string.substring(0, 4)) - 1900);
			date.setMonth(Integer.parseInt(string.substring(5, 7)) - 1);
			date.setDate(Integer.parseInt(string.substring(8, 10)));
		} catch (Exception e) {
			throw e;
		}
		return date;
	}
	
	public static String timeToReadableString (Date date) {
		String returned = "";
		if (date.getHours() < 10) returned += "0" + date.getHours();
		else returned += date.getHours();
		returned += ":";
		if (date.getMinutes() < 10) returned += "0" + date.getMinutes();
		else returned += date.getMinutes();
		return returned;
	}
	
	public static String monthOfDate (Date date) {
		String returned = "";
		switch (date.getMonth() + 1) {
			case 1:
				returned = HealthGenius.myLanguageManager.MONTH_JANUARY;
				break;
			case 2:
				returned = HealthGenius.myLanguageManager.MONTH_FEBRUARY;
				break;
			case 3:
				returned = HealthGenius.myLanguageManager.MONTH_MARCH;
				break;
			case 4:
				returned = HealthGenius.myLanguageManager.MONTH_APRIL;
				break;
			case 5:
				returned = HealthGenius.myLanguageManager.MONTH_MAY;
				break;
			case 6:
				returned = HealthGenius.myLanguageManager.MONTH_JUNE;
				break;
			case 7:
				returned = HealthGenius.myLanguageManager.MONTH_JULY;
				break;
			case 8:
				returned = HealthGenius.myLanguageManager.MONTH_AUGUST;
				break;
			case 9:
				returned = HealthGenius.myLanguageManager.MONTH_SEPTEMBER;
				break;
			case 10:
				returned = HealthGenius.myLanguageManager.MONTH_OCTOBER;
				break;
			case 11:
				returned = HealthGenius.myLanguageManager.MONTH_NOVEMBER;
				break;
			case 12:
				returned = HealthGenius.myLanguageManager.MONTH_DECEMBER;
				break;
		}
		return returned;
	}
	
	public static String dateToXMLDate(Date date) {
		if (date == null) return null;
		String returned = "";
		returned += date.getYear() + 1900;
		if (date.getMonth() < 10) returned += "0" + (date.getMonth() + 1);
		else returned += (date.getMonth() + 1);
		if (date.getDate() < 10) returned += "0" + date.getDate();
		else returned += date.getDate();
		if (date.getHours() < 10) returned += "0" + date.getHours();
		else returned += date.getHours();
		if (date.getMinutes() < 10) returned += "0" + date.getMinutes();
		else returned += date.getMinutes();
		if (date.getSeconds() < 10) returned += "0" + date.getSeconds();
		else returned += date.getSeconds();
		return returned;
	}
	
	public static Date XMLDateToDate (String xmlDate) {
		if (xmlDate == null) return null;
		else if (xmlDate.equalsIgnoreCase("null")) return null;
		Date date = new Date(0);
		date.setYear(Integer.parseInt(xmlDate.substring(0,4))-1900);
    	date.setMonth(Integer.parseInt(xmlDate.substring(4,6))-1);
    	date.setDate(Integer.parseInt(xmlDate.substring(6,8)));
    	date.setHours(Integer.parseInt(xmlDate.substring(8,10)));
    	date.setMinutes(Integer.parseInt(xmlDate.substring(10,12)));
    	date.setSeconds(Integer.parseInt(xmlDate.substring(12,14)));
		return date;
	}
	
	public static boolean isAMail(String mail) {
		if (mail == null) return false;
		Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@");
		Matcher m = p.matcher(mail);
		return m.matches();
	}
	
	public static String reduceGraphicalPassword(String password) {
		String returned = "";
		for (int i = 0; i < password.length(); i++) {
			if (i == 0) {
				returned += password.charAt(i);
				continue;
			}
			if (password.charAt(i) != password.charAt(i - 1)) {
				returned += password.charAt(i);
				continue;				
			}
		}
		return returned;
	}
	
}