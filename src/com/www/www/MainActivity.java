package com.www.www;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	private int[][] TextViewId = {{R.id.r00,R.id.r01,R.id.r02,R.id.r03,},
			{R.id.r10,R.id.r11,R.id.r12,R.id.r13,},
			{R.id.r20,R.id.r21,R.id.r22,R.id.r23,},
			{R.id.r30,R.id.r31,R.id.r32,R.id.r33,}};
	private static final String[] nums = {"2", "4", "8",
			"16", "32", "64", "128", "256", "512", "1024", 
			"2048", "4096", "8192"};
	private List<Point> emptyPoints = new ArrayList<Point>();	

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        startGame();
    }
	
	
    public int getIntById(int id){
    	int num;
		TextView tv = (TextView)findViewById(id);
		String text = tv.getText().toString().trim();
		if(!text.equals("")){
			num = Integer.valueOf(text).intValue();
		}else {
			num = 0;
		}
		return num;	
	}
    
    public String getTextById(int id){
		TextView tv = (TextView)findViewById(id);
		String text = tv.getText().toString().trim();
		return text;	
	}
    
    public void setTextById(int id ,String text){
		TextView tv = (TextView)findViewById(id);
		tv.setText(text);	
	}
    public void setIntById(int id ,int in){
		TextView tv = (TextView)findViewById(id);
		tv.setText(String.valueOf(in));	
	}
     
    private void startGame(){
    	addRandomNum();
    	addRandomNum();
    	addRandomNum();
    }
    private void addRandomNum() {
		emptyPoints.clear();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(getIntById(TextViewId[i][j]) == 0){
					emptyPoints.add(new Point(i,j));
				}
			}
		}
		Point p = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
		setIntById(TextViewId[p.x][p.y],(Math.random()>0.5 ? 4:2));
	}
    
    private void init(){
    	TableLayout layout = (TableLayout)findViewById(R.id.tableLayout);
    	TextView tv = (TextView)findViewById(R.id.r00);
        layout.setOnTouchListener(new View.OnTouchListener() {
        	private float startX, startY, offsetX, offsetY;
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					//Toast.makeText(MainActivity.this,"按下",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					//Toast.makeText(MainActivity.this,"抬起",Toast.LENGTH_SHORT).show();
					if(Math.abs(offsetX) > Math.abs(offsetY)){
						if(offsetX < -5){
							toLeft();
						}
						else if(offsetX > 5){
							toRight();
							//Toast.makeText(MainActivity.this,"右",Toast.LENGTH_SHORT).show();
						}
					}
					else {
						if(offsetY < -5){
							toUp();
							//Toast.makeText(MainActivity.this,"上",Toast.LENGTH_SHORT).show();
						}
						else if(offsetY > 5){
							toDown();
							//Toast.makeText(MainActivity.this,"下",Toast.LENGTH_SHORT).show();
						}
					}
					break;
				}
				return true;
			}
		}); 
    }
    public void toUp(){
    	checkComplete();
		boolean addFlag = false;
		for (int y = 0; y < 4; y++) {//遍历Y
			for (int x = 0; x < 4; x++) {//遍历X		
				//setIntById(TextViewId[x][y],y);
				for (int x1 = x+1; x1 < 4; x1++){//寻找X后面大于0的card
					//setIntById(TextViewId[x][y],x1);
					if (getIntById(TextViewId[x1][y]) > 0){//如果找到大于零的card
						if(getTextById(TextViewId[x][y]).equals("")){//判断card[x][y]是否为了，为零则把后面的数赋给它
						setIntById(TextViewId[x][y], getIntById(TextViewId[x1][y]));
						setTextById(TextViewId[x1][y],"");
						addFlag = true;
						}
						else if (getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x1][y]))){
							setIntById(TextViewId[x][y], 2*getIntById(TextViewId[x1][y]));
							setTextById(TextViewId[x1][y],"");
							addFlag = true;
						}
						break;
					}
				}
			}
		}
		if(addFlag == true){
			checkComplete();
			addRandomNum();
		}
		
}
    public void toDown(){
    	checkComplete();
		boolean addFlag = false;
		for (int y = 0; y < 4; y++) {//遍历Y
			for (int x = 3; x >= 0; x--) {//遍历X		
				//setIntById(TextViewId[x][y],y);
				for (int x1 = x-1; x1 >= 0; x1--){//寻找X后面大于0的card
					//setIntById(TextViewId[x][y],x1);
					if (getIntById(TextViewId[x1][y]) > 0){//如果找到大于零的card
						if(getTextById(TextViewId[x][y]).equals("")){//判断card[x][y]是否为了，为零则把后面的数赋给它
						setIntById(TextViewId[x][y], getIntById(TextViewId[x1][y]));
						setTextById(TextViewId[x1][y],"");
						addFlag = true;
						}
						
						else if (getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x1][y]))){
							setIntById(TextViewId[x][y], 2*getIntById(TextViewId[x1][y]));
							setTextById(TextViewId[x1][y],"");
							addFlag = true;
						}
						break;
					}
				}
			}
		}
		if(addFlag == true){			
			addRandomNum();
		}
		
}
    
    public void toLeft(){
    	checkComplete();
		boolean addFlag = false;
		for (int y = 0; y < 4; y++) {//遍历Y
			for (int x = 0; x < 4; x++) {//遍历X		
				//setIntById(TextViewId[x][y],y);
				for (int x1 = x+1; x1 < 4; x1++){//寻找X后面大于0的card
					//setIntById(TextViewId[x][y],x1);
					if (getIntById(TextViewId[y][x1]) > 0){//如果找到大于零的card
						if(getTextById(TextViewId[y][x]).equals("")){//判断card[x][y]是否为了，为零则把后面的数赋给它
						setIntById(TextViewId[y][x], getIntById(TextViewId[y][x1]));
						setTextById(TextViewId[y][x1],"");
						addFlag = true;
						}
						else if (getTextById(TextViewId[y][x]).equals(getTextById(TextViewId[y][x1]))){
							setIntById(TextViewId[y][x], 2*getIntById(TextViewId[y][x1]));
							setTextById(TextViewId[y][x1],"");
							addFlag = true;
						}
						break;
					}
				}
			}
		}
		if(addFlag == true){
			checkComplete();
			addRandomNum();
		}
		
}
/*public void toLeft(){
	Toast.makeText(MainActivity.this,"左",Toast.LENGTH_SHORT).show();
	boolean addFlag = false;

	for(int x=0;x<4;x++){
		for(int y=0;y<4;y++){
			for(int y1=y+1;y<4;y++){
				if (getIntById(TextViewId[x][y1]) > 0){//如果找到大于零的card
					if(getTextById(TextViewId[x][y]).equals("")){//判断card[x][y]是否为了，为零则把后面的数赋给它
					setIntById(TextViewId[x][y],getIntById(TextViewId[x][y1]));
					setTextById(TextViewId[x][y1],"");
					addFlag = true;
					}else if (getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x][y1]))){
					setIntById(TextViewId[x][y], 2*getIntById(TextViewId[x][y1]));
					setTextById(TextViewId[x][y1],"");
					addFlag = true;
					}
					break;
				}
			}
		}
		
	}
	if(addFlag == true){
		addRandomNum();
	}
}*/
    public void toRight(){
    	checkComplete();
		boolean addFlag = false;
		for (int y = 0; y < 4; y++) {//遍历Y
			for (int x = 3; x >= 0; x--) {//遍历X		
				//setIntById(TextViewId[x][y],y);
				for (int x1 = x-1; x1 >= 0; x1--){//寻找X后面大于0的card
					//setIntById(TextViewId[x][y],x1);
					if (getIntById(TextViewId[y][x1]) > 0){//如果找到大于零的card
						if(getTextById(TextViewId[y][x]).equals("")){//判断card[x][y]是否为了，为零则把后面的数赋给它
						setIntById(TextViewId[y][x], getIntById(TextViewId[y][x1]));
						setTextById(TextViewId[y][x1],"");
						addFlag = true;
						}
						
						else if (getTextById(TextViewId[y][x]).equals(getTextById(TextViewId[y][x1]))){
							setIntById(TextViewId[y][x], 2*getIntById(TextViewId[y][x1]));
							setTextById(TextViewId[y][x1],"");
							addFlag = true;
						}
						break;
					}
				}
			}
		}
		if(addFlag == true){			
			addRandomNum();
		}
		
}

private void checkComplete() {
	boolean completeFlag = true;
	//ALL:
	for (int y = 0; y < 4; y++) {
		for (int x = 0; x < 4; x++) {
			if(getTextById(TextViewId[x][y]).equals("")||
					x>0&&getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x-1][y]))||
					x<3&&getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x+1][y]))||
					y>0&&getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x][y-1]))||
					y<0&&getTextById(TextViewId[x][y]).equals(getTextById(TextViewId[x][y+1]))
					)
				completeFlag = false;
		}

	}
	if(completeFlag == true){
		new AlertDialog.Builder(MainActivity.this)
		.setTitle("你失败了！")
		.setNegativeButton("退出", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			
		})
		.setPositiveButton("重来", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startGame();
			}
		})
		.setCancelable(false).show();
	}
	
}  
}
