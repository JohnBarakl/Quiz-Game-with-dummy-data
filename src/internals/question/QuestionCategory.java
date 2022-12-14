package internals.question;

import java.util.HashMap;
import java.util.Stack;

/**
 * Η κλάση {@code QuestionCategory} μοντελοποιεί την έννοια της κατηγορίας ερωτήσεων.
 * Συνεπώς, αποθηκεύει όλες τις ερωτήσεις που ανήκουν σε αυτή την κατηγορία και παρέχει ένα απλό περιβάλλον για πρόσβαση σε αυτές μέσω των εκφωνήσεων τους.
 * Παράλληλα, παρέχει και μεθόδους για επιλογή τυχαίων ερωτήσεων αυτής της κατηγορίας.
 *
 * @author Ioannis Baraklilis
 * @author Alexandros Tsingos
 *
 * @version 2020.11.27
 */


public class QuestionCategory {
    private String categoryName; // Όνομα κατηγορίας.
    private HashMap<String, Question> questionStore; // Αποθηκεύει τις ερωτήσεις, δίνοντας την δυνατότητα να αντιστοιχίσουμε εύκολα την εκφώνηση τους με το αντίστοιχο αντικείμενο που τις διαχειρίζεται.
    private Stack<Question> questionStack; // Αποθηκεύει αντίγραφο των ερωτήσεων που επιτρέπει την εύκολη αφαίρεση των στοιχείων.
    private boolean automaticShuffle; // Αποθηκεύει την επιλογή που δόθηκε ως όρισμα στον κατασκευαστή για αυτόματο "ανακάτεμα".

    /**
     * Τυπικός κατασκευαστής της κλάσης.
     * @param categoryName Όνομα κατηγορίας
     * @param inputQuestions Πίνακας με τις ερωτήσεις που πρέπει να αποθηκευτούν στην κατηγορία
     * @param automaticShuffle κατάσταση αυτόματου "ανακατέματος". true για ενεργοποίηση, false διαφορετικά.
     */
    public QuestionCategory(String categoryName, Question[] inputQuestions, boolean automaticShuffle) {
        this.categoryName = categoryName;

        this.questionStore = new HashMap<>();
        for (Question q : inputQuestions){ // Αντιστοίχηση εκφώνησης ερωτήσεων με το αντίστοιχο αντικείμενο
            questionStore.put(q.getQuestion(), q);
        }

        this.automaticShuffle = automaticShuffle;
        reshuffle(); // Πρέπει αμέσως μετά την δημιουργία να μπορώ να πάρω τυχαίες ερωτήσεις, οπότε κάνω τυχαιοποίηση τους.
    }

    /**
     * Επιστρέφει το όνομα της κατηγορίας
     * @return όνομα της κατηγορίας.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Επιστέφει πίνακα με τα αντίγραφα όλων των ερωτήσεων που ανήκουν στην συγκεκριμένη κατηγορία.
     * @return πίνακα με όλες τις αποθηκευμένες ερωτήσεις της κατηγορίας.
     */
    public Question[] getAllQuestions(){
        Question[] temp = new Question[questionStore.size()];

        int i = 0; // Μετρητής θέσης στον πίνακα temp
        for (Question q : questionStore.values()){ // Εισάγω στον πίνακα μία-μία τις ερωτήσεις που υπάρχουν (ως values) στο HashMap
            temp[i++] = q;
        }

        return temp;
    }

    /**
     * Επιστρέφει την ερώτηση που αντιστοιχεί στην εκφώνηση που δίνεται ως όρισμα (αν υπάρχει τέτοια ερώτηση αντίστοιχη με αυτή την εκφώνηση).
     * Αν δεν υπάρχει επιστρέφει null.
     * @param question Εκφώνηση της ερώτησης.
     * @return Αναφορά σε αντικείμενο ερώτησης που αντιστοιχεί, ή null αν δεν υπάρχει αντίστοιχη αποθηκευμένη.
     */
    public Question getQuestion(String question){
        return questionStore.get(question);
    }

