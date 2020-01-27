package wanek.average;

import java.util.ArrayList;

public class HandleNotes {

    private ArrayList<Double> recordOfAscores; // запись каждого среднего значения в массив
    private ArrayList<Double> recordOfSums; // запись каждой суммы отметок в массив
    private ArrayList<Integer> recordOfNotes; // запись каждой оценки
    private int countNotes; // текущее кол-во введенных оценок
    private double ascoreNotes; // текущее среднее значение
    private double sumNotes; // текущая сумма оценок

    private double roundFive, roundFour;

    public double getRoundFive() {
        return roundFive;
    }

    public double getRoundFour() {
        return roundFour;
    }

    HandleNotes(double roundFive, double roundFour) {
        recordOfAscores = new ArrayList<>();
        recordOfSums  = new ArrayList<>();
        recordOfNotes  = new ArrayList<>();
        countNotes = 0;
        ascoreNotes = 0f;
        sumNotes = 0;
        if(roundFive < 4.1) this.roundFive = 4.1;
        else if(roundFive > 4.9) this.roundFive = 4.9;
        else if(roundFive >= 4.1 && roundFive <= 4.9) this.roundFive = roundFive;
        else this.roundFive = 4.5;
        if(roundFour < 3.1) this.roundFour= 3.1;
        else if(roundFour > 3.9) this.roundFour = 3.9;
        else if(roundFour >= 3.1 && roundFour <= 3.9) this.roundFour = roundFour;
        else this.roundFour = 3.5;
    }
    HandleNotes() {
        recordOfAscores = new ArrayList<>();
        recordOfSums = new ArrayList<>();
        recordOfNotes = new ArrayList<>();
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
        recordOfNotes.add(note);
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
            recordOfNotes.remove(recordOfNotes.size() - 1);
            return ascoreNotes;
        }
    }
    public double clickDeleteAll() {
        countNotes = 0;
        ascoreNotes = 0f;
        sumNotes = 0f;
        recordOfAscores.clear();
        recordOfSums.clear();
        recordOfNotes.clear();
        return ascoreNotes;
    }
    public double getAscoreNotes() {
        return ascoreNotes;
    }
    public int getCountNotes() {
        return countNotes;
    }

    // методы для 5-бальной системы
    public String getFiveWithFive() { // сколько нужно получить 5ок, чтобы вышла 5ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            count++;
            countNotes++;
            sumNotes = sumNotes + 5.0f;
            if(sumNotes/countNotes >= roundFive) {
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
            if(sumNotes/countNotes >= roundFour) {
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
            if(sumNotes/countNotes >= roundFour) {
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

    //методы для 12-бальной системы
    public String getElevenWithEleven() { // сколько нужно получить 11ок, чтобы вышла 11ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            count++;
            countNotes++;
            sumNotes = sumNotes + 11.0f;
            if(sumNotes/countNotes >= 10.5) {
                return textNoteUk(count,11);
            }
        }
    }
    public String getEightWithEight() { // сколько нужно получить 8ок, чтобы вышла 8ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            count++;
            countNotes++;
            sumNotes = sumNotes + 8.0f;
            if(sumNotes/countNotes >= 7.5) {
                return textNoteUk(count,8);
            }
        }
    }
    public String getEightWithEleven() { // сколько нужно получить 11ок, чтобы вышла 8ка
        int count = 0;
        int countNotes = this.countNotes;
        double sumNotes = this.sumNotes;
        for(;;){
            countNotes++;
            count++;
            sumNotes = sumNotes + 11.0f;
            if(sumNotes/countNotes >= 7.5) {
                return textNoteUk(count,11);
            }
        }
    }
    private String textNoteUk(int note, int noteText) {
        if(note == 1 || ((note % 10 == 1) && (note != 11))) {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерка";
            }
        } else if ((note % 10 == 2 || note % 10 == 3 || note % 10 == 4) && (note - note % 10 != 10)) {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерки";
            }
        } else {
            if(noteText == 11) {
                return note + " одиннадцать";
            } else {
                return note + " восьмерок";
            }
        }
    }
    public String getNotesString() {
        if(getCountNotes() == 0) return "";
        if(getCountNotes() == 1) return String.valueOf(recordOfNotes.get(0));
        else {
            String notes = "(".concat(String.valueOf(recordOfNotes.get(0)));
            for(int i = 1; i < (recordOfNotes.size()-1); i++) {
                notes += " + " + (recordOfNotes.get(i));
            }
            notes += " + " + (recordOfNotes.get(recordOfNotes.size()-1)) + ")";
            return notes.concat("/").concat(String.valueOf(getCountNotes()));
        }
    }
}
