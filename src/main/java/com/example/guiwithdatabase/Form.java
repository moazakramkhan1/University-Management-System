package com.example.guiwithdatabase;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.*;

public class Form extends Application {

    public static final String URL = "jdbc:mysql://localhost:3306/universitydatabase";
    public static final String user = "root";
    public static final String Password = "mak23";
    Connection connection = DriverManager.getConnection(URL, user, Password);
    Statement statement = connection.createStatement();
    public Form() throws SQLException {
    }

    //Start Function
    @Override
    public void start(Stage primarystage) throws IOException {

        primarystage.setTitle("Login Form");

        // Create the GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Username label and field
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        // Password label and field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Create Account button
        Button signUp = new Button("Sign up");
        signUp.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                // Save the information to the file
                try {
                    saveToEmployeesFile(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                clearFields(usernameField, passwordField);
                showAlert("Account created successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Please enter both username and password.", Alert.AlertType.ERROR);
            }
        });
        gridPane.add(signUp, 0, 2);

        // Sign In button
        Button signInButton = new Button("Sign In");
        signInButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    if (validateLogin(username, password)) {
                        clearFields(usernameField, passwordField);
                        showAlert("Login successful", Alert.AlertType.INFORMATION);
                        showActionsScreen(primarystage);
                    } else {
                        showAlert("Credentials do not match with any account", Alert.AlertType.ERROR);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                showAlert("Please enter both username and password.", Alert.AlertType.ERROR);
            }
        });
        gridPane.add(signInButton, 1, 2);

        Scene scene = new Scene(gridPane, 400, 350);
        primarystage.setScene(scene);
        primarystage.show();
    }

    private boolean validateLogin(String username, String password) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
        while (resultSet.next()) {
            if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                return true;
            } else {
                System.out.println("no account found");
            }
        }
        return false;
    }

    private void showActionsScreen(Stage primaryStage) {
        primaryStage.setTitle("Actions");

        // Create the VBox layout
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(25, 25, 25, 25));

        // Add PCs button
        Button addPcsButton = new Button("PCs");
        addPcsButton.setOnAction(e -> addPCAction(primaryStage));
        vbox.getChildren().add(addPcsButton);

