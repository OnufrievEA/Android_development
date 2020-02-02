package lesson_1.obstacle;

import lesson_1.competitor.Competitor;

public class Track extends Obstacle{

	int length;
	
	public Track(int length) {
		this.length = length;
	}

	@Override
	public void start(Competitor c) {
		c.run(length);
	}

}
