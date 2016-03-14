package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

    String[] result = new String[2];
    Boolean endWithNL = false;

    // Check if there is a new line at the end
    if (lines.charAt(lines.length() - 1) == '\n' || lines.charAt(lines.length() - 1) == '\r'){
      endWithNL = true;
    }

    // '\R' matches @CRLF, @CR or @LF only. (\r\n, \r or \n)
    // '?<=' to include the delimiter char in the part before the split
    String[] splitedLines = lines.split("(?<=\\R)");

    // Check if there is only one line not ending with a new line
    if (splitedLines.length == 1 && !endWithNL) {
      result[0] = "";
      result[1] = lines;
    }
    else {
      result[0] = splitedLines[0];
      // We take only what comes after the first line
      result[1] = lines.substring(splitedLines[0].length());
    }

    return result;
  }

}
