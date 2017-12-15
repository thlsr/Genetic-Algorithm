import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.*;

public class MyApplet extends JApplet implements ActionListener {
  static int app_width = 800;
  static int app_height = 640;
  
  private JLabel mutationLabel;
  private JLabel populationLabel;

  DecimalFormat numberFormat = new DecimalFormat("#0.00");

  double mutationRate = 0.01;
  int DronesNum = 2000;
  Populacao Populacao = new Populacao(mutationRate, DronesNum);
  JButton start_button;
  JButton plus_mutation, minus_mutation;

  public void init() {
    setSize(app_width, app_height);
    start_button = new JButton("START");
    start_button.setActionCommand("START");
    start_button.addActionListener(this);

    plus_mutation = new JButton("+ Mutation");
    plus_mutation.setActionCommand("MUTATION+");
    plus_mutation.addActionListener(this);
    minus_mutation = new JButton("- Mutation");
    minus_mutation.setActionCommand("MUTATION-");
    minus_mutation.addActionListener(this);

    mutationLabel = new JLabel("Mutation rate = " + mutationRate);

    setLayout(new FlowLayout());
    add(Populacao);
    add(start_button);
    
    add(plus_mutation);
    add(minus_mutation);

    add(mutationLabel);
    
  }

  public void actionPerformed(ActionEvent e){
    if("START".equals(e.getActionCommand()))
    {
      Populacao.draw = !Populacao.draw;
      if(Populacao.draw) {
        Populacao.repaint();
        start_button.setText("PAUSE");
      }
      else {
        start_button.setText("START");
      }
    }

    if("MUTATION+".equals(e.getActionCommand()))
    {
      if(mutationRate < 1.0)
      {
        mutationRate += 0.01;
        Populacao.mutationRate +=0.01;
        mutationLabel.setText("Mutation rate = " + numberFormat.format(Populacao.mutationRate));
      }
    }
    if("MUTATION-".equals(e.getActionCommand()))
    {
      if(mutationRate > 0.00)
      {
        mutationRate -= 0.01;
        Populacao.mutationRate -=0.01;
        mutationLabel.setText("Mutation rate = " + numberFormat.format(Populacao.mutationRate));
      }
    } 
  }
}

/*<applet code=MyApplet.java width=800 height=640></applet>*/