//         Lab button
        Button addLabButton = new Button("Labs");
        addLabButton.setOnAction(e -> addLabAction(primaryStage));
        vbox.getChildren().add(addLabButton);
        //Department Button
        Button addDepartmentsButton = new Button("Departments");
        addDepartmentsButton.setOnAction(e -> addDepartments(primaryStage));
        vbox.getChildren().add(addDepartmentsButton);

        // Campus button
        Button addCampusButton = new Button("Campuses");
        addCampusButton.setOnAction(e -> addCampus(primaryStage));
        vbox.getChildren().add(addCampusButton);

        //update employee info button
       Button updatebutton1=new Button("updateEmployee");
       updatebutton1.setOnAction(e->updateEmployeeinfo(primaryStage));
       vbox.getChildren().add(updatebutton1);

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setTitle("Login Form");
            try {
                start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.show();
        });
        vbox.getChildren().add(logoutButton);

        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //    //Each Option Menus
    public void addPCAction(Stage stage) {
        Button button = new Button("Add PC");
        button.setAlignment(Pos.CENTER_LEFT);
        Button button1 = new Button("Search PC");
        button.setAlignment(Pos.CENTER_RIGHT);
        Button button2 = new Button("Back");
        button2.setAlignment(Pos.BOTTOM_LEFT);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        Scene scene = new Scene(hBox, 300, 300);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(f -> {

            TextField companyName = new TextField();
            TextField id = new TextField();
            TextField ram = new TextField();
            TextField diskSize = new TextField();
            TextField screenSize = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(5);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));
            //back button
            Button button3 = new Button("back");
            button3.setAlignment(Pos.BOTTOM_LEFT);
            gridPane.add(button3, 0, 5);
            button3.setOnAction(e -> addPCAction(stage));

            Label label1 = new Label("Company:");
            gridPane.add(label1, 0, 0);
            gridPane.add(companyName, 1, 0);

            Label label2 = new Label("ID:");
            gridPane.add(label2, 0, 1);
            gridPane.add(id, 1, 1);

            Label label3 = new Label("RAM:");
            gridPane.add(label3, 0, 2);
            gridPane.add(ram, 1, 2);

            Label label4 = new Label("Disk Size:");
            gridPane.add(label4, 0, 3);
            gridPane.add(diskSize, 1, 3);

            Label label5 = new Label("Screen Size:");
            gridPane.add(label5, 0, 4);
            gridPane.add(screenSize, 1, 4);

            gridPane.add(button, 1, 5);
            button.setOnAction(e -> {
                if (!companyName.getText().isEmpty() && !id.getText().isEmpty() && !ram.getText().isEmpty() && !diskSize.getText().isEmpty() && !screenSize.getText().isEmpty()) {
                    String sql = "INSERT INTO pc (companyname,id,ram,disksize,screensize) VALUES (?, ?,?,?,?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, companyName.getText());
                        statement.setString(2, id.getText());
                        statement.setInt(3, Integer.parseInt(ram.getText()));
                        statement.setInt(4, Integer.parseInt(diskSize.getText()));
                        statement.setInt(5, Integer.parseInt(screenSize.getText()));

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Failed to insert data.");
                        }
                        clearPc(companyName, id, ram, diskSize, screenSize);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    showAlert("please fill all the fields", Alert.AlertType.ERROR);
                }
                Scene scene1 = new Scene(gridPane, 300, 300);
                stage.setScene(scene1);
                stage.show();
            });
            Scene scene1 = new Scene(gridPane, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });


        button1.setOnAction(a -> {
            GridPane gridPane1 = new GridPane();
            GridPane gridPane2 = new GridPane();
            gridPane1.setAlignment(Pos.CENTER);
            gridPane1.setHgap(5);
            gridPane1.setVgap(10);
            gridPane1.setPadding(new Insets(25, 25, 25, 25));
            TextField companyName = new TextField();
            TextField id = new TextField();
            TextField ram = new TextField();
            TextField diskSize = new TextField();
            TextField screenSize = new TextField();
            Label label = new Label("enter id");
            Button search = new Button("Search");
            label.setAlignment(Pos.CENTER);
            TextField textField = new TextField();
            gridPane1.add(label, 0, 0);
            gridPane1.add(textField, 0, 1);
            gridPane1.add(search, 0, 2);
            //back button
            Button button3 = new Button("back");
            button3.setAlignment(Pos.BOTTOM_LEFT);
            gridPane1.add(button3, 0, 6);
            button3.setOnAction(e -> addPCAction(stage));
            search.setOnAction(r -> {
                String s = textField.getText();
                String id1;
                String cn;
                int ram1;
                int disksize;
                int screensize;
                if (!textField.getText().isEmpty()) {
                    try {
                        String sql = "SELECT * FROM pc WHERE id = ?";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, s);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            id1 = resultSet.getString("id");
                            cn = resultSet.getString("companyname");
                            ram1 = resultSet.getInt("ram");
                            disksize = resultSet.getInt("disksize");
                            screensize = resultSet.getInt("screensize");
                            Label label1 = new Label("Company:");
                            gridPane2.add(label1, 0, 0);
                            gridPane2.add(companyName, 1, 0);
                            companyName.setText(cn);


                            Label label2 = new Label("id:");
                            gridPane2.add(label2, 0, 1);
                            gridPane2.add(id, 1, 1);
                            id.setText(id1);


                            Label label3 = new Label("RAM:");
                            gridPane2.add(label3, 0, 2);
                            gridPane2.add(ram, 1, 2);
                            ram.setText(String.valueOf(ram1));


                            Label label4 = new Label("Disk Size:");
                            gridPane2.add(label4, 0, 3);
                            gridPane2.add(diskSize, 1, 3);
                            diskSize.setText(String.valueOf(disksize));


                            Label label5 = new Label("Screen Size:");
                            gridPane2.add(label5, 0, 4);
                            gridPane2.add(screenSize, 1, 4);
                            screenSize.setText(String.valueOf(screensize));

                            button3.setAlignment(Pos.BOTTOM_LEFT);
                            gridPane2.add(button3, 0, 5);
                            button3.setOnAction(e -> addPCAction(stage));
                        } else {
                            showAlert("Pc does not exist", Alert.AlertType.ERROR);
                            gridPane2.add(button2, 0, 5);
                            button2.setOnAction(e -> addPCAction(stage));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    gridPane2.add(button2, 0, 5);
                    button2.setOnAction(e -> addPCAction(stage));
//
                } else {
                    showAlert("No id added", Alert.AlertType.ERROR);
                }

                Scene scene2 = new Scene(gridPane2, 300, 300);
                stage.setScene(scene2);
                stage.show();
            });

            Scene scene1 = new Scene(gridPane1, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        //back button
        button2.setOnAction(e -> showActionsScreen(stage));
    }


    public void addLabAction(Stage stage) {
        Button button = new Button("Add Lab");
        button.setAlignment(Pos.CENTER_LEFT);
        Button button1 = new Button("Search Lab");
        button1.setAlignment(Pos.CENTER_RIGHT);
        Button button3 = new Button("back");
        button3.setAlignment(Pos.BOTTOM_LEFT);
        Button back = new Button("Back");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button3);
        Scene scene = new Scene(hBox, 300, 300);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(actionEvent -> {
            TextField LabInchargeName = new TextField();
            TextField LabName = new TextField();
            TextField hasProjector = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(5);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));
            Button button5 = new Button("back");
            button5.setAlignment(Pos.BOTTOM_LEFT);
            gridPane.add(button5, 0, 5);
            button5.setOnAction(e -> addLabAction(stage));

            Label label1 = new Label("LabIncharge:");
            gridPane.add(label1, 0, 0);
            gridPane.add(LabInchargeName, 1, 0);

            Label label2 = new Label("LabName");
            gridPane.add(label2, 0, 1);
            gridPane.add(LabName, 1, 1);

            Label label3 = new Label("ProjectorStatus:");
            gridPane.add(label3, 0, 2);
            gridPane.add(hasProjector, 1, 2);

            Button button4 = new Button("save");
            gridPane.add(button4, 1, 5);
            button4.setOnAction(e -> {
                if (!LabInchargeName.getText().isEmpty() && !LabName.getText().isEmpty() && !hasProjector.getText().isEmpty()) {
                    String sql = "INSERT INTO lab (staffname,labname,projectorstatus) VALUES (?, ?,?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, LabInchargeName.getText());
                        statement.setString(2, LabName.getText());
                        statement.setString(3, hasProjector.getText());

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Failed to insert data.");
                        }
                        clearLab(LabInchargeName, LabName, hasProjector);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    showAlert("please fill all the fields", Alert.AlertType.ERROR);
                }
            });

            Scene scene1 = new Scene(gridPane, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        button1.setOnAction(a -> {
            GridPane gridPane1 = new GridPane();
            GridPane gridPane2 = new GridPane();
            gridPane1.setAlignment(Pos.CENTER);
            gridPane1.setHgap(5);
            gridPane1.setVgap(10);
            gridPane1.setPadding(new Insets(25, 25, 25, 25));
            button3.setAlignment(Pos.BOTTOM_LEFT);
            gridPane1.add(button3, 0, 3);
            button3.setOnAction(e -> addLabAction(stage));

            TextField LabInchargeName = new TextField();
            TextField LabName = new TextField();
            TextField hasProjector = new TextField();
            Label label = new Label("enter LabName");
            Button search = new Button("Search");
            label.setAlignment(Pos.CENTER);
            TextField textField = new TextField();
            gridPane1.add(label, 0, 0);
            gridPane1.add(textField, 0, 1);
            gridPane1.add(search, 0, 2);
            search.setOnAction(r -> {
                String s = textField.getText();
                String labin;
                String ln;
                String pro;
                if (!textField.getText().isEmpty()) {
                    try {
                        String sql = "SELECT * FROM lab WHERE labname = ?";
                        PreparedStatement statement = null;
                        try {
                            statement = connection.prepareStatement(sql);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            statement.setString(1, s);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        ResultSet resultSet = null;
                        try {
                            resultSet = statement.executeQuery();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if (resultSet.next()) {
                                try {
                                    labin = resultSet.getString("staffname");
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                ln = resultSet.getString("labname");
                                pro = resultSet.getString("projectorstatus");
                                Label label1 = new Label("LabInchargeName:");
                                gridPane2.add(label1, 0, 0);
                                gridPane2.add(LabInchargeName, 1, 0);
                                LabInchargeName.setText(labin);


                                Label label2 = new Label("LabName:");
                                gridPane2.add(label2, 0, 1);
                                gridPane2.add(LabName, 1, 1);
                                LabName.setText(ln);


                                Label label3 = new Label("ProjectorStatus:");
                                gridPane2.add(label3, 0, 2);
                                gridPane2.add(hasProjector, 1, 2);
                                hasProjector.setText(pro);
                                Button button4 = new Button("back");
                                button3.setAlignment(Pos.BOTTOM_LEFT);
                                gridPane2.add(button4, 0, 4);
                                button4.setOnAction(e -> addLabAction(stage));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
//                    else {
//                        showAlert("Cannot Find Lab",Alert.AlertType.ERROR);
//                        gridPane2.add(back,0,3);
//                        back.setOnAction(e-> addLabAction(stage));
//                    }
                } else {
                    showAlert("no lab name added", Alert.AlertType.ERROR);
                    gridPane2.add(back, 0, 3);
                    back.setOnAction(e -> addLabAction(stage));
                }
                Scene scene2 = new Scene(gridPane2, 300, 300);
                stage.setScene(scene2);
                stage.show();

            });

            Scene scene1 = new Scene(gridPane1, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });

        button3.setOnAction(e -> showActionsScreen(stage));

    }

    public void addDepartments(Stage stage) {
        Button button = new Button("Add");
        button.setAlignment(Pos.CENTER_LEFT);
        Button button1 = new Button("Search");
        button.setAlignment(Pos.CENTER_RIGHT);
        Button button2 = new Button("back");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        Scene scene = new Scene(hBox, 300, 300);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(actionEvent -> {
            TextField HODname = new TextField();
            TextField Labs = new TextField();
            TextField departmentName = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(5);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));
            Button button5 = new Button("back");
            button5.setAlignment(Pos.BOTTOM_LEFT);
            gridPane.add(button5, 0, 5);
            button5.setOnAction(e -> addDepartments(stage));

            Label label1 = new Label("HOD name:");
            gridPane.add(label1, 0, 0);
            gridPane.add(HODname, 1, 0);

            Label label2 = new Label("DepartmentName:");
            gridPane.add(label2, 0, 2);
            gridPane.add(Labs, 1, 1);

            Label label3 = new Label("NoOfLabs:");
            gridPane.add(label3, 0, 1);
            gridPane.add(departmentName, 1, 2);

            Button button4 = new Button("save");
            gridPane.add(button4, 1, 5);
            button4.setOnAction(e -> {
                if (!HODname.getText().isEmpty() && !Labs.getText().isEmpty() && !departmentName.getText().isEmpty()) {
                    String sql = "INSERT INTO department (hodname,departmentname,numberoflabs) VALUES (?, ?,?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, HODname.getText());
                        statement.setString(2, departmentName.getText());
                        statement.setString(3, Labs.getText());

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Failed to insert data.");
                        }
                        clearLab(HODname, departmentName, Labs);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    showAlert("please fill all the fields", Alert.AlertType.ERROR);
                }
            });

            Scene scene1 = new Scene(gridPane, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        button1.setOnAction(a -> {
            GridPane gridPane1 = new GridPane();
            GridPane gridPane2 = new GridPane();
            gridPane1.setAlignment(Pos.CENTER);
            gridPane1.setHgap(5);
            gridPane1.setVgap(10);
            gridPane1.setPadding(new Insets(25, 25, 25, 25));
            TextField hodName = new TextField();
            TextField NumberOfLab = new TextField();
            TextField departmentName = new TextField();
            Label label = new Label("enter DepartmentName");
            Button search = new Button("Search");
            Button backButton = new Button("back");
            backButton.setAlignment(Pos.BOTTOM_LEFT);
            label.setAlignment(Pos.CENTER);
            TextField textField = new TextField();
            gridPane1.add(label, 0, 0);
            gridPane1.add(textField, 0, 1);
            gridPane1.add(search, 0, 2);
            gridPane1.add(backButton, 0, 3);
            backButton.setOnAction(e -> addDepartments(stage));
            search.setOnAction(r -> {
                String s = textField.getText();
                String hodn;
                String departn;
                int labs;
                if (!s.isEmpty()) {
                    try {
                        String sql = "SELECT * FROM department WHERE departmentname = ?";
                        PreparedStatement statement = null;
                        try {
                            statement = connection.prepareStatement(sql);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            statement.setString(1, s);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        ResultSet resultSet = null;
                        try {
                            resultSet = statement.executeQuery();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            if (resultSet.next()) {
                                try {
                                    hodn = resultSet.getString("hodname");
                                    departn = resultSet.getString("departmentname");
                                    labs = resultSet.getInt("numberoflabs");
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                Label label1 = new Label("HODname:");
                                gridPane2.add(label1, 0, 0);
                                gridPane2.add(hodName, 1, 0);
                                hodName.setText(hodn);


                                Label label2 = new Label("DepartmentName:");
                                gridPane2.add(label2, 0, 1);
                                gridPane2.add(departmentName, 1, 1);
                                NumberOfLab.setText(departn);


                                Label label3 = new Label("NoOfLabs:");
                                gridPane2.add(label3, 0, 2);
                                gridPane2.add(NumberOfLab, 1, 2);
                                departmentName.setText(String.valueOf(labs));

                                Button button3 = new Button("back");
                                button3.setAlignment(Pos.BOTTOM_LEFT);
                                gridPane2.add(button3, 0, 4);
                                button3.setOnAction(e -> addDepartments(stage));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    showAlert("no department name added", Alert.AlertType.ERROR);
                    Button button3 = new Button("back");
                    button3.setAlignment(Pos.BOTTOM_LEFT);
                    gridPane2.add(button3, 0, 4);
                    button3.setOnAction(e -> addDepartments(stage));
                }
                   else {
                       showAlert("No such Department Exists",Alert.AlertType.ERROR);
                       Button button3 = new Button("back");
                       button3.setAlignment(Pos.BOTTOM_LEFT);
                       gridPane2.add(button3, 0, 4);
                       button3.setOnAction(e -> addDepartments(stage));
                   }

               }
                Scene scene2 = new Scene(gridPane2, 300, 300);
                stage.setScene(scene2);
                stage.show();


            });

            Scene scene1 = new Scene(gridPane1, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        button2.setOnAction(e -> showActionsScreen(stage));
    }

    public void addCampus(Stage stage) {

        //Buttons
        Button button = new Button("Add Campus");
        button.setAlignment(Pos.CENTER_LEFT);
        Button button1 = new Button("Search Campus");
        button1.setAlignment(Pos.CENTER_RIGHT);
        Button button2 = new Button("back");
        //Hbox
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);
        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        //Scene
        Scene scene = new Scene(hBox, 300, 300);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(actionEvent -> {
            //Text fields
            TextField CampusName = new TextField();
            TextField Address = new TextField();
            TextField directorName = new TextField();
            TextField NumberOfDepartments = new TextField();
            //Grid
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(5);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));

            //Labels
            Label label1 = new Label("CampusName:");
            gridPane.add(label1, 0, 0);
            gridPane.add(CampusName, 1, 0);

            Label label2 = new Label("Address:");
            gridPane.add(label2, 0, 1);
            gridPane.add(Address, 1, 1);

            Label label3 = new Label("DirectorName:");
            gridPane.add(label3, 0, 2);
            gridPane.add(directorName, 1, 2);

            Label label4 = new Label("NumberOfDepartments:");
            gridPane.add(label4, 0, 3);
            gridPane.add(NumberOfDepartments, 1, 3);
            // Back button
            Button button5 = new Button("back");
            gridPane.add(button5, 0, 5);
            button5.setOnAction(e -> addCampus(stage));
            Button button3 = new Button("save");
            gridPane.add(button3, 1, 5);
            //Taking text from fields and saving in file
            button3.setOnAction(e -> {
                if (!CampusName.getText().isEmpty() && !Address.getText().isEmpty() && !directorName.getText().isEmpty() && !NumberOfDepartments.getText().isEmpty()) {
                    String sql = "INSERT INTO campus (address,campusname,directorname,numberofdepartments) VALUES (?, ?,?,?)";

                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, Address.getText());
                        statement.setString(2, CampusName.getText());
                        statement.setString(3, directorName.getText());
                        statement.setString(4, NumberOfDepartments.getText());

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Failed to insert data.");
                        }
                        clearCampus(CampusName, Address, directorName, NumberOfDepartments);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    showAlert("please fill all the fields", Alert.AlertType.ERROR);
                }
            });
            //Scene
            Scene scene1 = new Scene(gridPane, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        //Search Button
        button1.setOnAction(a -> {
            //Grid
            GridPane gridPane1 = new GridPane();
            GridPane gridPane2 = new GridPane();
            gridPane1.setAlignment(Pos.CENTER);
            gridPane1.setHgap(5);
            gridPane1.setVgap(10);
            gridPane1.setPadding(new Insets(25, 25, 25, 25));
            TextField CampusName = new TextField();
            TextField Address = new TextField();
            TextField directorName = new TextField();
            TextField NumberOfDepartments = new TextField();
            Label label = new Label("enter CampusName");
            Button search = new Button("Search");
            label.setAlignment(Pos.CENTER);
            TextField textField = new TextField();
            gridPane1.add(label, 0, 0);
            gridPane1.add(textField, 0, 1);
            gridPane1.add(search, 0, 2);
            Button button5 = new Button("back");
            gridPane1.add(button5, 1, 2);
            button5.setOnAction(e -> addCampus(stage));
            search.setOnAction(r -> {
                String s = textField.getText();
                String campname;
                String address;
                String dn;
                int num;
                if (!textField.getText().isEmpty()) {
                    try {
                        String sql = "SELECT * FROM campus WHERE campusname = ?";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, s);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            campname = resultSet.getString("campusname");
                            address = resultSet.getString("address");
                            dn = resultSet.getString("directorname");
                            num = resultSet.getInt("numberofdepartments");
                            Label label1 = new Label("CampusName:");
                            gridPane2.add(label1, 0, 0);
                            gridPane2.add(CampusName, 1, 0);
                            CampusName.setText(campname);


                            Label label2 = new Label("Address:");
                            gridPane2.add(label2, 0, 1);
                            gridPane2.add(Address, 1, 1);
                            Address.setText(address);


                            Label label3 = new Label("DirectorName:");
                            gridPane2.add(label3, 0, 2);
                            gridPane2.add(directorName, 1, 2);
                            directorName.setText(dn);


                            Label label4 = new Label("NumberOfDepartments:");
                            gridPane2.add(label4, 0, 3);
                            gridPane2.add(NumberOfDepartments, 1, 3);
                            NumberOfDepartments.setText(String.valueOf(num));
                            Button button3 = new Button("back");
                            button3.setAlignment(Pos.BOTTOM_LEFT);
                            gridPane2.add(button3, 0, 4);
                            button3.setOnAction(e -> addCampus(stage));

                        } else {
                            showAlert("No such Campus Exists", Alert.AlertType.ERROR);
                            Button button3 = new Button("back");
                            button3.setAlignment(Pos.BOTTOM_LEFT);
                            gridPane2.add(button3, 0, 4);
                            button3.setOnAction(e -> addCampus(stage));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    showAlert("no campus name added", Alert.AlertType.ERROR);
                    Button button3 = new Button("back");
                    button3.setAlignment(Pos.BOTTOM_LEFT);
                    gridPane2.add(button3, 0, 4);
                    button3.setOnAction(e -> addCampus(stage));
                }
                Scene scene2 = new Scene(gridPane2, 300, 300);
                stage.setScene(scene2);
                stage.show();

            });

            Scene scene1 = new Scene(gridPane1, 300, 300);
            stage.setScene(scene1);
            stage.show();
        });
        button2.setOnAction(e -> showActionsScreen(stage));
    }

    //Useful Functions

    private void showAlert(String s, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login Form");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void clearFields(TextField usernameField, PasswordField passwordField) {
        usernameField.clear();
        passwordField.clear();
    }

    public void clearPc(TextField a, TextField b, TextField c, TextField d, TextField f) {
        a.clear();
        b.clear();
        c.clear();
        d.clear();
        f.clear();
    }

    public void clearLab(TextField a, TextField b, TextField c) {
        a.clear();
        b.clear();
        c.clear();
    }

    public void clearCampus(TextField a, TextField b, TextField c, TextField d) {
        a.clear();
        b.clear();
        c.clear();
        d.clear();
    }

    //    //File Writing
    private void saveToEmployeesFile(String username, String password) throws SQLException {
        String sql = "INSERT INTO employee (username, password) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        }
    }

   public void updateEmployeeinfo(Stage stage) throws SQLException {

       GridPane gridPane=new GridPane();
       HBox hBox=new HBox();
       hBox.setAlignment(Pos.CENTER);
       hBox.setPadding(new Insets(25,25,25,25));
       hBox.getChildren().add(label);
       hBox.getChildren().add(username);
       hBox.getChildren().add(label2);
       hBox.getChildren().add(password1);
       gridPane.add(hBox,2,3);
       gridPane.add(saveButton,2,4);

       Button saveButton=new Button("save");
       Button b4=new Button("Search");

       Label label=new Label("new username");
       Label label2=new Label("new password");

       TextField search=new TextField();

       TextField username = new TextField();
       TextField password1 = new TextField();
       b4.setOnAction(e->{
           ResultSet resultSet = null;
           try {
               resultSet = statement.executeQuery("SELECT * FROM employee");
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
           while (true) {
               try {
                   if (!resultSet.next()) break;
               } catch (SQLException ex) {
                   throw new RuntimeException(ex);
               }
               try {
                   if (search.getText().equals(resultSet.getString("username"))) {
                       username.setText(resultSet.getString("username"));
                       password1.setText(resultSet.getString("password"));
                   } else {
                       System.out.println("no account found");
                   }
               } catch (SQLException ex) {
                   throw new RuntimeException(ex);
               }
           }
       });


       String sql="UPDATE employee SET password=? WHERE username=?";

       saveButton.setOnAction(e->{
           try {
               PreparedStatement preparedStatement= connection.prepareStatement(sql);
               preparedStatement.setString(1,username.getText());
               preparedStatement.setString(2,password1.getText());
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
           clearemployeefields(username,password1);
       });


       Scene scene=new Scene(gridPane,300,400);
       stage.setScene(scene);
       stage.show();
   }
   public void saveUniversityInfo(String s,File file) {
       try {
           FileWriter fileWriter=new FileWriter(file,true);
           fileWriter.write(s);
           fileWriter.close();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

   //File Reading
   public String[] searchRecord(File file,String s){
       String content ="";

       //Getting all the content from file in a String
       try (FileReader reader = new FileReader(file)) {
           int i;
           while ((i = reader.read()) != -1) {
               content += (char) i;
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       String[] split = content.split("\n");
       //Checking if the given ID is in the Saved Entries in File
       for (String c: split){
           if (c.split(",")[1].equals(s)){
               return c.split(",");
           }
       }
       return null;
   }
    public void clearemployeefields(TextField t,TextField f){
        t.clear();
        f.clear();
    }
    public static void main(String[] args) {
        launch();
    }
}