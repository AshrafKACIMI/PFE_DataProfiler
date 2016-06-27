/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import Computing.BasicStatisticsProfiler;
import Computing.BasicStatisticsService;
import Computing.DatesEngine;
import Computing.DuplicatesEngine;
import Computing.ProfilingScheduler;
import Computing.ReferentialIntegrityEngine;
import DQRepository.IConnector;
import DQRepository.MetaDataConnector;
import Entities.ColumnInfo;
import Entities.ColumnProfilingStats;
import Entities.ColumnProfilingStatsRow;
import Entities.TableInfo;
import Entities.TablesFactory;
import GraphicWidgets.ColumnComboBox;
import GraphicWidgets.ProgressIndicatorGraph;
import GraphicWidgets.RegexFieldValidator;
import GraphicWidgets.ScheduleTasksDisplay;
import GraphicWidgets.TableComboBox;
import GraphicWidgets.ThresholdFormGrid;
import GraphicWidgets.ToolbarIcon;
import Mail.EmailOptions;
import Mail.MailListView;
import Reporting.ConcatenatedReports;
import Reporting.TableReport;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.CANCELLED;
import static javafx.concurrent.Worker.State.FAILED;
import static javafx.concurrent.Worker.State.SUCCEEDED;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

/**
 *
 * @author Ashraf
 */
public class FXMLDocumentController implements Initializable {
    private static final String FILE_NAME = "jaxb-emp.xml";
    
    private static JFXBadge badge;    

    /**
     * @return the tables
     */
    public static ArrayList<TableInfo> getTables() {
        return tables;
    }

    /**
     * @return the parentColumns
     */
    public static ColumnComboBox getParentColumns() {
        return parentColumns;
    }

    /**
     * @return the childColumns
     */
    public static ColumnComboBox getChildColumns() {
        return childColumns;
    }
    
    private Label label;
    private static IConnector connector;
    private static ArrayList<TableInfo> tables;
    private int tableNumber;
    private static final ObservableList<ColumnProfilingStatsRow> data
            = FXCollections.observableArrayList();
    private int selectedCol = 1;
    private static ProfilingScheduler scheduler = new ProfilingScheduler();
    private static JFXDialog loginDialog;
    private static JFXDialog thresholdDialog;
    private static JFXDialog launchDialog;
    private static JFXDialog laterDialog;
    

    

    @FXML
    private TreeView tableTreeView;
    @FXML
    private AnchorPane anchor;
    @FXML
    private static TextArea logArea;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Label resultTableLabel;
    @FXML
    private PieChart resultsChart;


    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem refreshMenu;
    @FXML
    private MenuItem menuItemProfile;
    @FXML
    private TableColumn<?, ?> columnCol;
    @FXML
    private TableColumn<?, ?> countCol;
    @FXML
    private TableColumn<?, ?> nullCol;
    @FXML
    private TableColumn<ColumnProfilingStatsRow, Integer> distinctCol;
    @FXML
    private TableColumn<?, ?> minCol;
    @FXML
    private TableColumn<?, ?> maxCol;
    @FXML
    private TableColumn<?, ?> violDistinct;
    @FXML
    private TableColumn<?, ?> violNotNull;
    @FXML
    private TableColumn<?, ?> violMin;
    @FXML
    private TableColumn<?, ?> violMax;
    @FXML
    private VBox dashboardVbox;
    @FXML
    private HBox dashboardHBox;
    @FXML
    private HBox notificationsBox;
    @FXML
    private JFXTextField addMailField;
    @FXML
    private JFXButton addMailButton;
    
    private ProgressIndicatorGraph dashboardCompletenessProgress;
    private ScheduleTasksDisplay schedulerTaskDisplay;
    
    @FXML
    private TableView tableView;
    @FXML
    private JFXSpinner spinner;
    private static BasicStatisticsProfiler profiler;
    @FXML
    private MenuItem sendMailMenu;
    
    @FXML
    private VBox completenessVBox;
    @FXML
    private VBox uniquenessVBox;
    
    private ProgressIndicatorGraph completenessIndicator;
    private ProgressIndicatorGraph uniquenessIndicator;
    
    
    
    @FXML
    JFXToggleButton mailToggle;
    @FXML
    JFXToggleButton reportToggle;
    
    @FXML HBox notificationBox;
    
    @FXML 
    private VBox mailVBox;
    @FXML
    private VBox childTableBox;
    @FXML
    private VBox parentTableBox;
    @FXML
    private VBox childColumnBox;
    @FXML
    private VBox parentColumnBox;
    
