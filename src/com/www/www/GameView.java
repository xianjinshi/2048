package com.www.www;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {
	private Card[][] cardsMap = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
	
	public GameView(Context context) {
		super(context);
		init();
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h) - 10)/4;
		addCard(cardWidth, cardWidth);
		boolean firstFlag = true;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum() != 0){
					firstFlag = false;
					break;
				}
			}
		}
		if(firstFlag == true){
			startGame();
		}
	}
	private void startGame() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cardsMap[j][i].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}
	private void addRandomNum() {
		emptyPoints.clear();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(cardsMap[i][j].getNum() <=0){
					emptyPoints.add(new Point(i,j));
				}
			}
		}
		Point p = emptyPoints.remove((int)Math.random()*emptyPoints.size());
		cardsMap[p.x][p.y].setNum(Math.random()>0.5 ? 4:2);
	}
	private void addCard(int cardWidth, int cardWidth2) {
		Card card;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				card = new Card(getContext());
				card.setNum(0);
				addView(card,cardWidth,cardWidth2);
				cardsMap[i][j] = card;
			}
		}
		
	}
	private void init() {
		setBackgroundColor(0xff000000);
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if(Math.abs(offsetX) > Math.abs(offsetY)){
						if(offsetX < -5){
							toLeft();
						}
						else if(offsetX > 5){
							toRight();
						}
					}
					else {
						if(offsetY < -5){
							toUp();
						}
						else if(offsetY > 5){
							toDown();
						}
					}
					break;

				default:
					
				}	
				
				
				
				
				return false;
			}
			
		});
		
	}
	private void toDown() {
		
		
	}
	private void toUp() {
		
		
	}
	private void toRight() {
		
		
	}
	private void toLeft() {
		
		
	}

}
