import javax.swing.*;
import java.awt.*;

public class NFButton extends JButton {
    public NFButton(String string){
        super(string);
        setFocusPainted(false);
        setFocusable(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    }
    public void setFontSize(int size){
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size));
    }
}