import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Populacao extends JPanel implements MouseListener {
  boolean draw;
  static int size = 600;
  public static int circle_ray = 50;

  private double genBest = 0;
  private double bestOfALL = 0;//////////////////
  private double genAvg = 0;
  private double genWorst = 0;

  public double mutationRate;
  Drone[] Populacao;
  ArrayList<Drone> matingPool;
  int generations, cycleNum;
  static final int lifetime = 750;

  static Vetor2d alvo;
  static Vetor2d obstacle;

  public Populacao(double m, int num) {
    draw = false;
    alvo = new Vetor2d(size/2, 12);
    obstacle = new Vetor2d(size/2, 200+100);

    mutationRate = m;
    Populacao = new Drone[num];
    matingPool = new ArrayList<Drone>();
    generations = 0;

    for(int i = 0; i < Populacao.length; i++) {
      //gera aleatoriamente um local para cada da populacao
      Vetor2d local = new Vetor2d(size / 2, size - 20);
      Populacao[i] = new Drone(local, new DNA());
    }

    setBorder(BorderFactory.createLineBorder(Color.YELLOW));
    setBackground(Color.BLACK);
    addMouseListener(this);
  }

  public void mousePressed(MouseEvent e) {
    if(SwingUtilities.isLeftMouseButton(e))
    {
      alvo.set(e.getX(), e.getY()); //setta o alvo no lugar clicado no jpanel
    }
    if(SwingUtilities.isRightMouseButton(e))
    {
      obstacle.set(e.getX(), e.getY());
    }
  }
  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseDragged(MouseEvent e) {}

  public Dimension getPreferredSize() {
    return new Dimension(size, size);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    g.drawString("Generation: " + getGenerations(), 10, 18);
    g.drawString("Cycles to next gen: " + (lifetime - cycleNum), 10, 36);
    g.drawString("Previous generation best fitness: " + genBest, 10, 54);
    g.drawString("Previous generation average fitness: " + genAvg, 10, 72);
    g.drawString("Previous generation worst fitness: " + genWorst, 10, 90);
    g.drawString("Best Of All fitness: " + bestOfALL, 10, 108);

    g.setColor(Color.BLUE);
    g.fillRect((int)alvo.x, (int)alvo.y, 20, 20);
    g.setColor(Color.cyan);
    g.fillOval((int)obstacle.x, (int)obstacle.y, circle_ray, circle_ray);
    g.setColor(Color.YELLOW);
    for(int i = 0; i < Populacao.length; i++) {
      g.fillOval((int)Populacao[i].local.x, (int)Populacao[i].local.y, 10, 10);
    }
    if(draw) {
      if(cycleNum < lifetime) {
        live();
        cycleNum++;
      }
      else {
        cycleNum = 0;
        fitness();
        selection();
        reproduction();
      }
    }
    try {
      Thread.sleep(10);
    } catch(InterruptedException t) {

    }
    repaint();
  }

  public void live() {
    for(int i = 0; i < Populacao.length; i++) {
      Populacao[i].run();
    }
  }

  public void fitness() {
    for(int i = 0; i < Populacao.length; i++) {
      Populacao[i].fitness();
    }
  }

  public void selection() {
    matingPool.clear();

    double maxFitness = getMaxFitness();
    getAvgFitness();
    getLowFitness();

    //mapeia o fitness de 0 a 1 para que seja calculado em porcentagem, cria uma roleta russa
    for(int i = 0; i < Populacao.length; i++) {
      double fitnessNormal = Vetor2d.map(Populacao[i].getFitness(), 0, maxFitness, 0, 1);
      int n = (int) (fitnessNormal * 100);
      for(int j = 0; j < n; j++) {
        matingPool.add(Populacao[i]);
      }
    }
  }

  public void reproduction() {
    for(int i = 0; i < Populacao.length; i++) {
      int m = (int) (Math.random() * matingPool.size());
      int d = (int) (Math.random() * matingPool.size());

      while(d == m) {
        d = (int) (Math.random() * matingPool.size());
      }

      Drone mom = matingPool.get(m);
      Drone dad = matingPool.get(d);

      DNA momgenes = mom.getDNA();
      DNA dadgenes = dad.getDNA();

      DNA child =  momgenes.cruzamento(dadgenes);

      child.mutate(mutationRate);

      Vetor2d local = new Vetor2d(size / 2, size - 20);
      Populacao[i] = new Drone(local, child);
    }
    generations++;
  }

  public int getGenerations() {
    return generations;
  }

  public double getMaxFitness() {
    double record = 0;
    for(int i = 0; i < Populacao.length; i++) {
      if(Populacao[i].getFitness() > record) {
        record = Populacao[i].getFitness();
      }
    }
    genBest = record;

    for (int i = 0; i < Populacao.length; i++) {///////////////////////
      if (bestOfALL < genBest) {
        bestOfALL = genBest;
      }
    }
    System.out.println(getGenerations() + " " + genWorst + " " + genAvg + " " + record + " " + bestOfALL);

    return bestOfALL;
  }

  /*public double getHighFitness(){
    for(int i = 0; i < Populacao.length; i++) {
      if(Populacao[i].getFitness() > record) {
        record = Populacao[i].getFitness();
      }
    }
    genBest = record;

  }*/

  public void getAvgFitness()
  {
    double avg = 0;
    double sum = 0;
    for(int i = 0; i < Populacao.length; i++)
    {
      sum += Populacao[i].getFitness();
    }

    avg = (sum / Populacao.length);
    genAvg = avg;
  }

  public void getLowFitness() {
    double low = genBest;
    for(int i = 0; i < Populacao.length; i++) {
      if(Populacao[i].getFitness() < low) {
        low = Populacao[i].getFitness();
      }
    }
    genWorst = low;
  }

}