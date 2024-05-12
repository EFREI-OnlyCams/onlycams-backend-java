package tp2.lsi.data;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.sql.Date;

public class ActionsBDDImpl{
    private static Connection c;

    public static void openConnection() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/psychologue";
            c = DriverManager.getConnection(url, "adm", "adm");
            System.out.println("======================================");
            System.out.println("Successfully connected to the database");
            System.out.println("=======================================\n");
        } catch (SQLException e) {
            System.out.println("======================================");
            System.err.println("Error connecting to the database: " + e.getMessage());
            System.out.println("======================================");
        }
    }

    public static void closeConnection() throws SQLException {
        c.close();
        System.out.println("Connection closed");
    }

    public static void insertDummyPatients() throws SQLException {
        PreparedStatement stmtCheck = c.prepareStatement("SELECT COUNT(*) AS count FROM patient WHERE securite_sociale = ?");
        PreparedStatement stmtInsert = c.prepareStatement("CALL insert_patient(?, ?, ?, ?, ?, ?)");

        // Données factices des patients à insérer
        Object[][] patientsData = {
                {123456789, "John", "Doe", "Male", "1980-01-01", "Reference 1"},
                {987654321, "Jane", "Smith", "Female", "1990-05-15", "Reference 2"},
                {456789123, "Michael", "Johnson", "Male", "1975-09-20", "Reference 3"},
                {654321987, "Emily", "Brown", "Female", "1988-03-10", "Reference 4"},
                {789123456, "William", "Davis", "Male", "1995-12-25", "Reference 5"}
        };

        // Insérer les données des 5 patients dans la base de données
        for (Object[] data : patientsData) {
            int socialSecurityNumber = (int) data[0];

            // Vérifier si le patient existe déjà
            stmtCheck.setInt(1, socialSecurityNumber);
            ResultSet rs = stmtCheck.executeQuery();
            rs.next();
            int count = rs.getInt("count");

            if (count == 0) {
                // Si le patient n'existe pas déjà, l'insérer dans la base de données
                stmtInsert.setInt(1, socialSecurityNumber); // Numéro de sécurité sociale
                stmtInsert.setString(2, (String) data[1]); // Prénom
                stmtInsert.setString(3, (String) data[2]); // Nom
                stmtInsert.setString(4, (String) data[3]); // Genre
                stmtInsert.setString(5, (String) data[4]); // Année de naissance
                stmtInsert.setString(6, (String) data[5]); // Référence

                stmtInsert.execute();
                System.out.println("Inserted patient : " + socialSecurityNumber);
            } else {
                System.out.println("Patient number " + socialSecurityNumber + " already exists, skipping insertion.");
            }
        }

        System.out.println("\nDummy patients data insertion completed.\n");
    }


    public void insertProfessions(int SS, int numProfession) throws SQLException {
        CallableStatement statement;
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < numProfession; i++) {
            System.out.println("What is your profession number " + (i + 1) + " ?");
            String profession = input.nextLine();

            // Validate start date format
            String startDate;
            LocalDate parsedStartDate = null;
            while (parsedStartDate == null) {
                System.out.println("When did you start this profession (yyyy-mm-dd)? ");
                startDate = input.nextLine();
                try {
                    parsedStartDate = LocalDate.parse(startDate);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
                }
            }
            // Insert profession into database
            try {
                statement = c.prepareCall("CALL fillProfessions(?, ?)");
                statement.setString(1, profession);
                statement.registerOutParameter(2, Types.INTEGER);
                statement.execute();
                int idProfession = statement.getInt(2);

                // Insert profession and start date into exerce table
                PreparedStatement stmt = c.prepareStatement("CALL fillExerce(?,?,?)");
                stmt.setInt(1, SS);
                stmt.setInt(2, idProfession);
                stmt.setDate(3, Date.valueOf(parsedStartDate));
                stmt.execute();
            } catch (SQLException e) {
                System.out.println("An error occurred while inserting profession. Please try again.");
                // Rollback any changes made in case of an error
                c.rollback();
                return; // Exit the method
            }
        }
    }


    public void insertPatient() throws SQLException {
        Scanner sc = new Scanner(System.in);

        int SS;
        String nom;
        String prenom;
        String genre;
        String AnneeDeNaissance;
        String Reference;

        // Get patient details
        System.out.println("Patient's social security number: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid social security number: ");
            sc.next();
        }
        SS = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (UserFound(SS)) {
            do {
                System.out.println("Existing patient. Please enter another social security number: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid social security number: ");
                    sc.next();
                }
                SS = sc.nextInt();
                sc.nextLine(); // Consume newline
            } while (UserFound(SS));
        }

        System.out.println("Patient family name: ");
        nom = sc.nextLine();

        System.out.println("Patient first name: ");
        prenom = sc.nextLine();

        System.out.println("Patient gender: ");
        genre = sc.nextLine();

        System.out.println("Patient year of birth (yyyy-mm-dd): ");
        LocalDate parsedDateOfBirth = null;
        do {
            String input = sc.nextLine();
            try {
                parsedDateOfBirth = LocalDate.parse(input);
                if (parsedDateOfBirth.getMonthValue() < 1 || parsedDateOfBirth.getMonthValue() > 12 || parsedDateOfBirth.getDayOfMonth() > parsedDateOfBirth.lengthOfMonth()) {
                    throw new DateTimeParseException("Invalid date", input, 0);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please enter the year of birth in the format yyyy-mm-dd: ");
            }
        } while (parsedDateOfBirth == null);

        AnneeDeNaissance = parsedDateOfBirth.toString();


        System.out.println("Patient reference: ");
        Reference = sc.nextLine();

        // Insert patient into database
        try {
            PreparedStatement stmt = c.prepareStatement("CALL insert_patient(?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, SS);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, genre);
            stmt.setString(5, AnneeDeNaissance);
            stmt.setString(6, Reference);
            stmt.execute();

            // Insert patient's professions
            System.out.println("How many professions do you have: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number: ");
                sc.next();
            }
            int numProfession = sc.nextInt();
            sc.nextLine(); // Consume newline
            insertProfessions(SS, numProfession);

            System.out.println("Successfully added patient.");
        } catch (SQLException e) {
            System.out.println("An error occurred while inserting patient. Please try again.");
            // Rollback any changes made in case of an error
            c.rollback();
        }
    }


    public void deletePatient() throws SQLException {
        int SS;
        Scanner sc= new Scanner(System.in); 

        System.out.println("Securite Sociale du patient : ");
        SS = sc.nextInt(); 
        sc.nextLine(); 

        if(!UserFound(SS)){
            do{
                System.out.println("Patient non existant, veuillez saisir un autre numéro de sécurité sociale : ");
                SS = sc.nextInt(); 
                sc.nextLine();
            }while(!UserFound(SS));
        }

        Statement stmt = c.createStatement(); //création d'un statement pour exécuter les requêtes

        stmt.execute("CALL Delete_patient ("+SS+")"); //exécution de la requête

        System.out.println("Patient supprimé avec succès");

    }

    public static boolean UserFound(int securiteSociale) throws SQLException {
        boolean userFound = false; // Initialize to false
        CallableStatement callableStatement = c.prepareCall("{call UserFound(?, ?)}");
        callableStatement.registerOutParameter(1, Types.BOOLEAN); // Register the OUT parameter
        callableStatement.setInt(2, securiteSociale); // Set the IN parameter securiteSociale
        callableStatement.execute(); // Execute the stored procedure
        userFound = callableStatement.getBoolean(1); // Retrieve the result from the OUT parameter
        callableStatement.close(); // Close the statement
        return userFound;

    }

    public static boolean has3Appointments(int securiteSociale, Date rdvDate) throws SQLException {
        boolean has3appointments = false; // Initialize to false
        CallableStatement callableStatement = c.prepareCall("{call has3dailyAppointments(?, ?, ?)}");
        callableStatement.setInt(1, securiteSociale); // Set the IN parameter securiteSociale
        callableStatement.setDate(2, rdvDate); // Set the IN parameter securiteSociale
        callableStatement.registerOutParameter(3, Types.BOOLEAN); // Register the OUT parameter
        callableStatement.execute(); // Execute the stored procedure
        has3appointments = callableStatement.getBoolean(3); // Retrieve the result from the OUT parameter
        callableStatement.close(); // Close the statement
        return has3appointments;

    }

    public static ResultSet getPatient(int securiteSociale) throws SQLException {
        CallableStatement callableStatement = c.prepareCall("{call getPatient(?)}");
        callableStatement.setInt(1, securiteSociale); // Set the IN parameter securiteSociale
        return callableStatement.executeQuery(); // Execute the stored procedure and return the result
    }

    public void viewPatients() throws SQLException {
        Statement stmt = c.createStatement(); //création d'un statement pour exécuter les requêtes
        ResultSet rs = stmt.executeQuery("SELECT * FROM patient"); //exécution de la requête
        while (rs.next()) {
            System.out.println("Securite Sociale: " + rs.getInt("securite_sociale") + " Prenom: " + rs.getString("Prenom") + " Nom: " + rs.getString("Nom") + " Genre: " + rs.getString("Genre") + " Annee de naissance: " + rs.getString("Annee_De_Naissance") + " Reference: " + rs.getString("Reference"));
            Statement stmt2 = c.createStatement(); //création d'un statement pour exécuter les requêtes
            ResultSet rs2 = stmt2.executeQuery("CALL getProfessions(" + rs.getInt("securite_sociale") +")"); //exécution de la requête
            System.out.println("Professions : ");
            while(rs2.next()){
                System.out.println(rs2.getString("Profession") + " on " + rs2.getString("Date_de_debut"));
            }
        }
    }

    public static void prendreRendezVous(Patient currentPatient) throws SQLException {
        Date chosenDate = chooseAppointmentDate();
        if(has3Appointments(currentPatient.getSocialSecurityNumber(),chosenDate)){
            System.out.println("Patient already has 3 appointments on " + chosenDate);
        }else {
            Time chosenTime = chooseAppointmentTime(chosenDate);
            bookAppointment(currentPatient, chosenDate, chosenTime);

        }
    }

    private static Date chooseAppointmentDate() throws SQLException {
        List<Date> availableDates = getAvailableDates();
        for(int i = 0; i < availableDates.size(); i++){
            System.out.println( ++i +" - "+ availableDates.get(--i));
        }
        int choice = getUserChoice("Enter the number corresponding to your chosen date: ", availableDates.size());
        return availableDates.get(choice-1);
    }

    private static Time chooseAppointmentTime(Date chosenDate) throws SQLException {
        List<Time> availableTimes = getAvailableTimes(chosenDate);
        for(int i = 0; i < availableTimes.size(); i++){
            System.out.println( ++i +" - "+ availableTimes.get(--i));
        }
        int choice = getUserChoice("Enter the number corresponding to your chosen time: ", availableTimes.size());
        return availableTimes.get(choice - 1);
    }

    private static int getUserChoice(String prompt, int maxChoice) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume non-integer input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice < 1 || choice > maxChoice) {
                System.out.println("Invalid choice. Please enter a number within the range.");
            }
        } while (choice < 1 || choice > maxChoice);
        return choice;
    }


    private static List<Date> getAvailableDates() throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("CALL top20DaysPlanning");
        List<Date> availableDates = new ArrayList<>();
        while (rs.next()) {
            availableDates.add(rs.getDate("DatePart"));
        }
        return availableDates;
    }

    private static List<Time> getAvailableTimes(Date chosenDate) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("CALL timeOfDate(?)");
        pstmt.setDate(1, chosenDate);
        ResultSet rs = pstmt.executeQuery();
        List<Time> availableTimes = new ArrayList<>();
        while (rs.next()) {
            availableTimes.add(rs.getTime("Time(Date_et_Heure)"));
        }
        return availableTimes;
    }

    private static void bookAppointment(Patient currentPatient, Date chosenDate, Time chosenTime) throws SQLException {
        CallableStatement cstmt = c.prepareCall("{CALL takeAppointmentDateandTime(?, ?, ?)}");
        cstmt.setDate(1, chosenDate);
        cstmt.setTime(2, chosenTime);
        cstmt.registerOutParameter(3, Types.INTEGER);
        cstmt.execute();
        int planningId = cstmt.getInt(3);
        PreparedStatement pstmt = c.prepareStatement("CALL prendre_RendezVous(?,?)");
        pstmt.setInt(1, currentPatient.getSocialSecurityNumber());
        pstmt.setInt(2, planningId);
        pstmt.executeQuery();
        System.out.println("The price will be decided by the psy during the appointment");
    }


    public static void updatePlanning() throws SQLException{
        Statement stmt = c.createStatement(); //création d'un statement pour exécuter les requêtes
        stmt.execute("CALL updatePlanning()"); //exécution de la requête

    }

    public static void seeAppointments(Patient  currentPatient) throws SQLException{
        CallableStatement callableStatement = c.prepareCall("{call seeAppointments(?)}");
        callableStatement.setInt(1, currentPatient.getSocialSecurityNumber()); // Set the IN parameter securiteSociale

        ResultSet rs = callableStatement.executeQuery(); // Execute the stored procedure and return the result
        boolean empty = true;
        while (rs.next()) {
            empty = false;
            Date dateSQL = rs.getDate("Date_et_Heure");
            LocalDate date = dateSQL.toLocalDate(); // Current date and time
            LocalDate today = LocalDate.now(); // Current date

            if (date != null && date.isBefore(today)) {
                System.out.println("Past appointment on :  "+ rs.getDate("Date_et_Heure") + " at " + rs.getTime("Date_et_Heure"));
            }

            else if (date != null && date.isAfter(today)){
                System.out.println("Upcoming appointment on :  "+ rs.getDate("Date_et_Heure") + " at " + rs.getTime("Date_et_Heure"));
            }else{
                System.out.println(" Appointment today ! :  "+ rs.getDate("Date_et_Heure") + " at " + rs.getTime("Date_et_Heure"));

            }
        }

        if(empty){
            System.out.println("There are no appointments taken");
        }

    }

    public void prendreRendezVousPsychologue() throws SQLException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Patient's social security number : ");

        int socialSecurityNumber = sc.nextInt();
        sc.nextLine();

        if(!UserFound(socialSecurityNumber)) {
            do {
                System.out.println("Patient's social security number : ");

                socialSecurityNumber = sc.nextInt();
                sc.nextLine();
            } while (!UserFound(socialSecurityNumber));

        }

        ResultSet rs = ActionsBDDImpl.getPatient(socialSecurityNumber);
        rs.next();

        Patient newPatient = new Patient(rs.getInt("securite_sociale"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("Genre"), rs.getString("Annee_De_Naissance"), rs.getString("Reference"));

        prendreRendezVous(newPatient);

    }

    public void deleteRdv() throws SQLException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Patient's social security number : ");

        int socialSecurityNumber = sc.nextInt();
        sc.nextLine();

        ResultSet rs = ActionsBDDImpl.getPatient(socialSecurityNumber);
        rs.next();

        Patient currentPatient = new Patient(rs.getInt("securite_sociale"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("Genre"), rs.getString("Annee_De_Naissance"), rs.getString("Reference"));

        System.out.println("Which Appointment would you like to delete  ? ");

        List<String> appointmentDates = getAppointments(currentPatient);

        if(appointmentDates.size() == 0){
            System.out.println("There are no appointments ");
            return;
        }

        for(int i = 0; i < appointmentDates.size(); i++){
            System.out.println(i+ " - " + appointmentDates.get(i));
        }
        int choiceDate = sc.nextInt();
        sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the string into a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDates.get(choiceDate), formatter);

        // Extract date and time parts
        java.sql.Date datePart = java.sql.Date.valueOf(dateTime.toLocalDate());
        java.sql.Time timePart = java.sql.Time.valueOf(dateTime.toLocalTime());

        CallableStatement callableStatement = c.prepareCall("{call Delete_Rdv(?,?,?)}");
        callableStatement.setInt(1, currentPatient.getSocialSecurityNumber());
        callableStatement.setDate(2, datePart); // Set the IN parameter securiteSociale
        callableStatement.setTime(3, timePart); // Set the IN parameter securiteSociale

        callableStatement.executeQuery();

        System.out.println("Successfully deleted appointment ! ");
    }


    private static List<String> getAppointments(Patient currentPatient) throws SQLException {
        CallableStatement callableStatement = c.prepareCall("{call seeAppointments(?)}");
        callableStatement.setInt(1, currentPatient.getSocialSecurityNumber()); // Set the IN parameter securiteSociale

        List<String> appointmentDates = new ArrayList<>();;
        ResultSet rs = callableStatement.executeQuery(); // Execute the stored procedure and return the result
        while (rs.next()) {
            assert appointmentDates != null;
            appointmentDates.add(rs.getString("Date_et_Heure"));
        }
        
        return appointmentDates;
    }


    public void modifyRdv() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Patient's social security number: ");

        int socialSecurityNumber = sc.nextInt();
        sc.nextLine();

        ResultSet rs = ActionsBDDImpl.getPatient(socialSecurityNumber);
        rs.next();

        Patient currentPatient = new Patient(rs.getInt("securite_sociale"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("Genre"), rs.getString("Annee_De_Naissance"), rs.getString("Reference"));

        System.out.println("Which Appointment would you like to modify? ");

        List<String> appointmentDates = getAppointments(currentPatient);

        if (appointmentDates.isEmpty()) {
            System.out.println("There are no appointments ");
            return;
        }

        for (int i = 0; i < appointmentDates.size(); i++) {
            System.out.println(i + " - " + appointmentDates.get(i));
        }
        int choiceDate = sc.nextInt();
        sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDates.get(choiceDate), formatter);
        java.sql.Date datePart = java.sql.Date.valueOf(dateTime.toLocalDate());
        java.sql.Time timePart = java.sql.Time.valueOf(dateTime.toLocalTime());



        boolean continueModifying = true;

        while (continueModifying) {

            CallableStatement callableStatement = c.prepareCall("{call getRdv(?,?,?)}");
            callableStatement.setInt(1, currentPatient.getSocialSecurityNumber());
            callableStatement.setDate(2, datePart); // Set the IN parameter securiteSociale
            callableStatement.setTime(3, timePart); // Set the IN parameter securiteSociale

            ResultSet rs1 = callableStatement.executeQuery();

            rs1.next();

            int Id_planning = rs1.getInt("Id_Planning_Apparait");

            System.out.println("1.Prix : " + rs1.getInt("Prix") +  "\n2.Mode_de_reglement :  " + rs1.getString("Mode_de_reglement") +
                    "\n3.Type_de_consultation : "+ rs1.getString("Type_de_consultation") + "\n4.Nombre_de_patients : "+rs1.getString("Nombre_de_patients") +"\n5.Comportements : "+ rs1.getString("Comportements") +
                    "\n6.Postures : "+rs1.getString("Postures")+ "\n7.Symptomes : "+rs1.getString("Symptomes"));

            System.out.println("Choose which value you would like to change : ");

            int columnChoice = sc.nextInt();
            sc.nextLine();

            switch (columnChoice) {
                case 1:
                    // Modify Prix
                    modifyPrix(Id_planning,sc);
                    break;
                case 2:
                    // Modify Mode de reglement
                    modifyModeDeReglement(Id_planning, sc);
                    break;
                case 3:
                    // Modify Type de consultation
                    modifyTypeDeConsultation(Id_planning, sc);
                    break;
                case 4:
                    // Modify Nombre de patients
                    modifyNombreDePatients(Id_planning, sc);
                    break;
                case 5:
                    // Modify Comportements
                    modifyComportements(Id_planning,sc);
                    break;
                case 6:
                    // Modify Postures
                    modifyPostures(Id_planning, sc);
                    break;
                case 7:
                    // Modify Symptomes
                    modifySymptomes(Id_planning, sc);
                    break;
                case 8:
                    // Quit modifying
                    continueModifying = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }


            if (continueModifying) {
                System.out.println("Do you want to modify another column? (yes/no)");
                String choice = sc.nextLine();
                if (!choice.equalsIgnoreCase("yes")) {
                    continueModifying = false;
                }
            }
        }
    }

    // Modify Prix
    private void modifyPrix(int Id_planning,Scanner sc) throws SQLException {
        System.out.println("Enter new Prix: ");
        int newPrix = sc.nextInt();
        sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyPrix(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setInt(2, newPrix);
        callableStatement.execute();
    }

    // Modify Mode de reglement
    private void modifyModeDeReglement(int Id_planning, Scanner sc) throws SQLException {
        System.out.println("Enter new Mode de reglement: ");
        String newModeDeReglement = sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyModeDeReglement(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setString(2, newModeDeReglement);
        callableStatement.execute();
    }

    // Modify Type de consultation
    private void modifyTypeDeConsultation(int Id_planning, Scanner sc) throws SQLException {
        System.out.println("Enter new Type de consultation: ");
        String newTypeDeConsultation = sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyTypeDeConsultation(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setString(2, newTypeDeConsultation);
        callableStatement.execute();
    }

    // Modify Nombre de patients
    private void modifyNombreDePatients(int Id_planning, Scanner sc) throws SQLException {
        System.out.println("Enter new Nombre de patients: ");
        int newNombreDePatients = sc.nextInt();
        sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyNombreDePatients(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setInt(2, newNombreDePatients);
        callableStatement.execute();
    }

    // Modify Comportements
    private void modifyComportements(int Id_planning,  Scanner sc) throws SQLException {
        System.out.println("Enter new Comportements: ");
        String newComportements = sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyComportements(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setString(2, newComportements);
        callableStatement.execute();
    }

    // Modify Postures
    private void modifyPostures(int Id_planning,  Scanner sc) throws SQLException {
        System.out.println("Enter new Postures: ");
        String newPostures = sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifyPostures(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setString(2, newPostures);
        callableStatement.execute();
    }

    // Modify Symptomes
    private void modifySymptomes(int Id_planning, Scanner sc) throws SQLException {
        System.out.println("Enter new Symptomes: ");
        String newSymptomes = sc.nextLine();

        CallableStatement callableStatement = c.prepareCall("{call modifySymptomes(?, ?)}");
        callableStatement.setInt(1, Id_planning);
        callableStatement.setString(2, newSymptomes);
        callableStatement.execute();
    }

    private void weeklyAppointments(LocalDate startDate, LocalDate endDate) throws SQLException {
        CallableStatement callableStatement = c.prepareCall("{call weeklyAppointments(?, ?)}");
        callableStatement.setDate(1, Date.valueOf(startDate));
        callableStatement.setDate(2, Date.valueOf(endDate));
        ResultSet rs = callableStatement.executeQuery();
        boolean hasAppointments = false;

        while(rs.next()){
            hasAppointments = true;

            System.out.println("- "+ rs.getDate("Date_et_Heure") + " at " + rs.getTime("Date_et_Heure") + " with " + rs.getString("Nom") + " " +rs.getString("Prenom"));
        }

        if (!hasAppointments) {
            System.out.println("There are no appointments for that week");
        }
    }

    private void dailyAppointments(LocalDate selectedDate) throws SQLException {
        CallableStatement callableStatement = c.prepareCall("{call dailyAppointments(?)}");
        callableStatement.setDate(1, Date.valueOf(selectedDate));
        ResultSet rs = callableStatement.executeQuery();
        boolean hasAppointments = false;

        while (rs.next()) {
            hasAppointments = true;

            System.out.println("- " + rs.getDate("Date_et_Heure") + " at " + rs.getTime("Date_et_Heure") + " with " + rs.getString("Nom") + " " + rs.getString("Prenom"));
        }

        if (!hasAppointments) {
            System.out.println("There are no appointments for " + selectedDate);
        }
    }




    public void consultRdv() throws SQLException {
        Scanner sc = new Scanner(System.in);
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDate selectedDate = null;

        System.out.println("Choose consultation type:");
        System.out.println("1. Weekly");
        System.out.println("2. Daily");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            // Weekly consultation
            System.out.println("Choose week:");
            for (int i = 1; i <= 5; i++) {
                startDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).plusDays((i - 1) * 7);
                endDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SATURDAY)).plusDays((i - 1) * 7);
                System.out.println("Week " + i + ": " + startDate + " to " + endDate);
            }
            System.out.print("Enter the week number (1-5): ");
            int weekChoice = sc.nextInt();
            if (weekChoice >= 1 && weekChoice <= 5) {
                startDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).plusDays((weekChoice - 1) * 7);
                endDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SATURDAY)).plusDays((weekChoice - 1) * 7);
            } else {
                System.out.println("Invalid week choice.");
            }

            weeklyAppointments(startDate, endDate);

        } else if (choice == 2) {
            // Daily consultation
            System.out.println("Choose the day (1-20) for the next 20 days:");
            for (int i = 1; i <= 20; i++) {
                System.out.println(i+"." + LocalDate.now().plusDays(i - 1));
            }

            int dayChoice = sc.nextInt();
            if (dayChoice >= 1 && dayChoice <= 20) {
                selectedDate = LocalDate.now().plusDays(dayChoice - 1);
            } else {
                System.out.println("Invalid day choice.");
            }

            dailyAppointments(selectedDate);
        } else {
            System.out.println("Invalid choice.");
        }

    }

    public void seeAppointmentsPsychologue() throws SQLException {

        Scanner sc= new Scanner(System.in);
        System.out.println("Patient's social security number : ");

        int socialSecurityNumber = sc.nextInt();
        sc.nextLine();

        if(!UserFound(socialSecurityNumber)) {
            do {
                System.out.println("Patient's social security number : ");

                socialSecurityNumber = sc.nextInt();
                sc.nextLine();
            } while (!UserFound(socialSecurityNumber));

        }

        ResultSet rs = ActionsBDDImpl.getPatient(socialSecurityNumber);
        rs.next();

        Patient newPatient = new Patient(rs.getInt("securite_sociale"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("Genre"), rs.getString("Annee_De_Naissance"), rs.getString("Reference"));

        seeAppointments(newPatient);
    }


}
