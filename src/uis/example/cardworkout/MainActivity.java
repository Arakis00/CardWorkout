package uis.example.cardworkout;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

	//UI objects
	TextView currentExercise, total1, total2, total3, total4, cardCountLabel;
	ImageView cardImage;
	Button drawBtn, againBtn, finishBtn;
	
	Chronometer myChrono;
	
	//global variables
	ArrayList<Card> cardDeck;
	int cardsLeft = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//link variables
		currentExercise = (TextView) findViewById(R.id.currentExercise);
		total1 = (TextView) findViewById(R.id.total1);
		total2 = (TextView) findViewById(R.id.total2);
		total3 = (TextView) findViewById(R.id.total3);
		total4 = (TextView) findViewById(R.id.total4);
		cardCountLabel = (TextView) findViewById(R.id.cardCountLabel);
		cardImage = (ImageView) findViewById(R.id.cardImage);
		drawBtn = (Button) findViewById(R.id.drawBtn);
		againBtn = (Button) findViewById(R.id.againBtn);
		finishBtn = (Button) findViewById(R.id.finishBtn);
		
		myChrono = (Chronometer) findViewById(R.id.timer);
		
		//initialize list
		cardDeck = new ArrayList<Card>(52);
		
		//setup deck
		createDeck();
		
		/*
		//testing purposes
		for (int i = 0; i < 52; i++)
		{
			System.out.println(cardDeck.get(i).getValue() + " - " + cardDeck.get(i).getSuit() + " - " + cardDeck.get(i).getImagePath());
		}
		*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void createDeck(){
		//loop control
		int SUITS = 4; //number of suits
		
		int cardImageCount = 1; //counter that holds which png image to load
		String imagePath; //to hold image path to load
		
		//cycle through the cards in the following order: (Ace, King, Queen, Jack, 10, 9, 8, 7, 6, 5, 4, 3, 2)
		for (int c = 14; c > 1; c--) //c will hold card value
		{
			//cycle through suits in the following order (Club, Space, Heart, Diamond)
			for (int s = 0; s < SUITS; s++) //s will hold suit value
			{
				imagePath = "_"+cardImageCount; //prefix imagePath with underscore
				Card tempCard = new Card(s, c, imagePath);
				cardDeck.add(cardsLeft, tempCard);
				
				cardImageCount++;
				cardsLeft++;
			}
		}
		
		//shuffle deck
				Collections.shuffle(cardDeck);
	}
	
	public void displayExercise(){
		String exercise; //to hold exercise to perform
		
		//check card suit and save proper exercise to string in order to display in label
		if (cardDeck.get(cardsLeft).getSuit() == 0) //clubs
		{
			exercise = "Pushups";
			//update total label
			total1.setText(String.valueOf((Integer.valueOf((String) total1.getText()) + cardDeck.get(cardsLeft).getValue())));
		}
		else if (cardDeck.get(cardsLeft).getSuit() == 1) //spades
		{
		exercise = "Pullups";
			//update total label
			total2.setText(String.valueOf((Integer.valueOf((String) total2.getText()) + cardDeck.get(cardsLeft).getValue())));
		}
		else if (cardDeck.get(cardsLeft).getSuit() == 2) //hearts
		{
			exercise = "Situps";
			//update total label
			total3.setText(String.valueOf((Integer.valueOf((String) total3.getText()) + cardDeck.get(cardsLeft).getValue())));
		}
		else //diamonds
		{
			exercise = "Jumping Jacks";
			//update total label
			total4.setText(String.valueOf((Integer.valueOf((String) total4.getText()) + cardDeck.get(cardsLeft).getValue())));
		}
		
		//print exercise to perform
		currentExercise.setText("Do " + cardDeck.get(cardsLeft).getValue() + " " + exercise);
		
	}
	
	
	public void drawClicked(View view){
	
		if (cardsLeft == 52) //start timer if first card is being drawn
		{
			myChrono.setBase(SystemClock.elapsedRealtime());
			myChrono.start();
		}
		
		--cardsLeft;
		
		//set current card image
		String imageString = cardDeck.get(cardsLeft).getImagePath();
		Resources res = getResources();
		int resId = res.getIdentifier(imageString, "drawable", getPackageName());
		Drawable drawable = res.getDrawable(resId);
		cardImage.setImageDrawable(drawable);
		
		
		//show exercise
		displayExercise();
		//put remaining count in label
		cardCountLabel.setText(String.valueOf(cardsLeft));
		
		if (cardsLeft == 0) //on last card
		{
			drawBtn.setVisibility(View.GONE);  //hide button
			finishBtn.setVisibility(View.VISIBLE); //show button
		}
		
		
	}
	
	
	public void againClicked(View view){
		againBtn.setVisibility(View.GONE);
		drawBtn.setVisibility(View.VISIBLE);
		
		cardsLeft = 0; //ensure cards are reset to 0
		//setup a new deck
		cardDeck = new ArrayList<Card>(52);
		createDeck();
		//clear labels
		currentExercise.setText("");
		total1.setText("0");
		total2.setText("0");
		total3.setText("0");
		total4.setText("0");
		cardCountLabel.setText(String.valueOf(cardsLeft)); //cards left should be 52 again after calling createDeck
		//reset card image
		cardImage.setImageResource(R.drawable.b1fv);
		//reset timer
		myChrono.setBase(SystemClock.elapsedRealtime());
		myChrono.start();
		myChrono.stop();
		
	}
	
	
	public void finishClicked(View view){
		finishBtn.setVisibility(View.GONE);
		againBtn.setVisibility(View.VISIBLE);
		
		//stop timer when last exercise is finished
		myChrono.stop();
	}
	
	

}
