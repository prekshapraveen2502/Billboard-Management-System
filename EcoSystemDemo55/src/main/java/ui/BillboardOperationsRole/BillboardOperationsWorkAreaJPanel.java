package ui.BillboardOperationsRole;

import Business.Billboard.Billboard;
import Business.Billboard.BillboardStatus;
import Business.Billboard.MaintenanceRecord;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.EnterpriseType;
import Business.Enterprise.PowerUtilityEnterprise;
import Business.Enterprise.SkyViewBillboardEnterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.PowerGridMaintenanceOrganization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.MaintenanceRequest;
import Business.WorkQueue.PowerIssueRequest;
import Business.WorkQueue.WorkRequest;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class BillboardOperationsWorkAreaJPanel extends JPanel {

    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Enterprise enterprise;
    private EcoSystem business;
    private JTable boardStatusTable;
    private SkyViewBillboardEnterprise skyViewEnterprise;

    private static final Color HEADER_BG = new Color(41, 128, 185);

    public BillboardOperationsWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
            Organization organization, Enterprise enterprise,
            EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;

        if (enterprise instanceof SkyViewBillboardEnterprise) {
            this.skyViewEnterprise = (SkyViewBillboardEnterprise) enterprise;
        }

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Billboard Operations Manager");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel userLabel = new JLabel("Welcome, " + account.getUsername());
        userLabel.setForeground(Color.WHITE);
        headerPanel.add(userLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab("Board Status", createBoardStatusPanel());
        tabbedPane.addTab("Maintenance Requests", createMaintenanceRequestsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBoardStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        String[] columns = { "ID", "Location", "Status", "Description", "Object" }; // Added Object col to store
                                                                                    // Billboard
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        boardStatusTable = new JTable(model);

        // Hide object column
        boardStatusTable.removeColumn(boardStatusTable.getColumnModel().getColumn(4));

        populateBoardTable(model);

        panel.add(new JScrollPane(boardStatusTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton refreshBtn = createStyledButton("Refresh", HEADER_BG);
        JButton maintainBtn = createStyledButton("Request Maintenance", HEADER_BG);

        refreshBtn.addActionListener(e -> populateBoardTable(model));
        maintainBtn.addActionListener(e -> handleRequestMaintenance());

        buttonPanel.add(maintainBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void handleRequestMaintenance() {
        int selectedRow = boardStatusTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Get from hidden column 4 (model index 4)
            // Even if view column is removed, we must access via model
            DefaultTableModel model = (DefaultTableModel) boardStatusTable.getModel();
            // Convert view index to model index just in case of sorting/filtering later
            // (good practice)
            int modelRow = boardStatusTable.convertRowIndexToModel(selectedRow);
            Billboard b = (Billboard) model.getValueAt(modelRow, 4);

            // Input Issue
            JTextField issueField = new JTextField();
            String[] urgencyLevels = { "Low", "Medium", "High", "Critical" };
            JComboBox<String> urgencyCombo = new JComboBox<>(urgencyLevels);

            JPanel inputPanel = new JPanel(new GridLayout(0, 1));
            inputPanel.add(new JLabel("Describe the issue:"));
            inputPanel.add(issueField);
            inputPanel.add(new JLabel("Urgency Level:"));
            inputPanel.add(urgencyCombo);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Report Maintenance Issue",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String issue = issueField.getText();
                String urgency = (String) urgencyCombo.getSelectedItem();

                if (issue.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please describe the issue.");
                    return;
                }

                // Create Request
                MaintenanceRequest req = new MaintenanceRequest();
                req.setSender(account);
                req.setBoardId(b.getBoardId());
                req.setIssueDescription(issue);
                req.setUrgencyLevel(urgency);
                req.setMessage(issue);
                req.setStatus("Pending");

                // Add to OWN organization queue (Ops)
                if (organization != null) {
                    organization.getWorkQueue().getWorkRequestList().add(req);
                    account.getWorkQueue().getWorkRequestList().add(req);
                    JOptionPane.showMessageDialog(this, "Maintenance Request Created!");

                    // Refresh Request Table? It's in another tab. user will see it when they
                    // switch.
                    // We might need to refresh requests panel model if we could access it, or just
                    // let user click refresh there.
                    // But wait, createMaintenanceRequestsPanel creates its own model/table.
                    // Ideally we should have a refresh method for that tab or reload it on tab
                    // switch.
                    // For now, let's just confirm success.
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a billboard.");
        }
    }

    private void populateBoardTable(DefaultTableModel model) {
        model.setRowCount(0);
        if (skyViewEnterprise != null) {
            for (Billboard b : skyViewEnterprise.getBillboardDirectory().getBillboards()) {
                model.addRow(new Object[] { b.getBoardId(), b.getLocation(), b.getStatus().toString(),
                        b.getDescription(), b });
            }
        }
    }

    private JPanel createMaintenanceRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        String[] columns = { "Sender", "Status", "Message", "Board ID", "Request Object" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Hide the Request Object column
        table.removeColumn(table.getColumnModel().getColumn(4));

        populateRequestTable(model);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton processBtn = createStyledButton("Process Request", HEADER_BG);
        JButton completeBtn = createStyledButton("Complete Maintenance", HEADER_BG);
        JButton powerBtn = createStyledButton("Forward Power Issue", HEADER_BG);

        processBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                MaintenanceRequest request = (MaintenanceRequest) model.getValueAt(selectedRow, 4);
                if (!request.getStatus().equals("Completed")) {
                    request.setStatus("Processing");
                    request.setReceiver(account);

                    Billboard b = findBillboard(request.getBoardId());
                    if (b != null) {
                        b.setStatus(BillboardStatus.MAINTENANCE);
                        MaintenanceRecord record = new MaintenanceRecord(request.getMessage(), "Maintenance");
                        b.addMaintenanceRecord(record);
                    }

                    populateRequestTable(model);
                    JOptionPane.showMessageDialog(this, "Request processed. Billboard set to Maintenance.");
                } else {
                    JOptionPane.showMessageDialog(this, "Request already completed.");
                }
            }
        });

        completeBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                MaintenanceRequest request = (MaintenanceRequest) model.getValueAt(selectedRow, 4);
                if (request.getStatus().equals("Processing")) {
                    request.setStatus("Completed");
                    request.setResolveDate(new Date());

                    Billboard b = findBillboard(request.getBoardId());
                    if (b != null) {
                        b.setStatus(BillboardStatus.AVAILABLE);
                        // Close the open maintenance record
                        for (MaintenanceRecord mr : b.getMaintenanceHistory()) {
                            if (mr.getStatus().equals("Open")) {
                                mr.setStatus("Closed");
                                mr.setResolution("Maintenance Completed");
                            }
                        }
                    }

                    populateRequestTable(model);
                    JOptionPane.showMessageDialog(this, "Maintenance Completed. Billboard Available.");
                } else {
                    JOptionPane.showMessageDialog(this, "Request must be in Processing state.");
                }
            }
        });

        powerBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                MaintenanceRequest mRequest = (MaintenanceRequest) model.getValueAt(selectedRow, 4);

                // Create Power Issue Request
                PowerIssueRequest powerRequest = new PowerIssueRequest();
                powerRequest.setMessage("Power issue reported via Maintenance: " + mRequest.getMessage());
                powerRequest.setSender(account);
                powerRequest.setStatus("Sent");
                powerRequest.setBoardId(mRequest.getBoardId());
                powerRequest.setSeverity("High"); // Default

                // Find Power Utility Enterprise
                Enterprise powerEnt = findPowerUtilityEnterprise();
                if (powerEnt != null) {
                    // Find Organization? Or just send to Enterprise queue?
                    // Usually sends to specific Organization or Enterprise queue.
                    // Instructions say "Forward ... to PowerGridCoordinator"
                    // Assuming PowerGridCoordinator is in an organization in PowerUtilityEnterprise

                    boolean sent = false;
                    for (Organization org : powerEnt.getOrganizationDirectory().getOrganizationList()) {
                        if (org instanceof PowerGridMaintenanceOrganization) {
                            org.getWorkQueue().getWorkRequestList().add(powerRequest);
                            sent = true;
                            break;
                        }
                    }

                    if (sent) {
                        JOptionPane.showMessageDialog(this, "Power Issue Request Sent to Power Utility.");
                        account.getWorkQueue().getWorkRequestList().add(powerRequest);
                    } else {
                        JOptionPane.showMessageDialog(this, "Power Grid Maintenance Organization not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Power Utility Enterprise not found.");
                }
            }
        });

        buttonPanel.add(processBtn);
        buttonPanel.add(completeBtn);
        buttonPanel.add(powerBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void populateRequestTable(DefaultTableModel model) {
        model.setRowCount(0);
        if (organization != null) {
            for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                if (request instanceof MaintenanceRequest) {
                    MaintenanceRequest mr = (MaintenanceRequest) request;
                    model.addRow(new Object[] { mr.getSender() != null ? mr.getSender().getUsername() : "Unknown",
                            mr.getStatus(),
                            mr.getMessage(),
                            mr.getBoardId(),
                            mr });
                }
            }
        }
    }

    private Billboard findBillboard(int id) {
        if (skyViewEnterprise != null) {
            for (Billboard b : skyViewEnterprise.getBillboardDirectory().getBillboards()) {
                if (b.getBoardId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    private Enterprise findPowerUtilityEnterprise() {
        for (Network n : business.getNetworkList()) {
            for (Enterprise e : n.getEnterpriseDirectory().getEnterpriseList()) {
                if (e instanceof PowerUtilityEnterprise) {
                    return e;
                }
            }
        }
        return null;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }
}
