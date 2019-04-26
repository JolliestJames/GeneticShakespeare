float mutationRate;
int totalPopulation = 150;
int generations = 0;
DNA[] population;
ArrayList<DNA> matingPool;
String target;
String bestPhrase;
 
void setup() {
  size(1200, 800);
  background(0);

  target = "written by someone who cares";
  mutationRate = 0.01;

  population = new DNA[totalPopulation];

  for (int i = 0; i < population.length; i++) {
    population[i] = new DNA();
  }
}

void draw() {
  textSize(15);
  background(0);
  fill(random(0, 255));

  text(String.format("generations: %s", generations), 50, 50);
  generations++;

  float populationFitness = 0;
  float bestFitness = 0;

  for (int i = 0; i < population.length; i++) {
    populationFitness += population[i].fitness();
    if (bestFitness < population[i].fitness()) {
      bestFitness = population[i].fitness();
      bestPhrase = population[i].phrase();
    }
  }

  text(String.format("best fitness: %s", bestFitness), 50, 150);
  text(String.format("best phrase: %s", bestPhrase), 50, 200);

  float averageFitness = populationFitness/population.length;

  text(String.format("average fitness: %s", averageFitness), 50, 100);

  ArrayList<DNA> matingPool = new ArrayList<DNA>();

  for (int i = 0; i < population.length; i++) {
    int n = int(population[i].fitness * 100);
    for (int j = 0; j < n; j++) {
      matingPool.add(population[i]);
    }
  }

  for (int i = 0; i < population.length; i++) {
    DNA parentA;
    DNA parentB;

    int a = int(random(matingPool.size()));

    parentA = matingPool.get(a);
    parentB = parentA;

    while (parentA.equals(parentB)) {
      int b = int(random(matingPool.size()));
      parentB = matingPool.get(b);
    }

    DNA child = parentA.crossover(parentB);
    child.mutate(mutationRate);

    population[i] = child;
  }

  text("All phrases:", 500, 25);

  for (int i = 0; i < population.length; i++) {
    if (population[i].phrase().equals(target)) {
      fill(255, 0, 0);
      text(population[i].phrase(), 50, 250);
      noLoop();
      break;
    } else {
      text(population[i].phrase(), 500, i*25+50);
    }
  }

}
