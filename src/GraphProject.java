
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class City {
    int index;
    String name;
    String code;
    LinkedList<City> connectedCity = new LinkedList<>();

    public City(int i, String name, String code) {
        index = i;
        this.name = name;
        this.code = code;
    }
}

public class GraphProject {

    int size =0;
    List<City> cities = new ArrayList<>();

    void addCity(String code, String name){
        int i = size++;
        City city = new City(i,name, code);
        cities.add(city);
    }

    City cityLookup(String code){
        for(City c:cities){
            if(c.code.equalsIgnoreCase(code)){
                return c;
            }
        }
        return null;
    }

    void connectCity(String code1, String code2){
        City c1 = cityLookup(code1);
        City c2 = cityLookup(code2);
        if( c1 == null || c2 == null){
            System.out.println("Cannot connect, Invalid city code.");
            return;
        }
        c1.connectedCity.add(c2);
    }


    private void findBFSpath(String code) {
        boolean[] visited = new boolean[size];
        City city = cityLookup(code);
        System.out.println("\n printing BFS path from city "+ city.name);
        LinkedList<City> q = new LinkedList<>();
        q.add(city);
        visited[city.index] = true;

        while(!q.isEmpty()){
            City currentCity = q.poll();
            System.out.print(" >> "+ currentCity.name);


            for(City cc:currentCity.connectedCity){
                if(!visited[cc.index]){
                    visited[cc.index] = true;
                    q.add(cc);
                }
            }


        }

    }

    private void findDFSpath(String code) {
        boolean[] visited = new boolean[size];
        City city = cityLookup(code);
        System.out.println("printing DFS path from city "+ city.name);
        DFS(city, visited);
    }

    private void DFS(City city, boolean[] visited) {
        visited[city.index] = true;
        for(City c :city.connectedCity){
            if(!visited[c.index]){
                DFS(c,visited);
            }
        }
        System.out.println(" >> "+city.name);
    }
    //shortest distance between two cities
    public int disBtwTwoCities(String cityOneCode , String cityTwoCode){
    	Graph_Shortest_Path g = new Graph_Shortest_Path();
    	return g.algo_dijkstra(genereateAdjacencyMatrix(), cityOneVertexIndex, cityTwoVertexIndex);
        //return 0;
    }



    public static void main(String[] args) {

        GraphProject g = new GraphProject();


        g.addCity("LR", "Larkana");
        g.addCity("NS", "Naseerabad");
        g.addCity("DD", "Dadu");
        g.addCity("PT", "Petaro");
        g.addCity("JM", "Jamshoro");


        g.addCity("GB", "Gambat");
        g.addCity("KH" , "Khairpur");
        g.addCity("MR" , "Moro");
        g.addCity("NW" , "Nawabshah");

        //larkana to jamshoro through DADU
        g.connectCity("LR", "NS");
        g.connectCity("NS", "DD");
        g.connectCity("DD", "PT");
        g.connectCity("PT", "JM");

        //larkana to jamshoro through moro
        g.connectCity("LR", "GB");
        g.connectCity("GB", "KH");
        g.connectCity("KH", "MR");
        g.connectCity("MR", "NW");
        g.connectCity("NW", "JM");



        // DFS to find path
        g.findDFSpath("LR");
        g.findDFSpath("NS");

        // BFS to find path
        g.findBFSpath("LR");
        g.findBFSpath("NS");


    }



}
