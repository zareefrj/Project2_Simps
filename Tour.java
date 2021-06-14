package Project;

import java.util.ArrayList;

public class Tour {
    //This Tour class is solely created for the implementation of the MCTS Search

    ArrayList<ArrayList<Customer>> route;
    double TourCost;
    GraphMap g;

    public Tour(GraphMap g) {
        route=new ArrayList<>();
        TourCost=0;
        this.g=g;
    }

    public void setRoute(ArrayList<Customer> route) {
        this.route.add(route);
    }



    public double getTourCost() {
        if(route.isEmpty())
            return 0;
        else{
            for(int i=0;i< route.size();i++){
                for(int j=0;j<route.get(i).size();j++){
                    if (j + 1 == route.size())
                        TourCost += g.getEdge(route.get(i).get(0), route.get(i).get(j - 1));
                else
                        TourCost += g.getEdge(route.get(i).get(j), route.get(i).get(j - 1));
            }
        }
            return TourCost;
    }
    }

}
