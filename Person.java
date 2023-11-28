import java.util.Random;
import vectors_custom.*;

public class Person {
    private Health health;
    private Random rand;
    private Vector2D direction;
    private Vector2D location;

    public Person(boolean immune, Vector2D location) {
        this.rand = new Random();
        this.health = new NotInfected(immune);//immune, rand.nextBoolean()

        this.location = location;
        double x = rand.nextDouble() * 0.2 -0.1;
        double y = rand.nextDouble() * 0.2 -0.1;
        this.direction = new Vector2D(x,y);
    }

    public Person(Person source) {
        this.direction = new Vector2D(source.direction);
        this.rand = new Random();
        this.location = new Vector2D(source.location);

        if (source.health instanceof Infected) {
            this.health = new Infected((Infected) source.health);
        } else if (source.health instanceof NotInfected) {
            this.health = new NotInfected((NotInfected) source.health);
        }
    }

    public Vector2D getLocation() {
        return this.location;
    }

    // for pupulation grow
    public Person() {
        this.rand = new Random();
        if (getInfection()) {
            this.health = new Infected(this.rand.nextBoolean(), this);
        } else {
            this.health = new NotInfected(false);
        }
        
        double x;
        double y;
        
        if (this.rand.nextBoolean()) {
            x = Simulation.width;
            if (this.rand.nextBoolean()) {
                x = -x;
                y = this.rand.nextDouble(Simulation.length);
            } else {
                y = this.rand.nextDouble(Simulation.length);
            }
        } else {
            y = Simulation.length;
            if (this.rand.nextBoolean()) {
                y = -y;
                x = this.rand.nextDouble(Simulation.width);
            } else {
                x = this.rand.nextDouble(Simulation.width);
            }
        }
        this.location = new Vector2D(x, y);
        x = rand.nextDouble() * 0.2 -0.1;
        y = rand.nextDouble() * 0.2 -0.1;
        this.direction = new Vector2D(x,y);
    }

    private boolean getInfection() {
        double range = this.rand.nextInt(100);
        return range < 10;
    }

    public Health getHealth() {
        return this.health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public void gainInfection(Person infected) { 

        if (this.health.isImmune() == false) {
            if (infected.getHealth().haveSymptoms()) {
                if (rand.nextBoolean()) {
                    setHealth(new Infected(false, this));
                } else {
                    setHealth(new Infected(true, this));
                }
            } else {
                if (rand.nextBoolean()) {
                    if (rand.nextBoolean()) {
                        setHealth(new Infected(false, this));
                    } else {
                        setHealth(new Infected(true, this));
                    }
                }
            }
        }
    }

    public void recover() {
        this.health = new NotInfected(true);
        InfectedList.getInstance().removeInfected(this);
    }

    // on true Person leaves Simulation area
    public boolean move() {

        //max przesuniecie na klatkę = 0.1
        // szana na zmianę kierunku
        if (this.rand.nextInt(100) < 50) {
            double x = rand.nextDouble() * 0.2 -0.1;
            double y = rand.nextDouble() * 0.2 -0.1;
            this.direction = new Vector2D(x,y);
        }
        double[] save = this.location.getComponents();
        this.location.add(this.direction);
        double[] tmp = this.location.getComponents(); 
        
        if (tmp[0] > Simulation.width || tmp[0] < 0 || tmp[1] > Simulation.length || tmp[1] < 0) {
            
            if (this.rand.nextInt(100) < 20) {
                this.location.setComponents(save[0], save[1]);
                double[] tmp2 = this.direction.getComponents();
                this.direction.setComponents(-tmp2[0], -tmp2[1]);

                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}