package Project;

import java.util.ArrayList;

public class Tour {
    GraphMap g;
    double cost,tourcost;
    ArrayList<Vehicle> vehicles;
    Vehicle currentVan;
    //ArrayList<Customer> routes;

    public Tour(GraphMap g) {
        this.g=g;
        this.cost = 0;
        this.tourcost = 0;
        this.vehicles = new ArrayList<>();
      //  this.routes = new ArrayList<>();
    }

    public void dispatchNewVehicle(){
        currentVan=new Vehicle();
        vehicles.add(currentVan);
        this.cost = 0;
    }

    public double getTourcost() {
        for(int i=0;i<vehicles.size();i++) {
            cost=0;
            for (int j = 1; j <vehicles.get(i).route.size(); j++) {
                if (j + 1 == vehicles.get(i).route.size())
                    cost += g.getEdge(vehicles.get(i).route.get(0), vehicles.get(i).route.get(j - 1));
                else
                    cost += g.getEdge(vehicles.get(i).route.get(j), vehicles.get(i).route.get(j - 1));
            }
            vehicles.get(i).setCost(cost);
            tourcost+=cost;
        }
        return tourcost;
    }

    @Override
    public String toString() {
        for(Vehicle v: vehicles)
            System.out.println(v.toString());

        return "Tour: "+getTourcost()+"\n";
    }
}