    @FXML
    private JFXTextArea refArea;
    
    @FXML
    private HBox controlBox;
    
    @FXML
    private Label folderLabel;
    
    @FXML
    private Label refResultLabel;
    @FXML
    private JFXListView dateKeysList;
    @FXML
    private JFXListView duplicateKeysList;
    
    @FXML
    private VBox refVBox;
    
    //Dates Tab
    @FXML
    private HBox dateTableBox;
    @FXML
    private HBox dateFromBox;
    @FXML
    private HBox dateToBox;
    @FXML
    HBox chartBox;
    
    //duplicate tab
    @FXML
    private HBox duplicatesTableBox;
    
    
    
    
    private static MailListView mailListView;
    private static TableComboBox parentTables;
    private static TableComboBox childTables;
    private static ColumnComboBox parentColumns;
    private static ColumnComboBox childColumns;
    
    //dates tab
    private static TableComboBox dateTables;
    private static ColumnComboBox dateFromColumns;
    private static ColumnComboBox dateToColumns;
    
    //duplicate tab
    private static TableComboBox duplicateTables;
    
    
    private static JFXPopup loginPopup;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart barChart;
    @FXML
    private BarChart logBars;
    @FXML
    private JFXButton dateCheckButton;
    @FXML
    private JFXButton dateScheduleButton;
    @FXML
    private JFXButton duplicateButton;
    
    
    
    //dashboard
    @FXML
    private PieChart validityChart;
    @FXML
    private PieChart consistencyChart;
    @FXML
    private PieChart completenessChart;
    @FXML
    private PieChart unicityChart;
    @FXML
    private PieChart integrityChart;
    
    private ObservableList<PieChart.Data> validityChartData;
    private ObservableList<PieChart.Data> consistencyChartData;
    private ObservableList<PieChart.Data> completenessChartData;
    private ObservableList<PieChart.Data> unicityChartData;
    private ObservableList<PieChart.Data> integrityChartData;
    
    @FXML
    private Label totalRows;
    @FXML
    private Label totalDuplicates;
    @FXML
    private Label totalRef;
    
    
    
    
    
    
    private static FXMLDocumentController controller;
    
    public static FXMLDocumentController getController(){
        return controller;
    }
    private void handleButtonAction(ActionEvent event) {
        createLoginPopUp();
        label.setText("Hello World!");
    }
    
    

    
    
    
    @FXML
    private void handleNewMenuItem(ActionEvent event) {
        createLoginPopUp();
    }
    
    @FXML
    private void handleSendMailMenu(ActionEvent event) {
        //createLoginPopUp();
        //TODO
    }
    
    
    
