package ui.AdministrativeRole;

import Business.Enterprise.Enterprise;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author raunak
 */
public class AdminWorkAreaJPanel extends javax.swing.JPanel {

    JPanel userProcessContainer;
    Enterprise enterprise;

    // Professional color scheme
    private static final Color ADMIN_PRIMARY = new Color(142, 68, 173); // Purple for admin
    private static final Color BUTTON_BG = new Color(155, 89, 182);

    /**
     * Creates new form AdminWorkAreaJPanel
     */
    public AdminWorkAreaJPanel(JPanel userProcessContainer, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.enterprise = enterprise;

        // Initialize components manually to control layout completely
        initComponentsCustom();
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

        JLabel titleLabel = new JLabel("Enterprise Administration - " + enterprise.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        add(headerPanel, java.awt.BorderLayout.NORTH);

        // ============================================================
        // 2. Main Content Panel (GridBagLayout for centering)
        // ============================================================
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        // --- Dashboard for SkyViewBillboardEnterprise ---
        if (enterprise instanceof Business.Enterprise.SkyViewBillboardEnterprise) {
            Business.Enterprise.SkyViewBillboardEnterprise skyEnt = (Business.Enterprise.SkyViewBillboardEnterprise) enterprise;
            if (skyEnt.getBillboardDirectory() != null) {
                AnalyticsDashboardJPanel analyticsPanel = new AnalyticsDashboardJPanel(skyEnt.getBillboardDirectory());
                gbc.gridy = 0;
                gbc.insets = new java.awt.Insets(0, 0, 30, 0); // Bottom margin
                contentPanel.add(analyticsPanel, gbc);
                gbc.gridy++; // Increment gridy for buttons
            }
        }

        // --- Buttons Container ---
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBackground(Color.WHITE);

        // Define Buttons
        JButton manageOrgBtn = new JButton("Manage Organization");
        JButton manageEmpBtn = new JButton("Manage Employee");
        JButton manageUserBtn = new JButton("Manage User");
        JButton analyticsBtn = new JButton("Enterprise Statistics");

        styleButton(manageOrgBtn, BUTTON_BG);
        styleButton(manageEmpBtn, BUTTON_BG);
        styleButton(manageUserBtn, BUTTON_BG);
        styleButton(analyticsBtn, BUTTON_BG);

        // Add Listeners
        manageOrgBtn.addActionListener(evt -> manageOrganizationJButtonActionPerformed(evt));
        manageEmpBtn.addActionListener(evt -> manageEmployeeJButtonActionPerformed(evt));
        manageUserBtn.addActionListener(evt -> userJButtonActionPerformed(evt));
        analyticsBtn.addActionListener(evt -> analyticsJButtonActionPerformed(evt));

        // Add to Container
        buttonContainer.add(manageOrgBtn);
        buttonContainer.add(Box.createVerticalStrut(20));
        buttonContainer.add(manageEmpBtn);
        buttonContainer.add(Box.createVerticalStrut(20));
        buttonContainer.add(manageUserBtn);
        buttonContainer.add(Box.createVerticalStrut(20));
        buttonContainer.add(analyticsBtn);

        gbc.insets = new java.awt.Insets(0, 0, 0, 0); // Reset insets
        contentPanel.add(buttonContainer, gbc);

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new java.awt.Dimension(250, 50));
        button.setMaximumSize(new java.awt.Dimension(250, 50)); // Enforce size in BoxLayout
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        button.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    }

    private void analyticsJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        StatisticsJPanel statsPanel = new StatisticsJPanel(userProcessContainer, enterprise);
        userProcessContainer.add("StatisticsJPanel", statsPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void userJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ManageUserAccountJPanel muajp = new ManageUserAccountJPanel(userProcessContainer, enterprise);
        userProcessContainer.add("ManageUserAccountJPanel", muajp);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void manageEmployeeJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ManageEmployeeJPanel manageEmployeeJPanel = new ManageEmployeeJPanel(userProcessContainer,
                enterprise.getOrganizationDirectory());
        userProcessContainer.add("manageEmployeeJPanel", manageEmployeeJPanel);

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void manageOrganizationJButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ManageOrganizationJPanel manageOrganizationJPanel = new ManageOrganizationJPanel(userProcessContainer,
                enterprise.getOrganizationDirectory(), enterprise);
        userProcessContainer.add("manageOrganizationJPanel", manageOrganizationJPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
