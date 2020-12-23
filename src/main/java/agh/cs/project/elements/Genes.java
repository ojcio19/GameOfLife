package agh.cs.project.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genes {
    private final ArrayList<Integer> genes = new ArrayList<>();

    Genes(){
        for (int i = 0; i < 32; i++) {
            Random random = new Random();
            genes.add(random.nextInt(8));
        }
    }
    Genes(Genes mother,Genes father){
        for (int i = 0; i < 16; i++) {
            genes.add(mother.getRandomGen());
        }
        for (int i = 0; i < 8; i++) {
            genes.add(father.getRandomGen());
        }
        for (int i = 0; i < 32; i++) {
            Random random = new Random();
            genes.add(random.nextInt(8));
        }
    }

    private void checkExistingGenes(){
        Collections.sort(this.genes);

    }

    public int getRandomGen(){
        return genes.get(new Random().nextInt(32));
    }

    public Genes getGenes() {
        return this;
    }
}
