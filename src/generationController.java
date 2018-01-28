import java.util.Scanner;

public class generationController{

    private Generation gen=new Generation();
    private Scanner scan=new Scanner(System.in);

    public generationController(){
        int i;

        System.out.print("Enter number of generations: ");
            int generations=scan.nextInt();

        System.out.print("Enter frequency of mutations: ");
            gen.setMutation(scan.nextInt());

        System.out.println("\nParents of first generation: ");
            gen.printGen();

        for(i=0;i<generations;i++)
        {
            gen.setCounter(i);
            gen.Probability();
            gen.drawing();
            gen.createNextGen();
            gen.clear();
        }

            System.out.println("\nPopulation after " + i + " generations:");
            gen.printGen();
    }
}
