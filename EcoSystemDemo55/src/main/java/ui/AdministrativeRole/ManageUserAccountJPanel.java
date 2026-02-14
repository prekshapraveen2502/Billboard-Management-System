package ui.AdministrativeRole;

import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Role.Role;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class ManageUserAccountJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ManageUserAccountJPanel
     */
    private JPanel container;
    private Enterprise enterprise;

    // Professional color scheme
    private static final Color ADMIN_PRIMARY = new Color(142, 68, 173); // Purple for admin
    private static final Color BUTTON_BG = new Color(155, 89, 182);
    private static final Color PANEL_BG = new Color(236, 240, 241);

    public ManageUserAccountJPanel(JPanel container, Enterprise enterprise) {
        this.enterprise = enterprise;
        this.container = container;

        initComponentsCustom();

        popOrganizationComboBox();
        // employeeJComboBox.removeAllItems();
        popData();
    }

    private void initComponentsCustom() {
        setLayout(new java.awt.BorderLayout());
        setBackground(PANEL_BG);

        // ============================================================
        // 1. Header Panel
        // ============================================================
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(ADMIN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Manage User Accounts");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        add(headerPanel, java.awt.BorderLayout.NORTH);

        // ============================================================
        // 2. Main Content Panel
        // ============================================================
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(PANEL_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Table ---
        userJTable = new JTable();
        userJTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userJTable.setRowHeight(25);
        userJTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        userJTable.getTableHeader().setBackground(new Color(236, 240, 241));

        userJTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "User Name", "Role" }) {
            Class[] types = new Class[] { String.class, String.class };
            boolean[] canEdit = new boolean[] { true, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        JScrollPane jScrollPane1 = new JScrollPane(userJTable);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(jScrollPane1, gbc);

        // --- Reset weights ---
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BG);
        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "New User Account",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(44, 62, 80)));

        // Labels and Fields
        JLabel lblOrg = new JLabel("Organization:");
        lblOrg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        organizationJComboBox = new JComboBox();
        organizationJComboBox.addActionListener(evt -> organizationJComboBoxActionPerformed(evt));

        JLabel lblEmp = new JLabel("Employee:");
        lblEmp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        employeeJComboBox = new JComboBox();

        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleJComboBox = new JComboBox();

        JLabel lblUser = new JLabel("User Name:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameJTextField = new JTextField(15);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordJTextField = new JTextField(15);

        GridBagConstraints fGbc = new GridBagConstraints();
        fGbc.insets = new Insets(8, 8, 8, 8);
        fGbc.anchor = GridBagConstraints.WEST;

        // Row 0
        fGbc.gridx = 0;
        fGbc.gridy = 0;
        formPanel.add(lblOrg, fGbc);
        fGbc.gridx = 1;
        formPanel.add(organizationJComboBox, fGbc);

        // Row 1
        fGbc.gridx = 0;
        fGbc.gridy = 1;
        formPanel.add(lblEmp, fGbc);
        fGbc.gridx = 1;
        formPanel.add(employeeJComboBox, fGbc);

        // Row 2
        fGbc.gridx = 0;
        fGbc.gridy = 2;
        formPanel.add(lblRole, fGbc);
        fGbc.gridx = 1;
        formPanel.add(roleJComboBox, fGbc);

        // Row 3
        fGbc.gridx = 0;
        fGbc.gridy = 3;
        formPanel.add(lblUser, fGbc);
        fGbc.gridx = 1;
        formPanel.add(nameJTextField, fGbc);

        // Row 4
        fGbc.gridx = 0;
        fGbc.gridy = 4;
        formPanel.add(lblPass, fGbc);
        fGbc.gridx = 1;
        formPanel.add(passwordJTextField, fGbc);

        gbc.gridy = 1;
        contentPanel.add(formPanel, gbc);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(PANEL_BG);

        backJButton = new JButton("<< Back");
        styleButton(backJButton, new Color(127, 140, 141));
        backJButton.addActionListener(evt -> backJButtonActionPerformed(evt));

        createUserJButton = new JButton("Create Account");
        styleButton(createUserJButton, BUTTON_BG);
        createUserJButton.addActionListener(evt -> createUserJButtonActionPerformed(evt));

        GridBagConstraints bGbc = new GridBagConstraints();
        bGbc.insets = new Insets(10, 0, 10, 0);

        bGbc.gridx = 0;
        bGbc.weightx = 1.0;
        bGbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(backJButton, bGbc);

        bGbc.gridx = 1;
        bGbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(createUserJButton, bGbc);

        gbc.gridy = 2;
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new java.awt.Dimension(180, 40));
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

    public void popOrganizationComboBox() {
        organizationJComboBox.removeAllItems();

        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            organizationJComboBox.addItem(organization);
        }
    }

    public void populateEmployeeComboBox(Organization organization) {
        employeeJComboBox.removeAllItems();

        for (Employee employee : organization.getEmployeeDirectory().getEmployeeList()) {
            employeeJComboBox.addItem(employee);
        }
    }

    private void populateRoleComboBox(Organization organization) {
        roleJComboBox.removeAllItems();
        for (Role role : organization.getSupportedRole()) {
            roleJComboBox.addItem(role);
        }
    }

    public void popData() {

        DefaultTableModel model = (DefaultTableModel) userJTable.getModel();

        model.setRowCount(0);

        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            for (UserAccount ua : organization.getUserAccountDirectory().getUserAccountList()) {
                Object row[] = new Object[2];
                row[0] = ua;
                row[1] = ua.getRole();
                ((DefaultTableModel) userJTable.getModel()).addRow(row);
            }
        }
    }

    private void createUserJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String userName = nameJTextField.getText();
        String password = passwordJTextField.getText();
        Organization organization = (Organization) organizationJComboBox.getSelectedItem();
        Employee employee = (Employee) employeeJComboBox.getSelectedItem();
        Role role = (Role) roleJComboBox.getSelectedItem();

        if (organization == null || employee == null || role == null || userName.isEmpty() || password.isEmpty()) {
            return;
        }

        organization.getUserAccountDirectory().createUserAccount(userName, password, employee, role);

        popData();
        nameJTextField.setText("");
        passwordJTextField.setText("");
    }

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        container.remove(this);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }

    private void organizationJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        Organization organization = (Organization) organizationJComboBox.getSelectedItem();
        if (organization != null) {
            populateEmployeeComboBox(organization);
            populateRoleComboBox(organization);
        }
    }

    // Variables declaration
    private javax.swing.JButton backJButton;
    private javax.swing.JButton createUserJButton;
    private javax.swing.JComboBox employeeJComboBox;
    private javax.swing.JComboBox organizationJComboBox;
    private javax.swing.JComboBox roleJComboBox;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JTextField passwordJTextField;
    private javax.swing.JTable userJTable;
    // End of variables declaration
}
