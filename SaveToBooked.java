import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveToBooked {
   
    

public SaveToBooked(Room r){
      
        savetobook(r);
            
    }

    public void savetobook(Room room){
          try{
            AppendableObjectOutputStream2 aos= new AppendableObjectOutputStream2(new FileOutputStream("BookedRooms.ser"), true);
           aos.writeObject(room);
        }catch(Exception io){
            io.printStackTrace();
        }
            
    }


}
