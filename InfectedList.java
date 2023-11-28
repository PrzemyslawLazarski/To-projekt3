import java.util.ArrayList;

public class InfectedList {
    // Prywatne pole przechowujące jedyną instancję klasy
    private static InfectedList instance;

    //lista wszystkich zarażonych
    private ArrayList<Person> infected;
    //lista postępujących infekcji
    private ArrayList<InfectionProgress> infections;

    private InfectedList() {
        this.infected = new ArrayList<Person>();
        this.infections = new ArrayList<InfectionProgress>();
    }

    // Metoda do uzyskania instancji singletona
    public static InfectedList getInstance() {
        if (instance == null) {
            // Jeżeli instancja nie istnieje, utwórz ją
            instance = new InfectedList();
        }
        return instance;
    }

    public void addInfected(Person infected) {
        this.infected.add(infected);
    }

    public void removeInfected(Person recovered) {
        this.infected.remove(recovered);
    }

    public ArrayList<Person> getInfectedList() {
        ArrayList<Person> temp = new ArrayList<>();
        for (Person person : this.infected) {
            temp.add(person);
        }
        return temp;
    }

    public ArrayList<InfectionProgress> getInfections() {
        return this.infections;
    }

    public void setInfections(ArrayList<InfectionProgress> infections) {
        this.infections = new ArrayList<>(infections);
    }

    public void setInfectedList(ArrayList<Person> infectedList) {
        this.infected = new ArrayList<>(infectedList);
    }

    public boolean containsPPL(Person infected, Person healthy) {
        for (int i = 0; i < infections.size(); i++) {
            if (infections.get(i).getHealthy() == healthy && infections.get(i).getInfected() == infected) {
                return true;
            }
        }
        return false;
    }
}