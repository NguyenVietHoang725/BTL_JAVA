package com.battleship.view;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

public class CardPanel extends JPanel {
    private CardLayout cardLayout;

    public CardPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    public void showScreen(String name) {
        System.out.println("CardPanel: showScreen " + name);
        cardLayout.show(this, name);
    }

    public void setPanel(String name, Component panel) {
        // Remove panel cũ nếu đã tồn tại (theo name)
        for (Component comp : getComponents()) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                System.out.println("CardPanel: remove old panel with name = " + name);
                remove(comp);
                break;
            }
        }
        panel.setName(name); // Đặt name cho panel
        this.add(panel, name);
        System.out.println("CardPanel: add panel with name = " + name);
        revalidate();
        repaint();
    }
}