package com.team22.project_team_22_2018.view.util;

import javafx.beans.NamedArg;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Этот клас нужен для автонумерации строк в таблице
 */
public class NumberTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private final int startNumber;

    public NumberTableCellFactory(@NamedArg("startNumber") final int startNumber) {
        this.startNumber = startNumber;
    }

    public NumberTableCellFactory() {
        this(1);
    }

    public static class NumberTableCell<S, T> extends TableCell<S, T> {
        private final int startNumber;

        public NumberTableCell(final int startNumber) {
            this.startNumber = startNumber;
        }

        @Override
        public void updateItem(final T item, final boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? "" : Integer.toString(startNumber + getIndex()));
        }
    }

    @Override
    public TableCell<S, T> call(final TableColumn<S, T> param) {
        return new NumberTableCell<>(startNumber);
    }

    public static <T> TableColumn<T, Void> createNumberColumn(final String text, final int startNumber) {
        final TableColumn<T, Void> column = new TableColumn<>(text);
        column.setSortable(false);
        column.setEditable(false);
        column.setCellFactory(new NumberTableCellFactory<>(startNumber));
        return column;
    }

}
