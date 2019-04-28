import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GeneticShakespeare extends PApplet {

float mutationRate;
int totalPopulation = 150;
int generations = 0;
DNA[] population;
ArrayList<DNA> matingPool;
String target;
String bestPhrase;
 
public void setup() {
  
  background(0);

  target = "written by someone who cares";
  mutationRate = 0.01f;

  population = new DNA[totalPopulation];

  for (int i = 0; i < population.length; i++) {
    population[i] = new DNA();
  }
}

public void draw() {
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
    int n = PApplet.parseInt(population[i].fitness * 100);
    for (int j = 0; j < n; j++) {
      matingPool.add(population[i]);
    }
  }

  for (int i = 0; i < population.length; i++) {
    DNA parentA;
    DNA parentB;

    int a = PApplet.parseInt(random(matingPool.size()));

    parentA = matingPool.get(a);
    parentB = parentA;

    while (parentA.equals(parentB)) {
      int b = PApplet.parseInt(random(matingPool.size()));
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
class DNA {
  float fitness;
  char[] genes;

  DNA() {
    genes = new char[target.length()];

    for (int i = 0; i < genes.length; i++) {
      genes[i] = (char) random(32,128);
    }
  }

  public float fitness() {
    int score = 0;

    for (int i = 0; i < genes.length; i++) {
      if (genes[i] == target.charAt(i)) {
        score++;
      }
    }

    fitness = PApplet.parseFloat(score)/target.length();

    return fitness;
  }

  public DNA crossover(DNA partner) {
    DNA child = new DNA();

    for (int i = 0; i < genes.length; i++) {
      int coinToss = PApplet.parseInt(random(2));
      if (coinToss == 1) {
        child.genes[i] = genes[i]; 
      } else {
        child.genes[i] = partner.genes[i];
      }
    }

    return child;
  }

  public void mutate(float mutationRate) {
    for (int i = 0; i < genes.length; i++) {
      if (random(1) < mutationRate) {
        genes[i] = (char) random(32,128);
      }
    }
  }

  public String phrase() {
    return new String(genes);
  }
}
  public void settings() {  size(1200, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GeneticShakespeare" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
