import java.io.FileOutputStream;

public class SavUsertoFile {
    public SavUsertoFile(User u){
      
        savetoFile(u);
            
    }

    public void savetoFile(User u){
          try{
            AppendableObjectOutputStream aos= new AppendableObjectOutputStream(new FileOutputStream("users.ser"), true);
           aos.writeObject(u);
        }catch(Exception io){
            io.printStackTrace();
        }
            
    }
}
