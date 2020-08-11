package wanek.average;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NotesControl {

    private ArrayList<Double> recordOfAscores; // запись каждого среднего значения
    private ArrayList<Double> recordOfSums; // запись каждой суммы отметок
    private ArrayList<Integer> recordOfNotes; // запись каждой введенной оценки
    private int currentCountNotes; // текущее кол-во введенных оценок
    private double currentAscoreNotes; // текущее среднее значение
    private double currentSumNotes; // текущая сумма оценок

    private double roundFive = 4.5, roundFour = 3.5;

    public NotesControl(double roundFive, double roundFour) {
        recordOfAscores = new ArrayList<>();
        recordOfSums  = new ArrayList<>();
        recordOfNotes  = new ArrayList<>();
        currentCountNotes = 0;
        currentAscoreNotes = 0f;
        currentSumNotes = 0;
        if(roundFive < 4.1) this.roundFive = 4.1;
        else if(roundFive > 4.9) this.roundFive = 4.9;
        else if(roundFive >= 4.1 && roundFive <= 4.9) this.roundFive = roundFive;
        else this.roundFive = 4.5;
        if(roundFour < 3.1) this.roundFour= 3.1;
        else if(roundFour > 3.9) this.roundFour = 3.9;
        else if(roundFour >= 3.1 && roundFour <= 3.9) this.roundFour = roundFour;
        else this.roundFour = 3.5;
    }
    public NotesControl() {
        recordOfAscores = new ArrayList<>();
        recordOfSums = new ArrayList<>();
        recordOfNotes = new ArrayList<>();
        currentCountNotes = 0;
        currentAscoreNotes = 0f;
        currentSumNotes = 0;
    }

    public double getRoundFive() {
        return roundFive;
    }
    public double getRoundFour() {
        return roundFour;
    }

    public double addNote(int note){
        currentCountNotes++;
        currentSumNotes = currentSumNotes + (double)note;
        currentAscoreNotes = Math.round(currentSumNotes/currentCountNotes*100.0)/100.0;
        recordOfAscores.add(currentAscoreNotes);
        recordOfSums.add(currentSumNotes);
        recordOfNotes.add(note);
        return currentAscoreNotes;
    }
    public double deleteOne() {
        currentCountNotes--;
        if(currentCountNotes == 0) {
            return 0f;
        } else {
            recordOfAscores.remove(recordOfAscores.size() - 1);
            currentAscoreNotes = recordOfAscores.get(recordOfAscores.size() - 1);
            recordOfSums.remove(recordOfSums.size() - 1);
            currentSumNotes = recordOfSums.get(recordOfSums.size() - 1);
            recordOfNotes.remove(recordOfNotes.size() - 1);
            return currentAscoreNotes;
        }
    }
    public double deleteAll() {
        currentCountNotes = 0;
        currentAscoreNotes = 0f;
        currentSumNotes = 0f;
        recordOfAscores.clear();
        recordOfSums.clear();
        recordOfNotes.clear();
        return currentAscoreNotes;
    }
    public double getCurrentAscoreNotes() {
        return currentAscoreNotes;
    }
    public int getСurrentCountNotes() {
        return currentCountNotes;
    }

    public int getHowManyNotes(float whatWith, double whatNeed) {
        if(whatNeed == 4) {
            whatNeed = roundFour;
        } else if(whatNeed == 5) {
            whatNeed = roundFive;
        } else {
            whatNeed -= 0.5;
        }
        int count = 0;
        int tempCountNotes = this.currentCountNotes;
        double tempSumNotes = this.currentSumNotes;
        for(;;){
            count++;
            tempCountNotes++;
            tempSumNotes = tempSumNotes + whatWith;
            if(tempSumNotes/tempCountNotes >= whatNeed) {
                return count;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        if(getСurrentCountNotes() == 0) return "";
        if(getСurrentCountNotes() == 1) return String.valueOf(recordOfNotes.get(0));
        else {
            String notes = "(".concat(String.valueOf(recordOfNotes.get(0)));
            for(int i = 1; i < (recordOfNotes.size()-1); i++) {
                notes += " + " + (recordOfNotes.get(i));
            }
            notes += " + " + (recordOfNotes.get(recordOfNotes.size()-1)) + ")";
            return notes.concat("/").concat(String.valueOf(getСurrentCountNotes()));
        }
    }

    public String getNotesToString() {
        if(getСurrentCountNotes() == 0) return "";
        if(getСurrentCountNotes() == 1) return String.valueOf(recordOfNotes.get(0));
        else {
            String notes = "(".concat(String.valueOf(recordOfNotes.get(0)));
            for(int i = 1; i < (recordOfNotes.size()-1); i++) {
                notes += " + " + (recordOfNotes.get(i));
            }
            notes += " + " + (recordOfNotes.get(recordOfNotes.size()-1)) + ")";
            return notes.concat("/").concat(String.valueOf(getСurrentCountNotes()));
        }
    }
}
