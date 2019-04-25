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
DNA[] population;
ArrayList<DNA> matingPool;
String target;
 
public void setup() {
  target = "it is not enough for code to work";
  mutationRate = 0.1f;

  population = new DNA[totalPopulation];

  for (int i = 0; i < population.length; i++) {
    population[i] = new DNA();
  }
}

public void draw() {
  for (int i = 0; i < population.length; i++) {
    population[i].fitness();
  }

  ArrayList<DNA> matingPool = new ArrayList<DNA>();

  for (int i = 0; i < population.length; i++) {
    int n = PApplet.parseInt(population[i].fitness * 100);
    for (int j = 0; j < n; j++) {
      matingPool.add(population[i]);
    }
  }

  for (int i = 0; i < population.length; i++) {
    int a = PApplet.parseInt(random(matingPool.size()));
    int b = PApplet.parseInt(random(matingPool.size()));
    DNA parentA = matingPool.get(a);
    DNA parentB = matingPool.get(b);
    DNA child = parentA.crossover(parentB);
    child.mutate(mutationRate);

    population[i] = child;
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

  public void fitness() {
    int score = 0;

    for (int i = 0; i < genes.length; i++) {
      if (genes[i] == target.charAt(i)) {
        score++;
      }
    }

    fitness = PApplet.parseFloat(score)/target.length();
  }

  public DNA crossover(DNA partner) {
    DNA child = new DNA();

    int midpoint = PApplet.parseInt(random(genes.length));

    for (int i = 0; i < genes.length; i++) {
      if (i > midpoint) child.genes[i] = genes[i];
      else child.genes[i] = partner.genes[i];
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GeneticShakespeare" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
