public class DNA {
  public Vetor2d [] genes;

  double maxforce = 0.1;

  public DNA() {
    //DNA aleatório para a geração inicial
    genes = new Vetor2d[Populacao.lifetime];
    for(int i = 0; i < genes.length; i++) {
      double angle = Math.random() * 2 * Math.PI;
      //Escolha um vetor de ate 360 grays cuja direção o objeto se deslocará
      genes[i] = new Vetor2d(Math.cos(angle), Math.sin(angle));
      genes[i].mult(Math.random() * maxforce);
    }
  }

  public DNA(Vetor2d[] newgenes) {
    genes = newgenes;
  }

  public DNA cruzamento(DNA partner) {
    Vetor2d[] child = new Vetor2d[genes.length];
    //Aleatoriamente escolhe um ponto intermediario entre os pais
    int cruzamento = (int) (Math.random() * genes.length);
    //Pega genes de um pai até chegar no ponto intermediario e depois pega do outro
    for(int i = 0; i < genes.length; i++) {
      if(i > cruzamento) {
        child[i] = genes[i];
      }
      else {
        child[i] = partner.genes[i];
      }
    }
    //Retorna o DNA do novo filho
    DNA newgenes = new DNA(child);
    return newgenes;

    //Método 2
    //Nesse metodo, escolhe primeiro gene do pai, segundo da mae, terceiro do pai e assim vai
    /*for(int i = 0; i < genes.length; i++)
    {
      if(i % 2 == 0)
      {
        child[i] = genes[i];
      }
      else
      {
        child[i] = partner.genes[i];
      }
    }
    DNA newgenes = new DNA(child);
    return newgenes;*/
  }

  public void mutate(double m) { //m is mutationRate given to the Populacao object while instantiating
    for(int i = 0; i < genes.length; i++) {
      if(Math.random() < m) {
        double angle = Math.random() * 2 * Math.PI;
        genes[i] = new Vetor2d(Math.cos(angle), Math.sin(angle));
        genes[i].mult(Math.random() * maxforce);
      }
    }
  }
}
