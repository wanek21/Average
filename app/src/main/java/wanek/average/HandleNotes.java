package wanek.average;

import java.util.ArrayList;

public class HandleNotes {

    private ArrayList<Double> recordOfAscores; // запись каждого среднего значения в массив
    private ArrayList<Double> recordOfSums; // запись каждой суммы отметок в массив
    private int countNotes; // текущее кол-во введенных оценок
    private double ascoreNotes; // текущее среднее значение
    private double sumNotes; // текущая сумма оценок
    HandleNotes() {
        recordOfAscores = new ArrayList<>();
        recordOfSums  = new ArrayList<>();
        countNotes = 0;
        ascoreNotes = 0f;
        sumNotes = 0;
    }
    public double clickNote(int note){
        countNotes++;
        sumNotes = sumNotes + (double)note;
        ascoreNotes = Math.round(sumNotes/countNotes*100.0)/100.0;
        recordOfAscores.add(ascoreNotes);
        recordOfSums.add(sumNotes);
        return ascoreNotes;
    }
    public double clickDeleteOne() {
        countNotes--;
        if(countNotes == 0) {
            return this.clickDeleteAll();
        } else {
            recordOfAscores.remove(recordOfAscores.size() - 1);
            ascoreNotes = recordOfAscores.get(recordOfAscores.size() - 1);
            recordOfSums.remove(recordOfSums.size() - 1);
            sumNotes = recordOfSums.get(recordOfSums.size() - 1);
            return ascoreNotes;
        }
    }
    public double clickDeleteAll() {
        countNotes = 0;
        ascoreNotes = 0f;
        sumNotes = 0f;
        recordOfAscores.clear();
        recordOfSums.clear();
        return ascoreNotes;
    }
    public double getAscoreNotes() {
        return ascoreNotes;
    }
    public int getCountNotes() {
        return countNotes;
    }
    public String getFiveWithFive() { // сколько нужно получить 5ок, чтобы вышла 5ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            count++;
            countNotes++;
            sumNotes = sumNotes + 5.0f;
            if(sumNotes/countNotes >= 4.5) {
                return textNote(count,5);
            }
        }
    }
    public String getFourWithFive() { // сколько нужно получить 5ок, чтобы вышла 4ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            count++;
            countNotes++;
            sumNotes = sumNotes + 5.0f;
            if(sumNotes/countNotes >= 3.5) {
                return textNote(count,5);
            }
        }
    }
    public String getFourWithFour() { // сколько нужно получить 4ок, чтобы вышла 4ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            countNotes++;
            count++;
            sumNotes = sumNotes + 4.0f;
            if(sumNotes/countNotes >= 3.5) {
                return textNote(count,4);
            }
        }
    }
    public String textNote(int note, int noteText) {
        if(note == 1 || ((note % 10 == 1) && (note != 11))) {
            if(noteText == 4) {
                return note + " четверка";
            } else {
                return note + " пятерка";
            }
        } else if ((note % 10 == 2 || note % 10 == 3 || note % 10 == 4) && (note - note % 10 != 10)) {
            if(noteText == 4) {
                return note + " четверки";
            } else {
                return note + " пятерки";
            }
        } else {
            if(noteText == 4) {
                return note + " четверок";
            } else {
                return note + " пятерок";
            }
        }
    }
}
