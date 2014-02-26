package palmap;

import java.io.IOException;

public class ModifyUdidUtil
{
  public DirScanner ds;
  
  public ModifyUdidUtil(String path) {
	  this.ds = new DirScanner(path);
  }

  public void modifyMdb() throws IOException {
    this.ds.scanDir();
  }
  
}