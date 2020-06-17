package ServerSocketHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * this class created for showing on GUI
 *
 * @author Amir Naziri
 */
public class ViewServer extends JFrame
{

    private JTextArea textArea;
    /**
     * creates new ViewServer
     */
    public ViewServer ()
    {
        setBackground (Color.WHITE);
        setSize (600,600);
        JPanel panel = new JPanel (new BorderLayout ());
        textArea = new JTextArea ();
        textArea.setWrapStyleWord (true);
        textArea.setLineWrap (true);
        textArea.setEditable (false);
        textArea.setFont (new Font ("arial",Font.BOLD,12));
        textArea.setBorder (new EmptyBorder (10,10,10,10));
        panel.add (new JScrollPane (textArea));
        setContentPane (panel);
        setDefaultCloseOperation (EXIT_ON_CLOSE);
    }

    /**
     *
     * @return textArea
     */
    public JTextArea getTextArea () {
        return textArea;
    }
}
