package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int number = 0;
  private char previous = ' ';

  public FileNumberingFilterWriter(Writer out){
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    int totalLen = 0;

    if (number == 0) {
      super.write("1\t", 0, 2);
      number ++;
    }

    // '\R' matches @CRLF, @CR or @LF only. (\r\n, \r or \n)
    // '?<=' to include the delimiter char in the part before the split
    String[] lines = str.split("(?<=\\R)");

    for(String line : lines) {
      if(line.endsWith("\n") || line.endsWith("\r")) {
        // Add line number for the next line after the new line char only
        line += String.valueOf(++number) + "\t";
        // Update the max length according to length of the line number and tab
        len += String.valueOf(number).length() + 1;
      }

      // Check if we have to stop according to the len parameter
      if (len < totalLen + line.length()) {
        super.write(line, off, len - totalLen);
        break;
      }
      else {
        super.write(line, off, line.length());
        totalLen += line.length();
      }
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(String.valueOf(cbuf));
  }

  @Override
  public void write(int c) throws IOException {
    if (number == 0) {
      super.write("1\t", 0, 2);
      number ++;
    }

    // \r and \r\n case
    if (previous == '\r' && (char)c != '\n') {
      super.write(String.valueOf(++number) + "\t");
    }

    super.write(c);

    if ((char)c == '\n') {
      super.write(String.valueOf(++number) + "\t");
    }

    previous = (char)c;
  }

}