    @FXML 
    private void refreshMenuOnAction(ActionEvent event){
        initializeTableTreeView();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logBars.setAnimated(true);
                
        //connector = new OracleConnector("hr", "hrpassword", "jdbc:oracle:thin:@localhost:1522:orcl");
        
        initializeMailTab();
        stopSpinner();  
//        
        
//        webView.getEngine().setUserStyleSresultBarsheetLocation(getClass().getResource("/dashweb/css/bootstrap.min.css").toString());
//        webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/dashweb/css/bootstrap.css").toString());
//        webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/dashboard/styles.css").toString());
        
        
        
        
        initBarChart();
        
 


        
        completenessIndicator = new ProgressIndicatorGraph(0, 120, 120);
        uniquenessIndicator = new ProgressIndicatorGraph(0, 120, 120);
        completenessVBox.getChildren().add(0, completenessIndicator);
        uniquenessVBox.getChildren().add(0, uniquenessIndicator);
        
        Icon value = new Icon(AwesomeIcon.PIE_CHART, "30px", "-fx-text-fill: #E0E4CC;", "icon");
        value.setId("icon");
        badge = new JFXBadge(value);
        badge.setPosition(Pos.TOP_RIGHT);
        badge.setText("0");
        badge.setId("icons-badge");
        badge.setPrefSize(50, 50);
        badge.setOnMouseClicked((e) -> {
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            this.schedulerTaskDisplay = new ScheduleTasksDisplay(scheduler);
            loginPopup = new JFXPopup(root, schedulerTaskDisplay);
            loginPopup.setSource(badge);
            loginPopup.show(PopupVPosition.TOP, PopupHPosition.RIGHT);
        });
        notificationBox.getChildren().add(new ToolbarIcon(badge));

        
        ToolbarIcon schedulerStart = new ToolbarIcon(AwesomeIcon.PLAY);
        ToolbarIcon newConnection = new ToolbarIcon((AwesomeIcon.PLUG));
        ToolbarIcon scheduleLater = new ToolbarIcon((AwesomeIcon.ARCHIVE));
        
        newConnection.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                createLoginPopUp();
            }
        });
        
        schedulerStart.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                createLaunchPopUp();
                //scheduler.start();
            }
            
        });
        
        scheduleLater.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                createLaterPopUp();
            }
        });
        
        
        controlBox.getChildren().addAll(newConnection, schedulerStart, scheduleLater);
        controller = this;
        //initializeTableTreeView();
        initializeTable();
        dashboardCompletenessProgress = new ProgressIndicatorGraph(0, 150, 150);
        VBox completenessVBox = new VBox(20);
        completenessVBox.getChildren().addAll(
                new Label("Completeness"),
                dashboardCompletenessProgress
        );
        //dashboardVbox.getChildren().add(dashboardCompletenessProgress);
        ProgressIndicatorGraph dashboardUnicityProgress = new ProgressIndicatorGraph(0, 150, 150);
        VBox unicityVBox = new VBox(20);
        unicityVBox.getChildren().addAll(
                new Label("Unicity"),
                dashboardUnicityProgress
        );
        ProgressIndicatorGraph dashboardIntegrityProgress = new ProgressIndicatorGraph(0, 150, 150);
        VBox integrityVBox = new VBox(20);
        integrityVBox.getChildren().addAll(
                new Label("Integrity"),
                dashboardIntegrityProgress
        );
        //dashboardHBox.getChildren().addAll(completenessVBox, unicityVBox, integrityVBox);
        
        //initializeRefTab();
        


        //BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
        //System.out.println(profiler.profileTableQuery());
    }

    private void initBarChart() {
        
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        
        barChart = new BarChart(xAxis, yAxis);
        barChart.setAnimated(false);
        //chartBox.getChildren().add(barChart);
        
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);

    }
    
    private void initializeMailTab(){
        
        mailToggle.setSelected(true);
        reportToggle.setSelected(true);
        //folderLabel.setText(EmailOptions.getFileDirectory());
        mailToggle.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                EmailOptions.setOn(newValue);
                System.out.println("EMAIL OPTIONS: " + EmailOptions.isOn());
            }
        });
        
        reportToggle.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //EmailOptions.setOn(newValue);
                EmailOptions.setReport(newValue);
                if (!newValue){
                    mailToggle.setSelected(false);
                    mailToggle.setDisable(true);
                } else {
                    mailToggle.setDisable(false);
                }
                
            }
        });
        //mailToggle.isSelected()
        
        RegexFieldValidator validator = new RegexFieldValidator(RegexFieldValidator.EMAIL_PATTERN);
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"1em",";","error"));
        addMailField.getValidators().add(validator);
        addMailField.focusedProperty().addListener((o,oldVal,newVal)->{
                if(!newVal) addMailField.validate();
        });
        
        mailListView = new MailListView(new ArrayList<String>());
        getMailListView().addMail("kacimi.achraf@gmail.com");
        getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");