    /**
     * Επιστρέφει τυχαία ερώτηση της κατηγορίας.
     *
     * Αν έχουν ήδη επιστραφεί όλες οι ερωτήσεις τότε:
     *
     * Αν έχει επιτραπεί το αυτόματο "ανακάτεμα" οι ερωτήσεις ξανά-ανακατεύονται αυτόματα.
     * (Οι ερωτήσεις που έχουν ηδη εμφανιστεί ξαναγίνονται διαθέσιμες, τυχαιοποιείται η σειρά τους και επιστρέφεται μία απο αυτές.)
     *
     * Αν δεν έχει επιτραπεί το αυτόματο "ανακάτεμα" οι ερωτήσεις δεν ξανά-ανακατεύονται αυτόματα και επιστρέφεται null!
     * (Θα πρέπει αυτός που διαχειρίζεται την κλάση να καλέσει ο ίδιος και όχι αυτόματα όπως πριν την reshuffle(), ώστε οι ερωτήσεις που έχουν ηδη εμφανιστεί να ξαναγίνουν διαθέσιμες)
     *
     * Παρέχεται έτσι μια προγραμματιστική ευελιξία για αυτόν που διαχειρίζεται την κλάση!
     *
     * Αν δεν έχουν ήδη επιστραφεί όλες οι ερωτήσεις τότε:
     *
     * Επιστρέφεται μια τυχαία ερώτηση της κατηγορίας.
     *
     * @return μία τυχαία ερώτηση ή null
     */
    public Question getRandomQuestion(){
        if (questionStack.size()==0){ // Αν έχουν ήδη επιστραφεί όλες οι ερωτήσεις
            if (automaticShuffle) { // Αν έχει επιτραπεί το αυτόματο "ανακάτεμα"
                reshuffle(); // Κάνω ανακάτεμα
            } else {
                return null; // Επιστρέφεται null
            }
        }

        return questionStack.pop(); // Επιστρέφεται μια τυχαία ερώτηση της κατηγορίας.
    }


    /**
     * Επιστρέφει τον αριθμό των ερωτήσεων που απομένουν για επιστροφή απο την μέθοδο {@code getRandomQuestion()}.
     *
     * Αν το automaticReshuffle == false τότε υπάρχει η περίπτωση να επιστρέφει 0 εαν έχουν "εξαντληθεί" οι ερωτήσεις
     * (μέχρι ο διαχειριστής της κλάσης να έχει κάνει reshuffle()).
     *
     * @return αριθμό των ερωτήσεων που απομένουν για επιστροφή απο την μέθοδο {@code getRandomQuestion()}.
     */
    public int getRemainingRandomQuestions(){
        return questionStack.size();
    }

    /**
     * Υλοποιεί την λειτουργία "ανακατέματος" της κλάσης. Κάθε αναφορά σε "ανακάτεμα" εννοεί κλήση αυτής της μεθόδου.
     * Όλες οι ερωτήσεις που έχουν ηδη εμφανιστεί ξαναγίνονται διαθέσιμες και τυχαιοποιείται η σειρά επιστροφής όλων των ερωτήσεων
     * (επιστροφή μέσω της μεθόδου getRandomQuestion()).
     *
     * @return αναφορά στην κλάση που καλείται με σκοπό την χρήση της σε "αλυσιδωτές" κλήσεις αυτής.
     */
    public QuestionCategory reshuffle(){
        questionStack=new Stack<>(); // Αγνοώ τις ερωτήσεις που δεν έχουν εμφανιστεί για να τις συμπεριλάβω όλες εκ νέου.
        for(Question q: questionStore.values()){ // Εισάγω όλες τις ερωτήσεις στο stack
            questionStack.push(q);
        }

        java.util.Collections.shuffle(questionStack); //Ανακάτεμα - τυχαιοποίηση σειράς ερωτήσεων στην δομή απο την οποία θα επιστραφούν.
        return this;

    }

}
