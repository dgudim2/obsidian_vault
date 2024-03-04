package org.kloud.utils.card;

// All credit goes to https://gist.github.com/icchan/47d83bacc5113db59fbc
public class RegexCardValidator {
    /**
     * Checks if the field is a valid credit card number.
     * @param cardIn The card number to validate.
     * @return Whether the card number is valid.
     */
    public static CardValidationResult isValid(final String cardIn) {
        String card = cardIn.replaceAll("[^0-9]+", ""); // remove all non-numerics
        if (card.length() < 13 || card.length() > 19) {
            return new CardValidationResult(card,"Failed length check");
        }

        if (!luhnCheck(card)) {
            return new CardValidationResult(card,"Failed luhn check");
        }

        CardCompany cc = CardCompany.gleanCompany(card);
        if (cc == null) return new CardValidationResult(card,"Failed card company check");

        return new CardValidationResult(card,cc);
    }

    /**
     * Checks for a valid credit card number.
     * @param cardNumber Credit Card Number.
     * @return Whether the card number passes the luhnCheck.
     */
    protected static boolean luhnCheck(String cardNumber) {
        // number must be validated as 0..9 numeric first!!
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch(NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) { // not
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return sum != 0 && (sum % 10 == 0);
    }
}
