package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    // Let's visit it
    vistor.visit(rootDirectory);

    // In case of a directory we have to check all the files inside
    if (rootDirectory.isDirectory()) {

      for (File file : rootDirectory.listFiles()) {
        if (file.isFile()) {
          // It's so file so we only visit it
          vistor.visit(file);
        }
        else if (file.isDirectory()) {
          // It's another directory so we have to explore it
          explore(file, vistor);
        }
      }
    }
  }

}
