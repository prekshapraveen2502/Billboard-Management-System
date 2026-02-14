package ui.AdministrativeRole;

import Business.Employee.Employee;
import Business.Organization.Organization;
import Business.Organization.OrganizationDirectory;
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
 * @author raunak
 */
public class ManageEmployeeJPanel extends javax.swing.JPanel {

    private OrganizationDirectory organizationDir;
    private JPanel userProcessContainer;

    // Professional color scheme
    private static final Color ADMIN_PRIMARY = new Color(142, 68, 173); // Purple for admin
    private static final Color BUTTON_BG = new Color(155, 89, 182);
    private static final Color PANEL_BG = new Color(236, 240, 241);

    /**
     * Creates new form ManageOrganizationJPanel
     */
    public ManageEmployeeJPanel(JPanel userProcessContainer, OrganizationDirectory organizationDir) {
        this.userProcessContainer = userProcessContainer;
        this.organizationDir = organizationDir;

        initComponentsCustom();

        populateOrganizationComboBox();
        populateOrganizationEmpComboBox();
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

        JLabel titleLabel = new JLabel("Manage Employee");
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

        // --- Top Control: Select Organization ---
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(PANEL_BG);

        JLabel lblFilter = new JLabel("Filter by Organization:");
        lblFilter.setFont(new Font("Segoe UI", Font.BOLD, 14));

        organizationJComboBox = new JComboBox();
        organizationJComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        organizationJComboBox.addActionListener(evt -> organizationJComboBoxActionPerformed(evt));

        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.insets = new Insets(5, 5, 5, 5);
        topPanel.add(lblFilter, topGbc);
        topPanel.add(organizationJComboBox, topGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(topPanel, gbc);

        // --- Table ---
        organizationJTable = new JTable();
        organizationJTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        organizationJTable.setRowHeight(25);
        organizationJTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizationJTable.getTableHeader().setBackground(new Color(236, 240, 241));

        organizationJTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Name" }) {
            Class[] types = new Class[] { String.class, String.class };
            boolean[] canEdit = new boolean[] { false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        JScrollPane jScrollPane1 = new JScrollPane(organizationJTable);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        contentPanel.add(jScrollPane1, gbc);

        // --- Reset weights ---
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Bottom Control: Add Employee ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BG);
        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "New Employee",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(44, 62, 80)));

        JLabel lblOrg = new JLabel("Organization:");
        lblOrg.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        organizationEmpJComboBox = new JComboBox();
        organizationEmpJComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        nameJTextField = new JTextField(15);
        nameJTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.anchor = GridBagConstraints.WEST;

        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(lblOrg, formGbc);
        formGbc.gridx = 1;
        formPanel.add(organizationEmpJComboBox, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(lblName, formGbc);
        formGbc.gridx = 1;
        formPanel.add(nameJTextField, formGbc);

        gbc.gridy = 2;
        contentPanel.add(formPanel, gbc);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(PANEL_BG);

        backJButton = new JButton("<< Back");
        styleButton(backJButton, new Color(127, 140, 141));
        backJButton.addActionListener(evt -> backJButtonActionPerformed(evt));

        addJButton = new JButton("Create Employee");
        styleButton(addJButton, BUTTON_BG);
        addJButton.addActionListener(evt -> addJButtonActionPerformed(evt));

        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(10, 0, 10, 0);
        btnGbc.gridx = 0;
        btnGbc.weightx = 1.0;
        btnGbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(backJButton, btnGbc);

        btnGbc.gridx = 1;
        btnGbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(addJButton, btnGbc);

        gbc.gridy = 3;
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

    public void populateOrganizationComboBox() {
        organizationJComboBox.removeAllItems();

        for (Organization organization : organizationDir.getOrganizationList()) {
            organizationJComboBox.addItem(organization);
        }
    }

    public void populateOrganizationEmpComboBox() {
        organizationEmpJComboBox.removeAllItems();

        for (Organization organization : organizationDir.getOrganizationList()) {
            organizationEmpJComboBox.addItem(organization);
        }
    }

    private void populateTable(Organization organization) {
        DefaultTableModel model = (DefaultTableModel) organizationJTable.getModel();

        model.setRowCount(0);

        for (Employee employee : organization.getEmployeeDirectory().getEmployeeList()) {
            Object[] row = new Object[2];
            row[0] = employee.getId();
            row[1] = employee.getName();
            model.addRow(row);
        }
    }

    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Organization organization = (Organization) organizationEmpJComboBox.getSelectedItem();
        String name = nameJTextField.getText();

        if (organization == null || name.isEmpty()) {
            return;
        }

        organization.getEmployeeDirectory().createEmployee(name);
        // If the selected filter organization matches, update table
        Organization filterOrg = (Organization) organizationJComboBox.getSelectedItem();
        if (filterOrg != null && filterOrg.equals(organization)) {
            populateTable(organization);
        }
        nameJTextField.setText("");
    }

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }

    private void organizationJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        Organization organization = (Organization) organizationJComboBox.getSelectedItem();
        if (organization != null) {
            populateTable(organization);
        }
    }

    // Variables declaration
    private javax.swing.JButton addJButton;
    private javax.swing.JButton backJButton;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JComboBox organizationEmpJComboBox;
    private javax.swing.JComboBox organizationJComboBox;
    private javax.swing.JTable organizationJTable;
    // End of variables declaration
}
