package Project;

import java.util.ArrayList;

public class Vehicle {
    GraphMap g;
    double cost;
    int capacity;
    ArrayList<Customer> route;

    public Vehicle(GraphMap g,double cost, ArrayList<Customer> route, int capacity) {
        this.g=g;
        this.cost = cost;
        this.capacity=capacity;
        this.route = route;
    }

    public double getCost() {
        this.cost=0;

        for(int j=1;j< route.size();j++)
            if (j + 1 == route.size())
                cost += g.getEdge(route.get(0), route.get(j - 1));
            else
                cost += g.getEdge(route.get(j), route.get(j - 1));

        return cost;
    }


    public void getRoute() {
       for(int i=0;i< route.size();i++)
           if(i+1!= route.size())
           System.out.print(route.get(i).id+"--->");

           else
               System.out.print(route.get(i).id);
    }

    @Override
    public String toString() {
       return "\nCapacity: "+capacity+"\nCost: "+getCost()+"\n";
    }
}
