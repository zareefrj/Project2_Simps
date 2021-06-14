package Project;
public class GraphMap  <T extends Comparable<T>, N extends Comparable<N>>{
    private final int vertices; //no of customer+depot
    static double[][] adjacency_matrix;

    public GraphMap(int v) //constructor
    {
        vertices = v;
        adjacency_matrix = new double[vertices+1][vertices+1]; //for storing the edge values

    }

    public void makeEdge(Customer to, Customer from)
    {
        try
        {   //the edge is the Euclidean distance between 2 customers
            adjacency_matrix[(int) to.id][(int) from.id] =Math.sqrt(Math.pow((to.x-from.x),2)+Math.pow((to.y-from.y),2));
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }

    }

    public  double getEdge(Customer to, Customer from)
    {
        try
        {
            //for getting the edge value of 2 nodes
            return adjacency_matrix[(int) to.id][(int) from.id];
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }
        return -1;
    }
}
