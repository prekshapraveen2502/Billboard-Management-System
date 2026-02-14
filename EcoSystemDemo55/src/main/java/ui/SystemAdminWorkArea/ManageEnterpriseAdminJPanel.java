/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.SystemAdminWorkArea;

import Business.EcoSystem;
import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Role.AdminRole;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raunak
 */
public class ManageEnterpriseAdminJPanel extends javax.swing.JPanel {

        private JPanel userProcessContainer;
        private EcoSystem system;

        // Professional color scheme
        private static final Color ADMIN_PRIMARY = new Color(142, 68, 173);
        private static final Color BUTTON_BG = new Color(155, 89, 182);
        private static final Color SUCCESS_BG = new Color(46, 204, 113);

        /**
         * Creates new form ManageEnterpriseJPanel
         */
        public ManageEnterpriseAdminJPanel(JPanel userProcessContainer, EcoSystem system) {
                initComponents();

                this.userProcessContainer = userProcessContainer;
                this.system = system;

                populateTable();
                populateNetworkComboBox();
                applyProfessionalStyling();
        }

        private void applyProfessionalStyling() {
                setBackground(Color.WHITE);

                // Style table
                enterpriseJTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                enterpriseJTable.setRowHeight(30);
                enterpriseJTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
                enterpriseJTable.getTableHeader().setBackground(ADMIN_PRIMARY);
                enterpriseJTable.getTableHeader().setForeground(Color.WHITE);
                jScrollPane1.setBorder(new javax.swing.border.LineBorder(new Color(189, 195, 199), 1));

                // Set table to show exactly 10 rows
                int visibleRows = 10;
                int rowHeight = 30;
                int headerHeight = 25;
                int tableHeight = (visibleRows * rowHeight) + headerHeight;
                jScrollPane1.setPreferredSize(new java.awt.Dimension(700, tableHeight));

                // Style labels
                jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
                jLabel1.setForeground(new Color(44, 62, 80));
                jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                jLabel2.setForeground(new Color(44, 62, 80));
                jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
                jLabel3.setForeground(new Color(44, 62, 80));
                jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
                jLabel4.setForeground(new Color(44, 62, 80));
                jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 14));
                jLabel5.setForeground(new Color(44, 62, 80));

                // Style text fields
                nameJTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                nameJTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                new javax.swing.border.LineBorder(new Color(189, 195, 199), 1),
                                new javax.swing.border.EmptyBorder(5, 10, 5, 10)));

                passwordJPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                passwordJPasswordField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                new javax.swing.border.LineBorder(new Color(189, 195, 199), 1),
                                new javax.swing.border.EmptyBorder(5, 10, 5, 10)));

                usernameJTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                usernameJTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                new javax.swing.border.LineBorder(new Color(189, 195, 199), 1),
                                new javax.swing.border.EmptyBorder(5, 10, 5, 10)));

                // Style combo boxes
                networkJComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                networkJComboBox.setBackground(Color.WHITE);
                enterpriseJComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                enterpriseJComboBox.setBackground(Color.WHITE);

                // Style buttons
                styleButton(submitJButton, "✓ Submit", SUCCESS_BG);
                styleButton(backJButton, "← Back", BUTTON_BG);
        }

        private void styleButton(javax.swing.JButton button, String text, Color bgColor) {
                button.setText(text);
                button.setFont(new Font("Segoe UI", Font.BOLD, 13));
                button.setBackground(bgColor);
                button.setForeground(Color.WHITE);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                button.setBackground(bgColor.brighter());
                        }

                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                button.setBackground(bgColor);
                        }
                });
        }

        private void populateTable() {
                DefaultTableModel model = (DefaultTableModel) enterpriseJTable.getModel();

                model.setRowCount(0);
                for (Network network : system.getNetworkList()) {
                        for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                                for (UserAccount userAccount : enterprise.getUserAccountDirectory()
                                                .getUserAccountList()) {
                                        Object[] row = new Object[5];
                                        row[0] = enterprise.getName();
                                        row[1] = network.getName();
                                        row[2] = userAccount.getUsername();
                                        row[3] = "Edit"; // Edit button text
                                        row[4] = "Delete"; // Delete button text

                                        model.addRow(row);
                                }
                        }
                }
        }

        private void populateNetworkComboBox() {
                networkJComboBox.removeAllItems();

                for (Network network : system.getNetworkList()) {
                        networkJComboBox.addItem(network);
                }
        }

        private void populateEnterpriseComboBox(Network network) {
                enterpriseJComboBox.removeAllItems();

                for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                        enterpriseJComboBox.addItem(enterprise);
                }

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                enterpriseJTable = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();
                networkJComboBox = new javax.swing.JComboBox();
                jLabel2 = new javax.swing.JLabel();
                usernameJTextField = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                enterpriseJComboBox = new javax.swing.JComboBox();
                submitJButton = new javax.swing.JButton();
                jLabel4 = new javax.swing.JLabel();
                nameJTextField = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();
                passwordJPasswordField = new javax.swing.JPasswordField();
                backJButton = new javax.swing.JButton();

                enterpriseJTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null }
                                },
                                new String[] {
                                                "Enterprise Name", "Network", "Username", "Edit", "Delete"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });

                // Add mouse listener for edit/delete button clicks
                enterpriseJTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                int row = enterpriseJTable.rowAtPoint(evt.getPoint());
                                int col = enterpriseJTable.columnAtPoint(evt.getPoint());

                                if (row >= 0) {
                                        String enterpriseName = (String) enterpriseJTable.getValueAt(row, 0);
                                        String networkName = (String) enterpriseJTable.getValueAt(row, 1);
                                        String username = (String) enterpriseJTable.getValueAt(row, 2);

                                        // Find the user account
                                        UserAccount targetUser = null;
                                        Enterprise targetEnterprise = null;

                                        for (Network network : system.getNetworkList()) {
                                                if (network.getName().equals(networkName)) {
                                                        for (Enterprise enterprise : network.getEnterpriseDirectory()
                                                                        .getEnterpriseList()) {
                                                                if (enterprise.getName().equals(enterpriseName)) {
                                                                        targetEnterprise = enterprise;
                                                                        for (UserAccount ua : enterprise
                                                                                        .getUserAccountDirectory()
                                                                                        .getUserAccountList()) {
                                                                                if (ua.getUsername().equals(username)) {
                                                                                        targetUser = ua;
                                                                                        break;
                                                                                }
                                                                        }
                                                                        break;
                                                                }
                                                        }
                                                        break;
                                                }
                                        }

                                        if (targetUser != null && targetEnterprise != null) {
                                                if (col == 3) { // Edit column
                                                        javax.swing.JTextField usernameField = new javax.swing.JTextField(
                                                                        targetUser.getUsername());
                                                        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField(
                                                                        targetUser.getPassword());
                                                        Object[] message = {
                                                                        "Username:", usernameField,
                                                                        "Password:", passwordField
                                                        };

                                                        int option = javax.swing.JOptionPane.showConfirmDialog(
                                                                        ManageEnterpriseAdminJPanel.this,
                                                                        message,
                                                                        "Edit Admin User",
                                                                        javax.swing.JOptionPane.OK_CANCEL_OPTION);

                                                        if (option == javax.swing.JOptionPane.OK_OPTION) {
                                                                String newUsername = usernameField.getText().trim();
                                                                String newPassword = new String(
                                                                                passwordField.getPassword());

                                                                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                                                                        javax.swing.JOptionPane.showMessageDialog(
                                                                                        ManageEnterpriseAdminJPanel.this,
                                                                                        "Username and Password cannot be empty!",
                                                                                        "Error",
                                                                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                                                                } else {
                                                                        // Check for duplicate username if changed
                                                                        boolean exists = false;
                                                                        if (!newUsername.equals(
                                                                                        targetUser.getUsername())) {
                                                                                if (targetEnterprise
                                                                                                .getUserAccountDirectory()
                                                                                                .checkIfUsernameExists(
                                                                                                                newUsername)) {
                                                                                        exists = true;
                                                                                }
                                                                        }

                                                                        if (exists) {
                                                                                javax.swing.JOptionPane
                                                                                                .showMessageDialog(
                                                                                                                ManageEnterpriseAdminJPanel.this,
                                                                                                                "Username already exists!",
                                                                                                                "Error",
                                                                                                                javax.swing.JOptionPane.ERROR_MESSAGE);
                                                                        } else {
                                                                                targetUser.setUsername(newUsername);
                                                                                targetUser.setPassword(newPassword);
                                                                                populateTable();
                                                                                javax.swing.JOptionPane
                                                                                                .showMessageDialog(
                                                                                                                ManageEnterpriseAdminJPanel.this,
                                                                                                                "Admin user updated successfully!",
                                                                                                                "Success",
                                                                                                                javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                                                        }
                                                                }
                                                        }
                                                } else if (col == 4) { // Delete column
                                                        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                                                                        ManageEnterpriseAdminJPanel.this,
                                                                        "Are you sure you want to delete admin user: "
                                                                                        + username + "?",
                                                                        "Confirm Delete",
                                                                        javax.swing.JOptionPane.YES_NO_OPTION,
                                                                        javax.swing.JOptionPane.WARNING_MESSAGE);

                                                        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                                                                targetEnterprise.getUserAccountDirectory()
                                                                                .getUserAccountList()
                                                                                .remove(targetUser);
                                                                populateTable();
                                                                javax.swing.JOptionPane.showMessageDialog(
                                                                                ManageEnterpriseAdminJPanel.this,
                                                                                "Admin user deleted successfully!",
                                                                                "Success",
                                                                                javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                                        }
                                                }
                                        }
                                }
                        }
                });
                jScrollPane1.setViewportView(enterpriseJTable);

                jLabel1.setText("Network");

                networkJComboBox.setModel(
                                new javax.swing.DefaultComboBoxModel(
                                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                networkJComboBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                networkJComboBoxActionPerformed(evt);
                        }
                });

                jLabel2.setText("Username");

                jLabel3.setText("Enterprise");

                enterpriseJComboBox.setModel(
                                new javax.swing.DefaultComboBoxModel(
                                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                submitJButton.setText("Submit");
                submitJButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                submitJButtonActionPerformed(evt);
                        }
                });

                jLabel4.setText("Password");

                jLabel5.setText("Name");

                backJButton.setText("<< Back");
                backJButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                backJButtonActionPerformed(evt);
                        }
                });

                // New Layout using GridBagLayout for better alignment
                this.setLayout(new java.awt.GridBagLayout());
                java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
                c.insets = new java.awt.Insets(10, 10, 10, 10); // Uniform padding

                // 1. Table (Top, spans 2 columns)
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                c.fill = java.awt.GridBagConstraints.BOTH;
                c.weightx = 1.0;
                c.weighty = 0.7; // Takes most vertical space
                this.add(jScrollPane1, c);

                // 2. Form Fields (Centered)
                // Reset specific constraints
                c.gridwidth = 1;
                c.weighty = 0;
                c.fill = java.awt.GridBagConstraints.HORIZONTAL;

                // Define specific size for inputs to line up nicely
                java.awt.Dimension fieldSize = new java.awt.Dimension(200, 30);
                networkJComboBox.setPreferredSize(fieldSize);
                enterpriseJComboBox.setPreferredSize(fieldSize);
                usernameJTextField.setPreferredSize(fieldSize);
                passwordJPasswordField.setPreferredSize(fieldSize);
                nameJTextField.setPreferredSize(fieldSize);

                // Row 1: Network
                c.gridy = 1;
                c.gridx = 0;
                c.anchor = java.awt.GridBagConstraints.EAST; // Label right-aligned
                c.weightx = 0.3; // distribution
                this.add(jLabel1, c);

                c.gridx = 1;
                c.anchor = java.awt.GridBagConstraints.WEST; // Field left-aligned
                c.weightx = 0.7;
                this.add(networkJComboBox, c);

                // Row 2: Enterprise
                c.gridy = 2;
                c.gridx = 0;
                c.anchor = java.awt.GridBagConstraints.EAST;
                this.add(jLabel3, c);

                c.gridx = 1;
                c.anchor = java.awt.GridBagConstraints.WEST;
                this.add(enterpriseJComboBox, c);

                // Row 3: Username
                c.gridy = 3;
                c.gridx = 0;
                c.anchor = java.awt.GridBagConstraints.EAST;
                this.add(jLabel2, c);

                c.gridx = 1;
                c.anchor = java.awt.GridBagConstraints.WEST;
                this.add(usernameJTextField, c);

                // Row 4: Password
                c.gridy = 4;
                c.gridx = 0;
                c.anchor = java.awt.GridBagConstraints.EAST;
                this.add(jLabel4, c);

                c.gridx = 1;
                c.anchor = java.awt.GridBagConstraints.WEST;
                this.add(passwordJPasswordField, c);

                // Row 5: Name
                c.gridy = 5;
                c.gridx = 0;
                c.anchor = java.awt.GridBagConstraints.EAST;
                this.add(jLabel5, c);

                c.gridx = 1;
                c.anchor = java.awt.GridBagConstraints.WEST;
                this.add(nameJTextField, c);

                // Row 6: Buttons (Bottom)
                c.gridy = 6;
                c.gridx = 0;
                c.gridwidth = 2;
                c.anchor = java.awt.GridBagConstraints.CENTER;
                c.fill = java.awt.GridBagConstraints.NONE;

                // Use a small panel for buttons to keep them together
                javax.swing.JPanel buttonPanel = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));
                buttonPanel.setOpaque(false);
                buttonPanel.add(backJButton);
                buttonPanel.add(submitJButton);

                this.add(buttonPanel, c);
        }// </editor-fold>//GEN-END:initComponents

        private void networkJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_networkJComboBoxActionPerformed

                Network network = (Network) networkJComboBox.getSelectedItem();
                if (network != null) {
                        populateEnterpriseComboBox(network);
                }

        }// GEN-LAST:event_networkJComboBoxActionPerformed

        private void submitJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_submitJButtonActionPerformed

                Enterprise enterprise = (Enterprise) enterpriseJComboBox.getSelectedItem();

                String username = usernameJTextField.getText();
                String password = String.valueOf(passwordJPasswordField.getPassword());
                String name = nameJTextField.getText();

                // Create employee directly for enterprise admin
                Employee employee = new Employee(name);

                UserAccount account = enterprise.getUserAccountDirectory().createUserAccount(username, password,
                                employee,
                                new AdminRole());
                populateTable();

        }// GEN-LAST:event_submitJButtonActionPerformed

        private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_backJButtonActionPerformed
                userProcessContainer.remove(this);
                Component[] componentArray = userProcessContainer.getComponents();
                Component component = componentArray[componentArray.length - 1];
                SystemAdminWorkAreaJPanel sysAdminwjp = (SystemAdminWorkAreaJPanel) component;
                sysAdminwjp.populateTree();
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.previous(userProcessContainer);
        }// GEN-LAST:event_backJButtonActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton backJButton;
        private javax.swing.JComboBox enterpriseJComboBox;
        private javax.swing.JTable enterpriseJTable;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextField nameJTextField;
        private javax.swing.JComboBox networkJComboBox;
        private javax.swing.JPasswordField passwordJPasswordField;
        private javax.swing.JButton submitJButton;
        private javax.swing.JTextField usernameJTextField;
        // End of variables declaration//GEN-END:variables
}
