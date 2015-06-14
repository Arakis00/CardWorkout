package uis.example.cardworkout;


public class Card {
	
	//public enum Suit {Club, Space, Heart, Diamond};
	int suit;
	int value;
	String imagePath;
	
	//constructor
	Card(int suit1, int value1, String imagePath1){
		suit = suit1;
		value = value1;
		imagePath = imagePath1;
	}
	
	int getSuit(){
		return suit;
	}
	
	int getValue(){
		return value;
	}
	
	String getImagePath(){
		return imagePath;
	}
	

}