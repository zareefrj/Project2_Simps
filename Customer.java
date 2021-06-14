package Project;

import java.util.ArrayList;
import java.util.List;

public class Customer <T extends Comparable<T>, N extends Comparable<N>>{

    //CUSTOMER CLASS WILL REPRESENT THE NODES FOR THE GRAPH //
    double demands,id,x,y;
    public boolean visited,checked;
//each customer ade demands, id masing2 & coordinates lokasi

    public Customer(double x, double y, double demands, double id) {  //constructor
        this.demands = demands;
        this.x= x;
        this.y= y;
        this.id=id;
        visited = false;
        checked=false;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {  //for printing all the details of each customer
        return "Customer{" +
                "demands=" + demands +
                ", id=" + id +
                ", location= (" + this.x +", "+this.y+") "+
                '}';
    }
}
