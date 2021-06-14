package Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
///*input file ade byk kat github, link kat pdf soalan projek
        File file = new File("C:\\Users\\User\\Documents\\YUUTUBE\\Project2_Simps\\Project\\input2.txt");// use absolute path
        Scanner scanner = new Scanner(file);
        ArrayList<Customer> list = new ArrayList<>(); //list ni utk store smua info dlm txt file tu dulu

        int totalCustomers = scanner.nextInt() - 1, maxCapacity = scanner.nextInt(), id = 0;

        while (scanner.hasNextLine()) { //scan input file
            list.add(new Customer(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), id)); //smua customer obj is stored in the list
            id++; //id x termasuk dlm input file, so letak manually
        }

        GraphMap g = new GraphMap(totalCustomers + 1); //create graph

        for (int i = 0; i < list.size(); i++)
            for (int j = 0; j < list.size(); j++) {
                if (i != j) //yg ni sbb the customer is not going to itself, so cost dia auto kosong
                    g.makeEdge(list.get(j), list.get(i));  //buat adjacency matrix, (to,from) guna makeEdge method at GraphMap class
            }

        System.out.println("\nThe total capacity of each vehicle: " + maxCapacity + "\nTotal Customers: " + totalCustomers + "\n");

        //printing all the list of customers
        for (Object c : list.toArray())
            System.out.println(c.toString());

//printing the adjacency matrix
        System.out.println("\nThe adjacency matrix for the given graph is: ");
        for (int i = 0; i <= totalCustomers; i++)
            System.out.printf("%9s", i);
        System.out.println();

        for (int i = 0; i <= totalCustomers; i++) {
            System.out.print(i + " ");
            for (int j = 0; j <= totalCustomers; j++)
                System.out.printf("%9s", Math.round(g.getEdge(list.get(i), list.get(j))));
            System.out.println();
        }

        /*//greedy search
        Search search=new Search(g,list,maxCapacity);
        search.greedySearch();
        printTour(search.vehicles,search.TourCost);

        //DFs
        long start = System.currentTimeMillis();
        long end = start + 3*1000; //1s*1000ms/s
        Double max=Double.MAX_VALUE;
        ArrayList<Vehicle> temp=new ArrayList<>();
        while ( System.currentTimeMillis() < end ) {
            System.out.print("Elapsed Time: "+(System.currentTimeMillis()-start+1)/1000+"s\r");

                Search dfs = new Search(g, list, maxCapacity);
                dfs.DFSTraversal();

           if(dfs.TourCost<max){
                max=dfs.TourCost;

                if(!temp.isEmpty())
                    temp.clear();

                temp = (ArrayList<Vehicle>) dfs.vehicles.clone();
            }
        }
        System.out.println("\n------------DFS--------------BASIC SIMULATION------------");
        printTour(temp, max);*/

        MCTS mcts=new MCTS(g,list,maxCapacity);
        int level=3,iterations=100;
        System.out.println(mcts.MonteCarloSearch(level,iterations));
        
        //A* Search
        Search search1=new Search(g,list,maxCapacity);
        search1.aStarSearch();
        printTour(search1.vehicles,search1.TourCost);
        }




    public static void printTour(ArrayList<Vehicle> temp, double Tcost){
        System.out.println("Tour: "+Tcost);

        for(int i=0;i<temp.size();i++){
            System.out.println("Vehicle "+(i+1));
            temp.get(i).getRoute();
            System.out.println(temp.get(i).toString());
            }
        }
}
