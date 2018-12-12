package com.team22.Project_team_22_2018.util;
import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.view.session_data.SessionDataManagerTask;
import com.team22.Project_team_22_2018.view.session_data.SessionDataTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

//import com.netcracker.edu.fxmodel.Root;
//import com.netcracker.edu.fxmodel.Project;
//import com.netcracker.edu.fxmodel.Task;

/**
 * @author Tree
 */
//public class Tree {
//
//    private TreeView<SessionDataTask> treeView ;
//    private TableView<SessionDataTask> tableView ;
//    private ManagerTask managerTask = RuntimeHolder.getModelHolder();
//    private Controller controller = RuntimeHolder.getControllerHolder();
//
//    private final List<Class<? extends SessionDataTask>> itemTypes = Arrays.asList(
//            SessionDataTask.class
//    );
//
//    public Tree(TreeView tree, TableView tb, Label label, DatePicker datePicker, ObservableList<SessionDataTask> accounts, ContextMenu contextMenu, SessionDataManagerTask root) {
//        treeView = tree;
//        tableView = tb;
//
//
//        treeView.setContextMenu(contextMenu);
//        TreeItem<SessionDataTask> treeRoot = createItem(root);
//
//        treeView.setRoot(treeRoot);
//        treeView.setShowRoot(true);
//
//        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                e -> {
//                    if (e.getButton() == MouseButton.SECONDARY) {
//                        EventTarget target = e.getTarget();
//                        if (target instanceof TreeCell
//                                || ((Node) target).getParent() instanceof TreeCell) {
//                            treeView.getContextMenu().hide();
//                        } else {
//                            // hide the context menu when click event is outside table row
//                            treeView.getContextMenu().hide();
//                        }
//                    }
//                });
//
//
//
//
//        treeView.setCellFactory(tv -> {
//
//            TreeCell<SessionDataTask> cell = new TreeCell<SessionDataTask>() {
//
//                @Override
//                protected void updateItem(SessionDataTask item, boolean empty) {
//                    super.updateItem(item, empty);
//                    textProperty().unbind();
//                    if (empty) {
//                        setText(null);
//                        itemTypes.stream().map(Tree.this::asPseudoClass)
//                                .forEach(pc -> pseudoClassStateChanged(pc, false));
//                    } else {
//                        textProperty().bind(item.nameProperty());
//                        PseudoClass itemPC = asPseudoClass(item.getClass());
//                        itemTypes.stream().map(Tree.this::asPseudoClass)
//                                .forEach(pc -> pseudoClassStateChanged(pc, itemPC.equals(pc)));
//                    }
//                }
//            };
//
//            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
//                if (isNowHovered && (! cell.isEmpty())) {
//                    //System.out.println("Mouse hover on "+cell.getItem().getSummary());
//                }
//            });
//
//
//            cell.setOnMouseClicked(event -> {
//                if (! cell.isEmpty()) {
//
//
////                    managerTask.setTask(cell.getItem());
//                    controller.setTask(0,cell.getItem());
////                    tableView.setItems(managerTask.getCurrent().agregate());
////                    label.setText(managerTask.getCurrent().getSummary());
////                    datePicker.setValue(managerTask.getCurrent().getDeadline());
//
//                    //System.out.println(managerTask.getCurrentTask().getSummary());
//                }
//
//            });
//
//            return cell ;
//        });
//    }
//
//    public TreeView<SessionDataManagerTask> getTreeView() {
//        return treeView ;
//    }
//
//    private TreeItem<SessionDataManagerTask> createItem(SessionDataManagerTask object) {
//
//        // create tree item with children from game object's list:
//
//        TreeItem<SessionDataManagerTask> item = new TreeItem<>(object);
//        item.setExpanded(true);
//        item.getChildren().addAll(object.getProjectList().stream().map(this::createItem).collect(toList()));
//
////        List<Project> list =object.getProjectList();
////
////        for (int i=0; i<list.size();i++) {
////            item.getChildren().addAll(createItem(list.get(i)));
////        }
//
//
//        // update tree item's children list if game object's list changes:
//
//        (ObservableList)object.getTasks().addListener((ListChangeListener.Change<? extends SessionDataManagerTask> c) -> {
//            while (c.next()) {
//                if (c.wasAdded()) {
//                    item.getChildren().addAll(c.getAddedSubList().stream().map(this::createItem).collect(toList()));
//                }
//                if (c.wasRemoved()) {
//                    item.getChildren().removeIf(treeItem -> c.getRemoved().contains(treeItem.getValue()));
//                }
//            }
//        });
//
//        return item ;
//    }
//
//    private PseudoClass asPseudoClass(Class<?> clz) {
//        return PseudoClass.getPseudoClass(clz.getSimpleName().toLowerCase());
//    }
//
//}
//public class Tree extends TreeView{
//
//    private final ObservableList<SessionDataTask> tasks;
//    private final ManagerTask managerTask = RuntimeHolder.getModelHolder();
//
//    public Tree( ObservableList<SessionDataTask> tasks) {
//        this.tasks = tasks;
//
//        TreeItem<String> rootItem = new TreeItem<String>();
//        rootItem.setExpanded( true );
//        this.setShowRoot( false );
//        this.setCellFactory( p -> new TreeCell<String>() {
//
//            public void updateItem( SessionDataTask t, boolean empty ) {
//                super.updateItem( t.getName(), empty );
//                setText( t == null ? null : t.getName() );
//            }
//        });
//
//        for ( SessionDataTask p : tasks ) {
//            TreeItem<String> pItem = new TreeItem<String>( p.getName() );
//            rootItem.getChildren().add( pItem );
//            SessionDataTask fake1 = new SessionDataTask() {
//                public String getTitle() { return "Works"; }
//                private StringProperty property = new SimpleStringProperty( "-" );
//            };
//            SessionDataTask fake2 = new SessionDataTask() {
//                public String getTitle() { return "Certificates"; }
//                private StringProperty property = new SimpleStringProperty( "-" );
//            };
//            TreeItem<String> f1Item = new TreeItem<String>( fake1.getName() );
//            TreeItem<String> f2Item = new TreeItem<String>( fake2.getName() );
//            pItem.getChildren().addAll( f1Item, f2Item );
//            for ( SessionDataTask w : p.getSubTasks() ) {
//                f1Item.getChildren().add( new TreeItem<String>( w.getName() ) );
//            }
//        }
//        setRoot( rootItem );
//
//        EventDispatcher originalDispatcher = this.getEventDispatcher();
//        this.setEventDispatcher( new NotExpandedEventDispatcher( originalDispatcher ) );
//    }
//
//    private class NotExpandedEventDispatcher implements EventDispatcher {
//
//        private final EventDispatcher originDispatcher;
//
//        public NotExpandedEventDispatcher( EventDispatcher originDispatcher ){
//            this.originDispatcher = originDispatcher;
//        }
//
//        @Override
//        public Event dispatchEvent(Event event, EventDispatchChain tail ) {
//            if ( event instanceof MouseEvent) {
//                if ( ((MouseEvent)event).getButton() == MouseButton.PRIMARY &&
//                        ((MouseEvent)event).getClickCount() >= 2 ) {
//                    if ( !event.isConsumed() ) {
//                        TreeItem<SessionDataTask> selectedItem = (TreeItem<SessionDataTask>) getSelectionModel().getSelectedItem();
////                        managerTask.doSomething( selectedItem .getValue() );
//                    }
//                    event.consume();
//                }
//            }else if ( event instanceof KeyEvent){
//                if ( ((KeyEvent)event).getCode() == KeyCode.ENTER ){
//                    if ( !event.isConsumed() ) {
//                        TreeItem<SessionDataTask> selectedItem = (TreeItem<SessionDataTask>) getSelectionModel().getSelectedItem();
////                        managerTask.doSomething( selectedItem .getValue() );
//                    }
//                    event.consume();
//                }
//            }
//            return originDispatcher.dispatchEvent( event, tail );
//        }
//    }
//}

