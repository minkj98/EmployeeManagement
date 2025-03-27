package buttonCustom;
import javax.swing.*;
import java.awt.*;

public class roundButton extends JButton {
    public roundButton() {
        super();
        decorate();
    }
    public roundButton(String text) {
        super(text);
        decorate();
    }
    public roundButton(Action action) {
        super(action);
        decorate();
    }
    public roundButton(Icon icon) {
        super(icon);
        decorate();
    }
    public roundButton(String text, Icon icon) {
        super(text, icon);
        decorate();
    }
    protected void decorate() {
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color navyBlue = new Color(0, 51, 102); //배경색 결정
        Color white = new Color(255,255,255); //글자색 결정
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            graphics.setColor(navyBlue.darker());
        }
        else if (getModel().isRollover()) {
            graphics.setColor(navyBlue.brighter());
        }
        else {
            graphics.setColor(navyBlue);
        }
        graphics.fillRoundRect(0, 0, width, height, 10, 10);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
        graphics.setColor(white);
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);
        graphics.dispose();
        super.paintComponent(g);
    }
}