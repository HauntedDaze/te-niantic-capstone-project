package com.techelevator.model;

public class CardDTO {
    private Card card;
    private int quantity;

    public CardDTO() {

    }

    public CardDTO(Card card, int quantity) {
        this.card = card;
        this.quantity = quantity;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
