package ui.BrandMarketingRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.Billboard.Billboard;
import Business.Billboard.Billboard.BookingRecord;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BrandMarketingWorkAreaJPanel extends JPanel {

    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Enterprise enterprise;
    private EcoSystem business;
    private JTable billboardTable;
    private DefaultTableModel billboardModel;
    private JTable campaignTable;
    private DefaultTableModel campaignModel;

    // Professional color scheme
    private static final Color HEADER_BG = new Color(211, 84, 0); // Burnt Orange
    private static final Color BUTTON_BG = new Color(230, 126, 34); // Carrot Orange
    private static final Color WAITLIST_BG = new Color(41, 128, 185); // Blue
    private static final Color PANEL_BG = new Color(236, 240, 241);

    public BrandMarketingWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
            Organization organization, Enterprise enterprise,
            EcoSystem business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;

        initComponents();
        populateAvailableBillboards();
        populateMyCampaigns();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(PANEL_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));

        JLabel titleLabel = new JLabel("Brand Marketing Manager");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        headerPanel.add(Box.createHorizontalGlue());

        JLabel welcomeLabel = new JLabel("Welcome, " + account.getUsername());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab("Available Billboards & Waitlist", createBillboardPanel());
        tabbedPane.addTab("My Active Campaigns", createCampaignPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(PANEL_BG);
        JButton refreshBtn = createStyledButton("🔄 Refresh", new Color(149, 165, 166));
        refreshBtn.addActionListener(e -> {
            populateAvailableBillboards();
            populateMyCampaigns();
        });
        footer.add(refreshBtn);
        add(footer, BorderLayout.SOUTH);
    }

    private JButton requestBtn;
    private JButton waitlistBtn;

    private JPanel createBillboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lbl = new JLabel("Browse High-Traffic Billboards");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(lbl, BorderLayout.NORTH);

        String[] cols = { "Board ID", "Location", "Type", "Status", "Price/Day", "Object" };
        billboardModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        billboardTable = new JTable(billboardModel);
        styleTable(billboardTable);
        billboardTable.removeColumn(billboardTable.getColumnModel().getColumn(5));

        // Selection listener to enable/disable buttons
        billboardTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonState();
            }
        });

        panel.add(new JScrollPane(billboardTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(Color.WHITE);

        requestBtn = createStyledButton("Request Campaign", BUTTON_BG); // Orange
        requestBtn.setEnabled(false);
        requestBtn.addActionListener(e -> handleRequestBooking());
        btnPanel.add(requestBtn);

        waitlistBtn = createStyledButton("Join Waitlist", WAITLIST_BG); // Blue
        waitlistBtn.setEnabled(false);
        waitlistBtn.addActionListener(e -> handleJoinWaitlist());
        btnPanel.add(waitlistBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void updateButtonState() {
        int selectedRow = billboardTable.getSelectedRow();
        if (selectedRow >= 0) {
            Billboard b = (Billboard) billboardModel.getValueAt(billboardTable.convertRowIndexToModel(selectedRow), 5);
            if (b.getStatus() == Business.Billboard.BillboardStatus.AVAILABLE) {
                requestBtn.setEnabled(true);
                waitlistBtn.setEnabled(false);
            } else {
                requestBtn.setEnabled(false);
                waitlistBtn.setEnabled(true);
            }
        } else {
            requestBtn.setEnabled(false);
            waitlistBtn.setEnabled(false);
        }
    }

    private JPanel createCampaignPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lbl = new JLabel("Track Your Campaign Performance");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(lbl, BorderLayout.NORTH);

        String[] cols = { "Campaign Name", "Board Location/ID", "Start Date", "End Date", "Status" };
        campaignModel = new DefaultTableModel(cols, 0);
        campaignTable = new JTable(campaignModel);
        styleTable(campaignTable);

        panel.add(new JScrollPane(campaignTable), BorderLayout.CENTER);

        return panel;
    }

    // Logic
    private void populateAvailableBillboards() {
        billboardModel.setRowCount(0);
        for (Network network : business.getNetworkList()) {
            for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (ent instanceof Business.Enterprise.SkyViewBillboardEnterprise) {
                    Business.Enterprise.SkyViewBillboardEnterprise skyView = (Business.Enterprise.SkyViewBillboardEnterprise) ent;
                    if (skyView.getBillboardDirectory() != null) {
                        for (Billboard b : skyView.getBillboardDirectory().getBillboards()) {
                            billboardModel.addRow(new Object[] {
                                    b.getBoardId(),
                                    b.getLocation(),
                                    b.getType(),
                                    b.getStatus(),
                                    "$" + b.getPricePerDay(),
                                    b
                            });
                        }
                    }
                }
            }
        }
    }

    private void populateMyCampaigns() {
        campaignModel.setRowCount(0);

        // 1. Pending/Requested Campaigns from WorkQueue
        for (Business.WorkQueue.WorkRequest request : account.getWorkQueue().getWorkRequestList()) {
            if (request instanceof Business.WorkQueue.CampaignBookingRequest) {
                Business.WorkQueue.CampaignBookingRequest campReq = (Business.WorkQueue.CampaignBookingRequest) request;
                campaignModel.addRow(new Object[] {
                        campReq.getCampaignName(),
                        "Board " + campReq.getBoardId(), // Can't easily resolve location from ID without search,
                                                         // keeping simple
                        campReq.getStartDate(),
                        campReq.getEndDate(),
                        campReq.getStatus()
                });
            }
        }

        // 2. Active/Approved Bookings from Billboard Booking Records
        String myName = account.getUsername();

        for (Network network : business.getNetworkList()) {
            for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (ent instanceof Business.Enterprise.SkyViewBillboardEnterprise) {
                    Business.Enterprise.SkyViewBillboardEnterprise skyView = (Business.Enterprise.SkyViewBillboardEnterprise) ent;
                    if (skyView.getBillboardDirectory() != null) {
                        for (Billboard b : skyView.getBillboardDirectory().getBillboards()) {
                            for (BookingRecord record : b.getBookingHistory()) {
                                boolean match = record.getClientName().equalsIgnoreCase(account.getUsername());
                                if (!match && account.getEmployee() != null) {
                                    match = record.getClientName().equalsIgnoreCase(account.getEmployee().getName());
                                }

                                if (match) {
                                    campaignModel.addRow(new Object[] {
                                            "Active: " + b.getLocation(), // Using Location as name context
                                            "Board " + b.getBoardId(),
                                            record.getStartDate(),
                                            record.getEndDate(),
                                            "APPROVED / RUNNING"
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleJoinWaitlist() {
        int selectedRow = billboardTable.getSelectedRow();
        if (selectedRow < 0)
            return;

        Billboard b = (Billboard) billboardModel.getValueAt(billboardTable.convertRowIndexToModel(selectedRow), 5);
        // Button state logic ensures we only get here if status != AVAILABLE (or manual
        // check below)

        String msg = JOptionPane.showInputDialog(this, "Enter waitlist message (e.g. Desired dates):");
        if (msg != null && !msg.trim().isEmpty()) {
            b.joinWaitlist(account, msg);
            JOptionPane.showMessageDialog(this, "You have been added to the Waitlist for this billboard!");
        }
    }

    private void handleRequestBooking() {
        int selectedRow = billboardTable.getSelectedRow();
        if (selectedRow < 0)
            return;

        Billboard b = (Billboard) billboardModel.getValueAt(billboardTable.convertRowIndexToModel(selectedRow), 5);

        // Use a JDialog or just InputDialogs for simplicity as per current code style
        JTextField campaignNameField = new JTextField();
        JTextField budgetField = new JTextField();
        JTextField startDateField = new JTextField("2025-01-01"); // Default/Placeholder
        JTextField endDateField = new JTextField("2025-01-31"); // Default/Placeholder
        JTextArea creativeField = new JTextArea(5, 20);

        // Use 2 columns: Label | Field
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Campaign Name:"));
        panel.add(campaignNameField);
        panel.add(new JLabel("Budget ($):"));
        panel.add(budgetField);
        panel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (yyyy-MM-dd):"));
        panel.add(endDateField);
        panel.add(new JLabel("Creative Content / Message:"));
        panel.add(new JScrollPane(creativeField));

        int result = JOptionPane.showConfirmDialog(null, panel, "Booking Request - " + b.getLocation(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String campaign = campaignNameField.getText();
            String budgetStr = budgetField.getText();
            String startStr = startDateField.getText();
            String endStr = endDateField.getText();
            String content = creativeField.getText();

            if (campaign.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Campaign Name is required.");
                return;
            }

            double budget = 0;
            try {
                budget = Double.parseDouble(budgetStr);
            } catch (NumberFormatException e) {
                // optional: warn
            }

            // BUDGET VALIDATION
            if (budget < b.getPricePerDay()) {
                JOptionPane.showMessageDialog(this,
                        "Error: Budget is too low! Billboard cost is $" + b.getPricePerDay() + "/day.",
                        "Low Budget", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.util.Date startDate = new java.util.Date();
            java.util.Date endDate = new java.util.Date();

            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse(startStr);
                endDate = sdf.parse(endStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid Date Format. Please use yyyy-MM-dd.");
                return;
            }

            Business.WorkQueue.CampaignBookingRequest req = new Business.WorkQueue.CampaignBookingRequest();
            req.setSender(account);
            req.setClientName(account.getUsername());
            req.setCampaignName(campaign);
            req.setBoardId(b.getBoardId());
            req.setBudget(budget);
            req.setCreativeContent(content);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setStatus("Sent to Agency");
            req.setMessage("Please book this for me. Content: " + content);

            // Find Agency Org to send request to
            boolean sent = false;
            // Assuming Brand and Agency might be in same Enterprise, OR we search network.
            // For now, search in current Enterprise.
            if (enterprise.getOrganizationDirectory() != null) {
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    if (org instanceof Business.Organization.AgencyClientServicesOrganization) {
                        org.getWorkQueue().getWorkRequestList().add(req);
                        account.getWorkQueue().getWorkRequestList().add(req);
                        sent = true;
                        break;
                    }
                }
            }

            if (sent) {
                JOptionPane.showMessageDialog(this, "Booking Request sent successfully to Agency!");
                populateMyCampaigns(); // Refresh campaigns tab
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: Could not find Agency Services Organization to receive request.");
            }
        }
    }

    // UI Helpers
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(230, 126, 34));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(211, 84, 0));
        table.getTableHeader().setForeground(Color.WHITE);
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
