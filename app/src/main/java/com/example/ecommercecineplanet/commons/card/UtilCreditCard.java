package com.example.ecommercecineplanet.commons.card;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.ecommercecineplanet.commons.snackbar.SnackBarHelper;

import java.util.Calendar;

public class UtilCreditCard {

    public static TextWatcher formatExpirationDate(final EditText editText) {
        return new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    return;
                }
                isUpdating = true;
                String text = s.toString().replace("/", "");
                if (text.length() > 4) {
                    text = text.substring(0, 4);
                }
                StringBuilder formattedText = new StringBuilder(text);
                if (text.length() > 2) {
                    formattedText.insert(2, '/');
                }
                editText.setText(formattedText.toString());
                editText.setSelection(formattedText.length());
                isUpdating = false;
            }
        };
    }

    public static TextWatcher formatCreditCard(final EditText editText) {
        return new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    return;
                }
                isUpdating = true;
                String text = s.toString().replace(" ", "");
                if (text.length() > 16) {
                    text = text.substring(0, 16);
                }
                StringBuilder formattedText = new StringBuilder(text);
                for (int i = 4; i < formattedText.length(); i += 5) {
                    formattedText.insert(i, ' ');
                }
                editText.setText(formattedText.toString());
                editText.setSelection(formattedText.length());
                isUpdating = false;
            }
        };
    }
    public static TextWatcher insert(final EditText editText, final String mask) {
        return new TextWatcher() {
            private boolean isUpdating = false;
            private String old = "";



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                StringBuilder mascara = new StringBuilder();

                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                } else {
                    int i = 0;
                    char[] maskChars = mask.toCharArray();
                    int maskLength = maskChars.length;

                    for (char m : maskChars) {
                        if (m != '#' && str.length() > old.length() || m != '#' && str.length() < old.length() && str.length() != i) {
                            mascara.append(m);
                        } else {
                            try {
                                mascara.append(str.charAt(i));
                            } catch (Exception e) {
                                break;
                            }
                            i++;
                        }
                    }

                    isUpdating = true;
                    editText.setText(mascara.toString());
                    editText.setSelection(mascara.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            private String unmask(String value) {
                // Implement your unmask logic here
                return value;
            }
        };
    }
    public boolean validateDate(String cardMonth, String cardYear, View view) {
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int monthCard = Integer.parseInt(cardMonth);
        int yearCard = Integer.parseInt(cardYear);

        if (yearCard == yearNow) {
            if (monthCard >= monthNow && monthCard <= 12) {
                return true;
            } else {
                new SnackBarHelper().showSnackBar(view, "Mes de tarjeta invalido");
            }
        } else if (yearCard > yearNow) {
            if (monthCard >= 1 && monthCard <= 12) {
                return true;
            } else {
                new SnackBarHelper().showSnackBar(view, "Mes de tarjeta invalido");
            }
        } else {
            new SnackBarHelper().showSnackBar(view, "Año de tarjeta invalido");
        }
        return false;
    }

}
