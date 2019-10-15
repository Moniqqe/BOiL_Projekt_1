package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller {

    int amountOfSuppliers;
    int amountOfReceivents;
    int supplyTab[];
    int demandTab[];
    List<Cell> cellList = new ArrayList<>();


    @FXML
    private GridPane mainGrid;

    @FXML
    private TextField receiverTxt;

    @FXML
    private TextField supplierTxt;

    @FXML
    private TextField[][] txtFieldTab;

    @FXML
    private Label costLabel;

    @FXML
    void initialize() {
        supplierTxt.setAlignment(Pos.CENTER);
        receiverTxt.setAlignment(Pos.CENTER);
        supplierTxt.setText("1");
        receiverTxt.setText("1");
        costLabel.setAlignment(Pos.CENTER);

        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setStyle("-fx-background-color:#f0f1ff;");

        while (mainGrid.getRowConstraints().size() > 0) {
            mainGrid.getRowConstraints().remove(0);
        }

        while (mainGrid.getColumnConstraints().size() > 0) {
            mainGrid.getColumnConstraints().remove(0);
        }
    }

    @FXML
    void okButtonAction(ActionEvent event) {
        amountOfSuppliers = Integer.parseInt(supplierTxt.getText());
        amountOfReceivents = Integer.parseInt(receiverTxt.getText());

        txtFieldTab = new TextField[amountOfReceivents + 1][amountOfSuppliers + 1];

        supplyTab = new int[amountOfSuppliers];
        demandTab = new int[amountOfReceivents];


        for (int i = 0; i < amountOfReceivents + 1; i++) {
            for (int j = 0; j < amountOfSuppliers + 1; j++) {

                txtFieldTab[i][j] = new TextField();
                txtFieldTab[i][j].setMinSize(1, 1);
                txtFieldTab[i][j].setPrefSize(70, 70);
                txtFieldTab[i][j].setStyle("-fx-border-color: rgba(0,0,0,0.58); -fx-border-width: 1 1 1 1;");
                txtFieldTab[i][j].setAlignment(Pos.CENTER);
                mainGrid.add(txtFieldTab[i][j], i, j);

            }
        }

        txtFieldTab[0][0].setText("-");
        txtFieldTab[0][0].setStyle("-fx-font-size:14px; -fx-border-color: rgba(0,0,0,0.58); -fx-border-width: 1 1 1 1;");
        txtFieldTab[0][0].setEditable(false);

        for (int i = 1; i < amountOfReceivents + 1; i++) {
            txtFieldTab[i][0].setStyle("-fx-font-size:14px; -fx-border-color: rgba(0,0,0,0.58); -fx-border-width: 1 1 1 1;");
            txtFieldTab[i][0].setText("O" + i);
        }

        for (int j = 1; j < amountOfSuppliers + 1; j++) {
            txtFieldTab[0][j].setStyle("-fx-font-size:14px; -fx-border-color: rgba(0,0,0,0.58); -fx-border-width: 1 1 1 1;");
            txtFieldTab[0][j].setText("D" + j);
        }

    }

    @FXML
    void MaxEMClick(){
        compute();
        calculateMax();
    }

    @FXML
    void MinEMClick(){
        compute();
        calculateMin();
    }

    @FXML
    void PZClick(){
        compute();
        calculatePZ();
    }

    void compute() {
        for (int i = 1; i < amountOfReceivents + 1; i++) {
            demandTab[i - 1] = Integer.parseInt(txtFieldTab[i][0].getText());
            txtFieldTab[i][0].setEditable(false);
            //System.out.println(demandTab[i - 1]);
        }
        for (int j = 1; j < amountOfSuppliers + 1; j++) {
            supplyTab[j - 1] = Integer.parseInt(txtFieldTab[0][j].getText());
            txtFieldTab[0][j].setEditable(false);
            //System.out.println(supplyTab[j-1]);
        }

        for (int i = 1; i < amountOfReceivents + 1; i++) {
            for (int j = 1; j < amountOfSuppliers + 1; j++) {
                Cell newCell = new Cell(j, i, Integer.parseInt(txtFieldTab[i][j].getText()));
                newCell.displayCell();
                txtFieldTab[i][j].setEditable(false);
                cellList.add(newCell);
            }
        }

    }

    Cell findMax() {
        Cell maxC = cellList.get(0);
        int maxV = cellList.get(0).cost;
        for (int i = 1; i < cellList.size(); i++) {
            if (maxV < cellList.get(i).getCost()) {
                maxV = cellList.get(i).getCost();
                maxC = cellList.get(i);
            }
        }
        //System.out.println(maxV);
        return maxC;
    }

    Cell findMin(){
        Cell minC = cellList.get(0);
        int minV = cellList.get(0).cost;
        for (int i = 1; i < cellList.size(); i++) {
            if (minV > cellList.get(i).getCost()) {
                minV = cellList.get(i).getCost();
                minC = cellList.get(i);
            }
        }
        //System.out.println(minV);
        return minC;
    }

    Cell findNext(){
        Cell nextC = cellList.get(0);
        for (int i = 1; i < cellList.size(); i++) {
            if(cellList.get(i).getCost() != 100)
                nextC = cellList.get(i);
        }
        return nextC;
    }

    void calculateMax() {
        Cell temp = findMax();
        while (temp.getCost() != 0){
            int actX = temp.getX(), actY = temp.getY();
            System.out.println();
            System.out.println(actX + ", " + actY);

            if (supplyTab[temp.getX() - 1] > demandTab[temp.getY() - 1]) {
                temp.value = demandTab[temp.getY() - 1];
                System.out.println(">");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] -= demandTab[temp.getY() - 1];
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(0);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] < demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("<");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                demandTab[temp.getY() - 1] -= supplyTab[temp.getX() - 1];
                supplyTab[temp.getX() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(0);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] == demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("=");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] = 0;
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(0);
                    }
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(0);
                    }
                }
            }
            display();
            temp = findMax();
        }
        finalDisplay();
    }

    void calculateMin(){
        Cell temp = findMin();
        while (temp.getCost() != 100){
            int actX = temp.getX(), actY = temp.getY();
            System.out.println();
            System.out.println(actX + ", " + actY);

            if (supplyTab[temp.getX() - 1] > demandTab[temp.getY() - 1]) {
                temp.value = demandTab[temp.getY() - 1];
                System.out.println(">");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] -= demandTab[temp.getY() - 1];
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] < demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("<");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                demandTab[temp.getY() - 1] -= supplyTab[temp.getX() - 1];
                supplyTab[temp.getX() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] == demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("=");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] = 0;
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(100);
                    }
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            display();
            temp = findMin();
        }
        finalDisplay();
    }

    void calculatePZ(){
        Cell temp = findNext();
        while (temp.getCost() != 100){
            int actX = temp.getX(), actY = temp.getY();
            System.out.println();
            System.out.println(actX + ", " + actY);

            if (supplyTab[temp.getX() - 1] > demandTab[temp.getY() - 1]) {
                temp.value = demandTab[temp.getY() - 1];
                System.out.println(">");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] -= demandTab[temp.getY() - 1];
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] < demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("<");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                demandTab[temp.getY() - 1] -= supplyTab[temp.getX() - 1];
                supplyTab[temp.getX() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            if (supplyTab[temp.getX() - 1] == demandTab[temp.getY() - 1]) {
                temp.value = supplyTab[temp.getX() - 1];
                System.out.println("=");
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                supplyTab[temp.getX() - 1] = 0;
                demandTab[temp.getY() - 1] = 0;
                System.out.println(supplyTab[temp.x - 1] + ", " + demandTab[temp.y - 1]);
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getY() == actY) {
                        cellList.get(i).setCost(100);
                    }
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if (cellList.get(i).getX() == actX) {
                        cellList.get(i).setCost(100);
                    }
                }
            }
            display();
            temp = findNext();
        }
        finalDisplay();
    }

    void display(){
        for (int i = 0; i < cellList.size(); i++)   {
            cellList.get(i).displayCell();
        }
    }

    void finalDisplay(){

        int costSum = 0;

        for (int i = 0; i < cellList.size(); i++)   {
            String txt;
            costSum += cellList.get(i).getFinalCost()*cellList.get(i).getValue();
            if (cellList.get(i).getValue() == 0)
                txt = "X";
            else
                txt = Integer.toString(cellList.get(i).getValue());
           txtFieldTab[cellList.get(i).getY()][cellList.get(i).getX()].setText(txt);
        }

        costLabel.setText("Kc: " + Integer.toString(costSum));
        txtFieldTab[0][0].setText("-");

        for (int i = 1; i < amountOfReceivents + 1; i++) {
            txtFieldTab[i][0].setText("O" + i);
        }

        for (int j = 1; j < amountOfSuppliers + 1; j++) {
            txtFieldTab[0][j].setText("D" + j);
        }
    }

    @FXML
    void clearcCick() {
        mainGrid.getChildren().clear();
        costLabel.setText("");
        cellList.clear();
        System.out.println();
        System.out.println(cellList.isEmpty());
        System.out.println();
    }


}
