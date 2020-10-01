package com.example.canv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
/*
Do obsługi siatki uzywana jest tablica 3 wymiarowa
tablica przechowuje 3 informacje o kazdym punkcie
pierwsze dwa indexy sluza do wyboru punktu w tablicy 2
3 index sluzy do przechowywania nastepujacych informacji:
TaB[x][y][0] => stan rzeczywisty punktu 1 zycie 0 smierc na podstawie tej wartosci rysowane jest plotno
TaB[x][y][1] => ilosc sasiednich zyjacych pol
TaB[x][y][1] => Tablica pomocnicza zapisujaca stan nowej tablicy zyjacych i martwych komorek tak aby wykonywanie obliczen nie wplywalo na nastepne punkty,
po wykonaniu algorytmu wartosci z tej tablicy sa przepisywane do tablicy TaB[x][y][0] i aktualizowane jest plotno

 */
public class MyCanv extends View {

    Paint paint;
    public static int[][][] TaB= new int[13][18][3];

    public MyCanv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xs = canvas.getWidth() / 2;
        int ys = canvas.getHeight() / 2;
// rysowanie siatki czerwoych lini /////////////////////////////////////////////////////////////////
        for(int a=0; a< 2*xs; a=a+100){
            paint.setColor(Color.RED);
            canvas.drawLine(a, 2*ys, a, -ys, paint);
        }
        for(int b=0; b< 2*ys; b=b+100){
            paint.setColor(Color.RED);
            canvas.drawLine(2*xs, b, -xs, b, paint);
        }

// rysowanie komorek z tablicy na plotnie //////////////////////////////////////////////////////////
        for (int x1 = 0; x1<=11;x1++){
            for(int y1 = 0; y1<=16;y1++){
                RectF pp = new RectF(
                        x1*100+1,
                        y1*100+1,
                        x1*100+99,
                        y1*100+99);

                if( TaB[x1+1][y1][0]==1){
                    paint.setColor(Color.BLACK);
                    canvas.drawRect(pp, paint);

                }
                if( TaB[x1+1][y1][0]==0){
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(pp, paint);
                }
            }
        }

        //Gray Bar top
        paint.setColor(Color.GRAY);
        canvas.drawRect(1f,1f,canvas.getWidth(),100, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
//wyluskanie akcji pojedynczego klikniecia za pomoca rejestrowania akcji wcisniecia ekranu
        if(event.getAction()==MotionEvent.ACTION_DOWN){
//przepisanie wartosci wspolrzednej na tablice
           int a = (int) (( (event.getX()-event.getX()%100)/100)+1);
           int b = (int) (( (event.getY()-event.getY()%100)/100));
           if (TaB[a][b][0]==0)
           {
               TaB[a][b][0]=1;
           }
           else if(TaB[a][b][0]==1)
           {
               TaB[a][b][0]=0;
           }
            invalidate();
        }
        return true;
    }
//czyszczenie tablicy
    public void clear(){
        for (int x2 = 0; x2<=11;x2++){
            for(int y2 = 0; y2<=16;y2++) {

                TaB[x2][y2][0]=0;
            }
        }
    }
//algorytm life
    public void oneStep(){

        for (int x2 = 0; x2<=11;x2++){
            for(int y2 = 0; y2<=16;y2++) {
//liczenie sasiadow dla kazdegj komorki
                int neighbour = 0;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2-1][y2-1][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2-1][y2][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2-1][y2+1][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2][y2-1][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2][y2+1][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2+1][y2-1][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2+1][y2][0]==1)
                        neighbour++;

                if(x2-1>=0 && y2-1>=0)
                    if(TaB[x2+1][y2+1][0]==1)
                        neighbour++;

                TaB[x2][y2][1]=neighbour;

            }
        }
// realizacja zasad ewolucji
        for (int x3 = 0; x3<=11;x3++) {
            for (int y3 = 0; y3 <= 16; y3++) {
// tworzenie nowych komorek z martwych
                if(TaB[x3][y3][0]==0){
                    if (TaB[x3][y3][1] == 3){

                        TaB[x3][y3][2] = 1;

                    }
                    else
                        TaB[x3][y3][2] = TaB[x3][y3][0];
                }
//operacje na zyjacych komorkach
                if(TaB[x3][y3][0]==1){
                    if (TaB[x3][y3][1] < 2){

                        TaB[x3][y3][2] = 0;
                    }

                    else if (TaB[x3][y3][1] == 2 || TaB[x3][y3][1] == 3){

                        TaB[x3][y3][2] = 1;
                    }
                    else if (TaB[x3][y3][1] > 3){

                        TaB[x3][y3][2] = 0;
                    }
                    else
                        TaB[x3][y3][2] = TaB[x3][y3][0];
                }
            }
        }
//przepisanie pomocniczej tablicy do wlasciwej
        for (int x3 = 0; x3<=11;x3++) {
            for (int y3 = 0; y3 <= 16; y3++) {
                TaB[x3][y3][0] = TaB[x3][y3][2];
            }
        }
    }
}
