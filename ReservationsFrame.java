import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReservationsFrame extends JFrame implements ActionListener {
    private JTextField checkInField, checkOutField, adultsField, bedsField;

    public ReservationsFrame() {
        setTitle("Reservations");
        setSize(400, 300);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        checkInField = new JTextField(15);
        checkOutField = new JTextField(15);
        adultsField = new JTextField(15);
        bedsField = new JTextField(15);

        addComponent(mainPanel, gbc, new JLabel("Check-In Date (DD/MM/YYYY):"), 0, 0);
        addComponent(mainPanel, gbc, checkInField, 0, 1);
        addComponent(mainPanel, gbc, new JLabel("Check-Out Date (DD/MM/YYYY):"), 1, 0);
        addComponent(mainPanel, gbc, checkOutField, 1, 1);
        addComponent(mainPanel, gbc, new JLabel("Select Adults:"), 2, 0);
        addComponent(mainPanel, gbc, adultsField, 2, 1);
        addComponent(mainPanel, gbc, new JLabel("Number of Beds:"), 3, 0);
        addComponent(mainPanel, gbc, bedsField, 3, 1);

        JButton reservationsButton = new JButton("Show Reservations");
        reservationsButton.addActionListener(this);
        addComponent(mainPanel, gbc, reservationsButton, 4, 0, 1, 2);

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(Container container, GridBagConstraints gbc, Component component, int row, int column) {
        gbc.gridx = column;
        gbc.gridy = row;
        container.add(component, gbc);
    }

    private void addComponent(Container container, GridBagConstraints gbc, Component component, int row, int column, int width, int height) {
        gbc.gridwidth = width;
        gbc.gridheight = height;
        addComponent(container, gbc, component, row, column);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
    }

    public void actionPerformed(ActionEvent e) {
        
        showRoomsWindow();
    }

    private void showRoomsWindow() {
       
        SwingUtilities.invokeLater(() -> new RoomDisplay());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReservationsFrame());
    }
}
