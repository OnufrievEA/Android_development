package lesson_1.obstacle;

import lesson_1.competitor.Competitor;

public class Wall extends Obstacle{

	int height;
	
	public Wall(int height) {
		this.height = height;
	}

	@Override
	public void start(Competitor c) {
		c.jump(height);
	}

}
