package com.hans.newslook.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MortgageActivity extends BaseActivity {


    @BindView(R.id.calculate)
    Button calculate;
    @BindView(R.id.load_show)
    TextView loadShow;
    @BindView(R.id.loan_amount)
    EditText loanAmount;
    @BindView(R.id.loan_time)
    EditText loanTime;
    @BindView(R.id.loan_rates)
    EditText loanRates;

    private double totalLoanAmount = 1000000d;

    private double lendingRates = 0.0588d;

    private int loanMonth = 360;


//    200000*（6.80%/12）*（1+6.80%/12）^180/[（1+6.80%/12）^180-1]=1775.367832

    @Override
    protected void init() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mortgage;
    }

    @OnClick(R.id.calculate)
    public void onViewClicked() {
        double totalE = 0, reteE = 0;
        int timeE = 0;
        if (loanAmount.getEditableText() != null && loanAmount.getEditableText().toString().equalsIgnoreCase("")) {
            totalE = Double.valueOf(loanAmount.getEditableText().toString());
        }
        if (loanRates.getEditableText() != null)
            reteE = Double.valueOf(loanRates.getEditableText().toString());
        if (loanTime.getEditableText() != null)
            timeE = Integer.valueOf(loanTime.getEditableText().toString());

        if (totalE > 0) {
            totalLoanAmount = totalE;
        }
        if (reteE > 0) {
            lendingRates = totalE / 100;
        }
        if (timeE > 0) {
            loanMonth = timeE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("totalE:" + totalE);
        stringBuilder.append("reteE:" + reteE);
        stringBuilder.append("timeE:" + timeE);

        stringBuilder.append("totalLoanAmount:" + totalLoanAmount);
        stringBuilder.append("lendingRates:" + lendingRates);
        stringBuilder.append("loanMonth:" + loanMonth);

        stringBuilder.append("月供为：" + calculate(totalLoanAmount, lendingRates, loanMonth));


        loadShow.setText(stringBuilder.toString());

    }

    private double calculate(double total, double rates, int time) {
        double a = total;
        double b = (rates / 12);
        double c = getPow(1 + b, time);
        double d = c - 1;
        return a * b * c / d;
    }


    private double getPow(double base, double exponent) {
        return Math.pow(base, exponent);
    }


}
