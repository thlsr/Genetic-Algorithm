import java.util.Scanner;

public class Drone {
  Vetor2d local;
  Vetor2d velocidade;
  Vetor2d aceleracao;
  Vetor2d alvo;
  Vetor2d obstacle;

  double fitness;
  DNA dna;

  int genesNum = 0;

  boolean hitalvo = false;
  boolean hitObstacle = false;

  public Drone(Vetor2d loc, DNA dna_) {
    velocidade = new Vetor2d();
    aceleracao = new Vetor2d();
    local = loc.get();
    alvo = Populacao.alvo.get();
    obstacle = Populacao.obstacle.get();
    dna = dna_;
  }

  public void aplicarForca(Vetor2d forca) {
    aceleracao.add(forca);
  }

  public void update() {
    velocidade.add(aceleracao);
    local.add(velocidade);
    aceleracao.mult(0);
  }

  public void fitness() {
    //calcula a distancia entre o drone e o alvo, quanto menor a distancia, maior o fitness
    double dist = Vetor2d.dist(local.x, local.y, alvo.x, alvo.y);
    //System.out.println("distancia = " +  Vetor2d.dist(local.x, local.y, alvo.x, alvo.y));
    fitness = 1 / (dist);

    //System.out.println("dist = " + dist + "  " + "fitness = " + fitness);
    if(hitObstacle == true)
    {
      fitness *= 0.0000001;
    }
  }

  public void run() {
    checkAlvo();
    checkObstacle();
    if(!hitalvo && !hitObstacle) {
      aplicarForca(dna.genes[genesNum]); //aplicarForca escrito nesse pedaço de dna
      genesNum = (genesNum + 1) % dna.genes.length;
      update();
    }
  }

  public void checkAlvo() {
    double dist = Vetor2d.dist(local.x, local.y, alvo.x+2, alvo.y+2);
    if(dist < 12) {
      hitalvo = true;
      
    }
  }

  public void checkObstacle() {
    double dist = Vetor2d.dist(local.x, local.y, obstacle.x+(Populacao.circle_ray/2)-5, obstacle.y+(Populacao.circle_ray/2)-5);
    if(dist < 25) {
      hitObstacle = true;
    }
  }

  public double getFitness() {
    return fitness;
  }

  public DNA getDNA() {
    return dna;
  }
}
