package Project;

import java.util.ArrayList;

public class Vehicle {
    double cost;
    int capacity;
    ArrayList<Customer> route;

    public Vehicle(double cost, ArrayList<Customer> route, int capacity) {
        this.cost = cost;
        this.capacity=capacity;
        this.route = route;
    }

    public Vehicle() {
        this.cost = 0;
        this.capacity=0;
        this.route =new ArrayList<>();
    }

    public void setRoute(ArrayList<Customer> route) {
        this.route= (ArrayList<Customer>) route.clone();
        route.clear();
    }

    public void setCost(double cost) {
        this.cost = cost;
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
       return "\nCapacity: "+capacity+"\nCost: "+cost+"\n";
    }
}
