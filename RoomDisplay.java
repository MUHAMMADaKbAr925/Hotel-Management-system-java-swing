import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Room implements Serializable {
    private int roomNumber;
    private String roomType;
    private int numberOfBeds;
    private boolean availability;
    private double price;

    public Room(int roomNumber, String roomType, int numberOfBeds, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
        this.availability = true; // All rooms are initially available
        
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }
}

public class RoomDisplay extends JFrame {
    private ArrayList<Room> allRooms;
    private ArrayList<Room> bookedRooms = new ArrayList<>();
    private static final String BOOKED_ROOMS_FILE = "BookedRooms.ser";

    public RoomDisplay() {
        setTitle("Rooms Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        allRooms = new ArrayList<>();
        // Populate rooms
        allRooms.add(new Room(101, "Standard Bedroom", 1, 150.0));
        allRooms.add(new Room(102, "Standard Bedroom", 1, 150.0));
        allRooms.add(new Room(104, "Standard Bedroom", 1, 150.0));
        allRooms.add(new Room(106, "Standard Bedroom", 1, 140.0));
        allRooms.add(new Room(201, "Master Bedroom", 2, 250.0));
        allRooms.add(new Room(202, "Master Bedroom", 2, 250.0));
        allRooms.add(new Room(301, "Presidential Suite", 3, 500.0));

        loadBookedRoomsFromFile();
        updateRoomAvailability();
        displayRooms();
    }

    private void loadBookedRoomsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKED_ROOMS_FILE))) {
            while (true) {
                Room bookedRoom = (Room) ois.readObject();
                bookedRooms.add(bookedRoom);
            }
        } catch (EOFException ignored) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateRoomAvailability() {
        for (Room bookedRoom : bookedRooms) {
            for (Room room : allRooms) {
                if (room.getRoomNumber() == bookedRoom.getRoomNumber()) {
                    room.setAvailability(false);
                    break;
                }
            }
        }
    }

    private void displayRooms() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Room room : allRooms) {
            JPanel roomPanel = new JPanel(new GridLayout(0, 2));
            roomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            roomPanel.setPreferredSize(new Dimension(300, 100));

            roomPanel.add(new JLabel("Room Number:"));
            roomPanel.add(new JLabel(String.valueOf(room.getRoomNumber())));

            roomPanel.add(new JLabel("Room Type:"));
            roomPanel.add(new JLabel(room.getRoomType()));

            roomPanel.add(new JLabel("Number of Beds:"));
            roomPanel.add(new JLabel(String.valueOf(room.getNumberOfBeds())));

            roomPanel.add(new JLabel("Price:"));
            roomPanel.add(new JLabel(String.valueOf(room.getPrice())));

            JLabel availabilityLabel = new JLabel("Availability: " + (room.isAvailable() ? "Available" : "Not Available"));
            roomPanel.add(availabilityLabel);

            JButton bookButton = new JButton("Book");
            if (room.isAvailable()) {
                bookButton.setEnabled(true);
            } else {
                bookButton.setEnabled(false);
            }

            bookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (requestCreditCardDetails()) {
                        room.setAvailability(false);
                        bookedRooms.add(room);
                        saveBookedRoomsToFile();
                        availabilityLabel.setText("Availability: Not Available");
                        bookButton.setEnabled(false);
                        JOptionPane.showMessageDialog(RoomDisplay.this, "Room booked!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            roomPanel.add(bookButton);
            mainPanel.add(roomPanel);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        getContentPane().add(scrollPane);
        setVisible(true);
    }

    private void saveBookedRoomsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKED_ROOMS_FILE))) {
            for (Room room : bookedRooms) {
                oos.writeObject(room);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean requestCreditCardDetails() {
        JTextField cardNumberField = new JTextField();
        JTextField expiryDateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Credit Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Expiration Date (MM/YY):"));
        panel.add(expiryDateField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Credit Card Information", JOptionPane.OK_CANCEL_OPTION);

        return result == JOptionPane.OK_OPTION;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomDisplay roomDisplay = new RoomDisplay();
        });
    }
}
