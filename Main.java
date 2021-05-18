package Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
//input file ade byk kat github, link kat pdf soalan projek
        File file = new File("C:\\Users\\User\\Documents\\Project2\\src\\Project\\input1.txt");// use absolute path
        Scanner scanner = new Scanner(file);
        ArrayList<Customer> list = new ArrayList<>(); //list ni utk store smua info dlm txt file tu dulu

        int totalCustomers = scanner.nextInt() - 1,maxCapacity = scanner.nextInt(),id=0;

        while (scanner.hasNextLine()){ //scan input file
            list.add(new Customer(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), id)); //smua customer obj is stored in the list
            id++; //id x termasuk dlm input file, so letak manually
        }

        GraphMap g=new GraphMap(totalCustomers); //create graph

        for(int i=0;i<list.size();i++)
            for(int j=0;j<list.size();j++){
                if(i!=j) //yg ni sbb the customer is not going to itself, so cost dia auto kosong
                    g.makeEdge(list.get(j), list.get(i));  //buat adjacency matrix, (to,from) guna makeEdge method at GraphMap class
            }

        //printing all the list of customers
        for(Object c: list.toArray())
            System.out.println(c.toString());

//printing the adjacency matrix
        System.out.println("The adjacency matrix for the given graph is: ");
        for (int i = 1; i <= totalCustomers; i++)
            System.out.printf("%9s",i);
        System.out.println();

        for (int i = 1; i <= totalCustomers; i++)
        {
            System.out.print(i + " ");
            for (int j = 1; j <= totalCustomers; j++)
                System.out.printf("%9s",g.getEdge(list.get(i), list.get(j)));
            System.out.println();
        }
        
        //Greed main program
        GreedySearch gs = new GreedySearch(g, list, maxCapacity);
        gs.performGreed();
    }
}
