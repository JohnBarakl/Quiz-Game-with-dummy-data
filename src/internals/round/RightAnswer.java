package internals.round;

import internals.question.Question;
import internals.question.QuestionLibrary;

/**
 * Η {@code RightAnswer} επεκτείνει την {@code Round}, μοντελοποιώντας τον τύπο γύρου Σωστή Απάντηση.
 *
 * @author Ioannis Baraklilis
 * @author Alexandros Tsingos
 *
 * @version 2020.12.3
 */

public class RightAnswer extends Round {

    /**
     * Τυπικός κατασκευαστής που αρχικοποιεί τα δεδομένα της κλάσης.
     * @param questionStore αναφορά σε αντικείμενο της κλάσης QuestionLibrary, απο την οποία θα "παρθούν" οι ερωτήσεις.
     * @param questionNumber αριθμός ερωτήσεων που θα πρέπει να απαντηθούν σε αυτό τον γύρο.
     */
    public RightAnswer(QuestionLibrary questionStore, int questionNumber) {
        super(questionStore,1, 2, questionNumber);
    }

    /**
     * Επιστρέφει το όνομα του τύπου γύρου
     * @return όνομα του τύπου του γύρου.
     */
    @Override
    public String getRoundName(){
        return "Σωστή απάντηση";
    }

    /**
     * Επιστρέφει την περιγραφή του τύπου γύρου.
     * @return περιγραφή του τύπου του γύρου.
     */
    @Override
    public String getRoundDescription(){
        return "Άν ο παίκτης απαντήσει σωστά κερδίζει 1000 πόντους.";
    }


    /**
     * Υπολογίζει και επιστρέφει την αριθμό πόντων που (πιθανόν μπορεί να) κερδίζει ο παίχτης δεδομένης της απάντησης που έδωσε.
     * @param answer Η απάντηση που έδωσε ο παίχτης.
     * @return αριθμός πόντων που αντιστοιχούν στην δοθείσα απάντηση του ορίσματος.
     */
    public int answerQuestion(String answer){
        Question temp = getQuestionInstance(); // Παίρνω αναφορά στην τρέχουσα ερώτηση

       if(temp!=null && temp.isRight(answer)){ // Άν υπάρχουν διαθέσιμες ερωτήσεις και ο παίχτης απάντησε σωστά
           return 1000;
       }
       else {
           return 0;
       }

    }
}
