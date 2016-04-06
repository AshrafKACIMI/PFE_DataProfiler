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
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import fxmltest.computing.BasicStatisticsProfiler;
import fxmltest.computing.BasicStatisticsService;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.ColumnProfilingStats;
import fxmltest.computing.ColumnProfilingStatsRow;
import fxmltest.computing.ProfilingScheduler;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import testhierarchie.Graphics.ProgressIndicatorGraph;
import testhierarchie.Graphics.RegexFieldValidator;
import testhierarchie.Graphics.ScheduleTasksDisplay;
import testhierarchie.Graphics.ThresholdFormGrid;

/**
 *
 * @author Ashraf
 */
public class FXMLDocumentController implements Initializable {
    private static final String FILE_NAME = "jaxb-emp.xml";
    
    private static JFXBadge badge;    
    private Label label;
    private ArrayList<TableInfo> tables;
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
    
    private ProgressIndicatorGraph completenessProgress;
    private ScheduleTasksDisplay schedulerTaskDisplay;
    private ObservableList<StackedBarChart.Series> barChartData;
    
    @FXML
    private TableView tableView;
    @FXML
    private JFXSpinner spinner;
    private static BasicStatisticsProfiler profiler;
    @FXML
    private MenuItem sendMailMenu;
    
    private static MailListView mailListView;
    
    @FXML
    private AnchorPane mailListContainer;
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
        Icon value = new Icon("STAR");
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
            JFXPopup popup = new JFXPopup(root, schedulerTaskDisplay);
            popup.setSource(badge);
            popup.show(PopupVPosition.TOP, PopupHPosition.LEFT);
        });
        notificationsBox.getChildren().add(badge);
        controller = this;
        initializeTableTreeView();
        initializeTable();
        sqlArea.setWrapText(true);
        completenessProgress = new ProgressIndicatorGraph(0, 100, 100);
        dashboardVbox.getChildren().add(completenessProgress);
        //BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
        //System.out.println(profiler.profileTableQuery());
    }
    
    private void initializeMailTab(){
        
        RegexFieldValidator validator = new RegexFieldValidator(RegexFieldValidator.EMAIL_PATTERN);
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"1em",";","error"));
        addMailField.getValidators().add(validator);
        addMailField.focusedProperty().addListener((o,oldVal,newVal)->{
                if(!newVal) addMailField.validate();
        });
        
        mailListView = new MailListView(new ArrayList<String>());
        getMailListView().addMail("kacimi.achraf@gmail.com");
        getMailListView().addMail("ba_kacimi_el_hassani@esi.dz");
        mailListContainer.getChildren().add(getMailListView());

        //mailListContainer.getChildren();
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
            ColumnInfo c = tables.get(tableNumber).getColumnByName(d.getColumnName());
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
        int nullCount = getData().get(this.selectedCol).getNbNull();
        int count = getData().get(this.selectedCol).getNbLines();

        pieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Null", (float) (nullCount)/count),
               new PieChart.Data("Not null", (float) (count-nullCount)/count ));
        System.out.print("Null: ");
        System.out.println(((float) nullCount)/count);
        System.out.print("Not Null: ");
        System.out.println((float) (count - nullCount)/count);
        //System.out.println("Not Null = "+ (count - nullCount)/count);
         
        
        this.resultsChart.setData(pieChartData);
        this.resultsChart.setTitle(getData().get(this.selectedCol).getColumnName());
        
    }
    
    
    private int getTableNumber(String tableName){
        for (int i = 0; i < tables.size(); i++)
            if (tables.get(i).getName().equals(tableName))
                    return i;
        return -1;
    }
    
    private void createLoginPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            //Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.setTitle("New connexion");
//            stage.show();
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
            TableInfo table = tables.get(tableNumber);

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
                    //new TableReport(table);
                    completenessProgress.setProgress(getOverallCompleteness());
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
            StackPane content = new ThresholdFormGrid(tables.get(tableNumber));
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
        }
    }
    
    @FXML
    private void scheduleMenuAction(ActionEvent event){
        // When the context menu add to schedule is pressed
        if (tableNumber>= 0){
            TableInfo table = tables.get(tableNumber);

            // We create a new Service that handles the profiling thread
            final BasicStatisticsService calculateService = 
                        new BasicStatisticsService(table);
            
            //We      
            calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    loadTable();
                    new TableReport(calculateService.getTable());
                    completenessProgress.setProgress(getOverallCompleteness());
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
        for (TableInfo table: this.tables){
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
    
}