//        mailListContainer.setPrefSize(220, 400);
//        mailListContainer.setMinSize(220, 400);
//        mailListContainer.setMaxSize(220, 400);
        //mailListContainer.getChildren();
        mailVBox.getChildren().add(2, mailListView);
        
    }

    public void initializeRefTab(){
                    

        //parentTableBox.getChildren().remove(1);
//        childTableBox.getChildren().remove(1);
        
        parentTables = new TableComboBox(getTables(), 0);
        childTables = new TableComboBox(getTables(), 1);
        
        parentTableBox.getChildren().add(parentTables);
        childTableBox.getChildren().add(childTables);
        
//        parentColumnBox.getChildren().add(new ColumnComboBox(tables.get(0)));
//        parentColumnBox.getChildren().add(new ColumnComboBox(tables.get(1)));
        
        parentColumns = new ColumnComboBox();
        childColumns = new ColumnComboBox();
//        parentColumnBox.getChildren().remove(1);
//        childColumnBox.getChildren().remove(1);        
                
        parentColumnBox.getChildren().add(parentColumns);
        childColumnBox.getChildren().add(childColumns);
        
        
        System.out.println(getTables().size());
        System.out.println(getTables().get(0).getName());
        System.out.println(getTables().get(1).getName());
        
        int selected = parentTables.getSelectionModel().getSelectedIndex();
        
        //refVBox.getChildren().add(new ColumnsList(getTables().get(0)));
        
    }
    
    public void initializeTableTreeView (){
        
        final TreeItem<String> treeRoot = new TreeItem<String>("Tables");
        ArrayList<TableInfo> tables = new ArrayList<TableInfo>();

        //tables = new TablesFactory("SYSTEM", "OracleAdmin1", "jdbc:oracle:thin:@localhost:1522:orcl").getTables();
        tables = new TablesFactory(connector).getTables();
        //tables = new TablesFactory().getTables();
        for (TableInfo tab: tables){
            TreeItem<String> treeTab = new TreeItem<String>(tab.getName());
            
            for (ColumnInfo col: tab.getColumns()){
                treeTab.getChildren().add(new TreeItem<String>(col.getName()+":"+col.getType()));
            }
            
            treeRoot.getChildren().add(treeTab);
        }
        this.getTableTreeView().setShowRoot(true);
        this.getTableTreeView().setRoot(treeRoot);
        this.tables = tables;
        tableTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem treeItem = (TreeItem)newValue;
                tableNumber = getTableNumber(treeItem.getValue().toString());
                System.out.println("Selected item is: " + treeItem.getValue());
            }
            
        });
        
        tableTreeView.getRoot().expandedProperty().setValue(true);
        
        
     
    }
    
    private void initializeKeyboardShortcuts(){
        menuItemProfile.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
    }
    
    private void initializeTable(){
        
        columnCol.setCellValueFactory(
                new PropertyValueFactory<> ("columnName")
        );
        
        countCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbLines")
        );
        
        nullCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbNull")
        );
        
        distinctCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbDistinct")
        );
        
        minCol.setCellValueFactory(
                new PropertyValueFactory<> ("min")
        );
        
        maxCol.setCellValueFactory(
                new PropertyValueFactory<> ("max")
        );
        
        violDistinct.setCellValueFactory(
                new PropertyValueFactory<> ("violDistinct")
        );
        
        violNotNull.setCellValueFactory(
                new PropertyValueFactory<> ("violDistinct")
        );
        
        violMax.setCellValueFactory(
                new PropertyValueFactory<> ("violMin")
        );
        
        violMax.setCellValueFactory(
                new PropertyValueFactory<> ("violMax")
        );
        
        this.tableView.setItems(getData());
        
        
//        distinctCol.setCellFactory(column -> {
//            return new TableCell<ColumnProfilingStatsRow, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//                    int selectedCol = tableView.getSelectionModel().getSelectedIndex();
//                    System.out.println(selectedCol);
//                    if (!data.isEmpty())
//                        if (data.get(selectedCol).getViolDistinctFlag() == 1)
//                                setStyle("-fx-background-color: red");
//
//                }
//            };
//        });
    }
    