public class Tree {

    private TreeView<SessionDataTask> treeView ;
    private TableView<SessionDataTask> tableView ;
    private Controller controller=RuntimeHolder.getControllerHolder();
//    private Root data = RuntimeDataHolder.getHolder();

    private final List<Class<? extends SessionDataTask>> itemTypes = Arrays.asList(
            SessionDataTask.class
    );

    public Tree(TreeView tree, TableView tb, /*Label label, DatePicker datePicker, ObservableList<SessionDataTask> accounts,*/ /*ContextMenu contextMenu,*/ SessionDataTask root) {
        treeView = tree;
        tableView = tb;


//        treeView.setContextMenu(contextMenu);
        TreeItem<SessionDataTask> treeRoot = new TreeItem<>();//createItem(root);

        treeView.setRoot(treeRoot);
        treeView.setShowRoot(true);

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        EventTarget target = e.getTarget();
                        if (target instanceof TreeCell
                                || ((Node) target).getParent() instanceof TreeCell) {
                            treeView.getContextMenu().hide();
                        } else {
                            // hide the context menu when click event is outside table row
                            treeView.getContextMenu().hide();
                        }
                    }
                });




        treeView.setCellFactory(tv -> {

            TreeCell<SessionDataTask> cell = new TreeCell<SessionDataTask>() {

                @Override
                protected void updateItem(SessionDataTask item, boolean empty) {
                    super.updateItem(item, empty);
                    textProperty().unbind();
                    if (empty) {
                        setText(null);
                        itemTypes.stream().map(Tree.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, false));
                    } else {
                        textProperty().bind(item.nameProperty());
                        PseudoClass itemPC = asPseudoClass(item.getClass());
                        itemTypes.stream().map(Tree.this::asPseudoClass)
                                .forEach(pc -> pseudoClassStateChanged(pc, itemPC.equals(pc)));
                    }
                }
            };

            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && (! cell.isEmpty())) {
                    //System.out.println("Mouse hover on "+cell.getItem().getSummary());
                }
            });


            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {

//                    controller.setTask(0,cell.getItem());
//                    data.setCurrent(cell.getItem());
//                    tableView.setItems(data.getCurrent().agregate());

                    tableView.setItems(controller.getTasks());
//                    label.setText(data.getCurrent().getSummary());
//                    datePicker.setValue(data.getCurrent().getDeadline());

                    //System.out.println(data.getCurrentTask().getSummary());
                }

            });

            return cell ;
        });
    }

    public TreeView<SessionDataTask> getTreeView() {
        return treeView ;
    }

    private TreeItem<SessionDataTask> createItem(SessionDataTask object) {

        // create tree item with children from game object's list:

        TreeItem<SessionDataTask> item = new TreeItem<>(object);
        item.setExpanded(true);
        item.getChildren().addAll(object.getSubTasks().stream().map(this::createItem).collect(toList()));

        List<SessionDataTask> list =object.getSubTasks();

        for (int i=0; i<list.size();i++) {
            item.getChildren().addAll(createItem(list.get(i)));
        }


        // update tree item's children list if game object's list changes:
//        ObservableList<SessionDataTask> sessionDataTasks = (ObservableList<SessionDataTask>) object.getSubTasks();
//        sessionDataTasks.addListener((ListChangeListener.Change<? extends SessionDataTask> c) -> {
//            while (c.next()) {
//                if (c.wasAdded()) {
//                    item.getChildren().addAll(c.getAddedSubList().stream().map(this::createItem).collect(toList()));
//                }
//                if (c.wasRemoved()) {
//                    item.getChildren().removeIf(treeItem -> c.getRemoved().contains(treeItem.getValue()));
//                }
//            }
//        });

        return item ;
    }

    private PseudoClass asPseudoClass(Class<?> clz) {
        return PseudoClass.getPseudoClass(clz.getSimpleName().toLowerCase());
    }

}