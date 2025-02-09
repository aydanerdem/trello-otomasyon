package com.trello.test;

import com.trello.main.MainTrello;
import com.trello.utils.Config;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTrello {
    private Logger logger = LogManager.getLogger();
    private static String boardId;
    private static String listId;
    private static String cardId1;
    private static String cardId2;
    private static List<String> cardInfo1;
    private static List<String> cardInfo2;


    @Test
    @Order(0)
    public void boardAndListAndCardCreate() {
        // Board oluşturma
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        String boardName = "TestPanosu " + formattedDateTime;
        List<String> boardInfo = MainTrello.createBoard(boardName);
        boardId = boardInfo.get(0);
        Config.updateProperty("boardIdKey", boardId);
        String createdBoardName = boardInfo.get(1);
        logger.info("Board Oluşturuldu: " + createdBoardName);

        //List oluşturma
        String listName = "TestListesi";
        List<String> listInfo = MainTrello.createList(boardId, listName);
        listId = listInfo.get(0);
        Config.updateProperty("listIdKey", listId);
        String createdListName = listInfo.get(1);
        logger.info("Liste Oluşturuldu: " + createdListName);

        /* 2kart ekleme */
        String cardName1 = "testKart1";
        String cardName2 = "testKart2";
        cardInfo1 = MainTrello.createCard(listId, cardName1);
        cardInfo2 = MainTrello.createCard(listId, cardName2);
        cardId1 = cardInfo1.get(0);
        Config.updateProperty("cardId1Key", cardId1);
        cardId2 = cardInfo2.get(0);
        Config.updateProperty("cardId2Key", cardId2);
        List<String> createdCardNames = List.of(cardInfo1.get(1), cardInfo2.get(1));
        logger.info("Kartlar oluşturuldu: " + createdCardNames.get(0) + " " + createdCardNames.get(1));
    }

    @Test
    @Order(1)
    public void updateRandomCard()
    {
        //random kart adı değiştirme
        String newCardName = "testKart Güncelleme";
        List<String> updatedCardInfo = MainTrello.updateCard(Config.getBoardId(), newCardName);
        logger.info(updatedCardInfo.get(0) + " id'li kart adı güncellendi: " + updatedCardInfo.get(1));
    }

    @Test
    @Order(2)
    public void deleteCards()
    {
        MainTrello.deleteCard(Config.getCardId1());
        logger.info("1. Kart silindi");;
        MainTrello.deleteCard(Config.getCardId2());
        logger.info("2. Kart silindi");
    }

    @Test
    @Order(3)
    public void deleteBoard()
    {
        MainTrello.deleteBoard(Config.getBoardId());
        System.out.println("Board Silindi");
    }
}