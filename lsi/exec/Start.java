package tp2.lsi.exec;

import tp2.lsi.data.ActionsBDDImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) throws SQLException {
        ActionsBDDImpl.openConnection();
        ActionsBDDImpl.updatePlanning();
        Menu.SetLanguage();
        login();
    }

    static void login() throws SQLException {
        Scanner input = new Scanner(System.in);
        int userChoice;

        do {
            System.out.println("""
            Welcome to the application

            1. Login as a patient
            2. Login as the psychologist
            3. Register as a new patient
            4. Insert dummy patients for testing
            5. Exit

            Please enter your choice:
            """);
            userChoice = input.nextInt();
        } while (userChoice < 1 || userChoice > 5);

        switch (userChoice) {
            case 1:
                Menu.printMenuPatient();
                break;
            case 2:
                Menu.printMenuPsychologist();
                break;
            case 3:
            case 4:
                if (userChoice == 3) {
                    Menu.registerPatient();
                } else {
                    ActionsBDDImpl.insertDummyPatients();
                }
                login();
                break;
            case 5:
                ActionsBDDImpl.closeConnection();
                System.out.println("Goodbye!");
                break;
        }
    }

}
