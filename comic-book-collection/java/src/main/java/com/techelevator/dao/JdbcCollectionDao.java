package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JdbcCollectionDao implements CollectionDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardCollection> getCardCollections(String username) {
        List<CardCollection> cardCollections = new ArrayList<>();
        String sqlQuery = "SELECT collection_id, name, description, is_private, username FROM collection\n" +
                "JOIN users ON collection.user_id = users.user_id\n" +
                "ORDER BY is_private;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
        while (results.next()) {
            CardCollection cardCollection = mapRowToCardCollection(results);
            if (!cardCollection.isPrivate() || cardCollection.getOwnerUsername().equals(username)) {
                cardCollections.add(cardCollection);
            }
        }
        return cardCollections;
    }

    @Override
    public CardCollection getCardCollectionById(int id, String username) {
        CardCollection cardCollection = null;
        List<Card> cards = new ArrayList<>();
        String queryForCollectionDetails = "SELECT collection_id, name, description, is_private, username FROM collection\n" +
                "JOIN users ON collection.user_id = users.user_id\n" +
                "WHERE collection_id = ?;";
        String queryForCards = "SELECT card.card_id AS card_id, card.name AS name, large_image, small_image, quantity\n" +
                "FROM collection\n" +
                "JOIN collection_card ON collection.collection_id = collection_card.collection_id\n" +
                "JOIN card ON collection_card.card_id = card.card_id\n" +
                "WHERE collection.collection_id = ?;";
        SqlRowSet collectionDetailsResults = jdbcTemplate.queryForRowSet(queryForCollectionDetails, id);
        if (collectionDetailsResults.next()) {
            cardCollection = mapRowToCardCollection(collectionDetailsResults);
        }
        SqlRowSet cardsResults = jdbcTemplate.queryForRowSet(queryForCards, id);
        while (cardsResults.next()) {
            Card card = mapRowToCard(cardsResults);
            cards.add(card);
        }
        cardCollection.setCards(cards);
        if (!cardCollection.isPrivate() || cardCollection.getOwnerUsername().equals(username)) {
            return cardCollection;
        } else {
            return null;
        }
    }

    @Override
    public boolean toggleCollectionPrivacyStatus(int id, String username, boolean privacy) {
        String sqlQuery = "UPDATE collection SET is_private = ?\n" +
                "WHERE collection_id = ? AND user_id =\n" +
                "\t(SELECT user_id FROM users WHERE username = ?);";
        int rowsChanged = jdbcTemplate.update(sqlQuery, privacy, id, username);
        if (rowsChanged > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createNewCollection(NewCollectionDTO newCollection){
        String sqlQuery = "INSERT INTO collection (user_id, name, description, is_private)\n" +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, newCollection.getUserId(), newCollection.getTitle(), newCollection.getDescription(), newCollection.isPrivate());
    }

    @Override
    public void addCardToCollection(int id, Card card) {
        String sqlQuery = "SELECT * FROM card\n" +
                        "WHERE name = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, card.getName());
        if (!results.next()){
            sqlQuery = "INSERT INTO card (name, large_image, small_image)\n" +
                    "VALUES (?,?,?)\n" +
                    "RETURNING card_id";
            jdbcTemplate.update(sqlQuery, card.getName(), card.getImages().getLarge(),
                    card.getImages().getSmall());
        }
        sqlQuery = "INSERT INTO collection_card (card_id, collection_id)\n" +
                    "VALUES ((SELECT card_id FROM card WHERE name = ?), ?);";
        jdbcTemplate.update(sqlQuery, card.getName(), id);
    }

    @Override
    public void deleteCollection(int id, String username) {
        String queryToDeleteCardsFromCollection = "DELETE FROM collection_card\n" +
                "WHERE collection_id IN (SELECT collection_id FROM collection WHERE user_id = (SELECT user_id FROM users WHERE username = ?))\n" +
                "\tAND collection_id = ?;";
        String queryToDeleteCollection = "DELETE FROM collection\n" +
                "WHERE collection_id = ?\n" +
                "AND user_id = (SELECT user_id FROM users WHERE username = ?);";
        jdbcTemplate.update(queryToDeleteCardsFromCollection, username, id);
        jdbcTemplate.update(queryToDeleteCollection, id, username);
    }

    @Override
    public void updateCollection(String name, int id, CardCollection collection) {
        // TODO loop through cards in collection and remove or add them to database
        String sqlQuery = "UPDATE collection \n" +
                "SET name = ?,  description = ?, is_private = ?\n" +
                "WHERE collection_id = ?";
        jdbcTemplate.update(sqlQuery, collection.getTitle(), collection.getDescription(), collection.isPrivate(), id);
    }

    @Override
    public void updateCollectionQuantity(int collectionId, String cardId, int quantity) {
        String sqlQuery = "UPDATE collection_card SET quantity = ? WHERE collection_id = ? AND card_id = ?;";
        jdbcTemplate.update(sqlQuery, quantity, collectionId, cardId);
    }

    @Override
    public void deleteCardFromCollection(int collectionId, String cardId) {
        String sqlQuery = "DELETE FROM collection_card WHERE collection_id = ? AND card_id = ?;";
        jdbcTemplate.update(sqlQuery, collectionId, cardId);
    }


    private CardCollection mapRowToCardCollection(SqlRowSet results) {
        CardCollection cardCollection = new CardCollection();
        cardCollection.setCollectionId(results.getInt("collection_id"));
        cardCollection.setTitle(results.getString("name"));
        cardCollection.setOwnerUsername(results.getString("username"));
        cardCollection.setDescription(results.getString("description"));
        cardCollection.setIsPrivate(results.getBoolean("is_private"));
        return cardCollection;
    }

    private Card mapRowToCard(SqlRowSet results) {
        Card card = new Card();
        Images externalApiCardImagesDto = new Images();
        card.setId(results.getString("card_id"));
        card.setName(results.getString("name"));
        externalApiCardImagesDto.setLarge(results.getString("large_image"));
        externalApiCardImagesDto.setSmall(results.getString("small_image"));
        card.setImages(externalApiCardImagesDto);
        card.setQuantity(results.getInt("quantity"));
        return card;
    }
}
