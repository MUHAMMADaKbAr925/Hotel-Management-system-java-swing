import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String id;
    private String email;
private boolean bookedRoom;
private Room room;
    public User(String username, String password, String name, String surname, String id, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setBookedRoom(Room room) {
        this.bookedRoom = true;
        this.room = room;
    }

}

public class UserAuthentication {
    private ArrayList<User> userList = new ArrayList<>();

    private void saveUsersToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            outputStream.writeObject(userList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readUsersFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.ser"))) {
            userList = (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void registerUser(String username, String password, String name, String surname, String id, String email) {
        readUsersFromFile();
        userList.add(new User(username, password, name, surname, id, email));
        saveUsersToFile();
        JOptionPane.showMessageDialog(null, "User Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean verifyUserCredentials(String username, String password) {
        readUsersFromFile();
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public UserAuthentication() {
        JFrame frame = new JFrame("User Authentication");
        frame.setLayout(new GridLayout(5, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(signUpButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (verifyUserCredentials(username, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                ReservationsFrame newReservation= new ReservationsFrame();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signUpButton.addActionListener(e -> {
            JFrame signUpFrame = new JFrame("Sign Up");
            signUpFrame.setLayout(new GridLayout(7, 2));

            JLabel nameLabel = new JLabel("Name:");
            JLabel surnameLabel = new JLabel("Surname:");
            JLabel idLabel = new JLabel("ID:");
            JLabel emailLabel = new JLabel("Email:");
            JTextField nameField = new JTextField(20);
            JTextField surnameField = new JTextField(20);
            JTextField idField = new JTextField(20);
            JTextField emailField = new JTextField(20);

            JButton confirmSignUpButton = new JButton("Confirm Sign Up");

            signUpFrame.add(usernameLabel);
            signUpFrame.add(usernameField);
            signUpFrame.add(passwordLabel);
            signUpFrame.add(passwordField);
            signUpFrame.add(nameLabel);
            signUpFrame.add(nameField);
            signUpFrame.add(surnameLabel);
            signUpFrame.add(surnameField);
            signUpFrame.add(idLabel);
            signUpFrame.add(idField);
            signUpFrame.add(emailLabel);
            signUpFrame.add(emailField);
            signUpFrame.add(confirmSignUpButton);

            confirmSignUpButton.addActionListener(actionEvent -> {
                String signUpUsername = usernameField.getText();
                String signUpPassword = new String(passwordField.getPassword());
                String signUpName = nameField.getText();
                String signUpSurname = surnameField.getText();
                String signUpID = idField.getText();
                String signUpEmail = emailField.getText();

                registerUser(signUpUsername, signUpPassword, signUpName, signUpSurname, signUpID, signUpEmail);
                signUpFrame.dispose();
            });

            signUpFrame.pack();
            signUpFrame.setLocationRelativeTo(null);
            signUpFrame.setVisible(true);
        });

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new UserAuthentication();
    }
}
