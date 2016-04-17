/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import Mail.EmailOptions;
import Mail.MailListView;
import Reporting.TableReport;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import fxmltest.computing.BasicStatisticsProfiler;
import fxmltest.computing.BasicStatisticsService;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.ColumnProfilingStats;
import fxmltest.computing.ColumnProfilingStatsRow;
import fxmltest.computing.ProfilingScheduler;
import fxmltest.computing.ReferentialIntegrityEngine;
import fxmltest.computing.TableInfo;
import fxmltest.computing.TablesFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import testhierarchie.Graphics.ColumnComboBox;
import testhierarchie.Graphics.ProgressIndicatorGraph;
import testhierarchie.Graphics.RegexFieldValidator;
import testhierarchie.Graphics.ScheduleTasksDisplay;
import testhierarchie.Graphics.TableComboBox;
import testhierarchie.Graphics.ThresholdFormGrid;
import testhierarchie.Graphics.ToolbarIcon;

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
    private static ArrayList<TableInfo> tables;
    private int tableNumber;
    private static final ObservableList<ColumnProfilingStatsRow> data
            = FXCollections.observableArrayList();
    private int selectedCol = 1;
    private static ProfilingScheduler scheduler = new ProfilingScheduler();

    @FXML
    private TreeView tableTreeView;
    @FXML
    private AnchorPane anchor;
    @FXML
    private static TextArea sqlArea;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Label resultTableLabel;
    @FXML
    private PieChart resultsChart;
    @FXML
    private StackedBarChart<?, ?> resultsHisto;
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
    private WebView webView;
    @FXML
    private HBox notificationsBox;
    @FXML
    private JFXTextField addMailField;
    @FXML
    private JFXButton addMailButton;
    
    private ProgressIndicatorGraph dashboardCompletenessProgress;
    private ScheduleTasksDisplay schedulerTaskDisplay;
    private ObservableList<StackedBarChart.Series> barChartData;
    
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
    
    
    
    private static MailListView mailListView;
    private static TableComboBox parentTables;
    private static TableComboBox childTables;
    private static ColumnComboBox parentColumns;
    private static ColumnComboBox childColumns;
    private static JFXPopup loginPopup;
    
    
    
    
    
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

        initializeMailTab();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://localhost:4848/sense/app/C%3A%5CUsers%5CAshraf%5CDocuments%5CQlik%5CSense%5CApps%5CExecutive%20Dashboard/sheet/PfKsJK/state/analysis");    
        stopSpinner();
        
        completenessIndicator = new ProgressIndicatorGraph(0, 80, 80);
        uniquenessIndicator = new ProgressIndicatorGraph(0, 80, 80);
        completenessVBox.getChildren().add(0, completenessIndicator);
        uniquenessVBox.getChildren().add(0, uniquenessIndicator);
        
        Icon value = new Icon(AwesomeIcon.PIE_CHART, "30px", "-fx-text-fill: #E0E4CC;", "icon");
        sqlArea = new TextArea();
        value.setId("icon");
        badge = new JFXBadge(value);
        badge.setPosition(Pos.TOP_RIGHT);
        badge.setText("0");
        badge.setId("icons-badge");
        sqlArea.setText(badge.getStyle());
        badge.setPrefSize(50, 50);
        badge.setOnMouseClicked((e) -> {
            StackPane root = (StackPane) FXMLTest.getRoot();
            this.schedulerTaskDisplay = new ScheduleTasksDisplay(scheduler);
            loginPopup = new JFXPopup(root, schedulerTaskDisplay);
            loginPopup.setSource(badge);
            loginPopup.show(PopupVPosition.TOP, PopupHPosition.RIGHT);
        });
        notificationBox.getChildren().add(new ToolbarIcon(badge));

        
        ToolbarIcon schedulerStart = new ToolbarIcon(AwesomeIcon.PLAY);
        ToolbarIcon newConnection = new ToolbarIcon((AwesomeIcon.PLUG));
        newConnection.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                createLoginPopUp();
            }
        });
        
        schedulerStart.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                scheduler.start();
            }
            
        });
        controlBox.getChildren().addAll(newConnection, schedulerStart);
        controller = this;
        initializeTableTreeView();
        initializeTable();
        sqlArea.setWrapText(true);
        dashboardCompletenessProgress = new ProgressIndicatorGraph(0, 100, 100);
        dashboardVbox.getChildren().add(dashboardCompletenessProgress);
        initializeRefTab();
        


        //BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
        //System.out.println(profiler.profileTableQuery());
    }
    
    private void initializeMailTab(){
        
        mailToggle.setSelected(true);
        reportToggle.setSelected(true);
        //folderLabel.setText(EmailOptions.getFileDirectory());
        mailToggle.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                EmailOptions.setOn(newValue);
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
        getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");
//        mailListContainer.setPrefSize(220, 400);
//        mailListContainer.setMinSize(220, 400);
//        mailListContainer.setMaxSize(220, 400);
        //mailListContainer.getChildren();
        mailVBox.getChildren().add(2, mailListView);
        
    }

    public void initializeRefTab(){
        
        
        parentTables = new TableComboBox(getTables(), 0);
        childTables = new TableComboBox(getTables(), 1);
        
        parentTableBox.getChildren().add(parentTables);
        childTableBox.getChildren().add(childTables);
        
//        parentColumnBox.getChildren().add(new ColumnComboBox(tables.get(0)));
//        parentColumnBox.getChildren().add(new ColumnComboBox(tables.get(1)));
        
        parentColumns = new ColumnComboBox();
        childColumns = new ColumnComboBox();
        parentColumnBox.getChildren().add(parentColumns);
        childColumnBox.getChildren().add(childColumns);
    
        System.out.println(getTables().size());
        System.out.println(getTables().get(0).getName());
        System.out.println(getTables().get(1).getName());
        
        
        
    }
    
    public void initializeTableTreeView (){
        
        final TreeItem<String> treeRoot = new TreeItem<String>("Mes tables");
        ArrayList<TableInfo> tables = new ArrayList<TableInfo>();

        //tables = new TablesFactory("SYSTEM", "OracleAdmin1", "jdbc:oracle:thin:@localhost:1522:orcl").getTables();
        tables = new TablesFactory("hr", "hrpassword", "jdbc:oracle:thin:@localhost:1522:orcl").getTables();
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
    
    private void loadTable(){
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
                System.out.println(getData().get(selectedCol));
                loadChart();
            }
        });
        loadChart();
        
    }
    
    private void loadChart(){
        int selected = this.tableView.getSelectionModel().getSelectedIndex();

        ObservableList<PieChart.Data> pieChartData;
        String columnName;
        float nullCount = getData().get(this.selectedCol).getNbNull();
        float count = getData().get(this.selectedCol).getNbLines();
        float distinct = getData().get(this.selectedCol).getNbDistinct();

        pieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Null", (float) (nullCount)/count),
               new PieChart.Data("Not null", (float) (count-nullCount)/count ));
        completenessIndicator.setProgress((count - nullCount) / count);
        uniquenessIndicator.setProgress(distinct / count);
        
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
            StackPane root = (StackPane) FXMLTest.getRoot();
            StackPane content;
            content = (StackPane) FXMLLoader.load(getClass().getResource("login.fxml"));
            content.setId("jfx-dialog-layout");
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onContextMenuRequested(ContextMenuEvent event){
        TreeItem<String> ti;
        System.out.println(event.getSource());
        System.out.println(event.getSource().getClass());
        System.out.println(event.getSource().toString());
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
                    loadTable();
                    jaxbObjectToXML(table);
                    if (EmailOptions.isReport())
                        new TableReport(table);
                    dashboardCompletenessProgress.setProgress(getOverallCompleteness());
                    resetCursor();
                break;
            }
        });

        calculateService.start();
        startSpinner();

        
        } else{
            System.out.println(tableNumber);
        }
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
            StackPane root = (StackPane) FXMLTest.getRoot();
            StackPane content = new ThresholdFormGrid(getTables().get(tableNumber));
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
        }
    }
    
    @FXML
    private void scheduleMenuAction(ActionEvent event){
        // When the context menu add to schedule is pressed
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
                    JasperReportBuilder report = new TableReport(calculateService.getTable()).getReport();
                    scheduler.addReport(report);
                    dashboardCompletenessProgress.setProgress(getOverallCompleteness());
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
        sqlArea.setText(cct);
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
        FXMLTest.getRoot().getScene().setCursor(Cursor.WAIT);
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
    
    public static void setSqlAreaText(String text){
        sqlArea.setText(text);
    }
    
    public static ProfilingScheduler getScheduler(){
        return scheduler;
    }
    
    @FXML
    private void deleteFromScheduler(){
        this.schedulerTaskDisplay.removeSelectedTask();
    }
    
    @FXML
    private void integrityAction(){
        //refArea.
        String parentTable = parentTables.getValue().getText();
        String childTable = childTables.getValue().getText();
        String parentColumn = parentColumns.getSelectedColumn();
        String childColumn = childColumns.getSelectedColumn();
        ReferentialIntegrityEngine engine = 
                new ReferentialIntegrityEngine(parentTable, parentColumn, childTable, childColumn);
        String value = engine.referentialIntegritySampleQuery();
        int result = engine.checkReferentialIntegrity();
        refArea.setText(value);
        refResultLabel.setText(Integer.toString(result));
    }
    
    @FXML
    private void selectFolder(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File(EmailOptions.getFileDirectory());
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(FXMLTest.getMainStage());
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
    
    private static void jaxbObjectToXML(TableInfo table) {
 
        try {
            JAXBContext context = JAXBContext.newInstance(TableInfo.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // Write to System.out for debugging
            // m.marshal(emp, System.out);
 
            // Write to File
            m.marshal(table, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeLoginPopup(){
        loginPopup.close();
    }
    
    private void resetCursor(){
        FXMLTest.getRoot().getScene().setCursor(Cursor.DEFAULT);
    }
    
}
