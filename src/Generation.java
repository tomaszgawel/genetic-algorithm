import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

public class Generation {

    private Vector<String> parents=new Vector<String>();
    private Vector<Integer> parents_dec=new Vector<Integer>();
    private Vector<Double> parents_prob=new Vector<Double>();
    private Vector<String> new_parents = new Vector<String>();
    private int [] draw = new int[100];
    private final int size;
    private int counter;
    private int mutation;
    private int genSize;

    public Generation(){
        this.setCounter(0);
        Scanner scan=new Scanner(System.in);
        System.out.print("Enter quantity of parents (even number): ");
        Random rand=new Random();
        size=scan.nextInt();
        System.out.print("Enter size of gene: ");
        this.setGenSize(scan.nextInt());
        String characters = "01";
        for(int i=0;i<size;i++)
        {
            String temp=new String();
            for(int j=0;j<getGenSize();j++)
            {
                temp+=characters.charAt((int)(rand.nextDouble()*2));
            }
            parents.add(temp);
        }
    }

    private long binaryToDecimal(String str){
        return Long.parseLong(str,2);
    }

    public void Probability()
    {
        long sum=0;
        for(int i=0;i<size;i++){
            parents_dec.add((int)pow((double)binaryToDecimal(parents.elementAt(i)),2.0));
            sum+=parents_dec.elementAt(i);
        }

        for(int i=0;i<size;i++){
            parents_prob.add((double)parents_dec.elementAt(i)/(double)sum);
        }

        int lastIndex=0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<(int)floor(parents_prob.elementAt(i)*100);j++) {
                draw[lastIndex] = i;
                lastIndex++;
            }
        }
        if(lastIndex<100)
        {
            for(int i=lastIndex;i<100;i++)
            {
                draw[i] = parents_prob.size()-1;
            }
        }
    }

    public void drawing()
    {
        Random rand=new Random();
        for(int i=0;i<parents.size();i++)
        {
            new_parents.add(parents.elementAt(draw[rand.nextInt(99)]));
        }
    }

    private String cutString(String str1, String str2,int index)
    {
        return str1.substring(0,index)+str2.substring(index,getGenSize());
    }

    public void createNextGen()
    {
        StringBuilder temp;
        Random r=new Random();
        int index;
        int cutIndex;
        parents.clear();
        int i=0;
        while(i<size)
        {
            cutIndex=r.nextInt(this.getGenSize());

            if(this.getCounter()%this.getMutation()!=0){
                parents.add(cutString(new_parents.elementAt(i),new_parents.elementAt(i+1),cutIndex));
                parents.add(cutString(new_parents.elementAt(i+1),new_parents.elementAt(i),cutIndex));
                i+=2;
            }
            else{
                temp=new StringBuilder();
                index=r.nextInt(this.getGenSize());
                temp.append(cutString(new_parents.elementAt(i),new_parents.elementAt(i+1),cutIndex));

                if(temp.charAt(index)=='1')
                    temp.setCharAt(index,'0');
                else if(temp.charAt(index)=='0')
                    temp.setCharAt(index,'1');

                parents.add(temp.toString());

                temp=new StringBuilder();
                index=r.nextInt(this.getGenSize());
                temp.append(cutString(new_parents.elementAt(i+1),new_parents.elementAt(i),cutIndex));

                if(temp.charAt(index)=='1')
                    temp.setCharAt(index,'0');
                else if(temp.charAt(index)=='0')
                    temp.setCharAt(index,'1');

                parents.add(temp.toString());

                i+=2;
            }
        }
    }

    public void clear(){
        parents_dec.clear();
        parents_prob.clear();
        new_parents.clear();
        for(int i=0;i<100;i++){
            draw[i]=0;
        }
    }

    public void printGen(){
        for(int i=0;i<parents.size();i++)
        {
            System.out.println(parents.elementAt(i));
        }
    }

    boolean checkGen() {
        int p=0;
        for(int i=0;i<size;i++)
        {
            if(parents.elementAt(0).equals(parents.elementAt(i)))
                p++;
        }
        if(p==size)
            return true;
        else
            return false;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setMutation(int mutation) {
        this.mutation = mutation;
    }

    public int getMutation() {
        return mutation;
    }

    public int getGenSize() {
        return genSize;
    }

    public void setGenSize(int genSize) {
        this.genSize = genSize;
    }
}
