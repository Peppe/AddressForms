package com.example.addressforms.fields;

import java.util.Calendar;
import java.util.Date;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;

public class SimpleDateField extends CustomField {

    private static final long serialVersionUID = -6387909886861821720L;
    private NativeSelect day;
    private NativeSelect month;
    private NativeSelect year;

    public enum Month {
        JANUARY(1, "January"), FEBUARY(2, "Febuary"), MARS(3, "Mars"), APRIL(4,
                "April"), MAY(5, "May"), JUNE(6, "June"), JULY(7, "July"), AUGUST(
                8, "August"), SEPTEMBER(9, "September"), OCTOBER(10, "October"), NOVEMBER(
                11, "November"), DECEMBER(12, "December");

        private int monthNr;
        private String name;

        private Month(int monthNr, String name) {
            this.monthNr = monthNr;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getMonthNr() {
            return monthNr;
        }

        public String getName() {
            return name;
        }
    }

    public SimpleDateField() {
        this(null);
    }

    public SimpleDateField(String caption) {
        setCaption(caption);
        day = new NativeSelect();
        day.addItem("Day");
        for (int i = 1; i < 32; i++) {
            day.addItem(i + "");
        }
        day.select("Day");

        month = new NativeSelect();
        month.addItem("Month");
        month.addItem(Month.JANUARY);
        month.addItem(Month.FEBUARY);
        month.addItem(Month.MARS);
        month.addItem(Month.APRIL);
        month.addItem(Month.MAY);
        month.addItem(Month.JUNE);
        month.addItem(Month.JULY);
        month.addItem(Month.AUGUST);
        month.addItem(Month.SEPTEMBER);
        month.addItem(Month.OCTOBER);
        month.addItem(Month.NOVEMBER);
        month.addItem(Month.DECEMBER);
        month.select("Month");

        year = new NativeSelect();
        year.addItem("Year");
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i > 1900; i--) {
            year.addItem(i + "");
        }
        year.select("Year");
        day.setNullSelectionAllowed(false);
        month.setNullSelectionAllowed(false);
        year.setNullSelectionAllowed(false);

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(day);
        layout.addComponent(month);
        layout.addComponent(year);
        layout.setWidth("100%");
        layout.setHeight(null);
        day.setWidth("100%");
        month.setWidth("100%");
        year.setWidth("100%");
        setCompositionRoot(layout);
        addValidator(new AbstractValidator("Please select a date") {
            private static final long serialVersionUID = 6023987693208168293L;

            public boolean isValid(Object value) {
                if (day.getValue() == "Day") {
                    return false;
                }
                if (month.getValue() == "Month") {
                    return false;
                }
                if (year.getValue() == "Year") {
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Class<?> getType() {
        return Date.class;
    }

    @Override
    protected void setInternalValue(Object newValue) {
        if (newValue == null) {
            day.select("Day");
            month.select("Month");
            year.select("Year");
            return;
        }
        if (newValue instanceof Date) {
            Date date = (Date) newValue;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // FIXME Integer to int conversion problem
            day.select(calendar.get(Calendar.DAY_OF_MONTH) + "");
            int monthNr = calendar.get(Calendar.MONTH);
            Month[] months = Month.values();
            month.select(months[monthNr]);
            year.select(calendar.get(Calendar.YEAR) + "");
            return;
        }
        throw new ConversionException(
                "Only Date.class accepted in setValue of SimpleDateField");
    }

    @Override
    public void setValue(Object newValue) throws ReadOnlyException,
            ConversionException {
        setInternalValue(newValue);
    }

    @Override
    public Object getValue() {
        Calendar calendar = Calendar.getInstance();
        Object dayValue = day.getValue();
        Object monthValue = month.getValue();
        Object yearValue = year.getValue();

        if (dayValue != "Day") {
            calendar.set(Calendar.DAY_OF_MONTH,
                    new Integer(dayValue.toString()));
        }
        if (monthValue != "Month") {
            calendar.set(Calendar.MONTH, ((Month) monthValue).getMonthNr() - 1);
        }
        if (yearValue != "Year") {
            calendar.set(Calendar.YEAR, new Integer(yearValue.toString()));
        }
        return calendar.getTime();
    }

}
