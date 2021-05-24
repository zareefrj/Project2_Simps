package Project;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {
    private GraphMap g;
    private ArrayList<Customer> list;
    private Stack<Customer> stack=new Stack<>();
    public int maxCapacity, parcelSent=0,vehicleCount=1;
    public double TourCost = 0;
    private ArrayList<Customer> route = new ArrayList<>();


    public DFS(GraphMap g, ArrayList<Customer> list, int maxCapacity) {
        this.g = g;
        this.list = list;
        this.maxCapacity = maxCapacity;
    }

    public void DFSTraversal(){
        System.out.println("------------DFS-------------");

    while (allVisited()){
        Customer current, prev;
        current= list.get(0); //source
        current.visited=false;
        stack.push(current);

        while(!stack.isEmpty()){
            current= stack.peek();
            if(parcelSent+current.demands<=maxCapacity && !current.visited){
                parcelSent+=current.demands;
                current.visited=true;
                route.add(current);
                prev= stack.pop();

                for(int i=0;i< list.size();i++)
                    if(prev.id!=i)
                        stack.push(list.get(i));
            }
            else stack.pop();
        }
        printTour();
        route.clear();
        vehicleCount++;
        parcelSent = 0;
    }
        System.out.println("Tour Cost: "+TourCost);
    }

    //to return true if there's at least one customer that hasn't yet be visited
    private boolean allVisited(){
        for(int i = 0; i < list.size() ; i++){
            if(list.get(i).visited == false){
                return true;
            }
        }
        return false;
    }

    private void printTour(){
        double cost=0;
        int i,j;
        System.out.println("Vehicle : " + vehicleCount);
        System.out.println("Capacity : " + parcelSent);

        for(Customer no : route)
            System.out.print(no.id + " --> ");
        System.out.print(0); //back to depot
        System.out.println();
        route.add(list.get(0));
        for(i=0,j=1;i< route.size() && j< route.size();i++,j++)
            cost+=g.getEdge(route.get(j), route.get(i) );

        System.out.println("Cost : " + cost+"\n");
        TourCost+=cost;
    }
}
