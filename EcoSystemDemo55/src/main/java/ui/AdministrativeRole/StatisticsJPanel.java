package ui.AdministrativeRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Agent
 */
public class StatisticsJPanel extends JPanel {

    private JPanel userProcessContainer;
    private Enterprise enterprise;

    public StatisticsJPanel(JPanel userProcessContainer, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.enterprise = enterprise;

        initComponentsCustom();
    }

    private void initComponentsCustom() {
        setLayout(new java.awt.BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 20));
        headerPanel.setBackground(Color.WHITE);

        JButton backBtn = new JButton("<< Back");
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });
        styleButton(backBtn);

        JLabel titleLabel = new JLabel("Enterprise Statistics & Analytics");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(142, 68, 173));

        headerPanel.add(backBtn);
        headerPanel.add(titleLabel);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        // Calculate Stats
        int totalOrgs = enterprise.getOrganizationDirectory().getOrganizationList().size();
        int totalEmployees = 0;
        int totalUsers = enterprise.getUserAccountDirectory().getUserAccountList().size(); // Enterprise level users

        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
            totalEmployees += org.getEmployeeDirectory().getEmployeeList().size();
            totalUsers += org.getUserAccountDirectory().getUserAccountList().size();
        }

        // Create Cards
        JPanel statsContainer = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 columns, auto rows
        statsContainer.setBackground(Color.WHITE);
        statsContainer.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        statsContainer.add(createCard("Total Organizations", totalOrgs, new Color(52, 152, 219)));
        statsContainer.add(createCard("Total Employees", totalEmployees, new Color(46, 204, 113)));
        statsContainer.add(createCard("Total User Accounts", totalUsers, new Color(230, 126, 34)));

        // If SkyView, add billboard stats
        if (enterprise instanceof Business.Enterprise.SkyViewBillboardEnterprise) {
            Business.Enterprise.SkyViewBillboardEnterprise skyEnt = (Business.Enterprise.SkyViewBillboardEnterprise) enterprise;
            if (skyEnt.getBillboardDirectory() != null) {
                statsContainer.add(createCard("Total Billboards", skyEnt.getBillboardDirectory().getTotalBillboards(),
                        new Color(155, 89, 182)));
                statsContainer.add(createCard("Booked Billboards",
                        skyEnt.getBillboardDirectory().getBookedBillboardsCount(), new Color(241, 196, 15)));
                statsContainer.add(createCard("Vacant Billboards",
                        skyEnt.getBillboardDirectory().getAvailableBillboardsCount(), new Color(231, 76, 60)));
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;

        contentPanel.add(statsContainer, gbc);

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }

    private JPanel createCard(String title, int count, Color accentColor) {
        JPanel card = new JPanel(new java.awt.BorderLayout());
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 8, 0, 0, accentColor),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        countLabel.setForeground(Color.DARK_GRAY);
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(countLabel, java.awt.BorderLayout.CENTER);
        card.add(titleLabel, java.awt.BorderLayout.SOUTH);

        return card;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        button.setPreferredSize(new java.awt.Dimension(100, 30));
    }

    private void backAction() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
