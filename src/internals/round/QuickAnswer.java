package internals.round;

import internals.question.Question;
import internals.question.QuestionLibrary;

/**
 * Η {@code QuickAnswer} επεκτείνει την {@code Round}, μοντελοποιώντας τον τύπο γύρου Γρήγορη απάντηση.
 * @author Ioannis Baraklilis
 * @author Alexandros Tsingos
 *
 * @version 2021.1.2
 */

public class QuickAnswer extends Round{
    private int questionAnsweredCorrectly; // Ο αριθμός των παιχτών που έχουν απαντήσει σωστά, ανάλογα με το εάν έχει απαντηθεί σωστά η ερώτηση

    /**
     * Τυπικός κατασκευαστής που αρχικοποιεί τα δεδομένα της κλάσης.
     * @param questionStore αναφορά σε αντικείμενο της κλάσης QuestionLibrary, απο την οποία θα "παρθούν" οι ερωτήσεις.
     * @param questionNumber αριθμός ερωτήσεων που θα πρέπει να απαντηθούν σε αυτό τον γύρο.
     */
    public QuickAnswer(QuestionLibrary questionStore, int questionNumber){
        super(questionStore,2,2,questionNumber);
    }

    /**
     * Επιστρέφει το όνομα του τύπου γύρου.
     * @return όνομα του τύπου του γύρου.
     */
    @Override
    public String getRoundName() {
        return "Γρήγορη Απάντηση";
    }

    /**
     * Επιστρέφει την περιγραφή του τύπου γύρου.
     * @return περιγραφή του τύπου του γύρου.
     */
    @Override
    public String getRoundDescription() {
        return "Ο πρώτος που απαντάει σωστά κερδίζει 1000 πόντους και ο δεύτερος που απαντάει σωστά κερδίζει 500 πόντους.";
    }

    /**
     * Σηματοδοτεί την συνέχεια του γύρου σε επόμενη ερώτηση και εκτελεί τις απαραίτητες προετοιμασίες (αν αυτές χρειάζονται) για αυτή.
     * @return αναφορά στο ίδιο το αντικείμενο για χρήση σε "αλυσιδωτές" κλήσεις αυτού.
     */
    @Override
    public Round proceed() {
        questionAnsweredCorrectly = 0; //Εφόσον προχωράμε σε καινούργια ερώτηση, αρχικοποιώ την μεταβλητή σε 0
        return super.proceed();
    }

    /**
     * Υπολογίζει και επιστρέφει τον αριθμό πόντων που (πιθανόν μπορεί να) κερδίζει ο παίχτης δεδομένης της απάντησης που έδωσε
     * και της σειράς που απάντησε την ερώτηση.
     * Αν ο παίχτης είναι ο πρώτος που απαντάει σωστά τότε επιστρέφονται 1000 πόντοι, ενώ εάν είναι ο δεύτερος επιστρέφονται 500, διαφορετικά επιστρέφονται 0.
     * Αν ο παίχτης δώσει λανθασμένη απάντηση τότε επιστρέφονται μηδέν πόντοι.
     * @param answer Η απάντηση που έδωσε ο παίχτης.
     * @return αριθμός πόντων που αντιστοιχούν στην δοθείσα απάντηση του ορίσματος.
     */
    public int answerQuestion(String answer){
        Question temp = getQuestionInstance(); // Παίρνω αναφορά στην τρέχουσα ερώτηση

        if(temp!=null){// Άν υπάρχουν διαθέσιμες ερωτήσεις
            if(temp.isRight(answer)){ //Αν ο παίχτης απάντησε σωστά
                if(questionAnsweredCorrectly == 0){ //Αν είναι ο πρώτος που απαντάει σωστά
                    questionAnsweredCorrectly++;
                    return 1000;
                }
                else if (questionAnsweredCorrectly == 1){ // Ο δεύτερος που απαντάει σωστά
                    questionAnsweredCorrectly++;
                    return 500;
                } else { // Έχει νόημα να απαντάνε 2 παίχτες, οπότε επιστρέφω 0
                    return 0;
                }
            }
            else { //Απάντησε λάθος
                return 0;
            }
        }
        else { //Δεν υπάρχουν διαθέσιμες ερωτήσεις, οπότε γυρνάω μηδέν πόντους
            return 0;
        }
    }

}