//
    
    public void loadTable(){
        getData().clear();
        //data.addAll(this.profiler.profilingResult());
        
        for (ColumnProfilingStats d: this.profiler.profilingResult()){
            ColumnInfo c = getTables().get(tableNumber).getColumnByName(d.getColumnName());
            getData().add(c.getStats());
        }
        
        stopSpinner();
        System.out.println("Table Loaded :3 !");
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedCol = tableView.getSelectionModel().getSelectedIndex();
                barChart.setTitle(getData().get(selectedCol).getColumnName() + " values");
                //System.out.println(getData().get(selectedCol));
                loadChart();
            }
        });
        this.tableView.getSelectionModel().clearAndSelect(0);
        loadChart();
    }
    
    public void loadChart(){
        int selected = this.tableView.getSelectionModel().getSelectedIndex();
        if (selected < 0)
            return;
        ObservableList<PieChart.Data> pieChartData;
        String columnName;
        float nullCount = getData().get(this.selectedCol).getNbNull();
        float count = getData().get(this.selectedCol).getNbLines();
        float distinct = getData().get(this.selectedCol).getNbDistinct();
        //float min = getData().get(this.selectedCol).getMin();
        //float max = getData().get(this.selectedCol).getNbDistinct();

        pieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Null", (float) (nullCount)/count),
               new PieChart.Data("Not null", (float) (count-nullCount)/count ));
        completenessIndicator.setProgress((count - nullCount) / count);
        uniquenessIndicator.setProgress(distinct / count);

        
        yAxis = new NumberAxis();
        yAxis.setLabel("Results");//"Results", 0.0d, 3000.0d, 10.0d);

        String[] categories = {"Counts", "Values"};
        xAxis.setCategories(FXCollections.<String>observableArrayList(categories));
        
        ObservableList<BarChart.Series> barChartData;
        barChartData = FXCollections.observableArrayList(
            new BarChart.Series(categories[0], FXCollections.observableArrayList(
               new BarChart.Data("Null", nullCount),
               new BarChart.Data("Not null", count - nullCount),
               new BarChart.Data("Distinct", distinct)
            ))
                ,
            new BarChart.Series(categories[1], FXCollections.observableArrayList(
               new BarChart.Data("Min", Integer.parseInt(getData().get(this.selectedCol).getMin())),
               new BarChart.Data("Max", Integer.parseInt(getData().get(this.selectedCol).getMax()))
            ))
        );
        
        ObservableList<BarChart.Data>logChartData = FXCollections.observableArrayList(
               new BarChart.Data("Null", nullCount),
               new BarChart.Data("Not null", count - nullCount),
               new BarChart.Data("Distinct", distinct)
        );
        
        //this.barChart.setData(logChartData);
        logBars.setData(barChartData);
        System.out.print("Null: ");
        System.out.println(((float) nullCount)/count);
        System.out.print("Not Null: ");
        System.out.println((float) (count - nullCount)/count);
        
        //System.out.println("Not Null = "+ (count - nullCount)/count);
        
        this.resultsChart.setData(pieChartData);
        this.resultsChart.setTitle(getData().get(this.selectedCol).getColumnName());
        
    }
    
    
    private int getTableNumber(String tableName){
        for (int i = 0; i < getTables().size(); i++)
            if (getTables().get(i).getName().equals(tableName))
                    return i;
        return -1;
    }
    
    private void createLoginPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            StackPane content;
            content = (StackPane) FXMLLoader.load(getClass().getResource("login.fxml"));
            content.setId("jfx-dialog-layout");
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            loginDialog = dialog;
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createLaunchPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("launchScheduler.fxml"));
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            StackPane content;
            content = (StackPane) FXMLLoader.load(getClass().getResource("launchScheduler.fxml"));
            content.setId("jfx-dialog-layout");
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            launchDialog = dialog;
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createLaterPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("launchLater.fxml"));
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            StackPane content;
            content = (StackPane) FXMLLoader.load(getClass().getResource("launchLater.fxml"));
            content.setId("jfx-dialog-layout");
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            laterDialog = dialog;
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onContextMenuRequested(ContextMenuEvent event){
//        TreeItem<String> ti;
//        System.out.println(event.getSource());
//        System.out.println(event.getSource().getClass());
//        System.out.println(event.getSource().toString());
    }
    
    
    @FXML
    private void onMenuClick(ActionEvent event){
        System.out.println(event.getSource().toString());
        if (tableNumber>= 0){
            startSpinner();
            TableInfo table = getTables().get(tableNumber);

            // We create a new Service that handles the profiling thread
            final Service<Void> calculateService = 
                        new BasicStatisticsService(table);
            
            //We      
            calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    postProfilingResult(table);
                break;
            }
        });

            startSpinner();
            calculateService.start();

        
        } else{
            System.out.println(tableNumber);
        }
    }

    public void postProfilingResult(TableInfo table) {
        loadTable();
        if (EmailOptions.isReport()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new TableReport(table, EmailOptions.isReport());
                }
            });

        }
        dashboardCompletenessProgress.setProgress(getOverallCompleteness());
        resetCursor();
    }
    

    
   
