package tp2.lsi.exec;

import tp2.lsi.data.ActionsBDDImpl;
import tp2.lsi.data.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Menu {

    static ResourceBundle messages;
    static Patient currentPatient;

    public static void SetLanguage() {
        messages = ResourceBundle.getBundle("tp2.lsi.utils.messages", Locale.forLanguageTag("en-US"));
    }

    private static void loginPsychologist() {
        Scanner Input = new Scanner(System.in);
        String admin;
        String password;

        while (true) {
            System.out.println(messages.getString("login.NomAdmin"));
            admin = Input.nextLine();
            System.out.println(messages.getString("login.PasswordAdmin"));
            password = Input.nextLine();

            if (isValidLogin(admin, password)) {
                break;
            } else {
                System.out.println(messages.getString("login.UserNotFound"));
            }
        }
    }

    private static boolean isValidLogin(String admin, String password) {
        return Objects.equals(admin, "admin") && Objects.equals(password, "admin");
    }


    public static void printMenuPsychologist() throws SQLException {

        loginPsychologist();

        Scanner Input = new Scanner(System.in);

        int choice = 0;
        ActionsBDDImpl actionsBDDImpl = new ActionsBDDImpl(); // Création d'un objet de type ActionsBDDImpl (ou on a implémenté les fonctions)

        while (choice != 9) {
            System.out.println("Welcome to the admin menu\n");

            // Affichage du menu
            for (int i = 1; i <= 9; i++) {
                String key = i == 9 ? "exit" : "menu.psychologist.option" + i;
                System.out.println(i + ". " + messages.getString(key));
            }

            // Récupération du choix de l'utilisateur
            System.out.println("\n" + messages.getString("menu.choice.pick"));
            choice = Input.nextInt();

            while (choice < 1 || choice > 9) {
                System.out.println(messages.getString("menu.choice.tryAgain"));
                choice = Input.nextInt();
            }

            switch (choice) {
                case 1 -> actionsBDDImpl.insertPatient();
                case 2 -> actionsBDDImpl.deletePatient();
                case 3 -> actionsBDDImpl.viewPatients();
                case 4 -> actionsBDDImpl.prendreRendezVousPsychologue();
                case 5 -> actionsBDDImpl.deleteRdv();
                case 6 -> actionsBDDImpl.modifyRdv();
                case 7 -> actionsBDDImpl.consultRdv();
                case 8 -> actionsBDDImpl.seeAppointmentsPsychologue();
                case 9 ->{
                    System.out.println(messages.getString("exit"));
                    ActionsBDDImpl.closeConnection();
                }
            }
        }
    }

    private static void logInPatient() throws SQLException {
        int socialSecurityNumber;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println(messages.getString("login.ss"));
            while (!scanner.hasNextInt()) {
                System.out.println(messages.getString("login.invalidInput"));
                scanner.next(); // Consume non-integer input
            }
            socialSecurityNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (!ActionsBDDImpl.UserFound(socialSecurityNumber)) {
                System.out.println(messages.getString("login.UserNotFound"));
            }
        } while (!ActionsBDDImpl.UserFound(socialSecurityNumber));

        setCurrentPatient(socialSecurityNumber); // Retrieve patient information
    }


    static void setCurrentPatient(int socialSecurityNumber) throws SQLException {
        ResultSet rs = ActionsBDDImpl.getPatient(socialSecurityNumber);
        rs.next();

        // Création d'un objet de type Patient
        currentPatient = new Patient(rs.getInt("securite_sociale"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("Genre"), rs.getString("Annee_De_Naissance"), rs.getString("Reference"));
        // Affichage du message de bienvenue
        System.out.println(messages.getString("login.welcome") + " " + currentPatient.getName() + " " + currentPatient.getFirstName()+"\n");
    }

    public static void printMenuPatient() throws SQLException {
        logInPatient();

        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            // Affichage du menu
            for (int i = 1; i <= 4; i++) {
                String key = i == 4 ? "exit" : "menu.patient.option" + i;
                System.out.println(i + ". " + messages.getString(key));
            }
            System.out.println();

            // Récupération du choix de l'utilisateur
            System.out.println(messages.getString("menu.choice.pick"));
            choice = input.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println(messages.getString("menu.choice.tryAgain"));
                continue;
            }

            switch (choice) {
                case 1 -> ActionsBDDImpl.prendreRendezVous(currentPatient);
                case 2 -> ActionsBDDImpl.seeAppointments(currentPatient);
                case 3 -> Start.login();
                case 4 -> {
                    System.out.println(messages.getString("exit"));
                    ActionsBDDImpl.closeConnection();
                    return;
                }
            }
        }
    }

    public static void registerPatient() throws SQLException {
        ActionsBDDImpl actionsBDDImpl = new ActionsBDDImpl();
        actionsBDDImpl.insertPatient();
    }

}
