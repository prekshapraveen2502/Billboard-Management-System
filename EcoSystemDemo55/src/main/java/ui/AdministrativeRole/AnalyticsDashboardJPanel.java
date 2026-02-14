package ui.AdministrativeRole;

import Business.Billboard.BillboardDirectory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */
public class AnalyticsDashboardJPanel extends JPanel {

    private BillboardDirectory billboardDirectory;

    // Colors
    private static final Color CARD_BG = new Color(245, 245, 245);
    private static final Color TOTAL_COLOR = new Color(41, 128, 185);
    private static final Color BOOKED_COLOR = new Color(46, 204, 113);
    private static final Color VACANT_COLOR = new Color(230, 126, 34);
    private static final Color MAINTENANCE_COLOR = new Color(231, 76, 60);

    public AnalyticsDashboardJPanel(BillboardDirectory billboardDirectory) {
        this.billboardDirectory = billboardDirectory;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(1, 4, 20, 0)); // 1 row, 4 cols, 20px gap
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create cards
        add(createCard("Total Boards", billboardDirectory.getTotalBillboards(), TOTAL_COLOR));
        add(createCard("Booked", billboardDirectory.getBookedBillboardsCount(), BOOKED_COLOR));
        add(createCard("Vacant", billboardDirectory.getAvailableBillboardsCount(), VACANT_COLOR));
        add(createCard("Under Maintenance", billboardDirectory.getMaintenanceBillboardsCount(), MAINTENANCE_COLOR));
    }

    private JPanel createCard(String title, int count, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(2, 1));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, accentColor)); // Left accent border

        JLabel countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        countLabel.setForeground(Color.DARK_GRAY);
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(countLabel);
        card.add(titleLabel);

        card.setPreferredSize(new Dimension(200, 100));

        return card;
    }

    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }
}
