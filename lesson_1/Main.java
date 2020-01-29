package lesson_1;

import lesson_1.competitor.Cat;
import lesson_1.competitor.Competitor;
import lesson_1.competitor.Human;
import lesson_1.competitor.Robot;
import lesson_1.obstacle.Obstacle;
import lesson_1.obstacle.Track;
import lesson_1.obstacle.Wall;

public class Main {

	public static void main(String[] args) {
		Competitor[] competitors = {
				new Human("Eugene", 42, 2),
				new Robot("T1000", 100, 10),
				new Cat("Barsik", 1, 8)
		};
		
		Obstacle[] obstacles = {
				new Track(10),
				new Wall(15),
				new Track(100)
		};
		
		for(Competitor c: competitors) {
			for(Obstacle o: obstacles) {
				o.start(c);
				if(!c.isOnDistance()) {
					break;
				}
			}
		}
	}

}
