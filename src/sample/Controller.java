package sample;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateFrom;


    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField saveAmount;

    @FXML
    private TextField initialAmount;

    @FXML
    private TextField expensesAmount;

    @FXML
    private Button actionBtn;

    @FXML
    private Text totalAmount;

    @FXML
    void initialize() {
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now().plusDays(30));
        actionBtn.setOnAction(actionEvent -> {
            totalAmount.setText(calculationAmount(initialAmount, expensesAmount, saveAmount, dateTo, dateFrom));
        }     );
    }

    private static String calculationAmount(TextField initialAmount,
                                            TextField expensesAmount,
                                            TextField saveAmount,
                                            DatePicker dateTo,
                                            DatePicker dateFrom) {
        // convert to double
        double initialAmountD = ParseDouble(initialAmount.getText().replaceAll("[^\\d.]", ""));
        double expensesAmountD = ParseDouble(expensesAmount.getText().replaceAll("[^\\d.]", ""));
        double saveAmountD = ParseDouble(saveAmount.getText().replaceAll("[^\\d.]", ""));

        if (initialAmountD < 0 || expensesAmountD < 0 || saveAmountD < 0) {
            return "Values should be > 0";
        }

        double calcTotalAmount =  initialAmountD - expensesAmountD - saveAmountD;



        long datesInPeriod = Duration.between( dateFrom.getValue().atStartOfDay(), dateTo.getValue().atStartOfDay()).toDays();

        if (datesInPeriod < 0 ) return "End period should after Start period";
        double avgAmount = calcTotalAmount / datesInPeriod;
        if (avgAmount < 0 ) {
            return "You have too small initial amount to save for the period";
        }else {
            return String.format("%.2f",avgAmount);
        }

    }

    private static double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
}
