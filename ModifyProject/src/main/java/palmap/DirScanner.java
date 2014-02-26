package palmap;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DirScanner
{
  private String srcPath;
  private final String dirPath = "src\\test\\resources\\四维数据统计.mdb";
  public int count = 0;
  private boolean floorexsits = false;
  public int convertMode = 0;

  public DirScanner(String srcPath) {
	  this.srcPath = srcPath;
  }

  public void scanDir() throws IOException {
    Path path = Paths.get(this.srcPath, new String[0]);
    Files.walkFileTree(path, new FileVis());
  }

  private class FileVis extends SimpleFileVisitor<Path>
  {
    private final Path floorMdb = Paths.get("Floor.mdb", new String[0]);

    private FileVis() {
    }
    
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      Path fileName = file.getFileName();
      if (fileName.equals(this.floorMdb)) {
        MdbOper mdbOper = new MdbOper(DirScanner.this.dirPath, file.toString());
        if (DirScanner.this.convertMode == 1) {
        	mdbOper.setNavUDID("Mall");
        } else {
        	mdbOper.setPalmapUDID("Mall");
        }
        DirScanner.this.count += 1;
        DirScanner.this.floorexsits = true;
        System.out.println(file.getParent().getFileName().toString() + "修改完成");
      }
      return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
      throws IOException
    {
      if (dir.getParent().equals(Paths.get(DirScanner.this.dirPath, new String[0]))) {
        if (DirScanner.this.floorexsits)
          DirScanner.this.floorexsits = false;
        else {
          System.out.println(dir.getFileName() + "不包含Floor.mdb");
        }
      }

      return FileVisitResult.CONTINUE;
    }
  }
}