//    @FXML
//    private void handleTreeClick(ActionEvent event){
//        BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
//        System.out.println("Menu clicked");
//        System.out.println(profiler.profileTableQuery());
//    }
    
    @FXML
    private void setThreshold(ActionEvent event){
        // When the context menu "set threshold" is pressed
        if (tableNumber >= 0){
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            StackPane content = new ThresholdFormGrid(getTables().get(tableNumber));
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            thresholdDialog = dialog;
        }
    }
    
    @FXML
    private void scheduleMenuAction(ActionEvent event){
        // When the context menu add to schedule is pressed
        ConcatenatedReports concatRep = new ConcatenatedReports();
        if (tableNumber>= 0){
            TableInfo table = getTables().get(tableNumber);

            // We create a new Service that handles the profiling thread
            final BasicStatisticsService calculateService = 
                        new BasicStatisticsService(table);
            
            //We      
            calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    //loadTable();
                    JasperReportBuilder report = new TableReport(table, false).getReport();
                    scheduler.addReport(report);
                    dashboardCompletenessProgress.setProgress(getOverallCompleteness());
                    scheduler.decCount();
                    if (scheduler.getCount() == 0)
                        scheduler.getReport().makePdf();
                    badge.setText(String.valueOf( 
                            Integer.parseInt(badge.getText()) - 1));
                break;
            }
        });
            scheduler.addTask(calculateService);
            int val = Integer.parseInt(badge.getText());
            badge.setText(String.valueOf(val + 1 ));

        } else{
            System.out.println(tableNumber);
        }
    }
    
    @FXML
    private void startScheduler(ActionEvent event){
        scheduler.start();
    }
    
    @FXML
    private void addMailAction(ActionEvent event){
        
        if (!addMailField.getValidators().get(0).getHasErrors()){
            getMailListView().addMail(addMailField.getText());
        
        for (String s: EmailOptions.getMails()){
            System.out.println("mail : " + s);
        }
        
        String cct = "";
        for (String s: mailListView.getMailList()){
            System.out.println("mail 2: " + s);
            cct+= s;
        }
        }
    }
    
    @FXML
    private void deleteMailAction(ActionEvent event){
        getMailListView().removeSelectedMails();
    }
    
    @FXML
    private void clearMailAction(ActionEvent event){
        getMailListView().clear();
    }
    
    
    @FXML
    private void scheduleDatesAction(ActionEvent event){
        return;
    }
    
        @FXML
    private void checkDatesAction(ActionEvent event){
        
        int selected = dateTables.getSelectionModel().getSelectedIndex();
        

        
        String table = getTables().get(selected).getName();
        DatesEngine engine = 
            new DatesEngine(connector, table, "valid_from", "valid_to", dateColumns());
        engine.checkDates();
        String query = engine.overlapQuery();
        System.out.println("overlap : \n" +  query);
        System.out.println("holes : \n" +  engine.holesQuery());
        System.out.println(MetaDataConnector.getCount(connector.getDbName(), table, "valid_from"));
    }
    
    
    

    /**
     * @return the tableTreeView
     */
    public TreeView getTableTreeView() {
        return tableTreeView;
    }

    /**
     * @param tableTreeView the tableTreeView to set
     */
    public void setTableTreeView(TreeView tableTreeView) {
        this.tableTreeView = tableTreeView;
    }
    
    public void startSpinner(){
        this.spinner.setVisible(true);
        DataProfilerPFE.getRoot().getScene().setCursor(Cursor.WAIT);
    }
    
    public void stopSpinner(){
        this.spinner.setVisible(false);
        //FXMLTest.getRoot().getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * @return the data
     */
    public ObservableList<ColumnProfilingStatsRow> getData() {
        return data;
    }
    
    
    public float getOverallCompleteness(){
        float totalNotNull = 0;
        float totalLines = 0;
        for (TableInfo table: this.getTables()){
            for (ColumnInfo columnInfo: table.getColumns()){
                ColumnProfilingStatsRow stat = columnInfo.getStats();    
                totalNotNull += stat.getNbLines() - stat.getNbNull();
                totalLines += stat.getNbLines();
            }
        }
        if (totalLines == 0)
            return -1;
        return totalNotNull / totalLines;
    }
    
    

    /**
     * @return the mainStage
     */

    public static void setProfiler(BasicStatisticsProfiler profiler) {
            FXMLDocumentController.profiler = profiler;
        }
    

    
    public static ProfilingScheduler getScheduler(){
        return scheduler;
    }
    
    @FXML
    private void deleteFromScheduler(){
        this.schedulerTaskDisplay.removeSelectedTask();
    }
    
    
    
    
    
    @FXML
    private void duplicateAction(){
        System.out.println("SELECTION MODEL: " + 
                duplicateKeysList.getSelectionModel().getSelectedIndices()
        );
        System.out.println(duplicationColumns());
        int selected = duplicateTables.getSelectionModel().getSelectedIndex();
        

        
        String table = getTables().get(selected).getName();
        DuplicatesEngine engine = 
                new DuplicatesEngine(connector, table, duplicationColumns());
        int result = engine.checkDuplicates();
        System.out.println("RESULT : " +  result);
    }
    
    @FXML
    private void integrityAction(){
        //refArea.
        String parentTable = parentTables.getValue().getText();
        String childTable = childTables.getValue().getText();
        String parentColumn = parentColumns.getSelectedColumn();
        String childColumn = childColumns.getSelectedColumn();
        ReferentialIntegrityEngine engine = 
                new ReferentialIntegrityEngine(connector, parentTable, parentColumn, childTable, childColumn);
        String value = engine.referentialIntegritySampleQuery();
        int result = engine.checkReferentialIntegrity();
        refArea.setText(value);
        if(result >= 0)
            refResultLabel.setText(Integer.toString(result));
        else
            refResultLabel.setText("Incompatible columns");
    }
    
    @FXML
    private void selectFolder(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File(EmailOptions.getFileDirectory());
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(DataProfilerPFE.getMainStage());
        if (selectedDirectory!=null){
            String path = selectedDirectory.getAbsolutePath();
            EmailOptions.setFileDirectory(path);
            folderLabel.setText(path);
        }
            
    }

    /**
     * @return the mailListView
     */
    public static MailListView getMailListView() {
        return mailListView;
    }
    
    
    
    private void resetCursor(){
        DataProfilerPFE.getRoot().getScene().setCursor(Cursor.DEFAULT);
    }
    
    public static void setConnector(IConnector conn){
        connector = conn;
    }
    
    public static IConnector getConnector(){
        return connector;
    }
    
    public static void closeLoginDialog(){
        loginDialog.close();
    }
    
    public static void closeThresholdDialog(){
        thresholdDialog.close();
    }
    
    public static void closeLaunchDialog(){
        launchDialog.close();
    }
    
    public static void closeLaterDialog(){
        laterDialog.close();
    }
    
    public static void startScheduler() {
        launchSchedulerController.getController().startSpinner();

        // asynchronously loads the list view items
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
                scheduler.start();
//                launchSchedulerController.getController().stopSpinner();
//
//            }
//        });

    }

    public static void setSchedulerLabel(String string) {
        
        if (launchSchedulerController.getController()!=null){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    launchSchedulerController.getController().updateLabel(string);
                }
            });
        }
    }
    
    public static void addToLog(String taskName){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        final String content = timeStamp + taskName + "\n";
        logArea.appendText(content);
        System.out.println(content);
        
        //sqlArea.setText(timeStamp + taskName + "\n");
        
    }    
    
    public void initializeDateCheck(){
        dateKeysList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        updateColumnsList(getTables().get(1));

        dateTables = new TableComboBox(getTables(), 0);
        dateTables.getSelectionModel().select(1);
        dateFromColumns = new ColumnComboBox();
        dateToColumns = new ColumnComboBox();
        dateTableBox.getChildren().add(dateTables);
        dateFromBox.getChildren().add(dateFromColumns);
        dateToBox.getChildren().add(dateToColumns);
        
        dateTables.valueProperty().addListener(new ChangeListener<Label>() {

            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                FXMLDocumentController.getController().resetRefLabel();
                int selected = dateTables.getSelectionModel().getSelectedIndex();
                updateColumnsList(getTables().get(selected));
                dateFromColumns.update(selected);
                dateToColumns.update(selected);
                
            }
        });
    }
    

    public void initializeDuplicateCheck(){
        duplicateKeysList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        updateColumnsList(getTables().get(1));

        duplicateTables = new TableComboBox(getTables(), 0);
        duplicateTables.getSelectionModel().select(1);
                
        duplicatesTableBox.getChildren().add(duplicateTables);
        
        duplicateTables.valueProperty().addListener(new ChangeListener<Label>() {

            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                FXMLDocumentController.getController().resetRefLabel();
                int selected = duplicateTables.getSelectionModel().getSelectedIndex();
                updateDuplicateList(getTables().get(selected));
            }
        });

    }

    
    
    public void updateColumnsList(TableInfo table){
        
        ObservableList<String> names = FXCollections.observableArrayList();
        for (ColumnInfo col: table.getColumns()){
            names.add(col.getName());
        }
        dateKeysList.setItems(names);
    }
    
    public void updateDuplicateList(TableInfo table){
        
        ObservableList<String> names = FXCollections.observableArrayList();
        for (ColumnInfo col: table.getColumns()){
            names.add(col.getName());
        }
        duplicateKeysList.setItems(names);
    }
    
    public void initializeDashboard(){
        refreshTotalRows();
        
        validityChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Valid", 10000),
               new PieChart.Data("< Min", 1200),
               new PieChart.Data("> Max", 2000));
        validityChart.setData(validityChartData);
        
        consistencyChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Valid", 8000),
               new PieChart.Data("Hole", 1200),
               new PieChart.Data("Overlap", 1000));
        consistencyChart.setData(consistencyChartData);
        
        completenessChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Present", 8000),
               new PieChart.Data("Missing", 200));
        completenessChart.setData(completenessChartData);
        
        unicityChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Distinct", 5000),
               new PieChart.Data("Duplicate", 1200));
        unicityChart.setData(unicityChartData);
        
        integrityChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Valid", 4000),
               new PieChart.Data("Broken", 300));
        integrityChart.setData(integrityChartData);
        
        refreshCompleteness();
    

    }

    private void refreshCompleteness() {
        ArrayList<Integer> results = MetaDataConnector.getCompleteness(connector.getDbName());
        completenessChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Present", results.get(0)),
                        new PieChart.Data("Missing", results.get(1)));
        completenessChart.setData(completenessChartData);
    }

        private void refreshUniqueness() {
        ArrayList<Integer> results = MetaDataConnector.getUniqueness(connector.getDbName());
         unicityChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Distinct", results.get(0)),
                        new PieChart.Data("Duplicate", results.get(1)));
        unicityChart.setData(unicityChartData);
    }
    private void refreshTotalRows() {
        int total = MetaDataConnector.getTotalRows(connector.getDbName());
        System.out.println("connector:" + connector.getDbName());
        System.out.println("total:" + total);
        totalRows.setText(Integer.toString(total));
    }
    
    private void refreshIntegrity() {
        ArrayList<Integer> results = MetaDataConnector.getRefs();
        integrityChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Valid", results.get(1) - results.get(0)),
                        new PieChart.Data("Broken", results.get(0)));
        integrityChart.setData(integrityChartData);
        totalRef.setText(Integer.toString(results.get(0)));
    }
    
    private void refreshDates() {
        ArrayList<Integer> results = MetaDataConnector.getDates(connector.getDbName());
        
        consistencyChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Valid", results.get(2) - results.get(0) - results.get(1)),
                        new PieChart.Data("Holes", results.get(0)),
                        new PieChart.Data("Overlaps", results.get(1))
                );
        consistencyChart.setData(consistencyChartData);
    }
    
    private void refreshValidity() {
        ArrayList<Integer> results = MetaDataConnector.minMaxViolations(connector.getDbName());
        System.out.println(results.get(0));
        System.out.println(results.get(1));
        System.out.println(results.get(2));
        validityChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Valid", results.get(2) - results.get(0) - results.get(1)),
                        new PieChart.Data("< Min", results.get(0)),
                        new PieChart.Data("> Max", results.get(1))
                );
        validityChart.setData(validityChartData);
    }

    @FXML
    private void refreshAction(ActionEvent event){
        refreshCompleteness();
        refreshTotalRows();
        refreshUniqueness();
        refreshIntegrity();
        refreshDates();
        refreshValidity();
        int total = MetaDataConnector.getTotalRows(connector.getDbName());
        int count = MetaDataConnector.getCount("sh", "CUSTOMERS", "CUST_EFF_FROM");
        int duplicates = MetaDataConnector.getDuplicates(connector.getDbName());
        totalDuplicates.setText(Integer.toString(duplicates));
        

        System.out.println("RESULTS: " + MetaDataConnector.getRefs());
        System.out.println("COUNT : " + count);
    }
    
    
    public void resetRefLabel(){
        refResultLabel.setText("");
    
    }
    
    
    private ArrayList<String> duplicationColumns(){
        duplicateKeysList.getSelectionModel().getSelectedItems();
        ArrayList<String> keys = new ArrayList<String>();
        for (Object o: duplicateKeysList.getSelectionModel().getSelectedItems()){
            keys.add((String) o);
        }
        System.out.println(String.join(",", keys));
        return keys;
    }
    
    private ArrayList<String> dateColumns(){
        dateKeysList.getSelectionModel().getSelectedItems();
        ArrayList<String> keys = new ArrayList<String>();
        for (Object o: dateKeysList.getSelectionModel().getSelectedItems()){
            keys.add((String) o);
        }
        System.out.println(String.join(",", keys));
        return keys;
    }
         
}
