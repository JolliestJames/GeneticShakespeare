class DNA {
  float fitness;
  char[] genes;

  DNA() {
    genes = new char[target.length()];

    for (int i = 0; i < genes.length; i++) {
      genes[i] = (char) random(32,128);
    }
  }

  float fitness() {
    int score = 0;

    for (int i = 0; i < genes.length; i++) {
      if (genes[i] == target.charAt(i)) {
        score++;
      }
    }

    fitness = float(score)/target.length();

    return fitness;
  }

  DNA crossover(DNA partner) {
    DNA child = new DNA();

    for (int i = 0; i < genes.length; i++) {
      int coinToss = int(random(2));
      if (coinToss == 1) {
        child.genes[i] = genes[i];
      } else {
        child.genes[i] = partner.genes[i];
      }
    }

    return child;
  }

  void mutate(float mutationRate) {
    for (int i = 0; i < genes.length; i++) {
      if (random(1) < mutationRate) {
        genes[i] = (char) random(32,128);
      }
    }
  }

  String phrase() {
    return new String(genes);
  }
}
