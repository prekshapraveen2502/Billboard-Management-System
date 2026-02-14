package ui.AdministrativeRole;

import Business.Enterprise.Enterprise;
import Business.Enterprise.EnterpriseType;
import Business.Organization.Organization;
import Business.Organization.Organization.Type;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raunak
 */
public class ManageOrganizationJPanel extends javax.swing.JPanel {

        private OrganizationDirectory directory;
        private JPanel userProcessContainer;
        private Enterprise enterprise;
        private JComboBox<Organization.Type> organizationJComboBox;
        private JTable organizationJTable;
        private JButton addJButton;
        private JButton backJButton;

        // Professional color scheme
        private static final Color ADMIN_PRIMARY = new Color(142, 68, 173); // Purple for admin

        /**
         * Creates new form ManageOrganizationJPanel
         */
        public ManageOrganizationJPanel(JPanel userProcessContainer, OrganizationDirectory directory,
                        Enterprise enterprise) {
                this.userProcessContainer = userProcessContainer;
                this.directory = directory;
                this.enterprise = enterprise;

                initComponentsCustom();

                populateTable();
                populateCombo();
        }

        private void initComponentsCustom() {
                setLayout(new java.awt.BorderLayout());
                setBackground(Color.WHITE);

                // ============================================================
                // 1. Header Panel
                // ============================================================
                JPanel headerPanel = new JPanel(new GridBagLayout());
                headerPanel.setBackground(ADMIN_PRIMARY);
                headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

                JLabel titleLabel = new JLabel("View Organizations");
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                titleLabel.setForeground(Color.WHITE);

                headerPanel.add(titleLabel);
                add(headerPanel, java.awt.BorderLayout.NORTH);

                // ============================================================
                // 2. Main Content Panel
                // ============================================================
                JPanel contentPanel = new JPanel(new GridBagLayout());
                contentPanel.setBackground(Color.WHITE);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Table
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

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                gbc.weightx = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.8; // Table takes most space
                contentPanel.add(jScrollPane1, gbc);

                // ============================================================
                // 3. Form Panel (Add Organization)
                // ============================================================
                JPanel formPanel = new JPanel(new GridBagLayout());
                formPanel.setBackground(Color.WHITE);
                formPanel.setBorder(new javax.swing.border.TitledBorder(
                                new javax.swing.border.LineBorder(new Color(189, 195, 199)),
                                "Add Organization",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                new Color(44, 62, 80)));

                GridBagConstraints fbc = new GridBagConstraints();
                fbc.insets = new Insets(10, 10, 10, 10);
                fbc.fill = GridBagConstraints.HORIZONTAL;

                // Label
                JLabel typeLabel = new JLabel("Organization Type:");
                typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                fbc.gridx = 0;
                fbc.gridy = 0;
                formPanel.add(typeLabel, fbc);

                // Combo Box
                organizationJComboBox = new JComboBox<>();
                organizationJComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                organizationJComboBox.setBackground(Color.WHITE);
                fbc.gridx = 1;
                fbc.gridy = 0;
                fbc.weightx = 1.0;
                formPanel.add(organizationJComboBox, fbc);

                // Add Button
                addJButton = new JButton("Add Organization");
                styleButton(addJButton, new Color(46, 204, 113)); // Success Green
                addJButton.addActionListener(evt -> addJButtonActionPerformed(evt));
                fbc.gridx = 2;
                fbc.gridy = 0;
                fbc.weightx = 0.0;
                formPanel.add(addJButton, fbc);

                gbc.gridy = 1;
                gbc.weighty = 0.0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                contentPanel.add(formPanel, gbc);

                // ============================================================
                // 4. Back Button Area
                // ============================================================
                JPanel buttonPanel = new JPanel(new GridBagLayout());
                buttonPanel.setBackground(Color.WHITE);

                backJButton = new JButton("<< Back");
                styleButton(backJButton, new Color(127, 140, 141)); // Grey for back
                backJButton.addActionListener(evt -> backJButtonActionPerformed(evt));

                GridBagConstraints subGbc = new GridBagConstraints();
                subGbc.insets = new Insets(10, 0, 10, 0);
                subGbc.gridx = 0;
                subGbc.gridy = 0;
                subGbc.weightx = 1.0;
                subGbc.anchor = GridBagConstraints.WEST;
                buttonPanel.add(backJButton, subGbc);

                gbc.gridy = 2; // Moved up since control panel is gone
                gbc.weighty = 0.0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
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

        private void populateTable() {
                DefaultTableModel model = (DefaultTableModel) organizationJTable.getModel();

                model.setRowCount(0);

                for (Organization organization : directory.getOrganizationList()) {
                        Object[] row = new Object[2];
                        row[0] = organization.getOrganizationID();
                        row[1] = organization.getName();

                        model.addRow(row);
                }
        }

        private void populateCombo() {
                organizationJComboBox.removeAllItems();
                EnterpriseType entType = enterprise.getEnterpriseType();

                for (Type type : Organization.Type.values()) {
                        if (entType == EnterpriseType.AD_AGENCY) {
                                if (type == Type.AGENCY_CLIENT_SERVICES || type == Type.AGENCY_CAMPAIGN_PLANNING) {
                                        organizationJComboBox.addItem(type);
                                }
                        } else if (entType == EnterpriseType.BILLBOARD_OPERATOR) {
                                if (type == Type.BILLBOARD_SALES || type == Type.BILLBOARD_OPERATIONS) {
                                        organizationJComboBox.addItem(type);
                                }
                        } else if (entType == EnterpriseType.CITY_SERVICES) {
                                if (type == Type.CITY_PERMITS || type == Type.COMPLIANCE_INSPECTION) {
                                        organizationJComboBox.addItem(type);
                                }
                        } else if (entType == EnterpriseType.POWER_UTILITY) {
                                if (type == Type.POWERGRID_MAINTENANCE || type == Type.FIELD_ENGINEERS) {
                                        organizationJComboBox.addItem(type);
                                }
                        }
                }
        }

        private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {
                Type type = (Type) organizationJComboBox.getSelectedItem();

                if (type == null) {
                        JOptionPane.showMessageDialog(null, "Please select an organization type.", "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // If enterprise specific logic is needed, add checks here.
                directory.createOrganization(type);
                populateTable();
                JOptionPane.showMessageDialog(null, "Organization added successfully!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {
                userProcessContainer.remove(this);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.previous(userProcessContainer);
        }
}
