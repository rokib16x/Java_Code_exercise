import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.awt.*;
import javax.swing.*;
public class Counter2  {
    JFrame J;
    JTextField text;
    JButton count;
    JButton reset;
    main2(){
        J = new JFrame("Counter");
        J.setSize(500,200);

        JPanel P = new JPanel();
        JLabel Lebel = new JLabel("Counter");
        text = new JTextField("0",10);
        count = new JButton("Count");
        reset = new JButton("Reset");
        text.setEditable(true);
        P.add(Lebel);
        P.add(text);
        P.add(count);
        P.add(reset);
        J.setContentPane(P);

        J.setVisible(true);
        J.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        count.addActionListener(new ActionListener() {
            int sum;
            @Override
            public void actionPerformed(ActionEvent e) {
                sum = Integer.parseInt(text.getText());
                sum +=1;
                text.setText(String.valueOf(sum));
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("0");
            }
        });



    }
    public static void main(String[] args) {
        new main2();

    }
}
