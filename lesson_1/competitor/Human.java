package lesson_1.competitor;

public class Human implements Competitor{
	
	String name;
	int max_length;
	int max_height;
	boolean isActive;

	public Human(String name, int max_length, int max_height){
		this.name = name;
		this.max_length = max_length;
		this.max_height = max_height;
		this.isActive = true;
	}
	
	@Override
	public void run(int length) {
		if(max_length >= length) {
			System.out.println(name + " успешно пробежал дистанцию");
		}else {
			System.out.println(name + " провалил забег");
			isActive = false;
		}
	}

	@Override
	public void jump(int height) {
		if(max_height >= height) {
			System.out.println(name + " успешно перепрыгнул препятствие");
		}else {
			System.out.println(name + " провалил прыжок");
			isActive = false;
		}
	}
	
	@Override
	public boolean isOnDistance() {
		return isActive;
	}

}
