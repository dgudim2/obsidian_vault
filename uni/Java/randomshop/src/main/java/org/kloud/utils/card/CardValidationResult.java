package org.kloud.utils.card;

import lombok.Getter;

/**
 * @see <a href="https://gist.github.com/icchan/47d83bacc5113db59fbc">Source</a>
 */
public class CardValidationResult {
    @Getter
    private boolean valid;
    @Getter
    private CardCompany cardType;
    @Getter
    private String error;
    @Getter
    private final String cardNo;

    public CardValidationResult(String cardNo, String error) {
        this.cardNo = cardNo;
        this.error = error;
    }
    public CardValidationResult(String cardNo, CardCompany cardType) {
        this.cardNo = cardNo;
        this.valid = true;
        this.cardType = cardType;
    }

    public String getMessage() {
        return cardNo + "    >>    " + ((valid) ? ("card: " + this.cardType ): error) ;
    }
}
